package org.altervista.numerone.comixmanager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Date;

public class Oggetto {
	private int idOggetto,
					Quantita,
					Sconto,
					idDipendente,
					idNegozio,
					idArticolo,
					Minimo,
					Vendita;
	private Date DataOrdine;
	private float Prezzo;
	public Oggetto(int id, int quantita, float prezzo, int sconto, int iddipendente, int idnegozio, int idarticolo, Date dataordine, int minimo) {
		idOggetto=id;
		Quantita=quantita;
		Prezzo=prezzo;
		Sconto=sconto;
		idDipendente=iddipendente;
		idNegozio=idnegozio;
		idArticolo=idArticolo;
		DataOrdine=dataordine;
		Minimo=minimo;
		Vendita=0;
	}
	
	public int getId() {
		return idOggetto;
	}
	
	public float getPrezzo() {
		return Prezzo;
	}
	
	public int getSconto() {
		return Sconto;
	}
	
	public boolean incrementaVendita() {
		if (Vendita==Quantita)
			return false;
		else
			Vendita++;
		return true;
	}
	public int getQuantita() {
		return Quantita;
	}
	
	public int getVendita() {
		return Vendita;
	}
	
	public long getIdArticolo() {
		return idArticolo;
	}
	
	public static Oggetto getOggetto(Connection conn, long idArticolo, int idNegozio) throws SQLException {
		Oggetto o=null;
		Statement st=conn.createStatement();
		ResultSet rs=st.executeQuery("SELECT idOggetto, Quantita, Prezzo, Sconto, idDipendente, idNegozio, idArticolo, DataOrdine, Minimo FROM Oggetti WHERE idArticolo="+idArticolo+" AND idNegozio="+idNegozio+";");
		if (!rs.next()) {
			rs.close();
			return null;
		}
		o=new Oggetto(rs.getInt("idOggetto"), rs.getInt("Quantita"), rs.getFloat("Prezzo"), rs.getInt("Sconto"), rs.getInt("idDipendente"), rs.getInt("idNegozio"), rs.getInt("idArticolo"), rs.getDate("DataOrdine"), rs.getInt("Minimo"));
		rs.close();
		return o;
	}
}
