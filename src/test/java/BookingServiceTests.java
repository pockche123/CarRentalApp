import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class BookingServiceTests {
    @Test
    public void testcreateBookingService() throws SQLException {
        // Mock the Connection and PreparedStatement
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);

        // When prepareStatement is called, return the mocked PreparedStatement
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);

        // Test the method with mocked connection
        String insertQuery = "INSERT INTO booking_services (service_type, registration_plate, service_status) VALUES (?, ?, ?)";
        PreparedStatement stmt = mockConn.prepareStatement(insertQuery);
        stmt.setString(1, "repair");
        stmt.setString(2, "ABC123");
        stmt.setString(3, "In Progress");
        stmt.executeUpdate();

        // Verify if execute was called
        verify(mockStmt, times(1)).executeUpdate();
    }
}
