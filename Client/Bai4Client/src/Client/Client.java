package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public void ClientStart(String host, int port) {
		try {
			Socket socket = new Socket(host, port);
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			DataInputStream in = new DataInputStream(socket.getInputStream());
			Scanner sc = new Scanner(System.in);
			String nickName ="";
			nickName = sc.nextLine();
			out.writeUTF(nickName);
			
			
		} catch (Exception e) {
			
		}
	}

	public static void main(String[] args) {
		Client client = new Client();
		client.ClientStart("localhost",1234);
	}
}
