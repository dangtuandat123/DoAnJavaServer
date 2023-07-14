package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class ThreadServer extends Thread {
	private Socket socket;
	private String Client;

	public ThreadServer(Socket socket,String Client,Map<String, Socket> Clients) {
		this.socket = socket;
		this.Client = Client;	}

	@Override
	public void run() {
		try {
			DataOutputStream out = new DataOutputStream((Client.get(Client)).getOutputStream());
			DataInputStream in = new DataInputStream(Client.getInputStream());
			System.out.println("ngupi chufn");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
