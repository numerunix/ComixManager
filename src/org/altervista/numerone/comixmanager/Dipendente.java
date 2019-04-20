package org.altervista.numerone.comixmanager;

public class Dipendente {
	private int idDipendente, idNegozio;
	private String Nome, Cognome;
	private long Cellulare;
	
	public Dipendente(int Id, String nome, String cognome, long cellulare, int idnegozio)
	{
		idDipendente=Id;
		Nome=nome;
		Cognome=cognome;
		Cellulare=cellulare;
		idNegozio=idnegozio;
	}
	
	public String getNome() {
		return Nome;
	}
	
	public String getCognome() {
		return Cognome;
	}
	
	public int getId() {
		return idDipendente;
	}
	
	public int getIdNegozio() {
		return idNegozio;
	}
	
	public boolean isRoot() {
		return Nome.equals("root");
	}
}
