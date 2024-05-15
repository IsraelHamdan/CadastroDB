package cadastrodb;

import cadastrodb.model.PessoaFisica;
import cadastrodb.model.PessoaFisicaDAO;
import cadastrodb.model.PessoaJuridica;
import cadastrodb.model.PessoaJuridicaDAO;
import cadastrodb.model.util.SequenceManager;
import java.sql.SQLException;


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
        
        pf = new PessoaFisica(seqM, "Sérgio", "Apto", "Curitiba", "PR", "41987654321", "Sergio@gmail.com", 
                "15458679687");
        pfDAO.incluiPessoa(pf);
        pfDAO.exibirPessoaFisica(seqM);
    }
    
    private void testeDeInclusaoDaPessoaJuridica() throws SQLException {
        String sequencia = "seq_pessoa";
        int seqM = seq.getValue(sequencia);
        
        pj = new PessoaJuridica(seqM, "Bar do João  ", "Avenida", "Jequié", "BA", "7335432100", 
                "JoaooDa51@gmail.com", "66852279669");
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
       pf.setNome("Flávio"); 
       pf.setLogradouro("Apto");
       pf.setCidade("Curitiba");
       pf.setEstado("PR");
       pf.setTelefone("41987654321");
       pf.setEmail("Favin@gmail.com");
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
//        System.out.println("========Inserindo=======");
//        testeDeInclusao(); 
//        System.out.println("========Alterando========");
//        testeDeAlteracao();
//        System.out.println("========Buscando========");
//        buscaPessoaFisica(pf);
//        System.out.println("========Exluindo========");
//        testeDeExclusao();
//        
        System.out.println("========Inserindo PJ=======");
        testeDeInclusaoDaPessoaJuridica();
//        System.out.println("========Alterando PJ========");
//        testeDeAlteracaoDaPessoaJuridica();
//        System.out.println("========Buscando PJ========");
//        buscaPessoaJuridica();
//        System.out.println("========Exluindo PJ========");
//        testeDeExclusaoDaPessoaJuridica();
//        System.out.println("=========TESTES FINALIZADOS========");
    }
    
    public static void main(String[] args) throws SQLException {
         new CadastroDBTeste().run();
    }

}