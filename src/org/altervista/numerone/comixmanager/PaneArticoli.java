package org.altervista.numerone.comixmanager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PaneArticoli extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2174337872291720502L;
	/**
	 * 
	 */
	protected JTextField codiceABarre,
					Descrizione,
					Numero,
					DataPubblicazione;
	protected JComboBox<String> Collane,
				Categorie,
				Editori,
				Fornitori;
	protected JLabel errCodiceABarre,
				errDescrizione,
				errNumero,
				errDataPubblicazione,
				errOperazione;
	
	protected JTextArea Note;
	
	protected JButton ok;

	protected Connection conn;
	
	protected LinkedList<Collana> coll;
	protected LinkedList<Categoria> cat;
	protected LinkedList<Editore> edit;
	protected LinkedList<Fornitore> forn;
	
	public PaneArticoli(Connection conn)  {
		//super(parent, "Aggiungi Articolo", Dialog.ModalityType.DOCUMENT_MODAL);
		setLayout(new GridBagLayout());
		//setSize(850, 450);
		this.conn=conn;
		GridBagConstraints c=new GridBagConstraints();
		c.fill=GridBagConstraints.BOTH;
		c.gridheight=1;
		c.gridwidth=1;
		c.gridx=0;
		c.gridy=0;
		add(new JLabel("Codice a barre"), c);
		c.gridy=1;
		add(new JLabel("Descrizione"), c);
		c.gridy=2;
		add(new JLabel("Numero"), c);
		c.gridy=3;
		add(new JLabel("Data Pubblicazione"), c);
		c.gridy=4;
		add(new JLabel("Collana"),c);
		c.gridy=5;
		add(new JLabel("Categoria"), c);
		c.gridy=6;
		add(new JLabel("Editore"), c);
		c.gridy=7;
		add(new JLabel("Fornitore"), c);
		c.gridy=8;
		c.gridwidth=3;
		add(new JLabel("Note"), c);
		c.gridy=15;
		add(errOperazione=new JLabel("Ok"), c);
		c.gridx=1;
		c.gridy=0;
		c.gridwidth=1;
		add(codiceABarre=new JTextField(45), c);
		c.gridy=1;
		add(Descrizione=new JTextField(45), c);
		c.gridy=2;
		add(Numero=new JTextField(45), c);
		c.gridy=3;
		add(DataPubblicazione=new JTextField(45), c);
		c.gridy=4;
		add(Collane=new JComboBox<String>(), c);
		c.gridy=5;
		add(Categorie=new JComboBox<String>(), c);
		c.gridy=6;
		add(Editori=new JComboBox<String>(), c);
		c.gridy=7;
		add(Fornitori=new JComboBox<String>(), c);
		c.gridy=9;
		add(Note=new JTextArea(5,5), c);
		c.gridx=2;
		c.gridy=0;
		add(errCodiceABarre=new JLabel("Obbligatorio"), c);
		c.gridy=1;
		add(errDescrizione=new JLabel("Obbligatorio"), c);
		c.gridy=2;
		add(errNumero=new JLabel("Obbligatorio"), c);
		c.gridy=3;
		add(errDataPubblicazione=new JLabel("Obbligatorio. In formato gg-mm-aaaa."), c);
		c.gridx=0;
		c.gridy=14;

		ok=new JButton("Ok");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				errOperazione.setText("");
//				String password=new String(Password.getPassword());
				if (ProcessData(codiceABarre.getText(), Descrizione.getText(), Numero.getText(), DataPubblicazione.getText())) {
				try {
						SaveData(codiceABarre.getText(), Descrizione.getText(), Integer.parseInt(Numero.getText()), DataPubblicazione.getText(), 
								coll.get(Collane.getSelectedIndex()).getId(), cat.get(Categorie.getSelectedIndex()).getId(), edit.get(Editori.getSelectedIndex()).getId(), forn.get(Fornitori.getSelectedIndex()).getId(), Note.getText());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						errOperazione.setText("<html><font color='red'>"+e.getLocalizedMessage()+"</font></html>");
						return;
					}
					//dispose();
				errOperazione.setText("<html><font color='green'>Ok</font></html>");
				}
			}
		});
		add(ok, c);
		RiempiComboCollana(conn);
		RiempiComboCategorie(conn);
		RiempiComboEditori(conn);
		RiempiComboFornitori(conn);
		if (coll.size()==0) {
			errOperazione.setText("<html><font color='red'>Non ci sono collane salvate.</font></html>");
			ok.setEnabled(false);
			return;
		}
		if (cat.size()==0) {
			errOperazione.setText("<html><font color='red'>Non ci sono categorie salvate.</font></html>");
			ok.setEnabled(false);
			return;
		}
		if (edit.size()==0) {
			errOperazione.setText("<html><font color='red'>Non ci sono editori salvate.</font></html>");
			ok.setEnabled(false);
			return;
		}
		if (forn.size()==0) {
			errOperazione.setText("<html><font color='red'>Non ci sono fornitori salvate.</font></html>");
			ok.setEnabled(false);
			return;
		}
	}
	
	private void RiempiComboCollana(Connection conn) {
		try {
			coll = Collana.GetCollane(conn);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			errOperazione.setText(e1.getLocalizedMessage());
			return;
		}
		for (int i=0; i<coll.size(); i++)
			Collane.addItem(coll.get(i).getNome());
	}
	
	private void RiempiComboCategorie(Connection conn) {
		try {
			cat = Categoria.GetCategorie(conn);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			errOperazione.setText(e1.getLocalizedMessage());
			return;
		}
		for (int i=0; i<cat.size(); i++)
			Categorie.addItem(cat.get(i).getNome());
	}
	
	private void RiempiComboEditori(Connection conn) {
		try {
			edit = Editore.GetEditori(conn);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			errOperazione.setText(e1.getLocalizedMessage());
			return;
		}
		for (int i=0; i<edit.size(); i++)
			Editori.addItem(edit.get(i).getNome());
	}
	private void RiempiComboFornitori(Connection conn) {
		try {
			forn = Fornitore.GetFornitori(conn);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			errOperazione.setText(e1.getLocalizedMessage());
			return;
		}
		for (int i=0; i<forn.size(); i++)
			Fornitori.addItem(forn.get(i).getNome());
	}
	
	protected boolean ProcessData(String CodiceABarre, String Descrizione, String Numero, String DataPubblicazione) {
		boolean error=CodiceABarre.isEmpty() || Descrizione.isEmpty() || Numero.isEmpty() || DataPubblicazione.isEmpty();
		errCodiceABarre.setText("");
		errDescrizione.setText("");
		errNumero.setText("");
		errDataPubblicazione.setText("");
		if (error) {

			if (CodiceABarre.isEmpty())
				errCodiceABarre.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (Descrizione.isEmpty())
				errDescrizione.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (Numero.isEmpty())
				errNumero.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (DataPubblicazione.isEmpty())
				errDataPubblicazione.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			return false;
		}
		SimpleDateFormat df=new SimpleDateFormat("dd-MM-yyyy");
		Date d;
		try {
			d=df.parse(DataPubblicazione);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			errDataPubblicazione.setText("<html><font color='red'>Non è una data</font></html>");
			return false;
		}
		try {
			Integer.parseInt(Numero);
		} catch (NumberFormatException e)  {
			errNumero.setText("<html><font color='red'>Il campo non è numerico.</font></html>");
			return false;
		}
		return true;
	}
	
	protected void SaveData(String CodiceABarre, String Descrizione, int Numero, String DataPubblicazione, int Collana, int Categoria, int Editore, int Fornitore, String Note) throws SQLException {
		Date Data;
		String d;
		SimpleDateFormat df=new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat df1=new SimpleDateFormat("yyyy-MM-dd");
		try {
			Data=df.parse(DataPubblicazione);
			d=df1.format(Data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			errOperazione.setText(e.getLocalizedMessage());
			return;
		}
		Statement st=conn.createStatement();
		st.execute("INSERT INTO Articoli (CodiceABarre, Descrizione, Numero, DataPubblicazione, idCollana, idCategoria, idEditore, idFornitore, Note) "
				+ "	VALUES (\""+CodiceABarre+"\",\""+Descrizione+"\", "+Numero+", \""+d+"\", "
						+ Collana+", "+Categoria+", "+Editore+", "+Fornitore+ ",\""+Note+"\");");
		st.close();
		
		
	}
}
