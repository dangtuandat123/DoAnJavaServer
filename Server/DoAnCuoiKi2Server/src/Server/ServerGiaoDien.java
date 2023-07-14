package Server;

import java.awt.BorderLayout;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ServerGiaoDien {

	private DefaultListModel<String> listModel;
	private JList<String> jList_danhSachNguoiDung;
	private JPanel jPanel_hienThiDsNguoiDung;
	private JPanel jPanel_main;
	private JLabel jLabel_danhSachNguoiDung;

	public void khoiDong() {
		
		JFrame jFrame_main = new JFrame();
		jFrame_main.setSize(600, 500);
		jFrame_main.setLocationRelativeTo(null);
		jFrame_main.setTitle("Server");
		jFrame_main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame_main.setVisible(true);
		Color mau1 = new Color(0, 151, 178);


		jPanel_main = new JPanel();
		jPanel_main.setBackground(mau1);
		jPanel_main.setLayout(new BorderLayout());
		jFrame_main.add(jPanel_main);

		jPanel_hienThiDsNguoiDung = new JPanel();
		jPanel_hienThiDsNguoiDung.setBackground(mau1);
		jPanel_hienThiDsNguoiDung.setLayout(new BorderLayout());
		jPanel_main.add(jPanel_hienThiDsNguoiDung, BorderLayout.CENTER);
		
		JPanel jPanel_onOff = new JPanel();
		jPanel_main.add(jPanel_onOff, BorderLayout.NORTH);
		jPanel_onOff.setBackground(mau1);
		jPanel_onOff.setPreferredSize(new Dimension(600, 100));

		JButton jButton_onOff = new JButton("Tắt Server");
		jButton_onOff.setFont(new Font("Arial", 20, 20));
		jButton_onOff.setBackground(Color.WHITE);
		jPanel_onOff.add(jButton_onOff);
		
		jButton_onOff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});
		
		hienThiDanhSachNguoiDung();
		jFrame_main.setVisible(false);
		jFrame_main.setVisible(true);
		
	}

	public void hienThiDanhSachNguoiDung() {
		
		jLabel_danhSachNguoiDung = new JLabel("USER ONLINE: 0");
		Font font = new Font("Arial", Font.BOLD, 20);
		jLabel_danhSachNguoiDung.setFont(font);

		Color mau2 = new Color(88, 255, 155);
		listModel = new DefaultListModel<>();

		jList_danhSachNguoiDung = new JList<>(listModel);
		JScrollPane jc = new JScrollPane(jList_danhSachNguoiDung);
		jList_danhSachNguoiDung.setFont(font);
		JPanel jPanel_lable = new JPanel();
		jPanel_lable.add(jLabel_danhSachNguoiDung);
		jPanel_lable.setBackground(mau2);

		jPanel_hienThiDsNguoiDung.add(jPanel_lable, BorderLayout.NORTH);
		jPanel_hienThiDsNguoiDung.add(jc, BorderLayout.CENTER);

	}

	public void removeList(String user) {
		listModel.removeElement(user);
		
	}

	public void soNguoiDungOnline(int soNguoiOn) {
		jLabel_danhSachNguoiDung.setText("USER ONLINE: " + soNguoiOn);
		
	}

	public void addListNguoiDungOnline(String user) {

		if (!listModel.contains(user)) {
			listModel.addElement(user);
			
		} else {
			System.err.println("người dùng đã tồn tại" + user);
			
		}

	}

}
