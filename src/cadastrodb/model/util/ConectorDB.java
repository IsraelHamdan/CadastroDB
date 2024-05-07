package cadastrodb.model.util;
import java.sql.*;
import java.util.Properties;

public class ConectorDB {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=loja" ; 
    private static final String USERNAME = "loja";
    private static final String SENHA = "loja";
    private Connection conection; 
    private Properties props;
    
    public ConectorDB() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            props = new Properties(); 
            props.setProperty("user", USERNAME);
            props.setProperty("password", SENHA);
            conection = DriverManager.getConnection(URL, props);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Connection getConnection() {
        return conection;
    }
    
    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return conection.prepareStatement(sql);
    }
    
    public ResultSet getSelected(String sql) throws SQLException {
        Statement statement = conection.createStatement();
        return statement.executeQuery(sql);
    }
    
    // fecha o PreparedStatement
    public void close(PreparedStatement ps) {
        try {
           if(ps != null && !ps.isClosed()) {
               ps.close();
           }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }
    // metodo close para fechar o result
    public void close (ResultSet rs) {
        try {
            if(rs != null && !rs.isClosed()) rs.clearWarnings();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void close() {
        try {
            if(conection != null && !conection.isClosed()) {
                conection.close();
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
