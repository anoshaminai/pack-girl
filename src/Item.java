/**
 * Abstract class for any object that has a point value and can be collected
 * @author Main
 *
 */
public abstract class Item extends GameObj {

    private int pointVal;
    
    //Items can't move
    private static final int V_X = 0;
    private static final int V_Y = 0;
    
    
    public Item(int pos_x, int pos_y, int court_width, int court_height,
            int index_x, int index_y, char itemChar, int pointVal) {
        super(V_X, V_Y, pos_x, pos_y, court_width, court_height, index_x, index_y, itemChar);
        this.pointVal = pointVal;
    }
    
    public int getPointVal() {
        return pointVal;
    }
    
    abstract public void actionOnPickUp();

}
