package SoloPlay;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

// 1. �������� 1~9¥�� ���� �ٸ� 3���� ���� �����Ѵ�.
// 2. ����ڿ��� ���� �Է¹޴´�.
// 3. �Է¹��� ���� �����Ѵ�.
// 4. �Է¹��� 3�ڸ� ������ ��, ��Ʈ����ũ ������ ���ؼ� ��ȯ�Ѵ�.
// 5. ������ ��, ��Ʈ����ũ�� ���� ��°��� �����Ѵ�.
// -1. ��Ʈ����ũ, �� 0�� : "����"
// -2. ��Ʈ����ũ 0~2��, �� 0�� �ƴ� : "n�� n��Ʈ����ũ"
// -3. ��Ʈ����ũ 3�� : "3��Ʈ����ũ"
// 		-1���乮�� ��� : "3���� ���ڸ� ��� �����̽��ϴ�! ���� ����"
// 		-2��� ������ ������ ���� : "������ ���� �����Ϸ��� 1, �����Ϸ��� 2�� �Է��ϼ���."
// 			-1 �Է� : ó������ ���ư���.
// 			-2 �Է� : ���α׷��� �����Ѵ�.
// 6. ��Ʈ����ũ 3���� ���� �� ���� 2~5������ �ݺ��Ѵ�.

// Ŭ���� : ���� ����, �Է�, ��Ʈ����ũ�� ó��
public class NumBase {
	// 2�� ) ù°, ��°, ��° ���� �ٸ� �ε��� ���� �ű�� ���?
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		/* try Ƚ�� (1~100) */
		/* �Է°�, ��Ʈ����ũ, �� */
		
		
			
		Scanner sc = new Scanner(System.in);
	
		Random rd = new Random();
		
		BufferedReader bu = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int[] num = new int[3];	// ���� ���� ���� ����
		int[] pu = new int[3];	// �߸��� ���� ����
		char ch = 0;	// ���α׷� �ݺ� ���� ���� 
		int cnt = 0; // ���� �ݺ� ���� ����
		
		do {
			
		int strike = 0, ball = 0;
		for(int i = 0; i < 3; i++) {
			
			//while(num[i] == num[i+1]) {		// ���� ���� �� ���� ���
			int ans = rd.nextInt(9)+1;	// �� �ڸ� �� ������ ���� 1~9
			num[i] = ans;	// ���� 4�ڸ� �� ����
			
			for(int j = 0; j < i; j++) {	// �ߺ� ����
				
				if(num[i] == num[j]) {	// �ߺ� ���� ����� i�� -1�ؼ� �ݺ�
				i--;	
				if( i < 0 ) i = 0;
				//break;
				}
				
					// �������� ������ �� �迭�� ������� �ֱ�
			}
			
			System.out.print(num[i]);	// �˻�
			
		}	// ���� ����
		
		do {
			cnt = 0; // �� Ƚ�� ���� ����
			strike = 0;
			ball = 0;
		System.out.print("\r\n���� : ");
		String str = bu.readLine();
		for(int i = 0; i < pu.length; i++) {	// ���� Ű ġ�� ���� ���â���� ���ڰ� ������ ���������� //3
			pu[i] = str.charAt(i) - '0';	// ����, �ٹٲ� ���� �Է�
			
			
		if (num[i] == pu[i]) {	// ��Ʈ����ũ
			strike++;
			cnt++;
		}
	}
			
		/* �� */
		for(int i = 0; i < 3; i++) {
			
			for(int j = 0; j < 3; j++) {
				
				if(num[i] == pu[j]) {	// num[1] == pu[0], [1][0],[1][1],[1][2]	// ���� ������ �ٸ� �ڸ��� �ִ� ���
					if( i != j ) {
					ball++;
					}
				}
				
				if(num[i] == pu[j]) {	// ��Ʈ����ũ�� ��ø ����
					if( i == j ) {
					ball--;
					}
					if(ball < 0) {
						ball = 0;
					}
				}
				
			}
			
		}
			if(strike == 0 && ball == 0) {	// �ƹ��͵� ���ٸ� nothing
				System.out.println("nothing");
			}
			else {
		System.out.println("��Ʈ����ũ :  " + strike + ", " + "�� : " + ball + "");
			} if(cnt == 3) {
				break;		// ���� �Է� ���
			}
		} while(cnt < 3); 
		
		System.out.println("3���� ���ڸ� ��� �����̽��ϴ�! ���� ����");
		System.out.println("�ݺ��Ͻ÷��� �ƹ� Ű�� �����ּ���. / ���α׷� ���� (n || N)");
		ch = sc.next().charAt(0);	// 0��° ���� ��������
		if(ch == 'n' || ch == 'N') {
			break;
		}
		}while(cnt == 3);	// ���α׷� �ݺ�
}
}
		/* ��� = ���� �����߸� ������ */
		
	


