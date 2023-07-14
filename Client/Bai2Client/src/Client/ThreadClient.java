package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ThreadClient extends Thread {
	private Socket socket;
	
	public  ThreadClient(Socket socket) {
		this.socket = socket;
	}
	@Override
	public void run() {
		try {
			
		
		DataInputStream in = new DataInputStream(socket.getInputStream());
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());

		System.out.println("Danh sach cac file co the tai");
		System.out.println(in.readUTF());
		Scanner sc = new Scanner(System.in);
		System.out.println("Chon 1 file: ");
		String chonFile = sc.nextLine();
		out.writeUTF(chonFile);
		out.flush();
		System.out.println("Nhap thu muc luu file");
		String thuMucLuuFile = sc.nextLine();
		FileOutputStream FileOut = new FileOutputStream(thuMucLuuFile + "\\" + chonFile);

		byte[] bytes = new byte[1024];
		int byteRead;
		while ((byteRead = in.read(bytes)) != -1) {
			FileOut.write(bytes, 0, byteRead);
		}
		FileOut.close();
		in.close();
		out.close();

		System.out.println("Da tai file "+chonFile );
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
