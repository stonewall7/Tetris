import java.awt.*;

/**
 * Homework 13.4: This class creates the Playing Field.
 * 
 * @author Jackson Scroggs
 * @version 1.0 4/21/2012
 */

public class PlayingField {
	final static int ROWS=20;
	final static int COLS=10;
	private int totScore;
	private static Color[][] tiles;
	
	/**
	 * Constructs the Playing Field.
	 */
	public PlayingField(){
		tiles=new Color[ROWS+1][COLS];
		for (int j=0;j<COLS;j++){
			tiles[20][j]=Color.gray;
		}
		totScore=0;
	}
	
	/**
	 * Checks for rows to clear and add to the score accordingly.
	 * 
	 * @param currLevel The Current Level
	 * @return Boolean list of cleared rows.
	 */
	public boolean[] checkForClears(int currLevel){
		boolean[] clears=new boolean[ROWS];
		int combo=0;
		
		for(int i=0;i<ROWS;i++){
			boolean clearRow=true;
			for(int j=0;j<COLS;j++){
				if (tiles[i][j]==null){
					clearRow=false;
				}
			}
			if (clearRow){
				for(int j=0;j<COLS;j++){
					tiles[i][j]=null;
				}
				combo++;
				clears[i]=true;
			}
			else{
				clears[i]=false;
			}
		}
		if (combo>0){
			totScore+=(200*((int)Math.pow(2, (combo-1))))*(1+(currLevel/10.0));
		}
		return clears;
	}
	
	/**
	 * Checks for loss.
	 * 
	 * @return Boolean of loss
	 */
	public boolean isLossConditionMet(){
		for(int j=0;j<COLS;j++){
			if(tiles[0][j]!=null){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if a tile is vacant
	 * 
	 * @param x Row of tile
	 * @param y Col of tile
	 * @return Whether the tile is vacant.
	 */
	public boolean isTileVacant(int x,int y){
		if(tiles[x][y]==null){
			return true;
		}
		return false;
	}

	/**
	 * Fills a tile
	 * 
	 * @param x Row of tile
	 * @param y Col of tile
	 * @param color Color of tile
	 */
	public void fillTile(int x, int y, Color color){
		tiles[x][y]=color;
	}

	/**
	 * Resets the Playing Field
	 */
	public void reset(){
		tiles=new Color[ROWS+1][COLS];
		for (int j=0;j<COLS;j++){
			tiles[20][j]=Color.gray;
		}
		totScore=0;
	}
	
	/**
	 * Draws the field.
	 * 
	 * @param g Graphical input
	 * @param tileDimension The dimension of each tile
	 */
	public static void draw(Graphics g, Dimension tileDimension){
		int h=(int) tileDimension.getHeight();
		int w=(int) tileDimension.getWidth();
		for(int i=0;i<(ROWS+1);i++){
			for(int j=0;j<COLS;j++){
				if(tiles[i][j]!=null){
					g.setColor(tiles[i][j].darker());
					g.fillRect(w*(j), h*(i), w, h);
					int[] xPoly1={w*j,w*j+w/7,w*j+w/7,w*j};
					int[] yPoly1={h*i,h*i+h/7,h*i+h-h/7,h*i+h};
					int[] xPoly2={w*j,w*j+w/7,w*j+w-w/7,w*j+w};
					int[] yPoly2={h*i,h*i+h/7,h*i+h/7,h*i};
					int[] xPoly3={w*j+w,w*j+w-w/7,w*j+w-w/7,w*j+w};
					int[] yPoly3={h*i,h*i+h/7,h*i+h-h/7,h*i+h};
					int[] xPoly4={w*j+w,w*j+w-w/7,w*j+w/7,w*j};
					int[] yPoly4={h*i+h,h*i+h-h/7,h*i+h-h/7,h*i+h};
					g.setColor(tiles[i][j].darker().darker());
					g.fillPolygon(xPoly1,yPoly1,4);
					g.fillPolygon(xPoly3,yPoly3,4);
					g.setColor(tiles[i][j]);
					g.fillPolygon(xPoly2,yPoly2,4);
					g.setColor(tiles[i][j].darker().darker().darker());
					g.fillPolygon(xPoly4,yPoly4,4);
					g.setColor(tiles[i][j].darker());
				}
			}
		}
	}
	
	/**
	 * Gets the tiles array.
	 * 
	 * @return The tiles array
	 */
	public Color[][] getTiles(){
		return tiles;
	}

	/**
	 * Gets the total score.
	 * 
	 * @return Total Score
	 */
	public int getTotScore() {
		return totScore;
	}
	
	/**
	 * Sets the total score.
	 * 
	 * @param totScore New total score
	 */
	public void setTotScore(int totScore){
		this.totScore=totScore;
	}
}
