
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
            case 4: 
                buscarPeloId();
                break;
            case 5: 
               buscarTodos();
               break;
        }
    }
    
    private char question(String v) {
        System.out.printf("Você deseja %s uma pessoa física ou jurídica? ", v);
        return sc.next().toUpperCase().charAt(0);
    }
    
    private int questionId(String tipo, String verbo) {
        System.out.printf("Qual o id da pessoa %s que você desja %s",tipo, verbo);
        int id = sc.nextInt();
        return id;
    }
    
    private String errorMensage(String verbo, String tipo) {
        String mensage = "Erro ao %s uma pessoa %t" + verbo + tipo;
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
                break;
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
                break;
            }
        }
    } 
    
    public void alterarPessoa() throws SQLException {
        char res = question("alterar");
        switch (res) {
            case 'F' -> {
                pfDAO.exibirPessoasFisicas();
                int idPessoaFisica  = questionId("física", "alterar ");
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
                pfDAO.exibirPessoaFisica(idPessoaFisica);
                sc.close();
            }
            case 'J' -> {
                pjDAO.exibirPessoasJuridicas();
                
                int idPJ  = questionId("física", "alterar ");
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
                pjDAO.exibirPessoaJuridica(idPJ);
                sc.close();
            } 
        }
    }
    
    public void excluirPessoa() throws SQLException {
        char res = question("excluir");
        int idPessoaFisica = 0;
        int idPJ = 0;
        switch (res) {
            case 'F' -> { 
                try {
                    pfDAO.exibirPessoasFisicas();
                    idPessoaFisica= questionId("física", "excluir");

                    pfDAO.excluirPessoaFisica(idPessoaFisica);
                    
                } catch (SQLException e) {
                    String erro = errorMensage("excluir", "física");
                    LOGGER.log(Level.SEVERE, erro + " com id: " + idPessoaFisica, e);
                }
                break;
            }
              
            case 'J' -> { 
                try {
                    pjDAO.exibirPessoasJuridicas();
                    idPJ= questionId("jurídica", "excluir");
                    pjDAO.excluirPessoaJuridica(idPJ);
                } catch (SQLException e) {
                    String erro = errorMensage("excluir", "juridica");
                    LOGGER.log(Level.SEVERE, erro + " com o id: " + idPJ, e);
                } 
                break;
            }        
        }
    }
    
    public void buscarPeloId() throws  SQLException {
        char res = question("buscar");
        int idPessoaFisica = 0;
        int idPJ = 0;
        switch (res) {
            case 'F' -> {
                try {
                    idPessoaFisica = questionId("física", "buscar");
                    pfDAO.exibirPessoaFisica(idPessoaFisica);
                    
                } catch (SQLException e) {
                    String erro = errorMensage("buscar", "física");
                    LOGGER.log(Level.SEVERE, erro + " com o id: " + idPessoaFisica, e);
                }
            }
            case 'J' -> { 
                try {
                    idPJ = questionId("juridica", "buscar");
                    pjDAO.exibirPessoaJuridica(idPJ);
                    
                } catch (SQLException e) {
                    String erro = errorMensage("buscar", "jurídica");
                    LOGGER.log(Level.SEVERE, erro + " com o id: " + idPJ, e);
                }
            }
        }   
    }
    
    public void buscarTodos() throws SQLException {
        System.out.println("Você quer buscar todas as pessoas físias ou jurídicas? ");
        String res = sc.nextLine();
        try{
            if(res.equalsIgnoreCase("F")) {
                pfDAO.exibirPessoasFisicas();
            } else if (res.equalsIgnoreCase("J")) {
                pjDAO.exibirPessoasJuridicas();
            } else {
                System.out.println("Escolha um tipo valido");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar todas as pessoas físicas do banco", e);
        }
    }
    
}
