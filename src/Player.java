
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A player, able to move around the room & interact with objects
 * 
 */
public class Player extends GameObj {
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;
	public static final char PLAYER_CHAR = 'P';
	private int playerTest = 7;
    public static final String img_file = "avatar.png";
    private static BufferedImage img;
    
	public Player(int pos_x, int pos_y, int courtWidth, int courtHeight, 
	        int index_x, int index_y) {
		super(INIT_VEL_X, INIT_VEL_Y, pos_x, pos_y, courtWidth,
				courtHeight, index_x, index_y, PLAYER_CHAR);

        try {
            if (img == null) {
                img = ImageIO.read(new File(img_file));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
	}
	
	/**
	 * Determine whether the board array contains a GameObj at the Player's 
	 * current position
	 * @param arr
	 * @return
	 */
	public boolean foundObj(GameObj[][] arr) {
	    
	    int index_x = this.getXIndex();
	    int index_y = this.getYIndex();
	    if (arr[index_x][index_y] != null) {
	        return true;
	    }
	    return false;
	}

	@Override
	public void draw(Graphics g) {
	    g.drawImage(img, this.getXPos(), this.getYPos(), OBJ_WIDTH, OBJ_HEIGHT, null);
	}

	public int isThisAPlayer() {
	    return playerTest;
	}
}
