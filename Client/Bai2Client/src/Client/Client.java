package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public void ClientStart(String host, int port) {
		try {
			Socket socket = new Socket(host, port);
			Thread th = new Thread(new ThreadClient(socket));
			th.start();
			// socket.close();
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		Client client = new Client();
		client.ClientStart("localhost", 1234);
	}
}
