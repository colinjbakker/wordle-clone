package model;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Random;

public class FileIO{
    private Scanner infile;
    private ArrayList<String> wordsList = new ArrayList<String>();

    public void scanFile(String fileName){
        try
		{
		      infile = new Scanner(new FileReader(fileName));
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("File not found");
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
			wordsList.add(s);
		}
		infile.close( );
    }

	public boolean inList(String word){
		return wordsList.contains(word.toLowerCase());
	}

	public String getRandomWord(){
		Random rand = new Random();
		int index = rand.nextInt(5757);
		return wordsList.get(index);
	}
}