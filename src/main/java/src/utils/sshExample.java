package src.utils;

import com.jcraft.jsch.*;
import src.globalConstant.FilePaths;

import java.io.IOException;
import java.io.InputStream;

public class sshExample {
        public static void main(String[] args) {
            String jumpHost = "10.74.1.71";
            String finalHost = "10.74.4.89";
            String user = "ayush_45110";
            int jumpPort = 22;

            try {
                // Start a new JSch instance
                JSch jsch = new JSch();

                // Add the private key for authentication
                jsch.addIdentity(FilePaths.SSH_PRIVATE_KEY + "id_rsa", FilePaths.SSH_PRIVATE_KEY + "id_rsa.pub");

                // Connect to the jump host
                Session jumpSession = jsch.getSession(user, jumpHost, jumpPort);
                jumpSession.setConfig("StrictHostKeyChecking", "no");
                jumpSession.connect();

                System.out.println(jumpSession.getHost());

                // Forward a port from the jump host to the final host
                int forwardedPort = jumpSession.setPortForwardingL(0, finalHost, 22);

                System.out.println(forwardedPort);

                // Connect to the final host via the jump host
                //Session finalSession = jsch.getSession(user, "localhost", forwardedPort);
                Session finalSession = jsch.getSession("10.74.4.89");
                finalSession.setConfig("StrictHostKeyChecking", "no");
                finalSession.connect();

                // Execute a command on the final host
                Channel channel = finalSession.openChannel("exec");
                ((ChannelExec) channel).setCommand("ls -l");
                channel.setInputStream(null);
                ((ChannelExec) channel).setErrStream(System.err);
                InputStream in = channel.getInputStream();
                channel.connect();
                byte[] tmp = new byte[1024];
                while (true) {
                    while (in.available() > 0) {
                        int i = in.read(tmp, 0, 1024);
                        if (i < 0) break;
                        System.out.print(new String(tmp, 0, i));
                    }
                    if (channel.isClosed()) {
                        if (in.available() > 0) continue;
                        System.out.println("exit-status: " + channel.getExitStatus());
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (Exception ee) {
                    }
                }
                channel.disconnect();
                finalSession.disconnect();
                jumpSession.disconnect();
            } catch (JSchException | IOException e) {
                e.printStackTrace();
            }
        }
    }

