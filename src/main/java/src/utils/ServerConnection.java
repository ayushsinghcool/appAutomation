package src.utils;
public class ServerConnection {

    /*private static Logger logger = LoggerFactory.getLogger(ServerConnection.class);
    private String username;
    private String host;
    private String password;
    private String destinationHost;
    private int port;
    private int sourcePort;
    private int destinationPort;
    private static final String STRICT_HOST_KEY_CHECKING = "StrictHostKeyChecking";
    private static final String STRICT_HOST_KEY_CHECKING_VALUE = "no";
    JSch jsch = new JSch();
    Session session = null;

    public ServerConnection() {
        username = ServerCredentialsProperties.getProperty("server.username");
        host = ServerCredentialsProperties.getProperty("server.host");
        password = ServerCredentialsProperties.getProperty("server.password");
        destinationHost = ServerCredentialsProperties.getProperty("server.destination.host");
        port = parseInt(ServerCredentialsProperties.getProperty("server.port"));
        sourcePort = parseInt(ServerCredentialsProperties.getProperty("server.source.port"));
        destinationPort = parseInt(ServerCredentialsProperties.getProperty("server.destination.port"));
    }

    public Session userAuthPubKey() {
        Properties config = new Properties();
        Session session = null;
        config.put(STRICT_HOST_KEY_CHECKING, STRICT_HOST_KEY_CHECKING_VALUE);
        try {
            jsch.setKnownHosts(new FileInputStream(FilePaths.SSH_PRIVATE_KEY + "known_hosts"));
            jsch.addIdentity(FilePaths.SSH_PRIVATE_KEY + "id_rsa", FilePaths.SSH_PRIVATE_KEY + "id_rsa.pub");
            session = jsch.getSession(username, host, port);
            session.setConfig(config);
            session.connect();
        } catch (JSchException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return session;
    }

    public Session connectToServerWithKey() {
        Session session = null;
        Properties config = new Properties();
        config.put(STRICT_HOST_KEY_CHECKING, STRICT_HOST_KEY_CHECKING_VALUE);
        try {
            ServerConnection serverConnection = new ServerConnection();
            session = serverConnection.userAuthPubKey();
            logger.info(session.getHost());

        } catch (Exception e) {
            e.printStackTrace();

        }
        return session;
    }

    public Session connectToServer() {
        Session session = null;
        try {
            java.util.Properties config = new java.util.Properties();
            config.put(STRICT_HOST_KEY_CHECKING, STRICT_HOST_KEY_CHECKING_VALUE);
            session = jsch.getSession(username, host, port);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            logger.info("Connection Successful, Current User is :  {}", session.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return session;
    }

    public void execCmd(String cmdString){
        if(session == null)
            session = userAuthPubKey();
        else {
            try {
                Channel channel = session.openChannel("exec");
                logger.info("Command is {}", cmdString);
                ((ChannelExec) channel).setCommand(cmdString);
                OutputStream outputStream = channel.getOutputStream();
                ((ChannelExec) channel).setErrStream(outputStream);
                channel.connect();
                InputStream inputStream = channel.getInputStream();
                byte[] buffer = new byte[1024];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    String output = new String(buffer, 0, bytesRead);
                    System.out.print(output);
                }
                //channel.disconnect();

            } catch (NullPointerException | JSchException | IOException e) {
                logger.error("Error : {}", e.getMessage());
            }
        }
    }

    public void executeCommand(String cmdString) {
        try {
            Session session = connectToServer();
            Channel channel = session.openChannel("exec");
            logger.info("Command is {}", cmdString);
            ((ChannelExec) channel).setCommand(cmdString);
            channel.setInputStream(null);
            OutputStream outputStream = channel.getOutputStream();
            ((ChannelExec) channel).setErrStream(outputStream);
            channel.connect();
            channel.disconnect();
            session.disconnect();
            logger.info("Session Disconnected Successfully");
        } catch (NullPointerException | JSchException | IOException e) {
            logger.error("Error : {}", e.getMessage());
        }
    }

    public void disconnectSession(Session session){
        session.disconnect();
    }

    public static void main(String[] args) {

       ServerConnection serverConnection = new ServerConnection();
        //serverConnection.execCmd("tail -500  /var/log/nos-app/nos.log");
        //serverConnection.executeCommand("kubectl exec -it " + "instaproxy-fccddc79-klm9l -n qa14 /bash" + "tail -500  /var/log/nos-app/nos.log");
        new FetchLogs().getServerLog(ServerCredentialsProperties.getProperty("server.logPath"));
       // serverConnection.execCmd("tail -500 nos.log");
      *//*   serverConnection.executeCommand("redis-cli -h 10.144.25.139 -p 3090 -a redispass FLUSHALL");
        serverConnection.executeCommand("redis-cli -h 10.144.25.139 -p 3091 -a redispass FLUSHALL");
        serverConnection.executeCommand("redis-cli -h 10.144.25.139 -p 3070 -a redispass FLUSHALL");
        serverConnection.executeCommand("redis-cli -h 10.144.25.139 -p 3071 -a redispass FLUSHALL");
        serverConnection.executeCommand("redis-cli -h 10.144.25.139 -p 3072 -a redispass FLUSHALL");*//*
    }*/

}
