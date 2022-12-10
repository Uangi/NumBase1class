package SoloPlay;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

// 1. 랜덤으로 1~9짜리 서로 다른 3개의 수를 생성한다.
// 2. 사용자에게 수를 입력받는다.
// 3. 입력받은 수를 검증한다.
// 4. 입력받은 3자리 수에서 볼, 스트라이크 개수를 구해서 반환한다.
// 5. 구해진 볼, 스트라이크를 통해 출력값을 결정한다.
// -1. 스트라이크, 볼 0개 : "낫싱"
// -2. 스트라이크 0~2개, 볼 0개 아님 : "n볼 n스트라이크"
// -3. 스트라이크 3개 : "3스트라이크"
// 		-1정답문구 출력 : "3개의 숫자를 모두 맞히셨습니다! 게임 종료"
// 		-2계속 진행할 것인지 묻기 : "게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요."
// 			-1 입력 : 처음으로 돌아간다.
// 			-2 입력 : 프로그램을 종료한다.
// 6. 스트라이크 3개가 나올 때 까지 2~5과정을 반복한다.

// 클래스 : 난수 생성, 입력, 스트라이크볼 처리
public class NumBase {
	// 2안 ) 첫째, 둘째, 셋째 각각 다른 인덱스 값을 매기는 방법?
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		/* try 횟수 (1~100) */
		/* 입력값, 스트라이크, 볼 */
		
		
			
		Scanner sc = new Scanner(System.in);
	
		Random rd = new Random();
		
		BufferedReader bu = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int[] num = new int[3];	// 랜덤 생성 저장 변수
		int[] pu = new int[3];	// 추리할 저장 변수
		char ch = 0;	// 프로그램 반복 제어 변수 
		int cnt = 0; // 추측 반복 제어 변수
		
		do {
			
		int strike = 0, ball = 0;
		for(int i = 0; i < 3; i++) {
			
			//while(num[i] == num[i+1]) {		// 랜덤 생성 시 같을 경우
			int ans = rd.nextInt(9)+1;	// 한 자리 수 무작위 생성 1~9
			num[i] = ans;	// 가끔 4자리 수 들어옴
			
			for(int j = 0; j < i; j++) {	// 중복 제거
				
				if(num[i] == num[j]) {	// 중복 값이 생기면 i를 -1해서 반복
				i--;	
				if( i < 0 ) i = 0;
				//break;
				}
				
					// 무작위로 생성된 값 배열에 순서대로 넣기
			}
			
			System.out.print(num[i]);	// 검사
			
		}	// 난수 생성
		
		do {
			cnt = 0; // 답 횟수 제어 변수
			strike = 0;
			ball = 0;
		System.out.print("\r\n추측 : ");
		String str = bu.readLine();
		for(int i = 0; i < pu.length; i++) {	// 엔터 키 치는 순간 출력창에서 숫자가 밑으로 내려가버림 //3
			pu[i] = str.charAt(i) - '0';	// 공백, 줄바꿈 없이 입력
			
			
		if (num[i] == pu[i]) {	// 스트라이크
			strike++;
			cnt++;
		}
	}
			
		/* 볼 */
		for(int i = 0; i < 3; i++) {
			
			for(int j = 0; j < 3; j++) {
				
				if(num[i] == pu[j]) {	// num[1] == pu[0], [1][0],[1][1],[1][2]	// 값은 같지만 다른 자리에 있는 경우
					if( i != j ) {
					ball++;
					}
				}
				
				if(num[i] == pu[j]) {	// 스트라이크와 중첩 방지
					if( i == j ) {
					ball--;
					}
					if(ball < 0) {
						ball = 0;
					}
				}
				
			}
			
		}
			if(strike == 0 && ball == 0) {	// 아무것도 없다면 nothing
				System.out.println("nothing");
			}
			else {
		System.out.println("스트라이크 :  " + strike + ", " + "볼 : " + ball + "");
			} if(cnt == 3) {
				break;		// 숫자 입력 벗어남
			}
		} while(cnt < 3); 
		
		System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
		System.out.println("반복하시려면 아무 키나 눌러주세요. / 프로그램 종료 (n || N)");
		ch = sc.next().charAt(0);	// 0번째 문자 가져오기
		if(ch == 'n' || ch == 'N') {
			break;
		}
		}while(cnt == 3);	// 프로그램 반복
}
}
		/* 출력 = 남은 정답추리 가짓수 */
		
	


