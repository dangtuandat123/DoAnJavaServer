package Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class ServerThread implements Runnable {

	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private String listUser;
	private HashMap<String, Socket> threadMap;
	private String userName;
	private String passWord;
	private String user;
	private String pass;
	private String trangThai;
	private String tinnhan;
	private String userGui;
	private String userNhan;
	private ServerGiaoDien serverGiaoDien;
	private Thread threadTruyenNguoiDung;
	private String timeGuiTin;
	private String idTinNhan;
	private String duongDan;
	private String tenFile;
	private Thread threadTruyenFile;
	private String timeGuiFile;

	public ServerThread(Socket socket, HashMap<String, Socket> threadMap, ServerGiaoDien serverGiaoDien) {

		this.socket = socket;
		this.threadMap = threadMap;
		this.serverGiaoDien = serverGiaoDien;

	}

	public String hienThiDanhSachNguoiDung() {

		int i = 1;
		listUser = "";

		for (Entry<String, Socket> entry : threadMap.entrySet()) {
			String key = entry.getKey();

			listUser = listUser + i + ": " + key + "\n";
			i++;
		}

		serverGiaoDien.soNguoiDungOnline(threadMap.size());
		System.out.println("Co " + threadMap.size() + " dang online.\n" + listUser);
		return ("Co " + threadMap.size() + " dang online.\n" + listUser);

	}

	public void truyenNguoiDung() {

		for (Entry<String, Socket> entry : threadMap.entrySet()) {

			String key = entry.getKey();

			// them nguoi dung
			String nguoidung = "themNguoiDungVaoList " + key;
			serverGiaoDien.addListNguoiDungOnline(key);
			guiTinNhanChoTatCaNguoiDung(nguoidung);

		}
	}

	public void guiTinNhanChoTatCaNguoiDung(String tinNhan) {

		for (Entry<String, Socket> entry : threadMap.entrySet()) {

			Socket targetThread = threadMap.get(entry.getKey());

			try {

				System.out.println(targetThread.getRemoteSocketAddress());
				DataOutputStream out1 = new DataOutputStream(targetThread.getOutputStream());
				out1.writeUTF(tinNhan);

			} catch (IOException e) {

				e.printStackTrace();

			}
		}

	}

	public void guiTinNhanChoNguoiDung(String tinNhan, String user) {

		Socket targetThread = threadMap.get(user);

		try {

			System.out.println(targetThread.getRemoteSocketAddress());
			DataOutputStream out1 = new DataOutputStream(targetThread.getOutputStream());
			out1.writeUTF(userName + ": " + tinNhan);

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	public void xoaNguoiDungRaKhoiHash() {

		threadMap.remove(userName);

		for (Entry<String, Socket> entry : threadMap.entrySet()) {

			String key = entry.getKey();
			// them nguoi dung
			String nguoidung = "xoaNguoiDungKhoiList " + userName;
			guiTinNhanChoTatCaNguoiDung(nguoidung);

		}
	}

	public void truyenNguoiDungDaKetNoi() {

		try {

			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();

			String sql = "SELECT DISTINCT  T_UserName1,T_UserName2 FROM `ta_mnr_tinnhan` WHERE T_UserName1 = '"
					+ userName + "' or T_UserName2 = '" + userName + "';";
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {

				String user2 = rs.getString("T_UserName2");
				String user1 = rs.getString("T_UserName1");

				try {

					out.writeUTF("themNguoiDungcu List " + user1);
					out.writeUTF("themNguoiDungcu List " + user2);

				} catch (IOException e) {
					e.printStackTrace();

				}
			}

			JDBCUtil.closeConnection(con);

		} catch (SQLException e) {

			e.printStackTrace();

		}
	}

	public void xuLyDangNhap() {

		try {

			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();

			String sql = "SELECT * from ta_mnr_user WHERE  T_UserName=" + '"' + userName + '"' + " AND T_PassWord="
					+ '"' + passWord + '"' + ";";
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {

				user = rs.getString("T_UserName");
				pass = rs.getString("T_PassWord");

			}
			try {

				if (user.equals(userName) && pass.equals(passWord)) {
					out.writeUTF("thanhcong");
				}

			} catch (Exception e) {

				try {
					out.writeUTF("khongthanhcong");
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}

			JDBCUtil.closeConnection(con);

		} catch (SQLException e) {

			e.printStackTrace();

		}

	}

	public void xuLyDangKi() {

		try {

			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();

			String sql = "INSERT INTO ta_mnr_user (`T_UserName`, `T_PassWord`) VALUES (" + "'" + userName + "'" + ","
					+ "'" + passWord + "'" + ");";

			System.out.println(sql);
			int rs = st.executeUpdate(sql);

			// dang ki thanh cong
			try {
				out.writeUTF("thanhcong");

			} catch (IOException e) {

				e.printStackTrace();

			}

			JDBCUtil.closeConnection(con);

		} catch (SQLException e) {
			// ten nguoi dung ton tai

			try {
				out.writeUTF("khongthanhcong");

			} catch (IOException e1) {

				e1.printStackTrace();

			}

			e.printStackTrace();

		}

	}

	public void xuLyTinNhan(String tinNhan, String userName1, String userName2) {

		try {

			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();

			String sql = "INSERT INTO ta_mnr_tinnhan (`T_TinNhan`, `T_UserName1`, `T_UserName2`) VALUES (" + "'"
					+ tinNhan + "'" + "," + "'" + userName1 + "'" + "," + "'" + userName2 + "'" + ");";
			System.out.println(sql);
			int rs = st.executeUpdate(sql);

			JDBCUtil.closeConnection(con);

		} catch (SQLException e) {

			e.printStackTrace();

		}

	}

	public void nhanFile(String user, String Time, String tenFile) {

		System.out.println(user + Time + tenFile);
		Time = Time.replace("-", " ");
		Time = Time.replace(".", " ");
		Time = Time.replace(":", " ");
		System.out.println(Time);
		String thuMuc = "File//" + user + "//" + Time;
		File directory = new File(thuMuc);

		boolean created = directory.mkdirs();
		if (created) {
			System.out.println("Thư mục đã được tạo thành công.");
		} else {
			System.out.println("Không thể tạo thư mục.");
			System.out.println(Time);

		}

		try {

			// Đọc thông tin về kích thước của file
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			long fileSize = dis.readLong();
			System.out.println(fileSize);

			// Nhận nội dung của file
			FileOutputStream fos = new FileOutputStream("File\\" + user + "\\" + Time + "\\" + tenFile);
			byte[] buffer = new byte[8192];
			int bytesRead;
			while (fileSize > 0 && (bytesRead = dis.read(buffer, 0, (int) Math.min(buffer.length, fileSize))) != -1) {
				fos.write(buffer, 0, bytesRead);
				fileSize -= bytesRead;
				System.out.println(bytesRead);
			}
			fos.flush();

			// Đóng kết nối

			System.out.println("File received successfully.");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void guiFile(String duongDan) {

		try {

			File file = new File(duongDan);

			long fileSize = file.length();
			System.out.println(fileSize);
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			dos.writeLong(fileSize);

			// Gửi nội dung của file
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			byte[] buffer = new byte[8192];
			int bytesRead;
			while ((bytesRead = bis.read(buffer)) != -1) {
				dos.write(buffer, 0, bytesRead);
				System.out.println(bytesRead);
			}
			dos.flush();
			fis.close();

			// Đóng kết nối

			System.out.println("File sent successfully.");
		} catch (Exception e) {

		}

	}

	public void xuLyTinNhanGuiFile(String duongDan, String userName1, String userName2) {

		try {

			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();

			String sql = "INSERT INTO ta_mnr_tinnhan (`T_File`, `T_UserName1`, `T_UserName2`) VALUES (" + "'" + duongDan
					+ "'" + "," + "'" + userName1 + "'" + "," + "'" + userName2 + "'" + ");";
			System.out.println(sql);
			int rs = st.executeUpdate(sql);

			JDBCUtil.closeConnection(con);

		} catch (SQLException e) {

			e.printStackTrace();

		}

	}

	public String getTimeGuiFile(String userGui, String userNhan, String timeGuiTin) {
		// lay id tin nhan
		try {

			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();

			String sql = "SELECT I_Id FROM ta_mnr_tinnhan WHERE D_ThoiGian = '" + timeGuiTin + "';";
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {

				idTinNhan = rs.getString("I_Id");

			}

			JDBCUtil.closeConnection(con);

		} catch (SQLException e) {

			e.printStackTrace();

		}

		try {

			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();

			String sql = "SELECT * FROM ta_mnr_tinnhan WHERE I_id > " + idTinNhan + " AND( T_UserName1 ='" + userGui
					+ "' AND T_UserName2 = '" + userNhan + "'OR T_UserName1 = '" + userNhan + "' AND T_UserName2 = '"
					+ userGui + "');";
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {

				timeGuiFile = rs.getString("D_ThoiGian");

			}

			JDBCUtil.closeConnection(con);

		} catch (SQLException e) {

			e.printStackTrace();

		}
		return timeGuiFile;

	}

	public void truyenTinNhanTuCsdlVeClient(String userGui, String userNhan) {

		if (threadMap.containsKey(userNhan)) {
			try {

				Connection con = JDBCUtil.getConnection();
				Statement st = con.createStatement();

				String sql = "SELECT * FROM ta_mnr_tinnhan WHERE T_UserName1 ='" + userGui + "' AND T_UserName2 = '"
						+ userNhan + "'OR T_UserName1 = '" + userNhan + "' AND T_UserName2 = '" + userGui + "';";
				System.out.println(sql);
				ResultSet rs = st.executeQuery(sql);

				try {

					Socket targetThread = threadMap.get(userNhan);
					System.out.println(targetThread.getRemoteSocketAddress());
					DataOutputStream out1 = new DataOutputStream(targetThread.getOutputStream());
				//	out1.writeUTF("resetMess00000000000");

				} catch (IOException e1) {

					e1.printStackTrace();

				}
				while (rs.next()) {

					String time = rs.getString("D_ThoiGian");
					String tinNhan = rs.getString("T_TinNhan");
					String duongDanFile = rs.getString("T_File");
					String UserName1 = rs.getString("T_UserName1");
					String UserName2 = rs.getString("T_UserName2");

					try {

						out.writeUTF("Time[" + time + "]" + " TinNhan[" + tinNhan + "]" + " user1[" + UserName1 + "]"
								+ " user2[" + UserName2 + "]" + " duongDanFile[" + duongDanFile + "]");

						try {

							Socket targetThread = threadMap.get(userNhan);
							System.out.println(targetThread.getRemoteSocketAddress());
							DataOutputStream out1 = new DataOutputStream(targetThread.getOutputStream());
//							out1.writeUTF("Time[" + time + "]" + " TinNhan[" + tinNhan + "]" + " user1[" + UserName1
//									+ "]" + " user2[" + UserName2 + "]" + " duongDanFile[" + duongDanFile + "]");

						} catch (IOException e) {

							e.printStackTrace();
						}

					} catch (Exception e) {

					}

				}

				JDBCUtil.closeConnection(con);

			} catch (SQLException e) {

				e.printStackTrace();

			}
		} else {

			try {

				Connection con = JDBCUtil.getConnection();

				Statement st = con.createStatement();

				String sql = "SELECT * FROM ta_mnr_tinnhan WHERE T_UserName1 ='" + userGui + "' AND T_UserName2 = '"
						+ userNhan + "'OR T_UserName1 = '" + userNhan + "' AND T_UserName2 = '" + userGui + "';";

				System.out.println(sql);
				ResultSet rs = st.executeQuery(sql);

				while (rs.next()) {

					String time = rs.getString("D_ThoiGian");
					String tinNhan = rs.getString("T_TinNhan");
					String duongDanFile = rs.getString("T_File");
					String UserName1 = rs.getString("T_UserName1");
					String UserName2 = rs.getString("T_UserName2");

					try {

						out.writeUTF("Time[" + time + "]" + " TinNhan[" + tinNhan + "]" + " user1[" + UserName1 + "]"
								+ " user2[" + UserName2 + "]" + " duongDanFile[" + duongDanFile + "]");

					} catch (Exception e) {

					}

				}

				JDBCUtil.closeConnection(con);
			} catch (SQLException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}

	}

	public void truyenTinNhanTheoIdTinNhan(String userGui, String userNhan, String timeGuiTin) {
		// lay id tin nhan
		try {

			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();

			String sql = "SELECT I_Id FROM ta_mnr_tinnhan WHERE D_ThoiGian = '" + timeGuiTin + "';";
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {

				idTinNhan = rs.getString("I_Id");

			}

			JDBCUtil.closeConnection(con);

		} catch (SQLException e) {

			e.printStackTrace();

		}

		if (threadMap.containsKey(userNhan)) {
			try {

				Connection con = JDBCUtil.getConnection();
				Statement st = con.createStatement();

				String sql = "SELECT * FROM ta_mnr_tinnhan WHERE I_id > " + idTinNhan + " AND( T_UserName1 ='" + userGui
						+ "' AND T_UserName2 = '" + userNhan + "'OR T_UserName1 = '" + userNhan
						+ "' AND T_UserName2 = '" + userGui + "');";
				System.out.println(sql);
				ResultSet rs = st.executeQuery(sql);

				while (rs.next()) {

					String time = rs.getString("D_ThoiGian");
					String tinNhan = rs.getString("T_TinNhan");
					String duongDanFile = rs.getString("T_File");
					String UserName1 = rs.getString("T_UserName1");
					String UserName2 = rs.getString("T_UserName2");

					try {

						out.writeUTF("Time[" + time + "]" + " TinNhan[" + tinNhan + "]" + " user1[" + UserName1 + "]"
								+ " user2[" + UserName2 + "]" + " duongDanFile[" + duongDanFile + "]");

						try {

							Socket targetThread = threadMap.get(userNhan);
							System.out.println(targetThread.getRemoteSocketAddress());
							DataOutputStream out1 = new DataOutputStream(targetThread.getOutputStream());
							out1.writeUTF("Time[" + time + "]" + " TinNhan[" + tinNhan + "]" + " user1[" + UserName1
									+ "]" + " user2[" + UserName2 + "]" + " duongDanFile[" + duongDanFile + "]");

						} catch (IOException e) {

							e.printStackTrace();
						}

					} catch (Exception e) {

					}

				}

				JDBCUtil.closeConnection(con);

			} catch (SQLException e) {

				e.printStackTrace();

			}
		} else {

			try {

				Connection con = JDBCUtil.getConnection();

				Statement st = con.createStatement();

				String sql = "SELECT * FROM ta_mnr_tinnhan WHERE I_id > " + idTinNhan + " AND( T_UserName1 ='" + userGui
						+ "' AND T_UserName2 = '" + userNhan + "'OR T_UserName1 = '" + userNhan
						+ "' AND T_UserName2 = '" + userGui + "');";

				System.out.println(sql);
				ResultSet rs = st.executeQuery(sql);

				while (rs.next()) {

					String time = rs.getString("D_ThoiGian");
					String tinNhan = rs.getString("T_TinNhan");
					String duongDanFile = rs.getString("T_File");
					String UserName1 = rs.getString("T_UserName1");
					String UserName2 = rs.getString("T_UserName2");

					try {

						out.writeUTF("Time[" + time + "]" + " TinNhan[" + tinNhan + "]" + " user1[" + UserName1 + "]"
								+ " user2[" + UserName2 + "]" + " duongDanFile[" + duongDanFile + "]");

					} catch (Exception e) {

					}

				}

				JDBCUtil.closeConnection(con);
			} catch (SQLException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}

	}

	public void timNguoiDung(String user) {

		int sokiTu = user.length();

		try {

			Connection con = JDBCUtil.getConnection();
			Statement st = con.createStatement();

			String sql = "SELECT T_UserName FROM ta_mnr_user WHERE LEFT(T_UserName, " + sokiTu + ") = '" + user + "'";
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);

			try {

				out.writeUTF("removeListTimNguoiDu");

			} catch (Exception e) {

			}

			while (rs.next()) {

				String tenUserCanTim = rs.getString("T_UserName");

				try {

					out.writeUTF("guiVeNguoiDungCanTim " + tenUserCanTim);
					System.out.println(tenUserCanTim);

				} catch (Exception e) {

				}

			}

			JDBCUtil.closeConnection(con);

		} catch (SQLException e) {

			e.printStackTrace();

		}

	}

	@Override
	public void run() {
		try {

			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			// nhan ve trang thai(dangnhap, dangki, guiTinNhan)
			trangThai = "";

			while (!trangThai.equals("thoat")) {

				trangThai = in.readUTF();
				System.out.println(trangThai);

				synchronized (in) {

					if (trangThai.equals("dangNhap")) {

						try {

							userName = in.readUTF();
							passWord = in.readUTF();
							System.out.println("xu ly dang nhap");
							System.out.println(userName + passWord);
							xuLyDangNhap();

						} catch (IOException e) {
							e.printStackTrace();
						}

					} else if (trangThai.equals("dangKi")) {

						try {
							userName = in.readUTF();
							passWord = in.readUTF();
							System.out.println("xu ly dang ki");
							System.out.println(userName + passWord);
							xuLyDangKi();

						} catch (IOException e) {
							e.printStackTrace();
						}

					} else if (trangThai.equals("nhanThongTin")) {

						userName = in.readUTF();
						System.out.println(userName);
						threadMap.put(userName, socket);

						// truyen danh sach nguoi dung toi client
						hienThiDanhSachNguoiDung();
						threadTruyenNguoiDung = new Thread(new Runnable() {

							@Override
							public void run() {
								while (true) {
									try {
										Thread.sleep(100);
									} catch (InterruptedException e) {

										e.printStackTrace();

									}
									truyenNguoiDungDaKetNoi();

								}

							}

						});
						threadTruyenNguoiDung.start();

						truyenNguoiDung();

					} else if (trangThai.equals("guiTinNhan")) {

						synchronized (in) {
							tinnhan = in.readUTF();
							timeGuiTin = in.readUTF();
							userGui = in.readUTF();
							userNhan = in.readUTF();
						}

						xuLyTinNhan(tinnhan, userGui, userNhan);
						truyenNguoiDungDaKetNoi();
						truyenTinNhanTheoIdTinNhan(userGui, userNhan, timeGuiTin);
					} else if (trangThai.equals("guiFile")) {

						synchronized (in) {
							tenFile = in.readUTF();
							timeGuiTin = in.readUTF();
							userGui = in.readUTF();
							userNhan = in.readUTF();
						}

						truyenNguoiDungDaKetNoi();

						String duongDan = "File\\\\" + user + "\\\\" + timeGuiTin + "\\\\" + tenFile;

						xuLyTinNhanGuiFile(duongDan, userGui, userNhan);

						nhanFile(userGui, getTimeGuiFile(userGui, userNhan, timeGuiTin), tenFile);

						truyenTinNhanTheoIdTinNhan(userGui, userNhan, timeGuiTin);

					} else if (trangThai.equals("nhanFile")) {
						synchronized (ServerThread.class) {
							synchronized (in) {
								duongDan = in.readUTF();
							}
							out.writeUTF("nhanFile000000000000");
							guiFile(duongDan);

						}

					} else if (trangThai.equals("layTinNhanTuCSDL")) {

						userGui = in.readUTF();
						userNhan = in.readUTF();
						out.writeUTF("resetMess00000000000");
						truyenTinNhanTuCsdlVeClient(userGui, userNhan);

					} else if (trangThai.equals("timNguoiDung")) {

						userGui = in.readUTF();
						timNguoiDung(userGui);

					}

				}
			}

		} catch (IOException e) {

			xoaNguoiDungRaKhoiHash();
			serverGiaoDien.removeList(userName);
			hienThiDanhSachNguoiDung();
			threadTruyenNguoiDung.stop();

		}

	}

}
