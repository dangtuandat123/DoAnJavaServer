package Client;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        // Tạo socket kết nối đến server tại địa chỉ IP và cổng kết nối cho trước
        Socket socket = new Socket("localhost", 5000);

        // Tạo luồng dữ liệu đầu vào và ra từ socket kết nối
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Đọc địa chỉ IP của client2 từ bàn phím
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter the IP address of client2: ");
        String address = userInput.readLine();

        // Đọc nội dung tin nhắn từ bàn phím và gửi đến server với địa chỉ IP của client2
        while (true) {
            System.out.print("Enter message to send to " + address + ": ");
            String message = userInput.readLine();
            out.println(address + ":" + message);

            // Nhận phản hồi từ server và in ra màn hình
            String response = in.readLine();
            System.out.println("Server response: " + response);
        }
    }
}