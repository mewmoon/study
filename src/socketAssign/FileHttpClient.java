package socketAssign;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author dxy
 * @version 1.0
 * Simple HTTP Client
 */
public class FileHttpClient {
    public static void main(String[] args) throws IOException {
        String serverHost = "localhost"; // 服务器地址
        int serverPort = 80;           // 服务器端口
        String requestFile = "/f2.txt"; // 请求文件
        try (Socket socket = new Socket(serverHost, serverPort)){
            // 1️⃣ 获取输出流，向服务器发送 HTTP 请求
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // HTTP Request Line + Host 头 + 空行
            out.println("GET " + requestFile + " HTTP/1.1");
            out.println("Host: " + serverHost);
            out.println("Connection: close"); // 请求完成后关闭连接
            out.println(); // 空行，表示请求头结束

            // 2️⃣ 获取输入流，读取服务器响应
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // 打印响应到控制台
            }
        }
    }
}
