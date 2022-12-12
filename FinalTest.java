package Final;

import java.awt.BorderLayout;
import java.awt.FlowLayout; 
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class FinalTest extends JFrame implements ActionListener{
	// 윈도우컨테이너 안에 만들면 새 창이 뜨는게 아니라 화면 전환이 됨.
	
	/* 필드 선언 */
	private Connection connection = null;
	private Statement stmt = null;
	private PreparedStatement pstmt = null;;
	private ResultSet rs = null;
	
	private JTextField mSid, mKeyword, mName, mDept, mGpa, mYear; // 키워드, 이름, 학과, 학점, 입학년도
	private JButton btnSearch, btnNext, btnPrev, btnExit, btnRegister, btnList, btnEdit, btnDel,
	Delcheck, btnname, btndept, btngpa, btnyear;	// 버튼들( + 등록, 조회, 수정, 삭제 )  
	
	/* 생성자  선언 */

	public FinalTest() {
		
			////////////////////////// btnList(목록) 은 select * from student
		setTitle("학생정보 관리 시스템");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		mSid = new JTextField();
		mKeyword = new JTextField(20);
		mName = new JTextField();
		mDept = new JTextField();
		mGpa = new JTextField();
		mYear = new JTextField();
		
		btnSearch = new JButton("검색");
		btnSearch.addActionListener(this);
		
		btnPrev = new JButton("이전");
		btnPrev.addActionListener(this);
		
		btnNext = new JButton("다음");
		btnNext.addActionListener(this);
		
		btnExit = new JButton("종료");
		btnExit.addActionListener(this);
		
		btnRegister = new JButton("등록");
		btnRegister.addActionListener(this);
		
		btnList = new JButton("목록");
		btnList.addActionListener(this);
		
		btnEdit = new JButton("수정");
		btnEdit.addActionListener(this);
		
		btnDel = new JButton("삭제");
		btnDel.addActionListener(this);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2, 10, 3));
		panel.add(mKeyword);
		
		panel.add(btnSearch);
		
		panel.add(new JLabel("NAME", SwingConstants.RIGHT));
		panel.add(mName);
		
		panel.add(new JLabel("DEPT", SwingConstants.RIGHT));
		panel.add(mDept);
		
		panel.add(new JLabel("GPA", SwingConstants.RIGHT));
		panel.add(mGpa);
		
		panel.add(new JLabel("YEAR", SwingConstants.RIGHT));
		panel.add(mYear);
	
		panel.add(btnPrev);
		panel.add(btnNext);
		panel.add(btnExit);
		panel.add(btnRegister);
		panel.add(btnList);
		panel.add(btnEdit);
		panel.add(btnDel);
		
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		add(panel);
		pack();
		connect();	// 데이터 연결 메소드
	}
	
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");	// 연결이 잘됐을 경우
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		String url = "jdbc:mysql://localhost:3306/22stm?useUnicode=true&characterEncoding=utf8";
		String username = "door";
		String password = "1234";
		
		
		try {
			connection = DriverManager.getConnection(url, username, password);
			pstmt = connection.prepareStatement("select * from student where name like ? or dept like ?"); 		// name이 들어오거나 dept가 들어오게 되면
		}	
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* 메소드  선언 */
	@Override
	public void actionPerformed(ActionEvent event) {
		
		//////////////////////// 등록 버튼
		if(event.getSource() == btnRegister) {
			String sql = "insert into student(name, dept, gpa, year) values ('"
					+ mName.getText().trim() + "', '" + // '이름 + ',' = '이름',' 
					mDept.getText().trim() + "'," +	// '학과 + ', = '학과',
					mGpa.getText().trim() + ", " +		// '학점 + ',' = '학점','
					mYear.getText().trim() + ")";	// 입학년도 + ) = 입학년도)
			// trim = 공백제거
				try {
					pstmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}	reset();
		}
		
		if(event.getSource() == btnDel) {	// 안되면 mKeyword 활용하기 --> 입력 시 삭제
			new Delete();
		}
		
		try {
			
			if(event.getSource() == btnSearch) {	// 검색 버튼
				String word = mKeyword.getText().trim();	// trim = 뒤에 공백 문자가 올 경우 제거해줌
				pstmt.setString(1, word + "%");	// 앞뒤로 %가 옴
				pstmt.setString(2, word + "%");	// 즉 '% ? %' == ? 가 있는 키워드 검색
				rs = pstmt.executeQuery();
				rs.next();
				showRecord();
			} else if(event.getSource() == btnList) {	// 목록 버튼 
				new ListView();
				
			} 
			else if(event.getSource() == btnEdit) {	// 수정 버튼 
				new Edit();
				
			} else if(event.getSource() == btnPrev) {	// 이전 버튼을 누를 경우
				if(!rs.isFirst()) {	// isFrist = 데이터 값이 첫번째가 아니라면
					rs.previous();	// 이전 페이지를 보여줌
					showRecord();
				}
				
			} else if (event.getSource() == btnNext) {	// 다음 버튼
				if(!rs.isLast()) {	// isLast = 데이터 값이 마지막이 아니라면
					rs.next();	// 다음 페이지를 보여줌
					showRecord();
				}
			} else if (event.getSource() == btnExit) {	// 종료 버튼
				disconnect();
				System.exit(0);
			}
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	////////////////////////////////////////////// 연결 종료 클래스
	public void disconnect() throws SQLException {
		try {	// 연결 종료가 잘 될 경우
			if(pstmt != null) pstmt.close();
			if(connection != null) connection.close();
		}
		catch (SQLException e) {	// 연결 종료가 되지 않을 경우
			e.printStackTrace();
		}
	}
	
	public void reset() {		/* 		취소      */
		mName.setText("");	// 이름을 입력하는 칸
		mDept.setText("");	// 학과는 미리 값을 입력했기 때문에 0
		mGpa.setText("");	// 성적 입력하는 칸
		mYear.setText("");	// 입학년도를 입력하는 칸
	}
	
	
	public void showRecord() throws SQLException {	// 이전, 다음 페이지 관리 
		mName.setText(rs.getString("NAME"));
		mDept.setText(rs.getString("DEPT"));
		mGpa.setText("" + rs.getFloat("GPA"));
		mYear.setText("" + rs.getInt("YEAR"));
	}
	//////////////////////////////////////////////////////////////////////
	
	/* 목록 보여주기 클래스 */
	class ListView extends JFrame {	// 별도로 화면 구성	
		ListView() {
			setTitle("리스트 목록");
			JPanel NewWindowContainer = new JPanel();	// 새로운 창을 담을 객체 생성
			setContentPane(NewWindowContainer);	// 새로운 창 생성
			String query = "select * from student";
			Statement stmt2;
			
			try {
				stmt2 = connection.createStatement();
				rs = stmt2.executeQuery(query);
				while(rs.next()) {
					JLabel NewLabel = new JLabel(rs.getInt(1) + ".이름 : " + rs.getString(2)
					+ "과목 : " + rs.getString(3) + "학점 : " + rs.getFloat(4) +	// 1번의 rs는 몇번째 순서인지를 세기 위함.
					"입학년도 : " + rs.getString(5));
					NewWindowContainer.add(NewLabel);
				}
				stmt2.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			setSize(400, 300);
			setResizable(false);	// 윈도우 창 화면 조절하기
			setVisible(true);
		}
	}
	
																/* 삭제하기 클래스 */
	
	public class Delete extends JFrame implements ActionListener{
		Delete() {
			setTitle("학생정보 관리 시스템 데이터 삭제");
			
			JPanel NewWindowContainer = new JPanel();	// 새로운 창을 담을 객체 생성
			setContentPane(NewWindowContainer);	// 새로운 창 생성
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1,1,10,10));
			panel.add(new JLabel("번호", SwingConstants.RIGHT));
			panel.add(mSid);
			Delcheck = new JButton("확인");
			Delcheck.addActionListener(this);
			panel.add(Delcheck);
			add(panel, BorderLayout.CENTER);
			add(panel);
			pack();
			setSize(400, 300);
			setResizable(false);	// 윈도우 창 화면 조절하기
			setVisible(true);
		}
		
		public void actionPerformed(ActionEvent event) {
		if (event.getSource() == Delcheck) {	// 확인 버튼을 누를 시
			String sql = "delete from student where sid = " + mSid.getText().trim();	// 삭제 쿼리문
			try {
				pstmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			mSid.setText("");
		}
		}
	}
	
	/* 수정 처리 클래스 */
	class Edit extends JFrame implements ActionListener {
		
		Edit() {	// 별도로 편집 화면 구성	--> 편집화면 안에 삭제 혹은 나가기 버튼 만들기  ///////////// ---> 목록 보여주고 체크박스들 클릭 시 삭제
			setTitle("학생정보 관리 시스템 수정");
			JPanel NewWindowContainer = new JPanel();	// 새로운 창을 담을 객체 생성
			setContentPane(NewWindowContainer);	// 새로운 창 생성
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(0,2,10,3));
			panel.add(new JLabel("번호", SwingConstants.RIGHT));	// 번호 입력 칸 ----- 몇번째 학생의 이름을 바꿀 것인지
			panel.add(mSid);
			
			panel.add(new JLabel("이름", SwingConstants.RIGHT));	// 이름 입력 칸
			panel.add(mName);
			
			panel.add(new JLabel("학과", SwingConstants.RIGHT));	// 학과 입력 칸
			panel.add(mDept);
			
			panel.add(new JLabel("학점", SwingConstants.RIGHT));	// 학점 입력 칸
			panel.add(mGpa);
			
			panel.add(new JLabel("입학년도", SwingConstants.RIGHT));	// 입학년도 입력 칸
			panel.add(mYear);
			
			btnname = new JButton("이름 수정 확인");
			btnname.addActionListener(this);	// 이름 수정 버튼
			panel.add(btnname);
			
			btndept = new JButton("학과 수정 확인");	// 학과 수정 버튼
			btndept.addActionListener(this);
			panel.add(btndept);
			
			btngpa = new JButton("학점 수정 확인");	// 학점 수정 버튼
			btngpa.addActionListener(this);
			panel.add(btngpa);
			
			btnyear = new JButton("입학년도 수정 확인");	// 입학년도 수정 버튼
			btnyear.addActionListener(this);
			panel.add(btnyear);
			
			add(panel);
			pack();
			setSize(400, 300);
			setResizable(false);	// 윈도우 창 화면 조절하기
			setVisible(true);
		}
				
		public void actionPerformed(ActionEvent event) {
			
			if (event.getSource() == btnname) {		// 이름 수정
				String sql = "update student set name = '" + mName.getText().trim() + "'" + " where sid = " + mSid.getText().trim();
				try {
					pstmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				mName.setText("");
			}
			
			if (event.getSource() == btndept) {		// 학과 수정
				String sql = "update student set dept = '" + mDept.getText().trim() + "'" + " where sid = " + mSid.getText().trim();
				try {
					pstmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				mDept.setText("");
			}
			
			if (event.getSource() == btngpa) {		// 학점 수정
				String sql = "update student set gpa = '" + mGpa.getText().trim() + "'" + " where sid = " + mSid.getText().trim();
				try {
					pstmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				mGpa.setText("");
			}
			
			if (event.getSource() == btnyear) {		// 입학년도 수정
				String sql = "update student set year = '" + mYear.getText().trim() + "'" + " where sid = " + mSid.getText().trim();
				try {
					pstmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				mYear.setText("");
			}
		}
		
	}
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		(new FinalTest()).setVisible(true);
	}

	
}
