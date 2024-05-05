
package cadastrodb.model;

public class PessoaFisica extends Pessoa {
    private String cpf; 

    
    public PessoaFisica (String cpf, int id, String nome) {
        super();
        setId(id); 
        
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
