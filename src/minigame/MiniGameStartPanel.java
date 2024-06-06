package minigame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Main;

public class MiniGameStartPanel extends JPanel {
    private ImageIcon minigameintroImage;

    public MiniGameStartPanel(Main main) {
        setLayout(null);

        // 배경 이미지 로드
        minigameintroImage = new ImageIcon("img/minigame/minigameintro.png");

        // 버튼 이미지 로드
        ImageIcon startButtonImage = new ImageIcon("img/minigame/minigamestartBtn.png");
        ImageIcon descriptionButtonImage = new ImageIcon("img/minigame/descriptionBtn.png");
        ImageIcon backButtonImage = new ImageIcon("img/minigame/backBtn.png");

        // 버튼 이미지 크기 조정
        startButtonImage = resizeIcon(startButtonImage, 600, 345);
        descriptionButtonImage = resizeIcon(descriptionButtonImage, 600, 345);
        backButtonImage = resizeIcon(backButtonImage, 50, 50);

        // 이미지 아이콘을 사용하여 JLabel
        JLabel startLabel = new JLabel(startButtonImage);
        JLabel descriptionLabel = new JLabel(descriptionButtonImage);
        JLabel backLabel = new JLabel(backButtonImage);

        // JLabel의 위치와 크기 설정
        startLabel.setBounds(320, 155, startButtonImage.getIconWidth(), startButtonImage.getIconHeight());
        descriptionLabel.setBounds(0, 239, descriptionButtonImage.getIconWidth(), descriptionButtonImage.getIconHeight());
        backLabel.setBounds(15, 15, backButtonImage.getIconWidth(), backButtonImage.getIconHeight());
        
        // 마우스 리스너 추가
        startLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                main.showCookieFactoryPanel();
            }
        });
        
        descriptionLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                main.showDescriptionPanel();
            }
        });
        
        backLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                main.showSelectbuttonPanel();
            }
        });
        
        // 패널에 버튼 추가
        add(startLabel);
        add(descriptionLabel);
        add(backLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 배경 이미지 그리기
        Image img = minigameintroImage.getImage();
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }

    // 이미지 아이콘 크기 조정 메서드
    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }
}