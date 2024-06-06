package minigame;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import ingame.Back;
import main.Main;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import main.listenAdapter;

public class CookieFactory extends JPanel implements ActionListener {
	private Main main;
	
	private JButton backButton; //미니게임 종료 후 메인화면으로 돌아가는 버튼
	private listenAdapter listener; //메인클래스 리스너 객체
	
	private ImageIcon backgroundImage; //배경화면
	
	private JButton ing1, ing2, ing3, ing4, ing5; // 쿠키 만들기 위한 재료 컴포넌트
	private List<ImageIcon> selectedIngredients; // 선택된 재료 리스트
	private JPanel selectedIngredientsPanel; // 골라진 재료를 표시하는 패널
	
	private List<ImageIcon> cookieImages; // 쿠키 이미지
	private final int REQUIRED_INGREDIENTS = 3; // 쿠키를 만들기 위해 필요한 재료 개수

	private List<ImageIcon> guestImages; // 손님 이미지
	private ImageIcon currentGuest; //현재 손님
	private JLabel guestLabel; //손님 이미지 표시를 위한 레이블

	private ImageIcon currentGuestCookie; // 현재 손님이 원하는 쿠키
	private JLabel cookieLabel; //현재 손님이 원하는 쿠키를 표시하기 위한 레이블
	private List<List<Integer>> cookieIngredients; //쿠키를 만들기 위한 재료 인덱스
	
	private List<ImageIcon> reqIngredientsImage; // 각 쿠키를 만들기 위해서 필요한 재료 이미지 리스트
	private ImageIcon currentReqIngredients; //현재 필요한 재료 이미지
	private JLabel reqIngredientsLabel; // 각 쿠키를 만들기 위해서 필요한 재료 이미지 표시하는 레이블
	
	// 제한시간 타이머
	private final int GAME_TIME_LIMIT = 100; // 총 게임 제한시간 100초
	private boolean isGameRunning;
	private JLabel timerLabel;
	private int timeRemaining;
	private Thread timerThread;
	
	//점수
	private int score;
	private JLabel scoreLabel;
	
	public CookieFactory(Main main) {
		this.main = main;
		this.selectedIngredients = new ArrayList<>();
		this.cookieIngredients = new ArrayList<>();
		setLayout(null);
		initialize();
	}
	
	//이미지 사이즈 조절 메소드
	private ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
		java.awt.Image img = icon.getImage();
		java.awt.Image resizedImg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(resizedImg);
	}
	
	private void initialize() {
		//패널 배경 설정
		backgroundImage = new ImageIcon("img/minigame/minigameBg.png");
		
		// 쿠키 만들때 필요한 재료 이미지 설정
		ing1 = createIngredientButton(resizeImageIcon(new ImageIcon("img/minigame/ingredient1.png"), 50, 50), 20, 390);
		ing2 = createIngredientButton(resizeImageIcon(new ImageIcon("img/minigame/ingredient2.png"), 50, 50), 80, 390);
		ing3 = createIngredientButton(resizeImageIcon(new ImageIcon("img/minigame/ingredient3.png"), 50, 50), 140, 390);
		ing4 = createIngredientButton(resizeImageIcon(new ImageIcon("img/minigame/ingredient4.png"), 50, 50), 200, 390);
		ing5 = createIngredientButton(resizeImageIcon(new ImageIcon("img/minigame/ingredient5.png"), 50, 50), 260, 390);
	    
		// 손님 이미지 초기화
		guestImages = new ArrayList<>();
		guestImages.add(resizeImageIcon(new ImageIcon("img/select/selectCh1.png"), 100, 150));
	    guestImages.add(resizeImageIcon(new ImageIcon("img/select/selectCh2.png"), 100, 150));
	    guestImages.add(resizeImageIcon(new ImageIcon("img/select/selectCh4.png"), 100, 150));
	    
		// 완성품 쿠키 이미지 초기화 (손님이 제시)
	    cookieImages = new ArrayList<>();
	    cookieImages.add(resizeImageIcon(new ImageIcon("img/minigame/cookie1.png"), 100, 100));
	    cookieImages.add(resizeImageIcon(new ImageIcon("img/minigame/cookie2.png"), 100, 100));
	    cookieImages.add(resizeImageIcon(new ImageIcon("img/minigame/cookie3.png"), 100, 100));
	    
	    // 각 쿠키별 필요한 재료 이미지
	    reqIngredientsImage = new ArrayList<>();
	    reqIngredientsImage.add(resizeImageIcon(new ImageIcon("img/minigame/reqIng1.png"), 120, 70));
	    reqIngredientsImage.add(resizeImageIcon(new ImageIcon("img/minigame/reqIng2.png"), 120, 70));
	    reqIngredientsImage.add(resizeImageIcon(new ImageIcon("img/minigame/reqIng3.png"), 120, 70));
	    
	    //재료 이미지 표시할 레이블
	    reqIngredientsLabel = new JLabel();
	    reqIngredientsLabel.setBounds(615, 310, 120, 100); // 재료 이미지가 표시될 위치 지정
	    add(reqIngredientsLabel);

	    
	    // 각 쿠키별 필요한 재료 인덱스 저장
	    List<Integer> cookie1Ingredients = new ArrayList<>();
	    cookie1Ingredients.add(1); // ing1
        cookie1Ingredients.add(2); // ing2
        cookie1Ingredients.add(5); // ing5
        cookieIngredients.add(cookie1Ingredients);
        
        List<Integer> cookie2Ingredients = new ArrayList<>();
	    cookie2Ingredients.add(2); // ing2
        cookie2Ingredients.add(3); // ing3
        cookie2Ingredients.add(5); // ing5
        cookieIngredients.add(cookie2Ingredients);
        
        List<Integer> cookie3Ingredients = new ArrayList<>();
        cookie3Ingredients.add(1); // ing1
        cookie3Ingredients.add(3); // ing3
        cookie3Ingredients.add(4); // ing4
        cookieIngredients.add(cookie3Ingredients);
        
		// 타이머 라벨 생성
		timerLabel = new JLabel();
		timerLabel.setBounds(650, 0, 100, 30);
		add(timerLabel);
		
		// 점수 라벨 생성
		scoreLabel = new JLabel();
		scoreLabel.setBounds(650, 20, 100, 30);
		add(scoreLabel);
		score = 0;
		
		//손님 위치 설정 및 손님 나타내기
		guestLabel = new JLabel();
		guestLabel.setBounds(50, 100, 200, 200);
		add(guestLabel);
		showRandomGuest();
		
		//손님이 랜덤으로 보여주는 쿠키 위치 설정
		cookieLabel = new JLabel();
		cookieLabel.setBounds(50, 150, 100, 100);
		add(cookieLabel);
		showRandomCookie();
		
		//쿠키를 만들기 위해 선택된 재료 나타내는 레이블 초기화
		selectedIngredientsPanel = new JPanel();
		selectedIngredientsPanel.setBounds(360, 390, 500, 50);
		selectedIngredientsPanel.setOpaque(false);
		selectedIngredientsPanel.setBorder(null);
		selectedIngredientsPanel.setLayout(null);
		add(selectedIngredientsPanel);
		
		//고른 쿠키 panel 비우는 reset 버튼 추가
		JButton resetButton = new JButton("재료 초기화");
		resetButton.setBounds(200, 330, 120, 30);
		resetButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedIngredients.clear();
				updateSelectedIngredientsLabel();
			}
		});
		add(resetButton);
		
		//뒤로가기 버튼 생성 // 뒤로가기 안됨 없지만 더 보편적으로 만들어야 함 
		backButton = new JButton("Back");
		backButton.setName("backButton");
		backButton.setBounds(5, 5, 100, 30);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetGame();
				main.returnToStart();
			}
		});
		add(backButton);
		startGame();
	}

	
	//랜덤으로 손님을 보여주는 코드
	private void showRandomGuest() {
		//랜덤으로 손님 선택
		Random rand = new Random();
		int index = rand.nextInt(guestImages.size());
		currentGuest = guestImages.get(index);
		
		currentGuest = resizeImageIcon(currentGuest, 120, 160);
		guestLabel.setIcon(currentGuest);
			
		//손님 위치 설정
		guestLabel.setBounds(400, 140, currentGuest.getIconWidth(), currentGuest.getIconHeight());
	}
		
	//손님이 랜덤으로 선택한 쿠키 보여주는 코드
	private void showRandomCookie() {
		// 랜덤으로 쿠키 선택
		Random rand = new Random();
		currentGuestCookie = cookieImages.get(rand.nextInt(cookieImages.size()));
		cookieLabel.setIcon(currentGuestCookie);
		
		currentReqIngredients = reqIngredientsImage.get(cookieImages.indexOf(currentGuestCookie));
		reqIngredientsLabel.setIcon(currentReqIngredients);
		
		//쿠키 위치 설정
		cookieLabel.setBounds(620, 140, currentGuestCookie.getIconWidth(), currentGuestCookie.getIconHeight());
	}
	
	// 재료 버튼 생성
	private JButton createIngredientButton(ImageIcon ingredientImage, int x, int y) {
        JButton button = new JButton(ingredientImage);
        button.setBounds(x, y, ingredientImage.getIconWidth(), ingredientImage.getIconHeight());
        button.setOpaque(false);
        button.setContentAreaFilled(false); // 버튼 내용 영역 채우기를 비활성화
        button.setBorderPainted(false); // 버튼 테두리를 비활성화
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIngredients.size() < REQUIRED_INGREDIENTS) {
                    selectedIngredients.add(ingredientImage);
                    updateSelectedIngredientsLabel();
                    if (selectedIngredients.size() == REQUIRED_INGREDIENTS) {
                        checkCookieCompletion();
                    }
                }
            }
        });
        add(button);
        return button;
    }
	
	private void updateSelectedIngredientsLabel() {
		//이전에 추가된 모든 이미지 삭제
		selectedIngredientsPanel.removeAll();
	    
	    // 새로운 이미지 추가
	    int x = 0;
	    for(ImageIcon ingredient : selectedIngredients) {
	        JButton ingredientButton = new JButton(ingredient);
	        ingredientButton.setBounds(x, 0, ingredient.getIconWidth(), ingredient.getIconHeight());
	        ingredientButton.setContentAreaFilled(false); // 배경 투명 설정
	        selectedIngredientsPanel.add(ingredientButton);
	        x += ingredient.getIconWidth() + 5; // 재료 사이에 간격을 주기 위해 5px 추가
	    }
	    
	    // 패널 다시 그리기
	    selectedIngredientsPanel.revalidate();
	    selectedIngredientsPanel.repaint();
	    
	}
	
	private void startGame() {
		//타이머 리셋
		timeRemaining = GAME_TIME_LIMIT;
		isGameRunning = true;
		score = 0;
		updateTimerLabel();
		updateScoreLabel();
		
		//카운트 다운 시작
		new Thread(() -> {
			while(timeRemaining > 0) {
				try {
					Thread.sleep(1000);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				timeRemaining--;
				updateTimerLabel();
			}
			//게임 오버
			GameOver();
		}).start();
	}
	
	//타이머 설정
	private void updateTimerLabel() {
		timerLabel.setText("TIME : " + timeRemaining);
		timerLabel.setFont(new Font("Serif", Font.BOLD, 17));
		timerLabel.setForeground(Color.DARK_GRAY);
	}
	
	private void updateScoreLabel() {
		scoreLabel.setText("SCORE : "+ score);
		scoreLabel.setFont(new Font("Serif", Font.BOLD, 17));
		scoreLabel.setForeground(Color.DARK_GRAY);
	}
	
	private void GameOver() {
		isGameRunning = false;
		main.showMiniGameEndPanel(score);
		resetGame();
	}
	
	private void resetGame() {
	    // 기존 타이머 스레드가 실행 중이면 중단
	    if (timerThread != null && timerThread.isAlive()) {
	        timerThread.interrupt();
	    }
		isGameRunning = false;
		timeRemaining = GAME_TIME_LIMIT;
		score = 0;
		updateTimerLabel();
		updateScoreLabel();
		selectedIngredients.clear();
		updateSelectedIngredientsLabel();
		showRandomGuest();
		showRandomCookie();
	}
	
	
	
	//쿠키 완성 검증
	private void checkCookieCompletion() {
		boolean isCookieComplete = false;
		
		//현재 선택된 재료 -> 리스트로 변환
	      List<Integer> selectedIngredientIndexes = new ArrayList<>();
	        for (ImageIcon selectedIngredient : selectedIngredients) {
	            if (selectedIngredient.equals(ing1.getIcon())) {
	                selectedIngredientIndexes.add(1);
	            } else if (selectedIngredient.equals(ing2.getIcon())) {
	                selectedIngredientIndexes.add(2);
	            } else if (selectedIngredient.equals(ing3.getIcon())) {
	                selectedIngredientIndexes.add(3);
	            } else if (selectedIngredient.equals(ing4.getIcon())) {
	                selectedIngredientIndexes.add(4);
	            } else if (selectedIngredient.equals(ing5.getIcon())) {
	                selectedIngredientIndexes.add(5);
	            }
	        }

	        // 현재 손님이 원하는 쿠키와 선택된 재료 비교
	        if (currentGuestCookie == cookieImages.get(0)) {
	            if (selectedIngredientIndexes.containsAll(cookieIngredients.get(0))) {
	                isCookieComplete = true;
	            }
	        } else if (currentGuestCookie == cookieImages.get(1)) {
	            if (selectedIngredientIndexes.containsAll(cookieIngredients.get(1))) {
	                isCookieComplete = true;
	            }
	        } else if (currentGuestCookie == cookieImages.get(2)) {
	            if (selectedIngredientIndexes.containsAll(cookieIngredients.get(2))) {
	                isCookieComplete = true;
	            }
	        }

	        if (isCookieComplete) {
	            JOptionPane.showMessageDialog(this, "쿠키를 만들었습니다!", "완성", JOptionPane.INFORMATION_MESSAGE);
	            score++;
	            scoreLabel.setText("점수: " + score);
	        } else {
	            JOptionPane.showMessageDialog(this, "쿠키를 만들 수 없습니다.", "실패", JOptionPane.ERROR_MESSAGE);
	            score--;
	            scoreLabel.setText("점수: " + score);
	        }

	        selectedIngredients.clear();
	        updateSelectedIngredientsLabel();
	        showRandomGuest();
	        showRandomCookie();
	        updateScoreLabel();
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(backgroundImage != null) {
			Image img = backgroundImage.getImage();
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == backButton) {
			System.out.println("메인화면으로 나갑니다.");
			main.returnToStart();
			resetGame();
			main.showSelectPanel();
		}	
	}
}



