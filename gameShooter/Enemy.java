package gameShooter;

import java.awt.Color;
import java.awt.Graphics;

public class Enemy {
	
	private int dir;
	private int x;
	private int y;
	private int size;
	private int speed;
	private int health;
	private int points;
	private int damage;
	private int sizeChange;
	
	public Enemy() {
		//set starting values based on current difficulty level
		size = 40 + ((int)Math.sqrt(Shooter.difficultyLevel * 5));
		speed = 3 + ((int)Math.sqrt(Shooter.difficultyLevel) * 5);
		health = 50 + (Shooter.difficultyLevel * 2);
		points = 100 + (Shooter.difficultyLevel * 5);
		damage = 20 + (Shooter.difficultyLevel * 2);
		sizeChange = 5;
		
		//randomly set start position on one of the edges
		if ((int)(Math.random()*2) == 0) {
			if ((int)(Math.random()*2) == 0) {
				x = 0;
				y = (int)(Math.random()*Shooter.WINDOW_HEIGHT);
				dir = Shooter.RIGHT;
			}
			else {
				x = (int)Shooter.WINDOW_WIDTH;
				y = (int)(Math.random()*Shooter.WINDOW_HEIGHT);
				dir = Shooter.LEFT;
			}
		}
		else {
			if ((int)Math.random()*2 == 0) {
				x = (int)(Math.random()*Shooter.WINDOW_WIDTH);
				y = 0;
				dir = Shooter.DOWN;
			}
			else {
				x = (int)(Math.random()*Shooter.WINDOW_WIDTH);
				y = (int) Shooter.WINDOW_WIDTH;
				dir = Shooter.UP;
			}
		}
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
	
	public int getDir() {
		return dir;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void reduceHealth(int d) {
		health -= d;
		size -= sizeChange;
	}
	
	//to do: decide how best to implement damage based on collisions
	public boolean collidesWith(Player p) {
		//find distance between player and enemy
		double yDist = p.getY()-this.getY();
		double xDist = p.getX()-this.getX();
		double dist = Math.sqrt(yDist*yDist + xDist*xDist);
		
		//take abs value of dist
		if (dist <0) {
			dist = -dist;
		}
		
		//check if that distance is less than the added radiuses
		if (dist <= (p.getSize()/2 + this.getSize()/2)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.PINK);
		g.drawOval(x-(size/2), y-(size/2), size, size);
	}

}