package network;

import java.io.*;
import java.net.*;
import main.Main;

public class GameClient {
    private static boolean isUser1 = true; // 기본값을 true로 설정하여 첫 번째 클라이언트는 user1로 시작
    private static PrintWriter out;
    private static BufferedReader in;
    private static Socket socket;
    private static Main mainInstance; // Main 인스턴스

    public static void main(String[] args) {
        connectToServer();
    }

    public static void connectToServer() {
        try {
            socket = new Socket("127.0.0.1", 8000);  // 포트 번호 8000
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("[Client] Connected to server.");

            while (true) {
                String inputMessage = in.readLine();
                if ("게임을 시작합니다".equalsIgnoreCase(inputMessage)) {
                    System.out.println("게임을 시작합니다.");
                    if (mainInstance != null) {
                        mainInstance.startGame(); // Main 인스턴스의 startGame 메서드 호출
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readyUser1() {
        System.out.println("user1: 준비되었습니다");
        out.println("user1_ready");
    }

    public static void readyUser2() {
        System.out.println("user2: 준비되었습니다");
        out.println("user2_ready");
    }

    public static void setMainInstance(Main main) {
        mainInstance = main;
    }

    public static void closeConnection() {
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
