
package cadastrodb.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaJuridicaDAO {
    private Connection connection;
    private PessoaJuridica pj;
    private ResultSet rs;
    private List<PessoaJuridica> pessoasJuridicas;

    public void PessoaJuridicaDAO(Connection connection) {
        this.connection = connection;
        pj = new PessoaJuridica();
        pessoasJuridicas = new ArrayList();
    }

    public PessoaJuridica getPessoa(int id) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM PessoasJuridicas WHERE id = ?")) {
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                pj.setId(rs.getInt("id"));
                pj.setNome(rs.getString("nome"));
                return pj;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PessoaJuridica> getPessoas() throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM PessoasJuridicas")) {
            rs = ps.executeQuery();
            while (rs.next()) {
                pj.setId(rs.getInt("id"));
                pj.setNome(rs.getString("nome"));
                pj.setCnpj(rs.getString("CNPJ"));
                pj.setCidade(rs.getString("Cidade"));
                pj.setLogradouro(rs.getString("Logradouro"));
                pj.setEstado(rs.getString("estado"));
                pj.setTelefone(rs.getString("telefone"));
                pj.setEmail(rs.getString("email"));
                pessoasJuridicas.add(pj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pessoasJuridicas;
    }

    public void incluiPessoa(PessoaJuridica pessoa) throws SQLException {
        String queryPessoa = "INSERT INTO Pesooas(nome) VALUES(?)";
        String queryPj = "INSERT INTO PessoasJuridicas(id, cnpj) VALUES(?,?)";

        try (PreparedStatement psPessoa = connection.prepareStatement(queryPessoa);
                PreparedStatement psPessoaJuridica = connection.prepareStatement(queryPj)) {
            connection.setAutoCommit(false);

            psPessoa.setInt(1, pessoa.getId());
            psPessoa.setString(1, pessoa.getNome());
            psPessoaJuridica.setString(1, pessoa.getCnpj());
            psPessoaJuridica.setString(1, pessoa.getCidade());
            psPessoaJuridica.setString(1, pessoa.getLogradouro());
            psPessoaJuridica.setString(1, pessoa.getEstado());
            psPessoaJuridica.setString(1, pessoa.getTelefone());
            psPessoaJuridica.setString(1, pessoa.getEmail());

            psPessoaJuridica.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }

    }

    public void alterarPessoa(PessoaJuridica pessoa) throws SQLException {
        String query = "UPDATE PessoasJuridicas SET nome = ?, cnpj = ? WHERE idPessoaJuridica = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, pessoa.getId());
            ps.setString(1, pessoa.getNome());
            ps.setString(1, pessoa.getCnpj());
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
        try (PreparedStatement ps = connection
                .prepareStatement("DELETE FROM PessoasJuridicas WHERE idPessoaJuridica = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM Pessoas WHERE idPessoa = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
