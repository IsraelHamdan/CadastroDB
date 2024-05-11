package cadastrodb.model;

import cadastrodb.model.util.ConectorDB;
import cadastrodb.model.util.SequenceManager;
import java.sql.Connection;
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
        try (PreparedStatement ps = connector.getConnection().prepareStatement("SELECT * FROM PessoasFisicas WHERE idPessoaFisica = ?")) {
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                pf.setId(rs.getInt("idPessoaFisica"));
                pf.setCpf(rs.getString("cpf"));
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
        try (PreparedStatement ps = connector.getConnection().prepareStatement("SELECT * FROM PessoasFisicas")) {
            rs = ps.executeQuery();
            while (rs.next()) {
                pf.setId(rs.getInt("idPesoaFisica"));
                pf.setNome(rs.getString("nome"));
                pf.setCpf(rs.getString("CPF"));
                pf.setCidade(rs.getString("Cidade"));
                pf.setLogradouro(rs.getString("Logradouro"));
                pf.setEstado(rs.getString("estado"));
                pf.setTelefone(rs.getString("telefone"));
                pf.setEmail(rs.getString("email"));
                pessoas.add(pf);
            }
        } catch (SQLException e)    {                                                     
            e.printStackTrace();
        } finally {
            connector.close();
        }
        return pessoas;
    }

    public void incluiPessoa(PessoaFisica pessoa) throws SQLException {
        String queryPessoa = "INSERT INTO Pessoas(idPessoa, nome, logradouro, cidade, estado, telefone, email) VALUES(?,?,?,?,?,?,?)";
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
        String query = "UPDATE Pessoas SET nome = ? WHERE idPessoa = ?";
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
        try (PreparedStatement ps = connector.getConnection().
                prepareStatement("DELETE FROM PessoasFisicas WHERE idPessoaFisica = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connector.close();
        }
        try (PreparedStatement ps = connector.getConnection().prepareStatement("DELETE FROM Pessoas WHERE idPessoa = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connector.close();
        }
    }
}
