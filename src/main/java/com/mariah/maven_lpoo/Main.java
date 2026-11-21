package com.mariah.maven_lpoo;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

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

public class Main {

    UsuarioCsv csv = new UsuarioCsv();
    ArrayList<Usuario> lista = csv.ler();

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
        cartao.setBounds(x, y, 100, 150);
        cartao.setOpaque(false);

        JButton btnAssistir = new JButton("▶");
        btnAssistir.setBounds(15, 60, 70, 30);
        btnAssistir.setBackground(Color.WHITE);
        btnAssistir.setForeground(Color.BLACK);
        btnAssistir.setOpaque(true);
        btnAssistir.setBorderPainted(false);
        btnAssistir.setFocusPainted(false);
        btnAssistir.setVisible(false);

        JLabel lblFoto = new JLabel();
        lblFoto.setBounds(0, 0, 100, 150);

        URL urlImagem = Main.class.getResource("/imagens/" + arquivo);

        if (urlImagem != null) {
            ImageIcon iconeOriginal = new ImageIcon(urlImagem);
            Image imagemAjustada = iconeOriginal.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
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

    public void mostrarTelaBusca(JPanel painel) {

        JLabel lblProcurarEmail = new JLabel("Digite um email para procurar:");
        lblProcurarEmail.setBounds(27, 90, 180, 25);

        JTextField txtProcurarEmail = new JTextField();
        txtProcurarEmail.setBounds(207, 90, 200, 25);

        JButton btnProcurar = new JButton("Procurar");
        btnProcurar.setBounds(157, 140, 120, 30);

        painel.removeAll();

        painel.add(lblProcurarEmail);
        painel.add(txtProcurarEmail);
        painel.add(btnProcurar);

        painel.revalidate();
        painel.repaint();

        btnProcurar.addActionListener(e -> {

            String procurarEmail = txtProcurarEmail.getText();
            boolean encontrou = false;

            for (Usuario x : lista) {

                if (x.getEmail().equals(procurarEmail)) {
                    System.out.println("Encontrado: " + x.getNome() + " - " + x.getPlano());
                    encontrou = true;
                    break;
                }
            }

            if (!encontrou) {
                System.out.println("Não encontrado");
            }

            for (Usuario x : lista) {
                System.out.println(x.getNome() + " - " + x.getEmail() + " - " + x.getSenha());
            }

        });
    }

    public static void main(String[] args) {

        Main app = new Main();

        JFrame janela = new JFrame("Cadastro");
        janela.setSize(450, 300);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);

        JLabel lblReflecTV = new JLabel("ReflecTV", SwingConstants.CENTER);
        lblReflecTV.setFont(new Font("Arial", Font.BOLD, 28));
        lblReflecTV.setBounds(0, 15, 434, 40);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(77, 80, 150, 25);

        JTextField txtEmail = new JTextField();
        txtEmail.setBounds(137, 80, 220, 25);

        JLabel lblSenha = new JLabel("Senha: ");
        lblSenha.setBounds(77, 120, 150, 25);

        JTextField txtSenha = new JTextField();
        txtSenha.setBounds(137, 120, 220, 25);

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(230, 180, 120, 30);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(90, 180, 120, 30);

        painel.add(lblReflecTV);
        painel.add(lblEmail);
        painel.add(txtEmail);
        painel.add(lblSenha);
        painel.add(txtSenha);
        painel.add(btnCadastrar);
        painel.add(btnLogin);

        janela.add(painel);
        janela.setVisible(true);

        btnLogin.addActionListener(eventoLogin -> {

            String email = txtEmail.getText();
            String senha = txtSenha.getText();

            boolean emailExisteLogin = false;

            if (email.isBlank() || email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Digite um email e senha!");
            } else {

                for (Usuario y : app.lista) {

                    if (y.getEmail().equals(email) && !BCrypt.checkpw(senha, y.getSenha())) {
                        JOptionPane.showMessageDialog(null, "Senha incorreta. Tente novamente!");
                        emailExisteLogin = true;
                        janela.dispose();
                        break;
                    } else if (y.getEmail().equals(email) && BCrypt.checkpw(senha, y.getSenha())) {

                        if (!emailExisteLogin) {

                            janela.setSize(550, 450);
                            janela.setLocationRelativeTo(null);

                            JLabel lblReflecTV0 = new JLabel("ReflecTV", SwingConstants.CENTER);
                            lblReflecTV0.setFont(new Font("Arial", Font.BOLD, 28));
                            lblReflecTV0.setBounds(0, 15, 534, 40);

                            painel.removeAll();

                            painel.add(lblReflecTV0);

                            for (int i = 0; i < 8; i++) {
                                adicionarFilme(painel, "filme" + (i + 1) + ".jpg", 37 + (i % 4) * 120, 70 + (i / 4) * 170);
                            }

                            painel.revalidate();
                            painel.repaint();

                        }

                    }
                }
            }

        });

        btnCadastrar.addActionListener(eventoCadastro -> {

            String email = txtEmail.getText();
            String senha = txtSenha.getText();

            boolean emailExisteCadastro = false;

            if (email.isBlank() || email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Digite um email e senha!");
            } else {

                for (Usuario y : app.lista) {

                    if (y.getEmail().equals(email) && !BCrypt.checkpw(senha, y.getSenha())) {
                        JOptionPane.showMessageDialog(null, "Esse email já foi cadastrado. Tente novamente!");
                        emailExisteCadastro = true;
                        janela.dispose();
                        break;
                    }
                }

                if (!emailExisteCadastro) {

                    JLabel lblReflecTV2 = new JLabel("ReflecTV", SwingConstants.CENTER);
                    lblReflecTV2.setFont(new Font("Arial", Font.BOLD, 28));
                    lblReflecTV2.setBounds(0, 15, 434, 40);

                    JLabel lblNome = new JLabel("Digite o nome:");
                    lblNome.setBounds(42, 80, 150, 25);

                    JTextField txtNome = new JTextField();
                    txtNome.setBounds(182, 80, 200, 25);

                    JLabel lblOpcPlano = new JLabel("Deseja adicionar plano?");
                    lblOpcPlano.setBounds(42, 120, 150, 25);

                    JRadioButton opcSimPlano = new JRadioButton("Sim");
                    opcSimPlano.setBounds(230, 120, 60, 25);

                    JRadioButton opcNaoPlano = new JRadioButton("Não");
                    opcNaoPlano.setBounds(302, 120, 70, 25);

                    ButtonGroup grupo = new ButtonGroup();

                    grupo.add(opcSimPlano);
                    grupo.add(opcNaoPlano);

                    JButton btnCadastrarNome = new JButton("Cadastrar");
                    btnCadastrarNome.setBounds(157, 180, 120, 30);

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

                        String nome = txtNome.getText();
                        String plano = "Nenhum";

                        Usuario u = new Usuario();

                        u.setNome(nome);
                        u.setEmail(email);
                        u.setSenha(BCrypt.hashpw(senha, BCrypt.gensalt()));

                        if (opcSimPlano.isSelected()) {

                            JLabel lblReflecTV3 = new JLabel("ReflecTV", SwingConstants.CENTER);
                            lblReflecTV3.setFont(new Font("Arial", Font.BOLD, 28));
                            lblReflecTV3.setBounds(0, 15, 434, 40);

                            JLabel lblInfoCartao = new JLabel("Número do cartão: ");
                            lblInfoCartao.setBounds(42, 80, 150, 25);

                            JTextField txtInfoCartao = new JTextField();
                            txtInfoCartao.setBounds(207, 80, 200, 25);

                            JLabel lblCpf = new JLabel("CPF: ");
                            lblCpf.setBounds(42, 120, 150, 25);

                            JTextField txtCpf = new JTextField();
                            txtCpf.setBounds(207, 120, 200, 25);

                            JLabel lblCvvCartao = new JLabel("CVC/CVV do cartão: ");
                            lblCvvCartao.setBounds(42, 160, 150, 25);

                            JTextField txtCvvCartao = new JTextField();
                            txtCvvCartao.setBounds(207, 160, 200, 25);

                            JButton btnCadastrarCartao = new JButton("Cadastrar");
                            btnCadastrarCartao.setBounds(157, 210, 120, 30);

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

                                String cpf = txtCpf.getText();
                                String infoCartao = txtInfoCartao.getText();
                                String cvvCartao = txtCvvCartao.getText();

                                if (cpfValido(cpf, infoCartao, cvvCartao)) {
                                    u.setPlano("Premium");
                                    app.csv.gravar(u);
                                    app.lista.add(u);
                                    painel.removeAll();
                                    JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
                                    painel.removeAll();                                   
                                } else {
                                    return;
                                }
                            });

                        } else if (opcNaoPlano.isSelected()) {

                            u.setPlano(plano);
                            app.csv.gravar(u);
                            app.lista.add(u);

                            JOptionPane.showMessageDialog(null, "Cadastro realizado!");
                        }
                    });
                }
            }
        });
    }
}