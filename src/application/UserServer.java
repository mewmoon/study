package application;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * @author dxy
 * @version 1.0
 */
public class UserServer {
    public static void main(String[] args) throws IOException {
        initUDP();
    }

    private static void initUDP() throws IOException {

        try (DatagramSocket socket = new DatagramSocket(12345)) {
            while (true) {
                byte[] buf = new byte[1024];
                DatagramPacket request = new DatagramPacket(buf, buf.length);

                socket.receive(request);
                String message = new String(request.getData(), 0, request.getLength());
                String clientInfo = request.getAddress().getHostAddress() + ":" + request.getPort();
                System.out.printf("From %s → %s%n", clientInfo, message);

                String responseMessage = message.toUpperCase();
                DatagramPacket response = new DatagramPacket(responseMessage.getBytes(), responseMessage.length(), request.getAddress(), request.getPort());
                socket.send(response);

                System.out.println("[RESPONSE]: " + responseMessage);
            }
        }
    }
}
