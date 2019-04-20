package org.altervista.numerone.comixmanager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class Categoria {
	private int IdCategoria;
	private String Nome,
					Note;
	
	public Categoria(int id, String nome, String note) {
		IdCategoria=id;
		Nome=nome;
		Note=note;
	}
	
	public int getId() {
		return IdCategoria;
	}
	
	
	public String getNome() {
		return Nome;
	}
	
	public static LinkedList<Categoria> GetCategorie(Connection conn) throws SQLException {
		LinkedList<Categoria> c=new LinkedList<Categoria>();
		Statement s=conn.createStatement();
		ResultSet rs=s.executeQuery("SELECT idCategoria, Nome, Note FROM Categoria ORDER BY Nome ASC");
		while (rs.next()) {
			c.add(new Categoria(rs.getInt("idCategoria"), rs.getString("Nome"), rs.getString("Note")));
		}
		rs.close();
		return c;
	}
}
