package src.utils;

import com.aventstack.extentreports.ExtentTest;
import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.globalConstant.FilePaths;
import src.propertyManagement.ServerCredentialsProperties;
import src.reportManagement.ExtentManager;

import java.io.*;

public class FetchLogs {

    private Logger logger = LoggerFactory.getLogger(FetchLogs.class);
    ServerConnection serverConnection = new ServerConnection();
    ExtentTest pageInfo;

    public FetchLogs() {
        pageInfo = ExtentManager.getTest();
    }

    //    private static final  String NCMC_LOGS = ServerCredentialsProperties.getProperty("server.logPath");
    private static final String LOG_LIMIT = ServerCredentialsProperties.getProperty("server.log.limit");

    private static final String NCMC_LOGS_OUT_PATH = FilePaths.NCMC_LOGS;
    private static final String NCMC_LOG_GREP_COMMAND = "tail -" + LOG_LIMIT + " ";
    private static final int BYTE_CONSTANT = 1024;

    private static void createLogsDirectory() {
        File directory = new File(FilePaths.NCMC_LOGS);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    /**
     * To print server logs in a log file
     *
     * @return LogFileName
     */

    public static void connectToVpn() {
        try {
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            com.jcraft.jsch.Session session = jsch.getSession("", "", 22);
            session.setPassword("");
            session.setConfig(config);
            session.connect();
            session.setPortForwardingL(22, "", 1525);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(String fileName ,byte[] tmp, int i){
        try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true))){
            String content;
            content = (new String(tmp, 0, i));
            out.write(content);
        } catch (IOException | NullPointerException e) {
            logger.info("Error : {}",e.getMessage());
        }
    }

    public String getServerLog(String logFileName){
        serverConnection.executeCommand("cd ..");
        String ncmcFileName = null;
        try {
            createLogsDirectory();
            String fileName = new File(logFileName).getName().split("\\.")[0];
            ncmcFileName = NCMC_LOGS_OUT_PATH + fileName + DateUtil.getTimeStamp() + ".log";
            logger.info("Log File Name {}", ncmcFileName);
            Session session = serverConnection.connectToServer();
            Channel channel = session.openChannel("exec");
            InputStream inputStream = channel.getInputStream();
            OutputStream outputStream = channel.getOutputStream();
            ((ChannelExec) channel).setCommand(NCMC_LOG_GREP_COMMAND + logFileName);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(outputStream);
            channel.connect();
            byte[] tmp = new byte[BYTE_CONSTANT];
            while (true) {
                while (inputStream.available() > 0) {
                    int i = inputStream.read(tmp, 0, BYTE_CONSTANT);
                    if (i < 0) break;
                    writeToFile(ncmcFileName,tmp,i);
                }
                if (channel.isClosed()) {
                    break;
                }
            }
            channel.disconnect();
            session.disconnect();
            logger.info("Session Disconnected successfully");
        } catch (JSchException | IOException e) {
            logger.error("Error {}", e.getMessage());
        }
        return ncmcFileName;
    }

}
