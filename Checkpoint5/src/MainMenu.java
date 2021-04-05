import java.util.Scanner;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class MainMenu {
	
	private static String DATABASE = "Library.db";
	
    /**
     * Connects to the database if it exists, creates it if it does not, and returns the connection object.
     * 
     * @param databaseFileName the database file name
     * @return a connection object to the designated database
     */
    public static Connection initializeDB(String databaseFileName) {
    	/**
    	 * The "Connection String" or "Connection URL".
    	 * 
    	 * "jdbc:sqlite:" is the "subprotocol".
    	 * (If this were a SQL Server database it would be "jdbc:sqlserver:".)
    	 */
        String url = "jdbc:sqlite:" + databaseFileName;
        Connection conn = null; // If you create this variable inside the Try block it will be out of scope
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
            	// Provides some positive assurance the connection and/or creation was successful.
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("The connection to the database was successful.");
            } else {
            	// Provides some feedback in case the connection failed but did not throw an exception.
            	System.out.println("Null Connection");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("There was a problem connecting to the database.");
        }
        return conn;
    }
    
    /**
     * Queries the database and prints the results.
     * 
     * @param conn a connection object
     * @param args a list of a query and its corresponding parameters
     */
    public static void selectQuery(Connection conn, String... args){
        try {
        	PreparedStatement ps = conn.prepareStatement(args[0]);
        	for(int i = 1; i < args.length; i++) {
        		ps.setString(i, args[i]);
        	}
        	ResultSet rs = ps.executeQuery();
        	ResultSetMetaData rsmd = rs.getMetaData();
        	int columnCount = rsmd.getColumnCount();
        	for (int i = 1; i <= columnCount; i++) {
        		String value = rsmd.getColumnName(i);
        		System.out.print(value);
        		if (i < columnCount) System.out.print(",  ");
        	}
			System.out.print("\n");
        	while (rs.next()) {
        		for (int i = 1; i <= columnCount; i++) {
        			String columnValue = rs.getString(i);
            		System.out.print(columnValue);
            		if (i < columnCount) System.out.print(",  ");
        		}
    			System.out.print("\n");
        	}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Inserts the tuple into the specified table
     * 
     * @param conn a connection object
     * @param args a list of a query and its corresponding parameters
     */
    public static void insert(Connection conn, String... args) {
    	try {
        	PreparedStatement ps = conn.prepareStatement(args[0]);
        	for(int i = 1; i < args.length; i++) {
        		ps.setString(i, args[i]);
        	}
        	ps.executeUpdate();
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    }
	
    /**
     * Presents the user with a list of options and calls the specified method.
     * 
     * @param in the scanner object
     */
	private static void presentOptions(Scanner in, Connection conn) {
		printOptions();
		// Verifies that the user entered a proper selection
		int selection = -1;
		while(selection < 0){
			try {
				System.out.print("\n\nPlease select an option (integers only): ");
				selection = Integer.parseInt(in.nextLine());
			} catch(NumberFormatException e) {
				System.out.println("Please enter an integer.");
			}
		}
		// Calls the approriate query
		switch(selection) {
			case 0:
				System.out.println("\nGoodbye!");
				try {
					conn.close();
				} catch (SQLException e) {
					System.out.println("Failed to close the database.");
				}
				System.exit(0);
				break;
			case 1:
				System.out.print("Please enter an artists stage name: ");
				String stageName = in.next();
				selectQuery(conn, Queries.artistSearch, stageName);
				break;
			case 2:
				System.out.print("Artist first name: ");
				String fName = in.nextLine();
				System.out.print("\nLast name: ");
				String lName = in.nextLine();
				System.out.print("\nDate of birth: ");
				String bday = in.nextLine();
				System.out.print("\nGender: ");
				String gender = in.nextLine();
				System.out.print("\nStage name: ");
				String sName = in.nextLine();
				insert(conn, Queries.artistAdd, fName, lName, bday, gender, sName);
				break;
			case 3:
				//TODO order items
				break;
			case 4:
				//TODO edit records
				break;
			case 5:
				//TODO show reports
				break;
			default:
				System.out.println("\nThe option you selected does not exist. Please try again.\n");
				break;
		}
	}
	
	private static void printOptions() {
		System.out.println("Welcome! Database options listed below:");
		System.out.println("\n(0) Exit\n(1) Search\n(2) Add new records\n(3) Order items\n(4) Edit records\n(5) Reports");
	}

	public static void main(String[] args) {
		System.out.println("This is a new run");
    	Connection conn = initializeDB(DATABASE);
		Scanner in = new Scanner(System.in);
		
		while(true)
			presentOptions(in, conn);
	}
}
