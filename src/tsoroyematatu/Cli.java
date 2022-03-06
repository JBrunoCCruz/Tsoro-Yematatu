package tsoroyematatu;

import java.io.*;
import java.net.*;

public class Cli extends Thread {
	// Atributos do Cliente
	private static DataOutputStream out = null;
	private static DataInputStream in = null;	      
	private static Socket soc = null; 
	private String host = "";
	private static String msgIn = "";
	private static String msgOut = "";
	private static int portaServidor = -1;
	
	public String enderecoDoServidorCriado = "";
	public int portaDoServidorCriado = -1;
	
	Srv servidor;
	

	public Cli () {
		//criaCliente(criarServidor, enderecoServidorInformado, portaServidorInformada);
	}
	
	public void criaCliente (int criarServidor, String enderecoServidorInformado, int portaServidorInformada) {
		// ("Criar servidor? 1 - Sim / 0 - Não ");
		
		if (criarServidor == 1) {
			this.start();
		}
		
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			
		}
		
		// Inicia novo cliente
		try {			
			if (criarServidor == 1) {
				host = "localhost";
				portaServidor = servidor.getPorta();
			} else {				
				host = enderecoServidorInformado;				
				portaServidor = portaServidorInformada;				
			}													
			
			// Cliente conectando ao servidor...
			soc = new Socket(host, portaServidor);
			
			// Objetos do cliente para leitura e escrita
			out = new DataOutputStream(soc.getOutputStream());
			in = new DataInputStream(soc.getInputStream());
			
			new Thread(recebeMensagem).start();
			
			while (true) {
				try {
					Thread.sleep(5000);
				} catch (Exception e) {

				}
			}
			
		} catch (Exception e){
			
		}
	}
	
	private Runnable recebeMensagem = new Runnable() {
		@Override
		public void run () {
			try {
				while (true) {
					msgIn = in.readUTF();		
				}												
				
			} catch (Exception e) {
				System.out.println("Parei de receber mensagens..");
			}					
		}
	};
	
	/* Tipos de mensagem
	 * - Chat
	 * - Jogo
	 * */
	public void enviaMensagem (String tipoDeMensagem, String mensagem) {
		try {
			System.out.println("Estou enviando: " + tipoDeMensagem + ":" + mensagem);
			msgOut = tipoDeMensagem + ":" + mensagem;
			out.writeUTF(msgOut);
			out.flush();
		} catch (Exception e) {

		}
	}		
	
	public String getMensagemRecebida () {
		return msgIn;
	}
	
	
	public String getEnderecoServidorCriado () {
		return soc.getLocalAddress().toString();
	}
	
	public String getPortaServidorCriado () {
		return Integer.toString(soc.getPort());
	}
	
	
	// Thread servidor
	public void run () {
		// Cria servidor
		servidor = new Srv();
		enderecoDoServidorCriado = servidor.getEndereco();
		System.out.print("Endereço servidor: " + enderecoDoServidorCriado + " ");
		portaDoServidorCriado = servidor.getPorta();
		System.out.println("Porta servidor: " + portaDoServidorCriado);
		
		// Não posso receber a mensagem aqui, pq aqui serve apenas para iniciar o servidor
		while (true) {
			try {
				Thread.sleep(10000);
			} catch (Exception e) {
				
			}
		}
	}
}
