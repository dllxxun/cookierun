package network;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class GameClient {
    public static void main(String[] args) {
        BufferedReader in = null;
        PrintWriter out = null;
        Socket socket = null;
        Scanner scanner = new Scanner(System.in);

        try {
            socket = new Socket("127.0.0.1", 8000);  // 포트 번호 8000
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("[Client] Connected to server.");

            while (true) {
                System.out.print("전송하기>>> ");
                String outputMessage = scanner.nextLine();
                out.println(outputMessage);
                if ("quit".equalsIgnoreCase(outputMessage)) break;

                String inputMessage = in.readLine();
                if (inputMessage == null) break; // 서버가 연결을 닫은 경우
                System.out.println("From Server: " + inputMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                scanner.close();
                if (socket != null) socket.close();
                System.out.println("[Client] Connection closed.");
            } catch (IOException e) {
                System.out.println("소켓통신에러");
            }
        }
    }
}
