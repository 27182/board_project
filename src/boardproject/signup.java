package boardproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class signup {

   
   String singUp() {
      
      Connection conn = Connect.getConnection();
      Scanner s = new Scanner(System.in);

      
      
      
      try {
         conn.setAutoCommit(false);
         String sql = "INSERT INTO  member (" 
               + ""
               + "    id" 
               + "    ,pw" 
               + "    ,nickname" 
               + ") VALUES (" 
               + "    ?"
               + "    ,?"
               + "    ,?"
               + ")";

         PreparedStatement pstmt = conn.prepareStatement(sql);
         System.out.println("***회원가입***");

         System.out.println("아이디를 입력하세요");
         String id = s.nextLine();
         pstmt.setString(1, id);

         System.out.println("비밀번호를 입력하세요");
         String pw = s.nextLine();
         pstmt.setString(2, pw);

         System.out.println("닉네임을 입력하세요");
         String nickname = s.nextLine();
         pstmt.setString(3, nickname);

         int count = pstmt.executeUpdate();
         if (count == 0) {
            System.out.println("저장 실패");
         } else {
            if (1 < count) {
               conn.rollback();
               System.out.println("회원정보 일부저장 : " + count);
            } else {
               conn.commit();
               System.out.println("저장 성공 : " + count);
               return nickname;
            } 
         } return "";
         
   
         
      } catch (SQLException e) {
      
         e.printStackTrace();
         return "";
      }
   }
}