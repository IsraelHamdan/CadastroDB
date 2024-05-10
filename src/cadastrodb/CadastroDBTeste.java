
package cadastrodb;

import cadastrodb.model.PessoaFisica;
import cadastrodb.model.PessoaFisicaDAO;
import cadastrodb.model.util.ConectorDB;
import java.lang.System.Logger;
import java.util.Scanner;
import java.sql.SQLException;
import java.util.List;


public class CadastroDBTeste {
  
    public static Scanner sc = new Scanner(System.in);
    public static PessoaFisicaDAO pfDAO = new PessoaFisicaDAO();
    
    private static PessoaFisica pf; 
    
    public static PessoaFisica testeDeInclusao () {
        pf = new PessoaFisica(1, "fulana", "12345678900");
        System.out.println(pf.exibir());
        return pf;
    }
    
    
    public static PessoaFisica testeDeAlteracao() throws SQLException {
       pf.getId();
       pf.setNome("Beltrana"); 
       pf.setCpf("00000000000");       
       pfDAO.alterarPessoa(pf);
        System.out.println(pf.exibir());
       return pf; 
       
    }
    
    
    
    public static void main(String[] args) throws SQLException {
        testeDeInclusao(); 
        testeDeAlteracao();
    }

}
