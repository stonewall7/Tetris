import java.awt.Color;

/**
 * Homework 13.8: This class creates the J Piece.
 * 
 * @author Jackson Scroggs
 * @version 1.0 4/21/2012
 */

public class J_Piece extends Piece{

	/**
	 * This constructor makes a new Piece through the super constructor of Piece.
	 */
	public J_Piece() {
		super("J_Piece",0, 3, Color.blue, new int[][][] {{{1,0,0,0},
									      				  {1,1,1,0},
									      				  {0,0,0,0},
									      				  {0,0,0,0}},
									      		
									      	             {{0,1,1,0},
										      	          {0,1,0,0},
										      	          {0,1,0,0},
										      	          {0,0,0,0}},
										      	
										      	         {{0,0,0,0},
											              {1,1,1,0},
											              {0,0,1,0},
											              {0,0,0,0}},
										      	
										                 {{0,1,0,0},
										      	          {0,1,0,0},
										      	          {1,1,0,0},
										      	          {0,0,0,0}}});
	}
}