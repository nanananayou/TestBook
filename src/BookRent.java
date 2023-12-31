import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
		book.listBook();
//		
	}
	
	public void showMenu() {
			System.out.println("===도서 관리 프로그램====");
			System.out.println("===메뉴를 선택하세요.====");
			System.out.println("===== 1.책 등록 ======");
			System.out.println("===== 2.책 폐기 ======");
			System.out.println("===== 3.책 조회 ======");
			System.out.println("===== 4.책 전체 조회 ====");
			System.out.println("=======5.종료 =======");
			System.out.println();
			System.out.println("어떤 메뉴를 선택하시겠습니까?");
		
	}
	
	public void listBook() {
		while(true) {
			showMenu();
			Scanner scan = new Scanner(System.in);
			int choice = scan.nextInt();
			switch(choice) {
			case 1:
				addBook();
				
			case 2:
				deleteBook();
				break;
			case 3:
				findBook();
				break;
			case 4:
				findAllBook();
				break;
			case 5:
				System.out.println("종료되었습니다.");
				return;
			default:
				System.out.println("잘못 입력하셨습니다.");
				break;	
			}
		}
	}
	public void addBook() {
		try {
			Scanner sc = new Scanner(System.in);
			System.out.print("책 제목을 입력하세요.");
			String BOOK_TITLE = sc.nextLine();
			System.out.print("책 수량을 입력해주세요.");
			int BOOK_CNT = sc.nextInt();
			
			String sql = "INSERT INTO BOOK_DB VALUES (seq_book_db.nextval, ?, ?)";
			PreparedStatement psmt1 = con.prepareStatement(sql);
			

		    psmt1.setString(1, BOOK_TITLE);
		    psmt1.setInt(2, BOOK_CNT);
		    
		    int inResult = psmt1.executeUpdate();
		    System.out.println(inResult + "행이 입력되었습니다.");
		    
		    psmt1.close();
		}
		catch(Exception e) {
			e.printStackTrace();			
		}
	}
	
	public void deleteBook() {
		try {
			Scanner scan2 = new Scanner(System.in);
			System.out.println("어떤 책을 삭제하시겠습니까? 도서번호 : ");
			int Book_id_scan = scan2.nextInt();
			String sql = "DELETE FROM BOOK_DB WHERE BOOK_ID=(?)";
			PreparedStatement psmt2 = con.prepareStatement(sql);
			psmt2.setInt(1, Book_id_scan);
			
			int inResult = psmt2.executeUpdate();
			System.out.println(inResult + "행이 삭제되었습니다.");
			psmt2.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void findBook() {
		try{	
			Scanner scan3 = new Scanner(System.in);
			System.out.print("어떤 책을 조회하시겠습니까? 책 제목 : ");
			String Book_id_scan = scan3.nextLine();
			String sql = "select * from book_db where book_title=?";
			PreparedStatement pstm4 = con.prepareStatement(sql);
			pstm4.setString(1, Book_id_scan);
			rs = pstm4.executeQuery();
			while(rs.next()) {
				System.out.println("책번호 : "+rs.getInt("book_id"));
				System.out.println("제  목 : "+rs.getString("book_title"));
				System.out.println("수  량 : " + rs.getInt("book_cnt"));
				System.out.println("----------------------------------------");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	
	public void findAllBook() {
		try{	
			String sql = "select * from book_db";
			PreparedStatement pstm4 = con.prepareStatement(sql);
			rs = pstm4.executeQuery();
		
			while(rs.next()) {
				System.out.println("책번호 : "+rs.getInt("book_id"));
				System.out.println("제  목 : "+rs.getString("book_title"));
				System.out.println("수  량 : " + rs.getInt("book_cnt"));
				System.out.println("----------------------------------------");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}
	

}
