package gameShooter2Player;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Player {	
	private int playerNumber;
	private int size;
	private int x;
	private int y;
	private double xMove;
	private double yMove;
	private int speed;
	private int health;
	private int lives;
	private int score;
	private int sizeChange; //the amount the player's size changes when it takes damage
	private boolean invulnerable; //whether the player can take damage
	private int invulnerableTimer; //time until the player can take damage again
	
	public Player(int xStart, int yStart, int playerNum) {
		playerNumber = playerNum;
		x = xStart;
		y = yStart;
		xMove = 5;
		yMove = 0;
		size = 20;
		speed = 5;
		health = 100;
		lives = 3;
		score = 0;
		sizeChange = 4;
		invulnerable = false;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int newX) {
		x = newX;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int newY) {
		y = newY;
	}
	
	public double getXMove() {
		return xMove;
	}
	
	public int getPlayerNumber() {
		return playerNumber;
	}
	
	public double getYMove() {
		return yMove;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int s) {
		speed = s;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getLives() {
		return lives;
	}
	
	public void increaseScore(int points) {
		score += points;
	}
	
	public int getScore() {
		return score;
	}
	
	public boolean isInvulnerable() {
		return invulnerable;
	}
	
	public void setInvulnerable(boolean b) {
		invulnerable = b;
	}
	
	public void reduceInvulnerableTimer() {
		if (invulnerableTimer <= 0) {
			invulnerable = false;
		}
		else {
			invulnerableTimer -= 1;
		}
	}
	
	public void reduceHealth(int damage) {
		if(!invulnerable) {
			health -= damage;
			size -= sizeChange;
			invulnerable = true;
			invulnerableTimer = 20;
		}
		if (health <= 0) {
			lives -= 1;
			health = 100;
			size = 20;
		}
	}
	
	public void move(int targetX, int targetY) {
		/*
		//note: arguments should be 1, -1, or 0
		xMove = speed*moveX;
		yMove = speed*moveY;
		x += xMove;
		y += yMove;
		*/
		
		//move player towards mouse
		double deltaX = targetX - x;
		double deltaY = targetY - y;
		double dist = Math.sqrt(deltaX*deltaX + deltaY*deltaY);
		if (dist > 20) {
			double distRatio = speed / dist;
			xMove = deltaX * distRatio;
			yMove = deltaY * distRatio;
			x += xMove;
			y += yMove;
			//update direction (for shooting)
			//dir = Math.atan(deltaY/deltaX) + Math.PI;
		}
		
		//keep player from leaving screen
		if (x<0) {
			x=0;
		}
		if (x>Shooter2Player.WINDOW_WIDTH-size-1) {
			x=(int)Shooter2Player.WINDOW_WIDTH-size-1;
		}
		if (y<0) {
			y=0;
		}
		if (y>Shooter2Player.WINDOW_HEIGHT-size-1) {
			y=(int)Shooter2Player.WINDOW_HEIGHT-size-1;
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		
		//draw a direction indicator
		g.drawLine(x, y, (int)(x+xMove*5), (int)(y+yMove*5));
		
		//draw the player
		if(playerNumber == 1) { //right player
			g.setColor(Color.RED);
			g.fillOval(x-(size/2), y-(size/2), size, size);
		}
		if(playerNumber == 2) { //left player
			g.setColor(Color.CYAN);
			g.fillOval(x-(size/2), y-(size/2), size, size);
		}
	}
	
	public void drawHealth(Graphics g) {
		g.setColor(Color.RED);
		g.setFont(new Font("TimesRoman", Font.PLAIN, (int)Shooter2Player.WINDOW_HEIGHT/50));
		int health_width = g.getFontMetrics().stringWidth("Health: ");
		int lives_width = g.getFontMetrics().stringWidth("Lives: ");
		int height = g.getFontMetrics().getHeight();
		if(playerNumber == 1) { //right player
			g.drawString("Health: ", (int)Shooter2Player.WINDOW_WIDTH-10-health_width-100-10-lives_width-10-3*(height+5), 10+height);
			g.drawRect((int)Shooter2Player.WINDOW_WIDTH-100-10-lives_width-10-3*(height+5), 10, 100, height); //empty health box
			g.fillRect((int)Shooter2Player.WINDOW_WIDTH-100-10-lives_width-10-3*(height+5), 10, health, height); //fill in current health
		}
		if(playerNumber == 2) { //left player
			g.drawString("Health: ", 10, 10+height);
			g.drawRect(10 + health_width, 10, 100, height); //empty health box
			g.fillRect(10 + health_width, 10, health, height); //fill in current health
		}
	}
	
	public void drawLives(Graphics g) {
		g.setColor(Color.RED);
		g.setFont(new Font("TimesRoman", Font.PLAIN, (int)Shooter2Player.WINDOW_HEIGHT/50));
		int health_width = g.getFontMetrics().stringWidth("Health: ");
		int lives_width = g.getFontMetrics().stringWidth("Lives: ");
		int height = g.getFontMetrics().getHeight();
		
		if(playerNumber == 1) { //right player
			g.drawString("Lives: ", (int)Shooter2Player.WINDOW_WIDTH-lives_width-10-3*(height+5), 10+height);
			for (int i = 0; i < lives; i++) {
				g.fillOval((int)Shooter2Player.WINDOW_WIDTH-10-(3-i)*(height+5), 10, height, height);
			}
		}
		if(playerNumber == 2) { //left player
			g.drawString("Lives: ", 120 + health_width, 10+height);
			for (int i = 0; i < lives; i++) {
				g.fillOval(10+health_width+110+lives_width+10+i*(height+5), 10, height, height);
			}
		}
	}
	
	public void drawScore(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, (int)Shooter2Player.WINDOW_HEIGHT/50));
		int health_width = g.getFontMetrics().stringWidth("Health: ");
		int lives_width = g.getFontMetrics().stringWidth("Lives: ");
		int score_width = g.getFontMetrics().stringWidth("Score: " + score);
		int height = g.getFontMetrics().getHeight();
		
		if(playerNumber == 1) { //right player
			g.drawString("Score : " + score, (int)Shooter2Player.WINDOW_WIDTH-10-score_width-10, 10+height+height);
		}
		if(playerNumber == 2) { //left player
			g.drawString("Score : " + score, 10, 10+height+height);
		}
		
	}
}
