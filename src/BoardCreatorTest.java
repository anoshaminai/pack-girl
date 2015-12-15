import static org.junit.Assert.assertEquals;
import java.io.FileNotFoundException;


import org.junit.Test;

public class BoardCreatorTest {
    
    @Test
    public void testBoardCreatorFileNotFound() throws FileNotFoundException {
        BoardCreator b = new BoardCreator("fakefile.txt");
    }
    
    @Test
    public void testBoardCreator() throws FileNotFoundException {
        BoardCreator b = new BoardCreator("simple.txt");
        Item[][] board = b.returnBoard();
        Player player = b.returnPlayer();
        
        //check shirt
        int pos_x = GameObj.calcXPos(1);
        int pos_y = GameObj.calcYPos(0);
        Item item = board[1][0];
        assertEquals(item.getPointVal(), 10);
        assertEquals(item.getXIndex(), 1);
        assertEquals(item.getYIndex(), 0);
        assertEquals(item.getXPos(), pos_x);
        assertEquals(item.getYPos(), pos_y);
        
        //check player
        int p_pos_x = GameObj.calcXPos(3);
        int p_pos_y = GameObj.calcYPos(0);
        assertEquals(player.isThisAPlayer(), 7);
        assertEquals(player.getXPos(), p_pos_x);
        assertEquals(player.getYPos(), p_pos_y);
    }
    
    @Test
    public void testNoPlayerInput() throws FileNotFoundException {
        BoardCreator b = new BoardCreator("simple_noplayer.txt");
        Player player = b.returnPlayer();
        
        //check player
        int p_pos_x = GameObj.calcXPos(0);
        int p_pos_y = GameObj.calcYPos(0);
        assertEquals(player.isThisAPlayer(), 7);
        assertEquals(player.getXPos(), p_pos_x);
        assertEquals(player.getYPos(), p_pos_y);
        
    }
    
    @Test
    public void testMultPlayersInput() throws FileNotFoundException {
        BoardCreator b = new BoardCreator("simple_multplayers.txt");
        Player player = b.returnPlayer();
        
        //check player
        int p_pos_x = GameObj.calcXPos(3);
        int p_pos_y = GameObj.calcYPos(0);
        assertEquals(player.isThisAPlayer(), 7);
        assertEquals(player.getXPos(), p_pos_x);
        assertEquals(player.getYPos(), p_pos_y);
        
        //check other player's indices are null
        Item[][] board = b.returnBoard();
        assertEquals(board[5][0], null);
    }
    
    @Test
    public void testItemInDefaultPlayerLoc() throws FileNotFoundException {
        BoardCreator b = new BoardCreator("simple_itemdefault.txt");
        Player player = b.returnPlayer();
        
        //check player
        int p_pos_x = GameObj.calcXPos(0);
        int p_pos_y = GameObj.calcYPos(0);
        assertEquals(player.isThisAPlayer(), 7);
        assertEquals(player.getXPos(), p_pos_x);
        assertEquals(player.getYPos(), p_pos_y);
        
        //check player's indices are null (no item also stored there)
        Item[][] board = b.returnBoard();
        assertEquals(board[0][0], null);
    }
    
    @Test
    public void testEmptyInputFile() throws FileNotFoundException {
        BoardCreator b = new BoardCreator("empty.txt");
        Item[][] board = b.returnBoard();
        Player player = b.returnPlayer();
        
        //check shirt
        int pos_x = GameObj.calcXPos(0);
        int pos_y = GameObj.calcYPos(14);
        Item item = board[0][14];
        assertEquals(item.getPointVal(), 10);
        assertEquals(item.getXIndex(), 0);
        assertEquals(item.getYIndex(), 14);
        assertEquals(item.getXPos(), pos_x);
        assertEquals(item.getYPos(), pos_y);
        
        //check player
        int p_pos_x = GameObj.calcXPos(14);
        int p_pos_y = GameObj.calcYPos(0);
        assertEquals(player.isThisAPlayer(), 7);
        assertEquals(player.getXPos(), p_pos_x);
        assertEquals(player.getYPos(), p_pos_y);
    }
    
    @Test
    public void testAllOtherCharsCreateNulls() throws FileNotFoundException {
        BoardCreator b = new BoardCreator("simple_otherchars.txt");
        Item[][] board = b.returnBoard();
        
        //check all other chars
        assertEquals(board[5][0], null);
        assertEquals(board[7][0], null);
        assertEquals(board[9][0], null);
        assertEquals(board[11][0], null);

    }
    
    @Test
    public void testInputFileShort() throws FileNotFoundException {
        BoardCreator b = new BoardCreator("simple_short.txt");
        Item[][] board = b.returnBoard();
        Player player = b.returnPlayer();
        
        //check player
        int p_pos_x = GameObj.calcXPos(0);
        int p_pos_y = GameObj.calcYPos(0);
        assertEquals(player.isThisAPlayer(), 7);
        assertEquals(player.getXPos(), p_pos_x);
        assertEquals(player.getYPos(), p_pos_y);
        
        //check board
        assertEquals(board[3][0], null);
        assertEquals(board[2][2], null);
        
    }
    
    @Test
    public void testInputFileLong() throws FileNotFoundException {
        BoardCreator b = new BoardCreator("board_long.txt");
        Item[][] board = b.returnBoard();
        Player player = b.returnPlayer();
        
        //check player
        int p_pos_x = GameObj.calcXPos(0);
        int p_pos_y = GameObj.calcYPos(0);
        assertEquals(player.isThisAPlayer(), 7);
        assertEquals(player.getXPos(), p_pos_x);
        assertEquals(player.getYPos(), p_pos_y);
        assertEquals(board[0][0], null);
        
        assertEquals(board.length, 15);
        assertEquals(board[0].length, 15);
    }
    
    @Test
    public void testInstructions() throws FileNotFoundException {
        ReadInstructions r = new ReadInstructions("short_instructions.txt");
        String info = r.getInstructions();
        String expected = "hello\nThese are my game instructions!\n";
        assertEquals(info, expected);
    }
    

}
