import java.awt.Color;

/**
 * Homework 13.7: This class creates the Square Piece.
 * 
 * @author Jackson Scroggs
 * @version 1.0 4/21/2012
 */

public class SquarePiece extends Piece{

	/**
	 * This constructor makes a new Piece through the super constructor of Piece.
	 */
	public SquarePiece() {
		super("Square_Piece",0, 3, Color.yellow, new int[][][] {{{0,1,1,0},
									      		                 {0,1,1,0},
									      	                     {0,0,0,0},
									      		                 {0,0,0,0}},
									      		
									      	                    {{0,1,1,0},
										      	                 {0,1,1,0},
										      	                 {0,0,0,0},
										      	                 {0,0,0,0}},
										      	
										      	                {{0,1,1,0},
											                     {0,1,1,0},
											                     {0,0,0,0},
											                     {0,0,0,0}},
										      	
										                        {{0,1,1,0},
											                     {0,1,1,0},
											                     {0,0,0,0},
											                     {0,0,0,0}}});
	}
}
