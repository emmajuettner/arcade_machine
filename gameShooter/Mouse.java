package gameShooter;

public class Mouse {
	private int x;
	private int y;
	private double xMove;
	private double yMove;
	private int speed;
	
	public Mouse(int xStart, int yStart) {
		x = xStart;
		y = yStart;
		speed = 7;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setSpeed(int s) {
		speed = s;
	}
	
	public void move (int moveX, int moveY) {
		//note: arguments should be 1, -1, or 0
		xMove = speed*moveX;
		yMove = speed*moveY;
		x += xMove;
		y += yMove;
	}
}
