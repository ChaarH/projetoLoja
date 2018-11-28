/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.ProdutoDao;
import Model.ProdutoModel;
import java.sql.SQLException;
import lib.ControleErros;

/**
 *
 * @author Carlos
 */
public class ProdutoControlle {
    
    public void chkForm(String nomeProd, String tipo, String genero, String tamanho, int preco, String cor, String marca, int qtdEstq, int qtdMin) throws SQLException {
        
        ControleErros ctrlErro = new ControleErros();
        ProdutoModel objModel = new ProdutoModel();
        
        if("".equals(nomeProd)) {
            ctrlErro.setErro("errorNomeProd");
        } else {
            objModel.setNomeProd(nomeProd.toUpperCase());
        }
        
        if("".equals(tipo)) {
            ctrlErro.setErro("errorTipo");
        } else {
            objModel.setTipo(tipo.toUpperCase());
        }
        
        if("".equals(genero)) {
            ctrlErro.setErro("errorGenero");
        } else {
            objModel.setGenero(genero.toUpperCase());
        }
        
        if("".equals(tamanho)) {
            ctrlErro.setErro("errorTamanho");
        } else {
            objModel.setTamanho(tamanho.toUpperCase());
        }
        
        if(preco == 0) {
            ctrlErro.setErro("errorPreco");
        } else {
            objModel.setPreco(preco);
        }
        
        if("".equals(cor)) {
            ctrlErro.setErro("errorCor");
        } else {
            objModel.setCor(cor.toUpperCase());
        }
        
        if("".equals(marca)) {
            ctrlErro.setErro("errorMarca");
        } else {
            objModel.setMarca(marca.toUpperCase());
        }
        
        if(qtdEstq == 0) {
            ctrlErro.setErro("errorQtdEstq");
        } else {
            objModel.setQtdEstq(qtdEstq);
        }
        
        if(qtdMin == 0) {
            ctrlErro.setErro("errorQtdMin");
        } else {
            objModel.setQtdMin(qtdMin);
        }
        
        ProdutoDao objDao = new ProdutoDao();
        objDao.salvar(objModel);
    }
    
    public int geraDesconto(int valorCompra,int valorDesc) {
        int desc = (valorCompra * valorDesc) / 100;
        return valorCompra - desc;
    }
    
}
