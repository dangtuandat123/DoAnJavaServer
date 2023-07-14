package Server;

import java.io.*;
import java.net.*;
import java.util.*;

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private HashMap<String, Socket> clients;

    public ClientHandler(Socket socket, HashMap<String, Socket> clients) {
        this.clientSocket = socket;
        this.clients = clients;
    }

    @Override
    public void run() {
        try {
            // Lấy luồng dữ liệu đầu vào và ra khỏi socket kết nối tới client
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Đọc và xử lý các yêu cầu từ client
            while (true) {
                String inputLine = in.readLine();
                
                // Nếu client đã ngắt kết nối thì thoát khỏi vòng lặp
                if (inputLine == null) {
                    break;
                }
                
                // Chia chuỗi yêu cầu thành 2 phần: địa chỉ client2 và nội dung tin nhắn
                String[] parts = inputLine.split(":");
                String address = parts[0];
                String message = parts[1];

                // Lấy socket kết nối tới client2 từ danh sách clients
                Socket client2Socket = clients.get(address);
                if (client2Socket != null) {
                    // Gửi tin nhắn đến client2
                    PrintWriter out2 = new PrintWriter(client2Socket.getOutputStream(), true);
                    out2.println(message);
                } else {
                    // Nếu không tìm thấy client2, gửi thông báo lỗi về cho client1
                    out.println("Client " + address + " not found.");
                }
            }
            
            // Đóng các luồng và socket kết nối tới client
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}