package minigame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

import main.Main;
import minigame.CookieFactory;

public class MiniGameEndPanel extends JPanel {
	private JLabel miniGameScoreLabel;
    private ImageIcon miniGameEndImage;

	
	public MiniGameEndPanel(Main main) {
		setLayout(null);
		
		//점수 라벨 추가
		miniGameScoreLabel = new JLabel("SCORE: ");
		miniGameScoreLabel.setBounds(345, 300, 300, 100); // 라벨 위치와 크기 설정
		miniGameScoreLabel.setFont(new Font("Serif", Font.BOLD, 100));
		miniGameScoreLabel.setForeground(Color.DARK_GRAY);
		add(miniGameScoreLabel);
		
		//패널 배경 설정
        miniGameEndImage = new ImageIcon("img/minigame/minigameendBg.png");

        
        // selectPanel로 돌아가기 버튼 이미지 로드
        ImageIcon backButtonImage = new ImageIcon("img/minigame/backBtn.png");
        
        // 버튼 이미지 크기 조정
        backButtonImage = resizeIcon(backButtonImage, 50, 50);
        
        // 이미지 아이콘을 사용하여 JLabel
        JLabel backLabel = new JLabel(backButtonImage);
        
        // JLabel의 위치와 크기 설정
        backLabel.setBounds(15, 15, backButtonImage.getIconWidth(), backButtonImage.getIconHeight());
        
        // 마우스 리스너 추가
        backLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                main.showSelectPanel();
            }
        });
        
        add(backLabel);
	}
	
	public void setMiniGameScoreLabel(int score) {
		miniGameScoreLabel.setText(" "+score);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		 // 배경 이미지 그리기
        Image img = miniGameEndImage.getImage();
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}

	 // 이미지 아이콘 크기 조정 메서드
    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }
}
