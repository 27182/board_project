package boardproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class login {

   public static boolean loginn = false;
   public static String nm  ;
   
   void log() {

      Connection conn = Connect.getConnection();

      try {
         
         
         Scanner s = new Scanner(System.in);

               String sql = " SELECT  SEQ " + "        ,ID" + "        ,PW" + "        ,NICKNAME"
                     + "  FROM member " + " where id = ? ";
               PreparedStatement pstmt = conn.prepareStatement(sql);
               System.out.println("아이디 입력");
               String i = s.nextLine();
               System.out.println("비밀번호 입력");
               String p = s.nextLine();

               pstmt.setString(1, i);
               ResultSet rs = pstmt.executeQuery();
               while (rs.next()) { 

                  if (p.equals(rs.getString(3))) {
                     nm=rs.getString(4);
                      loginn = true;
                     System.out.println("로그인성공");
                  } else {
                     System.out.println("아이디 또는 비밀번호를 다시 확인해주세요");
                  }
               }

      }

      catch (SQLException e) {
         e.printStackTrace();
      }
         }
      
   }