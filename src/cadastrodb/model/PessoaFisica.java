
package cadastrodb.model;

public class PessoaFisica extends Pessoa {
    private String cpf; 

    
    public PessoaFisica (int id, String nome, String cpf) {
        super();
        setId(id); 
        
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
           super.exibir(); 
           return "cpf" + this.cpf;
     }
}
