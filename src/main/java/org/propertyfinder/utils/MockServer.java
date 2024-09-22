package org.propertyfinder.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MockServer {
    public static void main(String[] args) throws IOException {  
        ServerSocket serverSocket = new ServerSocket(8000);
        while (true) {
            Socket socket = serverSocket.accept();
            handleRequest(socket);
        }
    }

    private static void handleRequest(Socket socket) throws IOException {
        String request = readRequest(socket);
        String url = extractUrl(request);

        // Handle different URL requests
        if (url.contains("/page1.html")) {
            sendResponse(socket, "src/main/resources/page1.html");
        } else if (url.contains("/page2.html")) {
            sendResponse(socket, "src/main/resources/page2.html");
        } else {
            sendResponse(socket, "src/main/resources/index.html");
        }
    }

    private static String readRequest(Socket socket) throws IOException {  
        // Read the request from the socket
        byte[] buffer = new byte[1024];
        int bytesRead = socket.getInputStream().read(buffer);
        return new String(buffer, 0, bytesRead);
    }

    private static String extractUrl(String request) {  
        int startIndex = request.indexOf("GET ") + 4;
        int endIndex = request.indexOf(" HTTP/");
        return request.substring(startIndex, endIndex);
    }

    private static void sendResponse(Socket socket, String htmlFile) throws IOException {  
        // Read the HTML file from disk
        File file = new File(htmlFile);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] htmlBytes = new byte[(int) file.length()];
        fileInputStream.read(htmlBytes);
        fileInputStream.close();
  
        // Send the HTML response
        String response = "HTTP/1.1 200 OK\r\n" +
        "Content-Type: text/html\r\n" +
        "Content-Length: " + htmlBytes.length + "\r\n" +
        "\r\n";
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(response.getBytes());
        outputStream.write(htmlBytes);
        outputStream.flush();
        socket.close();
    }
}
