package boardproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class test {
	public static void main(String[] args) {
		Connection c = Connect.getConnection();
		
		
		
		
		try {
		
		String sql = "" +
				"SELECT * " +
				" FROM member;";
			
			//PreparedStatement 얻기 및 값 지정
			PreparedStatement pstmt = c.prepareStatement(sql);
			
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getString("id"));
			}

			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
