package socketAssign;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author dxy
 * @version 1.0
 */
public class FileServer {
    public static String PREFIX_PATH = new String("D:/data/atest/");

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(12345)) {
            Socket client = server.accept();
            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();
            byte[] buffer = new byte[1024];
            int readLen = in.read(buffer);

            String path = new String(buffer, 0, readLen);
            File file = new File(PREFIX_PATH.concat(path));
            System.out.println("File: " + file.getAbsolutePath());
            if (!file.exists()) {
                String replyMessage = "404 Not Found";
                out.write(replyMessage.getBytes());
            } else {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] fileContent = new byte[1024];
                int len;
                while ((len = fileInputStream.read(fileContent)) != -1) {
                    out.write(fileContent, 0, len);
                }
                fileInputStream.close();
            }
            out.flush();
        }

    }
}
