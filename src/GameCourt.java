
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 * 
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {
    
    //Colors
    Color darkGrey = new Color(111, 120, 122);
    Color brightPink = new Color(255, 26, 26);
    
    // create board from input file
    private String inputFileName;
    public static final String DEFAULT_INPUT_FILE = "defaultBoard_cans.txt";
    public static final int BOARD_WIDTH = 15;
    public static final int BOARD_HEIGHT = 15;
    public Item[][] itemBoard = new Item[BOARD_WIDTH][BOARD_HEIGHT];

	// the state of the game logic
	private Player player; // the player, keyboard control
	private boolean playing; // whether the game is running
	private JLabel status; // Current status text (i.e. Running...)
	

	// Game constants
	public static final int COURT_WIDTH = 600;
	public static final int COURT_HEIGHT = 600;
	public static final int SQUARE_VELOCITY = 4;
	// Update interval for timer, in milliseconds
	public static final int INTERVAL = 35;
	
	//game timer values
	public static final int GAME_CLOCK_INTERVAL = 1000;
	public static final int TOTAL_TIME = 30;
	private static int gameTimeVal;
	private JLabel gameTimer;
	
	//Progress bar 
	private JProgressBar suitcase_p;
	private JProgressBar trashcan_p;
	
	
	public GameCourt(JLabel status, JLabel gameTimer, JProgressBar suitcase_p, 
	        JProgressBar trashcan_p, String fileName) {
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// The timer is an object which triggers an action periodically
		// with the given INTERVAL. One registers an ActionListener with
		// this timer, whose actionPerformed() method will be called
		// each time the timer triggers. We define a helper method
		// called tick() that actually does everything that should
		// be done in a single timestep.
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!
		
		//game timer - when it runs out, the game is over
		Timer gameClock = new Timer(GAME_CLOCK_INTERVAL, new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        updateGameTimer();
		    }
		});
		gameClock.start();

		// Enable keyboard focus on the court area.
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);

		// This key listener allows the square to move as long
		// as an arrow key is pressed, by changing the square's
		// velocity accordingly. (The tick method below actually
		// moves the square.)
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT)
					player.v_x = -SQUARE_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					player.v_x = SQUARE_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_DOWN)
					player.v_y = SQUARE_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_UP)
					player.v_y = -SQUARE_VELOCITY;
			}

			public void keyReleased(KeyEvent e) {
				player.v_x = 0;
				player.v_y = 0;
			}
		});

		this.status = status;
		this.gameTimer = gameTimer;
		GameCourt.gameTimeVal = TOTAL_TIME;
		this.suitcase_p = suitcase_p;
		this.trashcan_p = trashcan_p;
		if (!(fileName == null)) {
		    this.inputFileName = fileName;
		} else {
		    this.inputFileName = DEFAULT_INPUT_FILE;
		}
		
	}
	

	/**
	 * (Re-)set the game to its initial state.
	 */
	public void reset() {

	    BoardCreator b = new BoardCreator(inputFileName);
	    itemBoard = b.returnBoard();
	    player = b.returnPlayer();
	    playing = false;
	    status.setForeground(darkGrey);
		status.setText("Press Start");
		gameTimeVal = TOTAL_TIME;
		String gameTimeString = String.valueOf(gameTimeVal);
		gameTimer.setText(gameTimeString);
		Clothes.resetCumPointVal();
		Trash.resetCumPointVal();
        
		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}

	/**
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 */
	void tick() {
		if (playing) {
			// advance the player in their current direction.
			player.move();
			
			//if a player finds an object, update state
			if (player.foundObj(itemBoard)) {
			    int x = player.getXIndex();
			    int y = player.getYIndex();
			    Item obj = itemBoard[x][y];
			    itemBoard[x][y] = null;
			    obj.actionOnPickUp();
			    suitcase_p.setValue(Clothes.getCumPointVal());
			    trashcan_p.setValue(Trash.getCumPointVal());
			}
            
            // check if suitcase is full
            if (Clothes.itemCompleted() && Trash.itemCompleted()) {
                playing = false;
                status.setText("You won!");
                status.setForeground(brightPink);
            } else if (gameTimeVal == 0) {
                playing = false;
                status.setText("You lost!");
                status.setForeground(brightPink);
            }
			// update the display
			repaint();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (int i = 0; i < BOARD_WIDTH; i++) {
		    for (int j = 0; j < BOARD_HEIGHT; j++) {
		        if (itemBoard[i][j] != null) {
		            itemBoard[i][j].draw(g);
		        }
		    }
		}
		player.draw(g);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
	
	/**
	 * countdown game timer
	 */
	private void updateGameTimer() {
	    if (playing) {
	        gameTimeVal--;
	        String gameTimeString = String.valueOf(gameTimeVal);
	        gameTimer.setText(gameTimeString);
	    }
	}
	
	/**
	 * Read in game instructions from a file
	 * @return
	 */
	public String gameInstructions() {
        ReadInstructions r = new ReadInstructions("instructions.txt");
        String info = r.getInstructions();
	    return info;
	}
	
	/**
	 * Start playing & set some values
	 */
	public void start() {
	    reset();
	    playing = true;
        suitcase_p.setValue(0);
        trashcan_p.setValue(0);
        status.setForeground(darkGrey);
        status.setText("Go!");
	    // Make sure that this component has the keyboard focus
        requestFocusInWindow();
	}
	
}
