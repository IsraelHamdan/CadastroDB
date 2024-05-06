
package cadastrodb.model;


public class PessoaJuridica extends Pessoa {
    private String cnpj; 
    
    public PessoaJuridica (String cnpj) {
        super(); 
        this.cnpj = cnpj;
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
