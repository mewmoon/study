package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

/**
 * @author dxy
 * @version 1.0
 */
public class UserServer {
    public static void main(String[] args) throws IOException {
        // UDP 服务线程
        new Thread(() -> {
            try {
                initUDP();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // TCP 服务线程
        new Thread(() -> {
            try {
                initTCP();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


    }

    private static void initTCP() throws IOException {
        //欢迎套接字（listening socket）
        try (ServerSocket server = new ServerSocket(12345)) {
            System.out.println("TCP server started");
            System.out.println("Welcome socket hashCode: " + server.hashCode());

            while (true) {
                // 连接套接字（connection socket）
                // (客户端IP, 客户端端口, 服务器IP, 服务器端口)
                Socket socket = server.accept();
                System.out.println("Connection socket hashCode: " + socket.hashCode());

                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();

                byte[] buf = new byte[1024];
                int read = in.read(buf);
                String msg = new String(buf, 0, read);

                String clientInfo = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
                String localInfo = socket.getLocalAddress().getHostAddress() + ":" + socket.getLocalPort();
                System.out.printf("[TCP REQUEST] From %s To %s: %s%n", clientInfo, localInfo, msg);

                String responseMsg = msg.toUpperCase();
                out.write(responseMsg.getBytes());
                out.flush();
                System.out.println("[TCP RESPONSE] " + responseMsg);
            }
        }
    }

    private static void initUDP() throws IOException {

        try (DatagramSocket socket = new DatagramSocket(12345)) {
            System.out.println("UDP server started");
            while (true) {
                byte[] buf = new byte[1024];
                DatagramPacket request = new DatagramPacket(buf, buf.length);

                socket.receive(request);
                String message = new String(request.getData(), 0, request.getLength());
                String clientInfo = request.getAddress().getHostAddress() + ":" + request.getPort();
                System.out.printf("[UDP REQUEST] From %s → %s%n", clientInfo, message);

                String responseMessage = message.toUpperCase();
                DatagramPacket response = new DatagramPacket(responseMessage.getBytes(), responseMessage.length(), request.getAddress(), request.getPort());
                socket.send(response);

                System.out.println("[UDP RESPONSE]: " + responseMessage);
            }
        }
    }
}
