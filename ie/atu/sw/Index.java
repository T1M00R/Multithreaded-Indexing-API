package ie.atu.sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**	@author Timoor Nurzhanov
 *	@version 1.0
 *	
 *	The Index class executes a virtual thread that reads a book file
 *	Retrieves dictionary definitions from the book and stores the words in an Index.txt file
 */
public class Index {

	// Atomic int stays the same expected value during virtual thread operations
	AtomicInteger currentPage = new AtomicInteger(1);
	AtomicInteger currentLine = new AtomicInteger(1);
	
	// Store all words and their page numbers from file 
	private Map<String, Set<Integer>> wordIndex = new HashMap<>();
	
	// A set of the most 1000 common words according to Google
	private Set<String> commonWords = new ConcurrentSkipListSet<>(); // O(log n)
	
	Dictionary d = new Dictionary();

	
	
	/**
	 * Method that reads the book file and generates an Index.txt file
	 * */
	public void execute(String fileName) throws IOException {
		
		System.out.println("\n\nGoing to read for a minute.");
		
		// Create a virtual thread for the task of reading the book
		try (var executor = Executors.newVirtualThreadPerTaskExecutor()){
			readFile(fileName);
		}
		
		System.out.println("\n\n\nFinished reading the book...\n\n"
				+ "Generating Index file...");
		
		
		// Second virtual thread to write definitions to screen
		try (var executor2 = Executors.newVirtualThreadPerTaskExecutor()){
			
			// testing to see if printing to file works
			String outputFile = "Index.txt";
			try (PrintWriter writer = new PrintWriter(new File(outputFile))) {
		    	
		    	
		    	for (Map.Entry<String, Set<Integer>> entry : wordIndex.entrySet()) {
				    String word = entry.getKey();
				    Set<Integer> pageNumbers = entry.getValue();
				    String definition = d.getDefinition(word);
				    
				    // Print word and definition if it was found in the dictionary
				    if (!definition.equalsIgnoreCase("Word not found.")) {
				    	// System.out.println("\nWord: " + word + ", Page Numbers: " + pageNumbers + "\nDefinition: " + definition);
				    	
				    	writer.println("\nWord: " + word + ", Page Numbers: " + pageNumbers + "\nDefinition: " + definition);
				    }		
		            		
		            		
		            
		        
		        }
		    } catch (IOException e) {
		        System.out.println("An error occurred while writing the index to the file: " + e.getMessage());
		    }
			
			
		System.out.println("Index file finished generating!!!");
			
		}
		
		
	}
	
	
	/**
	 * Load google 1000 most common words
	 * */
	public void load1000CommonWords()
    {
        System.out.println("Loading Common Words...");

        try
        {
        	// set common words file path
            String commonWordsFile = "./google-1000.txt";
            
            // For each word in the common words file, add to common words set
            Files.lines(Paths.get(commonWordsFile)).forEach(word -> commonWords.add(word));
            
            // Print each word in the set
//            for (String word : commonWords) {
//            	System.out.println(word);
//            }
            
			
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
	
	/**
	 * Method that reads the book,
	 * counts the page numbers
	 * and excludes common words
	 * */
	public void readFile(String fileName) {
		
		// Read and process the file
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
		    
			
			String line;
			while ((line = reader.readLine()) != null) {
			    // Process the line
				
				String[] words = line.split("[^\\w]");
				
				// For each word in words array, add to word index collection if it's not a common word
				for (String word : words) {
					
					// If its not a common word, add the word to the word index
				    if (!commonWords.contains(word)) {
				    	
				    	// Create a set of page numbers
				        Set<Integer> pageNumbers = wordIndex.get(word);
				       
				        if (pageNumbers == null) {
				            pageNumbers = new HashSet<>();
				            
				            // Add word and page numbers it appears on to the wordIndex Map
				            wordIndex.put(word, pageNumbers);
				        }
				        pageNumbers.add(currentPage.get());
				    }
				}
				// Increment atomic number
				currentLine.incrementAndGet();
	            if (currentLine.get() % 40 == 0) {
	                currentPage.incrementAndGet();
	            }
			}
		} catch (IOException e) {
		    // Handle exception
		}
	}		
}
