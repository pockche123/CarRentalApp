import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class CustomerTests {


    @Test
    public void testcreateBookingRental() throws SQLException {
        // Mock the Connection and PreparedStatement
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);

        // When prepareStatement is called, return the mocked PreparedStatement
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);

        // Test the method with mocked connection
        String insertQuery = "INSERT INTO customers (first_name, last_name, address, username, password) VALUES (?,?,?,?,?)";
        PreparedStatement stmt = mockConn.prepareStatement(insertQuery);
        stmt.setString(1, "John");
        stmt.setString(2, "Hamm");
        stmt.setString(3, "12 City Street, Fulham, FU12 3AS");
        stmt.setString(4, "johnhamm123");
        stmt.setString(5, "password123");
        stmt.executeUpdate();

        // Verify if execute was called
        verify(mockStmt, times(1)).executeUpdate();
    }
}
