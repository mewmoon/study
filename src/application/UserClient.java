package application;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author dxy
 * @version 1.0
 */
public class UserClient {
    public static void main(String[] args) throws IOException {
        initUDP();
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
            datagramSocket.send(request);
            String localInfo = datagramSocket.getLocalAddress().getHostName()+ ":" + datagramSocket.getLocalPort();
            String serverInfo = request.getAddress().getHostAddress() + ":" + request.getPort();
            System.out.println("[REQUEST]  From " + localInfo + " To " + serverInfo + ":" + new String(request.getData(), StandardCharsets.UTF_8));

            datagramSocket.receive(request);
            String response = new String(request.getData(), 0, request.getLength(), StandardCharsets.UTF_8);
            System.out.println("[RESPONSE] " + response);
        }
    }
}
