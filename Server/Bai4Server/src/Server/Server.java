package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
	private Map<String, Socket> Clients;
	private HashMap clients;
	
	public void ServerStart(int port) {
		try {

			ServerSocket server = new ServerSocket(port);

			System.out.println("Server dang chay tren port: " + port);
			while (true) {
				Socket socket = server.accept();
				clients = new HashMap<>();
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				DataInputStream in = new DataInputStream(socket.getInputStream());
				System.out.println("Client " + socket.getRemoteSocketAddress()+" da ket noi.");
				String nickName = in.readUTF();
				System.out.println(nickName);
				Clients.put(nickName, socket);
				
			//	Thread th = new Thread(new ThreadServer(socket, nickName, Clients));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		Server server = new Server();
		server.ServerStart(1234);

	}
}
