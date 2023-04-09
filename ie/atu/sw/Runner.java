package ie.atu.sw;

/** @author Timoor Nurzhanov
 * 	@version 1.0
 *
 * Runner class that can be run from a JAR file.
 * Creates a menu object and shows a menu in the console.
 */
public class Runner {

	public static void main(String[] args) throws Exception {
		
		// Create an object of type MenuHandler in order to show the menu
		MenuHandler menu = new MenuHandler();	
		
		// Indefinite menu loop - closes when user inputs key to shutdown 
		do {
			
			menu.showMenu();
			
		}while(true);
		
		
    }
}