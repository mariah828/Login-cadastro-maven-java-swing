package Final;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
    	
    	JFrame janela = new JFrame("Cadastro");
        janela.setSize(350, 200);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);

        //JLabel lblNome = new JLabel("Nome:");
        //lblNome.setBounds(20, 20, 80, 25);

        //JTextField txtNome = new JTextField();
        //txtNome.setBounds(100, 20, 200, 25);
        //String nome = txtNome.getText();

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, 20, 80, 25);

        JTextField txtEmail = new JTextField();
        txtEmail.setBounds(100, 20, 200, 25);
        String email = txtEmail.getText();
        
        JLabel lblSenha = new JLabel("Senha: ");
        lblSenha.setBounds(20, 60, 80, 25);
        
        JTextField txtSenha = new JTextField();
        txtSenha.setBounds(100, 60, 200, 25);
        String senha = txtSenha.getText();

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(100, 110, 120, 30);
        // O principio eh que se um aumentar, todos aumentarão!!

        //painel.add(lblNome);
        //painel.add(txtNome);
        painel.add(lblEmail);
        painel.add(txtEmail);
        painel.add(lblSenha);
        painel.add(txtSenha);
        painel.add(btnCadastrar);

        janela.add(painel);
        janela.setVisible(true);
        
        UsuarioCsv csv = new UsuarioCsv();
        Scanner scan = new Scanner(System.in);
        
        String nome = "mariah";
        String plano = "Premium";
        
        Usuario u = new Usuario();
        u.setNome(nome);
        u.setEmail(email);
        u.setSenha(BCrypt.hashpw(senha, BCrypt.gensalt()));
        u.setPlano(plano);
        
        csv.gravar(u);
        
        JLabel lblProcurarEmail = new JLabel("Digite um email para procurar:");
        lblProcurarEmail.setBounds(20, 60, 80, 25);
        
        JTextField txtProcurarEmail = new JTextField();
        txtProcurarEmail.setBounds(100, 60, 200, 25);
        String procurarEmail = txtProcurarEmail.getText();
        
        ArrayList<Usuario> lista = csv.ler();
        
        boolean encontrou = false;
        
        for (Usuario x : lista) {
        	if (x.getEmail().equals(procurarEmail)) {
        		System.out.println("Encontrado: " + x.getNome() + " - " + x.getPlano());
        		encontrou = true;
        	}
        }
        
        if (!encontrou) {
        	System.out.println("Não encontrado");
        }
        
        for (Usuario x : lista) {
        	System.out.println(x.getNome() + " - " + x.getEmail() + " - " + x.getSenha());
        }
        
        //lembra de ver com equals() para permitir busca de email, nome  eplano
        
        
           
    }
}