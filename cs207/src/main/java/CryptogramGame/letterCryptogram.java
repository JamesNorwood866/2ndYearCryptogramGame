package CryptogramGame;

import java.io.Serializable;
import java.util.HashMap;



public class letterCryptogram  extends Cryptogram implements Serializable {

    HashMap<String, String> mappedAlphabet = new HashMap<>();

    public letterCryptogram() {

        //Creates hash map between the alphabet and the cryptogramAlphabet
        for(int i =0; i < cryptogramAlphabet.length; i++){
            mappedAlphabet.put(Character.toString('A' + i), Character.toString(cryptogramAlphabet[i]));
        }

    }
    public HashMap<String, String> getMappedAlphabet(){
        return mappedAlphabet;
    }

    public char getPlainLetter(String cryptoLetter){
        return mappedAlphabet.get(cryptoLetter).charAt(0);
    }
}