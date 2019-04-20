package org.altervista.numerone.comixmanager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class Collana {
	private int idCollana,
				idEditore;
	private String nome,
					note;
	
	public Collana(int idCollana, String nome, String Note, int idEditore) {
		this.idCollana=idCollana;
		this.nome=nome;
		this.note=Note;
		this.idEditore=idEditore;
	}
	
	public String getNome() {
		return nome;
	}
	
	public int getId() {
		return idCollana;
	}
	
	public static LinkedList<Collana> GetCollane(Connection conn) throws SQLException {
		LinkedList<Collana> c=new LinkedList<Collana>();
		Statement s=conn.createStatement();
		ResultSet rs=s.executeQuery("SELECT idCollana, Nome, Note, idEditore FROM Collana ORDER BY Nome ASC");
		while (rs.next()) {
			c.add(new Collana(rs.getInt("idCollana"), rs.getString("Nome"), rs.getString("Note"), rs.getInt("idEditore")));
		}
		rs.close();
		return c;
	}
}
