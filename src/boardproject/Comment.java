package Board_Commet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import BOARD_CONNECT.Connect;

public class Comment{
	

	
	public void CommentRead(int board_number) {
		Connection c = Connect.getConnection();
		Scanner sc = new Scanner(System.in);
		
		try {
			String sql = ""
					+ " SELECT boardproject.comment.context , boardproject.comment.nickname , boardproject.comment.date "
					+ " FROM boardproject.comment , boardproject.board "
					+ " WHERE boardproject.comment.board_number = boardproject.board.seq ";
			
			PreparedStatement pstmt = c.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				System.out.println(rs.getString("context"));
				System.out.println(rs.getString("nickname"));
				System.out.println(rs.getDate("date"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.close();
					System.out.println("연결 끊기");
				} catch (SQLException e) {
				}
			}
		}
	}
	
	
	public void CommentWrite(int board_number){
		Connection c = Connect.getConnection();
		Scanner sc = new Scanner(System.in);

		try {
			
			String sql = "" 
			+ " INSERT INTO boardproject.comment( context ,nickname ,board_number ) "
			+ " VALUES( ? , ? , ? ) ";

			PreparedStatement pstmt = c.prepareStatement(sql);
			
			System.out.println("댓글 입력: ");
			pstmt.setString(1, sc.nextLine());
			System.out.println("작성자 입력: ");
			pstmt.setString(2, sc.nextLine());
			System.out.println("댓글 작성 완료");
			pstmt.setInt(3, board_number);
			
			
			int rows = pstmt.executeUpdate();
			if(rows == 0) {
				System.out.println("DB저장 실패");
			}else {
				System.out.println("DB저장 성공 " + rows);				
			}
			
			pstmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					// 연결 끊기
					c.close();
					System.out.println("연결 끊기");
				} catch (SQLException e) {
				}
			}
		}

	}

}
