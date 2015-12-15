/**
 * Clothes are one major abstract Item type. Clothes fill the suitcase.
 * @author Main
 *
 */
public abstract class Clothes extends Item {
    
    //static counter for the amount of clothing points collected
    private static int cumPointVal;
    
    //# of points needed to fill suitcase - can be changed as desired
    public static final int MAX_POINT_VAL = 50;

    public Clothes(int pos_x, int pos_y, int court_width, int court_height,
            int index_x, int index_y, char itemChar, int point_val) {
        super(pos_x, pos_y, court_width, court_height, index_x, index_y, itemChar, point_val);
        cumPointVal = 0;
    }

    @Override
    public void actionOnPickUp() {
        cumPointVal += this.getPointVal();
    }
    
    
    public static int getCumPointVal() {;
        return cumPointVal;
    }
    
    public static void resetCumPointVal() {
        cumPointVal = 0;
    }
    
    /**
     * Determine if enough points have been collected to fill suitcase
     * @return
     */
    public static boolean itemCompleted() {
        if (cumPointVal >= MAX_POINT_VAL) {
            return true;
        }
        return false;
    }

}
