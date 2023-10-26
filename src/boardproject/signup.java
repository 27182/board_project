package boardproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class signup {

   String singUp() throws Exception {

      Connection conn = Connect.getConnection();
      Scanner s = new Scanner(System.in);

      while (true) {

         try {

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
            String nickname = " ";
            nickname += s.nextLine();
            pstmt.setString(3, nickname);

            int count = pstmt.executeUpdate();
            if (count == 0) {
               System.out.println("가입 실패");
               System.out.println("다른 아이디와 닉네임을 선택해주세요.");
               System.out.println();
            } else {
               if (1 < count) {
                  System.out.println("오류");
               } else {
                  System.out.println("회원가입 성공");
                  System.err.println("가입된 정보로 로그인합니다.");
                  Thread.sleep(1000);
                  return nickname;
               }
            }

         } catch (SQLException e) {
            System.out.println();
            System.out.println("가입 실패");
            System.out.println("다른 아이디와 닉네임을 선택해주세요.");
            System.out.println();
            Thread.sleep(1000);

         }
      }
   }
}