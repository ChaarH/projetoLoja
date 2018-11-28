package Model;

/**
 *
 * @author CARLOS AIRES
 */
public class ContatoModel {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
    private String rg;
    private String cpf;
    private String sexo;
    
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getNome() {
        return this.nome;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getTelefone() {
        return this.telefone;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    public String getEndereco() {
        return this.endereco;
    }
    
    public void setRG(String rg) {
        this.rg = rg;
    }
    public String getRG() {
        return this.rg;
    }
    
    public void setCPF(String cpf) {
        this.cpf = cpf;
    }
    public String getCPF() {
        return this.cpf;
    }
    
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    public String getSexo() {
        return this.sexo;
    }
}
