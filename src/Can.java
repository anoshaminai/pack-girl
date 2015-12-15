import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * Can is of Trash Item type. This class provides a useful template for creating 
 * other Trash items
 * @author Main
 *
 */
public class Can extends Trash {

    private static final int POINT_VAL = 10;
    public static final char CAN_CHAR = 'C';
    public static final String img_file = "can.png";
    private static BufferedImage img;
    
    public Can(int pos_x, int pos_y, int court_width, int court_height, int index_x, int index_y) {
        super(pos_x, pos_y, court_width, court_height, index_x, index_y, CAN_CHAR, POINT_VAL);
        
        try {
            if (img == null) {
                img = ImageIO.read(new File(img_file));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }
    
    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getXPos(), this.getYPos(), OBJ_WIDTH, OBJ_HEIGHT, null);
    }

}
