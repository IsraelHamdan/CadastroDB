package cadastrodb.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaFisicaDAO {
    private PessoaFisica pf;
    private Connection connection;
    
    private ResultSet rs;

    private List<PessoaFisica> pessoas;

    public PessoaFisicaDAO(Connection connection) {
        this.connection = connection;
        pf = new PessoaFisica();
        pessoas = new ArrayList<>();
    }

    public PessoaFisica getPessoa(int id) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM PessoaFisica WHERE id = ?") ) {
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                pf.setId(rs.getInt("id"));
                pf.setNome(rs.getString("nome"));
                return pf;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PessoaFisica> getPessoas() throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM PessoaFisica")) {
            rs = ps.executeQuery();
            while (rs.next()) {
                pf.setId(rs.getInt("id"));
                pf.setNome(rs.getString("nome"));
                pf.setCpf(rs.getString("CPF"));
                pf.setCidade(rs.getString("Cidade"));
                pf.setLogradouro(rs.getString("Logradouro"));
                pf.setEstado(rs.getString("estado"));
                pf.setTelefone(rs.getString("telefone"));
                pf.setEmail(rs.getString("email"));
                pessoas.add(pf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pessoas;
    }


    public void incluiPessoa(PessoaFisica pessoa) throws SQLException {
        String queryPessoa = "INSERT INTO Pesooas(nome) VALUES(?)";
        String queryPf = "INSERT INTO PessoasFisicas(id, cpf) VALUES(?,?)";

        try (PreparedStatement psPessoa = connection.prepareStatement(queryPessoa);
                PreparedStatement psPessoaFisica = connection.prepareStatement(queryPf)) {
            connection.setAutoCommit(false);

            psPessoa.setInt(1, pessoa.getId());
            psPessoa.setString(1, pessoa.getNome());
            psPessoaFisica.setString(1, pessoa.getCpf());
            psPessoaFisica.setString(1, pessoa.getCpf());
            psPessoaFisica.setString(1, pessoa.getCidade());
            psPessoaFisica.setString(1, pessoa.getLogradouro());
            psPessoaFisica.setString(1, pessoa.getEstado());
            psPessoaFisica.setString(1, pessoa.getTelefone());
            psPessoaFisica.setString(1, pessoa.getEmail());

            psPessoaFisica.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        
    }
    
    public void alterarPessoa(PessoaFisica pessoa) throws SQLException {
        String query = "UPDATE PessoasFisicas SET nome = ?, cpf = ? WHERE idPesspaFisica = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, pessoa.getId());
            ps.setString(1, pessoa.getNome());
            ps.setString(1, pessoa.getCpf());
            ps.setString(1, pessoa.getLogradouro()); 
            ps.setString(1, pessoa.getCidade());
            ps.setString(1, pessoa.getEstado());
            ps.setString(1, pessoa.getTelefone());
            ps.setString(1, pessoa.getEmail());
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
   public void excluirPessoa(int id) throws SQLException {
       try (PreparedStatement ps = connection.prepareStatement("DELETE FROM PessoasFisicas WHERE idPessoaFisica = ?")){
           ps.setInt(1, id);
           ps.executeUpdate();
       } catch (SQLException e) {
           e.printStackTrace();
       }
       try (PreparedStatement ps = connection.prepareStatement("DELETE FROM Pessoas WHERE idPessoa = ?")){
           ps.setInt(1, id); 
           ps.executeUpdate();
       } catch (SQLException e) {
           e.printStackTrace();
       }
   }
}
