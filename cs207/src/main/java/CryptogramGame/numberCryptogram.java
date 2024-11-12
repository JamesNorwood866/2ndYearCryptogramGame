package CryptogramGame;
import java.io.Serializable;
import java.util.HashMap;

public class numberCryptogram extends Cryptogram implements Serializable  {

    HashMap<String, String> mappedAlphabet = new HashMap<>();

    public numberCryptogram() {

        //Creates hash map between the alphabet and the cryptogramAlphabet
        for(int i =0; i < cryptogramAlphabet.length; i++){
            mappedAlphabet.put(Character.toString('A' + i), String.valueOf(cryptogramAlphabet[i]-'A'));
        }

    }
    public HashMap<String, String> getMappedAlphabet(){
        return mappedAlphabet;
    }

    public char getPlainLetter(Integer cryptoValue){
        return mappedAlphabet.get(""+cryptoValue).charAt(0);
    }
}