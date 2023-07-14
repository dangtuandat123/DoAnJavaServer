package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadServer extends Thread {

	private Socket socket;

	public ThreadServer(Socket socket) throws IOException{
		this.socket = socket;
	}

	@Override
	public void run() {
		try {

			// lay ds file
			File thuMuc = new File("C:\\Users\\ADMIN\\Desktop\\hocTap\\Java\\FileServer");
			File[] files = thuMuc.listFiles();
			String danhSachFile = "";
			for (int i = 0; i < files.length; i++) {
				danhSachFile = danhSachFile + files[i].getName() + "\n";
			}

			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			// gui ds file
			out.writeUTF(danhSachFile);
			out.flush();
			System.out.println("-------");
			String fileClientChon = in.readUTF();
			
			System.out.println("Client "+socket.getRemoteSocketAddress()+" yeu cau tai file: "+ fileClientChon);

			FileInputStream docFile = new FileInputStream(
					"C:\\Users\\ADMIN\\Desktop\\hocTap\\Java\\FileServer\\" + fileClientChon);
			byte[] bytes = new byte[1024];
			int byteRead;
			while ((byteRead = docFile.read(bytes)) != -1) {
				out.write(bytes, 0, byteRead);
			}
			out.flush();
			docFile.close();
			socket.close();
			
		} catch (Exception e) {
			System.out.println("Client "+socket.getRemoteSocketAddress()+" da thoat.");
		}

	}

}
