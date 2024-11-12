package CryptogramGame;

import java.io.*;
import java.util.*;

public class Game extends Cryptogram implements Serializable {
    public String cryptType;

    public String guessString = "";
    public Cryptogram cryptogram;
    public HashMap<String, String> mappedAlphabet;
    public ArrayList<String> alreadyMapped = new ArrayList<>();
    //public ArrayList<String> alreadyGuessed = new ArrayList<>();
    public Boolean correctGuess = false;

    public Game() {

    }
    public    Cryptogram getGame() { return this.cryptogram; }

    public  void setGame(Cryptogram game) { this.cryptogram = game; }

    public  String getGuessString() { return this.guessString; }

    public void setGuessString(String guessString) { this.guessString = guessString; }

    public  HashMap<String, String> getMappedAlphabet() { return this.mappedAlphabet; }

    public  void setMappedAlphabet(HashMap<String, String> mappedAlphabet) { this.mappedAlphabet = mappedAlphabet; }

    public  ArrayList<String> getAlreadyMapped() { return this.alreadyMapped; }

    public  void setAlreadyMapped(ArrayList<String> alreadyMapped) { this.alreadyMapped = alreadyMapped; }

    public String getCryptType() { return cryptType; }

    public void setCryptType(String cryptType) { this.cryptType = cryptType; }





    public Cryptogram generateCryptogram(String cryptType) {
        Cryptogram gameType = new Cryptogram();
        if (cryptType.equals("letter") || cryptType.equals("l")) {
             gameType = new letterCryptogram();

        } else if (cryptType.equals("number") || cryptType.equals("n")) {
             gameType = new numberCryptogram();
        }
        return gameType;
        
    }

    //Prints a the cryptogram as well as the already guessed letter/numbers
    public  void printCrypto(String phrase, String guessString, HashMap mappedAlphabet){
        System.out.println();
        System.out.println(phrase);
        for(int i =0; i < phrase.length(); i++){
            if(phrase.charAt(i) == ' '){
                System.out.print("   ");
            }else {
                System.out.print(mappedAlphabet.get(Character.toString(phrase.charAt(i))) + " ");
            }
        }
        System.out.println("\n" + guessString + "\n");
    }

    //Replace the guess string with guesses
    public  String guessLetter(String phrase, String guessString, HashMap mappedAlphabet, ArrayList alreadyMapped){
        char guess = ' ';
        String guessPlace = " ";
        correctGuess= false;
        //Gets the letter/number the user wants to guess and also what they want the guess is. Also puts them both to uppercase.
        Scanner myObj = new Scanner(System.in);
        while(true) {
            Boolean found = false;
            System.out.println("Enter letter/number to guess: ");
            guessPlace = myObj.nextLine();
            guessPlace = guessPlace.toUpperCase();
            for (int i = 0; i < phrase.length(); i++) {
                if (guessPlace.equals(mappedAlphabet.get(String.valueOf(phrase.charAt(i))))) {
                    found = true;
                }
            }
            if(found) break;
            System.out.println("Error: Letter/number not found");
        }

        while(true) {
            System.out.println("Enter guess: ");
            guess = myObj.next().charAt(0);
            guess = Character.toUpperCase(guess);

            if (guessString.contains(guess+"")) {
                System.out.println("Error: guess has already been made please try again");
            }else break;
        }
        myObj.nextLine();

        if(alreadyMapped.contains(guessPlace)){
            System.out.println("This has already been mapped. Enter 'Yes' to overwrite or 'No' to not overwrite: ");

            String ans = myObj.nextLine();
            ans = ans.toLowerCase();

            if(ans.equals("yes") || ans.equals("y")){
                for (int i = 0; i < phrase.length(); i++) {
                    if (guessPlace.equals(mappedAlphabet.get(String.valueOf(phrase.charAt(i))))) {
                        guessString = guessString.substring(0, i) + guess + guessString.substring(i + 1);
                    }
                }
            }
        }else {
            for (int i = 0; i < phrase.length(); i++) {
                if (guessPlace.equals(mappedAlphabet.get(String.valueOf(phrase.charAt(i))))) {
                    guessString = guessString.substring(0, i) + guess + guessString.substring(i + 1);
                    alreadyMapped.add(guessPlace);
                }
            }
        }

        if(phrase.contains("" + guess)){
            correctGuess = true;

        }
        return guessString;
    }

    //Replaces a letter/number with an underscore and removes it from alreadyMapped
    public  String undoLetter(String phrase, String guessString, HashMap mappedAlphabet, ArrayList alreadyMapped) {
        boolean found = false;
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter letter/number to remove: ");
        String undoPlace  = myObj.nextLine();
        undoPlace = undoPlace.toUpperCase();

        for(int i=0; i<phrase.length(); i++){
            if(undoPlace.equals(mappedAlphabet.get(String.valueOf(phrase.charAt(i)))) && alreadyMapped.contains(undoPlace)){
                alreadyMapped.remove(undoPlace);
                guessString = guessString.substring(0,i) + "_" + guessString.substring(i+1);
                found = true;
            }
        }

        if(!found){
            System.out.println("\nError: Letter/number not been mapped");
        }
        return guessString;
    }

    public   void saveGame(Game pl, String name) throws IOException {

        try {
            /*
            System.out.println(pl.getCryptType());
            System.out.println(pl.getGuessString());
            System.out.println(pl.cryptogram.phrase);
            System.out.println(pl.getMappedAlphabet());
            System.out.println(pl.getAlreadyMapped());
            System.out.println(pl.getAlreadyGuessed());
            System.out.println(pl.getGame());
             */
            // Saving of object in a file
            FileOutputStream fos = new FileOutputStream( name +"save.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // Method for serialization of object
            oos.writeObject(pl);
            oos.close();
            fos.close();


        }

        catch (IOException ex) {
            System.out.println("IOException ");
        }

    }
    public  void  loadGame(Game g) throws IOException{
        g = null;
        // Deserialization
        try {
            // Reading the object from a file
            FileInputStream fis = new FileInputStream(new File("save.txt"));
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Method for deserialization of object
            g = (Game)ois.readObject();

            ois.close();
            fis.close();
        }
        catch (IOException ex) {
            System.out.println("IOException is caught");
        }

        catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException" + " is caught");
        }
    }

    public Boolean getCorrectGuesses(){
        return correctGuess;
    }

    public String showSolution(String phrase, String guessString, HashMap mappedAlphabet){

        guessString = phrase;


        return guessString;

    }

    public void hint(String phrase){
        boolean check = false;
        System.out.println(phrase);
        for(int i=0; i<guessString.length(); i++){
            if((guessString.charAt(i)) != phrase.charAt(i)) {
                Character x = phrase.charAt(i);
                for (int y = 0; y < phrase.length(); y++) {
                    if (x.equals(phrase.charAt(y))) {
                        if(guessString.charAt(y) != '_') check = true;
                        guessString = guessString.substring(0, y) + phrase.charAt(i) + guessString.substring(y + 1);
                        alreadyMapped.add(mappedAlphabet.get(String.valueOf(phrase.charAt(y))));
                    }
                }
                if(check) System.out.println("Replacing previous input...");
                break;
            }
        }
    }
}
