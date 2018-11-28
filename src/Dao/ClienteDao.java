package Dao;

import Banco.Banco;
import Model.ContatoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author CARLOS AIRES
 */
public class ClienteDao {
    
    public void salvar(ContatoModel objModel) throws SQLException {
        
        String nome, email, telefone, endereco, rg, cpf, sexo;
        
        nome = objModel.getNome();
        email = objModel.getEmail();
        telefone = objModel.getTelefone();
        endereco = objModel.getEndereco();
        rg = objModel.getRG();
        cpf = objModel.getCPF();
        sexo = objModel.getSexo();
        
        Connection con = new Banco().getConnection();

        String sql = "INSERT INTO tb_clientes (TXT_NOME, TXT_ENDERECO, TXT_TELEFONE, TXT_EMAIL, TXT_SEXO, TXT_RG, TXT_CPF) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        
        stmt.setString(1, nome);
        stmt.setString(2, endereco);
        stmt.setString(3, telefone);
        stmt.setString(4, email);
        stmt.setString(5, sexo);
        stmt.setString(6, rg);
        stmt.setString(7, cpf);
        
        stmt.execute();
        stmt.close();
        
        JOptionPane.showMessageDialog(null,"Cadastro efetuado com sucesso!!!!","Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
    
   public void atualizar(ContatoModel objModel) throws SQLException {
        int id = objModel.getId();
        String nome = objModel.getNome();
        String email = objModel.getEmail();
        String endereco = objModel.getEndereco();
        String telefone = objModel.getTelefone();
        
        Connection con = new Banco().getConnection();
        
        String sql = "UPDATE tb_clientes SET TXT_NOME = (?),TXT_ENDERECO = (?),TXT_TELEFONE = (?),TXT_EMAIL = (?) WHERE ID_CLIENTE = (?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        
        stmt.setString(1, nome.toUpperCase());
        stmt.setString(2, endereco.toUpperCase());
        stmt.setString(3, telefone.toUpperCase());
        stmt.setString(4, email.toUpperCase());
        stmt.setInt(5, id);
        
        stmt.execute();
        stmt.close();
        
        JOptionPane.showMessageDialog(null,"Alteração realizada com sucesso!!!!","Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void deletar(int idCliente) throws SQLException {
        
        Connection con = new Banco().getConnection();
        
        String sql = "UPDATE tb_clientes SET FLG_STATUS = 'D' WHERE ID_CLIENTE = (?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        
        stmt.setInt(1, idCliente);
        
        stmt.execute();
        stmt.close();
        
        JOptionPane.showMessageDialog(null,"Dado deletado com sucesso!!!!","Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public ResultSet listar(int id) throws SQLException {
        
        Connection con = new Banco().getConnection();
        
        String sql = "SELECT * FROM tb_clientes WHERE FLG_STATUS = 'A'";
        
        if(id > 0) {
            sql += " AND ID_CLIENTE = " +id;
        }
        
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        return rs;
    }
    
    public ResultSet pesquisar(String pesquisa) throws SQLException {
        
        Connection con = new Banco().getConnection();
        
        String sql = "SELECT * FROM tb_clientes WHERE ID_CLIENTE LIKE '%"+pesquisa+"%' OR TXT_NOME LIKE '%"+pesquisa+"%' OR TXT_RG LIKE '%"+pesquisa+"%'";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        return rs;
    }
    
}
