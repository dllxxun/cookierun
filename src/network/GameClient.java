package network;

import java.io.*;
import java.net.*;

public class GameClient {
    private static boolean isUser1;

    public static void main(String[] args) {
        connectToServer();
    }

    private static void connectToServer() {
        BufferedReader in = null;
        PrintWriter out = null;
        Socket socket = null;

        try {
            socket = new Socket("127.0.0.1", 8000);  // 포트 번호 8000
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("[Client] Connected to server.");

            while (true) {
                String inputMessage = in.readLine();
                if ("게임을 시작합니다".equalsIgnoreCase(inputMessage)) {
                    System.out.println("게임을 시작합니다.");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
                System.out.println("[Client] Connection closed.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setReady(boolean isUser1) {
        GameClient.isUser1 = isUser1;
        new Thread(() -> {
            try (Socket socket = new Socket("127.0.0.1", 8000);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                if (isUser1) {
                    out.println("user1: 준비되었습니다");
                    System.out.println("From Client: user1: 준비되었습니다");
                } else {
                    out.println("user2: 준비되었습니다");
                    System.out.println("From Client: user2: 준비되었습니다");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
