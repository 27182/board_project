package boardproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class test {
	public static void main(String[] args) {
		Connection c = Connect.getConnection();
		
		
		
		
		try {
		
		board b = new board(true, "admin");
		b.showboard();

			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
