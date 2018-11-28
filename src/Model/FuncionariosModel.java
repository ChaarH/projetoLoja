package Model;

public class FuncionariosModel {

    private int idFun;
    private String nomeFuncionario;
    private String login;
    private String senha;
    private String email;
    private String tipo;
    
    public void setIdFun(int idFun) {
        this.idFun = idFun;
    }
    public int getIdFun() {
        return this.idFun;
    }
    
    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }
    public String getNomeFuncionario() {
        return this.nomeFuncionario;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    public String getLogin() {
        return this.login;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getSenha() {
        return this.senha;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getTipo() {
        return this.tipo;
    }
}
