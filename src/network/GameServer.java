package network;

import java.io.*;
import java.net.*;

public class GameServer {
    private static boolean user1Ready = false;
    private static boolean user2Ready = false;
    private static ServerSocket serverSocket;
    private static Socket clientSocket1;
    private static Socket clientSocket2;
    private static BufferedReader in1;
    private static PrintWriter out1;
    private static BufferedReader in2;
    private static PrintWriter out2;

    public static void main(String[] args) {
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

            new Thread(() -> handleClient(in1, out1, "user1")).start();
            new Thread(() -> handleClient(in2, out2, "user2")).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(BufferedReader in, PrintWriter out, String username) {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                handleClientMessage(message, username);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClientMessage(String message, String username) {
        if (message.equals("user1_ready")) {
            user1Ready = true;
            System.out.println("[Server] user1: 준비되었습니다");
        } else if (message.equals("user2_ready")) {
            user2Ready = true;
            System.out.println("[Server] user2: 준비되었습니다");
        }

        if (user1Ready && user2Ready) {
            startGame();
        }
    }

    private static void startGame() {
        //System.out.println("게임이 시작되었습니다.");
        out1.println("게임을 시작합니다");
        out2.println("게임을 시작합니다");
    }

    private static void closeConnections() {
        try {
            if (in1 != null) in1.close();
            if (out1 != null) out1.close();
            if (clientSocket1 != null) clientSocket1.close();
            if (in2 != null) in2.close();
            if (out2 != null) out2.close();
            if (clientSocket2 != null) clientSocket2.close();
            if (serverSocket != null) serverSocket.close();
            System.out.println("[Server] Connection closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
