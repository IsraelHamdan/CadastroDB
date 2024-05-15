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
                "15470783081");
        pfDAO.incluiPessoa(pf);
        pfDAO.exibirPessoaFisica(seqM);
    }
    
    private void testeDeInclusaoDaPessoaJuridica() throws SQLException {
        String sequencia = "seq_pessoa";
        int seqM = seq.getValue(sequencia);
        
        pj = new PessoaJuridica(seqM, "Cantinho do café", "rua", "Jequié", "BA", 
                "7337778888","cantinhodocafe@jequiecafe.com", "78771426094");
        pjDAO.incluirPessoa(pj);
        pjDAO.exibirPessoaJuridica(seqM);   
    }
    
    private void buscaPessoaFisica(PessoaFisica pf) throws SQLException {
        pfDAO.exibirPessoaFisica(pf.getId());
        
    }
    
    private void buscaPessoaJuridica()throws SQLException {
        pjDAO.exibirPessoaJuridica(pj.getId());
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
        pj.setNome("Café Avenida");
        pj.setLogradouro("avenida");
        pj.setCidade("Jequié");
        pj.setEstado("BA");
        pj.setTelefone("7336665555");
        pj.setEmail("cafeAvenida@cafejequie.br");
        pjDAO.alterarPessoa(pj.getId(), pj);
        pjDAO.exibirPessoaJuridica(pj.getId());
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
        System.out.println("========Alterando PJ========");
        testeDeAlteracaoDaPessoaJuridica();
        System.out.println("========Buscando PJ========");
        buscaPessoaJuridica();
        System.out.println("========Exluindo PJ========");
        testeDeExclusaoDaPessoaJuridica();
        System.out.println("=========TESTES FINALIZADOS========");
    }
    
    public static void main(String[] args) throws SQLException {
         new CadastroDBTeste().run();
    }

}