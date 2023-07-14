package Server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ThreadServer extends Thread{
	
	private Socket socket;

	public ThreadServer(Socket socket) throws IOException {
		this.socket = socket;
		
	}
	
	@Override
	public void run() {
		try {
		
		InputStream inputStream = socket.getInputStream();
		
		FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\ADMIN\\Desktop\\hocTap\\Java\\FileServer\\" + System.currentTimeMillis() + ".txt");

		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			fileOutputStream.write(buffer, 0, bytesRead);
		}

		fileOutputStream.close();
		inputStream.close();
		socket.close();
		System.out.println("Server Da nhan File");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
