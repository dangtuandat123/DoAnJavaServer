package Client;
import java.io.*;
import java.net.*;

public class Client {

    public void ClientStart(String host, int port) throws IOException {
        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));

        String input = "";
        while (!input.equals("exit")) {
            System.out.print("Nhap duong dan File hoac Nhap exit de thoat: ");
            input = buff.readLine();

            if (!input.equals("exit")) {
                try {
                    File file = new File(input);
                    Socket socket = new Socket(host, port);
                    OutputStream outputStream = socket.getOutputStream();
                    FileInputStream fileInputStream = new FileInputStream(file);
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    fileInputStream.close();
                    outputStream.close();
                    socket.close();
                    System.out.println("File da duoc gui di.");
                } catch (Exception e) {
                    System.out.println("Ban da nhap sai.");
                }
            }
        }
    }
	public static void main(String[] args) {
		Client client = new Client();
		try {
			client.ClientStart("localhost", 3333);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

