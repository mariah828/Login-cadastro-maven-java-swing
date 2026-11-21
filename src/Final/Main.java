package Final;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

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
            JOptionPane.showMessageDialog(null, "Cvv inválido!");
            return false;
        }

        return true;
    }

    public void mostrarTelaBusca(JPanel painel) {

        JLabel lblProcurarEmail = new JLabel("Digite um email para procurar:");
        lblProcurarEmail.setBounds(20, 60, 80, 25);

        JTextField txtProcurarEmail = new JTextField();
        txtProcurarEmail.setBounds(100, 60, 200, 25);
        
        JButton btnProcurar = new JButton("Procurar");
        btnProcurar.setBounds(130, 110, 120, 30);

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

    // lembra de ver com equals() para permitir busca de email, nome e plano

    public static void main(String[] args) {
    	
    	Main app = new Main();

        JFrame janela = new JFrame("Cadastro");
        janela.setSize(350, 200);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, 20, 80, 25);

        JTextField txtEmail = new JTextField();
        txtEmail.setBounds(110, 20, 220, 25);

        JLabel lblSenha = new JLabel("Senha: ");
        lblSenha.setBounds(20, 60, 80, 25);

        JTextField txtSenha = new JTextField();
        txtSenha.setBounds(110, 60, 220, 25);

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(130, 110, 120, 30);

        // O principio eh que se um aumentar, todos aumentarão!!

        painel.add(lblEmail);
        painel.add(txtEmail);
        painel.add(lblSenha);
        painel.add(txtSenha);
        painel.add(btnCadastrar);

        janela.add(painel);
        janela.setVisible(true);

        btnCadastrar.addActionListener(eventoCadastro -> {

            String email = txtEmail.getText();
            String senha = txtSenha.getText();

            boolean emailExiste = false;

            for (Usuario y : app.lista) {

                if (y.getEmail().equals(email)) {
                    JOptionPane.showMessageDialog(null, "Esse email já foi cadastrado. Tente novamente!");
                    emailExiste = true;
                    break;
                }
            }

            if (!emailExiste) {

                JLabel lblNome = new JLabel("Digite o nome:");
                lblNome.setBounds(20, 20, 150, 25);

                JTextField txtNome = new JTextField();
                txtNome.setBounds(170, 20, 200, 25);

                JLabel lblOpcPlano = new JLabel("Deseja adicionar um plano: ");
                lblOpcPlano.setBounds(20, 65, 200, 25);

                JRadioButton opcSimPlano = new JRadioButton("Sim");
                opcSimPlano.setBounds(170, 65, 70, 25);

                JRadioButton opcNaoPlano = new JRadioButton("Não");
                opcNaoPlano.setBounds(250, 65, 80, 25);

                ButtonGroup grupo = new ButtonGroup();

                JButton btnCadastrarNome = new JButton("Cadastrar");
                btnCadastrarNome.setBounds(130, 120, 120, 30);

                painel.removeAll();

                painel.add(lblNome);
                painel.add(txtNome);

                grupo.add(opcSimPlano);
                grupo.add(opcNaoPlano);

                painel.add(btnCadastrarNome);
                painel.add(opcSimPlano);
                painel.add(opcNaoPlano);

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

                        JLabel lblInfoCartao = new JLabel("Digite o número do cartão: ");
                        lblInfoCartao.setBounds(20, 20, 150, 25);

                        JTextField txtInfoCartao = new JTextField();
                        txtInfoCartao.setBounds(180, 20, 220, 25);

                        JLabel lblCpf = new JLabel("Digite o seu CPF: ");
                        lblCpf.setBounds(20, 65, 150, 25);

                        JTextField txtCpf = new JTextField();
                        txtCpf.setBounds(180, 65, 220, 25);

                        JLabel lblCvvCartao = new JLabel("Digite o CVC/CVV do cartão: ");
                        lblCvvCartao.setBounds(20, 110, 150, 25);

                        JTextField txtCvvCartao = new JTextField();
                        txtCvvCartao.setBounds(180, 110, 220, 25);

                        JButton btnCadastrarCartao = new JButton("Cadastrar");
                        btnCadastrarCartao.setBounds(150, 165, 120, 30);

                        painel.removeAll();

                        painel.add(lblInfoCartao);
                        painel.add(txtInfoCartao);
                        painel.add(lblCpf);
                        painel.add(txtCpf);
                        painel.add(lblCvvCartao);
                        painel.add(txtCvvCartao);
                        painel.add(btnCadastrarCartao);

                        painel.revalidate();
                        painel.repaint();

                        // e se eu fizer um metodo p esse plano.set

                        btnCadastrarCartao.addActionListener(eventoCartao -> {

                            String cpf = txtCpf.getText();
                            String infoCartao = txtInfoCartao.getText();
                            String cvvCartao = txtCvvCartao.getText();

                            if (cpfValido(cpf, infoCartao, cvvCartao)) {
                                u.setPlano("Premium");
                                app.csv.gravar(u);
                                app.lista.add(u);
                                
                            } else {
                                u.setPlano(plano);
                                app.csv.gravar(u);
                                app.lista.add(u);
                            }
                        });

                    } else if (opcNaoPlano.isSelected()) {

                        u.setPlano(plano);
                        app.csv.gravar(u);
                        app.lista.add(u);
                        
                        JOptionPane.showMessageDialog(null,"Cadastro realizado, mas o cartão é inválido. "
                        		+ "Você foi cadastrado sem o plano Premium.");
                    }
                });
            }
        });
    }
}
