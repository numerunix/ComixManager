package org.altervista.numerone.comixmanager;

import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

public class HandlerAlbero implements TreeSelectionListener {
	private JFrame parent;
	private Connection Conn;
	private Dipendente dipendente;
	JTabbedPane pannello;
	public HandlerAlbero(JFrame Parent, Connection conn, Dipendente dip, JTabbedPane p) {
		parent=Parent;
		Conn=conn;
		dipendente=dip;
		pannello=p;
	}
	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		// TODO Auto-generated method stub
		TreePath path=arg0.getPath();
		if (path.getLastPathComponent().toString().equals("Articolo")) {
			PaneArticoli f = new PaneArticoli(Conn);
			pannello.addTab("Nuovo Articolo", f);
			parent.pack();
			pannello.setSelectedIndex(pannello.getTabCount()-1);
			return;
		}
		if (path.getLastPathComponent().toString().equals("Categoria")) {
			PaneCategoria f=new PaneCategoria(Conn);
			pannello.addTab("Nuova Categoria", f);
			pannello.setSelectedIndex(pannello.getTabCount()-1);
			parent.pack();
			return;
		}
		if (path.getLastPathComponent().toString().equals("Clienti")) {
			PaneClienti f = new PaneClienti(Conn);
			pannello.addTab("Nuovo Cliente", f);
			pannello.setSelectedIndex(pannello.getTabCount()-1);
			parent.pack();
			return;

		}
		if (path.getLastPathComponent().toString().equals("Collana")) {
			PaneCollana f = new PaneCollana(Conn);
			pannello.addTab("Nuova Collana", f);
			pannello.setSelectedIndex(pannello.getTabCount()-1);
			parent.pack();
			return;
		}
		if (path.getLastPathComponent().toString().equals("Dipendente")) {
			PaneDipendenti f = new PaneDipendenti(Conn);
			pannello.addTab("Nuovo Dipendente", f);
			pannello.setSelectedIndex(pannello.getTabCount()-1);
			parent.pack();
			return;
		}
		if (path.getLastPathComponent().toString().equals("Editore")) {
			PaneEditore f = new PaneEditore(Conn);
			pannello.addTab("Nuovo Editore", f);
			pannello.setSelectedIndex(pannello.getTabCount()-1);
			parent.pack();
			return;
		}
		if (path.getLastPathComponent().toString().equals("Fornitore")) {
			PaneFornitori f = new PaneFornitori(Conn);
			pannello.addTab("Nuovo Fornitore", f);
			pannello.setSelectedIndex(pannello.getTabCount()-1);
			parent.pack();
			return;
		}
		if (path.getLastPathComponent().toString().equals("Negozio")) {
			PaneNegozio f = new PaneNegozio(Conn);
			pannello.addTab("Nuovo Negozio", f);
			pannello.setSelectedIndex(pannello.getTabCount()-1);
			parent.pack();
			return;
		}
		if (path.getLastPathComponent().toString().equals("Oggetto")) {
			PaneOggetti f = new PaneOggetti(Conn, dipendente);
			pannello.addTab("Nuovo Oggetto", f);
			pannello.setSelectedIndex(pannello.getTabCount()-1);
			parent.pack();
			return;
		}
		if (path.getLastPathComponent().toString().equals("Prenotazione")) {
			PanePrenotazioni f = new PanePrenotazioni(Conn, dipendente);
			pannello.addTab("Prenotazione", f);
			pannello.setSelectedIndex(pannello.getTabCount()-1);
			parent.pack();
			return;
		}
		if (path.getLastPathComponent().toString().equals("Acquisti")) {
			PaneAcquisti f = new PaneAcquisti(Conn, dipendente);
			pannello.addTab("Acquisti", f);
			pannello.setSelectedIndex(pannello.getTabCount()-1);
			parent.pack();
			return;
		}

	}

}
