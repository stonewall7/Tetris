import java.awt.Color;

/**
 * Homework 13.9: This class creates the L Piece.
 * 
 * @author Jackson Scroggs
 * @version 1.0 4/21/2012
 */

public class L_Piece extends Piece{

	/**
	 * This constructor makes a new Piece through the super constructor of Piece.
	 */
	public L_Piece() {
		super("L_Piece",0, 3, new Color(255,165,0), new int[][][] {{{0,0,0,1},
									      		          {0,1,1,1},
									      		          {0,0,0,0},
									      		          {0,0,0,0}},
									      		
									      	            {{0,0,1,0},
									      		         {0,0,1,0},
									      		         {0,0,1,1},
									      		         {0,0,0,0}},
										      	
									      		        {{0,0,0,0},
											             {0,1,1,1},
											             {0,1,0,0},
											             {0,0,0,0}},
										      	
											            {{0,1,1,0},
										      	         {0,0,1,0},
										      	         {0,0,1,0},
										      	         {0,0,0,0}}});
	}
}