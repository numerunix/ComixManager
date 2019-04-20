package org.altervista.numerone.comixmanager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class Fornitore {
	private int idFornitore,
					Cap;
	private String Nome, 
					Indirizzo,
					Citta,
					Provincia,
					eMail,
					SitoWeb;
	private long Telefono,
					Fax;
	
	public Fornitore(int id, String nome, String Indirizzo, int cap, String citta, 
			String provincia, long telefono, long fax, String eMail, String SitoWeb) {
		idFornitore=id;
		Nome=nome;
		this.Indirizzo=Indirizzo;
		Cap=cap;
		Citta=citta;
		Provincia=provincia;
		Fax=fax;
		this.eMail=eMail;
		this.SitoWeb=SitoWeb;
	}
	
	public int getId() {
		return idFornitore;
	}
	
	public String getNome() {
		return Nome;
	}
	
	public static LinkedList<Fornitore> GetFornitori(Connection conn) throws SQLException {
		LinkedList<Fornitore> f=new LinkedList<Fornitore>();
		Statement s=conn.createStatement();
		ResultSet rs=s.executeQuery("SELECT idFornitore, Nome, Indirizzo, Cap, Citta, Provincia, Telefono, Fax, eMail, SitoWeb FROM Fornitori ORDER BY Nome ASC");
		while (rs.next()) {
			f.add(new Fornitore(rs.getInt("idFornitore"), rs.getString("Nome"), rs.getString("Indirizzo"), rs.getInt("Cap"), rs.getString("Citta"), rs.getString("Provincia"), rs.getLong("Telefono"), rs.getLong("Fax"), rs.getString("eMail"), rs.getString("SitoWeb")));
		}
		rs.close();
		return f;
	}
}
