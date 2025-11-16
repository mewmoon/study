package socketAssign;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * @author dxy
 * @version 1.0
 */
public class MailServer {
    public static void main(String[] args) throws IOException {
        sendQmail();
    }

    private static void sendQmail() throws IOException {
        String host = "smtp.qq.com";
        int port = 465; // SSL 端口

        String username = "3250911172@qq.com";
        String password = "ibawsdlcwlfmdaji";  // 不是QQ密码！！

        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket socket = (SSLSocket) factory.createSocket(host, port);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        // 读取服务器欢迎语
        System.out.println("S: " + in.readLine());

        send(out, "EHLO mail.example.com\r\n");
        readMultiline(in);

        send(out, "AUTH LOGIN\r\n");
        System.out.println("S(login): " + in.readLine());

        send(out, base64(username) + "\r\n");
        System.out.println("S(u): " + in.readLine());

        send(out, base64(password) + "\r\n");
        System.out.println("S(*): " + in.readLine());

        send(out, "MAIL FROM:<" + username + ">\r\n");
        System.out.println("S(from): " + in.readLine());

        send(out, "RCPT TO:<220252275@seu.edu.cn>\r\n");
        System.out.println("S(to): " + in.readLine());

        send(out, "DATA\r\n");
        System.out.println("S(data): " + in.readLine());

        // 邮件内容的头部必须包含 From: 和 To:
        send(out, "From: <" + username + ">\r\n");
        send(out, "To: <2152236@tongji.edu.cn>\r\n");
        send(out, "Subject: Test Mail\r\n");
        send(out, "\r\n"); // 空行分隔头部与正文

        send(out, "Hello! This is a test mail.\r\n");
        send(out, ".\r\n");
        System.out.println("S(done): " + in.readLine());


        send(out, "QUIT\r\n");
        System.out.println("S(quit): " + in.readLine());

        socket.close();
    }

    static void send(BufferedWriter out, String s) throws IOException {
        out.write(s);
        out.flush();
        System.out.print("C: " + s);
    }

    static void readMultiline(BufferedReader in) throws IOException {
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println("S: " + line);
            if (!line.startsWith("250-")) break;
        }
    }

    static String base64(String s) {
        return java.util.Base64.getEncoder().encodeToString(s.getBytes());
    }
}
