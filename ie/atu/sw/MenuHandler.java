package ie.atu.sw;
import java.util.Scanner;

/**	@author Timoor Nurzhanov
 * 	@version 1.0
 * 
 * 
 * Menu class that contains menu operations and UI.
 * */
public class MenuHandler{
	
	// Input for menu
	Scanner scanner = new Scanner(System.in);
	Dictionary d = new Dictionary();
	Index index = new Index();
    
    String filePath, directory, outputFile;

	
    /**
	 * Method to show a menu in the console, take input, and run other methods.
	 * */
    public void showMenu() throws Exception{
		//You should put the following code into a menu or Menu class
				System.out.println(ConsoleColour.WHITE);
				System.out.println("************************************************************");
				System.out.println("*       ATU - Dept. Computer Science & Applied Physics     *");
				System.out.println("*                                                          *");
				System.out.println("*              Virtual Threaded Text Indexer               *");
				System.out.println("*                                                          *");
				System.out.println("************************************************************");
				System.out.println("(1) Specify a text file to process");
				System.out.println("(2) Choose an alternative dictionary location");
				System.out.println("(3) Load Google's 1000 most common words");
				System.out.println("(4) Specify output file path");
				System.out.println("(5) Generate Index");
				System.out.println("(6) Quit");
				
				//Output a menu of options and solicit text from the user
				System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
				System.out.print("Select Option [1-6]>");
				
				System.out.println();	// prints a new line
				
				int menuInput = scanner.nextInt();
			    
				
			    // Switch case to handle menu input
				switch (menuInput) {
				
					case 1: 
						// Specify text file
						System.out.println("Please specify the file to read in\ne.g. - ./text-files/filename.txt");
						scanner.nextLine();		// helps keep program from breaking
						filePath = scanner.nextLine();
						

						break;
					case 2: 
						// Configure dictionary
						System.out.println("Please specify the folder of a dictionary, \ndefault: ./dictionary.csv");
						directory = scanner.nextLine();
						d.configDictionary(directory);
						// scanner.nextLine();
						break;
					case 3: 
						// Configure common words

						index.load1000CommonWords();
						System.out.println("Common words loaded!");
						
						break;
					case 4: 
						// Specify output file
						
						System.out.println("Specify output file location");
						outputFile = scanner.nextLine();
						scanner.nextLine();
						break;
						
					case 5: 
						// Executes a virtual thread that reads file and generates index file
						index.execute(filePath + "");
						
						// open file afterwards 
							
						// ./text-files/PoblachtNaHEireannPearse.txt
						
						break;
					case 6: 
						// Quit
						System.out.println("\nShutting down...");
						System.exit(0);
						break;
				
				default:
					System.out.println("Select a valid input option");
					// throw new IllegalArgumentException("Select a valid input option");
				}
				
			}
	
			
	
}