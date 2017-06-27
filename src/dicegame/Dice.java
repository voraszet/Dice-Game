package dicegame;


import java.awt.Color;
import javax.swing.*;
import java.awt.event.*;
import java.awt.GridLayout;
import java.util.*;
import java.util.Scanner;
import java.io.*;

class MyWindowListener extends WindowAdapter {

    public void windowClosing(WindowEvent e) {
        System.out.println("Window closed!");
        System.exit(0);
        
    }
}
//button event handler class

class MyActionListener implements ActionListener {

    ImageIcon newGameImg = new ImageIcon("img/random.png");

    JButton throwDice;
    JButton score;
    JButton newGame;
    Dice theDice;
    JButton[] playerButton;
    JButton[] computerButton;

    JCheckBox[] playerBox;
    JCheckBox[] computerBox;

    JLabel playerScoreLabel;
    JLabel computerScoreLabel;
    JLabel WinsL;
    JLabel DefeatsL;

    int playerScore;
    int computerScore;
    int wins;
    int defeats;

    int playerScoreRolled;
    int computerScoreRolled;

    int numberofRolls;
    
    File fp = new File("my_data.txt");
     void write() {
        try { 
            PrintWriter pw = new PrintWriter(fp);
            pw.print("Wins : ");
            pw.println(wins);
            pw.print("Defeats : ");
            pw.println(defeats);
            pw.close();  
        }
        catch (Exception ex) {
            ex.printStackTrace(); 
        }
    }
    
    void read() {
        try {
            Scanner sc = new Scanner(fp);
            while (sc.hasNext()) {
                
                int i = sc.nextInt();
                System.out.println(i);
               
               //WinsL.setText(Integer.toString(wins));
               //DefeatsL.setText(Integer.toString(defeats));
                //System.out.println(i);

	    }
	}
        catch (FileNotFoundException ex) {
            System.err.println("Exception: " + ex);
        }
    }
     
    
    public void playerReRoll() {
        ArrayList<Die> playerReRoll;

        playerReRoll = theDice.roll();
        for (int i = 0; i < 5; i++) {
            theDice.playerDiceArray.set(i, playerReRoll.get(i));
            playerButton[i].setIcon(theDice.playerDiceArray.get(i).getDieImage());
        }
    }

    public void computerReRoll() {
        ArrayList<Die> computerReRoll;

        computerReRoll = theDice.roll();
        for (int i = 0; i < 5; i++) {
            theDice.computerDiceArray.set(i, computerReRoll.get(i));
            computerButton[i].setIcon(theDice.computerDiceArray.get(i).getDieImage());
        }
    }

    public void checkChecked(int passedI) {
        int i = passedI;
        ArrayList<Die> temp;
        temp = theDice.roll();

        if (!playerBox[i].isSelected()) {
            theDice.playerDiceArray.set(i, temp.get(i));
            playerButton[i].setIcon(theDice.playerDiceArray.get(i).getDieImage());

        } else {
            playerButton[i].setIcon(theDice.playerDiceArray.get(i).getDieImage());
        }
    }

    public void checkScore() {
        if (computerScore > playerScore && computerScore > 100) {
            JOptionPane.showMessageDialog(null, "You lose!");
            playerScoreLabel.setText(Integer.toString(playerScore));
            defeats++;
            DefeatsL.setText(Integer.toString(defeats));
            

        } else if (playerScore > computerScore && playerScore > 100) {
            JOptionPane.showMessageDialog(null, "Congratulations, you won! Your score : " + playerScore);
            wins++;
            WinsL.setText(Integer.toString(wins));
            
            
        } else if (playerScore > 100 && computerScore > 100 && playerScore == computerScore) {
            JOptionPane.showMessageDialog(null, "Draw!");
            for (int i = 0; i < 5; i++) {
                checkChecked(i);
            }
            
        }
        
    }

    MyActionListener(JButton throwTheDice, JButton theScore, Dice d, JButton[] pButton, JButton[] cButton, JLabel pText, JLabel cText, JCheckBox[] pBox, JButton nGame, JLabel win, JLabel def) {
        throwDice = throwTheDice;
        score = theScore;
        newGame = nGame;

        theDice = d;
        playerButton = pButton;
        computerButton = cButton;

        playerScoreLabel = pText;
        computerScoreLabel = cText;

        WinsL = win;
        DefeatsL = def;

        playerBox = pBox;
        
        
    }

    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == throwDice) {
            
            numberofRolls++;
            score.setEnabled(true);
            for (int i = 0; i < 5; i++) {
                playerBox[i].setEnabled(true);
            }

            if (numberofRolls == 1) {
                playerReRoll();
                computerReRoll();

            } else if (numberofRolls < 3) {
                computerReRoll();
                for (int i = 0; i < 5; i++) {
                    
                    //FOR ROLL
                    checkChecked(i);
                    //Collections.sort(theDice.playerDiceArray);
                    //Collections.sort(theDice.computerDiceArray);
                }
            } else {
                computerReRoll();
                for (int i = 0; i < 5; i++) {
                    checkChecked(i);
                    //Collections.sort(theDice.playerDiceArray);
                    //Collections.sort(theDice.computerDiceArray);
                    playerScore = playerScore + theDice.playerDiceArray.get(i).getValue();
                    computerScore = computerScore + theDice.computerDiceArray.get(i).getValue();

                    playerScoreLabel.setText(Integer.toString(playerScore));
                    computerScoreLabel.setText(Integer.toString(computerScore));

                    numberofRolls = 0;
                    score.setEnabled(false);

                    playerBox[i].setEnabled(false);
                    playerBox[i].setSelected(false);
                }

            }

            System.out.println(numberofRolls);
            checkScore();
            //END OF THROW BUTTON

        }
        if (event.getSource() == score) {
            //theDice.roll();
            for (int i = 0; i < 5; i++) {
                //Collections.sort(theDice.playerDiceArray);
                //Collections.sort(theDice.computerDiceArray);
                playerScore = playerScore + theDice.playerDiceArray.get(i).getValue();
                computerScore = computerScore + theDice.computerDiceArray.get(i).getValue();

                playerScoreLabel.setText(Integer.toString(playerScore));
                computerScoreLabel.setText(Integer.toString(computerScore));
                score.setEnabled(false);
                numberofRolls = 0;
            }

            checkScore();
            //END OF SCORE BUTTON    
        }

        if (event.getSource() == newGame) {
            //read();
            computerScore = 0;
            playerScore = 0;
            playerScoreLabel.setText(Integer.toString(playerScore));
            computerScoreLabel.setText(Integer.toString(computerScore));

            for (int i = 0; i < 5; i++) {
                playerButton[i].setIcon(newGameImg);
                computerButton[i].setIcon(newGameImg);
            }
            
        }
        //save the file
        write();
        
    }
}

public class Dice extends Die {

    ArrayList<Die> dieFaceArray;
    ArrayList<Die> playerDiceArray;
    ArrayList<Die> computerDiceArray;

    int numberofRolls = 0;
    int randomNumber;

    public void generateRandom() {
        Random rnd = new Random();

        System.out.println("done");

        System.out.println(randomNumber = rnd.nextInt(6));
    }

    public ArrayList<Die> roll() {
        Random random = new Random();
        ArrayList<Die> returning = new ArrayList<Die>();
        for (int i = 0; i < 5; i++) {
            //playerDiceArray.set(i, dieFaceArray.get(random.nextInt(6)));
            returning.add(dieFaceArray.get(random.nextInt(6)));
        }
        return returning;
    }

    public Dice() {
        dieFaceArray = new ArrayList<Die>(5);
        playerDiceArray = new ArrayList<Die>();
        computerDiceArray = new ArrayList<Die>();

        for (int i = 0; i < 6; i++) {
            //creates 6 dieface objects          
            Die dieFace = new Die();
            //adds the objects to array
            dieFaceArray.add(dieFace);
            //sets the values for relavant objects
            dieFaceArray.get(i).setValue(i + 1);
        }
        for (int i = 0; i < 5; i++) {
            playerDiceArray.add(null);
            computerDiceArray.add(null);
        }

        JFrame frame = new JFrame("Dice game!");
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();
        JPanel panel6 = new JPanel();
        JPanel panel7 = new JPanel();
        JPanel panel8 = new JPanel();

        //Player/Computer score
        JLabel playerLabel = new JLabel("Player Score: ");
        JLabel computerLabel = new JLabel("Computer Score: ");
        JLabel playerScoreLabel = new JLabel(" 0 ");
        JLabel computerScoreLabel = new JLabel(" 0 ");

        JLabel WinsLabel = new JLabel("WINS ");
        JLabel WinsL = new JLabel(" 0 ");
        JLabel defeatLabel = new JLabel("DEFEAT");
        JLabel DefeatsL = new JLabel(" 0 ");

        ImageIcon startImage = new ImageIcon("H:/DiceGame/img/random.png");

        //CHECKBOX ARRAYS
        JCheckBox[] playerBox = new JCheckBox[5];
        JCheckBox[] computerBox = new JCheckBox[5];

        //BUTTON ARRAYS
        JButton[] playerButton = new JButton[5];
        JButton[] computerButton = new JButton[5];

        for (int i = 0; i < 5; i++) {
            playerButton[i] = new JButton();
            playerButton[i].setIcon(startImage);
            playerButton[i].setBorder(null);
        }

        for (int i = 0; i < 5; i++) {
            computerButton[i] = new JButton();
            computerButton[i].setIcon(startImage);
            computerButton[i].setBorder(null);
        }

        for (int i = 0; i < 5; i++) {
            playerBox[i] = new JCheckBox();
        }

        for (int i = 0; i < 5; i++) {
            computerBox[i] = new JCheckBox();
        }

        JButton score = new JButton("Score");
        JButton throwDice = new JButton("Throw");
        //JButton reset = new JButton("Reset");
        JButton newGame = new JButton("New Game");

        frame.setLayout(new GridLayout(8, 1));

        JLabel label1 = new JLabel("Player");
        JLabel label2 = new JLabel("Computer");

        panel.add(label1);
        for (int i = 0; i < 5; i++) {
            panel.add(playerButton[i]);
        }

        for (int i = 0; i < 5; i++) {
            panel2.add(playerBox[i]);
        }

        panel3.add(label2);
        for (int i = 0; i < 5; i++) {
            panel3.add(computerButton[i]);
        }

        for (int i = 0; i < 5; i++) {
            panel4.add(computerBox[i]);
        }

        panel5.add(playerLabel);
        panel5.add(playerScoreLabel);
        panel5.add(computerLabel);
        panel5.add(computerScoreLabel);
        panel6.add(WinsLabel);
        panel6.add(WinsL);
        panel6.add(defeatLabel);
        panel6.add(DefeatsL);
        panel7.add(score);
        panel7.add(throwDice);
        //panel6.add(reset);
        panel8.add(newGame);

        frame.setVisible(true);
        frame.setSize(750, 875);
        frame.getContentPane().add(panel, "North");
        frame.getContentPane().add(panel2, "North");
        frame.getContentPane().add(panel3, "North");
        frame.getContentPane().add(panel4, "North");
        frame.getContentPane().add(panel5, "North");
        frame.getContentPane().add(panel6, "North");
        frame.getContentPane().add(panel7, "North");
        frame.getContentPane().add(panel8, "North");

        panel.setBackground(Color.white);
        panel2.setBackground(Color.white);
        panel3.setBackground(Color.white);
        panel4.setBackground(Color.white);
        panel5.setBackground(Color.white);
        panel6.setBackground(Color.white);
        panel7.setBackground(Color.white);
        panel8.setBackground(Color.white);

        MyActionListener actionListener = (new MyActionListener(throwDice, score, this, playerButton, computerButton, playerScoreLabel, computerScoreLabel, playerBox, newGame, WinsL, DefeatsL));
        throwDice.addActionListener(actionListener);
        score.addActionListener(actionListener);
        newGame.addActionListener(actionListener);

        frame.addWindowListener(new MyWindowListener());
    }
}
