package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	
	public void ClientStart(String host, int port) {
		try {
			Socket client = new Socket(host, port);
			Thread thread = new Thread( new ClientThread(client));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void khoiDong() {
		Client client = new Client();
		try {
			client.ClientStart("localhost", 5555);
		} catch (Exception e) {

		}
	}
}
