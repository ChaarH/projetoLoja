/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author palomalopes
 */
public class VendasModel {
    
    private int idVenda;
    private int idProd;
    private int qtdProd;
    private int vlrDesc;
    private double total;
    
    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }
    public int getIdVenda() {
        return this.idVenda;
    }
    
    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }
    public int getIdProd() {
        return this.idProd;
    }
    
    public void setQtdProd(int qtdProd) {
        this.qtdProd = qtdProd;
    }
    public int getQtdProd() {
        return this.qtdProd;
    }
    
    public void setVlrDesc(int vlrDesc) {
        this.vlrDesc = vlrDesc;
    }
    public int getVlrDesc() {
        return this.vlrDesc;
    }
    
    public void setTotal(double total) {
        this.total = total;
    }
    public double getTotal() {
        return this.total;
    }
    
}
