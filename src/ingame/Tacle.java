package ingame;

import java.awt.Image;

public class Tacle {
    
    private Image image; // 장애물 이미지
    
    // 장애물의 좌표와 넓이 높이
    private int x;
    private int y;
    private int width;
    private int height;
    
    // 장애물 상태
    private int state;

    // 기본 생성자
    public Tacle() {}

    // 모든 필드를 받는 생성자
    public Tacle(Image image, int x, int y, int width, int height, int state) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.state = state;
    }

    // Getter and Setter methods
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}