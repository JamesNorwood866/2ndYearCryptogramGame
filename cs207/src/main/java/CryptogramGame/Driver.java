package CryptogramGame;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {




    public static void main(String args[]) throws IOException {
        Game g = new Game();

        players playerList = new players();

        //Create scanner and ask for userName
        System.out.println();
        Scanner getInputName = new Scanner(System.in);
        System.out.println("Enter your username, or create a new username.");
        String name = getInputName.nextLine();
        player currentPlayer;


        if(playerList.findPlayer(name) == null){
            currentPlayer = new player(name);
            playerList.addPlayer(currentPlayer);
        } else {
            currentPlayer = playerList.findPlayer(name);
        }
        File file = new File(name + "save.txt");


        while(true) {

            //Create scanner and ask for input
            System.out.println();
            Scanner myObj = new Scanner(System.in);
            System.out.println("Enter 'letter' / 'number' to generate a new cryptogram.");
            System.out.println("Enter 'load' to load a previous cryptogram.");
            System.out.println("Enter 'stats' to see your statistics.");
            System.out.println("Enter 'scoreboard' to see the top 10 players.");
            System.out.println("Enter 'quit' to quit the game.");
            g.cryptType = myObj.nextLine();
            g.cryptType = g.cryptType.toLowerCase();



            while (true) {
                g.cryptogram = g.generateCryptogram(g.cryptType);

                if (g.cryptType.equals("letter") || g.cryptType.equals("l")) {
                    letterCryptogram letterGame = (letterCryptogram)  g.cryptogram;
                    g.mappedAlphabet = letterGame.getMappedAlphabet();
                    currentPlayer.incrementCryptogramsPlayed();
                    break;
                } else if (g.cryptType.equals("number") || g.cryptType.equals("n")) {
                    numberCryptogram numberGame = (numberCryptogram)  g.cryptogram;
                    g.mappedAlphabet = numberGame.getMappedAlphabet();
                    currentPlayer.incrementCryptogramsPlayed();
                    break;
                } else if ((g.cryptType.equals("load") ||  (g.cryptType.equals("lo"))) &&  file.exists()) {

                    g = null;
                    // Deserialization
                    try {
                        // Reading the object from a file
                        FileInputStream fis = new FileInputStream(file);
                        ObjectInputStream ois = new ObjectInputStream(fis);

                        // Method for deserialization of object
                        g = (Game) ois.readObject();

                        ois.close();
                        fis.close();
                    } catch (IOException ex) {
                        System.out.println("IOException is caught. Your saved file may be corrupt");
                    } catch (ClassNotFoundException ex) {
                        System.out.println("ClassNotFoundException" +
                                " is caught");
                    }
                    break;
                } else if (g.cryptType.equals("quit") || g.cryptType.equals("q")) {
                    playerList.savePlayers();
                    System.out.println("Quitting...");
                    System.exit(0);
                }else if (g.cryptType.equals("stats") || g.cryptType.equals("s") || g.cryptType.equals("stat")){
                    System.out.println();

                    System.out.println("Username: " + currentPlayer.username);
                    System.out.println("Correct Guesses: " + currentPlayer.correctGuesses);
                    System.out.println("Total Guesses: " + currentPlayer.totalGuesses);
                    System.out.println("Accuracy: " + currentPlayer.getAccuracy() + "%");
                    System.out.println("Total Games Played: " + currentPlayer.cryptogramsPlayed);
                    System.out.println("Total Games Finished: " + currentPlayer.cryptogramsCompleted);

                    System.out.println();

                    System.out.println("Enter 'letter' / 'number' to generate a new cryptogram.");
                    System.out.println("Enter 'load' to load a previous cryptogram.");
                    System.out.println("Enter 'stats' to see your statistics.");
                    System.out.println("Enter 'scoreboard' to see the top 10 players.");
                    System.out.println("Enter 'quit' to quit the game.");

                    g.cryptType = myObj.nextLine();
                    g.cryptType = g.cryptType.toLowerCase();

                }else if(g.cryptType.equals("scoreboard")) {

                    ArrayList<player> top10 = new ArrayList<>();
                    top10 = playerList.getTop10Players();


                    if(top10 != null) {

                        for (int i = 0; i < 10; i++) {
                            if (top10.size() > i) {
                                System.out.println(i + 1 + ". " + top10.get(i).username + "\t\tSolved: " + top10.get(i).cryptogramsCompleted);
                            } else {
                                System.out.println(i + 1 + ". ");
                            }
                        }
                    }

                    System.out.println();

                    System.out.println("Enter 'letter' / 'number' to generate a new cryptogram.");
                    System.out.println("Enter 'load' to load a previous cryptogram.");
                    System.out.println("Enter 'stats' to see your statistics.");
                    System.out.println("Enter 'scoreboard' to see the top 10 players.");
                    System.out.println("Enter 'quit' to quit the game.");

                    g.cryptType = myObj.nextLine();
                    g.cryptType = g.cryptType.toLowerCase();

                }
                else {
                    if (g.cryptType.equals("load") && !file.exists() ){
                        System.out.println("Error: new user save not available. Please enter 'letter', 'number', 'stats' or 'quit': ");

                    } else {
                        System.out.println("Error: Illegal input or new user save not available. Please enter 'letter', 'number', 'load', 'stats', or 'quit': ");
                    }
                    g.cryptType = myObj.nextLine();
                    g.cryptType = g.cryptType.toLowerCase();

                }
            }
            if (g.guessString.equals("")) {
                for (int i = 0; i < g.cryptogram.getPhrase().length(); i++) {
                    if (g.cryptogram.getPhrase().charAt(i) != ' ') {
                        g.guessString = g.guessString + "_";
                    } else {
                        g.guessString = g.guessString + " ";
                    }
                }
            }



            Boolean solved = false;

            while (!solved) {
                g.printCrypto(g.cryptogram.getPhrase(), g.getGuessString(), g.getMappedAlphabet());
                System.out.println("Guess, Undo, Solution, Frequencies, Hint, Save, or Quit?");
                String guessOrUndo = myObj.nextLine();
                guessOrUndo = guessOrUndo.toLowerCase();
                if (guessOrUndo.equals("guess") || guessOrUndo.equals("g")) {
                    g.guessString = g.guessLetter(g.cryptogram.getPhrase(), g.getGuessString(), g.getMappedAlphabet(), g.getAlreadyMapped());
                    currentPlayer.incrementGuesses();
                    if (g.getCorrectGuesses()) currentPlayer.incrementCorrectGuesses();
                } else if (guessOrUndo.equals("undo") || guessOrUndo.equals("u")) {
                    g.guessString = g.undoLetter(g.cryptogram.getPhrase(), g.getGuessString(), g.getMappedAlphabet(), g.getAlreadyMapped());

                } else if (guessOrUndo.equals("save") || guessOrUndo.equals("s")) {
                    guessOrUndo = "";
                    if (file.exists()) {
                        System.out.println("Save already exits. Do you wish to overwrite Yes/No");
                        String yn = myObj.nextLine();
                        yn = yn.toLowerCase();
                        if (yn.equals("yes") || yn.equals(("y"))) {
                            g.saveGame(g, name);
                            System.out.println("Save Complete");
                        }
                    } else {
                        g.saveGame(g, name);
                    }
                } else if (guessOrUndo.equals("solution") || guessOrUndo.equals("sol")) {

                    g.guessString = g.showSolution(g.cryptogram.getPhrase(), g.getGuessString(), g.getMappedAlphabet());

                } else if (guessOrUndo.equals("frequencies") || guessOrUndo.equals("f")) {
                    g.cryptogram.getFrequencies(g);
                } else if (guessOrUndo.equals("quit") || guessOrUndo.equals("q")) {
                    System.out.println("Would you like to save before quitting?");
                    String yn = myObj.nextLine();
                    yn.toLowerCase();
                    if (yn.equals("yes") || yn.equals("y")) {
                        System.out.println("Save already exits. Do you wish to overwrite Yes/No");
                        yn = myObj.nextLine();
                        yn = yn.toLowerCase();
                        if (yn.equals("yes") || yn.equals(("y"))) {
                            g.saveGame(g, name);
                            System.out.println("Save Complete");
                        }
                    }
                    playerList.savePlayers();
                    System.out.println("Quitting...");
                    System.exit(0);
                } else if (guessOrUndo.equals("hint") || (guessOrUndo.equals("h"))){
                    g.hint(g.cryptogram.getPhrase());
                }else {
                    System.out.println("Error: Illegal input");
                }


                if (g.guessString.equals(g.cryptogram.phrase)) {
                    if(guessOrUndo.equals("solution") || guessOrUndo.equals("sol")) {
                        g.printCrypto(g.cryptogram.getPhrase(),  g.getGuessString(),  g.getMappedAlphabet());
                        System.out.println("Here is the solution");

                    } else  {
                        g.printCrypto(g.cryptogram.getPhrase(),  g.getGuessString(),  g.getMappedAlphabet());
                        System.out.println("\nCongratulations. You solved the puzzle");
                        currentPlayer.incrementCryptogramsCompleted();
                    }
                    solved = true;
                    g.guessString = "";
                    //g.alreadyGuessed.clear();
                    g.alreadyMapped.clear();
                    g.mappedAlphabet.clear();
                } else if (!g.guessString.contains("_") ) {
                    System.out.println(g.guessString + "\nIncorrect. You failed to solve the puzzle");
                }
            }
        }
    }
}
