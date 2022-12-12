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
	// �����������̳� �ȿ� ����� �� â�� �ߴ°� �ƴ϶� ȭ�� ��ȯ�� ��.
	
	/* �ʵ� ���� */
	private Connection connection = null;
	private Statement stmt = null;
	private PreparedStatement pstmt = null;;
	private ResultSet rs = null;
	
	private JTextField mSid, mKeyword, mName, mDept, mGpa, mYear; // Ű����, �̸�, �а�, ����, ���г⵵
	private JButton btnSearch, btnNext, btnPrev, btnExit, btnRegister, btnList, btnEdit, btnDel,
	Delcheck, btnname, btndept, btngpa, btnyear;	// ��ư��( + ���, ��ȸ, ����, ���� )  
	
	/* ������  ���� */

	public FinalTest() {
		
			////////////////////////// btnList(���) �� select * from student
		setTitle("�л����� ���� �ý���");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		mSid = new JTextField();
		mKeyword = new JTextField(20);
		mName = new JTextField();
		mDept = new JTextField();
		mGpa = new JTextField();
		mYear = new JTextField();
		
		btnSearch = new JButton("�˻�");
		btnSearch.addActionListener(this);
		
		btnPrev = new JButton("����");
		btnPrev.addActionListener(this);
		
		btnNext = new JButton("����");
		btnNext.addActionListener(this);
		
		btnExit = new JButton("����");
		btnExit.addActionListener(this);
		
		btnRegister = new JButton("���");
		btnRegister.addActionListener(this);
		
		btnList = new JButton("���");
		btnList.addActionListener(this);
		
		btnEdit = new JButton("����");
		btnEdit.addActionListener(this);
		
		btnDel = new JButton("����");
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
		connect();	// ������ ���� �޼ҵ�
	}
	
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");	// ������ �ߵ��� ���
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		String url = "jdbc:mysql://localhost:3306/22stm?useUnicode=true&characterEncoding=utf8";
		String username = "door";
		String password = "1234";
		
		
		try {
			connection = DriverManager.getConnection(url, username, password);
			pstmt = connection.prepareStatement("select * from student where name like ? or dept like ?"); 		// name�� �����ų� dept�� ������ �Ǹ�
		}	
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* �޼ҵ�  ���� */
	@Override
	public void actionPerformed(ActionEvent event) {
		
		//////////////////////// ��� ��ư
		if(event.getSource() == btnRegister) {
			String sql = "insert into student(name, dept, gpa, year) values ('"
					+ mName.getText().trim() + "', '" + // '�̸� + ',' = '�̸�',' 
					mDept.getText().trim() + "'," +	// '�а� + ', = '�а�',
					mGpa.getText().trim() + ", " +		// '���� + ',' = '����','
					mYear.getText().trim() + ")";	// ���г⵵ + ) = ���г⵵)
			// trim = ��������
				try {
					pstmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}	reset();
		}
		
		if(event.getSource() == btnDel) {	// �ȵǸ� mKeyword Ȱ���ϱ� --> �Է� �� ����
			new Delete();
		}
		
		try {
			
			if(event.getSource() == btnSearch) {	// �˻� ��ư
				String word = mKeyword.getText().trim();	// trim = �ڿ� ���� ���ڰ� �� ��� ��������
				pstmt.setString(1, word + "%");	// �յڷ� %�� ��
				pstmt.setString(2, word + "%");	// �� '% ? %' == ? �� �ִ� Ű���� �˻�
				rs = pstmt.executeQuery();
				rs.next();
				showRecord();
			} else if(event.getSource() == btnList) {	// ��� ��ư 
				new ListView();
				
			} 
			else if(event.getSource() == btnEdit) {	// ���� ��ư 
				new Edit();
				
			} else if(event.getSource() == btnPrev) {	// ���� ��ư�� ���� ���
				if(!rs.isFirst()) {	// isFrist = ������ ���� ù��°�� �ƴ϶��
					rs.previous();	// ���� �������� ������
					showRecord();
				}
				
			} else if (event.getSource() == btnNext) {	// ���� ��ư
				if(!rs.isLast()) {	// isLast = ������ ���� �������� �ƴ϶��
					rs.next();	// ���� �������� ������
					showRecord();
				}
			} else if (event.getSource() == btnExit) {	// ���� ��ư
				disconnect();
				System.exit(0);
			}
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	////////////////////////////////////////////// ���� ���� Ŭ����
	public void disconnect() throws SQLException {
		try {	// ���� ���ᰡ �� �� ���
			if(pstmt != null) pstmt.close();
			if(connection != null) connection.close();
		}
		catch (SQLException e) {	// ���� ���ᰡ ���� ���� ���
			e.printStackTrace();
		}
	}
	
	public void reset() {		/* 		���      */
		mName.setText("");	// �̸��� �Է��ϴ� ĭ
		mDept.setText("");	// �а��� �̸� ���� �Է��߱� ������ 0
		mGpa.setText("");	// ���� �Է��ϴ� ĭ
		mYear.setText("");	// ���г⵵�� �Է��ϴ� ĭ
	}
	
	
	public void showRecord() throws SQLException {	// ����, ���� ������ ���� 
		mName.setText(rs.getString("NAME"));
		mDept.setText(rs.getString("DEPT"));
		mGpa.setText("" + rs.getFloat("GPA"));
		mYear.setText("" + rs.getInt("YEAR"));
	}
	//////////////////////////////////////////////////////////////////////
	
	/* ��� �����ֱ� Ŭ���� */
	class ListView extends JFrame {	// ������ ȭ�� ����	
		ListView() {
			setTitle("����Ʈ ���");
			JPanel NewWindowContainer = new JPanel();	// ���ο� â�� ���� ��ü ����
			setContentPane(NewWindowContainer);	// ���ο� â ����
			String query = "select * from student";
			Statement stmt2;
			
			try {
				stmt2 = connection.createStatement();
				rs = stmt2.executeQuery(query);
				while(rs.next()) {
					JLabel NewLabel = new JLabel(rs.getInt(1) + ".�̸� : " + rs.getString(2)
					+ "���� : " + rs.getString(3) + "���� : " + rs.getFloat(4) +	// 1���� rs�� ���° ���������� ���� ����.
					"���г⵵ : " + rs.getString(5));
					NewWindowContainer.add(NewLabel);
				}
				stmt2.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			setSize(400, 300);
			setResizable(false);	// ������ â ȭ�� �����ϱ�
			setVisible(true);
		}
	}
	
																/* �����ϱ� Ŭ���� */
	
	public class Delete extends JFrame implements ActionListener{
		Delete() {
			setTitle("�л����� ���� �ý��� ������ ����");
			
			JPanel NewWindowContainer = new JPanel();	// ���ο� â�� ���� ��ü ����
			setContentPane(NewWindowContainer);	// ���ο� â ����
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1,1,10,10));
			panel.add(new JLabel("��ȣ", SwingConstants.RIGHT));
			panel.add(mSid);
			Delcheck = new JButton("Ȯ��");
			Delcheck.addActionListener(this);
			panel.add(Delcheck);
			add(panel, BorderLayout.CENTER);
			add(panel);
			pack();
			setSize(400, 300);
			setResizable(false);	// ������ â ȭ�� �����ϱ�
			setVisible(true);
		}
		
		public void actionPerformed(ActionEvent event) {
		if (event.getSource() == Delcheck) {	// Ȯ�� ��ư�� ���� ��
			String sql = "delete from student where sid = " + mSid.getText().trim();	// ���� ������
			try {
				pstmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			mSid.setText("");
		}
		}
	}
	
	/* ���� ó�� Ŭ���� */
	class Edit extends JFrame implements ActionListener {
		
		Edit() {	// ������ ���� ȭ�� ����	--> ����ȭ�� �ȿ� ���� Ȥ�� ������ ��ư �����  ///////////// ---> ��� �����ְ� üũ�ڽ��� Ŭ�� �� ����
			setTitle("�л����� ���� �ý��� ����");
			JPanel NewWindowContainer = new JPanel();	// ���ο� â�� ���� ��ü ����
			setContentPane(NewWindowContainer);	// ���ο� â ����
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(0,2,10,3));
			panel.add(new JLabel("��ȣ", SwingConstants.RIGHT));	// ��ȣ �Է� ĭ ----- ���° �л��� �̸��� �ٲ� ������
			panel.add(mSid);
			
			panel.add(new JLabel("�̸�", SwingConstants.RIGHT));	// �̸� �Է� ĭ
			panel.add(mName);
			
			panel.add(new JLabel("�а�", SwingConstants.RIGHT));	// �а� �Է� ĭ
			panel.add(mDept);
			
			panel.add(new JLabel("����", SwingConstants.RIGHT));	// ���� �Է� ĭ
			panel.add(mGpa);
			
			panel.add(new JLabel("���г⵵", SwingConstants.RIGHT));	// ���г⵵ �Է� ĭ
			panel.add(mYear);
			
			btnname = new JButton("�̸� ���� Ȯ��");
			btnname.addActionListener(this);	// �̸� ���� ��ư
			panel.add(btnname);
			
			btndept = new JButton("�а� ���� Ȯ��");	// �а� ���� ��ư
			btndept.addActionListener(this);
			panel.add(btndept);
			
			btngpa = new JButton("���� ���� Ȯ��");	// ���� ���� ��ư
			btngpa.addActionListener(this);
			panel.add(btngpa);
			
			btnyear = new JButton("���г⵵ ���� Ȯ��");	// ���г⵵ ���� ��ư
			btnyear.addActionListener(this);
			panel.add(btnyear);
			
			add(panel);
			pack();
			setSize(400, 300);
			setResizable(false);	// ������ â ȭ�� �����ϱ�
			setVisible(true);
		}
				
		public void actionPerformed(ActionEvent event) {
			
			if (event.getSource() == btnname) {		// �̸� ����
				String sql = "update student set name = '" + mName.getText().trim() + "'" + " where sid = " + mSid.getText().trim();
				try {
					pstmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				mName.setText("");
			}
			
			if (event.getSource() == btndept) {		// �а� ����
				String sql = "update student set dept = '" + mDept.getText().trim() + "'" + " where sid = " + mSid.getText().trim();
				try {
					pstmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				mDept.setText("");
			}
			
			if (event.getSource() == btngpa) {		// ���� ����
				String sql = "update student set gpa = '" + mGpa.getText().trim() + "'" + " where sid = " + mSid.getText().trim();
				try {
					pstmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				mGpa.setText("");
			}
			
			if (event.getSource() == btnyear) {		// ���г⵵ ����
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
