import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdminTests {


    @Test
    public void testAdminLogin() throws SQLException {
        Statement stmt = mock(Statement.class);
        ResultSet mockRs = mock(ResultSet.class);
        String username = "user123";
        String password = "password123";
        String query = "SELECT * FROM admins WHERE username = '" + username + "' AND password = '" + password + "'";
        when(stmt.executeQuery(query)).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true, false);  // Simulate one row in the result set

        when(mockRs.getInt("admin_id")).thenReturn(1);
        ResultSet result = stmt.executeQuery(query);
        assertNotNull(result);
        assertEquals(1, result.getInt("admin_id"));


    }
}
