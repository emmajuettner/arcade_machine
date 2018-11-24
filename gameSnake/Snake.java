package gameSnake;

import java.awt.*;
import java.util.ArrayList;
import static java.lang.Math.*;

import multigame.*;

/**
 * Pong game
 */

public class Snake implements Game {

  private KeyHandler kh;
  private boolean gameOver;
  private GameState gameState;
  private MultiGame mg;


  /**
   * A Game class must have only one constructor and it must have exactly one
   * MultiGame parameter.<br>
   * Initialize all instance variables.
   */
  public Snake(MultiGame mg) {

    kh = mg.getKeyHandler();

    this.mg = mg;
    gameState = new GameState(mg);
    gameOver = false;

    initRound();
  }

  /**
   * Called at the beginning of each round to init appropriate variables.
   */
  public void initRound() {
    // invoked at the beginning of every round


    // sound.play(4, true);

  }

  /**
   * Required method for implementing Game interface .. gameUpdate must
   * eventually set gameOver to false
   */
  public void gameUpdate() {
    gameState.tick();
    for (int i = 1; i <= 3; i++) {// update 3 times for every call to update
    	
    }
    
    
      if (gameState.inState(gameState.READY)) {
        
          initRound();
      }


      else if (gameState.inState(gameState.PLAYING)) {
    	int lives = 0;
      	
      	if (lives < 1)
      	gameState.toState(gameState.DEAD);
         
      }
      else if (gameState.inState(gameState.DEAD)) {
    	  if (1 < 3) {
    		  gameOver = true;
    		  return;
    	  }
    	  
      }
      // end for loop .. extra updates
  }

  /**
   * Required method for implementing Game interface.. renders all Game elements
   * to the Graphics2D variable
   */
  public void gameRender(Graphics2D g) {
    // clear the screen
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, mg.pWidth(), mg.pHeight());

    
    if (gameState.inState(gameState.READY)) {
      g.setColor(Color.WHITE);
      g.drawString("READY!", 300, 300);
    }

    else if (gameState.inState(gameState.PLAYING)) {
    	
    }

    else if (gameState.inState(gameState.DEAD)) {
     
    }
    // render ship in all gameStates
    // ship.render(g);

  }



  /**
   * Required method for implementing Game interface
   */
  public boolean isGameOver() {
    return gameOver;
  }

}