import javax.swing.*;

import sun.applet.Main;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URISyntaxException;
import java.util.*;


/**
 * Homework 13.3: This class creates the Panel for Tetris's information.
 * 
 * @author Jackson Scroggs
 * @version 1.0 4/21/2012
 */

public class StatsPanel extends JPanel
{
	private JLabel level,score,nextPiece, heldPiece, instructions, hiScores;
	private JButton save,load,reset;
	private PlayingPanel pPanel;
	
	/**
	 * Constructs the StatsPanel.
	 */
	public StatsPanel(){
		setPreferredSize(new Dimension(150, PlayingPanel.HEIGHT));
		
		instructions=new JLabel("<html>Fill rows for points!<br>>1 Row=More Points!<br><br>Left/Right--Move<br>Down--Quick Fall<br>Up--Hard Drop<br>Space--Rotate<br>Shift--Hold Piece<br>P--Pause<br>F2--New Game</html>");
		Font font=instructions.getFont();
		instructions.setFont(new Font(font.getFontName(), font.getStyle(), 11));
		instructions.setBorder(BorderFactory.createTitledBorder("Instructions"));
		add(instructions);
		
		level=new JLabel("Current Level: 1");
		level.setFont(new Font(font.getFontName(), font.getStyle(), 13));
		add(level);
		
		score=new JLabel("Total Score: 0");
		score.setFont(new Font(font.getFontName(), font.getStyle(), 13));
		add(score);
		
		nextPiece=new JLabel();
		nextPiece.setBorder(BorderFactory.createTitledBorder("Next Piece:"));
		add(nextPiece);
		
		heldPiece=new JLabel();
		heldPiece.setBorder(BorderFactory.createTitledBorder("Held Piece:"));
		add(heldPiece);
		
		save=new JButton("Save");
		save.setFocusable(false);
		save.setEnabled(false);
		save.addActionListener(new SaveListener());
		add(save);
		
		load=new JButton("Load");
		load.setFocusable(false);
		load.setEnabled(false);
		load.addActionListener(new LoadListener());
		add(load);
		
		reset=new JButton("Reset High Scores");
		reset.setFocusable(false);
		reset.addActionListener(new resetListener());
		add(reset);
		
		hiScores=new JLabel();
		hiScores.setBorder(BorderFactory.createTitledBorder("High Scores:"));
		hiScores.setFont(new Font(font.getFontName(), font.getStyle(), 14));
		updateHiScores();
		add(hiScores);
	}
	
	/**
	 * Sets the text of the level Label.
	 * 
	 * @param lev Current Level
	 */
	public void setLevel(int lev){
		level.setText("Current Level: "+lev);
	}
	
	/**
	 * Sets the text of the score Label.
	 * 
	 * @param totScore Current Score
	 */
	public void setScore(int totScore) {
		this.score.setText("Total Score: "+totScore);
	}
	
	/**
	 * Sets the image of the nextPiece Label.
	 * 
	 * @param p Image Location
	 */
	public void setNextPiece(String p){
		nextPiece.setIcon(new ImageIcon(Main.class.getResource("/"+p+".png")));
	}
	
	/**
	 * Sets the image of the heldPiece Label.
	 * 
	 * @param p Image Location
	 */
	public void setHeldPiece(String p){
		if(p==null){
			heldPiece.setIcon(new ImageIcon(Main.class.getResource("/null.png")));
		}else{
			heldPiece.setIcon(new ImageIcon(Main.class.getResource("/"+p+".png")));
		}
	}
	
	/**
	 * Sets pPanel to the PlayingPanel
	 * 
	 * @param pPanel The current PlayingPanel.
	 */
	public void setPPanel(PlayingPanel pPanel){
		this.pPanel=pPanel;
	}

	/**
	 * Updates the high scores from the file.
	 */
	public void updateHiScores(){
		File file = null;
		try {
			file = new File(Main.class.getResource("/hiScores.txt").toURI());
		} catch (Exception e1) {
			file = new File("hiscores.txt");
			e1.printStackTrace();
		}
		int[] hiScores=new int[5];
		String[] players=new String[5];
		try{
			Scanner fScan=new Scanner(file);
			for(int i=0;i<5;i++){
				hiScores[i]=Integer.parseInt(fScan.nextLine());
				players[i]=fScan.nextLine();
			}
			String aString="";
			for (int i=4;i>=0;i--){
				aString+=players[i]+": "+hiScores[i];
				if (i!=0){
					aString+="<br>";
				}
			}
			this.hiScores.setText("<html>"+aString+"</html>");
			fScan.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Sets the enabled state of the save button.
	 * 
	 * @param b Enabled state
	 */
	public void setSave(Boolean b){
		save.setEnabled(b);
	}

	/**
	 * Sets the enabled state of the load button.
	 * 
	 * @param b Enabled state
	 */
	public void setLoad(Boolean b){
		load.setEnabled(b);
	}
	
	/**
	 * Makes a new Piece.
	 * 
	 * @param name Piece's name
	 * @return The new Piece
	 */
	public Piece newPiece(String name){
		Piece retPiece;
		if(name.equals("Line_Piece")){
			retPiece=new LinePiece();
			return (retPiece);
		}
		else if(name.equals("Square_Piece")){
			retPiece=new SquarePiece();
			return (retPiece);
		}
		else if(name.equals("S_Piece")){
			retPiece=new S_Piece();
			return (retPiece);
		}
		else if(name.equals("Z_Piece")){
			retPiece=new Z_Piece();
			return (retPiece);
		}
		else if(name.equals("J_Piece")){
			retPiece=new J_Piece();
			return (retPiece);
		}
		else if(name.equals("L_Piece")){
			retPiece=new L_Piece();
			return (retPiece);
		}
		else{
			retPiece=new T_Piece();
			return (retPiece);
		}
	}
	

	/**
	 * Homework 13.3.1: This class creates the listener for the save button.
	 * 
	 * @author Jackson Scroggs
	 * @version 1.0 4/21/2012
	 */
	private class SaveListener implements ActionListener{
		/**
		 * Writes the data to a selected file.
		 * 
		 * @param e Event that triggers the listener.
		 */
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChoose=new JFileChooser();
			int choice=fileChoose.showOpenDialog(null);
			if(choice==JFileChooser.APPROVE_OPTION){
				try{
					File file=fileChoose.getSelectedFile();
					file.createNewFile();
					FileWriter fWrite=new FileWriter(file);
					BufferedWriter bWrite=new BufferedWriter(fWrite);
					PrintWriter pWrite=new PrintWriter(bWrite);
					Color[][] tiles=pPanel.getTiles();
					pWrite.println(pPanel.getCurrScore());
					pWrite.println(pPanel.getCurrLevel());
					pWrite.println(pPanel.getTotScore());
					pWrite.println(pPanel.getTimerDelay());
					pWrite.println(pPanel.getCanHold());
					Piece aPiece=pPanel.getAPiece();
					pWrite.println(aPiece.getName());
					pWrite.println(aPiece.getRow());
					pWrite.println(aPiece.getCol());
					Piece nextPiece=pPanel.getNextPiece();
					pWrite.println(nextPiece.getName());
					pWrite.println(nextPiece.getRow());
					pWrite.println(nextPiece.getCol());
					if(pPanel.getHeldPiece()!=null){
						Piece heldPiece=pPanel.getHeldPiece();
						pWrite.println(heldPiece.getName());
						pWrite.println(heldPiece.getRow());
						pWrite.println(heldPiece.getCol());
					}else{
						pWrite.println(-1);
						pWrite.println(-1);
						pWrite.println(-1);
					}
					for(int i=0;i<tiles.length;i++){
						for (int j=0;j<tiles[0].length;j++){
							if(tiles[i][j]!=null){
								pWrite.println(tiles[i][j].getRGB());
							}else{
								pWrite.println(-1);
							}
						}
					}
					
					pWrite.flush();
					pWrite.close();
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(null, "Save Failed.");
				}
			}
		}
	}

	/**
	 * Homework 13.3.2: This class creates the listener for the load button.
	 * 
	 * @author Jackson Scroggs
	 * @version 1.0 4/21/2012
	 */
	private class LoadListener implements ActionListener{
		/**
		 * Loads the data from a selected file.
		 * 
		 * @param e Event that triggers the listener.
		 */
		public void actionPerformed(ActionEvent e){
			JFileChooser fileChoose=new JFileChooser();
			int choice=fileChoose.showOpenDialog(null);
			if(choice==JFileChooser.APPROVE_OPTION){
				try{
					File file =fileChoose.getSelectedFile();
					Scanner fScan=new Scanner(file);
					Color[][] tiles=pPanel.getTiles();
					pPanel.setCurrScore(Integer.parseInt(fScan.nextLine()));
					pPanel.setCurrLevel(Integer.parseInt(fScan.nextLine()));
					level.setText("Current Level: "+pPanel.getCurrLevel());
					pPanel.setTotScore(Integer.parseInt(fScan.nextLine()));
					score.setText("Total Score: "+pPanel.getTotScore());
					pPanel.setTimerDelay(Integer.parseInt(fScan.nextLine()));
					pPanel.setCanHold(Boolean.parseBoolean(fScan.nextLine()));
					Piece aPiece=newPiece(fScan.nextLine());
					aPiece.setRow(Integer.parseInt(fScan.nextLine()));
					aPiece.setCol(Integer.parseInt(fScan.nextLine()));
					Piece nextPiece=newPiece(fScan.nextLine());
					nextPiece.setRow(Integer.parseInt(fScan.nextLine()));
					nextPiece.setCol(Integer.parseInt(fScan.nextLine()));
					String checker=fScan.nextLine();
					Piece heldPiece;
					if(checker.equals("-1")){
						heldPiece=null;
						fScan.nextLine();
						fScan.nextLine();
					}else{
						heldPiece=newPiece(checker);
						heldPiece.setRow(Integer.parseInt(fScan.nextLine()));
						heldPiece.setCol(Integer.parseInt(fScan.nextLine()));
					}
					for(int i=0;i<tiles.length;i++){
						for (int j=0;j<tiles[0].length;j++){
							int next=Integer.parseInt(fScan.nextLine());
							if(next==-1){
								tiles[i][j]=null;
							}else{
								tiles[i][j]=new Color(next);
							}
						}
					}
					aPiece.predictDrop(tiles);
					pPanel.setAPiece(aPiece);
					pPanel.setNextPiece(nextPiece);
					setNextPiece(nextPiece.getName());
					pPanel.setHeldPiece(heldPiece);
					if(heldPiece==null){
						setHeldPiece(null);
					}else{
						setHeldPiece(heldPiece.getName());
					}
					fScan.close();
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(null, "Incompatible file.");
				}
			}
			pPanel.setGameOver(false);
			pPanel.repaint();
		}
	}

	/**
	 * Homework 13.3.3: This class creates the listener for the reset button.
	 * 
	 * @author Jackson Scroggs
	 * @version 1.0 4/21/2012
	 */
	private class resetListener implements ActionListener{
		/**
		 * Resets the High Scores.
		 * 
		 * @param e Event that triggers the listener.
		 */
		public void actionPerformed(ActionEvent e) {
			pPanel.resetHighScores();
			updateHiScores();
		}
		
	}
}