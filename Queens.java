import java.util.*;

/**
 * This class runs a program that will randomly place n Queens onto an n*n
 * board. It uses the Hill Climbing method to determine a solution in which
 * none of the n Queens are in conflict with one another.
 * 
 * @author Mardi Hach
 * @version 9/3/2019
 */
public class Queens {
    
    private int[][] board; // main board state
    private int[][] testBoard; // tempory board state
    private int heuristic; // count of conflicts between current queens
    private int restarts; // total times the climbing restarted
    private int moves; // total moves made to the queens overall
    private int neighbors; // number of neighbors with a lower heuristic
    private int queens; // the number of queens chosen by the user
    private Scanner input;
    
    /**
     * Main constructor that initializes the Scanner object
     */
    public Queens() {
        input = new Scanner(System.in);
    }
    
    /**
     * Overload constructor created when n is determined by user
     * 
     * @param int determines board dimensions and number of queens
     */
    public Queens(int in) {
        board = new int[in][in];
        testBoard = new int[in][in];
        queens = in;
        heuristic = 0;
        restarts = 0;
        moves = 0;
        neighbors = in;
        input = new Scanner(System.in);
    }
    
    /**
     * Generates a randomized board with randomly placed queens
     */
    public void generate() {
        Random random = new Random();
        int rand = random.nextInt(queens);
        
        for(int i = 0; i < board.length; i++) {
            board[rand][i] = 1;
            rand = random.nextInt(queens);
        }

        heuristic = heuristic(board);
    }
    
    /**
     * Prints the current state of the board
     */
    public void printState() {
        System.out.println("\n");
        System.out.println("Current h: " + heuristic);
        System.out.println("Current State");
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.println("Neighbors found with lower h: " + neighbors);
        System.out.println("Setting new current state");
    }
    
    /**
     * Prints the solution to the board
     */
    public void printSolution() {
        System.out.println("\nCurrent State");
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.println("Solution Found!");
        System.out.println("Number of Queens: " + queens);
        System.out.println("State changes: " + moves);
        System.out.println("Restarts: " + restarts);
    }
    
    /**
     * Determines if a queen is in conflict in same row
     * 
     * @param int[][] the current board state
     * @param int the column in which the current queen resides
     * @return boolean true or false depending on if a conflict was found
     */
    public boolean rConflict(int[][] inArr, int col) {
        boolean conflict = false;
        int count = 0;
        
        for(int i = 0; i < board.length; i++) {
            if(inArr[i][col] == 1) {
                count++;
            }
        }
        if(count > 1) {
            conflict = true;
        }
        return conflict;
    }
    
    /**
     * Determines if a queen is in conflict in same column
     * 
     * @param int[][] current board state
     * @param int the row in which the currrent queen resides
     * @return boolean depending on if a conflict was found
     */
    public boolean cConflict(int[][] inArr, int row) {
        boolean conflict = false;
        int count = 0;
        
        for(int i = 0; i < board.length; i++) {
            if(inArr[row][i] == 1) {
                count++;
            }
        }
        if(count > 1) {
            conflict = true;
        }
        return conflict;
    }
    
    /**
     * Determines if a queen is in conflict in the same diagonal
     * 
     * @param int[][] the current board state
     * @param int the row of the current queen in question
     * @param int the column of the current queen in question
     * @return boolean true or false depending on if a conflict was found
     */
    public boolean dConflict(int[][] inArr, int row, int col) {
        boolean conflict = false;
        
        for(int i = 1; i < board.length; i++) {
            if(conflict) {
                break;
            }
            if((row + i < board.length) && (col + i < board.length)){
                if(inArr[row + i][col + i] == 1){
                    conflict = true;
                }
            }
            if((row - i >= 0) && (col - i >= 0)){
                if(inArr[row - i][col - i] == 1){
                    conflict = true;
                }
            }
            if((row + i < board.length) && (col - i >= 0)){
                if(inArr[row + i][col - i] == 1){
                    conflict = true;
                }
            }  
            if((row - i >= 0) && (col + i < board.length)){
                if(inArr[row - i][col + i] == 1){
                    conflict = true;
                }
            }
        }
        return conflict;
    }
    
    /**
     * Determines the column of the minimum neighbor state
     * 
     * @param int[][] current board state
     * @return int the column of the minimum neighbor state
     */
    public int findMinCol(int[][] inArr) {
        int minCol = queens;
        int minVal = queens;
        int count = 0;
        
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                if(inArr[i][j] < minVal) {
                    minVal = inArr[i][j];
                    minCol = j;
                }
                if(inArr[i][j] < heuristic) {
                    count++;
                }
            }
        }
        neighbors = count;
        return minCol;
    }
    
    /**
     * Determines the row of the minimum neighbor state
     * 
     * @param int[][] current board state
     * @return int the row of the minimum neighbor state
     */
    public int findMinRow(int[][] inArr) {
        int minRow = queens;
        int minVal = queens;
        
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                if(inArr[i][j] < minVal) {
                    minVal = inArr[i][j];
                    minRow = i;
                }
            }
        }
        return minRow;
    }
    
    /**
     * Counts the total number of conflicts found on the current board state
     * 
     * @param int[][] the current board state
     * @return int the total number of queens in conflict
     */
    public int heuristic(int[][] inArr) {
        int count = 0;
        boolean row;
        boolean col;
        boolean dia;
        
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                if(inArr[i][j] == 1) {
                    row = rConflict(inArr, j);
                    col = cConflict(inArr, i);
                    dia = dConflict(inArr, i, j);
                    
                    if(row || col || dia) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
    
    /**
     * Determines if a restart is necessary; when there are no neighbors
     * with a lower heuristic value.
     * 
     * @param int[][] current board state
     * @return boolean depending on a restart has been determined
     */
    public boolean checkRestart(int[][] inArr) {
        int min = queens;
        boolean restart = false;
        
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                if(inArr[i][j] < min) {
                    min = inArr[i][j];
                }
            }
        }
        if(neighbors == 0) {
            restart = true;
        }
        return restart;
    }
    
    /**
     * Moves a queen to a valid position
     * 
     * @param int[][] current board state
     */
    public void move(int[][] inArr) {
        int minCol = findMinCol(inArr);
        int minRow = findMinRow(inArr);
        
        for(int i = 0; i < board.length; i++) {
            board[i][minCol] = 0;
        }
        board[minRow][minCol] = 1;
        moves++;
        heuristic = heuristic(board);
    }
    
    /**
     * Determines neighbor state
     * 
     * @return int[][] a neighbor array with the lowest heuristic
     */
    public int[][] hill() {
    	int[][] tempArray = new int[queens][queens];
    	int count = 0;
    	int prevQueen = 0;
    	
    	while(count < board.length) {
            for(int i = 0; i < board.length; i++) {
                testBoard[i][count] = 0;
            }
            for(int i = 0; i < board.length; i++) {
                if(board[i][count] == 1) {
                    prevQueen = i;
                }
                testBoard[i][count] = 1;
                tempArray[i][count] = heuristic(testBoard);
                testBoard[i][count] = 0;
            }
            testBoard[prevQueen][count] = 1;
            count++;
        }
    	return tempArray;
    }
    
    /**
     * Main climb method which moves the queen, determines if a restart is
     * required or if a solution has been determined
     */
    public void climb() { 
        while(true) {
           //count = 0;
            
            // initialize test board to current board state
            for(int i = 0; i < board.length; i++) {
                for(int j = 0; j < board.length; j++) {
                    testBoard[i][j] = board[i][j];
                }
            }
    
            // determine if restart is necessary
            if(checkRestart(hill())) {
                for(int i = 0; i < board.length; i++) {
                    for(int j = 0; j < board.length; j++) {
                        board[i][j] = 0;
                    }
                }
                generate();
                restarts++;
                System.out.println("RESTART!");
            }
            
            move(hill());
            
            // print if solution
            if(heuristic(board) == 0) {
                printSolution();
                break;
            }
            printState();
        }
    }
    
    /**
     * Method that gets user input to determine n
     * 
     * @return int n Queens
     */
    public int getInt() {
        int in = 0;
        boolean escape = false;
        
        while(!escape) {
        	try {
        		System.out.println("Enter an even number that is 8 or higher: ");
            	in = input.nextInt();
            	if((in % 2 != 0) || (in < 8)) {
            		throw new Exception("Non-even/less than 8");
            	} else {
            		escape = true;
            	}
        	} catch (Exception e) {
        		System.out.println("ERROR! Please enter an even number that is greater than or equal to 8!");
        	} finally {
        		input.nextLine();
        	}
        }
        return in;
    }
    
    /**
     * Method used to determine if user continues or quits
     * 
     * @return char users decision
     */
    public char getChar() {
        char charIn = 0;
        boolean escape = false;
        
        while(!escape) {
            try {
            	System.out.println("\nContinue? Y/N: ");
            	charIn = input.next().charAt(0);
            	charIn = Character.toUpperCase(charIn);
            	if(charIn != 'Y' && charIn != 'N') {
            		throw new Exception("Neither Y or N");
            	} else {
            		escape = true;
            	}
            } catch (Exception c) {
        		System.out.println("ERROR! Enter either Y or N (not case sensitive)");
            }
        }
        return charIn;
    }
    
    /**
     * Initial text to be displayed to user
     */
    public void init() {
    	System.out.println(" _               _______           _______  _______  _        _______ \r\n" + 
        		"( (    /|       (  ___  )|\\     /|(  ____ \\(  ____ \\( (    /|(  ____ \\\r\n" + 
        		"|  \\  ( |       | (   ) || )   ( || (    \\/| (    \\/|  \\  ( || (    \\/\r\n" + 
        		"|   \\ | | _____ | |   | || |   | || (__    | (__    |   \\ | || (_____ \r\n" + 
        		"| (\\ \\) |(_____)| |   | || |   | ||  __)   |  __)   | (\\ \\) |(_____  )\r\n" + 
        		"| | \\   |       | | /\\| || |   | || (      | (      | | \\   |      ) |\r\n" + 
        		"| )  \\  |       | (_\\ \\ || (___) || (____/\\| (____/\\| )  \\  |/\\____) |\r\n" + 
        		"|/    )_)       (____\\/_)(_______)(_______/(_______/|/    )_)\\_______)\n\n"
        		+ "Welcome!\n\n"
        		+ "This is a simple program that simulates the N-Queens problem and tries to solve it by using the\n"
        		+ "Hill Climbing Algorithm.\n\n"
        		+ "N-Queens: This problem consists of randomly placed n Queens on an n*n board.\n"
        		+ "The idea is to arrange the Queens in a way that none of the pieces will conflict with each other.\n\n"
        		+ "Hill Climbing Algorithm: An iterative algorithm that attempts to solve a problem by incrementing\n"
        		+ "multiple solution states and determining their heuristic values. The current state will be\n"
        		+ "compared to neighboring states with a lower heuristic value. If no neighboring state with\n"
        		+ "a lower heuristic value can be determined - a hard reset will be initiated.\n\n"
        		+ "The Output: The output will consist of 1's and 0's. The 1's represent a location of a Queen\n"
        		+ "while the 0's represent an empty location.\n");
    }
    
    
}