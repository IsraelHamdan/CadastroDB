
package cadastrodb.model.util;
import java.sql.*;
public class SequenceManager {
    private Connection connection; 

    public SequenceManager(Connection connection) {
        this.connection = connection;
    }
    static int nextValue = 0; 
    public int getValue(String sequenceName) throws SQLException {
        
        PreparedStatement ps = null; 
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement("SELECT NEXT VALUE FOR seq_pessoa");
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
