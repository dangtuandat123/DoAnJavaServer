package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ThreadClient extends Thread {
	private int					running = 0;
	private Socket 				socket;
	private DataOutputStream 	ou;
	private DataInputStream 	in;
	
	public ThreadClient(Socket socket) {
		this.socket = socket;
		try {
			ou = new DataOutputStream(this.socket.getOutputStream());
			in = new DataInputStream(this.socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public void run() {
		running =1;
		try {
			//---get menu from server---------
			String menu = "--- MENU--- \n";
			int menuLen = in.readInt();
			for (int i=0;i<menuLen;i++) {
				int len = in.readInt();
				byte[] bb = new byte[len];
				in.read(bb, 0, len);
				String s = new String (bb);
			//menu = menu + s + "\n";
			}
			menu = menu + menuLen +". Exit\n";
			menu = menu + "What is your choice?";
			
			Scanner sc = new Scanner (System.in);
			
			while(!socket.isClosed()) {
				//-----each loop , print menu
				System.out.println(menu);
				int choice = sc.nextInt(); sc.hasNextLine();
				if (choice==menuLen) {
					socket.close();
					return;
				} else {
					System.out.println("What is the file path to save?");
				}
								
				String path	= sc.nextLine();
				//-----send choice to server
				ou.write(choice);
				
				//-----receive data
				FileOutputStream 	os 	= new FileOutputStream(path);
				byte[] 				bb	= new byte[1024];
				while (true) {
					int id 		= in.readInt();
					int count 	= in.readInt();
					if (count==-1) break;
					
					
					in.read(bb, 0, count);
					os.write(bb, 0, count);
				}
				os.close();
			}
							
		} catch (Exception e) {
			e.printStackTrace();
		}
		running = 2;
	}
	public boolean isRunning () {
		return this.running == 1;
	}
}


