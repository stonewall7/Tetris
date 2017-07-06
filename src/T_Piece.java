import java.awt.Color;

/**
 * Homework 13.12: This class creates the T Piece.
 * 
 * @author Jackson Scroggs
 * @version 1.0 4/21/2012
 */

public class T_Piece extends Piece{

	/**
	 * This constructor makes a new Piece through the super constructor of Piece.
	 */
	public T_Piece() {
		super("T_Piece",0, 3, Color.magenta, new int[][][] {{{0,1,0,0},
									      		             {1,1,1,0},
									      	                 {0,0,0,0},
									      		             {0,0,0,0}},
									      		
									      	                {{0,1,0,0},
										      	             {0,1,1,0},
										      	             {0,1,0,0},
										      	             {0,0,0,0}},
										      	
										      	            {{0,0,0,0},
											                 {1,1,1,0},
											                 {0,1,0,0},
											                 {0,0,0,0}},
										      	
										                    {{0,1,0,0},
										      	             {1,1,0,0},
										      	             {0,1,0,0},
										      	             {0,0,0,0}}});
	}
}