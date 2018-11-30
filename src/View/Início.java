
import Dao.ClienteDao;
import Dao.FuncionariosDao;
import Dao.ProdutoDao;
import Dao.VendaDao;
import Model.ContatoModel;
import Model.FuncionariosModel;
import View.EditarCliente;
import View.EditarProduto;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import lib.Functions;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniela
 */
public class Início extends javax.swing.JFrame {
    
    NovaVenda NovaVenda; 
    NovoCliente NovoCliente;
    NovoProduto NovoProduto;
    NovoUsuario NovoUsuario;
    Troca Troca;
    Estoque Estoque; 
    
    EditarCliente EditarCliente;
    EditarProduto EditarProduto;
    
    Functions fn = new Functions();
   
    public Início() throws SQLException {
        initComponents();
        
            
        this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int resposta = JOptionPane.showConfirmDialog(null, "Deseja deslogar do sistema?","Sair", JOptionPane.YES_NO_OPTION);
                if(resposta == JOptionPane.YES_OPTION){
                dispose();
                
                Login log = new Login();
                log.setVisible(true);
                }
            }
        });
        
        NovaVenda = new NovaVenda();
        NovoCliente = new NovoCliente();
        NovoProduto = new NovoProduto();
        NovoUsuario = new NovoUsuario();
        Troca = new Troca();
        Estoque = new Estoque(); 
        
        EditarCliente = new EditarCliente();
        EditarProduto = new EditarProduto();
        
        jBtnEditarSelecionado.setEnabled(false);
        jBtbDeletarCliente.setEnabled(false);
        jBtnEditarSelecionadoProd.setEnabled(false);
        jBtnDeletarProd.setEnabled(false);
        
        FuncionariosDao objDaoUser = new FuncionariosDao();
        String tipoUsuario = objDaoUser.tipoUsuario;
        String nomeUsuario = objDaoUser.nomeUsuario;
        
        JOptionPane.showMessageDialog(null,"Olá "+nomeUsuario+", seja bem-vindo(a) ao sistema!","Boas vindas!", JOptionPane.INFORMATION_MESSAGE);
        
        // CONTROLE DE HIERARQUIA
        if("VENDEDOR".equals(tipoUsuario)) {
            jTabbedPane1.removeTabAt(1);
        }
        
        if("ESTOQUISTA".equals(tipoUsuario)) {
            jTabbedPane1.removeTabAt(1);
            jTabbedPane1.removeTabAt(2);
        }
        
        tabelaClientes();
        tabelaProdutos();
        tabelaVendas();
        tabelaProdEstqMin();
        tabelaUsuarios();
        
    }
    
    public void tabelaProdutos() throws SQLException {
        DefaultTableModel tabelaProdutos = (DefaultTableModel) jTableResultProdutos.getModel();
        tabelaProdutos.setNumRows(0);
        
        ProdutoDao objDaoProd = new ProdutoDao();
        ResultSet rsProd = objDaoProd.listar(0);
        
        while(rsProd.next()) {
            int cod = rsProd.getInt("ID_PRODUTO");
            String nome = rsProd.getString("TXT_NOME_PROD");
            String tamanho = rsProd.getString("TXT_TAMANHO");
            String cor = rsProd.getString("TXT_COR");
            double preco = rsProd.getInt("INT_PRECO");
            String tipo = rsProd.getString("TXT_TIPO");
            String genero = rsProd.getString("TXT_GENERO");
            String marca = rsProd.getString("TXT_MARCA");
            int qtdEstq = rsProd.getInt("QTD_ESTQ");
            int qtdEstqMin = rsProd.getInt("QTD_MIN_ESTQ");
            tabelaProdutos.addRow(new Object[]{cod,nome,tamanho,cor,preco,tipo,genero,marca,qtdEstq,qtdEstqMin});
        }
    }
    
    public void tabelaClientes() throws SQLException {
        DefaultTableModel tabelaClientes = (DefaultTableModel) jTableResultClientes.getModel();
        tabelaClientes.setNumRows(0);
        
        ClienteDao objDao = new ClienteDao();
        ResultSet rs = objDao.listar(0);
        
        while(rs.next()) {
            int cod = rs.getInt("ID_CLIENTE");
            String nome = rs.getString("TXT_NOME");
            String endereco = rs.getString("TXT_ENDERECO");
            String telefone = rs.getString("TXT_TELEFONE");
            String email = rs.getString("TXT_EMAIL");
            String sexo = rs.getString("TXT_SEXO");
            String rg = rs.getString("TXT_RG");
            String cpf = rs.getString("TXT_CPF");
            tabelaClientes.addRow(new Object[]{cod,nome,endereco,telefone,email,sexo,rg,cpf});
        }
    }

    public void tabelaProdEstqMin() throws SQLException {  
        DefaultTableModel tabelaEstqProd = (DefaultTableModel) jTableCtrlQtd.getModel();
        tabelaEstqProd.setNumRows(0);

        ProdutoDao objQtdEstq = new ProdutoDao();
        ResultSet rsQtdEstq = objQtdEstq.listarQtd();
        
        while(rsQtdEstq.next()) {
            int cod = rsQtdEstq.getInt("ID_PRODUTO");
            String nome = fn.ucFirst(rsQtdEstq.getString("TXT_NOME_PROD"));
            int qtdEstq = rsQtdEstq.getInt("QTD_ESTQ");
            int qtdEstqMin = rsQtdEstq.getInt("QTD_MIN_ESTQ");
            tabelaEstqProd.addRow(new Object[]{cod,nome,qtdEstq,qtdEstqMin});
        }
        if(tabelaEstqProd.getRowCount() == 0) {
            String msg = "Não há dados a serem exibidos";
            tabelaEstqProd.addRow(new Object[]{msg,msg,msg,msg});
        }
    }
    
    public void tabelaVendas() throws SQLException {
        Functions fn = new Functions();
        String dataHoje = fn.dataAtual();
        jLabelDataHoje.setText(dataHoje);
        
        DefaultTableModel tabelaVendas = (DefaultTableModel) jTableVendas.getModel();
        tabelaVendas.setNumRows(0);
        
        VendaDao objVendasDao = new VendaDao();
        ResultSet rsVendas = objVendasDao.listar(null,null);
        
        int labelTotal = 0;
        
        while(rsVendas.next()) {
            int codProd = rsVendas.getInt("INT_COD_PROD");
            String nomeVendedor = rsVendas.getString("TXT_VENDEDOR");
            int qtdProd = rsVendas.getInt("INT_QTD_VENDA");
            int vlrDesc = rsVendas.getInt("INT_DESCONTO");
            int total = rsVendas.getInt("INT_TOTAL");
            String dataVenda = rsVendas.getString("DAT_DATA_VENDA");
            tabelaVendas.addRow(new Object[] {codProd,nomeVendedor,qtdProd,vlrDesc,total,dataVenda});
            
            labelTotal += total;
        }
        
        jLabelTotal.setText(NumberFormat.getCurrencyInstance().format(labelTotal));
        
        if(tabelaVendas.getRowCount() == 0 ){
           String msg = "Não há dados a serem exibidos";
           tabelaVendas.addRow(new Object[]{msg,msg,msg,msg});
        }
    }
    
    public void tabelaUsuarios() throws SQLException {
        DefaultTableModel tabelaUsuario = (DefaultTableModel) jTableResultUsuarios.getModel();
        tabelaUsuario.setNumRows(0);
        
        FuncionariosDao objDao = new FuncionariosDao();
        ResultSet rs = objDao.listar(0);
        
        while(rs.next()) {
            int cod = rs.getInt("ID_USUARIO");
            String nome = rs.getString("TXT_NOME");
            String login = rs.getString("TXT_LOGIN");
            String senha = rs.getString("TXT_SENHA");
            String tipo = rs.getString("TXT_TIPO");
            String status = rs.getString("TXT_STATUS");
            tabelaUsuario.addRow(new Object[]{cod,nome,login,senha,tipo,status});
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextPesquisa = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jBtnEditarSelecionado = new javax.swing.JButton();
        jBtbDeletarCliente = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableResultClientes = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTableResultUsuarios = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableVendas = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jBtnPesquisarEntreDatas = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabelTotal = new javax.swing.JLabel();
        jLabelDataHoje = new javax.swing.JLabel();
        jDateBegin = new com.toedter.calendar.JDateChooser();
        jDateEnd = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jBtnEditarSelecionadoProd = new javax.swing.JButton();
        jBtnDeletarProd = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jBtnPesquisaProd = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextCodigo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxTipoPeça = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxCor = new javax.swing.JComboBox<>();
        jComboBoxTamanho = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableResultProdutos = new javax.swing.JTable();
        jRadioButton3 = new javax.swing.JRadioButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableCtrlQtd = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        jScrollPane3.setPreferredSize(new java.awt.Dimension(100, 100));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("La Viana's");
        setLocation(new java.awt.Point(300, 255));
        setResizable(false);

        jTabbedPane1.setBackground(new java.awt.Color(0, 0, 0));
        jTabbedPane1.setOpaque(true);
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Pesquisar: ");

        jTextPesquisa.setForeground(new java.awt.Color(153, 153, 153));
        jTextPesquisa.setText("Digite o nome, código ou rg do cliente:");
        jTextPesquisa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextPesquisaMouseClicked(evt);
            }
        });
        jTextPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextPesquisaKeyReleased(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/novo cliente.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jBtnEditarSelecionado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/editar cliente.png"))); // NOI18N
        jBtnEditarSelecionado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEditarSelecionadoActionPerformed(evt);
            }
        });

        jBtbDeletarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/deletar cliente.png"))); // NOI18N
        jBtbDeletarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtbDeletarClienteActionPerformed(evt);
            }
        });

        jTableResultClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "Endereço", "Telefone", "Email", "Sexo", "RG", "CPF"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableResultClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableResultClientesMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableResultClientesMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTableResultClientes);

        jLabel13.setForeground(new java.awt.Color(255, 0, 0));
        jLabel13.setText("para editar ou deletar clique na tabela");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBtbDeletarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                    .addComponent(jBtnEditarSelecionado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jTextPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 758, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBtnEditarSelecionado, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBtbDeletarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(8, 8, 8)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap(96, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Clientes", jPanel2);

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/novo usuario.png"))); // NOI18N
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/editar usuario.png"))); // NOI18N

        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/desativar usuario.png"))); // NOI18N

        jTableResultUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "Login", "Senha", "Tipo", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableResultUsuarios.getTableHeader().setReorderingAllowed(false);
        jTableResultUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableResultUsuariosMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(jTableResultUsuarios);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton14, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                    .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Usuários", jPanel4);

        jPanel1.setPreferredSize(new java.awt.Dimension(650, 290));
        jPanel1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jPanel1AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/nova venda.png"))); // NOI18N
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/trocas e devoluçoes.png"))); // NOI18N
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/deletar venda.png"))); // NOI18N

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/venda do dia.png"))); // NOI18N

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/data inicial.png"))); // NOI18N

        jTableVendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código Produto", "Vendedor", "Quantidade", "Desconto", "Total", "Data"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableVendas.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(jTableVendas);

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/data final.png"))); // NOI18N

        jBtnPesquisarEntreDatas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/pesquisar#1.png"))); // NOI18N
        jBtnPesquisarEntreDatas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPesquisarEntreDatasActionPerformed(evt);
            }
        });

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/total.png"))); // NOI18N

        jLabelTotal.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabelTotal.setText("R$ 0,00");

        jLabelDataHoje.setForeground(new java.awt.Color(102, 0, 102));
        jLabelDataHoje.setText("jLabel13");

        jLabel14.setForeground(new java.awt.Color(255, 0, 0));
        jLabel14.setText("para editar ou deletar clique na tabela");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jDateBegin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jDateEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(jBtnPesquisarEntreDatas)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel12)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(175, 175, 175))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelDataHoje)
                                .addGap(202, 202, 202)
                                .addComponent(jLabel14))
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabelDataHoje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel14)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelTotal)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel12)
                                    .addGap(3, 3, 3))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel11))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jBtnPesquisarEntreDatas)
                                        .addComponent(jDateBegin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jDateEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(74, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Vendas", jPanel1);

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/novo produto#1.png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jBtnEditarSelecionadoProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/editar produto.png"))); // NOI18N
        jBtnEditarSelecionadoProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEditarSelecionadoProdActionPerformed(evt);
            }
        });

        jBtnDeletarProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/excluir produto.png"))); // NOI18N
        jBtnDeletarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnDeletarProdActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/pesquisar#1.png"))); // NOI18N

        jBtnPesquisaProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/pesquisar#1.png"))); // NOI18N
        jBtnPesquisaProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPesquisaProdActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/codigo 2.png"))); // NOI18N

        jTextCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCodigoActionPerformed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/tipo de peça.png"))); // NOI18N

        jComboBoxTipoPeça.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/cor.png"))); // NOI18N

        jComboBoxCor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Preto", "Amarelo", "Azul" }));

        jComboBoxTamanho.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/tamanho 2.png"))); // NOI18N

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Feminino");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Masculino");

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/genero.png"))); // NOI18N

        jTableResultProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome Produto", "Tamanho", "Cor", "Preço", "Tipo", "Gênero", "Marca", "Qtd Estoque", "Qtd Mínima Estoque"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableResultProdutos.getTableHeader().setReorderingAllowed(false);
        jTableResultProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableResultProdutosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableResultProdutos);
        if (jTableResultProdutos.getColumnModel().getColumnCount() > 0) {
            jTableResultProdutos.getColumnModel().getColumn(5).setHeaderValue("Tipo");
            jTableResultProdutos.getColumnModel().getColumn(6).setHeaderValue("Gênero");
            jTableResultProdutos.getColumnModel().getColumn(7).setHeaderValue("Marca");
            jTableResultProdutos.getColumnModel().getColumn(8).setHeaderValue("Qtd Estoque");
            jTableResultProdutos.getColumnModel().getColumn(9).setHeaderValue("Qtd Mínima Estoque");
        }

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Unissex");

        jTableCtrlQtd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome Produto", "Qtd Estoque", "Qtd Mínima"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTableCtrlQtd);

        jLabel8.setText("Produtos Estoque Mínimo");

        jLabel15.setForeground(new java.awt.Color(255, 0, 0));
        jLabel15.setText("para editar ou deletar clique na tabela");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBtnDeletarProd)
                    .addComponent(jBtnEditarSelecionadoProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jComboBoxCor, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(jBtnPesquisaProd, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxTipoPeça, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(48, 48, 48)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jComboBoxTamanho, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(56, 56, 56)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton3)
                            .addComponent(jLabel7)
                            .addComponent(jRadioButton2)
                            .addComponent(jRadioButton1))))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15))
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jRadioButton1)))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jRadioButton2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jRadioButton3))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(36, 36, 36)
                                        .addComponent(jLabel3)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                                .addComponent(jBtnEditarSelecionadoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxTamanho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxTipoPeça, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBtnDeletarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(8, 8, 8)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxCor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBtnPesquisaProd))
                        .addGap(45, 45, 45))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jTabbedPane1.addTab("Produtos", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        setSize(new java.awt.Dimension(1094, 442));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        NovoCliente.setVisible(true);

        FuncionariosModel objModel = new FuncionariosModel();
        
        
        //if(!"ADMINISTRADOR".equals(objModel.getTipo())) {;
        //    jButton4.setEnabled(false);
         //   JOptionPane.showMessageDialog(null,"Solicite permissão ao Admin para acessar esta áerea!","Atenção",JOptionPane.WARNING_MESSAGE);
        //}
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        NovoProduto.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        NovaVenda.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        NovoUsuario.setVisible(true);
        // TODO add your handling code here: 
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        Troca.setVisible(true); 
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jTextCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextCodigoActionPerformed

    private void jBtnPesquisaProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesquisaProdActionPerformed
        int cod = Integer.parseInt(jTextCodigo.getText());
        String tipoPeca = jComboBoxTipoPeça.getSelectedItem().toString();
        String cor = jComboBoxCor.getSelectedItem().toString();
        String tamanho = jComboBoxTamanho.getSelectedItem().toString();
        
        ProdutoDao objDao = new ProdutoDao();
        DefaultTableModel tabelaPesquisaProd = (DefaultTableModel) jTableResultProdutos.getModel();
        tabelaPesquisaProd.setNumRows(0); // Limpa toda a tabela existente
        
        try {
            ResultSet rsPesquisaProd = objDao.pesquisar(cod,tipoPeca,cor,tamanho);
        
            while(rsPesquisaProd.next()) {
                int codigo = rsPesquisaProd.getInt("ID_PRODUTO");
                String nome = fn.ucFirst(rsPesquisaProd.getString("TXT_NOME_PROD"));
                String tamanhoProd = fn.ucFirst(rsPesquisaProd.getString("TXT_TAMANHO"));
                String corProd = fn.ucFirst(rsPesquisaProd.getString("TXT_COR"));
                int preco = rsPesquisaProd.getInt("INT_PRECO");
                String tipo = fn.ucFirst(rsPesquisaProd.getString("TXT_TIPO"));
                String genero = fn.ucFirst(rsPesquisaProd.getString("TXT_GENERO"));
                String marca = fn.ucFirst(rsPesquisaProd.getString("TXT_MARCA"));
                int qtdEstq = rsPesquisaProd.getInt("QTD_ESTQ");
                int qtdEstqMin = rsPesquisaProd.getInt("QTD_MIN_ESTQ");
                tabelaPesquisaProd.addRow(new Object[]{codigo,nome,tamanhoProd,corProd,preco,tipo,genero,marca,qtdEstq,qtdEstqMin});
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Início.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnPesquisaProdActionPerformed

    private void jTextPesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextPesquisaMouseClicked
        jTextPesquisa.setText("");
    }//GEN-LAST:event_jTextPesquisaMouseClicked

    private void jBtnEditarSelecionadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEditarSelecionadoActionPerformed
        DefaultTableModel tabelaClientes = (DefaultTableModel) jTableResultClientes.getModel();
        
        int row = jTableResultClientes.getSelectedRow(); 
        int idCliente = (int) tabelaClientes.getValueAt(row, 0);
        
        try {
            EditarCliente.setCCliente(idCliente);
            EditarCliente.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Início.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnEditarSelecionadoActionPerformed

    private void jBtbDeletarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtbDeletarClienteActionPerformed
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja excluir o Cliente selecionado?","Deletar", JOptionPane.YES_NO_OPTION);
        if(resposta == JOptionPane.YES_OPTION){
        
            DefaultTableModel tabelaClientes = (DefaultTableModel) jTableResultClientes.getModel();
        
            int row = jTableResultClientes.getSelectedRow(); 
            int idCliente = (int) tabelaClientes.getValueAt(row, 0);
        
            ClienteDao objDao = new ClienteDao();
            try {
                objDao.deletar(idCliente);
                tabelaClientes.setNumRows(0); // Limpa toda a tabela existente
                ResultSet rs = objDao.listar(0);
        
                while(rs.next()) {
                    int cod = rs.getInt("ID_CLIENTE");
                    String nome = fn.ucFirst(rs.getString("TXT_NOME"));
                    String endereco = fn.ucFirst(rs.getString("TXT_ENDERECO"));
                    String telefone = fn.ucFirst(rs.getString("TXT_TELEFONE"));
                    String email = fn.ucFirst(rs.getString("TXT_EMAIL"));
                    String sexo = fn.ucFirst(rs.getString("TXT_SEXO"));
                    String rg = rs.getString("TXT_RG");
                    String cpf = fn.ucFirst(rs.getString("TXT_CPF"));
                    tabelaClientes.addRow(new Object[]{cod,nome,endereco,telefone,email,sexo,rg,cpf});
                }
            

            } catch (SQLException ex) {
                Logger.getLogger(Início.class.getName()).log(Level.SEVERE, null, ex);
            }
            jBtbDeletarCliente.setEnabled(false);
            jBtnEditarSelecionado.setEnabled(false);
        }
    }//GEN-LAST:event_jBtbDeletarClienteActionPerformed

    private void jBtnDeletarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDeletarProdActionPerformed
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja excluir o Produto selecionado?","Deletar", JOptionPane.YES_NO_OPTION);
        if(resposta == JOptionPane.YES_OPTION){
            DefaultTableModel tabelaProd = (DefaultTableModel) jTableResultProdutos.getModel();
        
        int row = jTableResultProdutos.getSelectedRow(); 
        int idProd = (int) tabelaProd.getValueAt(row, 0);
        
        ProdutoDao objDao = new ProdutoDao();
        try {
            objDao.deletar(idProd);
            tabelaProd.setNumRows(0); // Limpa toda a tabela existente
            ResultSet rsProd = objDao.listar(0);
        
            while(rsProd.next()) {
                int codigo = rsProd.getInt("ID_PRODUTO");
                String nome = fn.ucFirst(rsProd.getString("TXT_NOME_PROD"));
                String tamanhoProd = fn.ucFirst(rsProd.getString("TXT_TAMANHO"));
                String corProd = fn.ucFirst(rsProd.getString("TXT_COR"));
                int preco = rsProd.getInt("INT_PRECO");
                String tipo = fn.ucFirst(rsProd.getString("TXT_TIPO"));
                String genero = fn.ucFirst(rsProd.getString("TXT_GENERO"));
                String marca = fn.ucFirst(rsProd.getString("TXT_MARCA"));
                int qtdEstq = rsProd.getInt("QTD_ESTQ");
                int qtdEstqMin = rsProd.getInt("QTD_MIN_ESTQ");
                tabelaProd.addRow(new Object[]{codigo,nome,tamanhoProd,corProd,preco,tipo,genero,marca,qtdEstq,qtdEstqMin});
            }
        } catch (SQLException ex) {
            Logger.getLogger(Início.class.getName()).log(Level.SEVERE, null, ex);
        }
        jBtnDeletarProd.setEnabled(false);
        jBtnEditarSelecionadoProd.setEnabled(false);
        }
        
        
    }//GEN-LAST:event_jBtnDeletarProdActionPerformed

    private void jPanel1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jPanel1AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1AncestorAdded

    private void jBtnPesquisarEntreDatasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesquisarEntreDatasActionPerformed
        Date dataInicio = jDateBegin.getDate();
        Date dataFim = jDateEnd.getDate();
        
        SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
        String dateBegin = formata.format(dataInicio);
        String dateEnd = formata.format(dataFim);
        
        DefaultTableModel tabelaVendas = (DefaultTableModel) jTableVendas.getModel();
        
        VendaDao objDao = new VendaDao();
        ResultSet rsVendasPeriodo = null;
        try {
            rsVendasPeriodo = objDao.listar(dateBegin, dateEnd);
        } catch (SQLException ex) {
            Logger.getLogger(Início.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int labelTotal = 0;
        tabelaVendas.setNumRows(0);
        
        try {
            while(rsVendasPeriodo.next()) {
                int codProd = rsVendasPeriodo.getInt("INT_COD_PROD");
                String nomeVendedor = rsVendasPeriodo.getString("TXT_VENDEDOR");
                int qtdProd = rsVendasPeriodo.getInt("INT_QTD_VENDA");
                int vlrDesc = rsVendasPeriodo.getInt("INT_DESCONTO");
                int total = rsVendasPeriodo.getInt("INT_TOTAL");
                String dataVenda = rsVendasPeriodo.getString("DAT_DATA_VENDA");
                tabelaVendas.addRow(new Object[] {codProd,nomeVendedor,qtdProd,vlrDesc,total,dataVenda});
                
                labelTotal += total;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Início.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        jLabelTotal.setText(NumberFormat.getCurrencyInstance().format(labelTotal));
        
        if(tabelaVendas.getRowCount() == 0 ){
           String msg = "Não há dados nesse período";
           tabelaVendas.addRow(new Object[]{msg,msg,msg,msg});
        }
    }//GEN-LAST:event_jBtnPesquisarEntreDatasActionPerformed

    private void jTableResultClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableResultClientesMouseClicked
        jBtnEditarSelecionado.setEnabled(true);
        jBtbDeletarCliente.setEnabled(true);
    }//GEN-LAST:event_jTableResultClientesMouseClicked

    private void jTableResultProdutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableResultProdutosMouseClicked
        jBtnEditarSelecionadoProd.setEnabled(true);
        jBtnDeletarProd.setEnabled(true);
    }//GEN-LAST:event_jTableResultProdutosMouseClicked

    private void jTableResultClientesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableResultClientesMousePressed
              // TODO add your handling code here:
    }//GEN-LAST:event_jTableResultClientesMousePressed

    private void jBtnEditarSelecionadoProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEditarSelecionadoProdActionPerformed
        DefaultTableModel tabelaProdutos = (DefaultTableModel) jTableResultProdutos.getModel();
        
        
        int row = jTableResultProdutos.getSelectedRow(); 
        int idProd = (int) tabelaProdutos.getValueAt(row, 0);

        try {
            EditarProduto.setPProd(idProd);
            EditarProduto.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Início.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnEditarSelecionadoProdActionPerformed

    private void jTextPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPesquisaKeyReleased
        String pesquisa = jTextPesquisa.getText();

        ClienteDao objDao = new ClienteDao();
        DefaultTableModel tabelaPesquisa = (DefaultTableModel) jTableResultClientes.getModel();
        tabelaPesquisa.setNumRows(0); // Limpa toda a tabela existente
        try {
            ResultSet rsPesquisaProd = objDao.pesquisar(pesquisa);
        
            while(rsPesquisaProd.next()) {
                int cod = rsPesquisaProd.getInt("ID_CLIENTE");
                String nome = fn.ucFirst(rsPesquisaProd.getString("TXT_NOME"));
                String endereco = fn.ucFirst(rsPesquisaProd.getString("TXT_ENDERECO"));
                String telefone = fn.ucFirst(rsPesquisaProd.getString("TXT_TELEFONE"));
                String email = fn.ucFirst(rsPesquisaProd.getString("TXT_EMAIL"));
                String sexo = fn.ucFirst(rsPesquisaProd.getString("TXT_SEXO"));
                String rg = rsPesquisaProd.getString("TXT_RG");
                String cpf = fn.ucFirst(rsPesquisaProd.getString("TXT_CPF"));
                tabelaPesquisa.addRow(new Object[]{cod,nome,endereco,telefone,email,sexo,rg,cpf});
            }
        } catch (SQLException ex) {
            Logger.getLogger(Início.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextPesquisaKeyReleased

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        try {
            tabelaClientes();
            tabelaProdutos();
            tabelaVendas();
            tabelaProdEstqMin();
            tabelaUsuarios();
        } catch (SQLException ex) {
            Logger.getLogger(Início.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jTableResultUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableResultUsuariosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableResultUsuariosMouseClicked

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jBtbDeletarCliente;
    private javax.swing.JButton jBtnDeletarProd;
    private javax.swing.JButton jBtnEditarSelecionado;
    private javax.swing.JButton jBtnEditarSelecionadoProd;
    private javax.swing.JButton jBtnPesquisaProd;
    private javax.swing.JButton jBtnPesquisarEntreDatas;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox<String> jComboBoxCor;
    private javax.swing.JComboBox<String> jComboBoxTamanho;
    private javax.swing.JComboBox<String> jComboBoxTipoPeça;
    private com.toedter.calendar.JDateChooser jDateBegin;
    private com.toedter.calendar.JDateChooser jDateEnd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelDataHoje;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTableCtrlQtd;
    private javax.swing.JTable jTableResultClientes;
    private javax.swing.JTable jTableResultProdutos;
    private javax.swing.JTable jTableResultUsuarios;
    private javax.swing.JTable jTableVendas;
    private javax.swing.JTextField jTextCodigo;
    private javax.swing.JTextField jTextPesquisa;
    // End of variables declaration//GEN-END:variables
}
