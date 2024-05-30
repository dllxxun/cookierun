package network;

import java.io.*;
import java.net.*;

public class GameServer {
    private static boolean user1Ready = false;
    private static boolean user2Ready = false;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        final Socket clientSocket1;
        final Socket clientSocket2;
        final BufferedReader in1;
        final PrintWriter out1;
        final BufferedReader in2;
        final PrintWriter out2;

        try {
            serverSocket = new ServerSocket(8000);  // 포트 번호 8000
            System.out.println("[Server] Waiting for client connection...");

            clientSocket1 = serverSocket.accept();
            System.out.println("[Server] Client 1 connected.");

            clientSocket2 = serverSocket.accept();
            System.out.println("[Server] Client 2 connected.");

            in1 = new BufferedReader(new InputStreamReader(clientSocket1.getInputStream()));
            out1 = new PrintWriter(clientSocket1.getOutputStream(), true);
            in2 = new BufferedReader(new InputStreamReader(clientSocket2.getInputStream()));
            out2 = new PrintWriter(clientSocket2.getOutputStream(), true);

            new Thread(() -> handleClient(in1, out1, true)).start();
            new Thread(() -> handleClient(in2, out2, false)).start();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("[Server] Connection closed.");
        }
    }

    private static void handleClient(BufferedReader in, PrintWriter out, boolean isUser1) {
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null) {
                System.out.println("From Client: " + inputLine);
                if ("user1: 준비되었습니다".equalsIgnoreCase(inputLine)) {
                    user1Ready = true;
                    System.out.println("[Server] user1: 준비되었습니다");
                } else if ("user2: 준비되었습니다".equalsIgnoreCase(inputLine)) {
                    user2Ready = true;
                    System.out.println("[Server] user2: 준비되었습니다");
                }

                if (user1Ready && user2Ready) {
                    System.out.println("준비완료 되었습니다");
                    System.out.println("게임을 시작합니다");
                    out.println("게임을 시작합니다");
                    break; // 모든 준비가 완료되면 루프를 종료
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
