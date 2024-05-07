package cadastrodb.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaFisicaDAO {
    private Connection connection; 
    
    public PessoaFisicaDAO(Connection connection) {
        this.connection = connection;
    }
    
    public PessoaFisica getPessoa(int id) throws SQLException {
        try (PreparedeStatement stmt = connection.prepareStatement(
                "SELECT * FROM Pessoa p"))
    }
            
}
