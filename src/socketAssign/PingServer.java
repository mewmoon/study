package socketAssign;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @author dxy
 * @version 1.0
 */
public class PingServer {
    public static void main(String[] args) throws Exception {
        byte[] buffer = new byte[1024];

        try (DatagramSocket server = new DatagramSocket(12345)) {
            System.out.println("UDP PingServer started on port 12345...");

            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                server.receive(request);
                String msg = new String(request.getData(), 0, request.getLength());
                System.out.println("Received: " + msg);

                // 准备响应
                String responseMsg = "Hello World  "+msg;
                byte[] responseBytes = responseMsg.getBytes();
                DatagramPacket response = new DatagramPacket(
                    responseBytes,
                    responseBytes.length,
                    request.getAddress(),
                    request.getPort()
                );

                // 发送响应
                server.send(response);
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
}
