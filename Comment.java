package Board_Commet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import BOARD_CONNECT.Connect;

public class Comment{
	
	int comment_count;
	
	public void CommentRead(int board_number) {
		Connection c = Connect.getConnection();
		Scanner sc = new Scanner(System.in);
		
		try {
			String sql = ""
					+ " SELECT boardproject.comment.context , boardproject.comment.nickname , boardproject.comment.date "
					+ " FROM boardproject.comment "
					+ " WHERE boardproject.comment.board_number = ? ";
			
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, board_number);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				System.out.println("댓글 내용: \n| " + rs.getString("context") + " |");
				System.out.println("|작성자 : " + rs.getString("nickname") + " |");
				System.out.println("|작성시간 : " + rs.getDate("date") + " |\n");
				
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
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
			pstmt.setString(2, mynickname);
			pstmt.setInt(3, board_number);
			System.out.println("--댓글 작성 완료--");
			
			
			int rows = pstmt.executeUpdate();
			if(rows == 0) {
				System.out.println("--댓글 작성실패--");
			}else {
				System.out.println("--댓글 " + rows + "개 작성 성공--");				
			}
			pstmt.close();
			
			String sql2 = ""
					+ " UPDATE boardproject.board " 
					+ " SET boardproject.board.comment_count = ? "
					+ " WHERE boardproject.board.seq = " + board_number;
			
			PreparedStatement pstmt2 = c.prepareStatement(sql2);
			pstmt2.setInt(1, comment_count += 1);
			pstmt2.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}

	}

}
