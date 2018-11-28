package Controller;

import Dao.FuncionariosDao;
import Model.FuncionariosModel;
import java.sql.SQLException;


public class FuncionariosControlle {

    public void chkForm(String nome, String login, String senha, String email, String tipo) throws SQLException {
        
        
        FuncionariosModel objModel = new FuncionariosModel();
                
        if(!"".equals(nome)) {
            objModel.setNomeFuncionario(nome.toUpperCase());
        } 
        
        if(!"".equals(login)) {
            objModel.setLogin(login.toUpperCase());
        } 
        
        if(!"".equals(senha)) {
           objModel.setSenha(senha.toUpperCase());
        }
        
        if(!"".equals(email)) {
           objModel.setEmail(email.toUpperCase());
        }
        
        if(!"Selecione".equals(tipo)) {
            objModel.setTipo(tipo.toUpperCase());
        }
        
        FuncionariosDao objDao = new FuncionariosDao();
        objDao.salvar(objModel);
        
    }
    
    public boolean vLogin(String login, String senha) throws SQLException {
        
        
        FuncionariosModel objModel = new FuncionariosModel();
        
        if(!"".equals(login)) {
            objModel.setLogin(login.toUpperCase());
        } 
        
        if(!"".equals(senha)) {
            objModel.setSenha(senha.toUpperCase());
        } 
        
        FuncionariosDao objDao = new FuncionariosDao();
        boolean returnValidate = objDao.vLogin(objModel);
        
        return returnValidate;
    
    }   
    
    public String tipoUser(String tipoUser) {
        FuncionariosModel objModel = new FuncionariosModel();
        objModel.setTipo(tipoUser);
        return tipoUser;
    }
}
