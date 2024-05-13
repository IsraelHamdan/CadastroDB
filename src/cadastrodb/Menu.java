
package cadastrodb;

import cadastrodb.model.PessoaFisica;
import cadastrodb.model.PessoaFisicaDAO;
import cadastrodb.model.PessoaJuridica;
import cadastrodb.model.PessoaJuridicaDAO;
import cadastrodb.model.util.SequenceManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Menu {
    private PessoaFisica pf; 
    private PessoaJuridica pj;
    
    private PessoaFisicaDAO pfDAO;
    private PessoaJuridicaDAO pjDAO;
    private SequenceManager seq;
    
    private Scanner sc;
    
    private int id;
    private String nome;
    private String logradouro; 
    private String cidade;
    private String estado;
    private String telefone;
    private String email;
    private String cpf;
    private String cnpj;
    
    public Menu () {
        pf = new PessoaFisica();
        pfDAO = new PessoaFisicaDAO();
        pj = new PessoaJuridica(); 
        pjDAO = new PessoaJuridicaDAO();
        sc = new Scanner(System.in);
        seq = new SequenceManager();
    }
    
    public void menu() throws SQLException {
        System.out.println("===============================");
        System.out.println("""
                           1 - inserir
                           2 - alterar
                           3 - excluir 
                           4 - buscar pelo id
                           5 - buscar todos 
                           0 - encerrar programa
                           """);
        int option = Integer.parseInt(sc.nextLine());
        switch (option) {
            case 1: 
                inserirPessoa();
                break;
        }
    }
    
    private char question(String v) {
        System.out.printf("Você deseja %s uma pessoa física ou jurídica? ", v);
        return sc.next().toUpperCase().charAt(0);
    }
    
    private int questionId(String v) {
        System.out.printf("Qual o id da pessoa %s que você desja alterar", v);
        return Integer.parseInt(sc.nextLine());
    }
    
    public void inserirPessoa() throws SQLException {
        String sequencia; 
        int seqM; 
        char res = question("inserir"); 
        switch (res) {
            case 'F' -> {
                sequencia = "seq_pessoa";
                seqM = seq.getValue(sequencia); 
                
                System.out.println("Insira o nome");
                nome = sc.next();
                
                System.out.println("Insira o logradouro");
                logradouro = sc.next();
                
                System.out.println("Insira a cidade");
                cidade = sc.next();
                
                System.out.println("Insira o estado (SOMENTE A SIGLA");
                estado = sc.next();
                
                System.out.println("Insira o telefone (DDD9xxxxxxxx");
                telefone = sc.next();
                
                System.out.println("Insira o email");
                email = sc.next();
                
                System.out.println("Insira o cpf");
                cpf = sc.next();
                
                pf = new PessoaFisica(seqM, nome, logradouro, cidade, estado, telefone, email, cpf);
                pfDAO.incluiPessoa(pf);
                pf.exibir();
            }
            case 'J' -> {
                sequencia = "seq_pessoa";
                seqM = seq.getValue(sequencia); 
                
                System.out.println("Insira o nome");
                nome = sc.next();
                
                System.out.println("Insira o logradouro");
                logradouro = sc.next();
                
                System.out.println("Insira a cidade");
                cidade = sc.next();
                
                System.out.println("Insira o estado (SOMENTE A SIGLA");
                estado = sc.next();
                
                System.out.println("Insira o telefone (DDD9xxxxxxxx");
                telefone = sc.next();
                
                System.out.println("Insira o email");
                email = sc.next();
                
                System.out.println("Insira o CNPJ (xxxxxxxxxxx");
                cnpj = sc.next();
                
                pj = new PessoaJuridica (seqM, nome, logradouro, cidade, estado, telefone, email, cnpj);
                pj.exibir(); 
                pjDAO.incluiPessoaJuridica(pj);
            }
        }
    } 
    
    
}
