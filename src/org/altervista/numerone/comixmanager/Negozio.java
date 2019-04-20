package org.altervista.numerone.comixmanager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.JList;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class Negozio {
	private int id;
	private String nome,
					indirizzo,
					citta,
					telefono;
	
	public Negozio(int id, String nome, String indirizzo, String citta, String telefono) {
		this.id=id;
		this.nome=nome;
		this.indirizzo=indirizzo;
		this.citta=citta;
		this.telefono=telefono;
	}
	
	public String getNome() {
		return nome;
	}
	
	public int getId() {
		return id;
	}
	
	public static LinkedList<Negozio> getNegozi(Connection conn) throws SQLException {
		LinkedList<Negozio> n=new LinkedList<Negozio>();
		Statement s=conn.createStatement();
		ResultSet rs=s.executeQuery("SELECT idNegozio, Nome, Indirizzo, Citta, Telefono FROM Negozi ORDER BY Nome ASC");
		while (rs.next()) {
			n.add(new Negozio(rs.getInt("idNegozio"), rs.getString("Nome"), rs.getString("Indirizzo"), rs.getString("Citta"), rs.getString("Telefono")));
		}
		rs.close();
		s.close();
		return n;
	}
	
	public static int SearchId(LinkedList<Negozio> l, int id) {
		for (int i=0; i<l.size(); i++)
			if (l.get(i).getId()==id)
				return i;
		return l.size();
	}

}
