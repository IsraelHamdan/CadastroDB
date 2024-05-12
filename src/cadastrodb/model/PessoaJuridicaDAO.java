package cadastrodb.model;

import cadastrodb.model.util.ConectorDB;
import cadastrodb.model.util.SequenceManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;


public class PessoaJuridicaDAO {
    private PessoaJuridica pj; 
    
    private ResultSet  rs; 
    private ConectorDB connector; 
    private SequenceManager sequence; 
    
    private List<PessoaJuridica> pessoas; 
    
    public PessoaJuridicaDAO() {
        sequence = new SequenceManager(); 
        connector = new ConectorDB();
        pj = new PessoaJuridica(); 
        pessoas = new ArrayList<>();
    }
    
    public PessoaJuridica getPessoaJuridica(int id) throws SQLException {
        String query = "Select PJ.idPJ, PJ.CNPJ, P.Nome, P.Logradouro, P.Cidade, P.Estado, P.Telefone, P.Email " +
	"From PessoasJuridicas PJ JOIN Pessoas P ON PJ.idPJ = P.idPessoa WHERE idPJ = ?";
        try (PreparedStatement ps = connector.getConnection()
                .prepareStatement(query)) {
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                pj.setId(rs.getInt("idPj"));
                pj.setCnpj(rs.getString("cnpj"));
                return pj;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connector.close();
        }
        return null;
    } 
    
    public List<PessoaJuridica> getPessoasJuridica() throws SQLException {
        String query = "Select PJ.idPJ, PJ.CNPJ, P.Nome, P.Logradouro, P.Cidade, P.Estado, P.Telefone, P.Email " +
	"From PessoasJuridicas PJ JOIN Pessoas P ON PJ.idPJ = P.idPessoa";
        
        try (PreparedStatement ps = connector.getConnection().prepareStatement(query)) {
            rs = ps.executeQuery(); 
            while(rs.next()) {
                pj.setId(rs.getInt("idPj"));
                pj.setNome(rs.getString("Nome"));
                pj.setCnpj(rs.getString("CNPJ")); 
                pj.setCidade(rs.getString("Cidade")); 
                pj.setLogradouro(rs.getString("Logradouro"));
                pj.setEstado(rs.getString("Estado"));
                pj.setTelefone(rs.getString("Telefone"));
                pj.setEmail(rs.getString("Email"));
                pessoas.add(pj);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            connector.close();
        }
        return pessoas;
    }
    
        public void incluiPessoaJuridica(PessoaJuridica pessoa) throws SQLException {
        String queryPessoa = "INSERT INTO Pessoas(idPessoa, Nome, Logradouro, Cidade, Estado, Telefone, Email) VALUES(?,?,?,?,?,?,?)";
        String queryCnpj = "INSERT INTO PessoasJuridicas(idPJ, CNPJ) VALUES(?,?)";

        try (PreparedStatement psPessoa = connector.getConnection().prepareStatement(queryPessoa);
                PreparedStatement psPj = connector.getConnection().prepareStatement(queryCnpj)) {

            psPessoa.setInt(1, pessoa.getId());
            psPessoa.setString(2, pessoa.getNome());
            psPessoa.setString(3, pessoa.getLogradouro());
            psPessoa.setString(4, pessoa.getCidade());
            psPessoa.setString(5, pessoa.getEstado());
            psPessoa.setString(6, pessoa.getTelefone());
            psPessoa.setString(7, pessoa.getEmail());
            psPessoa.executeUpdate();

            psPj.setInt(1, pessoa.getId());
            psPj.setString(2, pessoa.getCnpj());
            psPj.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connector.close();
        }
    }

    public void alterarPessoaJuridica(PessoaJuridica pessoa) throws SQLException {
        String query = "UPDATE Pessoas SET Nome = ? WHERE idPessoa = ?";
        String queryCnpj = "UPDATE PessoasJuridicas SET CNPJ = ? WHERE idPJ = ?";
        try (PreparedStatement ps = connector.getConnection().prepareStatement(query); 
             PreparedStatement psCNPj = connector.getConnection().prepareStatement(queryCnpj)) {
            
            System.out.println(pessoa.getNome());
            System.out.println(pessoa.getId());
            ps.setString(1, pessoa.getNome());
            ps.setInt(2, pessoa.getId());
            ps.executeUpdate();
            
            psCNPj.setString(1, pessoa.getCnpj());
            psCNPj.setInt(2, pessoa.getId());
            psCNPj.executeUpdate();
            
        } catch (SQLException e) {
        }
    }
    
    public void excluirPessoaJuridica(int id) throws SQLException {
        try(PreparedStatement ps = connector.getConnection().prepareStatement("DELETE FROM PessoasJuridicas WHERE idPj = ?"); 
            PreparedStatement pjDelete = connector.getConnection().prepareStatement("DELETE FROM Pessoas WHERE idPessoa = ?") ) {
            
            //excluindo da tabela de pessoas 
            ps.setInt(1, id);
            ps.executeUpdate();
            
            //excluindo da tabela de pessoas juridicas
            pjDelete.setInt(1, id);
            pjDelete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connector.close();
        }
        
    }
    
}
