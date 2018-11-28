package lib;

import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class ControleErros {
    
    public void setErro(String tipoErro) {
    
        switch(tipoErro) {
            
            // Erros form Cliente
            case "errorNome":
                JOptionPane.showMessageDialog(null,"Campo NOME não pode estar vazio!","Erro", JOptionPane.ERROR_MESSAGE);
                break;
            case "errorEmail":
                JOptionPane.showMessageDialog(null,"Campo EMAIL não pode estar vazio!","Erro", JOptionPane.ERROR_MESSAGE);
                break;
            case "errorTelefone":
                JOptionPane.showMessageDialog(null,"Campo TELEFONE não pode estar vazio!","Erro", JOptionPane.ERROR_MESSAGE);
                break;
            case "errorEndereco":
                JOptionPane.showMessageDialog(null,"Campo ENDEREÇO não pode estar vazio!","Erro", JOptionPane.ERROR_MESSAGE);
                break;
            case "errorRG":
                JOptionPane.showMessageDialog(null,"Campo RG não pode estar vazio!","Erro", JOptionPane.ERROR_MESSAGE);
                break;
            case "errorCPF":
                JOptionPane.showMessageDialog(null,"Campo CPF não pode estar vazio!","Erro", JOptionPane.ERROR_MESSAGE);
                break;
            case "errorSexo":
                JOptionPane.showMessageDialog(null,"Favor selecionar SEXO válido!","Erro", JOptionPane.ERROR_MESSAGE);
                break;
                
            // Erros form Produto
            case "errorNomeProd":
                JOptionPane.showMessageDialog(null,"Campo NOME PRODUTO não pode estar vazio!","Erro", JOptionPane.ERROR_MESSAGE);
                break;
            case "errorTipo":
                JOptionPane.showMessageDialog(null,"Campo TIPO não pode estar vazio!","Erro", JOptionPane.ERROR_MESSAGE);
                break;
            case "errorGenero":
                JOptionPane.showMessageDialog(null,"Campo GÊNERO não pode estar vazio!","Erro", JOptionPane.ERROR_MESSAGE);
                break;
            case "errorTamanho":
                JOptionPane.showMessageDialog(null,"Campo TAMANHO não pode estar vazio!","Erro", JOptionPane.ERROR_MESSAGE);
                break;
            case "errorPreco":
                JOptionPane.showMessageDialog(null,"Campo PREÇO não pode estar vazio!","Erro", JOptionPane.ERROR_MESSAGE);
                break;
            case "errorCor":
                JOptionPane.showMessageDialog(null,"Campo COR não pode estar vazio!","Erro", JOptionPane.ERROR_MESSAGE);
                break;
            case "errorMarca":
                JOptionPane.showMessageDialog(null,"Campo MARCA não pode estar vazio!","Erro", JOptionPane.ERROR_MESSAGE);
                break;
            case "errorQtdEstq":
                JOptionPane.showMessageDialog(null,"Campo QUANTIDADE ESTOQUE não pode estar vazio!","Erro", JOptionPane.ERROR_MESSAGE);
                break;
            case "errorQtdMin":
                JOptionPane.showMessageDialog(null,"Campo QUANTIDADE MÍNIMA não pode estar vazio!","Erro", JOptionPane.ERROR_MESSAGE);
                break;
                
                
            // Erros from Usuario
            case "errorTipoFuncionario":
                JOptionPane.showMessageDialog(null,"Campo FUNÇÃO deve ser selecionado!","Erro", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }
    
}