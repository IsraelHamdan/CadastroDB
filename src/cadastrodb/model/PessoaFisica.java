
package cadastrodb.model;

public class PessoaFisica extends Pessoa {
    private String cpf; 
    
    private Pessoa pessoa;

    public PessoaFisica(int id, String nome, String logradouro, String cidade, 
        String estado, String telefone, String email, String cpf) {
        super(id, nome, logradouro, cidade, estado, telefone, email);
        this.cpf = cpf;
        pessoa = new Pessoa();
    }
    

    public PessoaFisica() {
    }
    
    
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String exibir () {
           return pessoa.exibir() + "cpf" + this.cpf;
    }
     
}
