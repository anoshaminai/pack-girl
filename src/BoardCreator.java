import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * This class reads in an input file and creates a game board from it
 * @author Main
 *
 */
public class BoardCreator {
    
    // keep track of whether a player has been created
    private boolean createdPlayer;
    private Player player;
    private Item[][] board;
    
    public BoardCreator(String fileName) {

        createdPlayer = false;
        int numCols = GameCourt.BOARD_WIDTH;
        int numRows = GameCourt.BOARD_HEIGHT;
        board = new Item[numCols][numRows];
        
        Scanner in;
        try {
            in = new Scanner(new File(fileName));
            if (!in.hasNext()) {
                in.close();
                in = new Scanner(new File("defaultBoard.txt"));
            }
            
            int numReadLines = 0;
            while (in.hasNext() && numReadLines < numRows) {
                String line = in.next().trim();
                char[] lineChars = line.toCharArray();
                int numReadChars = 0;
                int i = 0;
                while (i < lineChars.length && numReadChars < numCols) {
                    findObjType(lineChars[i], i, numReadLines, board);
                    i++;
                    numReadChars++;
                }
                numReadLines++;
            }
            
            in.close();
            
            if (!createdPlayer) {
                int pos_x = GameObj.calcXPos(0);
                int pos_y = GameObj.calcYPos(0);
                this.player = new Player(pos_x, pos_y, GameCourt.COURT_WIDTH, 
                        GameCourt.COURT_HEIGHT, 0, 0);
                board[0][0] = null;
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Determine what object type a character refers to
     * @param objChar
     * @param x
     * @param y
     * @param arr
     */
    private void findObjType(char objChar, int x, int y, Item arr[][]) {
        char objCharUpper = Character.toUpperCase(objChar);
        if (objCharUpper == Shirt.SHIRT_CHAR) {
            int pos_x = GameObj.calcXPos(x);
            int pos_y = GameObj.calcYPos(y);
            arr[x][y] = new Shirt(pos_x, pos_y, GameCourt.COURT_WIDTH, 
                    GameCourt.COURT_HEIGHT, x, y);
        } else if (objCharUpper == Player.PLAYER_CHAR && !createdPlayer) {
            int pos_x = GameObj.calcXPos(x);
            int pos_y = GameObj.calcYPos(y);
            this.player = new Player(pos_x, pos_y, GameCourt.COURT_WIDTH, 
                    GameCourt.COURT_HEIGHT, x, y);
            createdPlayer = true;
            arr[x][y] = null;
        } else if (objCharUpper == Can.CAN_CHAR) {
            int pos_x = GameObj.calcXPos(x);
            int pos_y = GameObj.calcYPos(y);
            arr[x][y] = new Can(pos_x, pos_y, GameCourt.COURT_WIDTH, 
                    GameCourt.COURT_HEIGHT, x, y);
        } else {
            arr[x][y] = null;
        }
    }
    
    public Item[][] returnBoard() {
        return this.board;
    }
    
    public Player returnPlayer() {
        return this.player;
    }

}
