import org.example.BookingService;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class BookingServiceTest {
    @Test
    public void testcreateBookingService() throws SQLException {

        Connection mockConn = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);


        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);


        String insertQuery = "INSERT INTO booking_services (service_type, registration_plate, service_status) VALUES (?, ?, ?)";
        PreparedStatement stmt = mockConn.prepareStatement(insertQuery);
        stmt.setString(1, "repair");
        stmt.setString(2, "ABC123");
        stmt.setString(3, "In Progress");
        stmt.executeUpdate();


        verify(mockStmt, times(1)).executeUpdate();
    }

    @Test
    void testCheckForInvalidCarRegPlate() throws  SQLException {

        assertFalse(BookingService.checkForValidCar("asdf"));
        assertFalse(BookingService.checkForValidCar("ABC123"));
    }


    @Test
    void testCheckForValidCarRegPlate() throws SQLException {

        Statement stmt = mock(Statement.class);
        ResultSet expected = mock(ResultSet.class);

        String reg_plate = "XY231BA";

        when(stmt.executeQuery("SELECT * FROM cars where registration_plate = " + reg_plate)).thenReturn(expected);
        when(expected.next()).thenReturn(true, false);  // Simulate one row in the result set
        when(expected.getString("car_type")).thenReturn("Sedan");
        when(expected.getString("make")).thenReturn("Toyota");
        when(expected.getString("model")).thenReturn("Corolla");


        ResultSet result = stmt.executeQuery("SELECT * FROM cars where registration_plate = " + reg_plate);

        assertNotNull(result);
        assertTrue(result.next());



    }

}
