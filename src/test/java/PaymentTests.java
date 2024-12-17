import org.example.Payment;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PaymentTests {

 @Test
    void test_isNumericTrue(){
     assertEquals(true, Payment.isNumeric("123"));
     assertEquals(true, Payment.isNumeric("12353535"));
 }
 @Test
    void test_isNumericFalse(){
     assertEquals(false, Payment.isNumeric("12asdf"));
     assertEquals(false, Payment.isNumeric("1asdv"));
 }

 @Test
    void test_isValidMonthTrue(){
     assertEquals(true, Payment.validMonth("12"));
     assertEquals(true, Payment.validMonth("1"));

 }

 @Test
    void test_isValidMonthFalse(){
     assertEquals(false, Payment.validMonth("134"));
     assertEquals(false, Payment.validMonth("1fadsf"));
 }

 @Test
    void test_isValidYearTrue(){
     assertEquals(true, Payment.validYear("34"));
     assertEquals(true, Payment.validYear("99"));
 }

 @Test
    void test_isValidYearFalse(){
     assertEquals(false, Payment.validYear("123"));
     assertEquals(false, Payment.validYear("100"));
 }



    @Test
    void test_createBookingRental() throws SQLException {
        // Mocking Connection and PreparedStatement
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);

        // Set up mock behavior for prepareStatement and executeUpdate
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);

        String insertQuery = "INSERT INTO payments (card_type, card_number, expiry_date, cvc) VALUES (?, ?, ?, ?)";
        PreparedStatement insertStmt = mockConn.prepareStatement(insertQuery);

        // Insert payment details into the database
        insertStmt.setString(1, "Debit Card");
        insertStmt.setString(2, "1234567897654512");
        insertStmt.setString(3, "1032");
        insertStmt.setString(4, "399");
        insertStmt.execute();

        // Verify that executeUpdate() was called once
        verify(mockStmt, times(1)).execute();
    }


}
