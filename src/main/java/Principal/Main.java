package Principal;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class Main {

    public static void main(String[] args) {

        TelaPrincipal app = new TelaPrincipal();
        
        JFrame janela = new JFrame("Cadastro");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(700, 600);
        janela.setLocationRelativeTo(null);
        
        JPanel painel = new JPanel();
        painel.setLayout(null);  
        painel.setBackground(new Color(20, 20, 20));
        painel.setPreferredSize(new Dimension(684, 560));
        JScrollPane rolagem = new JScrollPane(painel);
        rolagem.setBorder(null);
        rolagem.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        rolagem.getVerticalScrollBar().setUnitIncrement(16);
        janela.add(rolagem);

        app.mostrarTelaLogin(painel, janela);     
   
        janela.setVisible(true);
        
    }
}