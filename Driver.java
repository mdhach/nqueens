public class Driver {
	/**
     * Driver for running Queens
     */
    public static void main(String[] args) throws InterruptedException {
    	Queens newGame = new Queens();
        int intIn = 0; // initialize input
        char charIn = ' ';
        
        newGame.init();
        
        while(charIn != 'N') {
            // start Hill Climbing Algorithm
            intIn = newGame.getInt();
            newGame = new Queens(intIn);
            newGame.generate();
            newGame.climb();
            charIn = newGame.getChar();
        }
        System.out.println("Goodbye!");
    }
}
