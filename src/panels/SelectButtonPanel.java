
package panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SelectButtonPanel extends JPanel {
	private ImageIcon Select1p = new ImageIcon("img/select/Select1p.png"); // 1p 아이콘
	private ImageIcon Select2p = new ImageIcon("img/select/Select2p.png"); // 2p 아이콘
    
	private JButton btn1;
	private JButton btn2;
    
    public SelectButtonPanel(Object o) {
		
    	//버튼 생성
    	btn1 = new JButton(Select1p); // 1p 버튼 생성
    	btn1.setName("btn1");
    	btn1.addMouseListener((MouseListener) o); // Main의 리스너를 추가
    	btn1.setBounds(60, 334, 250, 71);  // 버튼 위치 및 크기 설정
    	add(btn1); // 패널에 버튼 추가
        btn1.setBorderPainted(false);
        btn1.setContentAreaFilled(false);
        btn1.setFocusPainted(false);

        btn2 = new JButton(Select2p); // 2p 버튼 생성
        btn2.setName("btn2");
        btn2.addMouseListener((MouseListener) o); // Main의 리스너를 추가
        btn2.setBounds(474, 334, 250, 71);  // 버튼 위치 및 크기 설정
        add(btn2); // 패널에 버튼 추가
        btn2.setBorderPainted(false);
        btn2.setContentAreaFilled(false);
        btn2.setFocusPainted(false);

      	
    	// 배경
		JLabel selectBBg = new JLabel("");
		selectBBg.setForeground(Color.ORANGE);
		selectBBg.setHorizontalAlignment(SwingConstants.CENTER);
		selectBBg.setIcon(new ImageIcon("img/select/selectBg.png"));
		selectBBg.setBounds(0, 0, 784, 461);
		add(selectBBg); 
		
        
    }
}