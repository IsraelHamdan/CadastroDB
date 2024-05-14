
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
    
    private final PessoaFisicaDAO pfDAO;
    private final PessoaJuridicaDAO pjDAO;
    private final SequenceManager seq;
    
    private final Scanner sc;
  
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
            case 2: 
                alterarPessoa();
                
        }
    }
    
    
    private char question(String v) {
        System.out.printf("Você deseja %s uma pessoa física ou jurídica? ", v);
        return sc.next().toUpperCase().charAt(0);
    }
    
    private int questionId(String t, String v) {
        System.out.printf("Qual o id da pessoa %s que você desja %s",t, v);
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
                
                sc.close();
                
                pf = new PessoaFisica(seqM, nome, logradouro, cidade, estado, telefone, email, cpf);
                pfDAO.incluiPessoa(pf);
                pf.exibir();
            }
            case 'J' -> {
                sequencia = "seq_pessoa";
                seqM = seq.getValue(sequencia); 
                
                sc.nextLine();
                
                System.out.println("Insira o nome");
                nome = sc.nextLine();
                
                System.out.println("Insira o logradouro");
                logradouro = sc.nextLine();
                
                System.out.println("Insira a cidade");
                cidade = sc.nextLine();
                
                System.out.println("Insira o estado (SOMENTE A SIGLA");
                estado = sc.nextLine();
                
                System.out.println("Insira o telefone (DDD9xxxxxxxx");
                telefone = sc.nextLine();
                
                System.out.println("Insira o email");
                email = sc.nextLine();
                
                System.out.println("Insira o CNPJ (xxxxxxxxxxx");
                cnpj = sc.nextLine();
                
                sc.close();
                
                pj = new PessoaJuridica (seqM, nome, logradouro, cidade, estado, telefone, email, cnpj);
                pj.exibir(); 
                pjDAO.incluiPessoaJuridica(pj);
            }
        }
    } 
    
    public void alterarPessoa() throws SQLException {
        char res = question("alterar");
        switch (res) {
            case 'F':
                pfDAO.exibirPessoasFisicas();
                System.out.println("Insira o id da pessoa que deseja alterar");
                int idPessoaFisica  = Integer.parseInt(sc.next());
                
                PessoaFisica pf = 
                     pfDAO.getPessoaFisica(idPessoaFisica); // Método para buscar a PessoaFisica pelo ID

                    System.out.println("Insira o novo nome");
                    nome = sc.next();
                    pf.setNome(nome);

                    System.out.println("Insira o novo logradouro");
                    logradouro = sc.next();
                    pf.setLogradouro(logradouro);

                    System.out.println("Insira a nova cidade cidade");
                    cidade = sc.next();
                    pf.setCidade(cidade);

                    System.out.println("Insira o novo estado (SOMENTE A SIGLA");
                    estado = sc.next();
                    pf.setEstado(estado);

                    System.out.println("Insira o novo telefone (DDD9xxxxxxxx");
                    telefone = sc.next();
                    pf.setTelefone(telefone);

                    System.out.println("Insira novo o email");
                    email = sc.next();
                    pf.setEmail(email);

                pfDAO.alterarPessoaFisica(idPessoaFisica, pf);

                break;
            case 'J':
                pjDAO.exibirPessoasJuridias();
                int idPJ = Integer.parseInt(sc.next());
                PessoaJuridica pj =  
                    pjDAO.getPessoaJuridica(idPJ); // Método para buscar a PessoaFisica pelo ID
                    
                    System.out.println("Insira o novo nome");
                    nome = sc.next();
                    pj.setNome(nome);

                    System.out.println("Insira o novo logradouro");
                    logradouro = sc.next();
                    pj.setLogradouro(logradouro);

                    System.out.println("Insira a nova cidade cidade");
                    cidade = sc.next();
                    pj.setCidade(cidade);

                    System.out.println("Insira o novo estado (SOMENTE A SIGLA");
                    estado = sc.next();
                    pj.setEstado(estado);

                    System.out.println("Insira o novo telefone (DDD9xxxxxxxx");
                    telefone = sc.next();
                    pj.setTelefone(telefone);

                    System.out.println("Insira novo o email");
                    email = sc.next();
                    pj.setEmail(email);
                pjDAO.alterarPessoaJuriica(idPJ, pj);
                break; 
        }
    }
    
}
