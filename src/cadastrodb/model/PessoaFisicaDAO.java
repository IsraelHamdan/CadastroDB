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

    public PessoaFisicaDAO(ConectorDB connector, SequenceManager sequence) {
        this.connector = connector; 
        this.sequence = sequence;
    }

    public PessoaFisicaDAO() {
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
        String queryPessoa = "INSERT INTO Pesooas(nome) VALUES(?)";
        String queryPf = "INSERT INTO PessoasFisicas(idPessoaFisica, cpf) VALUES(?,?)";

        try (PreparedStatement psPessoa = connector.getConnection().prepareStatement(queryPessoa);
                PreparedStatement psPessoaFisica = connector.getConnection().prepareStatement(queryPf)) {
                      
            psPessoa.setInt(1, sequence.getValue("seq_pessoa"));
            psPessoa.setString(1, pessoa.getNome());
            psPessoaFisica.setString(1, pessoa.getCpf());
            psPessoaFisica.setString(1, pessoa.getCidade());
            psPessoaFisica.setString(1, pessoa.getLogradouro());
            psPessoaFisica.setString(1, pessoa.getEstado());
            psPessoaFisica.setString(1, pessoa.getTelefone());
            psPessoaFisica.setString(1, pessoa.getEmail());

            psPessoaFisica.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connector.close();
        }

    }

    public void alterarPessoa(PessoaFisica pessoa) throws SQLException {
        String query = "UPDATE PessoasFisicas SET nome = ?, cpf = ? WHERE idPessoaFisica = ?";
        try (PreparedStatement ps = connector.getConnection().prepareStatement(query)) {
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
