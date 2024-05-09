package view;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import controller.Controller;
import model.DTO;

public class QuizMain {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Random ran = new Random();
		Controller controller = new Controller();
		DTO dto = null;
		int rankH = 0;
		int scoreL = 0;
		
		Font impactFont = new Font("Impact", Font.PLAIN, 14);
		
		String text = "본 프로그램은 정보처리기사시험 학습을 목표로 하여 'BGM'은 일부러 생략하였습니다.\n"
				+ "명령어와 관련된 문제는 소문자로 작성하시고, 이외의 정답은  한글 및 영어 대문자로\n작성하셔야 정상 정답처리됩니다. 또한, 정답을 입력할 때 띄어쓰기 없이 입력해 주세요. "
				;
		
		
		for (int i = 0; i < text.length(); i++) {
			System.out.print(text.charAt(i));
			try {
				Thread.sleep(60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} // 1000이 1초. 100이 0.1초

	
		}System.out.println();
		System.out.println();
		

		while (true) {
			System.out.println("                                 ★정처기 꽉 붙 JAVA★");
			System.out.println("=================메뉴==================");
			System.out.print("1.회원가입 2.로그인 3.최고점수확인 4.종료  >>>  ");
			int a = sc.nextInt(); // 메뉴선택입력

			if (a == 1) { // 회원가입
				System.out.println("================회원등록================");
				System.out.print("ID를 입력하세요  :  ");
				String id = sc.next();
				System.out.print("PW를 입력하세요  :  ");
				String pw = sc.next();
				System.out.print("닉네임을 입력하세요  :  ");
				String nic = sc.next();
				System.out.println();
				
				dto = new DTO(id, pw, nic);
				controller.join(dto);
				System.out.println("         ♣ 환영합니다!! " + dto.getNic() + "님! ♣");
				System.out.println();
			} else if (a == 2) { // 로그인
				System.out.println("================로그인=================");
				System.out.print("ID를 입력하세요  :  ");
				String id = sc.next();
				System.out.print("PW를 입력하세요  :  ");
				String pw = sc.next();
				DTO info = controller.login(id, pw);
				System.out.println();
				if (info != null) {
					System.out.println("★ "+info.getNic() + "님 안녕하세요! ★");
					System.out.println();
					System.out.println("==============난이도 선택===============");
					System.out.print("1.이지 2.하드  >>  ");
					int b = sc.nextInt();
					System.out.println();
					if (b == 1) { // 이지모드 게임실행
						System.out.println("▶ 이지모드 게임을 시작합니다. ◀");

						int cnt = 1;
						int correct = 0;
						ArrayList<DTO> wlist = new ArrayList<DTO>();

						while (cnt <= 10) {
							ArrayList<DTO> list = controller.getQuestionList();
							DTO wrongQ = null;

							int num = ran.nextInt(list.size());
							String l_ans = list.get(num).getAnswer().substring(0, 1);
							System.out.println(list.get(num).getQuestion());
							System.out.print("Q" + cnt + ". 정답 입력  :  " + l_ans);
							String ans = sc.next();
							if (list.get(num).getAnswer().equals(l_ans + ans)) {
								System.out.println("◆ 정답입니다. ◆");
								correct++;
							} else {
								System.out.println("◇ 틀렸습니다. ◇");
								wrongQ = list.get(num);
								wlist.add(wrongQ);
							}
							cnt++;
							System.out.println();
						}
						scoreL = correct;
						dto = new DTO(id, info.getNic(), rankH, scoreL);
						controller.save(dto);

						System.out.printf("총 %d문제 중 %d문제를 맞추셨습니다.%n", cnt - 1, correct);
						System.out.println();
						System.out.println("틀린문제를 다시 푸시겠습니까?");
						System.out.print("1.예 2.종료 >> ");
						int c = sc.nextInt();
						if (c == 1) {
							System.out.println("▶ 오답풀이를 시작합니다. ◀");
							for (int i = 0; i < wlist.size(); i++) {
								System.out.println(wlist.get(i).getQuestion());
								System.out.print("Q" + (i + 1) + ". 정답 입력  :  ");
								String ans = sc.next();
								if (wlist.get(i).getAnswer().equals(ans)) {
									System.out.println("◆ 정답입니다. ◆");

								} else {
									System.out.printf("◇ 틀렸습니다. 정답은 '%s' 입니다. ◇%n",wlist.get(i).getAnswer());
								}
								System.out.println();
							}
						} else if (c == 2) {
							System.out.println("게임을 종료합니다.");
							break;
						} else {
							System.out.println("정확한 숫자를 입력하세요.");
						}
					} else if (b == 2) {
						int cnt = 1;
						int correct = 0;
						ArrayList<DTO> wlist = new ArrayList<DTO>();

						System.out.println("▶ 하드모드 게임을 시작합니다. ◀");
						System.out.println();
						// 하드모드 게임 실행
						while (cnt <= 10) {
							ArrayList<DTO> list = controller.getQuestionList();
							DTO wrongQ = null;

							int num = ran.nextInt(list.size());
							System.out.println(list.get(num).getQuestion());
							System.out.print("Q" + cnt + ". 정답 입력  :  ");
							String ans = sc.next();
							if (list.get(num).getAnswer().equals(ans)) {
								System.out.println("◆ 정답입니다. ◆");
								correct++;
							} else {
								System.out.println("◇ 틀렸습니다. ◇");
								wrongQ = list.get(num);
								wlist.add(wrongQ);
							}
							cnt++;
							System.out.println();
						}
						rankH = correct;
						dto = new DTO(id, info.getNic(), rankH, scoreL);
						controller.save(dto);

						System.out.printf("총 %d문제 중 %d문제를 맞추셨습니다.%n", cnt - 1, correct);
						System.out.println();
						System.out.println("틀린문제를 다시 푸시겠습니까?");
						System.out.print("1.예 2.종료 >>  ");
						int c = sc.nextInt();
						System.out.println();
						if (c == 1) {
							System.out.println("▶ 오답풀이를 시작합니다. ◀");
							for (int i = 0; i < wlist.size(); i++) {
								System.out.println(wlist.get(i).getQuestion());
								System.out.print("Q" + (i + 1) + ". 정답 입력  :  ");
								String ans = sc.next();
								if (wlist.get(i).getAnswer().equals(ans)) {
									System.out.println("◆ 정답입니다. ◆");

								} else {
									System.out.printf("◇ 틀렸습니다. 정답은 '%s'입니다. ◇%n",wlist.get(i).getAnswer());
								}
								System.out.println();
							}

						} else if (c == 2) {
							System.out.println("▷ 게임을 종료합니다. ◁");
							break;
						} else {
							System.out.println("정확한 숫자를 입력하세요.");
						}
					}
				} else {
					System.out.println("등록되지 않은 회원입니다.");
				}
			} else if (a == 3) {
				
				System.out.print("ID를 입력해주세요 >>  ");
				String id = sc.next();
				System.out.println("==============최고점수확인===============");

				int totalHighestScore = controller.getUserHighestScores(id);
				System.out.println("★ 총 최고 점수 : " + totalHighestScore);

			} else if (a == 4) {
				System.out.println("▷ 게임을 종료합니다. ◁");
				break;
			} else {
				System.out.println("정확한 번호를 입력하세요.");
			}
		}
	}
}