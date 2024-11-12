package CryptogramGame;

import java.io.Serializable;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Cryptogram implements Serializable {

    char[] cryptogramAlphabet = new char[26];
    ArrayList<String> phrases = new ArrayList<>();
    public String phrase = "";



    public Cryptogram(){

        //Create an offset to change the letters by
        Random r = new Random();
        int offset = r.nextInt(25 - 1) + 1;

        phrases = readFile();
        //Loop through cryptogramAlphabet array and change all the letters by the offset
        for(int i = 0; i<26; i++){
            char x = (char)(offset + (65 + i));     // Position i in alphabet + offset
            if((int)x > 90){                        // if x value > 'Z'
                x = (char)(((int)x) - 26);          // x-26
            }
            cryptogramAlphabet[i] = x;
        }

        int n = r.nextInt(phrases.size());
       // phrase = "HELLO THERE";
        phrase = phrases.get(n);

    }
    public ArrayList readFile(){
        try {
            File myObj = new File("src/main/java/CryptogramGame/Cryptos.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                phrases.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found");
            e.printStackTrace();
        }
        return phrases;
    }


    public String getPhrase(){
        return this.phrase;
    }

    public void setPhrase(String phrase) { this.phrase = phrase; }


    public HashMap<String, Integer> getFrequencies(Game g) {

        HashMap<String, Integer> frequencies = new HashMap<>();
        String x;
        String y;
        String cryptPhrase = "";
        String phrase = g.cryptogram.getPhrase();
        HashMap<String, String> mapped = g.getMappedAlphabet();
        System.out.println(g.getMappedAlphabet());
        ArrayList<String> map = g.getAlreadyMapped();
        String type = g.cryptType;

        for (int k = 0; k < phrase.length(); k++) {

            char a = phrase.charAt(k);
            String c = Character.toString(a);
            String b = mapped.get(c);
            if(a != ' '){
                cryptPhrase = cryptPhrase + " " + b;
            }
           
        }

        if(type.equals("letter") || type.equals("l")){
            
            for (int i = 0; i < cryptPhrase.length(); i++) {
                if(cryptPhrase.charAt(i) != ' '){
                    x = String.valueOf(cryptPhrase.charAt(i));
                
                if (frequencies.containsKey(x)) {
                    frequencies.replace(x, frequencies.get(x) + 1);
                } else {
                    frequencies.put(x, 1);
                }
                }
        }
    }

        if(type.equals("number") || type.equals("n")){
            y = "";
            cryptPhrase = cryptPhrase + " ";
            for(int j = 1; j < cryptPhrase.length(); j++){
                
                if(cryptPhrase.charAt(j) != ' '){
                    x = String.valueOf(cryptPhrase.charAt(j));
                    y = y + x;
                } else if(cryptPhrase.charAt(j) == ' ' || j == cryptPhrase.length()){
                    if (frequencies.containsKey(y)) {
                        frequencies.replace(y, frequencies.get(y) + 1);
                    } else {
                        frequencies.put(y, 1);
                    }
                    y = "";
                    
                } 

                }
            }
        

        System.out.println(
                "11.16% E     3.01% M \n8.49% A     3.00% H\n7.58% R     2.47% G\n7.54% I     2.07% B\n7.16% O     1.81% F\n6.95% T     1.77% Y\n6.65% N     1.28% W\n5.73% S     1.10% K\n5.48% L     1.00% V\n4.53% C     0.29% X\n3.63% U     0.27% Z\n3.38% D     0.19% J\n3.16% P     0.19% Q");
        System.out.println("\n" + frequencies);

        
   
    return frequencies;
}
}
