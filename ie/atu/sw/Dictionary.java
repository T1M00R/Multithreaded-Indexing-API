package ie.atu.sw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**	@author Timoor Nurzhanov
 *	@version 1.0
 *
 *
 *	Dictionary class
 *	Used for loading a dictionary.csv file,
 *	and getting the definitions of words
 */
public class Dictionary {
	
	// Final map to display
	public HashMap<String, String> dictionary = new HashMap<>();
	
	// Ask user to set this file later	- this is default directory
	private static String dictionaryFile = "./dictionary.csv";

	/** 
	 * getDefinition takes in a word and returns the definition
	 * @param word - Word to be defined by the dictionary file.
	 * */
	public String getDefinition(String word) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(dictionaryFile));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts[0].equals(word)) {
                reader.close();
                return parts[1];
            }
        }
        reader.close();
        return "Word not found.";
    }
	
	/**
	 * Configure the location of the dictionary file
	 * @param dir = Dictionary file directory
	 */
	public void configDictionary(String dir) {
		dictionaryFile = dir;
	}
    
    // Add word and definition to dictionary
    public void addToDictionary(String word, String definition) {
    	dictionary.put(word, definition);
    }
    
}