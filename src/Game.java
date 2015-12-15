/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	public void run() {
		// NOTE : recall that the 'final' keyword notes immutability
		// even for local variables.
	    
	    //COLORS
	    Color paleYellow = new Color(255, 253, 253);
	    Color rose = new Color(253, 222, 222);
	    Color darkGrey = new Color(111, 120, 122);
	    Color brightPink = new Color(255, 26, 26);
	    
	    //FONTS
        Font displayFont = new Font("Verdana", Font.BOLD, 16);
        Font timerFont = new Font("Verdana", Font.BOLD, 24);
	    
		// Top-level frame in which game components live
		// Be sure to change "TOP LEVEL FRAME" to the name of your game
		final JFrame frame = new JFrame("PACK-GIRL");
		frame.setLocation(300, 75);
		frame.setResizable(false);

		//Top panel, for space
		final JPanel top_panel = new JPanel();
		frame.add(top_panel, BorderLayout.NORTH);
		top_panel.add(Box.createRigidArea(new Dimension(800, 30)));
		top_panel.setBackground(paleYellow);
		
	    //Side panel, for space
        final JPanel left_panel = new JPanel();
        frame.add(left_panel, BorderLayout.WEST);
        left_panel.add(Box.createRigidArea(new Dimension(20, 650)));
        left_panel.setBackground(paleYellow);
        
        //Bottom panel, for space
        final JPanel bottom_panel = new JPanel();
        frame.add(bottom_panel, BorderLayout.SOUTH);
        bottom_panel.add(Box.createRigidArea(new Dimension(800, 20)));
        bottom_panel.setBackground(paleYellow);
        
        // Status panel - contains all game status displays
        final JPanel status_panel = new JPanel();
        status_panel.setPreferredSize(new Dimension(180, 650));
        status_panel.setBackground(paleYellow);
        frame.add(status_panel, BorderLayout.EAST);
        
        //status label
        final JLabel status = new JLabel("Press Start");
        status.setFont(displayFont);
        
        
        //game timer
        final JPanel timerPanel = new JPanel();
        timerPanel.setMaximumSize(new Dimension(300, 40));
        timerPanel.setBackground(paleYellow);
        final JLabel timerLabel = new JLabel("Timer");
        timerLabel.setFont(displayFont);
        final JLabel gameTimer = new JLabel("0");
        gameTimer.setFont(timerFont);
        gameTimer.setForeground(brightPink);
        timerPanel.add(gameTimer);
        gameTimer.setHorizontalAlignment(SwingConstants.CENTER);

        
        //suitcase displays
        final JPanel suitcasePanel = new JPanel();
        suitcasePanel.setMaximumSize(new Dimension(300, 40));
        suitcasePanel.setBackground(paleYellow);
        suitcasePanel.setLayout(new BoxLayout(suitcasePanel, BoxLayout.Y_AXIS));
        final JLabel suitcase_lab = new JLabel("Suitcase");
        suitcase_lab.setFont(displayFont);
        final JPanel suitcase_prog = new JPanel();
        suitcase_prog.setLayout(new BoxLayout(suitcase_prog, BoxLayout.X_AXIS));
        suitcase_prog.setBackground(paleYellow);
        final JProgressBar suitcase_p = new JProgressBar(0, Clothes.MAX_POINT_VAL);
        suitcase_p.setValue(0);
        suitcase_p.setMaximumSize(new Dimension(250, 20));
        suitcase_p.setForeground(brightPink);
        suitcase_p.setBackground(darkGrey);
        final JLabel suitcase_max = new JLabel();
        suitcase_max.setText("  / " + String.valueOf(Clothes.MAX_POINT_VAL));
        suitcase_prog.add(suitcase_p);
        suitcase_prog.add(suitcase_max);
        suitcasePanel.add(suitcase_lab);
        suitcasePanel.add(suitcase_prog);
        suitcase_lab.setHorizontalAlignment(SwingConstants.CENTER);
        
        //trash can displays
        final JPanel trashPanel = new JPanel();
        trashPanel.setMaximumSize(new Dimension(300, 40));
        trashPanel.setBackground(paleYellow);
        trashPanel.setLayout(new BoxLayout(trashPanel, BoxLayout.Y_AXIS));
        final JLabel trash_lab = new JLabel("Trash Can");
        trash_lab.setFont(displayFont);
        final JPanel trash_prog = new JPanel();
        trash_prog.setLayout(new BoxLayout(trash_prog, BoxLayout.X_AXIS));
        trash_prog.setBackground(paleYellow);
        final JProgressBar trash_p = new JProgressBar(0, Trash.MAX_POINT_VAL);
        trash_p.setValue(0);
        trash_p.setMaximumSize(new Dimension(250, 20));
        trash_p.setForeground(brightPink);
        trash_p.setBackground(darkGrey);
        final JLabel trash_max = new JLabel();
        trash_max.setText("  / " + String.valueOf(Trash.MAX_POINT_VAL));
        trash_prog.add(trash_p);
        trash_prog.add(trash_max);
        trashPanel.add(trash_lab);
        trashPanel.add(trash_prog);
        trash_lab.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Main playing area
        final GameCourt court = new GameCourt(status, gameTimer, suitcase_p, 
                trash_p, "defaultBoard_cans.txt");
        frame.add(court, BorderLayout.CENTER);
        court.setBackground(rose);

        //start button
        final JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.start();
            }
        });

        // Note here that when we add an action listener to the reset
        // button, we define it as an anonymous inner class that is
        // an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed,
        // actionPerformed() will be called.
        final JButton stop = new JButton("Stop");
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
            }
        });
        
        //info window
        final JButton info = new JButton("Instructions");
        info.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane popup = new JOptionPane();
                String message = court.gameInstructions();
                JOptionPane.showMessageDialog(popup, message);
            }
        });

        //add components to status_panel
        status_panel.setLayout(new BoxLayout(status_panel, BoxLayout.Y_AXIS));
        status_panel.add(Box.createRigidArea(new Dimension(0, 30)));
        status_panel.add(timerLabel);
        status_panel.add(timerPanel);
        status_panel.add(Box.createRigidArea(new Dimension(0, 20)));
        status_panel.add(suitcasePanel);
        status_panel.add(Box.createRigidArea(new Dimension(0, 20)));
        status_panel.add(trashPanel);
        status_panel.add(Box.createRigidArea(new Dimension(0, 20)));
        status_panel.add(status);
        status_panel.add(Box.createRigidArea(new Dimension(0, 20)));
        status_panel.add(start);
        status_panel.add(Box.createRigidArea(new Dimension(0, 10)));
        status_panel.add(stop);
        status_panel.add(Box.createRigidArea(new Dimension(0, 10)));
        status_panel.add(info);
        
		// Put the frame on the screen
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Start game
		court.reset();
	}

	/*
	 * Main method run to start and run the game Initializes the GUI elements
	 * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
	 * this in the final submission of your game.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}
