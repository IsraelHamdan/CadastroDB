package cadastrodb.model;

import cadastrodb.model.util.ConectorDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class PessoaFisicaDAO {
    private PessoaFisica pf;

    private ResultSet rs;
    private final ConectorDB connector;

    private List<PessoaFisica> pessoas;
    private String error;
    
    public PessoaFisicaDAO() {
        connector = new ConectorDB();
        pf = new PessoaFisica();
        pessoas = new ArrayList<>();
 
    }
    private static final Logger LOGGER = Logger.getLogger(PessoaFisicaDAO.class.getName());
    
    private String errorMenssage(String verbo, String especificador) {
        String mensage = "Erro ao %s : " + verbo + "pessoas fisicas %s banco" + especificador;
        return mensage;
    }
 
    public PessoaFisica getPessoa(int id) throws SQLException {
        String query = "SELECT PF.idPessoaFisica, PF.CPF, P.Nome, P.Logradouro, P.Cidade, P.Estado, P.Telefone, P.email " +
	"From PessoasFisicas PF JOIN Pessoas P ON PF.idPessoaFisica = P.idPessoa WHERE P.idPessoa = ?";
        try (PreparedStatement ps = connector.getConnection() 
                .prepareStatement(query)) {
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                pf.setNome(rs.getString("Nome"));
                pf.setLogradouro(rs.getString("Logradouro"));
                pf.setId(rs.getInt("idPessoaFisica"));
                pf.setCpf(rs.getString("cpf"));
                pf.setCidade(rs.getString("Logradouro"));
                pf.setEstado(rs.getString("Estado")); 
                pf.setTelefone(rs.getString("Telefone")); 
                pf.setEmail(rs.getString("Email"));
                
                return pf;
            }
        } catch (SQLException e) {
            error = errorMenssage("buscar", "no");
            LOGGER.log(Level.SEVERE, error, e );
        } finally {
            connector.close();
        }
        return null;
    }
    
    public void exibirPessoaFisica(int idPessoaFisica) throws SQLException {
        try {
            if(idPessoaFisica != 0) {
              PessoaFisica pf = getPessoa(idPessoaFisica); 
              if(pf != null) {
                  System.out.println("CPF: " + pf.getCpf());
                  System.out.println("Nome: " + pf.getNome());
                  System.out.println("Logradouro: " + pf.getLogradouro());
                  System.out.println("Cidade: " + pf.getCidade());
                  System.out.println("Estado: " + pf.getEstado());
                  System.out.println("Telefone " + pf.getTelefone());
                  System.out.println("Email: " + pf.getEmail());
              } else {
                  System.out.println("Pessoa não encontrada");
              }
          }
        } catch (SQLException e) {
            error = "Erro %s ao exibir pessoas encontradas no banco";
            LOGGER.log(Level.SEVERE, error, e);
        }

      }
    
    public List<PessoaFisica> getPessoas() throws SQLException {
        String query = "SELECT PF.idPessoaFisica, PF.CPF, P.Nome, P.Logradouro, P.Cidade, P.Estado, P.Telefone, P.email " +
	"From PessoasFisicas PF JOIN Pessoas P ON PF.idPessoaFisica = P.idPessoa";
        try (PreparedStatement ps = connector.getConnection().prepareStatement(query)) {
            rs = ps.executeQuery();
            while (rs.next()) {
                pf = new PessoaFisica();
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
            error = errorMenssage("listar", "do"); 
            LOGGER.log(Level.SEVERE, error, e);
            
        } finally {
            connector.close();
        }
        
        return pessoas;
    }
    
    public void exibirPessoasFisicas() throws SQLException{
        try {
            List<PessoaFisica> pessoas = getPessoas();
            if(!pessoas.isEmpty()) {
                for (PessoaFisica pessoa : pessoas) {
                    System.out.println("ID: " + pessoa.getId());
                    System.out.println("Nome: " + pessoa.getNome());
                    System.out.println("Logradouro: " + pessoa.getLogradouro());
                    System.out.println("Cidade: " + pessoa.getCidade());
                    System.out.println("Estado: " + pessoa.getEstado());
                    System.out.println("Telefone: " + pessoa.getTelefone());
                    System.out.println("Email: " + pessoa.getEmail());
                    System.out.println("------------------------");
                }
            } else {
                System.out.println("Id não encontrado!");
            }
        } catch (SQLException e) {
            error = errorMenssage("exibir","do");
            LOGGER.log(Level.SEVERE, error,  e);
        }


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
            error = errorMenssage("incluir", "no");
            LOGGER.log(Level.SEVERE, error, e);
        } finally {
            connector.close();
        }
    }

    public void alterarPessoa(int idPessoaFisica, PessoaFisica pessoa) throws SQLException {
            String query = "UPDATE Pessoas SET Nome = ?, Logradouro = ?, Cidade = ?, Estado = ?, Telefone = ?, Email = ?"
                + "WHERE idPessoa = ?";
            try (PreparedStatement ps = connector.getConnection().prepareStatement(query)) {
                ps.setString(1, pessoa.getNome());
                ps.setString(2, pessoa.getLogradouro());
                ps.setString(3, pessoa.getCidade());
                ps.setString(4, pessoa.getEstado());
                ps.setString(5, pessoa.getTelefone());
                ps.setString(6, pessoa.getEmail()); 
                ps.setInt(7, pessoa.getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                error = errorMenssage("alterar", "no");
                LOGGER.log(Level.SEVERE, error, e);
            }
     }


    public void excluirPessoa(int id) throws SQLException {   
        String queryDeletepf = "DELETE FROM PessoasFisicas WHERE idPessoaFisica = ?";
        String queryDeletePs = "DELETE FROM Pessoas WHERE idPessoa = ?";
        try(PreparedStatement ps = connector.getConnection().prepareStatement(queryDeletepf); 
            PreparedStatement pfDelete = connector.getConnection().prepareStatement(queryDeletePs)) {
            
            //excluindo da tabela de pessoas 
            ps.setInt(1, id);
            ps.executeUpdate();
            
            //excluindo da tabela de pessoas fisicas;
            pfDelete.setInt(1, id);
            pfDelete.executeUpdate();
        } catch (SQLException e) {
            error = errorMenssage("excluir", "do");
            LOGGER.log(Level.SEVERE, error, e);
        } finally {
            connector.close();
        }
    }
}
