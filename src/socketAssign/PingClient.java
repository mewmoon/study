package socketAssign;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author dxy
 * @version 1.0
 */
public class PingClient {
    public static void main(String[] args) throws Exception {
        String serverHost = "localhost"; // 服务器地址
        int serverPort = 12345;           // 服务器端口
        int timeout = 1000;              // 等待响应超时 1000 ms

        try (DatagramSocket clientSocket = new DatagramSocket()) {
            clientSocket.setSoTimeout(timeout); // 设置接收超时

            for (int i = 1; i <= 10; i++) {
                String message = "PING " + i;
                byte[] sendData = message.getBytes();

                // 发送 UDP 请求
                DatagramPacket request = new DatagramPacket(
                    sendData,
                    sendData.length,
                    InetAddress.getByName(serverHost),
                    serverPort
                );

                long sendTime = System.currentTimeMillis();
                clientSocket.send(request);

                // 接收响应
                byte[] buffer = new byte[1024];
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);

                try {
                    clientSocket.receive(response);
                    long receiveTime = System.currentTimeMillis();
                    long rtt = receiveTime - sendTime;

                    String reply = new String(response.getData(), 0, response.getLength());
                    System.out.println("Reply from server: " + reply + " | RTT = " + rtt + " ms");

                } catch (java.net.SocketTimeoutException e) {
                    System.out.println("Request timed out for ping " + i);
                }

                // 每次 ping 间隔 1 秒
                Thread.sleep(1000);
            }
        }
    }
}
