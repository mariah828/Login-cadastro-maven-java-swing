package Principal;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.mindrot.jbcrypt.BCrypt;

public class TelaPrincipal {
	
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
    
    public static boolean pagamentoValido(String cpf, String infoCartao, String cvvCartao) {

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
    
 public static boolean senhaValida(String senha) {
    	
    	if (senha.isBlank()) {
        	
            JOptionPane.showMessageDialog(null, "Digite uma senha válida!");
            return false;
            
        }
    	
    	return true;
    }
    
    public static boolean emailValido(String email) {
    	
    	if (email.isEmpty()) {
        	
            JOptionPane.showMessageDialog(null, "Digite um email e senha!");
            return false;
            
        } else if (email.contains(";")) {
        	
        	JOptionPane.showMessageDialog(null, "Digite um email válido!");
        	return false;
        	
        } else if (!email.contains("@")) {
        	
        	JOptionPane.showMessageDialog(null, "Digite um email válido!");
        	return false;
        	
        } else if (!email.contains(".com")) {
        	
        	JOptionPane.showMessageDialog(null, "Digite um email válido!");
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

        URL urlImagem = TelaPrincipal.class.getResource("/imagens/" + arquivo);

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
    	
    	boolean premium = false;
    	for (Usuario u : lista) {
    	    if (u.getEmail().equalsIgnoreCase(email) && "Premium".equalsIgnoreCase(u.getPlano())) {
    	        premium = true;
    	        break;
    	    }
    	}

    	painel.setPreferredSize(new Dimension(684, premium ? 760 : 560));
    	
    	JButton voltarTelaFilmes = criarVoltar();
    	voltarTelaFilmes.addActionListener(eventoVoltar -> mostrarTelaLogin(painel, janela));
    	
    	painel.removeAll();
    	
    	painel.add(lblReflecTV0);
    	painel.add(voltarTelaFilmes);
    	
    	if (email.equalsIgnoreCase("mariahlima828@gmail.com")) {
    		JButton btnBuscar = criarBotao("Buscar", 579, 510, Color.WHITE, Color.BLACK);
    		btnBuscar.setFont(new Font("Arial", Font.PLAIN, 14));
    		btnBuscar.setBounds(579, 510, 80, 30);
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
        
        if (premium) {
            int xPremium = (684 - (3 * LARGURA_FILME + 2 * ESPACO)) / 2;
            int yPremium = 80 + 2 * (ALTURA_FILME + ESPACO);

            for (int i = 0; i < 3; i++) {
                int x = xPremium + i * (LARGURA_FILME + ESPACO);
                adicionarFilme(painel, "filme" + (i + 9) + ".jpg", x, yPremium);
            }
        }
    		
    	painel.revalidate();
    	painel.repaint();
    }
    
    public void mostrarTelaPlano(JPanel painel, JFrame janela, String email, String senha) {
    	
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
    	
    	JButton voltarPlano = criarVoltar();
    	voltarPlano.addActionListener(eventoVoltarPlano -> mostrarTelaLogin(painel, janela));

        painel.removeAll();

        painel.add(lblReflecTV2);
        painel.add(lblNome);
        painel.add(txtNome);
        painel.add(lblOpcPlano);
        painel.add(opcSimPlano);
        painel.add(opcNaoPlano);
        painel.add(btnCadastrarNome);
        painel.add(voltarPlano);
        
        painel.setPreferredSize(new Dimension(684, 560));

        painel.revalidate();
        painel.repaint();

        btnCadastrarNome.addActionListener(eventoNome -> {

            String nome = txtNome.getText().trim();
            String plano = "Nenhum";
            
            if (nome.isEmpty()) { 
            	JOptionPane.showMessageDialog(null, "Digite o nome!"); 
            	return; 
            }
            
            if (!opcSimPlano.isSelected() && !opcNaoPlano.isSelected()) { 
            	JOptionPane.showMessageDialog(null, "Escolha uma opção de plano!"); 
            	return; 
            }

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
            	
            	JButton voltarCartao = criarVoltar();
            	voltarCartao.addActionListener(eventoVoltar -> mostrarTelaPlano(painel, janela, email, senha));

                painel.removeAll();

                painel.add(lblReflecTV3);
                painel.add(lblInfoCartao);
                painel.add(txtInfoCartao);
                painel.add(lblCpf);
                painel.add(txtCpf);
                painel.add(lblCvvCartao);
                painel.add(txtCvvCartao);
                painel.add(btnCadastrarCartao);
                painel.add(voltarCartao);
                
                painel.setPreferredSize(new Dimension(684, 560));

                painel.revalidate();
                painel.repaint();

                btnCadastrarCartao.addActionListener(eventoCartao -> {

                    String cpf = txtCpf.getText().trim();
                    String infoCartao = txtInfoCartao.getText().trim();
                    String cvvCartao = txtCvvCartao.getText().trim();

                    if (pagamentoValido(cpf, infoCartao, cvvCartao)) {
                        u.setPlano("Premium");
                        try {
                            csv.gravar(u);
                            lista.add(u);
                            JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
                            mostrarTelaFilmes(painel, janela, email);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Erro ao salvar o cadastro: " + ex.getMessage());
                        }
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
        
        painel.setPreferredSize(new Dimension(684, 560));

        painel.revalidate();
        painel.repaint();
        
        btnLogin.addActionListener(eventoLogin -> {

            String email = txtEmail.getText().trim();
            String senha = new String(txtSenha.getPassword());
 
            if (emailValido(email) && senhaValida(senha)) {
            	
            	boolean encontrou = false;

                for (Usuario y : lista) {

                	if (y.getEmail().equalsIgnoreCase(email)) {
                	    if (BCrypt.checkpw(senha, y.getSenha())) {
                	        mostrarTelaFilmes(painel, janela, email);
                	    } else {
                	        JOptionPane.showMessageDialog(null, "Senha incorreta. Tente novamente!");
                	    }
                	    encontrou = true;
                	    break;
                	}
                }
                
                if (!encontrou) {
                	
                    JOptionPane.showMessageDialog(null, "Informações incorretas. Tente novamente!");

                }
            }

        });

        btnCadastrar.addActionListener(eventoCadastro -> {

            String email = txtEmail.getText().trim();
            String senha = new String(txtSenha.getPassword());

            boolean emailExisteCadastro = false;

            if (emailValido(email) && senhaValida(senha)) {

                for (Usuario y : lista) {

                    if (y.getEmail().equalsIgnoreCase(email)) {
                        JOptionPane.showMessageDialog(null, "Esse email já foi cadastrado. Tente novamente!");
                        emailExisteCadastro = true;
                        break;
                    }
                }

                if (!emailExisteCadastro) {
                	
                	mostrarTelaPlano(painel, janela, email, senha);
                	
                }
            }
        });
    }


    public void mostrarTelaBusca(JPanel painel, JFrame janela, String email) {
    	
        JLabel lblTitulo = criarTitulo(120, 70);
        JLabel lblProcurar = criarLabel("Email:", 142, 240, 100);
        JTextField txtProcurar = criarCampo(242, 240, 300);
        JButton btnProcurar = criarBotao("Procurar", 243, 310, VERMELHO, Color.WHITE);
        JButton btnListar = criarBotao("Listar all", 407, 310, Color.WHITE, Color.BLACK);
        JButton btnAlterar = criarBotao("Alterar", 243, 380, Color.WHITE, Color.BLACK);
        JButton btnExcluir = criarBotao("Excluir", 407, 380, VERMELHO, Color.WHITE);
        
        JButton voltarBusca = criarVoltar();
        voltarBusca.addActionListener(e -> mostrarTelaFilmes(painel, janela, email));

        painel.removeAll();

        painel.add(lblTitulo);
        painel.add(lblProcurar);
        painel.add(txtProcurar);
        painel.add(btnProcurar);
        painel.add(voltarBusca);
        painel.add(btnListar);   
        painel.add(btnAlterar);
        painel.add(btnExcluir);
        painel.setPreferredSize(new Dimension(684, 560));

        painel.revalidate();
        painel.repaint();
        
        btnListar.addActionListener(e -> {

            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum usuário cadastrado.");
                return;
            }

            StringBuilder sb = new StringBuilder();
            for (Usuario x : lista) {
                sb.append(x.getNome()).append(" - ")
                  .append(x.getEmail()).append(" - ")
                  .append(x.getPlano()).append("\n");
            }

            JTextArea area = new JTextArea(sb.toString());
            area.setEditable(false);

            JScrollPane rolagem = new JScrollPane(area);
            rolagem.setPreferredSize(new Dimension(450, 250));

            JOptionPane.showMessageDialog(null, rolagem,
                    "Usuários cadastrados (" + lista.size() + ")",
                    JOptionPane.PLAIN_MESSAGE);
        });

        btnProcurar.addActionListener(e -> {

            String procurarEmail = txtProcurar.getText().trim();

            if (procurarEmail.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Digite um email para procurar!");
                return;
            }

            Usuario achado = null;

            for (Usuario x : lista) {
            	
                if (x.getEmail().equalsIgnoreCase(procurarEmail)) {
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
        
        btnAlterar.addActionListener(e -> {

            String procurarEmail = txtProcurar.getText().trim();

            Usuario achado = null;
            for (Usuario x : lista) {
                if (x.getEmail().equalsIgnoreCase(procurarEmail)) {
                    achado = x;
                    break;
                }
            }

            if (achado == null) {
                JOptionPane.showMessageDialog(null, "Digite o email de um usuário existente!");
                return;
            }

            String novoNome = JOptionPane.showInputDialog(null, "Novo nome:", achado.getNome());

            if (novoNome == null) {
                return; // clicou em cancelar
            }

            novoNome = novoNome.trim();

            if (novoNome.isEmpty() || novoNome.contains(";")) {
                JOptionPane.showMessageDialog(null, "Nome inválido!");
                return;
            }

            String[] opcoes = {"Premium", "Nenhum"};
            int escolha = JOptionPane.showOptionDialog(null, "Plano:", "Alterar plano",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, opcoes, achado.getPlano());

            if (escolha == JOptionPane.CLOSED_OPTION) {
                return;
            }

            achado.setNome(novoNome);
            achado.setPlano(opcoes[escolha]);

            try {
                csv.regravarTodos(lista);
                JOptionPane.showMessageDialog(null, "Usuário atualizado!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex.getMessage());
            }
        });

        btnExcluir.addActionListener(e -> {

            String procurarEmail = txtProcurar.getText().trim();

            if (procurarEmail.equalsIgnoreCase("mariahlima828@gmail.com")) {
                JOptionPane.showMessageDialog(null, "Não é possível excluir o administrador!");
                return;
            }

            Usuario achado = null;
            for (Usuario x : lista) {
                if (x.getEmail().equalsIgnoreCase(procurarEmail)) {
                    achado = x;
                    break;
                }
            }

            if (achado == null) {
                JOptionPane.showMessageDialog(null, "Digite o email de um usuário existente!");
                return;
            }

            int confirma = JOptionPane.showConfirmDialog(null,
                    "Excluir " + achado.getNome() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);

            if (confirma != JOptionPane.YES_OPTION) {
                return;
            }

            lista.remove(achado);

            try {
                csv.regravarTodos(lista);
                JOptionPane.showMessageDialog(null, "Usuário excluído!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex.getMessage());
            }
        });
    }

}
