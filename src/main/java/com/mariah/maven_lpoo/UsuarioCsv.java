package com.mariah.maven_lpoo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UsuarioCsv {
	private String caminho = "Informacoes.csv";
	
	public void gravar(Usuario u) throws IOException {
	    String linha = u.getNome() + ";" + u.getEmail() + ";" + u.getSenha() + ";" + u.getPlano();

	    try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho, true))) {
	        bw.write(linha);
	        bw.newLine();
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
	            	 
				if (partes.length < 4) {
				    continue;
				}
				
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