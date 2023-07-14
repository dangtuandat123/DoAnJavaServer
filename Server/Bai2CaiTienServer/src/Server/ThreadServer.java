package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadServer extends Thread {
	private static String[] path = {"D:\\a.txt", "D:\\b.txt"};
	private static String[] menu = {"1.text1.txt", "2.text2.txt"};
	
	
	private Socket 				socket;
	private DataOutputStream 	ou;
	private DataInputStream 	in;
	public ThreadServer(Socket socket, String[] path) {
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
		try {
			//----send menu of file to client----
			ou.write(menu.length);
			for (String s: menu) {
				byte[] bb = s.getBytes();
				ou.write(bb.length);
				ou.write(bb);
			}
			
			//---wait for choice from client
			while (!socket.isClosed()) {
				int choice = in.readInt();
				
				if (choice<0 || choice>path.length) {
					ou.write(-1);
					continue;
				}
				
				FileInputStream fis 	= new FileInputStream(path[choice]);
				byte[] 			bytes 	= new byte[1024];
				int count;
				while ((count = fis.read(bytes)) != -1) {
					synchronized (ou) {
						ou.writeInt(choice);
						ou.writeInt(count);
						ou.write(bytes, 0, count);
					}
				}
				synchronized (ou) {
					ou.writeInt(choice);
					ou.writeInt(-1);
					System.out.println("End of " + path);
				}
			}
		} catch (IOException e) {
			System.out.println(socket + " Vừa Thoát");
		}
	}
}
