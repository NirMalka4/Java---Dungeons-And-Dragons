package GameManager;
import Reader.Reader;
import Board.*;
import TilesFactory.TilesFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameManager
{
    private final Reader reader;
    private Board board;
    private final int left = 1, right =6;
    private final BufferedReader inputReader;
    private final String[] playersName = {null, "Jon Snow", "The Hound", "Melisandre", "Thoros of Myr", " Arya Stark", "Bronn"};
    private final Printer printer = PrinterImpl.getInstance();
    private final char ASCII_OFFSET = '0';

    public GameManager(String path)
    {
        reader = new Reader(path);
        inputReader = new BufferedReader(new InputStreamReader(System.in));
    }

    // Verify legal player choose
    // On success return selected player number
    // On failure return -1
    private int getChoice(String str)
    {
        int i = str.charAt(0) - ASCII_OFFSET;
        return  i >= left && i <= right ? i: // legal input
                -1; // illegal input
    }

    private boolean NotLegalChoice(int choice)
    {
        return choice == -1;
    }

    private boolean gameRun(){return board.cont();}

    private boolean legalMove(String move)
    {
        return move.equals("a") | move.equals("s") | move.equals("d") | move.equals("w") | move.equals("e") | move.equals("q");

    }

    //prompt characters
    private void promptPlayers()
    {
        printer.print("Choose your player:\n");
        TilesFactory.getInstance().showPlayers().execute();
    }

    // Lunch the game
    // Load next level either player won current level and path directory includes more level.
    // Prompt players menu only at the lunching of the first level.
    public void run() throws IOException
    {
        int choice = -1;
        String input;
        promptPlayers();
        while (NotLegalChoice(choice))
        {
            input = inputReader.readLine();
            choice = getChoice(input);
            if (NotLegalChoice((choice)))
                printer.print(String.format("You may enter value from %d to %d\n", left, right));
        }
        printer.print(String.format("You have selected:\n%s\n", playersName[choice]));
        board = new Board(reader, choice);
        // start game
        while(true)
        {
            board.proceedNextLevel();
            while (gameRun())
            {
                MessageCallback initialInfo = board.describe();
                initialInfo.execute();
                String move = inputReader.readLine();
                while (!legalMove(move))
                {
                    printer.print("You may enter one of the following: a, s, d, e, w, q\n");
                    move = inputReader.readLine();
                }
                MessageCallback sessionReport = board.gameTick(move);
                sessionReport.execute();
            }
            if(board.playerIsAlive()) // player has won current level.
            {
                if(reader.hasNextLevel()) // if next level is exists, load it and proceed.
                {
                    reader.loadNextLevel();
                    printer.print("Congratulations: you are advanced to the next level. Good Luck :)\n ");
                }
                else
                {
                    printer.print("You passed all levels. MAD RESPECT!\n ");
                    break;
                }
            }
            else // player is dead.
            {
                board.describe().execute(); // display board status
                break;
            }
        }
    }
}
