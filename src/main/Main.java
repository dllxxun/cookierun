package main;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ingame.CookieImg;
import panels.SelectButtonPanel;
import network.GameServer;
import network.GameClient1;
import network.GameClient2;

import panels.EndPanel;
import panels.GamePanel;
import panels.IntroPanel;
import panels.SelectPanel;
import panels.MinOrRunPanel;
import minigame.MiniGameStartPanel;
import minigame.CookieFactory;
import minigame.DescriptionPanel;
import minigame.MiniGameEndPanel;

import main.listenAdapter;
import java.awt.CardLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// windowBuilder 로 프레임만 제작하고 나머지는 입력dd

public class Main extends listenAdapter {
    
    private JFrame frame; // 창을 띄우기 위한 프레임

    private IntroPanel introPanel; // 인트로
    
    private SelectButtonPanel selectButtonPanel; //버튼 선택
    
    private SelectPanel selectPanel; // 캐릭터 선택
    
    private MinOrRunPanel minOrRunPanel;
    
    private GamePanel gamePanel; // 게임진행

    private EndPanel endPanel; // 게임결과
    
	private CookieFactory cookieFactoryPanel; // 미니게임 패널 추가
	
	private DescriptionPanel descriptionPanel;
	
	private MiniGameStartPanel miniGameStartPanel; //미니게임 intro 패널 추가
	
	private MiniGameEndPanel miniGameEndPanel;

    private CardLayout cl; // 카드 레이이웃 오브젝트

    private CookieImg ci; // 쿠키이미지

    public static boolean user1Ready = false;
    public static boolean user2Ready = false;
    public static boolean isUser1 = true;
    public static boolean isUser2 = true;
    
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
        
        minOrRunPanel = new MinOrRunPanel(this);
        
        // SelectButtonPanel 객체 초기화
        selectButtonPanel = new SelectButtonPanel(this);
        
        selectPanel = new SelectPanel(this); // Main의 리스너를 넣기위한 this
        gamePanel = new GamePanel(frame, cl, this); // Main의 프레임 및 카드레이아웃을 이용하고 리스너를 넣기위한 this
        endPanel = new EndPanel(this); // Main의 리스너를 넣기위한 this

        // 모든 패널의 레이아웃을 null로 설정
        introPanel.setLayout(null);
        minOrRunPanel.setLayout(null);
        selectButtonPanel.setLayout(null);
        selectPanel.setLayout(null);
        gamePanel.setLayout(null);
        endPanel.setLayout(null);

        // 프레임에 패널들 추가
        frame.getContentPane().add(introPanel, "intro");
        frame.getContentPane().add(minOrRunPanel, "minOrRun");
        frame.getContentPane().add(selectButtonPanel, "selectbutton");
        frame.getContentPane().add(selectPanel, "select");
        frame.getContentPane().add(gamePanel, "game");
        frame.getContentPane().add(endPanel, "end");
        
        // minigame용 코드
        miniGameStartPanel = new MiniGameStartPanel(this);
		frame.getContentPane().add(miniGameStartPanel, "miniGameStart");
		
		descriptionPanel = new DescriptionPanel(this);
		frame.getContentPane().add(descriptionPanel, "description");
		
		cookieFactoryPanel = new CookieFactory(this);
		cookieFactoryPanel.setLayout(null);
		frame.getContentPane().add(cookieFactoryPanel, "cookieFactory");
		
		miniGameEndPanel = new MiniGameEndPanel(this);
		frame.getContentPane().add(miniGameEndPanel, "miniGameEnd");
		
    }
    
    public void showSelectPanel() {
		cl.show(frame.getContentPane(), "select");
		selectPanel.requestFocus();
	}
	
    public void showSelectbuttonPanel() {
		cl.show(frame.getContentPane(), "selectbutton");
		selectButtonPanel.requestFocus();
	}
    
	public void showCookieFactoryPanel() {
		cl.show(frame.getContentPane(), "cookieFactory");
		cookieFactoryPanel.requestFocus();
	}
	
	public void showMiniGameIntro() {
		cl.show(frame.getContentPane(), "miniGameStart");
		miniGameStartPanel.requestFocus();
	}
	
	public void showDescriptionPanel() {
		cl.show(frame.getContentPane(), "description");
		descriptionPanel.requestFocus();
	}
	
	public void returnToStart() {
		cl.show(frame.getContentPane(), "miniGameStart");
		miniGameStartPanel.requestFocus();
	}
	
	public void showMiniGameEndPanel(int score) {
		miniGameEndPanel.setMiniGameScoreLabel(score);
		cl.show(frame.getContentPane(), "miniGameEnd");
		miniGameEndPanel.requestFocus();
	}

    // 마우스 버튼이 눌렸을 때 호출되는 메서드
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getComponent().toString().contains("IntroPanel")) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            cl.show(frame.getContentPane(), "minOrRun"); // IntroPanel에서 minOrRunPanel로 전환
            minOrRunPanel.requestFocus();
        } else if (e.getComponent().getName().equals("minibtn")) {
        	// minigamestartLabel을 클릭하면 미니게임 시작 패널로 넘어감
            cl.show(frame.getContentPane(), "miniGameStart");
            miniGameStartPanel.requestFocus();
        } else if (e.getComponent().getName().equals("cookiebtn")) {
            // cookierunstartLabel을 클릭하면 selectButtonPanel로 넘어감
            cl.show(frame.getContentPane(), "selectbutton");
            selectButtonPanel.requestFocus();
        } else if (e.getComponent().getName().equals("btn1")) {
            // btn1을 클릭하면 selectPanel로 넘어감
            cl.show(frame.getContentPane(), "select");
            selectPanel.requestFocus();
        } else if (e.getComponent().getName().equals("btn2")) {
            // btn2를 클릭하면 새 창이 생성되고 서버와 클라이언트가 시작됨
            cl.show(frame.getContentPane(), "select"); // 기존 창에서 selectPanel로 변경
            selectPanel.showReadyButton(); // ReadyBtn을 표시하는 메서드 호출
            openNewGameWindow(); // 새로운 창 생성 및 selectPanel로 표시
            new Thread(() -> GameServer.main(null)).start(); // 서버 시작
            new Thread(() -> {
                try {
                    Thread.sleep(1000); // 서버가 먼저 시작될 수 있도록 대기
                    
                    GameClient1.main(null); // 클라이언트 시작
                    GameClient2.main(null); // 클라이언트 시작
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }).start();
        } else if (e.getComponent().getName().equals("ReadyBtn")) {
            // ReadyBtn을 클릭하면 gamePanel로 전환
            if (selectPanel.getCi() == null) {
                JOptionPane.showMessageDialog(null, "캐릭터를 골라주세요"); // 캐릭터를 선택하지 않았을 경우 팝업
            } else {
            	if (isUser1) {
                    GameClient1.readyUser1();
                    isUser1 = false;
                    user1Ready=true;
                } if(isUser2) {
                    GameClient2.readyUser2();
                    user2Ready=true;
                }
                checkIfBothReady(); 
                
            }
        } else if (e.getComponent().getName().equals("StartBtn")) {
            // StartBtn을 클릭하면 gamePanel로 전환
            if (selectPanel.getCi() == null) {
                JOptionPane.showMessageDialog(null, "캐릭터를 골라주세요");
            } else {
                cl.show(frame.getContentPane(), "game");
                gamePanel.gameSet(selectPanel.getCi());
                gamePanel.gameStart();
                gamePanel.requestFocus();
            }
        } else if ("backButtonImage".equals(e.getComponent().getName())) {
            // MiniGameEndPanel에서 backButtonImage를 클릭하면 selectPanel로 전환
            cl.show(frame.getContentPane(), "select");
            selectPanel.requestFocus();
        } else if (e.getComponent().getName().equals("endAccept")) {
            // 게임 종료 후 설정
            frame.getContentPane().remove(gamePanel);
            gamePanel = new GamePanel(frame, cl, this);
            gamePanel.setLayout(null);
            frame.getContentPane().add(gamePanel, "game");

            frame.getContentPane().remove(selectPanel);
            selectPanel = new SelectPanel(this);
            selectPanel.setLayout(null);
            frame.getContentPane().add(selectPanel, "select");
            cl.show(frame.getContentPane(), "select");
            selectPanel.requestFocus();
        }
        
    }

    

    private void openNewGameWindow() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main window = new Main();
                    window.frame.setVisible(true);
                    window.cl.show(window.frame.getContentPane(), "select"); // 새로운 창에서 바로 select 패널을 보여줌
                    window.selectPanel.showReadyButton(); // 새로운 창에서도 ReadyBtn 보이기
                    new Thread(() -> GameClient1.main(null)).start(); // 클라이언트 시작
                    new Thread(() -> GameClient2.main(null)).start(); // 클라이언트 시작
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void checkIfBothReady() {
        if (user1Ready && user2Ready) {
        	System.out.println("준비완료 되었습니다");
            System.out.println("게임을 시작합니다");
            startGame(); // 게임 시작
        } else {
            JOptionPane.showMessageDialog(null, "다른 플레이어의 준비를 기다리세요."); // 다른 플레이어가 준비되지 않았을 경우 팝업
        }
    }
    public void startGame() {
        cl.show(frame.getContentPane(), "game");
        gamePanel.gameSet(selectPanel.getCi());
        gamePanel.gameStart();
        gamePanel.requestFocus();
    }
}
