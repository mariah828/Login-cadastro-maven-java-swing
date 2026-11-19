
package Final;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;

public class Main {
	
	public boolean planoAdd(boolean temPlano) {

        JLabel lblInfoCartao = new JLabel("Digite o número do cartão: ");
        lblInfoCartao.setBounds(20, 20, 80, 25);

        JTextField txtInfoCartao = new JTextField();
        txtInfoCartao.setBounds(100, 20, 200, 25);

        JLabel lblCpf = new JLabel("Digite o seu CPF: ");
        lblCpf.setBounds(20, 60, 80, 25);

        JTextField txtCpf = new JTextField();
        txtCpf.setBounds(100, 60, 200, 25);
        
        JButton btnCadastrarCartao = new JButton("Cadastrar");
        btnCadastrarCartao.setBounds(100, 110, 120, 30);
        
        btnCadastrarCartao.addActionListener(e -> {
        String cpf = txtCpf.getText();
        	
        return temPlano;
	}
        

    public static void main(String[] args) {

        UsuarioCsv csv = new UsuarioCsv();
        ArrayList<Usuario> lista = csv.ler();

        JFrame janela = new JFrame("Cadastro");
        janela.setSize(350, 200);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, 20, 80, 25);

        JTextField txtEmail = new JTextField();
        txtEmail.setBounds(100, 20, 200, 25);

        JLabel lblSenha = new JLabel("Senha: ");
        lblSenha.setBounds(20, 60, 80, 25);

        JTextField txtSenha = new JTextField();
        txtSenha.setBounds(100, 60, 200, 25);

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(100, 110, 120, 30);

        // O principio eh que se um aumentar, todos aumentarão!!

        painel.add(lblEmail);
        painel.add(txtEmail);
        painel.add(lblSenha);
        painel.add(txtSenha);
        painel.add(btnCadastrar);

        janela.add(painel);
        janela.setVisible(true);

        btnCadastrar.addActionListener(e -> {

            String email = txtEmail.getText();
            String senha = txtSenha.getText();

            boolean emailExiste = false;

            for (Usuario y : lista) {

                if (y.getEmail().equals(email)) {

                    JOptionPane.showMessageDialog(null, "Esse email já foi cadastrado. Tente novamente!");
                    emailExiste = true;
                    break;

                }
            }

            if (!emailExiste) {

                JLabel lblNome = new JLabel("Digite o nome:");
                lblNome.setBounds(20, 20, 80, 25);

                JTextField txtNome = new JTextField();
                txtNome.setBounds(100, 20, 200, 25);

                JLabel lblOpcPlano = new JLabel("Deseja adicionar um plano: ");
                lblOpcPlano.setBounds(20, 60, 80, 25);

                JRadioButton opcSimPlano = new JRadioButton("Sim");
                JRadioButton opcNaoPlano = new JRadioButton("Não");

                ButtonGroup grupo = new ButtonGroup();

                JButton btnCadastrarNome = new JButton("Cadastrar");
                btnCadastrarNome.setBounds(100, 110, 120, 30);

                painel.add(lblNome);
                painel.add(txtNome);

                grupo.add(opcSimPlano);
                grupo.add(opcNaoPlano);

                painel.add(btnCadastrarNome);

                janela.add(painel);
                janela.setVisible(true);

                btnCadastrarNome.addActionListener(e -> {

                    String nome = txtNome.getText();

                    Usuario u = new Usuario();

                    u.setNome(nome);
                    u.setEmail(email);
                    u.setSenha(BCrypt.hashpw(senha, BCrypt.gensalt()));

                    if (planoAdd)
                        csv.gravar(u);

                    JLabel lblProcurarEmail = new JLabel("Digite um email para procurar:");
                    lblProcurarEmail.setBounds(20, 60, 80, 25);

                    JTextField txtProcurarEmail = new JTextField();
                    txtProcurarEmail.setBounds(100, 60, 200, 25);

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

            //lembra de ver com equals() para permitir busca de email, nome e plano

        });

    }

}
