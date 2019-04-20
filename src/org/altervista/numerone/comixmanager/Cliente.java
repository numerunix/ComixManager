package org.altervista.numerone.comixmanager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;

public class Cliente {
	private int idCliente,
					Cap,
					idCasella,
					Sconto;
	private String Nome,
				Cognome,
				Indirizzo,
				Citta,
				Provincia,
				eMail,
				SitoWeb,
				Note;
	private Date DataNascita,
					DataInizioTesseramento,
					DataFineTesseramento;
	private long Telefono,
					Cellulare,
					Fax;
	
	public Cliente(int id, String nome, String cognome, String indirizzo,
			Date datanascita, int cap, String citta, String provincia, 
			long telefono, long fax, String email, String sitoweb,
			 Date datainiziotesseramento, Date datafinetesseramento,
			String note, int casella, int sconto) {
		idCliente=id;
		Nome=nome;
		Cognome=cognome;
		Indirizzo=indirizzo;
		DataNascita=datanascita;
		Citta=citta;
		Provincia=provincia;
		Telefono=telefono;
		Fax=fax;
		eMail=email;
		SitoWeb=sitoweb;
		DataInizioTesseramento=datainiziotesseramento;
		DataFineTesseramento=datafinetesseramento;
		Note=note;
		idCasella=casella;
		Sconto=sconto;
	}
	
	public int getId() {
		return idCliente;
	}
	
	public String getNome() {
		return Nome;
	}
	
	public String getCognome() {
		return Cognome;
		
	}

	public int getSconto() {
		return Sconto;
	}
	
	public static LinkedList<Cliente> GetClienti(Connection conn) throws SQLException {
		LinkedList<Cliente> c=new LinkedList<Cliente>();
		Statement s=conn.createStatement();
		ResultSet rs=s.executeQuery("SELECT idCliente, Nome, Cognome, Indirizzo, DataNascita, Cap, Citta, Provincia, Telefono, Fax, EMail, SitoWeb, InizioTesseramento, FineTesseramento, Note, Casella, Sconto FROM Clienti ORDER BY Cognome, Nome ASC");
		while (rs.next()) {
			c.add(new Cliente(rs.getInt("idCliente"), rs.getString("Nome"), rs.getString("Cognome"), 
					rs.getString("Indirizzo"), rs.getDate("DataNascita"), rs.getInt("Cap"), 
					rs.getString("Citta"), rs.getString("Provincia"), rs.getLong("Telefono"), 
					rs.getLong("Fax"), rs.getString("EMail"), rs.getString("SitoWeb"), 
					rs.getDate("InizioTesseramento"), rs.getDate("FineTesseramento"), 
					rs.getString("Note"), rs.getInt("Casella"), rs.getInt("Sconto")));
		}
		rs.close();
		return c;
	}
	
	public static Cliente getCliente(Connection conn, int idCasella) throws SQLException {
		Cliente c=null;
		Statement s=conn.createStatement();
		ResultSet rs=s.executeQuery("SELECT idCliente, Nome, Cognome, Indirizzo, DataNascita, Cap, Citta, Provincia, Telefono, Fax, EMail, SitoWeb, InizioTesseramento, FineTesseramento, Note, Sconto FROM Clienti WHERE Casella="+idCasella+";");
		if (rs.next()) {
			c=new Cliente(rs.getInt("idCliente"), rs.getString("Nome"), rs.getString("Cognome"), 
					rs.getString("Indirizzo"), rs.getDate("DataNascita"), rs.getInt("Cap"), 
					rs.getString("Citta"), rs.getString("Provincia"), rs.getLong("Telefono"), 
					rs.getLong("Fax"), rs.getString("EMail"), rs.getString("SitoWeb"),
					rs.getDate("InizioTesseramento"), rs.getDate("FineTesseramento"),
					rs.getString("Note"), idCasella, rs.getInt("Sconto"));
		}
		rs.close();
		return c;
		
	}
}
