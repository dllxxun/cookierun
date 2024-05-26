package network;

import java.io.*;
import java.net.*;

public class GameServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            serverSocket = new ServerSocket(8000);  // 포트 번호 8000
            System.out.println("[Server] Waiting for client connection...");

            clientSocket = serverSocket.accept();
            System.out.println("[Server] Client connected.");

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("From Client: " + inputLine);
                if ("quit".equalsIgnoreCase(inputLine)) break;
                out.println(inputLine); // Echo back the message to the client
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (clientSocket != null) clientSocket.close();
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("[Server] Connection closed.");
        }
    }
}
