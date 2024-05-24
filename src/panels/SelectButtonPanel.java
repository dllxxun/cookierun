package panels;

import javax.swing.*;

import main.Main;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SelectButtonPanel extends JPanel {
    public SelectButtonPanel(Object o) {
    	ImageIcon Select1p = new ImageIcon("img/selectBg/Select1p.png"); // 1p 아이콘
        ImageIcon Select2p = new ImageIcon("img/selectBg/Select2p.png"); // 2p 아이콘
        
        JButton btn1 = new JButton(Select1p); // 1p 버튼 생성
        JButton btn2 = new JButton(Select2p); // 2p 버튼 생성
    	
    	setLayout(new BorderLayout()); // BorderLayout으로 설정
    	
    	// 배경 이미지가 있는 패널 생성
        BackgroundPanel backgroundPanel = new BackgroundPanel("ButtonImg/selectBg.png");
        backgroundPanel.setLayout(new FlowLayout()); // 배경 패널의 레이아웃을 FlowLayout으로 설정

        // 패널에 버튼 추가
        backgroundPanel.add(btn1);
        backgroundPanel.add(btn2);

        add(backgroundPanel, BorderLayout.CENTER); // 배경 패널을 중앙에 추가    
    }

	
	// 배경 이미지를 그리기 위한 커스텀 패널
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String filePath) {
            try {
                backgroundImage = ImageIO.read(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}



