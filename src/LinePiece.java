import java.awt.Color;

/**
 * Homework 13.6: This class creates the Line Piece.
 * 
 * @author Jackson Scroggs
 * @version 1.0 4/21/2012
 */

public class LinePiece extends Piece{
	
	/**
	 * This constructor makes a new Piece through the super constructor of Piece.
	 */
	public LinePiece() {
		super("Line_Piece",0, 3, Color.cyan, new int[][][] {{{0,0,0,0},
									      		             {1,1,1,1},
									      		             {0,0,0,0},
									      		             {0,0,0,0}},
									      		
									      	                {{0,0,1,0},
										      	             {0,0,1,0},
										      	             {0,0,1,0},
										      	             {0,0,1,0}},
										      	
										                    {{0,0,0,0},
										                 	 {0,0,0,0},
										      	             {1,1,1,1},
										      	             {0,0,0,0}},
										      	
										                    {{0,1,0,0},
										      	             {0,1,0,0},
										      	             {0,1,0,0},
										      	             {0,1,0,0}}});
	}
}
