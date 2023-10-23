package boardproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class tetsettetet {
    public static void main(String[] args) throws Exception {
        
        
        // String sql = "insert into picture (picture) values (?)";
        String sql = "select * from picture";
        Connection c = Connect.getConnection();

        PreparedStatement pstmt = c.prepareStatement(sql);

       
        ResultSet rs =  pstmt.executeQuery();
        
        while(rs.next()){
            System.out.println(rs.getString("picture"));
        }
        
        



    }
}
