package cadastrodb;

import cadastrodb.model.PessoaFisica;
import cadastrodb.model.PessoaFisicaDAO;
import cadastrodb.model.PessoaJuridica;
import cadastrodb.model.PessoaJuridicaDAO;
import cadastrodb.model.util.SequenceManager;


import java.sql.SQLException;
import java.util.List;

public class CadastroDBTeste {
    
    PessoaFisica pf;
    PessoaJuridica pj;

    private PessoaFisicaDAO pfDAO;
    private PessoaJuridicaDAO pjDAO;
    private SequenceManager seq;

    public CadastroDBTeste() {
        pfDAO = new PessoaFisicaDAO(); 
        seq = new SequenceManager();
        pjDAO = new PessoaJuridicaDAO();
        pf = new PessoaFisica();
        pj = new PessoaJuridica();
    }
   
    private  void testeDeInclusao () throws SQLException{
        String sequencia = "seq_pessoa";
        int seqM = seq.getValue(sequencia);
        
        pf = new PessoaFisica(seqM, "Natalia", "condominio", "Maracaes", "BA", "73987654321", "Natalia@gmail.com", 
                "41258099500");
        pfDAO.incluiPessoa(pf);
        pfDAO.exibirPessoaFisica(seqM);
    }
    
    private void testeDeInclusaoDaPessoaJuridica() throws SQLException {
        String sequencia = "seq_pessoa";
        int seqM = seq.getValue(sequencia);
        
        pj = new PessoaJuridica(seqM, "Bar do João  ", "Avenida", "Jequié", "BA", "7335432100", 
                "JoaooDa51@gmail.com", "62221978099");
        pjDAO.incluirPessoa(pj);
        pjDAO.exibirPessoaJuridica(seqM);
    }
    
    private void buscaPessoaFisica(PessoaFisica pf) throws SQLException {
        pfDAO.exibirPessoaFisica(pf.getId());
        
    }
    
    private void buscaPessoaJuridica()throws SQLException {
        for(PessoaJuridica pj : pjDAO.getPessoas()) {
            System.out.println(pj.exibir());
        }
        pjDAO.exibirPessoasJuridicas();
    }
    
    private void testeDeAlteracao() throws SQLException {
       pf.setNome("Lorena"); 
       pf.setLogradouro("Condominio");
       pf.setCidade("Maracaes");
       pf.setEstado("BA");
       pf.setTelefone("73987654321");
       pf.setEmail("lorena@gmail.com");
       pfDAO.alterarPessoa(pf.getId(),pf);
       pfDAO.exibirPessoaFisica(pf.getId());
       
    }
    
    private void testeDeAlteracaoDaPessoaJuridica() throws SQLException {
        pj.setNome("Bar do Agemiro");
        pj.setLogradouro("rua");
        pj.setCidade("Jequié");
        pj.setEstado("BA");
        pj.setTelefone("7335654321");
        pj.setEmail("bardoAgemiro@gmail.com");
        pjDAO.alterarPessoa(pj.getId(), pj);
        
    }
    
    private void testeDeExclusao() throws SQLException {
        pfDAO.excluirPessoa(pf.getId());
    }
    
    private void testeDeExclusaoDaPessoaJuridica() throws SQLException {
        pjDAO.excluirPessoa(pj.getId());
    }
    
    public void run( ) throws SQLException {
        testeDeInclusao(); 
        testeDeAlteracao();
        buscaPessoaFisica(pf);
        testeDeExclusao();
        testeDeInclusaoDaPessoaJuridica();
        testeDeAlteracaoDaPessoaJuridica();
        buscaPessoaJuridica();
        testeDeExclusaoDaPessoaJuridica();
        
    }
    
    public static void main(String[] args) throws SQLException {
         new CadastroDBTeste().run();
    }

}