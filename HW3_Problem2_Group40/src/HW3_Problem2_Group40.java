import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.Scanner;

public class HW3_Problem2_Group40 {
	public static void main(String[] args) throws SQLException {
		Scanner input = new Scanner(System.in);
		
		// Connect to database
	
		final String hostName = "pham0027-sql-server.database.windows.net"; final String dbName = "cs-dsa-4513-sql-db";
		final String user = "pham0027";
		final String password = "Tiffy!297";
		final String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;host NameInCertificate=*.database.windows.net;loginTimeout=30;",
				hostName, dbName, user, password);

		// 
		int option = 0;
		
		while(option != 4) {
			System.out.println("Option 1: ");
			System.out.println("Option 2: ");
			System.out.println("Option 3: Display the complete information of all performers.");
			System.out.println("Option 4: Quit");
			
			System.out.print("Select an option: ");
			option = input.nextInt();
			
			if(option == 1 || option == 2 || option == 3) {
				try (final Connection connection = DriverManager.getConnection(url)) {
					final String schema = connection.getSchema();
					System.out.println("Successful connection - Schema:" + schema);
					System.out.println("Query data example:");
					System.out.println("========================================="); 
					final String selectSql = "SELECT * FROM performer;";
					
					try (final Statement statement = connection.createStatement(); 
						final ResultSet resultSet = statement.executeQuery(selectSql)) {
						
						System.out.println("Contents of the Performer Table:");
						while (resultSet.next()) {
							System.out.println(String.format("%s | %s | %s | %s",
									resultSet.getString(1),
									resultSet.getString(2),
									resultSet.getString(3),
									resultSet.getString(4)));
						}
					}
				}
			}
		}
	}
}
