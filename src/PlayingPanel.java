import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import sun.applet.Main;

import java.util.Random;
import java.util.Scanner;
import java.net.*;

/**
 * Homework 13.2: This class creates the Panel in which Tetris is played.
 * 
 * @author Jackson Scroggs
 * @version 1.0 4/21/2012
 */

public class PlayingPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int WIDTH=300,HEIGHT=600;
	public int delay,currScore,currLevel;
	private Timer timer;
	private Random rand;
	private PlayingField field;
	private Piece aPiece,nextPiece,heldPiece;
	private Boolean gameOver=false,paused=false,canHold=true;
	private StatsPanel sPanel;
	
	/**
	 * Contructs the Playing Panel.
	 * 
	 * @param stats The Stats Panel that this panel interacts with.
	 */
	
	public PlayingPanel(StatsPanel stats){
		field=new PlayingField();
		rand=new Random();
		delay=600;
		currScore=0;
		currLevel=1;
		aPiece=newPiece();
		aPiece.predictDrop(field.getTiles());
		nextPiece=newPiece();
		heldPiece=null;
		sPanel = stats;
		sPanel.setPPanel(this);
		sPanel.setNextPiece(nextPiece.getName());
		sPanel.setHeldPiece(null);
		
		//This was intended to play the Tetris theme, but it cuts out and I didn't have time to fix it. If you have any ideas to fix I'd appreciate the input.
		
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setBackground(Color.black);
		
		addKeyListener(new MyKeys());
		setFocusable(true);
		timer=new Timer(delay, new TimerListener());
		timer.start();
	}
	
	/**
	 * Creates one of the seven pieces.
	 * 
	 * @return A random new Piece.
	 */
	public Piece newPiece(){
		int whichPiece=rand.nextInt(7);
		if(whichPiece==0){
			return (new LinePiece());
		}
		else if(whichPiece==1){
			return (new SquarePiece());
		}
		else if(whichPiece==2){
			return (new S_Piece());
		}
		else if(whichPiece==3){
			return (new Z_Piece());
		}
		else if(whichPiece==4){
			return (new J_Piece());
		}
		else if(whichPiece==5){
			return (new L_Piece());
		}
		else{
			return (new T_Piece());
		}
	}
	
	/**
	 * Draws the PlayingField.
	 * 
	 * @param g Graphical input
	 */
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		PlayingField.draw(g, new Dimension(getSize().width/10,getSize().height/20));
		if(aPiece!=null){
			aPiece.draw(g, new Dimension(getSize().width/10,getSize().height/20));
		}
		if (paused){
		    g.setFont(new Font("Arial",Font.BOLD,getSize().width/7));
			g.setColor(Color.white);
		    g.drawString("-PAUSED-",getSize().width/6,getSize().height/2);
		}
		if(gameOver){
		    g.setFont(new Font("Arial",Font.BOLD,getSize().width/7));
			g.setColor(Color.white);
		    g.drawString("GAME OVER",getSize().width/12,getSize().height/2);
		}
	}
	
	/**
	 * Adds the active Piece to the field.
	 */
	public void settle(){
		int[][][] model=aPiece.getModel();
		for (int i=0;i<4;i++){
			for (int j=0;j<4;j++){
				if(model[aPiece.getRotation()][i][j]==1){
					field.fillTile(aPiece.getRow()+i,aPiece.getCol()+j,aPiece.getColor());
				}
			}
		}
		field.setTotScore(field.getTotScore()+aPiece.getRow());
		canHold=true;
	}
	
	/**
	 * Drops the rows above those clears.
	 * 
	 * @param clears List of boolean. The rows that cleared are true.
	 */
	
	public void dropRows(boolean[] clears){
		Color[][] tiles=field.getTiles();
		for(int a=0;a<clears.length;a++){
			if(clears[a]==true){
				for (int i=0;0<(a-i);i++){
					for (int j=0;j<PlayingField.COLS;j++){
						try{
							field.fillTile(a-i, j, tiles[a-i-1][j]);
							field.fillTile(a-i-1, j, null);
						}
						catch(Exception e){
						}
					}
				}
			}
		}
	}
	
	/**
	 * Homework 13.2.1: This class creates the keyListener.
	 * 
	 * @author Jackson Scroggs
	 * @version 1.0 4/21/2012
	 */
	
	public class MyKeys implements KeyListener{
		/**
		 * Performs the specified actions when keys are pressed.
		 * 
		 * @param e Event that triggered the listener.
		 */
		public void keyPressed(KeyEvent e) {
			if(!gameOver){
				if(!paused){
					if (e.getKeyCode()==KeyEvent.VK_LEFT){
						if(aPiece.noCollision(aPiece.getRotation(),aPiece.getRow(),aPiece.getCol()-1, field.getTiles())){
							aPiece.move(0, -1);
						}
					}
					if (e.getKeyCode()==KeyEvent.VK_RIGHT){
						if(aPiece.noCollision(aPiece.getRotation(),aPiece.getRow(),aPiece.getCol()+1, field.getTiles())){
							aPiece.move(0, 1);
						}
					}
					if (e.getKeyCode()==KeyEvent.VK_DOWN){
						timer.setDelay(30);
						timerActions();
						timer.restart();
					}

					if (e.getKeyCode()==KeyEvent.VK_SPACE){
						int newRot=(aPiece.getRotation()!=3)?(aPiece.getRotation()+1):(0);
						if(aPiece.noCollision(newRot,aPiece.getRow(),aPiece.getCol(), field.getTiles())){
							aPiece.rotate();
						}
					}
					if (e.getKeyCode()==KeyEvent.VK_UP){
						aPiece.hardDrop();
						timerActions();
						timer.restart();
					}
					if(aPiece!=null){
						aPiece.predictDrop(field.getTiles());
					}
				}
				if (e.getKeyCode()==KeyEvent.VK_P){
					if(!paused){
						pause(true);
					}
					else{
						timer.start();
						pause(false);
					}
				}
				if (e.getKeyCode()==KeyEvent.VK_SHIFT){
					if(canHold){
						if(heldPiece==null){
							heldPiece=aPiece;
							heldPiece.setRow(0);
							heldPiece.setCol(3);
							sPanel.setHeldPiece(heldPiece.getName());
							aPiece=nextPiece;
							aPiece.predictDrop(field.getTiles());
							nextPiece=newPiece();
							timer.restart();
						}else{
							Piece heldCopy=heldPiece;
							heldPiece=aPiece;
							heldPiece.setRow(0);
							heldPiece.setCol(3);
							sPanel.setHeldPiece(heldPiece.getName());
							aPiece=heldCopy;
							aPiece.predictDrop(field.getTiles());
							timer.restart();
						}
						canHold=false;
					}
				}
			}
			if (e.getKeyCode()==KeyEvent.VK_F2){
				field.reset();
				delay=600;
				timer.setDelay(delay);
				timer.restart();
				gameOver=false;
				pause(false);
				aPiece=newPiece();
				aPiece.predictDrop(field.getTiles());
				nextPiece=newPiece();
				sPanel.setNextPiece(nextPiece.getName());
				heldPiece=null;
				sPanel.setHeldPiece(null);
				sPanel.setLevel(1);
				currScore=0;
				currLevel=1;
			}
			repaint();
		}
		
		/**
		 * Resets the delay when down is released.
		 * 
		 * @param e Event that triggered the listener.
		 */
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode()==KeyEvent.VK_DOWN){
				timer.setDelay(delay);
			}
		}

		/**
		 * Unimplemented method
		 * 
		 * @param e Event that triggered the listener.
		 */
		public void keyTyped(KeyEvent e) {
		}
		
	}
	
	/**
	 * Homework 13.2.2: This class creates the timerListener.
	 * 
	 * @author Jackson Scroggs
	 * @version 1.0 4/21/2012
	 */
	
	public class TimerListener implements ActionListener{
		/**
		 * Calls the timer's actions when the timer ends.
		 * 
		 * @param e Event that triggered the listener.
		 */
		public void actionPerformed(ActionEvent e) {
			timerActions();
		}
	}
	
	/**
	 * Performs all the actions necessary to continue the game (check collisions, drop rows, lose, etc.
	 */
	public void timerActions(){
		if(aPiece!=null){

			if (aPiece.noCollision(aPiece.getRotation(),aPiece.getRow()+1,aPiece.getCol(), field.getTiles())&&aPiece!=null){
				aPiece.move(1, 0);
			}
			else{
				settle();
				if(!(field.isLossConditionMet())){
					aPiece=nextPiece;
					aPiece.predictDrop(field.getTiles());
					nextPiece=newPiece();
					sPanel.setNextPiece(nextPiece.getName());
				}
				else{
					aPiece=null;
					gameOver=true;
					timer.stop();
					if(checkHighScore()){
						String playerName="";
						Boolean flag=true;
						while(flag){
							playerName=JOptionPane.showInputDialog("High Score! Please enter your name:","Player 1");
							if(playerName!=null){
								if(playerName.equals("")){
									JOptionPane.showMessageDialog(null, "You must have a name!");
								}else{
									flag=false;
								}
							}else{
								flag=false;
							}
						}
						if(playerName!=null){
							setHighScore(field.getTotScore(),playerName);
							sPanel.updateHiScores();
						}
					}
				}
			}
		}
		dropRows(field.checkForClears(currLevel));
		int score=field.getTotScore();
		if(score>(currScore+300*currLevel)){
			currScore+=300*currLevel;
			currLevel++;
			sPanel.setLevel(currLevel);
			delay-=50/(1+currLevel/10.0);
			timer.setDelay(delay);
		}
		sPanel.setScore(score);
		repaint();
	}
	
	/**
	 * Changes the pause state.
	 * 
	 * @param p Boolean pause state.
	 */
	public void pause(Boolean p){
		if(p){
			timer.stop();
			paused=true;
			sPanel.setSave(true);
			sPanel.setLoad(true);
		}else{
			timer.start();
			paused=false;
			sPanel.setSave(false);
			sPanel.setLoad(false);
		}
	}
	
	/**
	 * Checks to see if the user got a High Score.
	 * 
	 * @return Boolean whether they did.
	 */
	public Boolean checkHighScore(){
		File file = null;
		try {
			file = new File(Main.class.getResource("/hiScores.txt").toURI());
		} catch (Exception e1) {
			file = new File("hiscores.txt");
			e1.printStackTrace();
		}
		try {
			Scanner fScan = new Scanner(file);
			int lowScore=Integer.parseInt(fScan.nextLine());
			fScan.close();
			if(field.getTotScore()>lowScore){
				return true;
			}else{
				return false;
			}
		}
		catch(Exception e){
			return false;
		}
	}
	
	/**
	 * Adds the Player's High Score to the file.
	 * 
	 * @param newScore The Player's score
	 * @param name The Player's name.
	 */
	public void setHighScore(int newScore,String name){
		File file = null;
		try {
			file = new File(Main.class.getResource("/hiScores.txt").toURI());
		} catch (Exception e1) {
			file = new File("hiscores.txt");
			e1.printStackTrace();
		}
		int[] hiScores=new int[5];
		String[] players=new String[5];
		try {
			Scanner fScan = new Scanner(file);
			hiScores[0]=Integer.parseInt(fScan.nextLine());
			players[0]=fScan.nextLine();
			hiScores[1]=Integer.parseInt(fScan.nextLine());
			players[1]=fScan.nextLine();
			hiScores[2]=Integer.parseInt(fScan.nextLine());
			players[2]=fScan.nextLine();
			hiScores[3]=Integer.parseInt(fScan.nextLine());
			players[3]=fScan.nextLine();
			hiScores[4]=Integer.parseInt(fScan.nextLine());
			players[4]=fScan.nextLine();
			for (int i=4;i>=0;i--){
				if (newScore>hiScores[i]){
					for (int j=0;j<i;j++){
						hiScores[j]=hiScores[j+1];
						players[j]=players[j+1];
					}
					hiScores[i]=newScore;
					players[i]=name;
					break;
				}
			}
			
			FileWriter fWrite=new FileWriter(file);
			BufferedWriter bWrite=new BufferedWriter(fWrite);
			PrintWriter pWrite=new PrintWriter(bWrite);
			for (int i=0;i<5;i++){
				pWrite.println(hiScores[i]);
				pWrite.println(players[i]);
			}
			pWrite.flush();
			pWrite.close();
			fScan.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Resets High Scores to their default values.
	 */
	public void resetHighScores(){
		try{
			File file = null;
			try {
				file = new File(Main.class.getResource("/hiScores.txt").toURI());
			} catch (Exception e1) {
				file = new File("hiscores.txt");
				e1.printStackTrace();
			}
			FileWriter fWrite=new FileWriter(file);
			BufferedWriter bWrite=new BufferedWriter(fWrite);
			PrintWriter pWrite=new PrintWriter(bWrite);
			pWrite.println("666");
			pWrite.println("Lucifer");
			pWrite.println("9001");
			pWrite.println("Vegeta");
			pWrite.println("25000");
			pWrite.println("UltraLord");
			pWrite.println("50000");
			pWrite.println("Tetris Wizard");
			pWrite.println("72000");
			pWrite.println("Jackson");
			pWrite.flush();
			pWrite.close();
		} catch (Exception e) {}
	}

	/**
	 * Gets the Current Score.
	 * 
	 * @return Current Score
	 */
	public int getCurrScore(){
		return currLevel;
	}

	/**
	 * Sets the Current Score.
	 * 
	 * @param currScore Current Score
	 */
	public void setCurrScore(int currScore) {
		this.currScore=currScore;
	}

	/**
	 * Gets the Current Level.
	 * 
	 * @return Current Level
	 */
	public int getCurrLevel(){
		return currLevel;
	}

	/**
	 * Sets the Current Level.
	 * 
	 * @param currLevel Current Level
	 */
	public void setCurrLevel(int currLevel) {
		this.currLevel=currLevel;
	}

	/**
	 * Gets the Total Score.
	 * 
	 * @return Total Score
	 */
	public int getTotScore(){
		return field.getTotScore();
	}

	/**
	 * Sets the Total Score.
	 * 
	 * @param totScore Total Score
	 */
	public void setTotScore(int totScore) {
		field.setTotScore(totScore);
	}

	/**
	 * Gets the timer's delay.
	 * 
	 * @return Timer's delay
	 */
	public int getTimerDelay(){
		return timer.getDelay();
	}

	/**
	 * Sets the timer's delay.
	 * 
	 * @param timerDelay Timer's delay
	 */
	public void setTimerDelay(int timerDelay) {
		timer.setDelay(timerDelay);
		delay=timerDelay;
	}

	/**
	 * Gets the state of CanHold.
	 * 
	 * @return CanHold's state.
	 */
	public Boolean getCanHold(){
		return canHold;
	}

	/**
	 * Sets the state of CanHold.
	 * 
	 * @param b CanHold's state.
	 */
	public void setCanHold(boolean b){
		canHold=b;
	}

	/**
	 * Gets the active Piece.
	 * 
	 * @return Active Piece
	 */
	public Piece getAPiece(){
		return aPiece;
	}

	/**
	 * Sets the active Piece.
	 * 
	 * @param aPiece Active Piece
	 */
	public void setAPiece(Piece aPiece){
		this.aPiece=aPiece;
	}

	/**
	 * Gets the Next Piece.
	 * 
	 * @return Next Piece
	 */
	public Piece getNextPiece(){
		return nextPiece;
	}

	/**
	 * Sets the Next Piece.
	 * 
	 * @param nextPiece Next Piece
	 */
	public void setNextPiece(Piece nextPiece){
		this.nextPiece=nextPiece;
	}

	/**
	 * Gets the Held Piece.
	 * 
	 * @return Held Piece
	 */
	public Piece getHeldPiece(){
		return heldPiece;
	}

	/**
	 * Sets the Held Piece.
	 * 
	 * @param heldPiece Held Piece
	 */
	public void setHeldPiece(Piece heldPiece){
		this.heldPiece=heldPiece;
	}

	/**
	 * Gets the field's tiles.
	 * 
	 * @return PlayingField's tiles.
	 */
	public Color[][] getTiles(){
		return field.getTiles();
	}

	/**
	 * Gets the paused state.
	 * 
	 * @return Paused state.
	 */
	public Boolean getPaused(){
		return paused;
	}

	/**
	 * Sets the gameOver state.
	 * 
	 * @return GameOver state.
	 */
	public void setGameOver(Boolean b){
		gameOver=b;
	}
}
