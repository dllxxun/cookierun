package minigame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Main;

public class DescriptionPanel extends JPanel { 
	private ImageIcon descriptionImage;
	
	public DescriptionPanel(Main main) {
		setLayout(null);
		
		//배경 이미지 로드
		descriptionImage = new ImageIcon("img/minigame/description.png");
		
		// 뒤로 가기 버튼 이미지 로드
        ImageIcon goBackButtonImage = new ImageIcon("img/minigame/backtomainBtn.png");
        goBackButtonImage = resizeIcon(goBackButtonImage, 700, 360);
        JLabel goBackLabel = new JLabel(goBackButtonImage);
        goBackLabel.setBounds(0, 280, goBackButtonImage.getIconWidth(), goBackButtonImage.getIconHeight());
        
        // 마우스 리스너 추가
        goBackLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                main.returnToStart();
            }
        });
        
        // 패널에 버튼 추가
        add(goBackLabel);	
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// 배경 이미지 그리기
        Image img = descriptionImage.getImage();
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}
	
	// 이미지 아이콘 크기 조정 메서드
    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }
}
