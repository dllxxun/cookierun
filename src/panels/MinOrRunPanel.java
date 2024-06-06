package panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import main.Main;

public class MinOrRunPanel extends JPanel {
    private ImageIcon minOrRunImage; // 배경 설정

    public MinOrRunPanel(Main main) {
        setLayout(null);

        // 배경 이미지 로드
        minOrRunImage = new ImageIcon("img/Objectimg/map1img/bg1.png");

        // 버튼 이미지 로드
        ImageIcon miniGameButtonImage = new ImageIcon("img/minigame/minigamestartBtn.png");
        ImageIcon cookieRunStartImage = new ImageIcon("img/minigame/descriptionBtn.png");

        // 버튼 이미지 크기 조정
        miniGameButtonImage = resizeIcon(miniGameButtonImage, 600, 345);
        cookieRunStartImage = resizeIcon(cookieRunStartImage, 600, 345);

        // 이미지 아이콘을 사용하여 JButton
        JButton minibtn = new JButton(miniGameButtonImage);
        JButton cookiebtn = new JButton(cookieRunStartImage);

        // JButton의 위치와 크기 설정
        minibtn.setBounds(320, 155, 600, 345);
        cookiebtn.setBounds(0, 239, 600, 345);

        // 버튼의 배경 및 테두리 제거
        minibtn.setBorderPainted(false);
        minibtn.setContentAreaFilled(false);
        cookiebtn.setBorderPainted(false);
        cookiebtn.setContentAreaFilled(false);

     // 액션 리스너 추가
        minibtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.showMiniGameIntro();
            }
        });

        cookiebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.showSelectbuttonPanel();
            }
        });

        // 패널에 버튼 추가
        add(minibtn);
        add(cookiebtn);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 배경 이미지 그리기
        Image img = minOrRunImage.getImage();
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }

    // 이미지 아이콘 크기 조정 메서드
    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }
}
