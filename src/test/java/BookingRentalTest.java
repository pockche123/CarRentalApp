import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;
import java.util.Properties;

import org.example.BookingRental;

import static org.mockito.Mockito.*;

public class BookingRentalTest {

    @Test
    public void testViewAllBookingRentals() throws Exception {
        // Mock the required objects
        Connection mockConnection = Mockito.mock(Connection.class);
        Statement mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        ResultSet mockResultSet = Mockito.mock(ResultSet.class);

        // Define the behavior of the mocks
        when(mockConnection.prepareStatement("SELECT * FROM booking_rentals WHERE customer_id = ?"))
                .thenReturn((PreparedStatement) mockPreparedStatement);
        when(((PreparedStatement) mockPreparedStatement).executeQuery()).thenReturn(mockResultSet);

        // Mock the result set data
        when(mockResultSet.next()).thenReturn(true, true, false); // Two rows of data
        when(mockResultSet.getInt("booking_rental_id")).thenReturn(1, 2);
        when(mockResultSet.getInt("payment_id")).thenReturn(1001, 1002);
        when(mockResultSet.getString("registration_plate")).thenReturn("ABC123", "XYZ789");
        when(mockResultSet.getInt("dropoff_location_id")).thenReturn(10, 20);
        when(mockResultSet.getInt("customer_id")).thenReturn(501, 502);
        when(mockResultSet.getBoolean("suspend")).thenReturn(false, true);

        // Mock the DriverManager to return the mocked connection
        Mockito.mockStatic(DriverManager.class).when(() -> DriverManager.getConnection(anyString(), any(Properties.class)))
                .thenReturn(mockConnection);

        // Call the method under test
        String testConnectionString = "jdbc:testdb";
        Properties testProps = new Properties();
        BookingRental.viewAllBookingRentals(testConnectionString, testProps);

        // Verify the interactions

        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet, times(3)).next();
        verify(mockResultSet, times(2)).getInt("booking_rental_id");
        verify(mockResultSet, times(2)).getInt("payment_id");
        verify(mockResultSet, times(2)).getString("registration_plate");
        verify(mockResultSet, times(2)).getInt("dropoff_location_id");
        verify(mockResultSet, times(2)).getInt("customer_id");
        verify(mockResultSet, times(2)).getBoolean("suspend");
    }

}
