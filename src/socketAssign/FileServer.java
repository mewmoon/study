package socketAssign;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author dxy
 * @version 1.0
 */
public class FileServer {
    public static final String PREFIX_PATH = new String("D:/data/atest");

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(80)) {
            while (true) {
                Socket client = server.accept();
                new Thread(() -> {
                    try {
                        handleClient(client);
                    } catch (IOException e) {
                        System.out.println("Error: " + e.getMessage());
//                        e.printStackTrace();
                    }
                }).start();
            }

        }

    }

    private static void handleClient(Socket client) throws IOException {
        // 如何获取http头部
        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        OutputStream out = client.getOutputStream();

        String requestLine = reader.readLine();  // 读取 GET /xxx HTTP/1.1
        System.out.println("Request line: " + requestLine);
        String[] parts = requestLine.split(" ");
        File filePath = new File(PREFIX_PATH, parts[1]); // 自动处理斜杠
        System.out.println("File: " + filePath.getAbsolutePath());


        // 再循环读取后续头部
        String headerLine;
        while ((headerLine = reader.readLine()) != null && !headerLine.isEmpty()) {
            System.out.println("Header: " + headerLine);
        }

        String header;
        if (!filePath.exists()) {
            header = "HTTP/1.1 404 Not Found\r\nContent-Type: text/html\r\n\r\n";
            out.write(header.getBytes());
            out.write("<html><body><h1>404 File Not Found</h1></body></html>".getBytes());
        } else {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            header = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";
            out.write(header.getBytes());
            byte[] fileContent = new byte[1024];
            int len;
            while ((len = fileInputStream.read(fileContent)) != -1) {
                out.write(fileContent, 0, len);
            }
            fileInputStream.close();
        }
        out.flush();
        client.close();
    }
}
