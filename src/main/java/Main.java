import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    public static Scanner stdin = new Scanner(System.in);

    public static void main(String[] args) {
        String connectionString = "jdbc:postgresql://localhost:5432/CarRental";
        Properties props = new Properties();

        try {
            InputStream input = Main.class.getClassLoader().getResourceAsStream("db.properties");
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BookingRental.viewAllBookingRentals(connectionString,props);


    }
}
