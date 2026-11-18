package Final;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UsuarioCsv {
	private String caminho = "Informacoes.csv";
	
	public void gravar(Usuario u) {
		String linha = u.getNome() + ";" + u.getEmail() + ";" + u.getSenha() + ";" + u.getPlano();
		
		// se usar false no filewriter o arquivo não salva, é apagado;
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("Informacoes.csv", true));) {
            bw.write(linha);
            bw.newLine();
            
        } catch (IOException e) {
            System.out.println("Erro ao gravar: " + e.getMessage());
        }
	}
		
	public ArrayList<Usuario> ler() {
		ArrayList<Usuario> lista = new ArrayList<>();
		File arquivo = new File(caminho);
		
		if (!arquivo.exists()) {
			return lista;
		}
			
		try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
			String linha;
			
			while ((linha = br.readLine()) != null) {
				String[] partes = linha.split(";");
	            	 
				Usuario u = new Usuario();
				u.setNome(partes[0]);
				u.setEmail(partes[1]);
				u.setSenha(partes[2]);
				u.setPlano(partes[3]);
				
				lista.add(u);
	        }
	            
	    } catch (IOException e) {
	    	System.out.println("Erro ao ler: " + e.getMessage());
	    }
		
		return lista;
		// lembra q a lksta so vai ser retornada para o admin ver!
			
		}
	}
	
