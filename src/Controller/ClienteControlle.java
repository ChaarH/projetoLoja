
import Dao.ClienteDao;
import Model.ContatoModel;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import lib.ControleErros;

public class ClienteControlle {
    
    public void chkForm(String nome, String email, String telefone, String endereco, String rg, String cpf, String sexo) throws SQLException {
        
       ControleErros ctrlErro = new ControleErros();
        ContatoModel objModel = new ContatoModel();
        
        if(!"".equals(nome)) {
            objModel.setNome(nome.toUpperCase());
        } 
        
        if(!"".equals(email)) {
           objModel.setEmail(email.toUpperCase());
        }
        
        if(!"".equals(telefone)) {
            objModel.setTelefone(telefone.toUpperCase());
        }
        
        if(!"".equals(endereco)) {
            objModel.setEndereco(endereco.toUpperCase());
        } 
        
        if(!"".equals(rg)) {
            objModel.setRG(rg);
        }
        
        if(!"".equals(cpf)) {
            objModel.setCPF(cpf);
        }
        
        if("Selecione".equals(sexo)) {
            ctrlErro.setErro("errorSexo");
            
        } else{
            objModel.setSexo(sexo.toUpperCase());
        }
        
        ClienteDao objDao = new ClienteDao();
        objDao.salvar(objModel);
    }
    
    public void chkPesquisa(String pesquisa) {
        
        
        
        if("".equals(pesquisa)) {
            // Adicionar setError
            JOptionPane.showMessageDialog(null,"Favor adicionar algo a ser pesquisa","Atenção", JOptionPane.INFORMATION_MESSAGE);
        } else {
            
        }
    }
}
