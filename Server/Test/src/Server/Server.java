package Server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    // Khởi tạo một HashMap để lưu trữ danh sách các client kết nối đến server
    private static HashMap<String, Socket> clients = new HashMap<String, Socket>();

    public static void main(String[] args) throws IOException {
        // Khởi tạo socket server với cổng kết nối 5000
        ServerSocket serverSocket = new ServerSocket(5000);

        while (true) {
            // Chấp nhận yêu cầu kết nối từ client
            Socket clientSocket = serverSocket.accept();
            
            // Lấy thông tin địa chỉ IP của client kết nối
            InetAddress clientAddress = clientSocket.getInetAddress();
            String address = clientAddress.getHostAddress();

            // Lưu trữ thông tin kết nối vào HashMap clients
            clients.put(address, clientSocket);
            
            // Tạo một thread mới để xử lý các yêu cầu từ client
            ClientHandler clientHandler = new ClientHandler(clientSocket, clients);
            Thread clientThread = new Thread(clientHandler);
            clientThread.start();
        }
    }
}
