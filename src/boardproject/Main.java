package boardproject;


import java.sql.Connection;
import java.util.Scanner;

public class Main {

   public static void main(String[] args) {
      
       Connection c = Connect.getConnection();
           Scanner sc = new Scanner(System.in);
           try {
               System.out.println();
               System.out.println("게시판 프로그램입니다.");
               String a = "";
               int b = 99;
               while(b < 1 || b > 4){
                   System.out.println();
                   System.out.println("( 1: 로그인 │ 2: 회원가입 │ 3: 비로그인으로 보기 │ 4: 종료 )");
                   a = sc.nextLine();
                   try {
                       b = Integer.parseInt(a);
                   } catch(Exception e) {
                       System.out.println("다시 입력해주세요");
                       continue;
                   }
               }
               switch(b) {
                   // 로그인 추가
                   case 1: 
                      {login n = new login();
                      n.log();
                   board bd1 = new board(login.loginn , login.nm);
                   while(!board.programEnd){
                       bd1.showboard();
                   }
                   break;}
                   // 회원가입 추가
                   case 2:{
                      signup g = new signup();
                      String in = g.singUp();
                      board bd2 = new board(true , in);
                       while(!board.programEnd){
                           bd2.showboard();
                       }
                      break; 
                   }

                   case 3: {
                       board bd = new board();
                       while(!board.programEnd){
                       bd.showboard();
                       }
                   }

                   case 4: return;
               }


           } catch (Exception e) {
               e.printStackTrace();
           }
       }

   }