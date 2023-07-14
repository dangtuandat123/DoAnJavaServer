package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	public void startServer(int port, String path[]) {
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Server đang chạy trên cổng " + port);

			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("Đã kết nối tới " + socket.getRemoteSocketAddress());

				ThreadServer threadServer = new ThreadServer(socket, path);
				threadServer.start();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server server = new Server();
		int port = 7777;
		String[] path = { "D:\\received_a.txt", "D:\\received_b.txt", "D:\\received_c.txt" };
		server.startServer(port, path);
	}
}
