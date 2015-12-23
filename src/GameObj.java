

import java.awt.Graphics;
import java.util.Set;
import java.util.TreeSet;

/** An object in the game. 
 *
 *  Game objects exist in the game court. They have a position, 
 *  velocity, size and bounds. Their velocity controls how they 
 *  move; their position should always be within their bounds.
 */
public class GameObj {

	/** Current position of the object (in terms of graphics coordinates)
	 *  
	 * Coordinates are given by the upper-left hand corner of the object.
	 * This position should always be within bounds.
	 *  0 <= pos_x <= max_x 
	 *  0 <= pos_y <= max_y 
	 */
	private int pos_x; 
	private int pos_y;

	/** Size of object, in pixels */
	public static final int OBJ_WIDTH = 40;
	public static final int OBJ_HEIGHT = 40;
	
	/** Velocity: number of pixels to move every time move() is called */
	public int v_x;
	public int v_y;

	/** Upper bounds of the area in which the object can be positioned.  
	 *    Maximum permissible x, y positions for the upper-left 
	 *    hand corner of the object
	 */
	public int max_x;
	public int max_y;
	
	/**Indices that describe objs location in array*/
	private int index_x;
	private int index_y;

	/**Set of characters relating to various items*/
	public static Set<Character> objChars = new TreeSet<Character>();
	private static char objChar;
	
	
	/**
	 * Constructor
	 */
	public GameObj(int v_x, int v_y, int pos_x, int pos_y, int court_width, int court_height,
		int index_x, int index_y, char itemChar){
		this.v_x = v_x;
		this.v_y = v_y;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.index_x = index_x;
		this.index_y = index_y;
		GameObj.objChar = itemChar;
		objChars.add(Character.toUpperCase(GameObj.objChar));
		
		// take the width and height into account when setting the 
		// bounds for the upper left corner of the object.
		this.max_x = court_width - OBJ_WIDTH;
		this.max_y = court_height - OBJ_HEIGHT;

	}


	/**
	 * Moves the object by its velocity.  Ensures that the object does
	 * not go outside its bounds by clipping.
	 */
	public void move(){
		pos_x += v_x;
		pos_y += v_y;
		index_x = getXIndexFromPos(pos_x);
		index_y = getYIndexFromPos(pos_y);

		clip();
	}
	
	/**WHAT ABOUT ROUNDING ????*/
	private static int getXIndexFromPos(int pos_x) {
	    double rounded = Math.floor(pos_x / OBJ_WIDTH);
	    return (int)rounded;
	}
	
	private static int getYIndexFromPos(int pos_y) {
        double rounded = Math.floor(pos_y / OBJ_HEIGHT);
        return (int)rounded;
    }

	/**
	 * Prevents the object from going outside of the bounds of the area 
	 * designated for the object. (i.e. Object cannot go outside of the 
	 * active area the user defines for it).
	 */ 
	public void clip(){
		if (pos_x < 0) pos_x = 0;
		else if (pos_x > max_x) pos_x = max_x;

		if (pos_y < 0) pos_y = 0;
		else if (pos_y > max_y) pos_y = max_y;
	}

	/**
	 * Determine whether this game object is currently intersecting
	 * another object.
	 * 
	 * Intersection is determined by comparing bounding boxes. If the 
	 * bounding boxes overlap, then an intersection is considered to occur.
	 * 
	 * @param obj : other object
	 * @return whether this object intersects the other object.
	 */
	public boolean intersects(GameObj obj){
		return (pos_x + OBJ_WIDTH >= obj.pos_x
				&& pos_y + OBJ_HEIGHT >= obj.pos_y
				&& obj.pos_x + GameObj.OBJ_WIDTH >= pos_x 
				&& obj.pos_y + GameObj.OBJ_HEIGHT >= pos_y);
	}

	
	/**
	 * Determine whether this game object will intersect another in the
	 * next time step, assuming that both objects continue with their 
	 * current velocity.
	 * 
	 * Intersection is determined by comparing bounding boxes. If the 
	 * bounding boxes (for the next time step) overlap, then an 
	 * intersection is considered to occur.
	 * 
	 * @param obj : other object
	 * @return whether an intersection will occur.
	 */
	public boolean willIntersect(GameObj obj){
		int next_x = pos_x + v_x;
		int next_y = pos_y + v_y;
		int next_obj_x = obj.pos_x + obj.v_x;
		int next_obj_y = obj.pos_y + obj.v_y;
		return (next_x + OBJ_WIDTH >= next_obj_x
				&& next_y + OBJ_HEIGHT >= next_obj_y
				&& next_obj_x + GameObj.OBJ_WIDTH >= next_x 
				&& next_obj_y + GameObj.OBJ_HEIGHT >= next_y);
	}

	
	/** Update the velocity of the object in response to hitting
	 *  an obstacle in the given direction. If the direction is
	 *  null, this method has no effect on the object. */
	public void bounce(Direction d) {
		if (d == null) return;
		switch (d) {
		case UP:    v_y = Math.abs(v_y); break;  
		case DOWN:  v_y = -Math.abs(v_y); break;
		case LEFT:  v_x = Math.abs(v_x); break;
		case RIGHT: v_x = -Math.abs(v_x); break;
		}
	}
	
	/** Determine whether the game object will hit a 
	 *  wall in the next time step. If so, return the direction
	 *  of the wall in relation to this game object.
	 *  
	 * @return direction of impending wall, null if all clear.
	 */
	public Direction hitWall() {
		if (pos_x + v_x < 0)
			return Direction.LEFT;
		else if (pos_x + v_x > max_x)
			return Direction.RIGHT;
		if (pos_y + v_y < 0)
			return Direction.UP;
		else if (pos_y + v_y > max_y)
			return Direction.DOWN;
		else return null;
	}

	/** Determine whether the game object will hit another 
	 *  object in the next time step. If so, return the direction
	 *  of the other object in relation to this game object.
	 *  
	 * @return direction of impending object, null if all clear.
	 */
	public Direction hitObj(GameObj other) {

		if (this.willIntersect(other)) {
			double dx = other.pos_x + GameObj.OBJ_WIDTH /2 - (pos_x + OBJ_WIDTH /2);
			double dy = other.pos_y + GameObj.OBJ_HEIGHT/2 - (pos_y + OBJ_HEIGHT/2);

			double theta = Math.acos(dx / (Math.sqrt(dx * dx + dy *dy)));
			double diagTheta = Math.atan2(OBJ_HEIGHT / 2, OBJ_WIDTH / 2);

   if (theta <= diagTheta ) {
     return Direction.RIGHT;
   } else if ( theta > diagTheta && theta <= Math.PI - diagTheta ) {
     if ( dy > 0 ) {
       // Coordinate system for GUIs is switched
       return Direction.DOWN;
     } else {
       return Direction.UP;
     }
   } else {
     return Direction.LEFT;
   }
		} else {
			return null;
		}

	}
	
	/**
	 * Default draw method that provides how the object should be drawn 
	 * in the GUI. This method does not draw anything. Subclass should 
	 * override this method based on how their object should appear.
	 * 
	 * @param g 
	 *	The <code>Graphics</code> context used for drawing the object.
	 * 	Remember graphics contexts that we used in OCaml, it gives the 
	 *  context in which the object should be drawn (a canvas, a frame, 
	 *  etc.)
	 */
	public void draw(Graphics g) {
	}
	
	/**Calculate the position value to feed to the constructor*/
    public static int calcXPos(int index) {
        return index * OBJ_WIDTH;
    }
    
    /**Calculate the position value to feed to the constructor*/
    public static int calcYPos(int index) {
        return index * OBJ_HEIGHT;
    }
	
    public int getXIndex() {
        return index_x;
    }
    
    public int getYIndex() {
        return index_y;
    }
    
    public int getXPos() {
        return pos_x;
    }
    
    public int getYPos() {
        return pos_y;
    }
    
    public static char getObjChar() {
        return objChar;
    }
    

}
