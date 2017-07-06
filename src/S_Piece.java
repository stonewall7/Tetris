import java.awt.Color;

/**
 * Homework 13.10: This class creates the S Piece.
 * 
 * @author Jackson Scroggs
 * @version 1.0 4/21/2012
 */

public class S_Piece extends Piece{

	/**
	 * This constructor makes a new Piece through the super constructor of Piece.
	 */
	public S_Piece() {
		super("S_Piece",0, 3, Color.green, new int[][][] {{{0,1,1,0},
									      		           {1,1,0,0},
									      	               {0,0,0,0},
									      		           {0,0,0,0}},
									      		
									      	              {{0,1,0,0},
										      	           {0,1,1,0},
										      	           {0,0,1,0},
										      	           {0,0,0,0}},
										      	
										      	          {{0,0,0,0},
											               {0,1,1,0},
											               {1,1,0,0},
											               {0,0,0,0}},
										      	
										                  {{1,0,0,0},
										      	           {1,1,0,0},
										      	           {0,1,0,0},
										      	           {0,0,0,0}}});
	}
}