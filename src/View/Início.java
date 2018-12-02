
import Dao.ClienteDao;
import Dao.FuncionariosDao;
import Dao.ProdutoDao;
import Dao.VendaDao;
import Model.ContatoModel;
import Model.FuncionariosModel;
import View.EditarCliente;
import View.EditarProduto;
import View.EditarUsuario;
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
    EditarUsuario EditarUsuario;
    
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
        EditarUsuario = new EditarUsuario();
        
        jBtnEditarSelecionado.setEnabled(false);
        jBtbDeletarCliente.setEnabled(false);
        jBtnEditarSelecionadoProd.setEnabled(false);
        jBtnDeletarProd.setEnabled(false);
        EditarSelecionadoUser.setEnabled(false);
        DesativarUser.setEnabled(false);
        
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
        ResultSet rsFun = objDao.listar(0);
        
        while(rsFun.next()) {
            int cod = rsFun.getInt("ID_USUARIO");
            String nome = rsFun.getString("TXT_NOME");
            String login = rsFun.getString("TXT_LOGIN");
            String senha = rsFun.getString("TXT_SENHA");
            String tipo = rsFun.getString("TXT_TIPO");
            String status = rsFun.getString("TXT_STATUS");
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
        EditarSelecionadoUser = new javax.swing.JButton();
        DesativarUser = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTableResultUsuarios = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
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
        setMaximumSize(new java.awt.Dimension(756, 1087));
        setPreferredSize(new java.awt.Dimension(756, 1087));
        setResizable(false);

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setMaximumSize(new java.awt.Dimension(1087, 756));
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(1087, 756));
        jTabbedPane1.setOpaque(true);
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1078, 756));
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
        jLabel13.setText("Para editar ou deletar clique na tabela");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnEditarSelecionado, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtbDeletarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jTextPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel13))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBtnEditarSelecionado, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtbDeletarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        jTabbedPane1.addTab("Clientes", jPanel2);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/novo usuario.png"))); // NOI18N
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        EditarSelecionadoUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/editar usuario.png"))); // NOI18N
        EditarSelecionadoUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditarSelecionadoUserActionPerformed(evt);
            }
        });

        DesativarUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/desativar usuario.png"))); // NOI18N
        DesativarUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DesativarUserActionPerformed(evt);
            }
        });

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

        jLabel16.setForeground(new java.awt.Color(255, 0, 0));
        jLabel16.setText("Para editar ou deletar clique na tabela");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(DesativarUser, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(EditarSelecionadoUser, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel16))
                            .addComponent(jScrollPane6))
                        .addGap(24, 24, 24))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(EditarSelecionadoUser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                        .addComponent(DesativarUser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButton12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        jTabbedPane1.addTab("Usuários", jPanel4);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
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
        jLabel14.setText("Para editar ou deletar clique na tabela");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelDataHoje)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateBegin, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnPesquisarEntreDatas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                        .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelDataHoje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateBegin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBtnPesquisarEntreDatas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                .addGap(22, 22, 22)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTotal)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Vendas", jPanel1);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

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

        jRadioButton2.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Feminino");

        jRadioButton1.setBackground(new java.awt.Color(255, 255, 255));
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

        jRadioButton3.setBackground(new java.awt.Color(255, 255, 255));
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
        jLabel15.setText("Para editar ou deletar clique na tabela");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel15))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jBtnDeletarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jBtnEditarSelecionadoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jRadioButton3)
                                .addGap(87, 87, 87))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(31, 31, 31)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                    .addComponent(jComboBoxTipoPeça, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jComboBoxTamanho, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                            .addComponent(jComboBoxCor, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addGap(42, 42, 42))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                                            .addComponent(jLabel4)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jBtnPesquisaProd, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                    .addGap(31, 31, 31)
                                                    .addComponent(jTextCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                    .addGap(23, 23, 23)
                                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(59, 59, 59)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jRadioButton2)
                                            .addComponent(jRadioButton1)
                                            .addComponent(jLabel7)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(23, 23, 23)
                                        .addComponent(jLabel8)))
                                .addGap(63, 63, 63)))
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxTipoPeça, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxTamanho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBtnPesquisaProd, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboBoxCor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                    .addComponent(jBtnEditarSelecionadoProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnDeletarProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(11, 11, 11))
        );

        jTabbedPane1.addTab("Produtos", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1087, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1103, 795));
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
       EditarSelecionadoUser.setEnabled(true);
       DesativarUser.setEnabled(true);
    }//GEN-LAST:event_jTableResultUsuariosMouseClicked

    private void EditarSelecionadoUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditarSelecionadoUserActionPerformed
    DefaultTableModel tabelaUsuarios = (DefaultTableModel) jTableResultUsuarios.getModel();
        
        int row = jTableResultUsuarios.getSelectedRow(); 
        int userId = (int) tabelaUsuarios.getValueAt(row, 0);
        
        try {
            EditarUsuario.setUUser(userId);
            EditarUsuario.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Início.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_EditarSelecionadoUserActionPerformed

    private void DesativarUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DesativarUserActionPerformed
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja Desativar o Funcionário selecionado?","Desativar", JOptionPane.YES_NO_OPTION);
        if(resposta == JOptionPane.YES_OPTION){
        DefaultTableModel tabelaUser = (DefaultTableModel) jTableResultUsuarios.getModel();
        
        int row = jTableResultUsuarios.getSelectedRow(); 
        int IdFun = (int) tabelaUser.getValueAt(row, 0);
        
        FuncionariosDao objDao = new FuncionariosDao();
        try {
            objDao.deletar(IdFun);
            tabelaUser.setNumRows(0); // Limpa toda a tabela existente
            ResultSet rsFun = objDao.listar(0);
        
            ResultSet rs = objDao.listar(0);
        
        while(rs.next()) {
            int cod = rs.getInt("ID_USUARIO");
            String nome = rs.getString("TXT_NOME");
            String login = rs.getString("TXT_LOGIN");
            String senha = rs.getString("TXT_SENHA");
            String tipo = rs.getString("TXT_TIPO");
            String status = rs.getString("TXT_STATUS");
            tabelaUser.addRow(new Object[]{cod,nome,login,senha,tipo,status});
        }
        } catch (SQLException ex) {
            Logger.getLogger(Início.class.getName()).log(Level.SEVERE, null, ex);
        }
        EditarSelecionadoUser.setEnabled(false);
        DesativarUser.setEnabled(false);
        }
        
        
    }//GEN-LAST:event_DesativarUserActionPerformed

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
    private javax.swing.JButton DesativarUser;
    private javax.swing.JButton EditarSelecionadoUser;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jBtbDeletarCliente;
    private javax.swing.JButton jBtnDeletarProd;
    private javax.swing.JButton jBtnEditarSelecionado;
    private javax.swing.JButton jBtnEditarSelecionadoProd;
    private javax.swing.JButton jBtnPesquisaProd;
    private javax.swing.JButton jBtnPesquisarEntreDatas;
    private javax.swing.JButton jButton12;
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
    private javax.swing.JLabel jLabel16;
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
