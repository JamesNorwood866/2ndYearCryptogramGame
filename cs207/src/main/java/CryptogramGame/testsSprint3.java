package CryptogramGame;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;


public class testsSprint3 {

    @Test

    public void leaderboardTest(){

        Game g = new Game();
        players playerList = new players();
        playerList.allPlayers = new ArrayList<player>(); //This removes all the players that were automatically loaded when playerList was created

        player player1 = new player("player1");
        player1.cryptogramsCompleted = 10;

        player player2 = new player("player2");
        player2.cryptogramsCompleted = 20;

        player player3 = new player("player3");
        player3.cryptogramsCompleted = 0;

        player player4 = new player("player4");
        player4.cryptogramsCompleted = 5;

        playerList.addPlayer(player1);
        playerList.addPlayer(player2);
        playerList.addPlayer(player4);
        playerList.addPlayer(player3);


        ArrayList<player> scoreBoard = playerList.getTop10Players();

        Assert.assertEquals("player2", scoreBoard.get(0).username);
        Assert.assertEquals("player1", scoreBoard.get(1).username);
        Assert.assertEquals("player4", scoreBoard.get(2).username);
        Assert.assertEquals("player3", scoreBoard.get(3).username);
    }


    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp(){
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test

    public void emptyLeaderBoard(){

        Game g = new Game();
        players playerList = new players();
        playerList.allPlayers = new ArrayList<player>(); //This removes all the players that were automatically loaded when playerList was created

        ArrayList<player> scoreBoard = playerList.getTop10Players();
        Assert.assertEquals("No players details have been saved.", outputStreamCaptor.toString().trim());

    }

    @AfterEach
    public void tearDown(){
        System.setOut(standardOut);
    }


    @Test
    public void solution(){
        Game g = new Game();

        g.cryptogram = g.generateCryptogram("letter");
        letterCryptogram letterGame = (letterCryptogram) g.cryptogram;
        g.guessString = "h";
        g.guessString = g.showSolution(g.getPhrase(),  g.getGuessString(),  g.getMappedAlphabet());
        Assert.assertEquals( g.phrase, g.guessString);

    }
    @Test
    public void seeFrequencies() throws IOException{

        Game g = new Game();

        g.cryptogram = g.generateCryptogram("letter");
        letterCryptogram letterGame = (letterCryptogram)g.cryptogram;
        g.setCryptType("letter");
        g.cryptogram.setPhrase("HELLO THERE");

        HashMap<String, String> testMap = new HashMap<>();
        testMap.put("H", "K");
        testMap.put("E", "Q");
        testMap.put("L", "P");
        testMap.put("O", "M");
        testMap.put("T", "W");
        testMap.put("R", "I");

        HashMap<String, Integer> freq = new HashMap<>();
        freq.put("P", 2);
        freq.put("Q", 3);
        freq.put("W", 1);
        freq.put("I", 1);
        freq.put("K", 2);
        freq.put("M", 1);

        g.setMappedAlphabet(testMap);

        Assert.assertEquals(freq, g.cryptogram.getFrequencies(g));
    }

    @Test
    public void emptyHintTest() {
        Game g = new Game();
        g.generateCryptogram("letter");
        letterCryptogram letterGame = (letterCryptogram) g.cryptogram;
        g.setCryptType("letter");

        HashMap<String, String> mappedAlphabet = new HashMap<>();
        mappedAlphabet.put("H", "K");
        mappedAlphabet.put("E", "Q");
        mappedAlphabet.put("L", "P");
        mappedAlphabet.put("O", "M");
        mappedAlphabet.put("T", "W");
        mappedAlphabet.put("R", "I");

        g.setMappedAlphabet(mappedAlphabet);
        g.setPhrase("HELLO THERE");
        g.guessString = "_____ _____";
        g.hint(g.getPhrase());

        Assert.assertEquals("H____ _H___", g.guessString);

        g.hint(g.getPhrase());
        Assert.assertEquals("HE___ _HE_E", g.guessString);
    }

    @Test
    public void replaceHintTest() {
        Game g = new Game();
        g.generateCryptogram("letter");
        letterCryptogram letterGame = (letterCryptogram) g.cryptogram;
        g.setCryptType("letter");

        HashMap<String, String> mappedAlphabet = new HashMap<>();
        mappedAlphabet.put("H", "K");
        mappedAlphabet.put("E", "Q");
        mappedAlphabet.put("L", "P");
        mappedAlphabet.put("O", "M");
        mappedAlphabet.put("T", "W");
        mappedAlphabet.put("R", "I");

        g.setMappedAlphabet(mappedAlphabet);
        g.setPhrase("HELLO THERE");
        g.guessString = "M____ _M___";
        g.hint(g.getPhrase());

        Assert.assertEquals("H____ _H___", g.guessString);
    }
}
