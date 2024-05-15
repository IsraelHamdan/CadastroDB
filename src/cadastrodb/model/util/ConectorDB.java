package cadastrodb.model.util;
import cadastrodb.model.PessoaFisicaDAO;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ConectorDB {
    private static final Logger LOGGER = Logger.getLogger(PessoaFisicaDAO.class.getName());
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=loja;encrypt=true;trustServerCertificate=true" ; 
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
            props.setProperty("encrypt", "true");
            props.setProperty("trustServerCertificate", "true");
            conection = DriverManager.getConnection(URL, props);    
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE, "erro ao conectar com o banco de dados", e);
        }
    }
        
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USERNAME, SENHA);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "error ao realizar a conexão do driver com o banco", e);
        }
        return null;
    }
    
    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        try {
            return conection.prepareStatement(sql);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "erro ao usar o preparedStatement na classe conectorDB: " , e);
        }
        return null;
    }
    
    public ResultSet getSelected(String sql) throws SQLException {
        try {
            Statement statement = conection.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "erro ao definir o ResultSet: ", e);
        }
        return null;
    }
    
    // fecha o PreparedStatement
    public void close(PreparedStatement ps) {
        try {
           if(ps != null && !ps.isClosed()) {
               ps.close();
           }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "erro ao fechar o PreparedStatement: ", e);
        } 
    }
    // metodo close para fechar o result
    public void close (ResultSet rs) {
        try {
            if(rs != null && !rs.isClosed()) rs.clearWarnings();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "erro ao fechar o ResultSet: ", e);
        }
    }
    
    public void close() {
        try {
            if(conection != null && !conection.isClosed()) {
                conection.close();
            } 
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "erro ao fechar o Connection: ", e);
        }
    }
}
