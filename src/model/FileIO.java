package model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class FileIO{
    private Scanner infile;
    private ArrayList<String> userWordList = new ArrayList<String>();
	private ArrayList<String> completeWordList = new ArrayList<String>();
	private ArrayList<String> gameLogList = new ArrayList<String>();

	public ArrayList<String> getUserWordList() 	   { return userWordList; }
	public ArrayList<String> getCompleteWordList() { return completeWordList; }
	public ArrayList<String> getGameLogList()	   { return gameLogList; }
	
    public void scanFile(String userList, String completeList){
		//read user list
        try {
		    infile = new Scanner(new FileReader(userList));
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
		while(infile.hasNextLine()) userWordList.add(infile.nextLine());
		infile.close( );

		//read complete list
		try {
		    infile = new Scanner(new FileReader(completeList));
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
		while(infile.hasNextLine()) completeWordList.add(infile.nextLine());
		infile.close( );
    }

	public void loadSave(){
		//file will contain a list of words that the user inputted + solution, other info will be derived from this
		try {
		    infile = new Scanner(new FileReader("resources/userData/gameLog.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
		while(infile.hasNextLine()) gameLogList.add(infile.nextLine());
		infile.close( );
	}

	public void writeToLog(String stringToWrite){
		try{
			FileWriter fo = new FileWriter("resources/userData/gameLog.txt", true);
			for(String str: userWordList){
				fo.write(str + "\n");
			}
			fo.close();
		} catch(IOException e){
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void updateList(String fileName){
		try{
			FileWriter fo = new FileWriter(fileName, false);
			for(String str: userWordList){
				fo.write(str + "\n");
			}
			fo.close();
		} catch(IOException e){
			e.printStackTrace();
			System.exit(0);
		}
	}

	public boolean inList(String word){
		return completeWordList.contains(word.toLowerCase());
	}

	public String getRandomWord(){
		Random rand = new Random();
		int index = rand.nextInt(userWordList.size());
		String word = userWordList.get(index);
		userWordList.remove(index);
		return word;
	}
}