package ingame;

import java.awt.Image;

public class Jelly {
    private Image image; // 젤리 이미지
    
    // 젤리들의 좌표와 크기
    private int x;
    private int y;
    private int width;
    private int height;
    
    // 젤리의 투명도 0투명 255불투명
    private int alpha;
    
    // 젤리의 점수
    private int score;

    // 기본 생성자
    public Jelly() {}

    // 모든 필드를 받는 생성자
    public Jelly(Image image, int x, int y, int width, int height, int alpha, int score) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.alpha = alpha;
        this.score = score;
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

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}