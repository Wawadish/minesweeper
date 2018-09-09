/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author Sam
 */
public class minesweepermenu extends JFrame {
    
    //Menu Bar
    private JMenuBar mainMenu;
    private JMenu menu;
    private JCheckBoxMenuItem debugMode;

    // Start Button
    private JButton startGame;

    //Text Labels
    public static JLabel gamesWon;
    public static JLabel gamesLost;
    public static JLabel gameInProgress;

    //game in progress?
    private boolean gameInProgressBoolean;

    //number of games Won and Lost
    private JMenuItem exit;
    public static int numberGamesWon;
    public static int numberGamesLost;
    
    public minesweepermenu() {
        //JFrame 
        super("Mine Sweeper Game Menu");
        setSize(16*30, 9*30);
        
        this.setLocationRelativeTo(null);
        //Menu Bar
        mainMenu = new JMenuBar();
        menu = new JMenu("Game");
        mainMenu.add(menu);
        debugMode = new JCheckBoxMenuItem("Debug Mode", false);
        debugMode.setAccelerator(KeyStroke.getKeyStroke('D', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        debugMode.addActionListener((ActionEvent ae) -> {
            MainWindow.DEBUG_MODE = debugMode.isSelected();
        });
        exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0,false));
        exit.addActionListener((ActionEvent ae) -> {
            System.exit(0);
        });
        
        menu.add(exit);
        menu.add(debugMode);
        
        //StartGame Button
        startGame = new JButton("Start Game");
        startGame.addActionListener((ActionEvent ae) -> {
            MainWindow mineSweeperGame = new MainWindow(); 
        });
        
        //games Won
        gamesWon = new JLabel("Games Won: ");
        
        //games Lost
        gamesLost = new JLabel("Games Lost: ");
        
        //game in progress
        gameInProgress = new JLabel("Press Start to launch the game!");
        gameInProgress.setVisible(true);
        
        //Creating container
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());
        
        //Adding Main menu
        pane.add(mainMenu,BorderLayout.PAGE_START );
        //Adding games won label
        pane.add(gamesWon, BorderLayout.LINE_START);
        //Adding start game button
        pane.add(startGame, BorderLayout.CENTER);
        //Adding games lost label
        pane.add(gamesLost, BorderLayout.LINE_END);
        //Adding game in progress label
        pane.add(gameInProgress, BorderLayout.PAGE_END);
        
        
    }
    
    public static void main(String[] args) {
        minesweepermenu menu = new minesweepermenu();
        menu.setVisible(true);
    }
}
