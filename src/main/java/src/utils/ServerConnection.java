package src.utils;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.propertyManagement.ServerCredentialsProperties;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ServerConnection {

    private static Logger logger = LoggerFactory.getLogger(ServerConnection.class);
    private String host;
    private int port;
    private String username;
    private String password;
    private Session session;

    private static String path;

    public ServerConnection(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }


    public void connect() {
        try {
            JSch jsch = new JSch();

            session = jsch.getSession(username, host, port);
            session.setPassword(password);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            logger.info("Connected to :  {}" , host);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if (session != null && session.isConnected()) {
            session.disconnect();
            logger.info("Disconnected to : {}", host);
        }
    }

    public String executeCommand(String command) throws InterruptedException {
        StringBuilder commandOutput = new StringBuilder();

        try {

            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            InputStream commandInputStream = channel.getInputStream();
            InputStream commandErrorStream = ((ChannelExec) channel).getErrStream();

            channel.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(commandInputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                commandOutput.append(line).append("\n");
            }

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(commandErrorStream));
            while ((line = errorReader.readLine()) != null) {
                logger.info("Error: {}" , line);
            }

            Thread.sleep(3000);

            channel.disconnect();

        } catch (JSchException | IOException e) {
            e.printStackTrace();
        }

        return commandOutput.toString();
    }

    public List<String> getPodsByPrefix(String prefix, String fetchPodsResponse) {
        List<String> matchingPods = new ArrayList<>();

        try {


            String[] podLines = fetchPodsResponse.split("\n");
            for (String podLine : podLines) {

                if (podLine.startsWith("NAME")) {
                    continue;
                }

                String podName = podLine.split("\\s+")[0].trim();

                if (podName.contains(prefix)) {
                    matchingPods.add(podName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return matchingPods;
    }

    public void executeCommandsInSequence(String podName, String environment, List<String> commands, String logFilePath) throws FileNotFoundException {
        StringBuilder combinedCommand = new StringBuilder();
        for (String command : commands) {
            combinedCommand.append(command).append(" && ");
        }

        String finalCommand = combinedCommand.substring(0, combinedCommand.length() - 3);

        String loginCommand = "kubectl exec -it " + podName + " -n "+ environment +" -c instaproxy -- /bin/bash -c '"
                + "sleep " + 5 + " && " + finalCommand + "'";

        Channel channel = null;
        OutputStream outputStream = null;

        try {
            channel = session.openChannel("exec");
            ChannelExec channelExec = (ChannelExec) channel;
            channelExec.setCommand(loginCommand);
            channelExec.setPty(true);
            channel.setInputStream(null);
            channel.setOutputStream(new PrintStream(new FileOutputStream("err.log"), true));
            ((ChannelExec) channel).setErrStream(new PrintStream(new FileOutputStream("err.log"), true));

            outputStream = new FileOutputStream(logFilePath);
            channel.setOutputStream(outputStream);

            channel.connect();

            while (!channel.isClosed()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.error("Thread interrupted while sleeping", e);
                }
            }
        } catch (JSchException | IOException e) {
            logger.error("Exception while executing SSH command", e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("Error closing output stream", e);
                }
            }

            if (channel != null) {
                channel.disconnect();
            }
        }

    }

    public static String fetchInstaLog(String environment , String orderId) {
        String instaproxyPath = "reports/logs/";
        String fileName =  "instaproxy_"+DateUtil.getTimeStamp()+".log";
        String logsStageResponse = null;

        path = Utils.createTxtFile(instaproxyPath,fileName);

        ServerConnection sshConnection = new ServerConnection(
                ServerCredentialsProperties.getProperty("stage.host"),
                Integer.parseInt(ServerCredentialsProperties.getProperty("stage.port")),
                ServerCredentialsProperties.getProperty("stage.username"),
                ServerCredentialsProperties.getProperty("stage.password"));
        sshConnection.connect();

        try {
            logsStageResponse = sshConnection.executeCommand("kubectl get pods -n " + environment + " | grep instaproxy");
            List<String> instaproxyPods = sshConnection.getPodsByPrefix("instaproxy-", logsStageResponse);

            String instaproxyPod = instaproxyPods.get(0);

            String grepCommand = ServerCredentialsProperties.getProperty("insta.grep");
            String logFile = ServerCredentialsProperties.getProperty("insta.file");
            String zcat = ServerCredentialsProperties.getProperty("insta.zcat");

            List<String> commands = List.of(
                    ServerCredentialsProperties.getProperty("insta.path"),
                    grepCommand + " '" + orderId + "' " +  logFile
                   // zcat + " " + logFile + " | grep -A20 '" + orderId + "'"

            );

            sshConnection.executeCommandsInSequence(instaproxyPod, environment, commands, path);

            sshConnection.disconnect();
        } catch (InterruptedException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "../logs/"+fileName;
    }
}
