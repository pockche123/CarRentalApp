import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.example.Car;
import org.example.Main;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class CarTest {

    // start by mocking the database related objects
    Connection mockConnection = mock(Connection.class);
    PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
    ResultSet mockResultSet = mock(ResultSet.class);

    //This test ensures that viewAllAvailableCars method behaves correctly in terms of its flow, logic, and interaction with database dependencies
    @Test
    public void testViewAllAvailableCars() throws SQLException {
        //define the mocked behaviour
        when(mockConnection.prepareStatement("SELECT * FROM cars WHERE car_status = ?")).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false); // Two rows and then no more rows
        when(mockResultSet.getString("registration_plate")).thenReturn("CAR123", "CAR456");
        when(mockResultSet.getString("car_type")).thenReturn("Sedan", "SUV");
        when(mockResultSet.getString("make")).thenReturn("Toyota", "Ford");
        when(mockResultSet.getString("model")).thenReturn("Corolla", "Explorer");


        // Mock the Main.establishConnection() method to return the mocked connection
        Mockito.mockStatic(Main.class);
        when(Main.establishConnection()).thenReturn(mockConnection);

        //call the method under test
        ArrayList<String> carRegistrationList = Car.viewAllAvailableCars();

        //Verify the returned list
        assertEquals(2, carRegistrationList.size()); // Ensure 2 cars were added
        assertEquals("CAR123", carRegistrationList.get(0)); // First car registration
        assertEquals("CAR456", carRegistrationList.get(1));

        //Verify interactions with the mocked objects
        verify(mockConnection).prepareStatement("SELECT * FROM cars WHERE car_status = ?");
        verify(mockPreparedStatement).setString(1, "available");
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet, times(3)).next(); // Called 3 times: 2 rows + 1 false
        verify(mockResultSet, times(2)).getString("registration_plate");
        verify(mockResultSet, times(2)).getString("car_type");
        verify(mockResultSet, times(2)).getString("make");
        verify(mockResultSet, times(2)).getString("model");
    }
}
