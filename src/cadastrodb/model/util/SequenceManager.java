
package cadastrodb.model.util;
import java.sql.*;
public class SequenceManager {

    public SequenceManager() {
    }

    public int getValue(String sequenceName) throws SQLException {
        ResultSet rs = null;
        rs = new ConectorDB().getSelected("SELECT NEXT VALUE FOR " + sequenceName);
        if(rs.next()) {
            return rs.getInt(1);
        } else {
            throw new SQLException("erro: n tem valor de sequencia" + sequenceName);
        }
        
    }
}
