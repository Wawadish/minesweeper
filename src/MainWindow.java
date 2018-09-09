/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;

/**
 * @author cstuser
 */
public class MainWindow extends JFrame implements KeyListener {


    private int mineCounter = 0;

    //This variable must be static to be set to true or false in the minesweeper menu.
    public static boolean DEBUG_MODE;

    /*The two following two dimensional arrays are linked together, buttons is used on the "front-end" whereas blocks
      is used on the "back-end".*/
    private JButton[][] buttons;
    private Block[][] blocks;

    //This variable will be used to modify the behaviour of buttons when a key is pressed.
    private boolean keyPressed = false;

    //Defining the MainWindow class default constructor
    public MainWindow() {
        minesweepermenu.gameInProgress.setText("Game is in progress...");

        this.addKeyListener(this);

        //Making the window a square, that will fit 8 square buttons (80px Height and Width).
        setSize(640, 640);

        //Creating the buttons in the buttons array(8x8)
        this.setLocationRelativeTo(null);
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

        //creating a container and retrieve the MainWindow's content pane from it.
        Container container = this.getContentPane();
        //setting the layout of our container to a 8x8 grid.
        container.setLayout(new GridLayout(8, 8));

        //Creating an two dimensional array of JButtons which will be filled later.
        buttons = new JButton[8][8];
        //Creating a two dimensional array of Blocks to be filled with Empty Blocks, Mine Blocks and Number Blocks.
        blocks = new Block[8][8];

        //Making all Blocks in the blocks array Empty Blocks.
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                blocks[i][j] = Block.EMPTY;
            }
        }

        //Randomly generating 10 Mine Blocks in the blocks array and replacing them with Empty Blocks.
        while (mineCounter != 10) {
            int x = (int) (Math.random() * 8);
            int y = (int) (Math.random() * 8);
            if (blocks[x][y] != Block.MINE) {
                blocks[x][y] = Block.MINE;
                mineCounter++;
            }
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    checkMine(blocks, i, j);
                }
            }
        }


        //Starting two big loops, the variables i and j will be used by both blocks array and buttons array.
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                //Displaying the mines in case the user in on DEBUG_MODE
                if (blocks[i][j] != Block.MINE) {
                    buttons[i][j] = new JButton();
                } else {
                    if (DEBUG_MODE) {
                        buttons[i][j] = new JButton("Mine");
                    } else {
                        buttons[i][j] = new JButton();
                    }
                }

                //Making the buttons 80px x 80px to fit the main window size.

                buttons[i][j].setSize(80, 80);
                final int row = i;
                final int column = j;

                //Setting the event which will happen once the button is clicked.

                buttons[i][j].addActionListener((e) -> {

                    if (!keyPressed) {

                        //This code is executed every time a button  is clicked.
                        //Disabling the button.
                        buttons[row][column].setEnabled(false);

                        //Depending on which type of block was clicked, execute a different command
                        switch (blocks[row][column]) {

                            //For number blocks, set the block text to whichever number it is.
                            case ONE:
                                buttons[row][column].setText("1");
                                break;
                            case TWO:
                                buttons[row][column].setText("2");
                                break;
                            case THREE:
                                buttons[row][column].setText("3");
                                break;
                            case FOUR:
                                buttons[row][column].setText("4");
                                break;
                            case FIVE:
                                buttons[row][column].setText("5");
                                break;
                            case SIX:
                                buttons[row][column].setText("6");
                                break;
                            case SEVEN:
                                buttons[row][column].setText("7");
                                break;
                            case EIGHT:
                                buttons[row][column].setText("8");
                                break;
                            case EMPTY:
                                emptyCheck(row, column);
                                break;
                            case MINE:

                                // If the block you clicked on is a mine, update the game menu and get rid of this frame.
                                minesweepermenu.numberGamesLost++;
                                minesweepermenu.gamesLost.setText("Games Lost:\n" + minesweepermenu.numberGamesLost);
                                minesweepermenu.gameInProgress.setText("The game is done. You lost!");
                                this.dispose();
                        }
                    } else {
                        buttons[row][column].setText("Flag");
                    }
                });

                //Adding the buttons to the Main Window
                this.add(buttons[i][j]);
                if (DEBUG_MODE) {
                    switch (blocks[i][j]) {
                        case ONE:
                            buttons[i][j].setText("1");
                            break;
                        case TWO:
                            buttons[i][j].setText("2");
                            break;
                        case THREE:
                            buttons[i][j].setText("3");
                            break;
                        case FOUR:
                            buttons[i][j].setText("4");
                            break;
                        case FIVE:
                            buttons[i][j].setText("5");
                            break;
                        case SIX:
                            buttons[i][j].setText("6");
                            break;
                        case SEVEN:
                            buttons[i][j].setText("7");
                            break;
                        case EIGHT:
                            buttons[i][j].setText("8");
                            break;
                    }
                }

                //The two following brackets mark the end of the two big for loops
            }
        }
        //The two previous brackets mark the end of the two big for loops.

        //Display the frame.
        this.setVisible(true);
    }
    //The above bracket marks the end of the Main Window constructor.


    /*This method utilizes recursion to open up all adjacent Empty Blocks, it calls it self again in the check method.
      This method also shows Number Blocks adjacent to empty Blocks. It basically uses the check method on all 4
      directions up, down, left and right*/
    public void emptyCheck(int x, int y) {

        try {
            //Makes the program check every block under.
            check(x + 1, y);
            //Makes the program check every block above.
            check(x - 1, y);
            //Makes the program check every block to the right.
            check(x, y + 1);
            //Makes the program check every block to the left.
            check(x, y - 1);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /*The following method is recursive and disables adjacent Empty or Number Blocks, it will only use recursion if
      it's inputted Block is an Empty (Not a Number Block).*/
    public void check(int x, int y) {
        if ((x != 8) && (y != -1) && (x != -1) && (y != 8) && (blocks[x][y] == Block.ONE || blocks[x][y] == Block.TWO ||
                blocks[x][y] == Block.THREE || blocks[x][y] == Block.FOUR || blocks[x][y] == Block.FIVE
                || blocks[x][y] == Block.SIX || blocks[x][y] == Block.SEVEN || blocks[x][y] == Block.EIGHT)) {
            buttons[x][y].setEnabled(false);
            buttons[x][y].setText(getBlockString(blocks[x][y]));
        }
        if ((x != 8) && (x != -1) && (y != 8) && (y != -1) && blocks[x][y] == Block.EMPTY && buttons[x][y].isEnabled()){
            buttons[x][y].setEnabled(false);
            emptyCheck(x, y);
        }
    }


    /*This method is used to set the value of a Number Block from 1 to 8, it checks for adjacent Mine Blocks and will
      increment the value of the Number Block for each surrounding Mine Block.*/
    public void checkMine(Block blocks[][], int x, int y) {
        int tempCounter = 0;

        /*The following statements look for Mine Blocks around the given Block (X , Y), it will also check if the
        array is out of bounds. */
        try {
            if ((x + 1 != 8) && blocks[x + 1][y] == Block.MINE) {
                tempCounter++;
            }
            if ((y + 1 != 8) && blocks[x][y + 1] == Block.MINE) {
                tempCounter++;
            }
            if ((x - 1 != -1) && blocks[x - 1][y] == Block.MINE) {
                tempCounter++;
            }
            if ((y - 1 != -1) && blocks[x][y - 1] == Block.MINE) {
                tempCounter++;
            }
            if ((x + 1 != 8) && (y - 1 != -1) && blocks[x + 1][y - 1] == Block.MINE) {
                tempCounter++;
            }
            if ((y + 1 != 8) && (x + 1 != 8) && blocks[x + 1][y + 1] == Block.MINE) {
                tempCounter++;
            }
            if ((x - 1 != -1) && (y + 1 != 8) && blocks[x - 1][y + 1] == Block.MINE) {
                tempCounter++;
            }
            if ((y - 1 != -1) && (x - 1 != -1) && blocks[x - 1][y - 1] == Block.MINE) {
                tempCounter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*The following statements will set the given Block (X , Y) to a block representing the amount of
          surrounding Mine Blocks.*/
        if(blocks[x][y] != Block.MINE) {
            switch (tempCounter) {
                case 1:

                    blocks[x][y] = Block.ONE;
                    break;

                case 2:

                    blocks[x][y] = Block.TWO;
                    break;

                case 3:

                    blocks[x][y] = Block.THREE;
                    break;

                case 4:

                    blocks[x][y] = Block.FOUR;
                    break;

                case 5:

                    blocks[x][y] = Block.FIVE;
                    break;

                case 6:

                    blocks[x][y] = Block.SIX;
                    break;

                case 7:

                    blocks[x][y] = Block.SEVEN;
                    break;

                case 8:

                    blocks[x][y] = Block.EIGHT;
                    break;
            }
        }
    }

    //This method will convert the name of Block to a digit and return it as a String.
    public String getBlockString(Block b) {
        switch (b) {
            case ONE:
                return "1";
            case TWO:
                return "2";
            case THREE:
                return "3";
            case FOUR:
                return "4";
            case FIVE:
                return "5";
            case SIX:
                return "6";
            case SEVEN:
                return "7";
            case EIGHT:
                return "8";
            default:
                return "error";
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_C) {
            keyPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            keyPressed = false;
        }
    }
}
