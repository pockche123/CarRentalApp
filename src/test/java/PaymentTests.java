import org.example.Payment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
