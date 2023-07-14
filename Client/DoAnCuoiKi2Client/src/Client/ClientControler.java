package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientControler implements ActionListener{

	private ClientGiaoDien clientGiaoDien;
	public ClientControler(ClientGiaoDien clientGiaoDien) {

		this.clientGiaoDien = clientGiaoDien;

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String suKien = e.getActionCommand();
		if (suKien.equals("Dang nhap")) {
			

		} else if (suKien.equals("CHƯA CÓ TÀI KHOẢN")) {
			
			clientGiaoDien.dangKi();
		}else if (suKien.equals("QUAY VỀ TRANG ĐĂNG NHẬP")) {
			
			clientGiaoDien.dangNhap();
		}else if (suKien.equals("ĐĂNG NHẬP")) {
			
			clientGiaoDien.xuLyDangNhap();
		}else if (suKien.equals("ĐĂNG KÍ")) {
			
			clientGiaoDien.xuLyDangKi();
		}
		
		
		
		
	}

}
