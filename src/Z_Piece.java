import java.awt.Color;

/**
 * Homework 13.11: This class creates the Z Piece.
 * 
 * @author Jackson Scroggs
 * @version 1.0 4/21/2012
 */

public class Z_Piece extends Piece{

	/**
	 * This constructor makes a new Piece through the super constructor of Piece.
	 */
	public Z_Piece() {
		super("Z_Piece",0, 3, Color.red, new int[][][] {{{0,1,1,0},
									      	             {0,0,1,1},
									      	             {0,0,0,0},
									      	             {0,0,0,0}},
									      		
									      	            {{0,0,0,1},
									      	             {0,0,1,1},
									      	             {0,0,1,0},
									      	             {0,0,0,0}},
										      	
									      	            {{0,0,0,0},
									      	             {0,1,1,0},
									      	             {0,0,1,1},
									      	             {0,0,0,0}},
										      	
									      	            {{0,0,1,0},
										                 {0,1,1,0},
										                 {0,1,0,0},
										                 {0,0,0,0}}});
	}
}