package cadastrodb.model;

import cadastrodb.model.util.ConectorDB;
import cadastrodb.model.util.SequenceManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaFisicaDAO {
    private PessoaFisica pf;

    private ResultSet rs;
    private ConectorDB connector;
    private SequenceManager sequence;

    private List<PessoaFisica> pessoas;

    public PessoaFisicaDAO() {
        sequence = new SequenceManager();
        connector = new ConectorDB();
        pf = new PessoaFisica();
        pessoas = new ArrayList<>();
    }

    public PessoaFisica getPessoa(int id) throws SQLException {
        String query = "SELECT PF.idPessoaFisica, PF.CPF, P.Nome, P.Logradouro, P.Cidade, P.Estado, P.Telefone, P.email " +
	"From PessoasFisicas PF JOIN Pessoas P ON PF.idPessoaFisica = P.idPessoa WHERE P.idPessoa = ?";
        try (PreparedStatement ps = connector.getConnection() 
                .prepareStatement(query)) {
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                pf.setId(rs.getInt("idPessoaFisica"));
                pf.setCpf(rs.getString("cpf"));
                pf.setCidade(rs.getString("Logradouro"));
                pf.setEstado(rs.getString("Estado")); 
                pf.setTelefone(rs.getString("Telefone")); 
                pf.setEmail(rs.getString("Email"));
                return pf;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connector.close();
        }
        return null;
    }

    public List<PessoaFisica> getPessoas() throws SQLException {
        String query = "SELECT PF.idPessoaFisica, PF.CPF, P.Nome, P.Logradouro, P.Cidade, P.Estado, P.Telefone, P.email " +
	"From PessoasFisicas PF JOIN Pessoas P ON PF.idPessoaFisica = P.idPessoa";
        try (PreparedStatement ps = connector.getConnection().prepareStatement(query)) {
            rs = ps.executeQuery();
            while (rs.next()) {
                pf.setId(rs.getInt("idPessoaFisica"));
                pf.setNome(rs.getString("Nome"));
                pf.setCpf(rs.getString("CPF"));
                pf.setCidade(rs.getString("Cidade"));
                pf.setLogradouro(rs.getString("Logradouro"));
                pf.setEstado(rs.getString("Estado"));
                pf.setTelefone(rs.getString("Telefone"));
                pf.setEmail(rs.getString("Email"));
                pessoas.add(pf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connector.close();
        }
        return pessoas;
    }

     public void incluiPessoa(PessoaFisica pessoa) throws SQLException {
        String queryPessoa = "INSERT INTO Pessoas(idPessoa, Nome, Logradouro, Cidade, Estado, Telefone, Email) VALUES(?,?,?,?,?,?,?)";
        String queryPf = "INSERT INTO PessoasFisicas(idPessoaFisica, cpf) VALUES(?,?)";

        try (PreparedStatement psPessoa = connector.getConnection().prepareStatement(queryPessoa);
                PreparedStatement psPessoaFisica = connector.getConnection().prepareStatement(queryPf)) {

            psPessoa.setInt(1, pessoa.getId());
            psPessoa.setString(2, pessoa.getNome());
            psPessoa.setString(3, pessoa.getLogradouro());
            psPessoa.setString(4, pessoa.getCidade());
            psPessoa.setString(5, pessoa.getEstado());
            psPessoa.setString(6, pessoa.getTelefone());
            psPessoa.setString(7, pessoa.getEmail());
            psPessoa.executeUpdate();

            psPessoaFisica.setInt(1, pessoa.getId());
            psPessoaFisica.setString(2, pessoa.getCpf());
            psPessoaFisica.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connector.close();
        }
    }

    public void alterarPessoa(PessoaFisica pessoa) throws SQLException {
        String query = "UPDATE Pessoas SET Nome = ? WHERE idPessoa = ?";
        String queryCpf = "UPDATE PessoasFisicas SET cpf = ? WHERE idPessoaFisica = ?";
        try (PreparedStatement ps = connector.getConnection().prepareStatement(query); 
             PreparedStatement psCpf = connector.getConnection().prepareStatement(queryCpf)) {
            
            System.out.println(pessoa.getNome());
            System.out.println(pessoa.getId());
            ps.setString(1, pessoa.getNome());
            ps.setInt(2, pessoa.getId());
            ps.executeUpdate();
            
            psCpf.setString(1, pessoa.getCpf());
            psCpf.setInt(2, pessoa.getId());
            psCpf.executeUpdate();
            
        } catch (SQLException e) {
        }
    }


    public void excluirPessoa(int id) throws SQLException {    
        try(PreparedStatement ps = connector.getConnection().prepareStatement("DELETE FROM PessoasFisicas WHERE idPessoaFisica = ?"); 
            PreparedStatement pfDelete = connector.getConnection().prepareStatement("DELETE FROM Pessoas WHERE idPessoa = ?") ) {
            
            //excluindo da tabela de pessoas 
            ps.setInt(1, id);
            ps.executeUpdate();
            
            //excluindo da tabela de pessoas fisicas;
            pfDelete.setInt(1, id);
            pfDelete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connector.close();
        }
    }
}
