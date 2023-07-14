package Server;

import java.io.*;
import java.net.*;
import java.util.Random;

public class Server {

	public void ServerStart(int port) throws IOException {
		ServerSocket serverSocket = new ServerSocket(port);
		System.out.println("Server da khoi dong Port: " + port);

		while (true) {
			Socket socket = serverSocket.accept();
			System.out.println("Client " + socket.getInetAddress() + " da ket noi");
			Thread thread = new Thread(new ThreadServer(socket));
		    thread.start();
			
		}
	}

	public static void main(String[] args) {
		Server server = new Server();
		try {
			server.ServerStart(3333);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
