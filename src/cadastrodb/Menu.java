
package cadastrodb;

import cadastrodb.model.PessoaFisica;
import cadastrodb.model.PessoaFisicaDAO;
import cadastrodb.model.PessoaJuridica;
import cadastrodb.model.PessoaJuridicaDAO;
import cadastrodb.model.util.SequenceManager;
import java.util.logging.Logger;
import java.util.logging.Level;
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
    
    private static final Logger LOGGER = Logger.getLogger(Menu.class.getName());
    
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
                break;
            case 3: 
                excluirPessoa();
                break;
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
    
    private String errorMensage(String v, String t) {
        String mensage = "Erro ao %s uma pessoa %t" + v + t;
        return mensage;
    }
    
    public void inserirPessoa() throws SQLException {
        String sequencia; 
        int seqM; 
        char res = question("inserir"); 
        switch (res) {
            case 'F' -> {
                try {
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

                    System.out.println("Insira o cpf");
                    cpf = sc.nextLine();

                    sc.close();

                    pf = new PessoaFisica(seqM, nome, logradouro, cidade, estado, telefone, email, cpf);
                    pfDAO.incluiPessoa(pf);
                    pf.exibir();
                } catch (SQLException e) {
                    String erro = errorMensage("incluir", "juridica");
                    LOGGER.log(Level.SEVERE, erro, e);
                }

            }
            case 'J' -> {
                try {
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
                } catch (SQLException e) {
                    String erro = errorMensage("incluir", "jurídica");
                    LOGGER.log(Level.SEVERE, erro, e);
                }

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
                    pfDAO.getPessoaFisica(idPessoaFisica); 
                
                    sc.nextLine();
                    
                    System.out.println("Insira o novo nome");
                    nome = sc.nextLine();
                    pf.setNome(nome);

                    System.out.println("Insira o novo logradouro");
                    logradouro = sc.nextLine();
                    pf.setLogradouro(logradouro);

                    System.out.println("Insira a nova cidade cidade");
                    cidade = sc.nextLine();
                    pf.setCidade(cidade);

                    System.out.println("Insira o novo estado (SOMENTE A SIGLA");
                    estado = sc.nextLine();
                    pf.setEstado(estado);

                    System.out.println("Insira o novo telefone (DDD9xxxxxxxx");
                    telefone = sc.nextLine();
                    pf.setTelefone(telefone);

                    System.out.println("Insira novo o email");
                    email = sc.nextLine();
                    pf.setEmail(email);
                    
                pfDAO.alterarPessoaFisica(idPessoaFisica, pf);
                sc.close();
                break;
            case 'J':
                pjDAO.exibirPessoasJuridicas();
                System.out.println("Insira o id da pessoa juridica que você quer alterar");
                int idPJ = Integer.parseInt(sc.next());
                PessoaJuridica pj =  
                    pjDAO.getPessoaJuridica(idPJ); 
                
                    sc.nextLine();
                    
                    System.out.println("Insira o novo nome");
                    nome = sc.nextLine();
                    pj.setNome(nome);

                    System.out.println("Insira o novo logradouro");
                    logradouro = sc.nextLine();
                    pj.setLogradouro(logradouro);

                    System.out.println("Insira a nova cidade cidade");
                    cidade = sc.nextLine();
                    pj.setCidade(cidade);

                    System.out.println("Insira o novo estado (SOMENTE A SIGLA");
                    estado = sc.nextLine();
                    pj.setEstado(estado);

                    System.out.println("Insira o novo telefone (DDD9xxxxxxxx");
                    telefone = sc.nextLine();
                    pj.setTelefone(telefone);

                    System.out.println("Insira novo o email");
                    email = sc.nextLine();
                    pj.setEmail(email);
                pjDAO.alterarPessoaJurdica(idPJ, pj);
                sc.close();
                break; 
        }
    }
    
    public void excluirPessoa() throws SQLException {
        char res = question("excluir");
        int idPessoaFisica = 0;
        int idPJ = 0;
        switch (res) {
            case 'F': 
                try {
                    idPessoaFisica= questionId("física", "excluir");
                    pfDAO.exibirPessoasFisicas();

                    pfDAO.excluirPessoa(idPessoaFisica);
                    pfDAO.exibirPessoasFisicas();
                } catch (SQLException e) {
                    String erro = errorMensage("excluir", "física");
                    LOGGER.log(Level.SEVERE, erro + " com id: " + idPessoaFisica, e);
                }
              
            case 'J': 
                try {
                    idPJ= questionId("jurídica", "excluir");
                    pjDAO.exibirPessoasJuridicas();

                    pjDAO.excluirPessoaJuridica(idPJ);
                    pfDAO.exibirPessoasFisicas();
                } catch (SQLException e) {
                    String erro = errorMensage("excluir", "juridica");
                    LOGGER.log(Level.SEVERE, erro + " com o id: " + idPJ, e);
                }
                        
        }
    }
    
}
