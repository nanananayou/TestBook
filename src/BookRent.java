import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import oracle.net.aso.m;

public class BookRent
{
	public Connection con;
	public Statement stmt;  
	public PreparedStatement psmt;  
	public ResultSet rs;
	
	public BookRent() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
            // 커넥션 URL, 계정 아이디와 패스워드 기술
            String url = "jdbc:oracle:thin:@localhost:1521:xe";  
            String id = "booktest";
            String pwd = "1234"; 
            // 오라클 DB 연결
            con = DriverManager.getConnection(url, id, pwd); 
            // 연결 성공시 콘솔에서 로그 확인
            System.out.println("DB 연결 성공");
        }
        catch (Exception e) {            
            e.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		BookRent book = new BookRent();
		book.librarian();
		book.addBook();
	}
	
	public void librarian() {
		System.out.println("===도서 관리 프로그램====");
		System.out.println("===메뉴를 선택하세요.====");
		System.out.println("===== 1.책 등록 ======");
		System.out.println("===== 2.책 폐기 ======");
		System.out.println("===== 3.책 조회 ======");
		System.out.println("======= 4.종료 =======");
		
	}
	public void addBook() {
		try {
			
			Scanner sc = new Scanner(System.in);
			
			
			System.err.println("책 제목을 입력하세요.");
			String BOOK_TITLE = sc.nextLine();
			System.out.println("책 수량을 입력해주세요.");
			int BOOK_CNT = sc.nextInt();
			
			String sql = "INSERT INTO BOOK_DB VALUES (seq_book_db.nextval, ?, ?)";
			PreparedStatement psmt1 = con.prepareStatement(sql);
			

		    psmt1.setString(1, BOOK_TITLE);
		    psmt1.setInt(2, BOOK_CNT);
		    
		    int inResult = psmt1.executeUpdate();
		    System.out.println(inResult + "행이 입력되었습니다.");
		}
		catch(Exception e) {
			e.printStackTrace();			
		}
	}
	

}
