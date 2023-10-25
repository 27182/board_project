package boardproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;
//s

public class board {
    Connection conn = Connect.getConnection();
    Scanner sc = new Scanner(System.in);
    ArrayList<Integer> writingnums = new ArrayList<>();
    static boolean isLogin = false;
    static String mynickname = null;
    public static boolean programEnd = false;
    boolean sct = false;


    board() {

    }

    board(boolean isLogin, String mynickname) {
        board.isLogin = isLogin;
        board.mynickname = mynickname;
    }

    public void showboard() {
        System.out.println();
        System.out.println("──────────────────────────────── 게시글 목록 ────────────────────────────────");
        System.out.println("no.│                  title                  │ writer         │ date      ");
        try {
            String sql = "" +
                    "SELECT * " +
                    "FROM board " +
                    "ORDER BY seq DESC"
                    ;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int num;
            String title, writer;
            Date date;
            while (rs.next()) {
                if (rs.getInt("visible") == 0) {
                    continue;
                }
                System.out.println("─────────────────────────────────────────────────────────────────────────────");
                num = rs.getInt("seq");
                title = rs.getString("title");
                writer = rs.getString("nickname");
                date = rs.getTimestamp("date");
                writingnums.add(num);
                SimpleDateFormat sdf = new SimpleDateFormat(" yy-MM-dd HH:mm");
                String dateform = sdf.format(date);
                int a = 41;
                int count = 0;
                for (int i = 0; i < title.length(); i++) {
                    if (Character.getType(title.charAt(i)) == 5) {

                        a--;
                        count++;
                        if (count == 20) {
                            title = title.substring(0, 20) + "...  ";
                            break;
                        }
                    }
                }
                int b = 16;
                count = 0;
                for (int i = 0; i < writer.length(); i++) {
                    if (Character.getType(writer.charAt(i)) == 5) {
                        b--;
                        count++;
                        if (count == 6) {
                            writer = writer.substring(0, 6) + "...  ";
                            break;
                        }
                    }
                }

                System.out.printf("%-3s│%-" + a + "s│%-" + b + "s│%-10s\n", num, title, writer, dateform);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("─────────────────────────────────────────────────────────────────────────────");

        System.out.println("( 1: 게시글 보기 │ 2: 게시글 작성 │ 3: 종료 )");

        int tem = Integer.parseInt(sc.nextLine());
        switch (tem) {
            case 1: {
                int adc = selectnumber();
                sct = false;
                while (!sct) {
                    showcontext(adc);
                }
                break;
            }
            case 2: {
                try {
                    if (!isLogin) {
                        System.out.println("글을 작성하시려면 로그인해주세요.");
                        return;
                    }
                    System.out.println("글 작성을 시작합니다.");
                    System.out.println();
                    String title, context;
                    String imwriting = "";
                    System.out.println("제목을 입력해주세요");
                    title = " ";
                    title += sc.nextLine();
                    System.out.println();
                    System.out.println("내용을 입력해주세요. 입력을 완료하시려면 !stop 을 입력해주세요.");
                    StringBuilder sb = new StringBuilder();
                    while (!imwriting.equals("!stop")) {
                        imwriting = sc.nextLine();
                        if (imwriting.equals("!stop")) {
                            break;
                        }
                        sb.append(imwriting).append("\n");
                    }
                    context = sb.toString();

                    String sql = "insert into " + " board (title, context, nickname) " + " values (? , ? , ?)";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, title);
                    pstmt.setString(2, context);
                    pstmt.setString(3, mynickname);

                    pstmt.executeUpdate();
                    System.out.println();
                    System.out.println("글 작성 성공");
                    System.out.println();
                    tem = 999;
                    return;

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            case 3:
                programEnd = true;
                return;
        }

    }

    public int selectnumber() {
        System.out.println("보고 싶은 글 번호를 선택해주세요.");
        sc = new Scanner(System.in);
        String a = sc.nextLine();
        int b = 0;
        while (true) {
            try {
                b = Integer.parseInt(a);
                if (writingnums.contains(b)) {
                    return b;
                } else {
                    throw new Exception();
                }

            } catch (Exception e) {
                System.out.println("다시 입력해주세요.");
                a = sc.nextLine();
            }
        }
    }

    public void showcontext(int writingnum) {
        try {
            String sql = "" +
                    "SELECT * " +
                    "FROM board " +
                    " WHERE seq = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, writingnum);
            ResultSet rs = pstmt.executeQuery();
            String title;
            String context;
            String nickname;
            Date date;
            int recomm;
            int decomm;
            int board_number;
            int cmmt_count;
            while (rs.next()) {
                board_number = rs.getInt("seq");
                title = rs.getString("title");
                context = rs.getString("context");
                nickname = rs.getString("nickname");
                date = rs.getTimestamp("date");
                recomm = rs.getInt("recomm");
                decomm = rs.getInt("decomm");
                cmmt_count = rs.getInt("comment_count");
                System.out.println();
                System.out.println("─────────────────────────────────────────────────────────────────────────────");
                System.out.println("제목: " + title);
                System.out.println();
                System.out.println("작성자: " + nickname);
                System.out.println();
                System.out.println("작성일시: " + date);
                System.out.println();
                System.out.println(context);
                System.out.println();
                System.out.println("추천 수: " + recomm + " " + "비추천 수: " + decomm);
                System.out.println();
                System.out.println("댓글 수: " + cmmt_count);
                System.out.println("─────────────────────────────────────────────────────────────────────────────");

                if (nickname.equals(mynickname)) {
                    sc = new Scanner(System.in);
                    int stat = 999;
                    while (stat > 6 || stat < 0) {
                        System.out.println("( 1: 댓글 보기 │ 2: 댓글 작성 │ 3: 추천하기 │ 4: 비추천하기 │ 5: 글 삭제 │ 6: 돌아가기 )");
                        stat = Integer.parseInt(sc.nextLine());
                        switch (stat) {
                            // 댓글 보기 메소드
                            case 1: {
                                Comment cmmt = new Comment();
                                cmmt.CommentRead(board_number);
                                break;
                            }
                            // 댓글 작성 메소드
                            case 2: {
                                Comment cmmt = new Comment();
                                cmmt.CommentWrite(board_number);
                                break;
                            }

                            case 3: {
                                try {
                                    String sq = "UPDATE board "
                                            + " set recomm = ?"
                                            + " where seq = ?";

                                    PreparedStatement pstmt2 = conn.prepareStatement(sq);
                                    pstmt2.setInt(1, recomm + 1);
                                    pstmt2.setInt(2, writingnum);
                                    pstmt2.executeUpdate();

                                    System.out.println("추천되었습니다.");
                                    System.out.println();
                                    stat = 999;
                                    break;

                                } catch (Exception e) {
                                    break;
                                }
                            }

                            case 4: {
                                try {
                                    String sql2 = "UPDATE board "
                                            + " set decomm = ?"
                                            + " where seq = ?";

                                    PreparedStatement pstmt3 = conn.prepareStatement(sql2);
                                    pstmt3.setInt(1, decomm + 1);
                                    pstmt3.setInt(2, writingnum);
                                    pstmt3.executeUpdate();

                                    System.out.println("비추천되었습니다.");
                                    System.out.println();
                                    stat = 999;
                                    break;

                                } catch (Exception e) {
                                    e.getStackTrace();
                                    System.out.println("비추천 실패");
                                    break;
                                }
                            }

                            case 5: {
                                try {
                                    String sqq = "UPDATE board "
                                            + " set visible = 0"
                                            + " where seq = ?";

                                    PreparedStatement pstmt3 = conn.prepareStatement(sqq);
                                    pstmt3.setInt(1, writingnum);
                                    pstmt3.executeUpdate();
                                    System.out.println();
                                    System.out.println("글이 삭제되었습니다.");
                                    System.out.println();
                                    sct = true;
                                    return;

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    System.out.println("삭제 실패");
                                    break;
                                }
                            }

                            case 6:
                                sct = true;
                                return;

                            default: {
                                System.out.println("잘못된 입력입니다.");
                                continue;
                            }

                        }
                    }
                } else if (!isLogin) {
                    sc = new Scanner(System.in);
                    int stat = 999;
                    while (stat > 6 || stat < 0) {
                        System.out.println("( 1: 댓글 보기 │ 6: 돌아가기 )");
                        stat = Integer.parseInt(sc.nextLine());
                        switch (stat) {
                            // 댓글 보기 메소드 추후 추가
                            case 1: {
                                Comment cmmt = new Comment();
                                cmmt.CommentRead(board_number);
                                break;
                            }

                            case 6:
                                sct = true;
                                return;

                            default: {
                                System.out.println("잘못된 입력입니다.");
                                continue;
                            }

                        }
                    }
                } else {
                    int stat = 999;
                    sc = new Scanner(System.in);
                    while (stat > 6 || stat < 0) {
                        System.out.println("( 1: 댓글 보기 │ 2: 댓글 작성 │ 3: 추천하기 │ 4: 비추천하기 │ 6: 돌아가기 )");
                        stat = Integer.parseInt(sc.nextLine());
                        switch (stat) {
                            // 댓글 보기 메소드 추후 추가
                            case 1: {
                                Comment cmmt = new Comment();
                                cmmt.CommentRead(board_number);
                                break;
                            }
                            // 댓글 작성 메소드 추후 추가
                            case 2: {
                                Comment cmmt = new Comment();
                                cmmt.CommentWrite(board_number);
                                break;
                            }

                            case 3: {
                                try {
                                    String sq = "UPDATE board "
                                            + " set recomm = ?"
                                            + " where seq = ?";

                                    PreparedStatement pstmt2 = conn.prepareStatement(sq);
                                    pstmt2.setInt(1, recomm + 1);
                                    pstmt2.setInt(2, writingnum);
                                    pstmt2.executeUpdate();

                                    System.out.println("추천되었습니다.");
                                    System.out.println();
                                    stat = 999;
                                    break;

                                } catch (Exception e) {
                                    break;
                                }
                            }

                            case 4: {
                                try {
                                    String sql2 = "UPDATE board "
                                            + " set decomm = ?"
                                            + " where seq = ?";

                                    PreparedStatement pstmt3 = conn.prepareStatement(sql2);
                                    pstmt3.setInt(1, decomm + 1);
                                    pstmt3.setInt(2, writingnum);
                                    pstmt3.executeUpdate();

                                    System.out.println("비추천되었습니다.");
                                    System.out.println();
                                    stat = 999;
                                    break;

                                } catch (Exception e) {
                                    e.getStackTrace();
                                    System.out.println("비추천 실패");
                                    break;
                                }
                            }

                            case 6:
                                sct = true;
                                return;

                            default: {
                                System.out.println("잘못된 입력입니다.");
                                continue;
                            }

                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
