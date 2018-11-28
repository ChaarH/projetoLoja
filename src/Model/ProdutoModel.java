package Model;

/**
 *
 * @author CARLOS AIRES
 */
public class ProdutoModel {
    
    private int idProd;
    private String nomeProd;
    private String tipo;
    private String genero;
    private String tamanho;
    private int preco;
    private String cor;
    private String marca;
    private int qtdEstq;
    private int qtdMin;
    
    public void setId(int idProd) {
        this.idProd = idProd;
    }
    public int getIdProd() {
        return this.idProd;
    }
    
    public void setNomeProd(String nomeProduto ) {
        this.nomeProd = nomeProduto;
    }
    public String getNomeProd() {
        return this.nomeProd;
    }
    
    public void setTipo(String tipo ) {
        this.tipo = tipo;
    }
    public String getTipo() {
        return this.tipo;
    }
    
    public void setGenero(String genero ) {
        this.genero = genero;
    }
    public String getGenero() {
        return this.genero;
    }
    
    public void setTamanho(String tamanho ) {
        this.tamanho = tamanho;
    }
    public String getTamanho() {
        return this.tamanho;
    }
    
    public void setPreco(int preco ) {
        this.preco = preco;
    }
    public int getPreco() {
        return this.preco;
    }
    
    public void setCor(String cor ) {
        this.cor = cor;
    }
    public String getCor() {
        return this.cor;
    }
    
    public void setMarca(String marca ) {
        this.marca = marca;
    }
    public String getMarca() {
        return this.marca;
    }
    
    public void setQtdEstq(int qtdEstq ) {
        this.qtdEstq = qtdEstq;
    }
    public int getQtdEstq() {
        return this.qtdEstq;
    }
    
    public void setQtdMin(int qtdMin ) {
        this.qtdMin = qtdMin;
    }
    public int getQtdMin() {
        return this.qtdMin;
    }
}
