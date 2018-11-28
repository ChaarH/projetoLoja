package Dao;

import Banco.Banco;
import Model.ContatoModel;
import Model.ProdutoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CARLOS AIRES
 */
public class ProdutoDao {
    
    public void salvar(ProdutoModel objModel) throws SQLException {
        Connection con = new Banco().getConnection();
        
        String sql = "INSERT INTO tb_produtos (TXT_NOME_PROD, TXT_TIPO, TXT_GENERO, TXT_TAMANHO, INT_PRECO, TXT_COR, TXT_MARCA, QTD_ESTQ, QTD_MIN_ESTQ) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        
        String nomeProd = objModel.getNomeProd();
        String tipo = objModel.getTipo();
        String genero = objModel.getGenero();
        String tamanho = objModel.getTamanho();
        int preco = objModel.getPreco();
        String cor = objModel.getCor();
        String marca = objModel.getMarca();
        int qtdEstq = objModel.getQtdEstq();
        int qtdMinEstq = objModel.getQtdMin();
        
        stmt.setString(1, nomeProd);
        stmt.setString(2, tipo);
        stmt.setString(3, genero);
        stmt.setString(4, tamanho);
        stmt.setInt(5, preco);
        stmt.setString(6, cor);        
        stmt.setString(7, marca);
        stmt.setInt(8, qtdEstq);
        stmt.setInt(9, qtdMinEstq);
        
        System.out.println(cor);
        
        stmt.execute();
        stmt.close();
        
        JOptionPane.showMessageDialog(null,"Produto cadastrado com sucesso!!!!","Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public ResultSet listar(int id) throws SQLException {

        Connection con = new Banco().getConnection();
        
        String sql = "SELECT * FROM tb_produtos WHERE FLG_STATUS = 'A'";
        
        if(id > 0) {
            sql += " AND ID_PRODUTO = '"+id+"'";
        }

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        return rs;
    }
    
    public void atualizar(ProdutoModel objModel) throws SQLException {
        int idProd = objModel.getIdProd();
        int preco = objModel.getPreco();
        int qtdEstq = objModel.getQtdEstq();
        int qtdMinEstq = objModel.getQtdMin();
        
        Connection con = new Banco().getConnection();
        
        String sql = "UPDATE tb_produtos SET INT_PRECO = (?), QTD_ESTQ = (?), QTD_MIN_ESTQ = (?) WHERE ID_PRODUTO = (?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        
        stmt.setInt(1, preco);
        stmt.setInt(2, qtdEstq);
        stmt.setInt(3, qtdMinEstq);
        stmt.setInt(4, idProd);
        
        stmt.execute();
        stmt.close();
        
        JOptionPane.showMessageDialog(null,"Alteração realizada com sucesso!!!!","Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public ResultSet listarQtd() throws SQLException {
        
        Connection con = new Banco().getConnection();
        
        String sql = "SELECT * FROM tb_produtos WHERE QTD_ESTQ <= QTD_MIN_ESTQ";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        return rs;
    }
    
    public void deletar(int idProd) throws SQLException {
        
        Connection con = new Banco().getConnection();
        
        String sql = "UPDATE tb_produtos SET FLG_STATUS = 'D' WHERE ID_PRODUTO = (?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        
        stmt.setInt(1, idProd);
        
        stmt.execute();
        stmt.close();
        
        JOptionPane.showMessageDialog(null,"Dado deletado com sucesso!!!!","Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public ResultSet pesquisar(int cod,String tipoPeca, String cor,String tamanho) throws SQLException {
        
        Connection con = new Banco().getConnection();
        
        String sql = "SELECT * FROM tb_produtos WHERE";
        
        if(!"".equals(cod)) {
            sql += " ID_PRODUTO LIKE '%"+cod+"%' OR";
        }
        
        if(!"".equals(tipoPeca)) {
            sql += " TXT_TIPO LIKE '%"+tipoPeca+"%' OR";
        }
        
        if(!"".equals(cor)) {
            sql += " TXT_COR LIKE '%"+cor+"%' OR";
        }
            
        if(!"".equals(tamanho))  {
            sql += " TXT_TAMANHO LIKE '%"+tamanho+"%";
        }
        
        
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        return rs;
    }
    
    public void atualizaEstq(int qtdInEstq, int qtdItens, int codProd) throws SQLException {
        
        int qtdAtualizada = (int) qtdInEstq - qtdItens;
        
        Connection con = new Banco().getConnection();
        
        String sql = "UPDATE tb_produtos SET QTD_ESTQ = (?) WHERE ID_PRODUTO = (?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        
        stmt.setInt(1, qtdAtualizada);
        stmt.setInt(2, codProd);
        
        stmt.execute();
        stmt.close();
    }
}
