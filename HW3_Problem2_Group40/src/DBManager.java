import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBManager {
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
