/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Banco.Banco;
import Model.VendasModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import lib.Functions;

/**
 *
 */
public class VendaDao {
    
    public ResultSet listar(String dataInicio, String dataFim) throws SQLException {
        
        Functions fn = new Functions();
        String dataHoje = fn.dataAtual();
    
        Connection con = new Banco().getConnection();
        
        String sql = "SELECT * FROM tb_vendas ";
        
        if(dataInicio != null && dataFim != null) {
            sql += "WHERE DAT_DATA_VENDA BETWEEN '"+dataInicio+"' AND '"+dataFim+"'";
        } else {
            sql += "WHERE DAT_DATA_VENDA = '"+dataHoje+"'";
        }
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        return rs;
    }
    
    public void salvar(VendasModel objModel) throws SQLException {
        
        int codProd = objModel.getIdProd();
        int qtdProd = objModel.getQtdProd();
        int vlrDesc = objModel.getVlrDesc();
        double total = objModel.getTotal();
        
        Functions fn = new Functions();
        String dataHoje = fn.dataAtual();
        
        Connection con = new Banco().getConnection();
        
        String sql = "INSERT INTO tb_vendas (INT_COD_PROD, INT_QTD_VENDA, INT_DESCONTO, INT_TOTAL, DAT_DATA_VENDA) VALUES (?,?,?,?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        
        stmt.setInt(1, codProd);
        stmt.setInt(2, qtdProd);
        stmt.setInt(3, vlrDesc);
        stmt.setDouble(4, total);
        stmt.setString(5, dataHoje);
        
        stmt.execute();
        stmt.close();
        
        JOptionPane.showMessageDialog(null,"Venda cadastrada com sucesso!!!!","Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
}
