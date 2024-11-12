package CryptogramGame;

public class player {

    String username;
    int correctGuesses = 0;
    int totalGuesses = 0;
    int cryptogramsPlayed = 0;
    int cryptogramsCompleted = 0;
    int accuracy = 0;


    public player(String name){
        username = name;
    }

    public player(String fileName, int fileCorrectGuesses, int fileGuesses, int filePlayed, int fileCompleted){
        username = fileName;
        correctGuesses = fileCorrectGuesses;
        totalGuesses = fileGuesses;
        cryptogramsPlayed = filePlayed;
        cryptogramsCompleted = fileCompleted;
        accuracy = getAccuracy();
    }



    public void incrementGuesses(){
        this.totalGuesses++;
    }
    public void incrementCorrectGuesses(){
        this.correctGuesses++;
    }
    public void incrementCryptogramsCompleted(){
        this.cryptogramsCompleted++;
    }
    public void incrementCryptogramsPlayed(){
        this.cryptogramsPlayed++;
    }



    public int getAccuracy(){
        if(this.getGuesses() != 0){
            this.accuracy = ((correctGuesses*100)/totalGuesses);
        }else{
            this.accuracy = 0;
        }
        return this.accuracy;
    }

    public int getCorrectGuesses(){
        return this.correctGuesses;
    }

    public int getNumCryptogramsPlayed(){
        return this.cryptogramsPlayed;
    }

    public int getNumCryptogramsCompleted() {
        return this.cryptogramsCompleted;
    }

      public String getName() {
        return this.username;
    }

    public int getGuesses() {
        return this.totalGuesses;
    }


}
