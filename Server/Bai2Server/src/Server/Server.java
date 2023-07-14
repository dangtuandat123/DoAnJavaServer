package Server;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public void ServerStart(int port) {
		try {
			ServerSocket server = new ServerSocket(port);
			System.out.println("Server da khoi dong port: " + port + ".");
			while (true) {

				Socket socket = server.accept();
				System.out.println("Client " + socket.getRemoteSocketAddress() + " da ket noi.");
				Thread th = new Thread(new ThreadServer(socket));
				th.start();

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.ServerStart(1234);

	}
}
