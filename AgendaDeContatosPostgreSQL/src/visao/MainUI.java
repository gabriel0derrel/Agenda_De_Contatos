/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package visao;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import conexao.ConexaoDB;
import controle.Controle;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.DefaultFormatterFactory;
import modelos.principais.*;
import modelos.secundarios.*;

public class MainUI extends javax.swing.JFrame {

    
    
    private Path encontrarCaminhoDoArquivo(String nomeDoAqruivo) throws Exception{
        try{
            String packagePath = "src/arquivos";
            Path currentWorkingDir = Paths.get("").toAbsolutePath();
            Path packageDir = currentWorkingDir.resolve(packagePath);
            if(!Files.exists(packageDir)){
                Files.createDirectories(packageDir);
            }
            Path filePath = packageDir.resolve(nomeDoAqruivo);
            return filePath;
        }catch(Exception erro){
            throw new Exception("Erro na MainUI - encontrarCaminhoDoArquivo - Erro na Manipulaçõa de Arquivo");
        }
    }
    
    private void SetImageLabel (JLabel labelName, String root){
        ImageIcon image = new ImageIcon(root);
        Icon icon = new ImageIcon(image.getImage().getScaledInstance(labelName.getWidth(), labelName.getHeight(), Image.SCALE_DEFAULT));
        labelName.setIcon(icon);
        this.repaint();
    }
    
    Icrud arquivo = new Controle();
    boolean consultarHasBeenClicked = false;
    private Connection conexao = null;
    
    public enum Colunas{
        Nome, ID, Email, Telefone, Logradouro, Numero, CEP, Estado, Cidade, Complemento
    }
    
    /*Cara ordenar a List, é só chamar a função comparar no segundo campo.
    List<Contato> lista = arquivo.listar();
    lista.sort()*/
    private Comparator<Contato> comparar(Colunas opcao){
        Comparator<Contato> funcaoLambda;
        switch(opcao){
            case Nome:
                funcaoLambda = (c1, c2) -> c1.getNome().compareTo(c2.getNome());
                break;
            case ID:
                funcaoLambda = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
                break;
            case Email:
                funcaoLambda = (c1, c2) -> c1.getEmail().compareTo(c2.getEmail());
                break;
            case Telefone:
                funcaoLambda = (c1, c2) -> c1.getTelefone().toString().compareTo(c2.getTelefone().toString());
                break;
            case Logradouro:
                funcaoLambda = (c1, c2) -> c1.getEndereco().getLogradouro().compareTo(c2.getEndereco().getLogradouro());
                break;
            case Numero:
                funcaoLambda = (c1, c2) -> c1.getEndereco().getNumero().compareTo(c2.getEndereco().getNumero());
                break;
            case CEP:
                funcaoLambda = (c1, c2) -> c1.getEndereco().getCep().compareTo(c2.getEndereco().getCep());
                break;
            case Estado:
                funcaoLambda = (c1, c2) -> c1.getEndereco().getEstado().compareTo(c2.getEndereco().getEstado());
                break;
            case Cidade:
                funcaoLambda = (c1, c2) -> c1.getEndereco().getCidade().compareTo(c2.getEndereco().getCidade());
                break;
            case Complemento:
                funcaoLambda = (c1, c2) -> c1.getEndereco().getComplemento().compareTo(c2.getEndereco().getComplemento());
                break;
            default:
                funcaoLambda = null;
                break;
        }
        return funcaoLambda;
    }
    
    private void atualizarTabela (List<Contato> lista, DefaultTableModel model){
        model.setRowCount(0);
                    
        for (int iterador = 0; iterador < lista.size(); iterador++) {
            String[] saida = new String[10];
            saida[0] = lista.get(iterador).getId()+"";
            saida[1] = lista.get(iterador).getNome();
            saida[2] = lista.get(iterador).getEmail();
            saida[3] = lista.get(iterador).getTelefone().toString();
            saida[4] = lista.get(iterador).getEndereco().getLogradouro();
            saida[5] = lista.get(iterador).getEndereco().getNumero();
            saida[6] = lista.get(iterador).getEndereco().getCep();
            saida[7] = lista.get(iterador).getEndereco().getEstado();
            saida[8] = lista.get(iterador).getEndereco().getCidade();
            saida[9] = lista.get(iterador).getEndereco().getComplemento();
            model.addRow(saida);
        }
    }
    
    // Inicialização da tela
    public MainUI() {
        initComponents();
        this.setLocationRelativeTo(this);
        
        
        getContentPane().setBackground(Color.getHSBColor(0.147f, 0.35f, 0.5f));

        jPanel1_info.setVisible(false);
        
        originalFormatterFactory = (DefaultFormatterFactory) jFormattedTextField1_numero.getFormatterFactory();
        originalFormatterFactoryCEP = (DefaultFormatterFactory) jFormattedTextField2_cep.getFormatterFactory();
        
        jFormattedTextField2_cep.setFormatterFactory(null);
        jFormattedTextField2_cep.setText("CEP");
        
        SetImageLabel(jLabel1_Info, "src/imagens/about.png");
        SetImageLabel(jLabel1_Logo, "src/imagens/grupo.png");
        
        SetImageLabel(jLabel6_lucas, "src/imagens/image.png");
        SetImageLabel(jLabel2_derrel, "src/imagens/Gabriel_Derrel_Foto.png");
        
        try {
            List<Contato> listaDeContatos = arquivo.listar();
            //Ordenamento
            listaDeContatos.sort(comparar((Colunas) jComboBox_Ordenar.getSelectedItem()));
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            // Apaga todos os elementos na tabela e atualiza
            jComboBox_Ordenar.addActionListener(e -> {
                try {
                    List<Contato> listaContatos = arquivo.listar();
                    listaContatos.sort(comparar((Colunas) jComboBox_Ordenar.getSelectedItem()));
                    atualizarTabela(listaContatos, model);
                } catch (Exception ex) {
                    Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            
            atualizarTabela(listaDeContatos, model);
            
            setDefaultCloseOperation(MainUI.DO_NOTHING_ON_CLOSE);
            
            //Encerra a conexão ao fechar a janela
            addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosing(WindowEvent e) {
                    int resposta = JOptionPane.showConfirmDialog(
                            MainUI.this, 
                            "Tem certeza que deseja fechar?", 
                            "Confirmação", 
                            JOptionPane.YES_NO_OPTION
                    );

                    if (resposta == JOptionPane.YES_OPTION) {
                        try {
                            conexao = ConexaoDB.getConexao();
                            conexao.close();
                            dispose();
                        } catch (Exception ex) {
                            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage());
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

        jLabel1_Info = new javax.swing.JLabel();
        jTextField1_Nome = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton3_consultar = new javax.swing.JButton();
        jTextField2_Email = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton6_alterar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jButton7_pdf = new javax.swing.JButton();
        jTextField4_Logradouro = new javax.swing.JTextField();
        jButton1_incluir = new javax.swing.JButton();
        jTextField6_Complemento = new javax.swing.JTextField();
        jButton8_excluir = new javax.swing.JButton();
        jTextField9_Cidade = new javax.swing.JTextField();
        jLabel1_Logo = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1_Estado = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jButton1_limparDados = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1_info = new javax.swing.JPanel();
        jLabel2_derrel = new javax.swing.JLabel();
        jLabel6_lucas = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jFormattedTextField1_telefone = new javax.swing.JFormattedTextField();
        jFormattedTextField1_numero = new javax.swing.JFormattedTextField();
        jFormattedTextField2_cep = new javax.swing.JFormattedTextField();
        jComboBox_Ordenar = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1_Info.setText("INFO");
        jLabel1_Info.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1_InfoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel1_InfoMouseExited(evt);
            }
        });

        jTextField1_Nome.setForeground(new java.awt.Color(153, 153, 153));
        jTextField1_Nome.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1_Nome.setText("Insira seu nome...");
        jTextField1_Nome.setOpaque(true);
        jTextField1_Nome.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField1_NomeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1_NomeFocusLost(evt);
            }
        });
        jTextField1_Nome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1_NomeActionPerformed(evt);
            }
        });
        jTextField1_Nome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1_NomeKeyTyped(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setLabelFor(jTextField1_Nome);
        jLabel3.setText("Nome:");

        jButton3_consultar.setBackground(new java.awt.Color(255, 255, 102));
        jButton3_consultar.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jButton3_consultar.setForeground(new java.awt.Color(87, 83, 55));
        jButton3_consultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/loupe.png"))); // NOI18N
        jButton3_consultar.setText("CONSULTAR ");
        jButton3_consultar.setBorderPainted(false);
        jButton3_consultar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton3_consultar.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton3_consultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3_consultarActionPerformed(evt);
            }
        });

        jTextField2_Email.setForeground(new java.awt.Color(153, 153, 153));
        jTextField2_Email.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2_Email.setText("usuario@gmail.com");
        jTextField2_Email.setOpaque(true);
        jTextField2_Email.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField2_EmailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField2_EmailFocusLost(evt);
            }
        });
        jTextField2_Email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2_EmailActionPerformed(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setLabelFor(jTextField2_Email);
        jLabel4.setText("Email:");

        jButton6_alterar.setBackground(new java.awt.Color(255, 255, 102));
        jButton6_alterar.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jButton6_alterar.setForeground(new java.awt.Color(87, 83, 55));
        jButton6_alterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alterar.png"))); // NOI18N
        jButton6_alterar.setText("ALTERAR ");
        jButton6_alterar.setBorderPainted(false);
        jButton6_alterar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton6_alterar.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton6_alterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6_alterarActionPerformed(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Telefone:");

        jButton7_pdf.setBackground(new java.awt.Color(255, 255, 102));
        jButton7_pdf.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jButton7_pdf.setForeground(new java.awt.Color(87, 83, 55));
        jButton7_pdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/ficheiro-pdf (1).png"))); // NOI18N
        jButton7_pdf.setBorderPainted(false);
        jButton7_pdf.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton7_pdf.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton7_pdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7_pdfActionPerformed(evt);
            }
        });

        jTextField4_Logradouro.setForeground(new java.awt.Color(153, 153, 153));
        jTextField4_Logradouro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField4_Logradouro.setText("Logradouro");
        jTextField4_Logradouro.setOpaque(true);
        jTextField4_Logradouro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField4_LogradouroFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField4_LogradouroFocusLost(evt);
            }
        });
        jTextField4_Logradouro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4_LogradouroActionPerformed(evt);
            }
        });

        jButton1_incluir.setBackground(new java.awt.Color(255, 255, 102));
        jButton1_incluir.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jButton1_incluir.setForeground(new java.awt.Color(87, 83, 55));
        jButton1_incluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/adicionar-ficheiro (2).png"))); // NOI18N
        jButton1_incluir.setText("INCLUIR ");
        jButton1_incluir.setBorderPainted(false);
        jButton1_incluir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1_incluir.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton1_incluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1_incluirActionPerformed(evt);
            }
        });

        jTextField6_Complemento.setForeground(new java.awt.Color(153, 153, 153));
        jTextField6_Complemento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField6_Complemento.setText("Complemento...");
        jTextField6_Complemento.setOpaque(true);
        jTextField6_Complemento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField6_ComplementoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField6_ComplementoFocusLost(evt);
            }
        });
        jTextField6_Complemento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6_ComplementoActionPerformed(evt);
            }
        });

        jButton8_excluir.setBackground(new java.awt.Color(255, 255, 102));
        jButton8_excluir.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jButton8_excluir.setForeground(new java.awt.Color(87, 83, 55));
        jButton8_excluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/excluir-arquivo (1).png"))); // NOI18N
        jButton8_excluir.setText("EXCLUIR ");
        jButton8_excluir.setBorderPainted(false);
        jButton8_excluir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton8_excluir.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton8_excluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8_excluirActionPerformed(evt);
            }
        });

        jTextField9_Cidade.setForeground(new java.awt.Color(153, 153, 153));
        jTextField9_Cidade.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField9_Cidade.setText("Cidade");
        jTextField9_Cidade.setOpaque(true);
        jTextField9_Cidade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField9_CidadeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField9_CidadeFocusLost(evt);
            }
        });

        jLabel1_Logo.setText("LOGO");

        jLabel7.setBackground(new java.awt.Color(255, 204, 204));
        jLabel7.setFont(new java.awt.Font("Sitka Small", 1, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 102));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("AGENDA DE CONTATOS");
        jLabel7.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel7.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jComboBox1_Estado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Estado", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));

        jLabel1.setLabelFor(jTextField4_Logradouro);
        jLabel1.setText("Endereço:");

        jButton1_limparDados.setBackground(new java.awt.Color(255, 255, 102));
        jButton1_limparDados.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jButton1_limparDados.setForeground(new java.awt.Color(87, 83, 55));
        jButton1_limparDados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/broom.png"))); // NOI18N
        jButton1_limparDados.setText("LIMPAR DADOS ");
        jButton1_limparDados.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton1_limparDados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1_limparDadosActionPerformed(evt);
            }
        });

        jTable1.setForeground(new java.awt.Color(102, 102, 0));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Email", "Telefone", "Logradouro", "Número", "CEP", "Estado", "Cidade", "Complemento"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setToolTipText("");
        jTable1.setShowGrid(true);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(35);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(35);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(140);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(140);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(60);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(60);
            jTable1.getColumnModel().getColumn(7).setPreferredWidth(60);
            jTable1.getColumnModel().getColumn(7).setMaxWidth(60);
        }

        jPanel1_info.setBackground(new java.awt.Color(153, 153, 0));

        jLabel2_derrel.setText("Derrel");

        jLabel6_lucas.setText("Lucas");

        jLabel8.setForeground(new java.awt.Color(255, 255, 153));
        jLabel8.setText("Projeto de uma Agenda de Contatos feito para a apresentação");
        jLabel8.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel9.setForeground(new java.awt.Color(255, 255, 153));
        jLabel9.setText("de um trabalho na disciplina de POO na PUC-GO, ministrada");

        jLabel10.setForeground(new java.awt.Color(255, 255, 153));
        jLabel10.setText("pelo Prof. Eugênio");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 153));
        jLabel11.setText("Autores:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 153));
        jLabel12.setText("Gabriel Derrel Martins Santee | Lucas Pereira Nunes");

        javax.swing.GroupLayout jPanel1_infoLayout = new javax.swing.GroupLayout(jPanel1_info);
        jPanel1_info.setLayout(jPanel1_infoLayout);
        jPanel1_infoLayout.setHorizontalGroup(
            jPanel1_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1_infoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1_infoLayout.createSequentialGroup()
                        .addComponent(jLabel2_derrel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6_lucas, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1_infoLayout.createSequentialGroup()
                        .addGroup(jPanel1_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addGroup(jPanel1_infoLayout.createSequentialGroup()
                                .addGap(147, 147, 147)
                                .addComponent(jLabel11))
                            .addGroup(jPanel1_infoLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel12)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1_infoLayout.setVerticalGroup(
            jPanel1_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1_infoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6_lucas, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2_derrel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jFormattedTextField1_telefone.setForeground(new java.awt.Color(153, 153, 153));
        try {
            jFormattedTextField1_telefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("+### (##) # ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextField1_telefone.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextField1_telefone.setText("+000 (00) 0 0000-0000");
        jFormattedTextField1_telefone.setOpaque(true);
        jFormattedTextField1_telefone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFormattedTextField1_telefoneFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFormattedTextField1_telefoneFocusLost(evt);
            }
        });
        jFormattedTextField1_telefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField1_telefoneActionPerformed(evt);
            }
        });

        jFormattedTextField1_numero.setForeground(new java.awt.Color(153, 153, 153));
        jFormattedTextField1_numero.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###"))));
        jFormattedTextField1_numero.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextField1_numero.setText("Número");
        jFormattedTextField1_numero.setOpaque(true);
        jFormattedTextField1_numero.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFormattedTextField1_numeroFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFormattedTextField1_numeroFocusLost(evt);
            }
        });

        jFormattedTextField2_cep.setForeground(new java.awt.Color(153, 153, 153));
        try {
            jFormattedTextField2_cep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextField2_cep.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextField2_cep.setText("00000-000");
        jFormattedTextField2_cep.setOpaque(true);
        jFormattedTextField2_cep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFormattedTextField2_cepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFormattedTextField2_cepFocusLost(evt);
            }
        });

        jComboBox_Ordenar.setModel(new javax.swing.DefaultComboBoxModel<>(Colunas.values()));
        jComboBox_Ordenar.setToolTipText("");

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 51));
        jLabel2.setLabelFor(jComboBox_Ordenar);
        jLabel2.setText("Ordenar Por:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1_Logo, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(291, 291, 291)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1_Info, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1_incluir, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(jButton8_excluir)
                        .addGap(47, 47, 47)
                        .addComponent(jButton6_alterar)
                        .addGap(51, 51, 51)
                        .addComponent(jButton3_consultar)
                        .addGap(83, 83, 83)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox_Ordenar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(jButton7_pdf, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextField4_Logradouro, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jFormattedTextField1_numero, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(jFormattedTextField2_cep, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(jComboBox1_Estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(jTextField9_Cidade, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jTextField6_Complemento, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jFormattedTextField1_telefone, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField2_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField1_Nome, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(38, 38, 38)
                                .addComponent(jButton1_limparDados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1_info, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(45, 45, 45)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jTextField1_Nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jTextField2_Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4)
                                            .addComponent(jButton1_limparDados))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel5)
                                            .addComponent(jFormattedTextField1_telefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(13, 13, 13)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jTextField9_Cidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField4_Logradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jComboBox1_Estado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel1)
                                            .addComponent(jFormattedTextField1_numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jFormattedTextField2_cep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField6_Complemento, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(33, 33, 33)
                                        .addComponent(jPanel1_info, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton6_alterar)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1_incluir)
                                        .addComponent(jButton3_consultar)
                                        .addComponent(jButton8_excluir)
                                        .addComponent(jComboBox_Ordenar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(jButton7_pdf, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel1_Info, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1_Logo, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1_NomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1_NomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1_NomeActionPerformed

    private void jTextField2_EmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2_EmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2_EmailActionPerformed

    private void jTextField4_LogradouroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4_LogradouroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4_LogradouroActionPerformed

    private void jTextField6_ComplementoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6_ComplementoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6_ComplementoActionPerformed

    //eventos focus para alteração do texto em display no textfield
    private void jTextField1_NomeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1_NomeFocusGained
        // TODO add your handling code here:
        if(jTextField1_Nome.getText().compareTo("Insira seu nome...") == 0){
            jTextField1_Nome.setText("");
            jTextField1_Nome.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_jTextField1_NomeFocusGained

    private void jTextField1_NomeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1_NomeFocusLost
        // TODO add your handling code here:
        if(jTextField1_Nome.getText().isEmpty()){
            jTextField1_Nome.setForeground(Color.getHSBColor(0, 0, 0.6f));
            jTextField1_Nome.setText("Insira seu nome...");
        }
    }//GEN-LAST:event_jTextField1_NomeFocusLost

    private void jTextField2_EmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2_EmailFocusGained
        // TODO add your handling code here:
        if(jTextField2_Email.getText().compareTo("usuario@gmail.com") == 0){
            jTextField2_Email.setText("");
            jTextField2_Email.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_jTextField2_EmailFocusGained

    private void jTextField2_EmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2_EmailFocusLost
        // TODO add your handling code here:
        if(jTextField2_Email.getText().isEmpty()){
            jTextField2_Email.setForeground(Color.getHSBColor(0, 0, 0.6f));
            jTextField2_Email.setText("usuario@gmail.com");
        }
    }//GEN-LAST:event_jTextField2_EmailFocusLost

    private void jTextField4_LogradouroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField4_LogradouroFocusGained
        // TODO add your handling code here:
        if(jTextField4_Logradouro.getText().compareTo("Logradouro") == 0){
            jTextField4_Logradouro.setText("");
            jTextField4_Logradouro.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_jTextField4_LogradouroFocusGained

    private void jTextField4_LogradouroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField4_LogradouroFocusLost
        // TODO add your handling code here:
        if(jTextField4_Logradouro.getText().isEmpty()){
            jTextField4_Logradouro.setForeground(Color.getHSBColor(0, 0, 0.6f));
            jTextField4_Logradouro.setText("Logradouro");
        }
    }//GEN-LAST:event_jTextField4_LogradouroFocusLost

    private void jTextField9_CidadeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField9_CidadeFocusGained
        // TODO add your handling code here:
        if(jTextField9_Cidade.getText().compareTo("Cidade") == 0){
            jTextField9_Cidade.setText("");
            jTextField9_Cidade.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_jTextField9_CidadeFocusGained

    private void jTextField9_CidadeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField9_CidadeFocusLost
        // TODO add your handling code here:
        if(jTextField9_Cidade.getText().isEmpty()){
            jTextField9_Cidade.setForeground(Color.getHSBColor(0, 0, 0.6f));
            jTextField9_Cidade.setText("Cidade");
        }
    }//GEN-LAST:event_jTextField9_CidadeFocusLost

    private void jTextField6_ComplementoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField6_ComplementoFocusGained
        // TODO add your handling code here:
        if(jTextField6_Complemento.getText().compareTo("Complemento...") == 0){
            jTextField6_Complemento.setText("");
            jTextField6_Complemento.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_jTextField6_ComplementoFocusGained

    private void jTextField6_ComplementoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField6_ComplementoFocusLost
        // TODO add your handling code here:
        if(jTextField6_Complemento.getText().isEmpty()){
            jTextField6_Complemento.setForeground(Color.getHSBColor(0, 0, 0.6f));
            jTextField6_Complemento.setText("Complemento...");
        }
    }//GEN-LAST:event_jTextField6_ComplementoFocusLost

    // Botão incluir
    private void jButton1_incluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1_incluirActionPerformed
        // TODO add your handling code here:
        try {
            String telefoneCompleto = jFormattedTextField1_telefone.getText();
            Pattern pattern = Pattern.compile("\\+(\\d{3}) \\((\\d{2})\\) (\\d) (\\d{4})-(\\d{4})");
            Matcher matcher = pattern.matcher(telefoneCompleto);

                // Inserir informações no contato
                Contato contato = new Contato();
                contato.setNome(jTextField1_Nome.getText());
                String telefoneFormatado = "";
                String ddi = "";
                String ddd = "";
                if (matcher.matches() && jFormattedTextField1_telefone.getText().compareTo("+000 (00) 0 0000-0000") != 0) {
                    ddi = matcher.group(1);
                    ddd = matcher.group(2);
                    String numeroParte1 = matcher.group(3);
                    String numeroParte2 = matcher.group(4);
                    String numeroParte3 = matcher.group(5);

                    // Reconstituir o telefone com formatação
                    telefoneFormatado = numeroParte1 + " " + numeroParte2 + "-" + numeroParte3;
                    Telefone telefone = new Telefone("+" + ddi, " (" + ddd + ") ", telefoneFormatado);
                    contato.setTelefone(telefone);
                    System.out.println("preenchido");
                }
                else{
                    System.out.println("nao preenchido");
                    contato.setTelefone("+000 (00) 0 0000-0000");
                }
                contato.setEmail(jTextField2_Email.getText());
                Endereco endereco = null;
                if(jTextField6_Complemento.getText().compareTo("Complemento...") == 0){
                    endereco = new Endereco(jTextField4_Logradouro.getText(), jFormattedTextField1_numero.getText(),
                            " ", jFormattedTextField2_cep.getText(), jTextField9_Cidade.getText(),
                            jComboBox1_Estado.getSelectedItem().toString());
                } else {
                    endereco = new Endereco(jTextField4_Logradouro.getText(), jFormattedTextField1_numero.getText(),
                            jTextField6_Complemento.getText(), jFormattedTextField2_cep.getText(), jTextField9_Cidade.getText(),
                            jComboBox1_Estado.getSelectedItem().toString());
                }
                
                contato.setEndereco(endereco);
                arquivo.incluir(contato);
                System.out.println(contato.getTelefone());
                List<Contato> listaDeContatos = arquivo.listar();
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setRowCount(0);
                atualizarTabela(listaDeContatos, model);
            } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage());
        }
    }//GEN-LAST:event_jButton1_incluirActionPerformed

    private String nomeAtual = "";
    
    // Botão de alterar
    private void jButton6_alterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6_alterarActionPerformed
        // TODO add your handling code here:
        try{
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
            int i = jTable1.getSelectedRow();
            if(i >= 0){
                // Verifica se o nome foi alterado
                if(nomeAtual.compareTo(jTextField1_Nome.getText()) == 0){
                    model.setValueAt(jTextField1_Nome.getText(), i, 1);
                    model.setValueAt(jTextField2_Email.getText(), i, 2);
                    model.setValueAt(jFormattedTextField1_telefone.getText(), i, 3);
                    model.setValueAt(jTextField4_Logradouro.getText(), i, 4);
                    model.setValueAt(jFormattedTextField1_numero.getText(), i, 5);
                    model.setValueAt(jFormattedTextField2_cep.getText(), i, 6);
                    model.setValueAt(jComboBox1_Estado.getSelectedItem(), i, 7);
                    model.setValueAt(jTextField9_Cidade.getText(), i, 8);
                    if(jTextField6_Complemento.getText().compareTo("Complemento...") != 0){
                        model.setValueAt(jTextField6_Complemento.getText(), i, 9);
                    } else {
                        model.setValueAt(" ", i, 9);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Nome não pode ser alterado!");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Nenhuma linha foi selecionada!");
            }
            
            // Insere as novas informações no contato
            String telefoneCompleto = jFormattedTextField1_telefone.getText();
            Pattern pattern = Pattern.compile("\\+(\\d{3}) \\((\\d{2})\\) (\\d) (\\d{4})-(\\d{4})");
            Matcher matcher = pattern.matcher(telefoneCompleto);
            if (matcher.matches()) {
                String ddi = matcher.group(1);
                String ddd = matcher.group(2);
                String numeroParte1 = matcher.group(3);
                String numeroParte2 = matcher.group(4);
                String numeroParte3 = matcher.group(5);

                // Reconstituir o telefone com formatação
                String telefoneFormatado = numeroParte1 + " " + numeroParte2 + "-" + numeroParte3;

                Contato contato = new Contato();
                contato.setId(Integer.parseInt((String) model.getValueAt(jTable1.getSelectedRow(), 0)));
                contato.setNome(jTextField1_Nome.getText());
                Telefone telefone = new Telefone("+" + ddi, " (" + ddd + ") ", telefoneFormatado);
                contato.setEmail(jTextField2_Email.getText());
                Endereco endereco = null;
                if(jTextField6_Complemento.getText().compareTo("Complemento...") == 0){
                    endereco = new Endereco(jTextField4_Logradouro.getText(), jFormattedTextField1_numero.getText(),
                            " ", jFormattedTextField2_cep.getText(), jTextField9_Cidade.getText(),
                            jComboBox1_Estado.getSelectedItem().toString());
                } else {
                    endereco = new Endereco(jTextField4_Logradouro.getText(), jFormattedTextField1_numero.getText(),
                            jTextField6_Complemento.getText(), jFormattedTextField2_cep.getText(), jTextField9_Cidade.getText(),
                            jComboBox1_Estado.getSelectedItem().toString());
                }
                contato.setTelefone(telefone);
                contato.setEndereco(endereco);
                int confirmacao = JOptionPane.showConfirmDialog(rootPane, "Deseja mesmo Alterar?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if(confirmacao == JOptionPane.YES_OPTION){
                    arquivo.alterar(contato);
                }
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage());
        }
    }//GEN-LAST:event_jButton6_alterarActionPerformed

    // Botão de excluir
    private void jButton8_excluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8_excluirActionPerformed
        // TODO add your handling code here:
        try {
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();

            int selectedRowIndex = jTable1.getSelectedRow();
            int confirmacao = JOptionPane.showConfirmDialog(rootPane, "Deseja mesmo Excluir?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if(confirmacao == JOptionPane.YES_OPTION){
                arquivo.excluir(Integer.parseInt((String) model.getValueAt(selectedRowIndex, 0)));
            }
            // Apaga a linha na table e depois exclui no arquivo
            int i = jTable1.getSelectedRow();
            if(i >= 0){
                model.removeRow(i);
            }else{
                JOptionPane.showMessageDialog(null, "Nenhuma linha foi selecionada!");
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage());
        }
    }//GEN-LAST:event_jButton8_excluirActionPerformed

    private void jButton1_limparDadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1_limparDadosActionPerformed
        // TODO add your handling code here:
        try {
            // Limpa os dados dos textfields
            jTextField1_Nome.setForeground(Color.getHSBColor(0, 0, 0.6f));
            jTextField1_Nome.setText("Insira seu nome...");

            jTextField2_Email.setForeground(Color.getHSBColor(0, 0, 0.6f));
            jTextField2_Email.setText("usuario@gmail.com");

            jFormattedTextField1_telefone.setForeground(Color.getHSBColor(0, 0, 0.6f));
            jFormattedTextField1_telefone.setText("+000 (00) 0 0000-0000");

            jTextField4_Logradouro.setForeground(Color.getHSBColor(0, 0, 0.6f));
            jTextField4_Logradouro.setText("Logradouro");

            jFormattedTextField1_numero.setForeground(Color.getHSBColor(0, 0, 0.6f));
            jFormattedTextField1_numero.setText("Número");

            jFormattedTextField2_cep.setFormatterFactory(null);
            jFormattedTextField2_cep.setForeground(Color.getHSBColor(0, 0, 0.6f));
            jFormattedTextField2_cep.setText("CEP");

            jComboBox1_Estado.setSelectedItem("Estado");

            jTextField9_Cidade.setForeground(Color.getHSBColor(0, 0, 0.6f));
            jTextField9_Cidade.setText("Cidade");

            jTextField6_Complemento.setForeground(Color.getHSBColor(0, 0, 0.6f));
            jTextField6_Complemento.setText("Complemento...");
        
            List<Contato> listaDeContatos;
            listaDeContatos = arquivo.listar();
            //Ordenamento
            listaDeContatos.sort(comparar((Colunas) jComboBox_Ordenar.getSelectedItem()));
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            atualizarTabela(listaDeContatos, model);
        } catch (Exception ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1_limparDadosActionPerformed

    public ArrayList<Contato> consultar(Contato filtro) throws Exception {
        ArrayList<Contato> contatosFiltrados = new ArrayList<>();
        try {
            List<Contato> listaDeContatos = arquivo.listar();
            //Sugestão de Ordenamento
            listaDeContatos.sort(comparar((Colunas) jComboBox_Ordenar.getSelectedItem()));

            for (Contato contato : listaDeContatos) {
                boolean match = true;

                if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
                    if (!contato.getNome().toLowerCase().contains(filtro.getNome().toLowerCase())) {
                        match = false;
                    }
                }

                if (filtro.getTelefone() != null && !filtro.getTelefone().toString().isEmpty()) {
                    if (!contato.getTelefone().toString().contains(filtro.getTelefone().toString())) {
                        match = false;
                    }
                }

                if (filtro.getEmail() != null && !filtro.getEmail().isEmpty()) {
                    if (!contato.getEmail().toLowerCase().contains(filtro.getEmail().toLowerCase())) {
                        match = false;
                    }
                }

                if (filtro.getEndereco().getEstado() != null && !filtro.getEndereco().getEstado().isEmpty()) {
                    if (!contato.getEndereco().getEstado().toLowerCase().contains(filtro.getEndereco().getEstado().toLowerCase())) {
                        match = false;
                    }
                }
                
                if (filtro.getEndereco().getCep()!= null && !filtro.getEndereco().getCep().isEmpty()) {
                    if (!contato.getEndereco().getCep().toLowerCase().contains(filtro.getEndereco().getCep().toLowerCase())) {
                        match = false;
                    }
                }
                
                if (filtro.getEndereco().getComplemento()!= null && !filtro.getEndereco().getComplemento().isEmpty()) {
                    if (!contato.getEndereco().getComplemento().toLowerCase().contains(filtro.getEndereco().getComplemento().toLowerCase())) {
                        match = false;
                    }
                }
                
                if (filtro.getEndereco().getLogradouro()!= null && !filtro.getEndereco().getLogradouro().isEmpty()) {
                    if (!contato.getEndereco().getLogradouro().toLowerCase().contains(filtro.getEndereco().getLogradouro().toLowerCase())) {
                        match = false;
                    }
                }
                
                if (filtro.getEndereco().getNumero()!= null && !filtro.getEndereco().getNumero().isEmpty()) {
                    if (!contato.getEndereco().getNumero().toLowerCase().contains(filtro.getEndereco().getNumero().toLowerCase())) {
                        match = false;
                    }
                }
                
                if (filtro.getEndereco().getCidade()!= null && !filtro.getEndereco().getCidade().isEmpty()) {
                    if (!contato.getEndereco().getCidade().toLowerCase().contains(filtro.getEndereco().getCidade().toLowerCase())) {
                        match = false;
                    }
                }

                if (match) {
                    contatosFiltrados.add(contato);
                }
            }

            if (contatosFiltrados.isEmpty()) {
                throw new Exception("Contato não Encontrado");
            }
        } catch (Exception erro) {
            throw erro;
        }

        return contatosFiltrados;
    }
    
    // Função de filtragem para a consulta
    private void filterTable() throws Exception {
        String nome = "", email = "", telefone = "", logradouro = "", numero = "", cep = "", estado = "", cidade = "", complemento = "";
        if (!jTextField1_Nome.getText().equals("Insira seu nome...")) {
            nome = jTextField1_Nome.getText();
        }
        if (!jTextField2_Email.getText().equals("usuario@gmail.com")) {
            email = jTextField2_Email.getText();
        }
        if (!jFormattedTextField1_telefone.getText().equals("+000 (00) 0 0000-0000")) {
            telefone = jFormattedTextField1_telefone.getText();
        }
        if (!jTextField4_Logradouro.getText().equals("Logradouro")) {
            logradouro = jTextField4_Logradouro.getText();
        }
        if (!jFormattedTextField1_numero.getText().equals("Número")) {
            numero = jFormattedTextField1_numero.getText();
        }
        if (!jFormattedTextField2_cep.getText().equals("CEP")) {
            cep = jFormattedTextField2_cep.getText();
        }
        if (!jComboBox1_Estado.getSelectedItem().toString().equals("Estado")) {
            estado = jComboBox1_Estado.getSelectedItem().toString();
        }
        if (!jTextField9_Cidade.getText().equals("Cidade")) {
            cidade = jTextField9_Cidade.getText();
        }
        if (!jTextField6_Complemento.getText().equals("Complemento...")) {
            complemento = jTextField6_Complemento.getText();
        }

        Telefone telefoneObj = null;
        if (!telefone.isEmpty()) {
            telefoneObj = new Telefone(telefone.substring(0, 3), telefone.substring(3, 5), telefone.substring(5));
        }
        Endereco endereco = new Endereco(logradouro, numero, complemento, cep, cidade, estado);
        Contato contato = new Contato(0, nome, telefoneObj, email, endereco);

        // Pega todos os contatos filtrados e insere na table após deletar a atual
        ArrayList<Contato> listaDeContatos = consultar(contato);
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        for (Contato c : listaDeContatos) {
            String[] saida = {
                c.getId()+"",
                c.getNome(),
                c.getEmail(),
                c.getTelefone().toString(),
                c.getEndereco().getLogradouro(),
                c.getEndereco().getNumero(),
                c.getEndereco().getCep(),
                c.getEndereco().getEstado(),
                c.getEndereco().getCidade(),
                c.getEndereco().getComplemento()
            };
            model.addRow(saida);
        }
    }
    
    // Botão de consulta
    private void jButton3_consultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3_consultarActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        TableRowSorter<DefaultTableModel> object = new TableRowSorter<>(model);
        jTable1.setRowSorter(object);

        // Verifica se entrou no modo consulta
        if(consultarHasBeenClicked){
            consultarHasBeenClicked = false;
            jButton3_consultar.setBackground(Color.getHSBColor(0.1667f, 0.6f, 1));
        } else {
            consultarHasBeenClicked = true;

            // Adiciona documentListener para verificar se algo foi digitado e caso seja então filtra a table
            DocumentListener documentListener = new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (consultarHasBeenClicked) {
                        try {
                            filterTable();
                        } catch (Exception ex) {
                            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (consultarHasBeenClicked) {
                        try {
                            filterTable();
                        } catch (Exception ex) {
                            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (consultarHasBeenClicked) {
                        try {
                            filterTable();
                        } catch (Exception ex) {
                            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };

            jTextField1_Nome.getDocument().addDocumentListener(documentListener);
            jTextField2_Email.getDocument().addDocumentListener(documentListener);
            jFormattedTextField1_telefone.getDocument().addDocumentListener(documentListener);
            jTextField4_Logradouro.getDocument().addDocumentListener(documentListener);
            jFormattedTextField1_numero.getDocument().addDocumentListener(documentListener);
            jFormattedTextField2_cep.getDocument().addDocumentListener(documentListener);
            jComboBox1_Estado.addActionListener(e -> {
                if (consultarHasBeenClicked) {
                    try {
                        filterTable();
                    } catch (Exception ex) {
                        Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            jTextField9_Cidade.getDocument().addDocumentListener(documentListener);
            jTextField6_Complemento.getDocument().addDocumentListener(documentListener);

            jButton3_consultar.setBackground(Color.getHSBColor(0.1667f, 0.6f, 0.55f));
        }
    }//GEN-LAST:event_jButton3_consultarActionPerformed

    // Botão de pdf
    private void jButton7_pdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7_pdfActionPerformed
        try {                                             
            // TODO add your handling code here:
            float marginLeft = 20;
            float marginRight = 20;
            float marginTop = 20;
            float marginBottom = 20;
            Document document = new Document(PageSize.A4, marginLeft, marginRight, marginTop, marginBottom);
            
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
            Path filePath = encontrarCaminhoDoArquivo("TabelaDados.pdf");
            
            try {
                // Criação do PDF
                PdfWriter.getInstance(document, new FileOutputStream(filePath.toFile()));
                document.open();
                
                // Cabeçalho
                document.add(new Paragraph("Dados da Tabela"));
                document.add(new Paragraph(" "));
                
                // Criando a tabela PDF
                PdfPTable pdfTable = new PdfPTable(model.getColumnCount());
                pdfTable.setWidthPercentage(100); // Define a largura da tabela para 100% da área disponível
                
                // Definindo larguras das colunas
                float[] columnWidths = new float[model.getColumnCount()];
                for (int i = 0; i < columnWidths.length; i++) {
                    String columnName = model.getColumnName(i).toLowerCase();
                    switch (columnName) {
                        case "id":
                            columnWidths[i] = 0.6f;
                            break;
                        case "nome":
                            columnWidths[i] = 1.5f;
                            break;
                        case "email":
                            columnWidths[i] = 1.5f;
                            break;
                        case "telefone":
                            columnWidths[i] = 1.8f;
                            break;
                        case "logradouro":
                            columnWidths[i] = 2.3f;
                            break;
                        case "número":
                            columnWidths[i] = 1.6f;
                            break;
                        case "estado":
                            columnWidths[i] = 1.5f;
                            break;
                        case "cidade":
                            columnWidths[i] = 1.5f;
                            break;
                        case "complemento":
                            columnWidths[i] = 3.5f;
                            break;
                        default:
                            columnWidths[i] = 1f;
                            break;
                    }
                }
                pdfTable.setWidths(columnWidths);
                
                // Adicionando os cabeçalhos da tabela
                for (int i = 0; i < model.getColumnCount(); i++) {
                    PdfPCell header = new PdfPCell(new Phrase(model.getColumnName(i)));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER); // Centralizando o texto do cabeçalho
                    pdfTable.addCell(header);
                }
                
                // Adicionando as linhas da tabela
                for (int rows = 0; rows < model.getRowCount(); rows++) {
                    for (int cols = 0; cols < model.getColumnCount(); cols++) {
                        PdfPCell cell = new PdfPCell(new Phrase(model.getValueAt(rows, cols).toString()));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER); // Centralizando o texto das células
                        pdfTable.addCell(cell);
                    }
                }
                
                document.add(pdfTable);
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            } finally {
                document.close();
            }
            
            // Abrindo o PDF criado
            try {
                File pdfFile = new File(filePath.toUri());
                if (pdfFile.exists()) {
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(pdfFile);
                    } else {
                        System.out.println("Abertura automática não suportada no seu sistema.");
                    }
                } else {
                    System.out.println("O arquivo PDF não foi encontrado.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton7_pdfActionPerformed
  
    // Função para consulta de contato na tabela via click
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();

        int selectedRowIndex = jTable1.getSelectedRow();

        jTextField1_Nome.setForeground(Color.BLACK);
        jTextField1_Nome.setText(model.getValueAt(selectedRowIndex, 1).toString());

        jTextField2_Email.setForeground(Color.BLACK);
        jTextField2_Email.setText(model.getValueAt(selectedRowIndex, 2).toString());

        jFormattedTextField1_telefone.setForeground(Color.BLACK);
        jFormattedTextField1_telefone.setText(model.getValueAt(selectedRowIndex, 3).toString());

        jTextField4_Logradouro.setForeground(Color.BLACK);
        jTextField4_Logradouro.setText(model.getValueAt(selectedRowIndex, 4).toString());

        jFormattedTextField1_numero.setForeground(Color.BLACK);
        jFormattedTextField1_numero.setText(model.getValueAt(selectedRowIndex, 5).toString());

        jFormattedTextField2_cep.setForeground(Color.BLACK);
        jFormattedTextField2_cep.setText(model.getValueAt(selectedRowIndex, 6).toString());

        jComboBox1_Estado.setSelectedItem(model.getValueAt(selectedRowIndex, 7).toString());

        jTextField9_Cidade.setForeground(Color.BLACK);
        jTextField9_Cidade.setText(model.getValueAt(selectedRowIndex, 8).toString());

        if(model.getValueAt(selectedRowIndex, 9).toString().compareTo(" ") != 0){
            jTextField6_Complemento.setForeground(Color.BLACK);
            jTextField6_Complemento.setText(model.getValueAt(selectedRowIndex, 9).toString());
        } else {
            jTextField6_Complemento.setForeground(Color.getHSBColor(0, 0, 0.6f));
            jTextField6_Complemento.setText("Complemento...");
        }
        
        nomeAtual = jTextField1_Nome.getText();
    }//GEN-LAST:event_jTable1MouseClicked

    // Eventos para mostrar o sobre mim caso o mouse passe por cima
    private void jLabel1_InfoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1_InfoMouseEntered
        // TODO add your handling code here:
        jPanel1_info.setVisible(true);
        SetImageLabel(jLabel1_Info, "src/imagens/about (1).png");
    }//GEN-LAST:event_jLabel1_InfoMouseEntered

    private void jLabel1_InfoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1_InfoMouseExited
        // TODO add your handling code here:
        jPanel1_info.setVisible(false);
        SetImageLabel(jLabel1_Info, "src/imagens/about.png");
    }//GEN-LAST:event_jLabel1_InfoMouseExited

    // Continuação de alguns focus adicionados posteriormente
    private void jFormattedTextField1_telefoneFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFormattedTextField1_telefoneFocusGained
        // TODO add your handling code here:
        if(jFormattedTextField1_telefone.getText().compareTo("+000 (00) 0 0000-0000") == 0){
            jFormattedTextField1_telefone.setForeground(Color.BLACK);
            jFormattedTextField1_telefone.setText("");
        }
    }//GEN-LAST:event_jFormattedTextField1_telefoneFocusGained

    private void jFormattedTextField1_telefoneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFormattedTextField1_telefoneFocusLost
        // TODO add your handling code here:
       if(jFormattedTextField1_telefone.getText().compareTo("+   (  )       -    ") == 0){
            jFormattedTextField1_telefone.setForeground(Color.getHSBColor(0, 0, 0.6f));
            jFormattedTextField1_telefone.setText("+000 (00) 0 0000-0000");
        }
    }//GEN-LAST:event_jFormattedTextField1_telefoneFocusLost

    private void jFormattedTextField1_telefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField1_telefoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField1_telefoneActionPerformed

    private DefaultFormatterFactory originalFormatterFactory;
    private DefaultFormatterFactory originalFormatterFactoryCEP;
    
    private void jFormattedTextField1_numeroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFormattedTextField1_numeroFocusGained
        // TODO add your handling code here:
        if(jFormattedTextField1_numero.getText().compareTo("Número") == 0){
            jFormattedTextField1_numero.setFormatterFactory(originalFormatterFactory);
            jFormattedTextField1_numero.setText("");
            jFormattedTextField1_numero.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_jFormattedTextField1_numeroFocusGained

    // Eventos de focus para textfields formatados
    private void jFormattedTextField1_numeroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFormattedTextField1_numeroFocusLost
        // TODO add your handling code here:
        if(jFormattedTextField1_numero.getText().compareTo("") == 0){
            jFormattedTextField1_numero.setForeground(Color.getHSBColor(0, 0, 0.6f));
            jFormattedTextField1_numero.setFormatterFactory(null);
            jFormattedTextField1_numero.setText("Número");
        }
    }//GEN-LAST:event_jFormattedTextField1_numeroFocusLost

    private void jFormattedTextField2_cepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFormattedTextField2_cepFocusGained
        // TODO add your handling code here:
        if (jFormattedTextField2_cep.getText().compareTo("CEP") == 0) {
            jFormattedTextField2_cep.setFormatterFactory(originalFormatterFactoryCEP);
            jFormattedTextField2_cep.setText("");
            jFormattedTextField2_cep.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_jFormattedTextField2_cepFocusGained

    private void jFormattedTextField2_cepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFormattedTextField2_cepFocusLost
        // TODO add your handling code here:
        if (jFormattedTextField2_cep.getText().compareTo("     -   ") == 0){
            jFormattedTextField2_cep.setForeground(Color.getHSBColor(0, 0, 0.6f));
            jFormattedTextField2_cep.setFormatterFactory(null);
            jFormattedTextField2_cep.setText("CEP");
        }
    }//GEN-LAST:event_jFormattedTextField2_cepFocusLost

    private void jTextField1_NomeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1_NomeKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1_NomeKeyTyped

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1_incluir;
    private javax.swing.JButton jButton1_limparDados;
    private javax.swing.JButton jButton3_consultar;
    private javax.swing.JButton jButton6_alterar;
    private javax.swing.JButton jButton7_pdf;
    private javax.swing.JButton jButton8_excluir;
    private javax.swing.JComboBox<String> jComboBox1_Estado;
    private javax.swing.JComboBox<Colunas> jComboBox_Ordenar;
    private javax.swing.JFormattedTextField jFormattedTextField1_numero;
    private javax.swing.JFormattedTextField jFormattedTextField1_telefone;
    private javax.swing.JFormattedTextField jFormattedTextField2_cep;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel1_Info;
    private javax.swing.JLabel jLabel1_Logo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel2_derrel;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6_lucas;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1_info;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1_Nome;
    private javax.swing.JTextField jTextField2_Email;
    private javax.swing.JTextField jTextField4_Logradouro;
    private javax.swing.JTextField jTextField6_Complemento;
    private javax.swing.JTextField jTextField9_Cidade;
    // End of variables declaration//GEN-END:variables
}
