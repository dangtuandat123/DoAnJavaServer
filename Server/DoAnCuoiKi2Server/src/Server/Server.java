package Server;

import java.net.*;
import java.util.HashMap;

public class Server {


	private HashMap threadMap;
	
	public void ServerStart(int port){
		
		try {
			
		ServerGiaoDien serverGiaoDien = new ServerGiaoDien();
		serverGiaoDien.khoiDong();	
		ServerSocket serverSocket = new ServerSocket(port);
		System.out.println("Server da khoi dong Port: " + port);
		threadMap = new HashMap<>();
		
		while (true) {
			
			Socket socket = serverSocket.accept();
			System.out.println("Client " + socket.getInetAddress().getHostName() + " da ket noi");
	
			//taoThread
			ServerThread threadServer = new ServerThread(socket, threadMap,serverGiaoDien);
			Thread thread = new Thread(threadServer);		
			thread.start();	      
	       
	        	        
		}
		
		} catch (Exception e) {
			
		}
	}

	

	public void khoiDong() {
		
		Server server = new Server();
		
		try {
			server.ServerStart(6665);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}

}
