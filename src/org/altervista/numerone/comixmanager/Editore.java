package org.altervista.numerone.comixmanager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class Editore {
	protected int Id,
					Sconto;
	protected String Nome,
						Note;
	
	public Editore (int id, String Nome, int Sconto, String note) {
		this.Id=id;
		this.Nome=Nome;
		this.Sconto=Sconto;
		this.Note=note;
	}
	
	public String getNome() {
		return Nome;
	}

	
	public int getId() {
		return Id;
	}
	
	public static LinkedList<Editore> GetEditori(Connection conn) throws SQLException {
		LinkedList<Editore> e=new LinkedList<Editore>();
		Statement s=conn.createStatement();
		ResultSet rs=s.executeQuery("SELECT idEditore, Nome, Sconto, Note FROM Editori ORDER BY Nome ASC");
		while (rs.next()) {
			e.add(new Editore(rs.getInt("idEditore"), rs.getString("Nome"), rs.getInt("Sconto"), rs.getString("Note")));
		}
		rs.close();
		return e;
	}
}
