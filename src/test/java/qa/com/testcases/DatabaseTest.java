package qa.com.testcases;
import org.testng.annotations.*;

import java.sql.*;

import static org.testng.Assert.assertEquals;
import qa.com.config.ConfigReader;

public class DatabaseTest {
    private Connection connection;
    ConfigReader configReader = new ConfigReader();

    @BeforeSuite
    public void setUpDatabaseConnection() throws SQLException {
        // Establish database connection
        String url = configReader.getdbConnectionString();
        String username = configReader.getdbUserName();
        String password = configReader.getdbPassword();
        connection = DriverManager.getConnection(url, username, password);
    }

   
    private int getNumberOfEmployees() throws SQLException {
        int count = 0;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Create statement
            statement = connection.createStatement();

            // Execute query to retrieve number of rows in the table
            String query = "SELECT COUNT(*) AS count FROM employee_table";
            resultSet = statement.executeQuery(query);

            // Retrieve count from result set
            if (resultSet.next()) {
                count = resultSet.getInt("count");
                System.out.println(count);
            }
        } finally {
            // Close result set and statement
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }

        return count;
    }
    
    @Test
    public void testDatabaseOperations() throws SQLException {
        
        int numberOfEmployees = getNumberOfEmployees();

        // Apply assertion on the number of employees
        assertEquals(numberOfEmployees, 4, "Number of employees does not match");
    }
    
    @AfterSuite
    public void closeDatabaseConnection() throws SQLException {
        // Close database connection
        if (connection != null) {
            connection.close();
        }
    }
}
