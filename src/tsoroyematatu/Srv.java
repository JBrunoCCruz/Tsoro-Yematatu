package tsoroyematatu;

import java.io.*;
import java.net.*;

public class Srv extends Thread {
	// Atributos do Servidor
	private static ServerSocket srvsoc = null;	
	
	// Atributos do Servidor para o cliente 1
	private static DataInputStream in_1 = null; 
	private static DataOutputStream out_1 = null;
	private static String msg_1 = "";
	static Socket soc1 = null;

	// Atributos do Servidor para o cliente 2
	private static DataInputStream in_2 = null; 
	private static DataOutputStream out_2 = null;
	private static String msg_2 = "";
	static Socket soc2 = null;
	
	public Srv () {
		try {
			srvsoc = new ServerSocket(0);
		} catch (Exception e) {
			
		}
		
		new Thread(cliente1Conexao).start();
		new Thread(cliente2Conexao).start();
	}
	
	private static Runnable cliente1Conexao = new Runnable() {
		
		@Override
		public void run() {
			try {
				Thread.sleep(500);
				System.out.println("Aguardando cliente 1...");
				soc1 = srvsoc.accept();
				System.out.println("Cliente 1 conectado!");				
				
				out_1 = new DataOutputStream(soc1.getOutputStream());
				in_1 = new DataInputStream(soc1.getInputStream());								
				
				while (true) {
					try {
						msg_1 = in_1.readUTF();
						out_2.writeUTF(msg_1);
					} catch (Exception e) {
						
					}
				}
				
			} catch (Exception e) {
				
			}
		}
	};
	
	private static Runnable cliente2Conexao = new Runnable() {
		
		@Override
		public void run() {
			try {
				Thread.sleep(500);
				System.out.println("Aguardando cliente 2...");
				soc2 = srvsoc.accept();
				System.out.println("Cliente 2 conectado!");				
				
				out_2 = new DataOutputStream(soc2.getOutputStream());
				in_2 = new DataInputStream(soc2.getInputStream());								
				
				while (true) {
					try {
						msg_2 = in_2.readUTF();
						out_1.writeUTF(msg_2);
					} catch (Exception e) {
						
					}					
				}
				
			} catch (Exception e) {
				
			}
		}
	};
	
	public void run () {
		try {
			
			while (true) {
				
			}
			
		} catch (Exception e) {
			
		}
	}
	
	// Getters e Setters
	public String getEndereco () {
		//return srvsoc.getLocalSocketAddress().toString();
		return srvsoc.getInetAddress().toString();
	}
	
	public int getPorta () {
		return srvsoc.getLocalPort();
	}
}
