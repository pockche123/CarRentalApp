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

}
