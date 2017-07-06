import java.awt.*;

/**
 * Homework 13.5: This class creates the Piece object.
 * 
 * @author Jackson Scroggs
 * @version 1.0 4/21/2012
 */

public abstract class Piece {
	private int row,col,rotation,preRow;
	private Color color;
	private int[][][] model;
	private String name;
	
	/**
	 * Constructs a new Piece.
	 * 
	 * @param name Name of Piece
	 * @param row Starting row
	 * @param col Starting column
	 * @param color Color of Piece
	 * @param model Array of rotations.
	 */
	public Piece(String name,int row,int col,Color color,int[][][] model){
		this.name=name;
		this.row=row;
		this.col=col;
		this.color=color;
		this.model=model;
		rotation=0;
	}
	
	/**
	 * Moves the piece.
	 * 
	 * @param addRow Number of rows to add
	 * @param addCol Number of columns to add
	 */
	public void move(int addRow, int addCol){
		row+=addRow;
		col+=addCol;
	}
	
	/**
	 * Checks for a collision at some new position.
	 * 
	 * @param rot New rotation
	 * @param newRow New Row
	 * @param newCol New Column
	 * @param tiles Tiles with which to collide
	 * @return Whether collision occurred.
	 */
	public boolean noCollision(int rot,int newRow, int newCol,Color[][] tiles){
		for (int i=0;i<4;i++){
			for (int j=0;j<4;j++){
				try{
					if(model[rot][i][j]==1 && tiles[newRow+i][newCol+j]!=null){
						return false;
					}
				}
				catch(Exception e){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Changes piece to the next rotation.
	 */
	public void rotate(){
		rotation=(rotation!=3)?(rotation+1):(0);
	}
	
	/**
	 * Predicts where the piece will drop.
	 * 
	 * @param tiles Tiles with which to collide
	 */
	public void predictDrop(Color[][] tiles){
		boolean flag=true;
		int rows=1;
		while(flag){
			if(!noCollision(rotation,row+rows,col,tiles)){
				preRow=row+rows-1;
				flag=false;
			}
			rows++;
		}
	}
	
	/**
	 * Drops the piece to the predicted place.
	 */
	public void hardDrop(){
		row=preRow;
	}
	
	/**
	 * Draws the piece.
	 * 
	 * @param g Graphical input
	 * @param tileDimension The dimension of each tile
	 */
	public void draw(Graphics g, Dimension tileDimension){
		int h=(int) tileDimension.getHeight();
		int w=(int) tileDimension.getWidth();
		for (int i=0;i<4;i++){
			for (int j=0;j<4;j++){
				if(model[rotation][i][j]==1){
					g.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),100));
					g.fillRect(w*(j+col), h*(i+preRow), w, h);
					g.setColor(color.darker());
					g.fillRect(w*((j+col)), h*((i+row)), w, h);
					int[] xPoly1={w*(j+col),w*(j+col)+w/7,w*(j+col)+w/7,w*(j+col)};
					int[] yPoly1={h*(i+row),h*(i+row)+h/7,h*(i+row)+h-h/7,h*(i+row)+h};
					int[] xPoly2={w*(j+col),w*(j+col)+w/7,w*(j+col)+w-w/7,w*(j+col)+w};
					int[] yPoly2={h*(i+row),h*(i+row)+h/7,h*(i+row)+h/7,h*(i+row)};
					int[] xPoly3={w*(j+col)+w,w*(j+col)+w-w/7,w*(j+col)+w-w/7,w*(j+col)+w};
					int[] yPoly3={h*(i+row),h*(i+row)+h/7,h*(i+row)+h-h/7,h*(i+row)+h};
					int[] xPoly4={w*(j+col)+w,w*(j+col)+w-w/7,w*(j+col)+w/7,w*(j+col)};
					int[] yPoly4={h*(i+row)+h,h*(i+row)+h-h/7,h*(i+row)+h-h/7,h*(i+row)+h};
					g.setColor(color.darker().darker());
					g.fillPolygon(xPoly1,yPoly1,4);
					g.fillPolygon(xPoly3,yPoly3,4);
					g.setColor(color);
					g.fillPolygon(xPoly2,yPoly2,4);
					g.setColor(color.darker().darker().darker());
					g.fillPolygon(xPoly4,yPoly4,4);
					g.setColor(color.darker());
				}
			}
		}	
	}
	
	/**
	 * Gets the piece's current row
	 * 
	 * @return Piece's row
	 */
	public int getRow(){
		return row;
	}
	
	/**
	 *Sets the piece's row
	 * 
	 * @param row New Row
	 */
	public void setRow(int row){
		this.row=row;
	}

	
	/**
	 * Gets the piece's current column
	 * 
	 * @return Piece's column
	 */
	public int getCol(){
		return col;
	}
	
	/**
	 *Sets the piece's column
	 * 
	 * @param row New Column
	 */
	public void setCol(int col){
		this.col=col;
	}
	
	/**
	 * Get the piece's current rotation
	 * 
	 * @return
	 */
	public int getRotation(){
		return rotation;
	}
	
	/**
	 * Get the array of piece rotations
	 * 
	 * @return Model array
	 */
	public int[][][] getModel(){
		return model;
	}

	/**
	 * Get the color of the piece
	 * 
	 * @return Piece's color
	 */
	public Color getColor(){
		return color;
	}

	/**
	 * Get the name of the piece
	 * 
	 * @return Piece's name
	 */
	public String getName() {
		return name;
	}
}
