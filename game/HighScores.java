package game;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader; 
import java.io.IOException;
import java.util.ArrayList;

public class HighScores 
{
	private static File highScores;
	private static BufferedWriter write;
	private static BufferedReader read;
	
	public HighScores() throws IOException {
		highScores = new File("HighScores.dat");
		write = new BufferedWriter(new FileWriter(highScores,true));
		read = new BufferedReader(new FileReader(highScores));
	}
	
	public static void addScore(int score) throws IOException {
		write.write(String.valueOf(score));
		write.newLine();
		write.flush();
	}
	
	public static ArrayList<Integer> readScores() throws IOException {
		ArrayList<Integer> scores = new ArrayList<Integer>();
		String score = "0";
		while((score = read.readLine()) != null) {
			scores.add(new Integer(Integer.parseInt(score)));
		}
		return scores;
	}
}
