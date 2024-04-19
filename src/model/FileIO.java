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
	private ArrayList<Integer> statsList = new ArrayList<Integer>();

	public ArrayList<String> getUserWordList() 	   { return userWordList; }
	public ArrayList<String> getCompleteWordList() { return completeWordList; }
	public ArrayList<String> getGameLogList()	   { return gameLogList; }
	public ArrayList<Integer> getStatsList()	   { return statsList; }
	
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

	public void loadSave(String fileName){
		//file will contain a list of words that the user inputted + solution, other info will be derived from this
		try {
		    infile = new Scanner(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
		gameLogList.clear();
		while(infile.hasNextLine()) gameLogList.add(infile.nextLine());
		infile.close( );
	}

	public void loadStats(String fileName){
		//file will contain a list of words that the user inputted + solution, other info will be derived from this
		try {
		    infile = new Scanner(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}

		gameLogList.clear();
		while(infile.hasNextInt()){
			statsList.add(infile.nextInt());
		} 
		infile.close( );
	}

	public void writeWordToFile(String stringToWrite, String fileName, boolean underwrite){
		try{
			FileWriter fo = new FileWriter(fileName, underwrite);
			fo.write(stringToWrite.toUpperCase() + "\n");
			fo.close();
		} catch(IOException e){
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void writeStats(String fileName, Stats stats){
		try{
			FileWriter fo = new FileWriter(fileName, false);
			fo.write(stats.getWinCount() + "\n");
			fo.write(stats.getGameCount() + "\n");
			fo.write(stats.getMaxStreak() + "\n");
			fo.write(stats.getCurrentStreak() + "\n");
			for(int i = 0; i < 6; i++){
				fo.write(stats.winCountArrayAt(i) + "\n");
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