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
                pj = new PessoaJuridica();
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
    public void exibirPessoasJuridias() throws SQLException {
        List<PessoaJuridica> pessoas = getPessoasJuridica();
        for (PessoaJuridica pessoa : pessoas) {
            System.out.println("ID: " + pessoa.getId());
            System.out.println("Nome: " + pessoa.getNome());
            System.out.println("Logradouro: " + pessoa.getLogradouro());
            System.out.println("Cidade: " + pessoa.getCidade());
            System.out.println("Estado: " + pessoa.getEstado());
            System.out.println("Telefone: " + pessoa.getTelefone());
            System.out.println("Email: " + pessoa.getEmail());
            System.out.println("------------------------");
        }
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

   public void alterarPessoaJuriica(int idPJ, PessoaJuridica pessoa) throws SQLException {
            System.out.println(idPJ);
            String query = "UPDATE Pessoas SET Nome = ?, Logradouro = ?, Cidade = ?, Estado = ?, Telefone = ?, Email = ?"
                + "WHERE idPessoa = ?";
            try (PreparedStatement ps = connector.getConnection().prepareStatement(query)) {

                System.out.println(pessoa.getNome());
                System.out.println(idPJ);

                ps.setString(1, pessoa.getNome());
                ps.setString(2, pessoa.getLogradouro());
                ps.setString(3, pessoa.getCidade());
                ps.setString(4, pessoa.getEstado());
                ps.setString(5, pessoa.getTelefone());
                ps.setString(6, pessoa.getEmail()); 
                ps.setInt(7, pessoa.getId());
                ps.executeUpdate();


            } catch (SQLException e) {
                e.printStackTrace();
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
