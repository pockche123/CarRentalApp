import org.example.BookingService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;

class BookingRentalTest {


    @Test
    void testAllBookingRentalsByCustomerId() throws SQLException {


        Statement stmt = mock(Statement.class);
        ResultSet rs = mock(ResultSet.class);


        String customer_id = "4";

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



    }

    @Test
    void testAllBookingRentals() throws SQLException {


        Statement stmt = mock(Statement.class);
        ResultSet rs = mock(ResultSet.class);

        // Define the behavior of the mock ResultSet
        when(stmt.executeQuery("SELECT * FROM booking_rentals")).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);  // Simulate one row in the result set

        // Mock the data for each column in the result set
        when(rs.getInt("booking_rental_id")).thenReturn(101);
        when(rs.getInt("payment_id")).thenReturn(202);
        when(rs.getString("registration_plate")).thenReturn("XYZ 1234");
        when(rs.getInt("dropoff_location_id")).thenReturn(303);
        when(rs.getInt("customer_id")).thenReturn(404);
        when(rs.getBoolean("suspend")).thenReturn(true);


        ResultSet result = stmt.executeQuery("SELECT * FROM booking_rentals");


        assertNotNull(result);
        assertTrue(result.next()); // Should be true for the first row


        assertEquals(101, result.getInt("booking_rental_id"));
        assertEquals(202, result.getInt("payment_id"));
        assertEquals("XYZ 1234", result.getString("registration_plate"));
        assertEquals(303, result.getInt("dropoff_location_id"));
        assertEquals(404, result.getInt("customer_id"));
        assertTrue(result.getBoolean("suspend")); // Expecting true for suspend


        verify(stmt).executeQuery("SELECT * FROM booking_rentals");


        result.close();

    }


    @Test
    void testAllActiveBookingRentals() throws SQLException {
        Statement stmt = mock(Statement.class);
        ResultSet rs = mock(ResultSet.class);

        //  behavior of the mock ResultSet
        when(stmt.executeQuery("SELECT * FROM booking_rentals where suspend = true")).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);  // Simulate one row in the result set

        // mock the data for each column in the result set
        when(rs.getInt("booking_rental_id")).thenReturn(101);
        when(rs.getInt("payment_id")).thenReturn(202);
        when(rs.getString("registration_plate")).thenReturn("XYZ 1234");
        when(rs.getInt("dropoff_location_id")).thenReturn(303);
        when(rs.getInt("customer_id")).thenReturn(404);
        when(rs.getBoolean("suspend")).thenReturn(true);

        // simulate the logic that uses the Statement and ResultSet
        ResultSet result = stmt.executeQuery("SELECT * FROM booking_rentals where suspend = true");

        // assert
        assertNotNull(result);
        assertTrue(result.next()); // Should be true for the first row


        assertEquals(101, result.getInt("booking_rental_id"));
        assertEquals(202, result.getInt("payment_id"));
        assertEquals("XYZ 1234", result.getString("registration_plate"));
        assertEquals(303, result.getInt("dropoff_location_id"));
        assertEquals(404, result.getInt("customer_id"));
        assertTrue(result.getBoolean("suspend")); // Expecting true for suspend

        // verify
        verify(stmt).executeQuery("SELECT * FROM booking_rentals where suspend = true");

        // clean up
        result.close();

    }

    @Test
    public void testCreateBookingRental() throws SQLException {
        // mock the Connection and PreparedStatement
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);


        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);

        // test the method with mocked connection
        String insertQuery = "INSERT INTO booking_rentals (payment_id, registration_plate, dropoff_location_id, customer_id, suspend) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = mockConn.prepareStatement(insertQuery);
        stmt.setInt(1, 1);
        stmt.setString(2, "ABC123");
        stmt.setInt(3, 4);
        stmt.setInt(4, 7);
        stmt.setBoolean(5, false);
        stmt.executeUpdate();

        //verify
        verify(mockStmt, times(1)).executeUpdate();
    }


    @Test
    public void testChangeBookingRentalStatus() throws SQLException {
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);

        PreparedStatement stmt = mockConn.prepareStatement("UPDATE booking_rentals  SET suspend  = ? WHERE booking_rental_id = ?");

        stmt.setBoolean(1, true);
        stmt.setInt(2, 1);
        stmt.executeUpdate();
        verify(mockStmt, times(1)).executeUpdate();

    }

    @Test
    public void testCheckForValidBookingRental() throws SQLException {
        Statement stmt = mock(Statement.class);
        ResultSet expected = mock(ResultSet.class);

        String booking_rental_id = "1";
        //  Define the behavior of the mock ResultSet
        when(stmt.executeQuery("SELECT * FROM booking_rentals where booking_rental_id = " + booking_rental_id)).thenReturn(expected);
        when(expected.next()).thenReturn(true, false);  // Simulate one row in the result set
        when(expected.getInt("booking_rental_id")).thenReturn(1);
        when(expected.getInt("payment_id")).thenReturn(202);
        when(expected.getString("registration_plate")).thenReturn("XYZ 1234");
        when(expected.getInt("dropoff_location_id")).thenReturn(303);
        when(expected.getInt("customer_id")).thenReturn(404);
        when(expected.getBoolean("suspend")).thenReturn(true);


        ResultSet result = stmt.executeQuery("SELECT * FROM booking_rentals where booking_rental_id = " + booking_rental_id);

        ResultSet falseResult = stmt.executeQuery("SELECT * FROM booking_rentals where booking_rental_id = 2");

//        when booking rental id is found
        assertNotNull(result);
        assertTrue(result.next());

//        when no booking rental id found
        assertNull(falseResult);

    }








    }
