package boardproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
	
	private static Connection conn;
	private static Connect instance = new Connect();

	
	private Connect() {
		try {
			//JDBC Driver 등록
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//연결하기
			conn = DriverManager.getConnection(
				"jdbc:mysql://192.168.200.97:3306/boardproject", 
				"newuser", 
				"1234"
			);	
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	 public static Connection getConnection() {
	        return conn;
	    }
	
	


}