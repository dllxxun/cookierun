package main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import ingame.CookieImg;
import panels.EndPanel;
import panels.GamePanel;
import panels.IntroPanel;
import panels.SelectPanel;
import panels.SelectButtonPanel;
import main.listenAdapter;

import java.awt.CardLayout;

// windowBuilder 로 프레임만 제작하고 나머지는 입력

public class Main extends listenAdapter {
	
	private JFrame frame; // 창을 띄우기 위한 프레임

	private IntroPanel introPanel; // 인트로
	
	private SelectButtonPanel selectButtonPanel; //버튼 선택
	
	private SelectPanel selectPanel; // 캐릭터 선택

	private GamePanel gamePanel; // 게임진행

	private EndPanel endPanel; // 게임결과

	private CardLayout cl; // 카드 레이이웃 오브젝트

	private CookieImg ci; // 쿠키이미지

	// GamePanel 객체를 반환하는 메서드
	public GamePanel getGamePanel() {
		return gamePanel;
	}

	// GamePanel 객체를 설정하는 메서드
	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	// EndPanel 객체를 반환하는 메서드
	public EndPanel getEndPanel() {
		return endPanel;
	}
	
	
	/**
	 * Launch the application.
	 */
	// 애플리케이션 실행 메서드
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Main 클래스의 생성자
	public Main() {
		initialize();
	}

	// 초기화 메서드
	private void initialize() {
		// JFrame 객체 초기화
		frame = new JFrame();
		// 창의 크기 설정
		frame.setBounds(100, 100, 800, 500);
		// 창을 화면 중앙에 배치
		frame.setLocationRelativeTo(null);
		// 창을 닫을 때 프로그램 종료 설정
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// CardLayout 객체 초기화
		cl = new CardLayout(0, 0);
		// 프레임의 레이아웃을 CardLayout으로 설정
		frame.getContentPane().setLayout(cl);

		introPanel = new IntroPanel();
		introPanel.addMouseListener(this); // intro패널은 여기서 바로 넣는 방식으로 마우스리스너를 추가함.
        
		// SelectButtonPanel 객체 초기화
		selectButtonPanel = new SelectButtonPanel(this);
		
		selectPanel = new SelectPanel(this); // Main의 리스너를 넣기위한 this
		gamePanel = new GamePanel(frame, cl, this); // Main의 프레임 및 카드레이아웃을 이용하고 리스너를 넣기위한 this
		endPanel = new EndPanel(this); // Main의 리스너를 넣기위한 this

		// 모든 패널의 레이아웃을 null로 설정
		introPanel.setLayout(null);
		selectButtonPanel.setLayout(null);
		selectPanel.setLayout(null);
		gamePanel.setLayout(null);
		endPanel.setLayout(null);

		// 프레임에 패널들 추가
		frame.getContentPane().add(introPanel, "intro");
		frame.getContentPane().add(selectButtonPanel, "selectbutton");
		frame.getContentPane().add(selectPanel, "select");
		frame.getContentPane().add(gamePanel, "game");
		frame.getContentPane().add(endPanel, "end");
	}

	// 마우스 버튼이 눌렸을 때 호출되는 메서드
	@Override
	public void mousePressed(MouseEvent e) {
		// IntroPanel에서 마우스를 눌렀다면
		if (e.getComponent().toString().contains("IntroPanel")) {
			try {
				// 300ms 동안 일시 정지
				Thread.sleep(300);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			// selectbutton 패널을 카드레이아웃 최상단으로 변경
			cl.show(frame.getContentPane(), "selectbutton");
			// 리스너를 selectButtonPanel에 강제로 줌
	        selectButtonPanel.requestFocus();
	    }  else if (e.getComponent().getName().equals("btn1") || e.getComponent().getName().equals("btn2")) {
	        // bnt1 또는 bnt2라는 이름을 가진 버튼을 눌렀다면
	        cl.show(frame.getContentPane(), "select");
	        // 리스너를 selectPanel에 강제로 줌
	        selectPanel.requestFocus();
	    } else if (e.getComponent().getName().equals("StartBtn")) { // StartBtn이라는 이름을 가진 버튼을 눌렀다면
			if (selectPanel.getCi() == null) {
				JOptionPane.showMessageDialog(null, "캐릭터를 골라주세요"); // 캐릭터를 안골랐을경우 팝업
			} else {
				cl.show(frame.getContentPane(), "game"); // 캐릭터를 골랐다면 게임패널을 카드레이아웃 최상단으로 변경
				gamePanel.gameSet(selectPanel.getCi()); // 쿠키이미지를 넘겨주고 게임패널 세팅
				gamePanel.gameStart(); // 게임시작
				gamePanel.requestFocus(); // 리스너를 game패널에 강제로 줌
			}
			
		} else if (e.getComponent().getName().equals("endAccept")) { // endAccept 이라는 이름을 가진 버튼을 눌렀다면
			frame.getContentPane().remove(gamePanel); // 방금 했던 게임 패널을 프레임에서 삭제
			gamePanel = new GamePanel(frame, cl, this); // 게임패널을 새 패널로 교체
			gamePanel.setLayout(null);
			frame.getContentPane().add(gamePanel, "game"); // 프레임에 새 게임패널 추가(카드레이아웃 하단)
			
			frame.getContentPane().remove(selectPanel); // 방금 선택했던 select패널을 삭제
			selectPanel = new SelectPanel(this); // select 패널을 새 패널로 교체
			selectPanel.setLayout(null);
			frame.getContentPane().add(selectPanel, "select"); // 프레임에 새 select패널 추가(카드레이아웃 하단)
			cl.show(frame.getContentPane(), "select"); // 새 select패널을 카드레이아웃 최상단으로 이동 (화면에 보임)
			selectPanel.requestFocus(); // 리스너를 select패널에 강제로 줌
		}
	}
}
