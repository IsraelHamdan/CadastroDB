
package cadastrodb.model;


public class PessoaJuridica extends Pessoa {
    private String cnpj; 
    
    public PessoaJuridica(int id, String nome, String logradouro, String cidade, 
        String estado, String telefone, String email, String cnpj) {
        super(id, nome, logradouro, cidade, estado, telefone, email);
        this.cnpj = cnpj;
    }
    
    public PessoaJuridica() {
        
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    public String exibir() {
        super.exibir(); 
        return "CNPJ" + this.cnpj;
    }
}
