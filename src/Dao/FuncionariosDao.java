package Dao;

import Banco.Banco;
import Controller.FuncionariosControlle;
import Model.FuncionariosModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class FuncionariosDao {
    
    public static String tipoUsuario;
    public static String nomeUsuario;
    
    public void salvar(FuncionariosModel objModel) throws SQLException {
        
        String nome,login,senha, email,tipo;
        
        nome = objModel.getNomeFuncionario();
        login = objModel.getLogin();
        senha = objModel.getSenha();
        email = objModel.getEmail();
        tipo = objModel.getTipo();
        
        Connection con = new Banco().getConnection();
        
        String sql =  "INSERT INTO tb_usuarios (TXT_NOME,TXT_LOGIN,TXT_SENHA, TXT_EMAIL, TXT_TIPO) VALUES (?,?,?,?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        
        stmt.setString(1, nome);
        stmt.setString(2, login);
        stmt.setString(3, senha);
        stmt.setString(4, email);
        stmt.setString(5, tipo);
        
        stmt.execute();
        stmt.close();
        
        JOptionPane.showMessageDialog(null,"Cadastro efetuado com sucesso!!!!","Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public boolean vLogin(FuncionariosModel objModel) throws SQLException {
        
        String login = objModel.getLogin();
        String senha = objModel.getSenha();
        int count = 0;
        
        Connection con = new Banco().getConnection();
        
        String sql = "SELECT * FROM tb_usuarios";
        Statement stmt = con.createStatement();
        ResultSet res = stmt.executeQuery(sql);
        
        while(res.next()) {
            if(login.equals(res.getString("TXT_LOGIN")) && senha.equals(res.getString("TXT_SENHA"))) {
                count++;
                
                tipoUsuario = res.getString("TXT_TIPO");
                nomeUsuario = res.getString("TXT_NOME");
            }
        }
        
        if(count > 0) {
            return true;
        } else {
            return false;
        }
    }
}
