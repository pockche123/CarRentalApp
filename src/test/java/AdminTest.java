import org.example.Admin;
import org.example.Main;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AdminTest {


    @Test
    public void testAdminLogin() throws SQLException {
        Statement stmt = mock(Statement.class);
        ResultSet mockRs = mock(ResultSet.class);
        String username = "user123";
        String password = "password123";
        String query = "SELECT * FROM admins WHERE username = '" + username + "' AND password = '" + password + "'";
        when(stmt.executeQuery(query)).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true, false);

        when(mockRs.getInt("admin_id")).thenReturn(1);
        ResultSet result = stmt.executeQuery(query);
        assertNotNull(result);
        assertEquals(1, result.getInt("admin_id"));


    }



}
