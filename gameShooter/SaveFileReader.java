package gameShooter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SaveFileReader {
	private String filename;
	private int high_score;
	
	public SaveFileReader(String save_file) {
		filename = save_file;
		String text = readData(save_file);
		//check that the file contains a numerical digit (ASCII range 48-57)
		String score_string = "";
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (48 <= c && c <= 57) {
				score_string += c;
			}
		}
		//set the current high score
		if (!score_string.equals("")) {
			high_score = Integer.parseInt(score_string);
		}
		else {
			high_score = 0;
		}
	}
	
	//read a text file and return the text
	public String readData(String file) {
		String text = "";
		String line = null;
		try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                text += line;
            }
            
            bufferedReader.close();
        }
        catch(FileNotFoundException e) {
            System.out.println("Unable to open file '" + file + "'");                
        }
        catch(IOException e) {
            System.out.println("Error reading file '" + file + "'");
        }
		return text;
	}
	
	//write some data to a text file (overwriting previous data)
	public void writeData(String data, String file_name) {
		try {
            FileWriter fileWriter = new FileWriter(file_name);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(data);
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println("Error writing to file '" + file_name + "'");
        }
	}
	
	public int getHighScore() {
		return high_score;
	}
	
	public void setHighScore(int new_score, String file_name) {
		high_score = new_score;
		String text_data = Integer.toString(high_score);
		writeData(text_data, file_name);
	}
}