package model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Random;

public class FileIO{
    private Scanner infile;
    private ArrayList<String> userWordList = new ArrayList<String>();
	private ArrayList<String> completeWordList = new ArrayList<String>();

    public void scanFile(String userList, String completeList){
		//read user list
        try
		{
		    infile = new Scanner(new FileReader(userList));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			System.exit(0);
		}
		String s;
		while(infile.hasNextLine())
		{
			String line = infile.nextLine();
			StringTokenizer tokenizer = new StringTokenizer(line);

			// You should know what you are reading in
			s = tokenizer.nextToken();
			userWordList.add(s);
		}
		infile.close( );

		//read complete list
		try
		{
		    infile = new Scanner(new FileReader(completeList));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			System.exit(0);
		}
		while(infile.hasNextLine())
		{
			String line = infile.nextLine();
			StringTokenizer tokenizer = new StringTokenizer(line);

			// You should know what you are reading in
			s = tokenizer.nextToken();
			completeWordList.add(s);
		}
		infile.close( );
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