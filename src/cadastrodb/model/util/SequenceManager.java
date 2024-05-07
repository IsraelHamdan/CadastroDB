
package cadastrodb.model.util;
import java.sql.*;
public class SequenceManager {
    private Connection connection; 

    public SequenceManager(Connection connection) {
        this.connection = connection;
    }
    
    public int getValue(String sequenceName) throws SQLException {
        int nextValue = 0; 
        PreparedStatement ps = null; 
        ResultSet rs = null;
        try {
            String query = "SELECT NEXT VALUE FOR" + sequenceName; 
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery(); 
            if(rs.next()) {
                nextValue = rs.getInt(1);
            }
        } finally {
            clolseResoureces(rs, ps);
        }
        return nextValue;
        
    }
    
    private void clolseResoureces(ResultSet rs, PreparedStatement ps) {
        try {
            if (rs != null) {
               rs.close();
            } 
        } 
        catch(SQLException e ) { 
            e.printStackTrace();
        }

        try {
            if(ps != null) {
                ps.close();
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
   
}
