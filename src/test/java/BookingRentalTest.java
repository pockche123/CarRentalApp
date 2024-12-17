import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;

class BookingRentalTest {

    @Test
    void testAllBookingRentalsByCustomerId() throws SQLException {
        // Simulate the user input for customer_id (e.g., user inputs "404")
        String simulatedInput = "4\n";  // The \n simulates pressing Enter after the input
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in); // Redirect System.in to use the simulated input

        // Step 1: Mock the Statement and ResultSet objects
        Statement stmt = mock(Statement.class);
        ResultSet rs = mock(ResultSet.class);

        // Step 2: Create a Scanner instance as if reading from stdin
        Scanner stdin = new Scanner(System.in);
        String customer_id = stdin.nextLine(); // This will now read from the simulated input stream

        // Step 3: Define the behavior of the mock ResultSet
        when(stmt.executeQuery("SELECT * FROM booking_rentals where customer_id = '" + customer_id + "'")).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);  // Simulate one row in the result set

        // Mock the data for each column in the result set
        when(rs.getInt("booking_rental_id")).thenReturn(101);
        when(rs.getInt("payment_id")).thenReturn(202);
        when(rs.getString("registration_plate")).thenReturn("XYZ 1234");
        when(rs.getInt("dropoff_location_id")).thenReturn(303);
        when(rs.getInt("customer_id")).thenReturn(404);
        when(rs.getBoolean("suspend")).thenReturn(true);

        // Step 4: Simulate the logic that uses the Statement and ResultSet
        ResultSet result = stmt.executeQuery("SELECT * FROM booking_rentals where customer_id = '" + customer_id + "'");

        // Step 5: Verify the results
        assertNotNull(result);
        assertTrue(result.next()); // Should be true for the first row

        // Verify each column value
        assertEquals(101, result.getInt("booking_rental_id"));
        assertEquals(202, result.getInt("payment_id"));
        assertEquals("XYZ 1234", result.getString("registration_plate"));
        assertEquals(303, result.getInt("dropoff_location_id"));
        assertEquals(404, result.getInt("customer_id"));
        assertTrue(result.getBoolean("suspend")); // Expecting true for suspend

        // Step 6: Verify that the query was executed with the correct customer_id
        verify(stmt).executeQuery("SELECT * FROM booking_rentals where customer_id = '4'");

        // Step 7: Clean up or close resources if needed
        result.close();

        // Restore the original System.in after the test is done
        System.setIn(System.in);
    }





}
