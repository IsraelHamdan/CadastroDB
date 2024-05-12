package cadastrodb;

import cadastrodb.model.PessoaFisica;
import cadastrodb.model.PessoaFisicaDAO;
import cadastrodb.model.PessoaJuridica;
import cadastrodb.model.PessoaJuridicaDAO;
import cadastrodb.model.util.SequenceManager;


import java.sql.SQLException;
import java.util.List;

public class CadastroDBTeste {
    
    private PessoaFisica pf; 
    private PessoaJuridica pj;

    private PessoaFisicaDAO pfDAO;
    private PessoaJuridicaDAO pjDAO;
    private SequenceManager seq;

    public CadastroDBTeste() {
        pfDAO = new PessoaFisicaDAO(); 
        seq = new SequenceManager();
        pjDAO = new PessoaJuridicaDAO();
    }
   
    private  PessoaFisica testeDeInclusao () throws SQLException{
        String sequencia = "seq_pessoa";
        int seqM = seq.getValue(sequencia);
        
        pf = new PessoaFisica(seqM, "Israel", "rua", "Jequié", "BA", "12345678900", "Cleyton@gmail.com", "48217469521");
        pfDAO.incluiPessoa(pf);
        System.out.println(pf.exibir());

        return pf;
    }
    
    private PessoaJuridica testeDeInclusaoDaPessoaJuridica() throws SQLException {
        String sequencia = "seq_pessoa";
        int seqM = seq.getValue(sequencia);
        
        pj = new PessoaJuridica(seqM, "ZPT", "complexo", "Belém", "PA", "91983662660", "ZPT@zptEmplements.com", "45814735812");
        pjDAO.incluiPessoaJuridica(pj);
        System.out.println(pj.exibir());
        return pj;
    }
    
    private List<PessoaFisica> buscaPessoaFisica() throws SQLException {
        for(PessoaFisica pf: pfDAO.getPessoas()) {
            System.out.println(pf.exibir());
        }
        return pfDAO.getPessoas();
        
    }
    
    private List<PessoaJuridica> buscaPessoaJuridica()throws SQLException {
        for(PessoaJuridica pj : pjDAO.getPessoasJuridica()) {
            System.out.println(pj.exibir());
        }
        return pjDAO.getPessoasJuridica();
    }
    
    private  PessoaFisica testeDeAlteracao() throws SQLException {
       pf.setNome("Rafaela"); 
       pf.setCpf("65845297215");       
       pfDAO.alterarPessoa(pf);
       System.out.println(pf.exibir());
       return pf; 
       
    }
    
    private PessoaJuridica testeDeAlteracaoDaPessoaJuridica() throws SQLException {
        pj.setNome("Xpto");
        pj.setCnpj("7845635150");
        pjDAO.alterarPessoaJuridica(pj);
        System.out.println(pj.exibir());
        return pj;
    }
    
    private void testeDeExclusao() throws SQLException {
        pfDAO.excluirPessoa(pf.getId());
    }
    
    private void testeDeExclusaoDaPessoaJuridica() throws SQLException {
        pjDAO.excluirPessoaJuridica(pj.getId());
    }
    
    public void run( ) throws SQLException {
        testeDeInclusao(); 
        testeDeAlteracao();
        testeDeExclusao();
        buscaPessoaFisica();
        
        testeDeInclusaoDaPessoaJuridica();
        testeDeAlteracaoDaPessoaJuridica();
        testeDeExclusaoDaPessoaJuridica();
        buscaPessoaJuridica();
    }
    
    public static void main(String[] args) throws SQLException {
         new CadastroDBTeste().run();
    }

}
