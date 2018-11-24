package gameShooter;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet {
	private int playerNumber;
	private double dir;
	private int x;
	private int y;
	private double xMove;
	private double yMove;
	private int size;
	private int damage;
	
	public Bullet(int xStart,int yStart, double xMove, double yMove, int playerNum)
	{
		playerNumber = playerNum;
		x = xStart;
		y = yStart;
		this.xMove = xMove;
		this.yMove = yMove;
		size = 5;
		damage = 10;
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
	
	public double getDir() {
		return dir;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void move() {
		x += xMove*2;
		y += yMove*2;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.CYAN);
		g.drawOval(x-(size/2), y-(size/2), size, size);
	}
	
	public boolean collidesWith(Enemy e) {
		//find distance between bullet and enemy
		double yDist = e.getY()-this.getY();
		double xDist = e.getX()-this.getX();
		double dist = Math.sqrt(yDist*yDist + xDist*xDist);
		
		//take abs value of dist
		if (dist <0) {
			dist = -dist;
		}
		
		//check if that distance is less than the added radiuses
		if (dist <= (e.getSize()/2 + this.getSize()/2)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean collidesWith(Player p) {
		if(playerNumber == p.getPlayerNumber()) {
			return false;
		}
		//find distance between bullet and player
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
}
