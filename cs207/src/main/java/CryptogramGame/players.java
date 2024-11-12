package CryptogramGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class players {

    public ArrayList<player> allPlayers = new ArrayList<player>();


    public players(){


        try {
            File myObj = new File("src/main/java/CryptogramGame/playersFile.txt");
            Scanner myReader = new Scanner(myObj);
            Scanner lineReader = new Scanner(myObj);

            int lines = 0;

            while(lineReader.hasNextLine()){
                if(lineReader.nextLine() != null){
                    lines++;
                }
            }
            lineReader.close();

            for (int i = 0; i < lines/5; i++){
                player filePlayer = new player(myReader.nextLine(), Integer.parseInt(myReader.nextLine()), Integer.parseInt(myReader.nextLine()), Integer.parseInt(myReader.nextLine()), Integer.parseInt(myReader.nextLine()));
                allPlayers.add(filePlayer);
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found");
            e.printStackTrace();
        }

    }


    public void addPlayer(player p){
        allPlayers.add(p);
    }

    public void savePlayers(){
        try {
            File myObj = new File("src/main/java/CryptogramGame/playersFile.txt");
            FileWriter myWriter = new FileWriter(myObj);
            for(int i = 0; i< allPlayers.size(); i++){
                myWriter.write(allPlayers.get(i).username + "\n");
                myWriter.write(allPlayers.get(i).correctGuesses+ "\n");
                myWriter.write(allPlayers.get(i).totalGuesses+ "\n");
                myWriter.write(allPlayers.get(i).cryptogramsPlayed+ "\n");
                myWriter.write(allPlayers.get(i).cryptogramsCompleted+ "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public player findPlayer(String name){
        for (int i = 0; i < allPlayers.size(); i++){
            if(allPlayers.get(i).username.equals(name)){
                return allPlayers.get(i);
            }
        }
        return null;
    }

    public ArrayList<player> getTop10Players(){

        ArrayList<player> top10 = new ArrayList<>();
        ArrayList<player> tempList = allPlayers;

        if(tempList.isEmpty()){
            System.out.println("No players details have been saved.");
            return null;
        }

        int size = 0;

        if(tempList.size() >= 10){
            size = 10;
        }else{
            size = tempList.size();
        }


        for(int i = 0; i < size; i++) {

            player firstPlace = tempList.get(0);

            for (int j = 0; j < tempList.size(); j++) {
                if (tempList.get(j).getNumCryptogramsCompleted() > firstPlace.getNumCryptogramsCompleted()) {
                    firstPlace = tempList.get(j);
                }
            }

            top10.add(firstPlace);
            tempList.remove(firstPlace);
        }



        return top10;
    }


}
