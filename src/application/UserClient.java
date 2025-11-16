package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author dxy
 * @version 1.0
 */
public class UserClient {
    public static void main(String[] args) throws IOException {
//        initUDP();
        initTCP();
    }

    private static void initTCP() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name: ");
//        String name = "hello, I am " + sc.nextLine();
        String name = sc.nextLine();
        byte[] data = name.getBytes(StandardCharsets.UTF_8);

        // 不固定本地端口，TCP连接关闭需要等待TIME_WAIT（通常 1~4 分钟），绑定null, 9999第二次报BindException
        try (Socket socket = new Socket("127.0.0.1", 12345)) {

            // 发送
            OutputStream out = socket.getOutputStream();
            out.write(data);
            out.flush();

            String localInfo = socket.getLocalAddress().getHostAddress() + ":" + socket.getLocalPort();
            String serverInfo = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
            System.out.println("[REQUEST] From " + localInfo + " To " + serverInfo + ": " + new String(data, StandardCharsets.UTF_8));

            // 接收
            InputStream in = socket.getInputStream();
            byte[] buf = new byte[1024];
            int len = in.read(buf); // 阻塞等待

            String response = new String(buf, 0, len, StandardCharsets.UTF_8);
            System.out.println("[RESPONSE] " + response);
        }
    }

    /**
     * DatagramSocket:  源地址（source IP + port）           -->getLocalAddress
     * DatagramPacket:  目的地址（destination IP + port）    -->getAddress
     */
    private static void initUDP() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String name = "hello, I am " + sc.nextLine();
        byte[] data = name.getBytes(StandardCharsets.UTF_8);

        InetSocketAddress destAddress = new InetSocketAddress("127.0.0.1", 12345);
        DatagramPacket request = new DatagramPacket(data, data.length, destAddress);

        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            // 发送
            datagramSocket.send(request);

            String localInfo = datagramSocket.getLocalAddress().getHostName() + ":" + datagramSocket.getLocalPort();
            String serverInfo = request.getAddress().getHostAddress() + ":" + request.getPort();
            System.out.println("[REQUEST]  From " + localInfo + " To " + serverInfo + ":" + new String(request.getData(), StandardCharsets.UTF_8));

            //接收
            datagramSocket.receive(request);

            String response = new String(request.getData(), 0, request.getLength(), StandardCharsets.UTF_8);
            System.out.println("[RESPONSE] " + response);
        }
    }
}
