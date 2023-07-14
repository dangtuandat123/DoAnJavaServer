package Client;

import java.net.Socket;

public class Client {

	private Socket socket;
	public Client () {
	}
	
	public void startClient(String host, int port) {
		try {
			socket = new Socket(host, port);
			System.out.println("Đã kết nối tới " + socket.getRemoteSocketAddress());
			Thread client = new Thread(new ThreadClient(socket));
			//ThreadClient client = new ThreadClient (socket);
			client.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		Client client = new Client();
		client.startClient("localhost", 7777);
	}
}
