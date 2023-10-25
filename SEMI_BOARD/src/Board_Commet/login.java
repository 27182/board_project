package Board_Commet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.TimeUnit;

import BOARD_CONNECT.Connect;

public class login {

	public static boolean loginn = false;
	public static String nm;

	void log() throws Exception {

		Connection conn = Connect.getConnection();

		try {

			Scanner s = new Scanner(System.in);

			while (true) {
				System.out.println("아이디 입력");
				String i = s.nextLine();
				System.out.println("비밀번호 입력");
				String p = s.nextLine();
				String sql = " SELECT  SEQ " + "        ,ID" + "        ,PW" + "        ,NICKNAME" + "  FROM member "
						+ " where id = ? ";
				PreparedStatement pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, i);
				ResultSet rs = pstmt.executeQuery();
				if (!rs.next()) {
					System.out.println();
					System.out.println("존재하지 않는 아이디입니다. 다시 입력해주세요.");
					System.out.println();
					continue;
				}
				if (p.equals(rs.getString(3))) {
					nm = rs.getString(4);
					loginn = true;
					System.out.println("로그인성공");
					Thread.sleep(1000);
					break;
				} else {
					System.out.println("로그인 실패: 아이디 또는 비밀번호를 다시 입력해주세요");
					System.out.println();
				}
			}

		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
