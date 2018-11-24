package gameShooter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JFrame;

import gameSnake.GameState;
import multigame.*;

/*
 * Controls used in the game:
 * Shift --> speed boost
 * Esc --> exit game
 * Space --> shoot
 * Mouse position --> player movement
 */

public class Shooter extends GameEngine implements multigame.Game {
	//multigame variables
	private KeyHandler kh;
	private boolean gameOver;
	private GameState gameState;
	private MultiGame mg;
	
	protected int gameOverTimer = 0; //sets timer for game over screen to be displayed
	
	//screen dimensions
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	protected static final double WINDOW_WIDTH = screenSize.getWidth();
	protected static final double WINDOW_HEIGHT = screenSize.getHeight();
	
	//directions (for the enemy)
	protected static final int UP = 0;
	protected static final int RIGHT = 1;
	protected static final int DOWN = 2;
	protected static final int LEFT = 3;
	
	//sounds
	protected String bg_music_file = "romariogrande_alien-dream.wav";
	protected String[] sound_files = {bg_music_file};
	protected Sound sounds = new Sound();
	
	//save data
	protected String save_data_file = "save_data.txt";
	SaveFileReader save_data = new SaveFileReader(save_data_file);
	
	//player variables
	Mouse mouse1 = new Mouse((int)WINDOW_WIDTH/2,(int)WINDOW_HEIGHT/2);
	Player player1 = new Player((int)WINDOW_WIDTH/2,(int)WINDOW_HEIGHT/2,1);
	
	//Mouse mouse2 = new Mouse((int)WINDOW_WIDTH/2,(int)WINDOW_HEIGHT/2);
	//Player player2 = new Player((int)WINDOW_WIDTH/2,(int)WINDOW_HEIGHT/2,2);
	
	//bullet variables
	private ArrayList<Bullet> bullets1 = new ArrayList<Bullet>();
	//private ArrayList<Bullet> bullets2 = new ArrayList<Bullet>();
	
	//enemy variables
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	protected static int difficultyLevel = 1;
	private int enemyIntervalTimer = 0;
	private int enemyInterval = 100;
	
	public Shooter(MultiGame mg) {
		kh = mg.getKeyHandler();

	    this.mg = mg;
	    gameState = new GameState(mg);
	    gameOver = false;

	    initRound();
	}
	
	/*
	 * Events that occur when the game starts
	 */
	
	public void initRound() {
		/*
		setTitle("Game Engine");
		
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		setVisible(true);
		
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		insets = getInsets();
		
		backBuffer = new BufferedImage((int)WINDOW_WIDTH, (int)WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);
		input = new InputHandler(this);
		*/
		//start the background music
		sounds.loadSoundFiles(sound_files);
		sounds.play(0, true);
		
		gameState.toState(gameState.PLAYING);
	}
	
	/*
	 * Events that occur every instant
	 */
	
	public void gameUpdate() {
		gameState.tick();
		
		if (gameState.inState(gameState.READY)) {
	        System.out.println("Game Ready");
	        initRound();
	    }
		
		else if (gameState.inState(gameState.PLAYING)) {
			System.out.println("Game Playing");
			//advance to the game over screen if the player runs out of lives
	      	if (player1.getLives() <= 0) {
	      		gameState.toState(gameState.DEAD);
	      	}
	      	/*
	      	if (player2.getLives() <= 0) {
	      		gameState.toState(gameState.DEAD);
	      	}
	      	 */
	      	
			
			/*
			 * Player Instructions
			 */
			
	      	/*
	      	 * Controls used in the game:
	      	 * Btn 1 --> speed boost
	      	 * Btn 2 --> shoot
	      	 * Joystick position --> mouse movement
	      	 * Mouse position --> player movement
	      	 */
	      	
			//move mouse1 according to joystick movement
	      	if(kh.isUpPressed(1)) {
	      		mouse1.move(0, -1);
	      	}
	      	if(kh.isDownPressed(1)) {
	      		mouse1.move(0, 1);
	      	}
	      	if(kh.isLeftPressed(1)) {
	      		mouse1.move(-1, 0);
	      	}
	      	if(kh.isRightPressed(1)) {
	      		mouse1.move(1, 0);
	      	}
		    
	      	/*
	      //move mouse2 according to joystick movement
	      	if(kh.isUpPressed(2)) {
	      		mouse2.move(0, -1);
	      	}
	      	if(kh.isDownPressed(2)) {
	      		mouse2.move(0, 1);
	      	}
	      	if(kh.isLeftPressed(2)) {
	      		mouse2.move(-1, 0);
	      	}
	      	if(kh.isRightPressed(2)) {
	      		mouse2.move(1, 0);
	      	}
	      	*/
	      	
	      	//move player1 according to mouse movement
	      	int mouseX = mouse1.getX();
		    int mouseY = mouse1.getY();
		    player1.move(mouseX,mouseY);
	      	
		    /*
		    //move player2 according to mouse movement
	      	int mouseX = mouse2.getX();
		    int mouseY = mouse2.getY();
		    player2.move(mouseX,mouseY);
		    */
		    
		    //if player is invulnerable, count down the invulnerableTimer
		    if (player1.isInvulnerable()) {
		    	player1.reduceInvulnerableTimer();
		    }
		    /*
		    if (player2.isInvulnerable()) {
		    	player2.reduceInvulnerableTimer();
		    }
		     */
		    
		    //if Btn1 is pressed, increase player1's speed
		    if (kh.isBtn1Pressed(1)) {
		    	mouse1.setSpeed(14);
				player1.setSpeed(10);
			}
		    else {
		    	mouse1.setSpeed(7);
		    	player1.setSpeed(5);
		    }
		    
		    /*
		    //if Btn1 is pressed, increase player2's speed
		    if (kh.isBtn1Pressed(2)) {
		    	mouse2.setSpeed(14);
				player2.setSpeed(10);
			}
		    else {
		    	mouse2.setSpeed(7);
		    	player2.setSpeed(5);
		    }
		    */
		    
			/*
			 * Bullet Instructions
			 */
			
			//create bullet when Btn2 is pressed
			if (kh.isBtn2Pressed(1)) {
				Bullet newBullet = new Bullet(player1.getX(),player1.getY(),player1.getXMove(),player1.getYMove(),1);
				bullets1.add(newBullet);
			}
			/*
			if (kh.isBtn2Pressed(2)) {
				Bullet newBullet = new Bullet(player2.getX(),player2.getY(),player2.getXMove(),player2.getYMove(),2);
				bullets2.add(newBullet);
			}
			*/
			
			//control bullet movement for player 1 & delete once offscreen
			for (int i = 0; i<bullets1.size(); i++) {
				Bullet currentBullet = bullets1.get(i);
				
				//move bullets
				currentBullet.move();
				
				//delete offscreen bullets
				if (currentBullet.getX() < 0 - currentBullet.getSize()
						|| currentBullet.getX() > WINDOW_WIDTH
						|| currentBullet.getY() < 0 - currentBullet.getSize()
						|| currentBullet.getY() > WINDOW_HEIGHT) {
					bullets1.remove(currentBullet);
				}
				
				//check if bullets collide with enemies
				for (int j = 0; j<enemies.size(); j++) {
					Enemy currentEnemy = (Enemy)enemies.get(j);
					if (currentBullet.collidesWith(currentEnemy)) {
						//damage the enemy and increase the player's score
						currentEnemy.reduceHealth(currentBullet.getDamage());
						player1.increaseScore(currentEnemy.getPoints());
						//increase the difficulty level as score increases
						//if(player1.getScore() > player2.getScore()) {
							difficultyLevel = 1 + (int)Math.sqrt(player1.getScore() / 1000);
						//}
					}
				}
				
				//check if bullets collide with players
				/*
				if(currentBullet.collidesWith(player2)) {
					player2.reduceHealth(currentBullet.getDamage());
					player1.increaseScore(currentBullet.getDamage());
					//increase the difficulty level as score increases
					if(player1.getScore() > player2.getScore()) {
						difficultyLevel = 1 + (int)Math.sqrt(player1.getScore() / 1000);
					}
				}
				*/
				
				for (int j = 0; j<enemies.size(); j++) {
					Enemy currentEnemy = (Enemy)enemies.get(j);
					if (currentBullet.collidesWith(currentEnemy)) {
						//damage the enemy and increase the player's score
						currentEnemy.reduceHealth(currentBullet.getDamage());
						player1.increaseScore(currentEnemy.getPoints());
						//increase the difficulty level as score increases
						//if(player1.getScore() > player2.getScore()) {
							difficultyLevel = 1 + (int)Math.sqrt(player1.getScore() / 1000);
						//}
					}
				}
			}
			
			/*
			//control bullet movement for player 2 & delete once offscreen
			for (int i = 0; i<bullets2.size(); i++) {
				Bullet currentBullet = bullets2.get(i);
				
				//move bullets
				currentBullet.move();
				
				//delete offscreen bullets
				if (currentBullet.getX() < 0 - currentBullet.getSize()
						|| currentBullet.getX() > WINDOW_WIDTH
						|| currentBullet.getY() < 0 - currentBullet.getSize()
						|| currentBullet.getY() > WINDOW_HEIGHT) {
					bullets.remove(currentBullet);
				}
				
				//check if bullets collide with enemies
				for (int j = 0; j<enemies.size(); j++) {
					Enemy currentEnemy = (Enemy)enemies.get(j);
					if (currentBullet.collidesWith(currentEnemy)) {
						//damage the enemy and increase the player's score
						currentEnemy.reduceHealth(currentBullet.getDamage());
						player2.increaseScore(currentEnemy.getPoints());
						//increase the difficulty level as score increases
						if(player2.getScore() > player1.getScore()) {
							difficultyLevel = 1 + (int)Math.sqrt(player2.getScore() / 1000);
						}
					}
				}
				
				//check if bullets collide with players
				if(currentBullet.collidesWith(player1)) {
					player1.reduceHealth(currentBullet.getDamage());
					player2.increaseScore(currentBullet.getDamage());
					//increase the difficulty level as score increases
					if(player2.getScore() > player1.getScore()) {
						difficultyLevel = 1 + (int)Math.sqrt(player2.getScore() / 1000);
					}
				}
				
			}
			*/
			
			/*
			 * Enemy Instructions
			 */
					
			//every instant, increase the enemy timers by the difficultyLevel
			enemyIntervalTimer+=difficultyLevel;
			
			//every enemyInterval, create a new enemy
			if (enemyIntervalTimer > enemyInterval) {
				Enemy newEnemy = new Enemy();
				enemies.add(newEnemy);
				enemyIntervalTimer = 0;
			}
			
			//control enemy movement & delete once offscreen
			for (int i = 0; i<enemies.size(); i++) {
				Enemy currentEnemy = (Enemy)enemies.get(i);
				
				//move enemies
				if (currentEnemy.getDir() == RIGHT) {
					currentEnemy.setX(currentEnemy.getX()+currentEnemy.getSpeed());
				}
				if (currentEnemy.getDir() == LEFT) {
					currentEnemy.setX(currentEnemy.getX()-currentEnemy.getSpeed());
				}
				if (currentEnemy.getDir() == UP) {
					currentEnemy.setY(currentEnemy.getY()-currentEnemy.getSpeed());
				}
				if (currentEnemy.getDir() == DOWN) {
					currentEnemy.setY(currentEnemy.getY()+currentEnemy.getSpeed());
				}
				
				//delete offscreen enemies
				if (currentEnemy.getX() < 0 - currentEnemy.getSize()
						|| currentEnemy.getX() > WINDOW_WIDTH
						|| currentEnemy.getY() < 0 - currentEnemy.getSize()
						|| currentEnemy.getY() > WINDOW_HEIGHT) {
					enemies.remove(currentEnemy);
				}
				
				//check if enemies collide with the player
				if (currentEnemy.collidesWith(player1)) {
					if(!player1.isInvulnerable()) {
						player1.reduceHealth(currentEnemy.getDamage());
					}
				}
				/*
				if (currentEnemy.collidesWith(player2)) {
					if(!player2.isInvulnerable()) {
						player2.reduceHealth(currentEnemy.getDamage());
					}
				}
				*/
				
				//check if enemies are out of health
				if (currentEnemy.getHealth() <= 0) {
					enemies.remove(currentEnemy);
				}
			}	         
	    }
	    
		else if (gameState.inState(gameState.DEAD)) {
			System.out.println("Game Dead");
			
			sounds.stopAll();
			/*
			for(Bullet b: bullets) {
				bullets.remove(b);
			}
			for(Enemy e: enemies) {
				enemies.remove(e);
			}*/
			
			//save a new high score if appropriate
			if (player1.getScore() > save_data.getHighScore()) {
				save_data.setHighScore(player1.getScore(),save_data_file);
			}
			/*
			if (player2.getScore() > save_data.getHighScore()) {
				save_data.setHighScore(player2.getScore(),save_data_file);
			}
			 */
			
			if (gameOverTimer >= 100) {
	    		gameOver = true;
	    		return;
	    	  }
			
			gameOverTimer++;
	    	  
	    }
		
		/*
		//if esc key is pressed, end the game
		if (input.isKeyDown(KeyEvent.VK_ESCAPE)) {
			System.exit(0);
		}
		*/
		
		
	}

	public void gameRender(Graphics2D g) {
	    if (gameState.inState(gameState.READY)) {
	    	// clear the screen
		    g.setColor(Color.BLACK);
		    g.fillRect(0, 0, mg.pWidth(), mg.pHeight());
		    
		    g.setColor(Color.WHITE);
		    g.drawString("READY!", 300, 300);
	    }

	    else if (gameState.inState(gameState.PLAYING)) {
	    	//background
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, (int)WINDOW_WIDTH, (int)WINDOW_HEIGHT);

		
			//player
			player1.draw(g);
			//player2.draw(g);
			
			//health bar
			player1.drawHealth(g);
			//player2.drawHealth(g);
			
			//lives
			player1.drawLives(g);
			//player2.drawLives(g);
			
			//score
			player1.drawScore(g);
			//player2.drawScore(g);
	
			//bullets
			for (int i = 0; i < bullets1.size(); i++) {
				Bullet currentBullet = (Bullet)bullets1.get(i);
				currentBullet.draw(g);
			}
			/*
			for (int i = 0; i < bullets2.size(); i++) {
				Bullet currentBullet = (Bullet)bullets2.get(i);
				currentBullet.draw(g);
			}
			*/

			//enemies
			for (int i = 0; i < enemies.size(); i++) {
				Enemy currentEnemy = (Enemy)enemies.get(i);
				currentEnemy.draw(g);
			}
		

	    }

	    else if (gameState.inState(gameState.DEAD)) {
	    	g.setColor(Color.BLACK);
			g.fillRect(0, 0, (int)WINDOW_WIDTH, (int)WINDOW_HEIGHT);
	    	
	    	g.setColor(Color.WHITE);
			g.setFont(new Font("TimesRoman", Font.PLAIN, (int)Shooter.WINDOW_HEIGHT/10));
			
			//calculate the display widths & heights of each string to be printed (so we can center them correctly)
			int width1 = g.getFontMetrics().stringWidth("GAME OVER");
			int width2 = g.getFontMetrics().stringWidth("Final Score: " + player1.getScore());
			//int width2 = g.getFontMetrics().stringWidth("Player 1: " + player2.getScore() + "Player 2: " + player1.getScore());
			int width3 = g.getFontMetrics().stringWidth("High Score: " + save_data.getHighScore());
			int width4 = g.getFontMetrics().stringWidth("Press ESC to Exit");
			int height = g.getFontMetrics().getHeight();
			
			g.drawString("GAME OVER", (int)(Shooter.WINDOW_WIDTH-width1)/2, (int)Shooter.WINDOW_HEIGHT/3);
			g.drawString("Final Score: " + player1.getScore(), (int)(Shooter.WINDOW_WIDTH-width2)/2, (int)Shooter.WINDOW_HEIGHT/3+height+20);
			//note: player 2 is left, player 1 is right, so their names get swapped here
			//g.drawString("Player 1: " + player2.getScore() + "Player 2: " + player1.getScore(), (int)(Shooter.WINDOW_WIDTH-width2)/2, (int)Shooter.WINDOW_HEIGHT/3+height+20);
			g.drawString("High Score: " + save_data.getHighScore(), (int)(Shooter.WINDOW_WIDTH-width3)/2, (int)Shooter.WINDOW_HEIGHT/3+2*height+40);
			g.drawString("Press ESC to Exit", (int)(Shooter.WINDOW_WIDTH-width4)/2, (int)Shooter.WINDOW_HEIGHT/3+3*height+60);
	    }
	}

	@Override
	public boolean isGameOver() {
		// TODO Auto-generated method stub
		return false;
	}
}