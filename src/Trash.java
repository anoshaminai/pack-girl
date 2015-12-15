
public abstract class Trash extends Item {

    private static int cumPointVal;
    public static final int MAX_POINT_VAL = 50;
    
    public Trash(int pos_x, int pos_y, int court_width, int court_height, int index_x, int index_y, char itemChar,
            int pointVal) {
        super(pos_x, pos_y, court_width, court_height, index_x, index_y, itemChar, pointVal);
        cumPointVal = 0;
    }

    @Override
    public void actionOnPickUp() {
        cumPointVal += this.getPointVal();
    }
    
    public static String getCumPointString() {
        String cumPointString = String.valueOf(cumPointVal);
        return cumPointString;
    }
    
    public static int getCumPointVal() {;
    return cumPointVal;
    }
    
    public static void resetCumPointVal() {
        cumPointVal = 0;
    }

    public static boolean itemCompleted() {
        if (cumPointVal >= MAX_POINT_VAL) {
            return true;
        }
        return false;
    }

}
