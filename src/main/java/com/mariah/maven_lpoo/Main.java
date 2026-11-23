package com.mariah.maven_lpoo;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JPasswordField;

import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import java.awt.Image;
import java.awt.Point;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.awt.Color;

import java.io.IOException;

public class Main {

    UsuarioCsv csv = new UsuarioCsv();
    ArrayList<Usuario> lista = csv.ler();
    static final int LARGURA_FILME = 130;
    static final int ALTURA_FILME = 195;
    static final int ESPACO = 30;
    static final Color VERMELHO = new Color(229, 9, 20);
    static final Font FONTE = new Font("Arial", Font.PLAIN, 18);

    public static JLabel criarTitulo(int y, int altura) {
        JLabel lbl = new JLabel("ReflecTV", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 48));
        lbl.setForeground(VERMELHO);
        lbl.setBounds(0, y, 684, altura);
        return lbl;
    }

    public static JLabel criarLabel(String texto, int x, int y, int largura) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FONTE);
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(x, y, largura, 40);
        return lbl;
    }

    public static JTextField criarCampo(int x, int y, int largura) {
        JTextField txt = new JTextField();
        txt.setFont(FONTE);
        txt.setBounds(x, y, largura, 40);
        return txt;
    }

    public static JButton criarBotao(String texto, int x, int y, Color fundo, Color letra) {
        JButton btn = new JButton(texto);
        btn.setFont(FONTE);
        btn.setBackground(fundo);
        btn.setForeground(letra);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setBounds(x, y, 150, 45);
        return btn;
    }
    
    public static JButton criarVoltar() {
        JButton btn = new JButton("<");
        btn.setBackground(VERMELHO);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setBounds(25, 510, 50, 30);
        return btn;
    }
    
    public static JPasswordField criarCampoSenha(int x, int y, int largura) {
        JPasswordField txt = new JPasswordField();
        txt.setFont(FONTE);
        txt.setBounds(x, y, largura, 40);
        return txt;
    }
    
    public static boolean cpfValido(String cpf, String infoCartao, String cvvCartao) {

        if (!cpf.matches("\\d{11}")) {
            JOptionPane.showMessageDialog(null, "CPF inválido!");

            return false;

        } else if (!infoCartao.matches("\\d{16}")) {
            JOptionPane.showMessageDialog(null, "Cartão inválido!");
            return false;

        } else if (!cvvCartao.matches("\\d{3}")) {
            JOptionPane.showMessageDialog(null, "CVV inválido!");
            return false;
        }

        return true;
    }

    public static void adicionarFilme(JPanel painel, String arquivo, int x, int y) {

        JPanel cartao = new JPanel(null);
        cartao.setBounds(x, y, LARGURA_FILME, ALTURA_FILME);
        cartao.setOpaque(false);
        
        int largBtn = 80;
        int altBtn = 35;

        JButton btnAssistir = new JButton("▶");
        btnAssistir.setBounds((LARGURA_FILME - largBtn) / 2, (ALTURA_FILME - altBtn) / 2, largBtn, altBtn);
        btnAssistir.setBackground(Color.WHITE);
        btnAssistir.setForeground(Color.BLACK);
        btnAssistir.setOpaque(true);
        btnAssistir.setBorderPainted(false);
        btnAssistir.setFocusPainted(false);
        btnAssistir.setVisible(false);

        JLabel lblFoto = new JLabel();
        lblFoto.setBounds(0, 0, LARGURA_FILME, ALTURA_FILME);

        URL urlImagem = Main.class.getResource("/imagens/" + arquivo);

        if (urlImagem != null) {
            ImageIcon iconeOriginal = new ImageIcon(urlImagem);
            Image imagemAjustada = iconeOriginal.getImage().getScaledInstance(LARGURA_FILME, ALTURA_FILME, Image.SCALE_SMOOTH);
            lblFoto.setIcon(new ImageIcon(imagemAjustada));
        } else {
            lblFoto.setText("Sem imagem");
            lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
        }

        cartao.add(btnAssistir);
        cartao.add(lblFoto);

        MouseAdapter hover = new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                btnAssistir.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Point p = SwingUtilities.convertPoint((Component) e.getSource(), e.getPoint(), cartao);

                if (!cartao.contains(p)) {
                    btnAssistir.setVisible(false);
                }
            }
        };

        cartao.addMouseListener(hover);
        lblFoto.addMouseListener(hover);
        btnAssistir.addMouseListener(hover);

        painel.add(cartao);
    }
    
    public void mostrarTelaFilmes(JPanel painel, JFrame janela, String email){
    	
    	JLabel lblReflecTV0 = criarTitulo(15, 60);
    	
    	JButton voltar = criarVoltar();
    	voltar.addActionListener(eventoVoltar -> mostrarTelaLogin(painel, janela));
    	
    	painel.removeAll();
    	
    	painel.add(lblReflecTV0);
    	painel.add(voltar);
    	
    	if (email.equalsIgnoreCase("mariahlima828@gmail.com")) {
    		JButton btnBuscar = criarBotao("Buscar", 497, 502, Color.WHITE, Color.BLACK);
    		btnBuscar.addActionListener(eventoBuscar -> mostrarTelaBusca(painel, janela, email));
    		painel.add(btnBuscar);
    	}
    		
    	int colunas = 4;
        int larguraTotal = colunas * LARGURA_FILME + (colunas - 1) * ESPACO;
        int xInicial = (684 - larguraTotal) / 2;

        for (int i = 0; i < 8; i++) {
            int x = xInicial + (i % colunas) * (LARGURA_FILME + ESPACO);
            int y = 80 + (i / colunas) * (ALTURA_FILME + ESPACO);
            adicionarFilme(painel, "filme" + (i + 1) + ".jpg", x, y);
        }
    		
    	painel.revalidate();
    	painel.repaint();
    }

    public void mostrarTelaLogin (JPanel painel, JFrame janela) {

    	JLabel lblReflecTV = criarTitulo(120, 70);

    	JLabel lblEmail = criarLabel("Email:", 142, 240, 100);
    	JTextField txtEmail = criarCampo(242, 240, 300);

    	JLabel lblSenha = criarLabel("Senha:", 142, 300, 100);
    	JPasswordField txtSenha = criarCampoSenha(242, 300, 300);

    	JButton btnLogin = criarBotao("Login", 182, 380, VERMELHO, Color.WHITE);
    	JButton btnCadastrar = criarBotao("Cadastrar", 352, 380, Color.WHITE, Color.BLACK);
        
        painel.removeAll();

        painel.add(lblReflecTV);
        painel.add(lblEmail);
        painel.add(txtEmail);
        painel.add(lblSenha);
        painel.add(txtSenha);
        painel.add(btnCadastrar);
        painel.add(btnLogin);

        painel.revalidate();
        painel.repaint();
        
        btnLogin.addActionListener(eventoLogin -> {

            String email = txtEmail.getText().trim();
            String senha = new String(txtSenha.getPassword());
 
            if (email.isEmpty()) {
            	
                JOptionPane.showMessageDialog(null, "Digite um email e senha!");
                
            } else if (email.contains(";")) {
            	
            	JOptionPane.showMessageDialog(null, "Digite um email válido!");
            	
            } else if (!email.contains("@")) {
            	
            	JOptionPane.showMessageDialog(null, "Digite um email válido!");
            	
            } else if (!email.contains(".com")) {
            	
            	JOptionPane.showMessageDialog(null, "Digite um email válido!");
            	
            } else {
            	
            	boolean encontrou = false;

                for (Usuario y : lista) {

                    if (y.getEmail().equals(email) && !BCrypt.checkpw(senha, y.getSenha())) {
                        JOptionPane.showMessageDialog(null, "Senha incorreta. Tente novamente!");
                        encontrou = true;
                        break;
                    } else if (y.getEmail().equals(email) && BCrypt.checkpw(senha, y.getSenha())) {
                    	mostrarTelaFilmes(painel, janela, email);
                    	encontrou = true;
                    	break;
                    }
                }
                
                if (encontrou == false) {
                	
                    JOptionPane.showMessageDialog(null, "Informações incorretas. Tente novamente!");

                }
            }

        });

        btnCadastrar.addActionListener(eventoCadastro -> {

            String email = txtEmail.getText().trim();
            String senha = new String(txtSenha.getPassword());

            boolean emailExisteCadastro = false;
            
            if (email.isEmpty()) {
            	
                JOptionPane.showMessageDialog(null, "Digite um email e senha!");
                
            } else if (email.contains(";")) {
            	
            	JOptionPane.showMessageDialog(null, "Digite um email válido!");
            	
            } else if (!email.contains("@")) {
            	
            	JOptionPane.showMessageDialog(null, "Digite um email válido!");
            	
            } else if (!email.contains(".com")) {
            	
            	JOptionPane.showMessageDialog(null, "Digite um email válido!");
            	
            } else {

                for (Usuario y : lista) {

                    if (y.getEmail().equals(email) && !BCrypt.checkpw(senha, y.getSenha())) {
                        JOptionPane.showMessageDialog(null, "Esse email já foi cadastrado. Tente novamente!");
                        emailExisteCadastro = true;
                        break;
                    }
                }

                if (!emailExisteCadastro) {

                	JLabel lblReflecTV2 = criarTitulo(120, 70);

                	JLabel lblNome = criarLabel("Digite o nome:", 92, 240, 200);
                	JTextField txtNome = criarCampo(292, 240, 300);

                	JLabel lblOpcPlano = criarLabel("Deseja adicionar plano?", 92, 300, 250);

                	JRadioButton opcSimPlano = new JRadioButton("Sim");
                	opcSimPlano.setFont(FONTE);
                	opcSimPlano.setForeground(Color.WHITE);
                	opcSimPlano.setOpaque(false);
                	opcSimPlano.setBounds(352, 305, 80, 30);

                	JRadioButton opcNaoPlano = new JRadioButton("Não");
                	opcNaoPlano.setFont(FONTE);
                	opcNaoPlano.setForeground(Color.WHITE);
                	opcNaoPlano.setOpaque(false);
                	opcNaoPlano.setBounds(442, 305, 80, 30);

                	ButtonGroup grupo = new ButtonGroup();
                	grupo.add(opcSimPlano);
                	grupo.add(opcNaoPlano);

                	JButton btnCadastrarNome = criarBotao("Cadastrar", 267, 380, VERMELHO, Color.WHITE);

                    painel.removeAll();

                    painel.add(lblReflecTV2);
                    painel.add(lblNome);
                    painel.add(txtNome);
                    painel.add(lblOpcPlano);
                    painel.add(opcSimPlano);
                    painel.add(opcNaoPlano);
                    painel.add(btnCadastrarNome);

                    painel.revalidate();
                    painel.repaint();

                    btnCadastrarNome.addActionListener(eventoNome -> {

                        String nome = txtNome.getText().trim();
                        String plano = "Nenhum";

                        Usuario u = new Usuario();

                        u.setNome(nome);
                        u.setEmail(email);
                        u.setSenha(BCrypt.hashpw(senha, BCrypt.gensalt()));

                        if (opcSimPlano.isSelected()) {

                        	JLabel lblReflecTV3 = criarTitulo(120, 70);

                        	JLabel lblInfoCartao = criarLabel("Número do cartão:", 92, 220, 220);
                        	JTextField txtInfoCartao = criarCampo(312, 220, 280);

                        	JLabel lblCpf = criarLabel("CPF:", 92, 280, 220);
                        	JTextField txtCpf = criarCampo(312, 280, 280);

                        	JLabel lblCvvCartao = criarLabel("CVC/CVV do cartão:", 92, 340, 220);
                        	JTextField txtCvvCartao = criarCampo(312, 340, 280);

                        	JButton btnCadastrarCartao = criarBotao("Cadastrar", 267, 420, VERMELHO, Color.WHITE);

                            painel.removeAll();

                            painel.add(lblReflecTV3);
                            painel.add(lblInfoCartao);
                            painel.add(txtInfoCartao);
                            painel.add(lblCpf);
                            painel.add(txtCpf);
                            painel.add(lblCvvCartao);
                            painel.add(txtCvvCartao);
                            painel.add(btnCadastrarCartao);

                            painel.revalidate();
                            painel.repaint();

                            btnCadastrarCartao.addActionListener(eventoCartao -> {

                                String cpf = txtCpf.getText().trim();
                                String infoCartao = txtInfoCartao.getText().trim();
                                String cvvCartao = txtCvvCartao.getText().trim();

                                if (cpfValido(cpf, infoCartao, cvvCartao)) {
                                    u.setPlano("Premium");
                                    try {
                                        csv.gravar(u);
                                        lista.add(u);
                                        JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
                                        mostrarTelaFilmes(painel, janela, email);
                                    } catch (IOException ex) {
                                        JOptionPane.showMessageDialog(null, "Erro ao salvar o cadastro: " + ex.getMessage());
                                    }
                                } else {
                                    return;
                                }
                            });

                        } else if (opcNaoPlano.isSelected()) {

                            u.setPlano(plano);
                            
                            try {
                            	
                                csv.gravar(u);
                                lista.add(u);
                                JOptionPane.showMessageDialog(null, "Cadastro realizado!");
                                mostrarTelaFilmes(painel, janela, email);
                                
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(null, "Erro ao salvar o cadastro: " + ex.getMessage());
                            }
                        }
                    });
                }
            }
        });
    }


    public void mostrarTelaBusca(JPanel painel, JFrame janela, String email) {
    	
        JLabel lblTitulo = criarTitulo(120, 70);
        JLabel lblProcurar = criarLabel("Email:", 142, 240, 100);
        JTextField txtProcurar = criarCampo(242, 240, 300);
        JButton btnProcurar = criarBotao("Procurar", 267, 310, VERMELHO, Color.WHITE);

        JButton voltar = criarVoltar();
        voltar.addActionListener(e -> mostrarTelaFilmes(painel, janela, email));

        painel.removeAll();

        painel.add(lblTitulo);
        painel.add(lblProcurar);
        painel.add(txtProcurar);
        painel.add(btnProcurar);
        painel.add(voltar);

        painel.revalidate();
        painel.repaint();

        btnProcurar.addActionListener(e -> {

            String procurarEmail = txtProcurar.getText().trim();

            if (procurarEmail.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Digite um email para procurar!");
                return;
            }

            Usuario achado = null;

            for (Usuario x : lista) {
            	
                if (x.getEmail().equals(procurarEmail)) {
                    achado = x;
                    break;
                }
            }

            if (achado == null) {
                JOptionPane.showMessageDialog(null, "Usuário não encontrado.");
            } else {
                JOptionPane.showMessageDialog(null, "Nome: " + achado.getNome() + "\nPlano: " + achado.getPlano());
            }
        });
    }

    public static void main(String[] args) {

        Main app = new Main();
        
        JFrame janela = new JFrame("Cadastro");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(700, 600);
        janela.setLocationRelativeTo(null);
        
        JPanel painel = new JPanel();
        painel.setLayout(null);  
        painel.setBackground(new Color(20, 20, 20));
        janela.add(painel);

        app.mostrarTelaLogin(painel, janela);     
   
        janela.setVisible(true);
        
    }
}