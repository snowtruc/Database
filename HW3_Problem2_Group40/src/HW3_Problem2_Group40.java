import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HW3_Problem2_Group40 {
	static Scanner input;
	static DBManager DB;
	
	public static void main(String[] args) throws SQLException {
		String hostName = "henn0020-sql-server.database.windows.net";
	    String dbName = "cs-dsa-4513-sql-db";
	    String user = "henn0020";
	    String password = "~^1@9pxk4*29s|\\a1k22";
	    input = new Scanner(System.in);
	    DB = new DBManager(hostName, dbName, user, password);

		int option = 0;
		
		while(option != 4) {
			option = getNextOption();
			
			if(option == 1) {
				//get input from user for pname and age of a new performer
				String description = "Input the values for a new performer...";
				String[] param_names = {"pname", "age"};
				String[] params = getStringInputs(description, param_names); 
				String[] param_types = {"string", "int"};
				String spname = "sp_q1";
				try {
					final ResultSet resultSet = DB.executeSPQuery(spname, params, param_types);

				} finally {
					
				}
			}
			else if(option == 2) {
			
			}
			else if(option == 3) {
				// Display the complete information of all performers
				try {
					final String selectSql = "SELECT * FROM performer;";
					final ResultSet resultSet = DB.executeStringQuery(selectSql);
					System.out.println("Contents of the Performer Table:");
					while (resultSet.next()) {
						System.out.println(String.format("%s | %s | %s | %s",
								resultSet.getString(1),
								resultSet.getString(2),
								resultSet.getString(3),
								resultSet.getString(4)));
					}
				} finally {
					
				}
			}
		}
		// Done getting inputs
		input.close();
	}
	
	private static int getNextOption() {
		
		System.out.println("Option 1: ");
		System.out.println("Option 2: ");
		System.out.println("Option 3: Display the complete information of all performers.");
		System.out.println("Option 4: Quit");
		
		System.out.print("Select an option: ");
		return input.nextInt();
	}
	
	private static String[] getStringInputs(String description, String[] param_names) {
		System.out.println(description);
		String[] params = new String[param_names.length];
		for(int i = 0; i < param_names.length; i++) {
			System.out.println("Enter the value for " + param_names[i] + ":");
			params[i] = input.next();
		}
		return params;
	}
}
