package org.altervista.numerone.comixmanager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class Articolo {
	private long idArticolo;
	private String Descrizione,
					CodiceABarre,
					Note;
	private int Iva,
				 idCollana,
				 idCategoria,
				 idEditore,
				 idFornitore,
				 Numero;
	private Date DataPubblicazione;
	
	public Articolo(int id, String descrizione, String codiceabarre, int iva, 
			String note, int idcollana, int idcategoria, int ideditore, int idfornitore, 
			int numero, String datapubblicazione) {
		idArticolo=id;
		Descrizione=descrizione;
		CodiceABarre=codiceabarre;
		Iva=iva;
		Note=note;
		idCollana=idcollana;
		idCategoria=idcategoria;
		idEditore=ideditore;
		idFornitore=idfornitore;
		Numero=numero;
		DateFormat df=new SimpleDateFormat("dd-MM-yyyy");
		try {
			DataPubblicazione=df.parse(datapubblicazione);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static Articolo searchFromCodice(Connection conn, String codiceabarre) {
		Statement st;
		Articolo a=null;
		try {
			st = conn.createStatement();
			ResultSet rs=st.executeQuery("SELECT idArticolo, Descrizione, codiceABarre, Iva, Note, idCollana, idCategoria, idEditore, idFornitore, Numero, DataPubblicazione FROM Articoli WHERE CodiceABarre=\""+codiceabarre+"\";");
			if (rs.next())
				a=new Articolo(rs.getInt("idArticolo"), rs.getString("Descrizione"),
						rs.getString("CodiceABarre"), rs.getInt("Iva"), 
						rs.getString("Note"), rs.getInt("idCollana"), rs.getInt("idCategoria"), 
						rs.getInt("idEditore"), rs.getInt("IdFornitore"), 
						rs.getInt("Numero"), rs.getString("DataPubblicazione"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
		return a;
	}
	
	public long getId() {
		return idArticolo;
	}
	
	public String getDescrizione() {
		return Descrizione;
	}
	
	public int getNumero() {
		return Numero;
	}
}
