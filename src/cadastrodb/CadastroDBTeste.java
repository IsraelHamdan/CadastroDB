
package cadastrodb;

import cadastrodb.model.PessoaFisica;
import cadastrodb.model.PessoaFisicaDAO;
import cadastrodb.model.util.SequenceManager;

import java.sql.Connection;
import java.util.Scanner;
import java.sql.SQLException;

public class CadastroDBTeste {
  
    private  Scanner sc;
   
    private PessoaFisica pf; 

    private  PessoaFisicaDAO pfDAO;
    private SequenceManager seq;

    public CadastroDBTeste() {
        pfDAO = new PessoaFisicaDAO(); 
        sc = new Scanner(System.in);
        seq = new SequenceManager();
    }
   
    private  PessoaFisica testeDeInclusao () throws SQLException{;
        String sequencia = "seq_pessoa";
        int seqM = seq.getValue(sequencia);
        pf = new PessoaFisica(seqM, "Cleyton", "apto", "Belém", "PA", "12345678900", "Cleyton@gmail.com", "36248212547");
        pfDAO.incluiPessoa(pf);
        System.out.println(pf.exibir());
        return pf;
      
    }

    private  PessoaFisica testeDeAlteracao() throws SQLException {
       pf.setNome("Yasmin"); 
       pf.setCpf("78956214710");       
       pfDAO.alterarPessoa(pf);
       System.out.println(pf.exibir());
       return pf; 
       
    }
    
    private void testeDeExclusao() throws SQLException {
        pfDAO.excluirPessoa(pf.getId());
    }
    
    public void run( ) throws SQLException {
        testeDeInclusao(); 
        testeDeAlteracao();
        testeDeExclusao();
    }
    
    public static void main(String[] args) throws SQLException {
         new CadastroDBTeste().run();
    }

}
