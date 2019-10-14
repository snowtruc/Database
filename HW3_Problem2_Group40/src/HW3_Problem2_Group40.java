import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
				String[] param_names = {"Name", "Age"};
				String[] params = getStringInputs(description, param_names); 
				String[] param_types = {"string", "int"};
				String spname = "sp_q1";
				//pid not taken since auto generated.
				try {
					final int result = DB.executeSPQuery(spname, params, param_types);

				} finally {
					
				}
			}
			else if(option == 2) {
				//get input from user for pname and age of a new performer
				String description = "Input the values for a new performer...";
				String[] param_names = {"Name", "Age", "Director ID"};
				String[] params = getStringInputs(description, param_names); 
				String[] param_types = {"string", "int", "int"};
				String spname = "sp_q2";
				//pid not taken since auto generated.
				try {
					final int result = DB.executeSPQuery(spname, params, param_types);

				} finally {
					
				}
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
		
		System.out.println("Option 1: Insert new performer based on average experience of other performers within close age.");
		System.out.println("Option 2: Insert new performer based on average experience of other performers who acted in movie directed by given director.");
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

class DBManager {
	private String hostName;
	private String dbName;
	private String user;
	private String password;
    private String url;
    final Connection connection;
    
	public DBManager(String hostName, String dbName, String user, String password) throws SQLException {
		this.hostName = hostName;
		this.dbName = dbName;
		this.user = user;
		this.password = password;
		this.url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
					       hostName, dbName, user, password);
		try{
			this.connection = DriverManager.getConnection(url);
		} finally {
			
		}
	}
	
	public ResultSet executeStringQuery(String query) throws SQLException{
		try{
			final Statement statement = this.connection.createStatement(); 
			return statement.executeQuery(query); 
		} finally {
			
		}			
	}
	
	public int executeSPQuery(String spname, String[] params, String[] param_types) throws SQLException{
		//Given a stored procedure name, the values for parameters, and types of parameters,
		// construct a string that is SQL statement to execute that procedure.
		//TODO: only does executeUpdate which does not return result set; only works for procedures which do not return results
		String query = "EXEC " + spname;
		int num_params = params.length;
		query = num_params > 0 ? query + " " : query; //append space to query if at least one parameter
		// Add question marks to end of query for each parameter
		for(int i = 0; i < num_params; i++) {
			if(i > 0) query += ",";
			query += "?";
		}
		PreparedStatement ps = this.connection.prepareStatement(query);
		ps.setEscapeProcessing(true);
		// Create prepared statement (fill in string with parameters)
		for(int i = 0; i < num_params; i++) {
			//TODO: make generalized to cover more variable types
			if (param_types[i].equals("string")){
				ps.setString(i+1, params[i]);
			}
			else if(param_types[i].equals("int")) {
				ps.setInt(i+1, Integer.parseInt(params[i]));
			}
		}
		return ps.executeUpdate();
	}

}

