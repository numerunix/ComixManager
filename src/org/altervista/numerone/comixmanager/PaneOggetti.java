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
import javax.swing.JTextField;

public class PaneOggetti extends JPanel {
	private static final long serialVersionUID = 1L;
	private Connection conn;
	protected int idDipendente;
	protected JTextField CodiceABarre,
							Quantita,
							Prezzo,
							Sconto,
							DataOrdine,
							Minimo;
	protected JComboBox<String> Negozi;
	protected JLabel errCodiceABarre,
						errQuantita,
						errPrezzo,
						errSconto,
						errDataOrdine,
						errOperazione,
						art,
						errMinimo;
	private JButton ok;
	private Articolo articolo;
	protected LinkedList<Negozio> negozi;
	
	public PaneOggetti(Connection conn, Dipendente dip) {
		//super(parent, "Aggiungi Oggetti", Dialog.ModalityType.DOCUMENT_MODAL);
		setLayout(new GridBagLayout());
		this.conn=conn;
		idDipendente=dip.getId();
		GridBagConstraints c=new GridBagConstraints();
		setSize(700, 400);
		c.fill=GridBagConstraints.BOTH;
		c.gridheight=1;
		c.gridwidth=1;
		c.gridx=0;
		c.gridy=0;
		add(new JLabel("Codice a Barre"), c);
		c.gridy=1;
		add(new JLabel("Nome articolo"), c);
		c.gridy=2;
		add(new JLabel("Quantità"), c);
		c.gridy=3;
		add(new JLabel("Prezzo"), c);
		c.gridy=4;
		add(new JLabel("Sconto"), c);
		c.gridy=5;
		add(new JLabel("Data Ordine"), c);
		c.gridy=6;
		add(new JLabel("Negozio"), c);
		c.gridy=7;
		add(new JLabel("Dipendente"), c);
		c.gridy=8;
		add(new JLabel("Quantità minima"), c);
		
		c.gridx=1;
		c.gridy=0;
		add(CodiceABarre=new JTextField(45), c);
		c.gridy=1;
		add(art=new JLabel("Codice a barre non trovato"), c);
		c.gridy=2;
		add(Quantita=new JTextField(45), c);
		c.gridy=3;
		add(Prezzo=new JTextField(45), c);
		c.gridy=4;
		add(Sconto=new JTextField(45), c);
		c.gridy=5;
		add(DataOrdine=new JTextField(45), c);
		c.gridy=6;
		add(Negozi=new JComboBox<String>(), c);
		c.gridy=7;
		add(new JLabel(dip.getNome()+ " "+ dip.getCognome()), c);
		c.gridy=8;
		add(Minimo=new JTextField(45), c);
		c.gridx=2;
		c.gridy=0;
		add(errCodiceABarre=new JLabel("Obbligatorio"), c);
		c.gridy=2;
		add(errQuantita=new JLabel("Obbligatorio"), c);
		c.gridy=3;
		add(errPrezzo=new JLabel("Obbligatorio"), c);
		c.gridy=4;
		add(errSconto=new JLabel(""), c);
		c.gridy=5;
		add(errDataOrdine=new JLabel("Obbligatorio"), c);
		c.gridy=8;
		add(errMinimo=new JLabel("Obbligatorio"), c);

		c.gridy=9;
		c.gridx=0;
		ok=new JButton("Ok");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				errOperazione.setText("");
				int sconto;
				try {
					sconto=Integer.parseInt(Sconto.getText());
				} catch (java.lang.NumberFormatException e) {
					sconto=0;
				}
				if (ProcessData(CodiceABarre.getText(), Quantita.getText(), Prezzo.getText(), Sconto.getText(), DataOrdine.getText(), Minimo.getText())) {
				try {
						SaveData(Integer.parseInt(Quantita.getText()), Float.parseFloat(Prezzo.getText()), sconto, DataOrdine.getText(), dip.getId(), negozi.get(Negozi.getSelectedIndex()).getId(), articolo.getId(), Integer.parseInt(Minimo.getText()));
				} catch (SQLException e) {
						// TODO Auto-generated catch block
					errOperazione.setText("<html><font color='red'>"+e.getLocalizedMessage()+"</font></html>");
						return;
					}
					//dispose();
				errOperazione.setText("<html><font color='green'>Operazione effettuata.</font></html>");

				}
			}
		});
		add(ok, c);

		c.gridy=10;
		c.gridx=0;
		c.gridwidth=3;
		add(errOperazione=new JLabel("<html><font color='green'>Ok.</font></html>"), c);
		RiempiComboBox(conn);
		if (dip.isRoot()) {
			errOperazione.setText("<html><font color='red'>Sei root. Non puoi aggiungere articoli.</font></html>");
			ok.setEnabled(false);
		}
		if (!dip.isRoot())
			Negozi.setSelectedIndex(Negozio.SearchId(negozi, dip.getIdNegozio()));
		CodiceABarre.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				errOperazione.setText("");
				articolo=Articolo.searchFromCodice(conn, CodiceABarre.getText());
				if (articolo==null) {
					art.setText("Codice a barre non trovato");
					ok.setEnabled(false);
					
				} else {
					art.setText(articolo.getDescrizione());
					ok.setEnabled(true);
				}
			}
		});
		if (negozi.size()==0) {
			errOperazione.setText("<html><font color='red'>Non ci sono negozi salvati.</font></html>");
			ok.setEnabled(false);
			return;
		}
		ok.setEnabled(false);
		if (dip.isRoot()) {
			errOperazione.setText("<html><font color='red'>Sei root. Non puoi aggiungere oggetti.</font></html>");
		}
	}
	
	protected boolean ProcessData(String CodiceABarre, String Quantita, String Prezzo, String Sconto, String DataOrdine, String Minimo) {
		boolean error=CodiceABarre.isEmpty() || Quantita.isEmpty() || Prezzo.isEmpty() || DataOrdine.isEmpty() || Minimo.isEmpty();
		errCodiceABarre.setText("");
		errQuantita.setText("");
		errPrezzo.setText("");
		errSconto.setText("");
		errDataOrdine.setText("");
		errMinimo.setText("");
		if (error) {
			if (CodiceABarre.isEmpty())
				errCodiceABarre.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (Quantita.isEmpty())
				errQuantita.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (Prezzo.isEmpty())
				errPrezzo.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (DataOrdine.isEmpty())
				errDataOrdine.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (Minimo.isEmpty())
				errMinimo.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			return false;
		}
		if (!Sconto.isEmpty()) {
			try {
				Integer.parseInt(Sconto);
			} catch (NumberFormatException e)  {
				errSconto.setText("<html><font color='red'>Il campo non è numerico.</font></html>");
				return false;
			}
		}
		try {
			Integer.parseInt(Quantita);
		} catch (NumberFormatException e)  {
			errQuantita.setText("<html><font color='red'>Il campo non è numerico.</font></html>");
			return false;
		}
		
		
		try {
			Float.parseFloat(Prezzo);
		} catch (NumberFormatException e)  {
			errPrezzo.setText("<html><font color='red'>Il campo non è numerico.</font></html>");
			return false;
		}
		try {
			Integer.parseInt(Minimo);
		} catch (NumberFormatException e)  {
			errMinimo.setText("<html><font color='red'>Il campo non è numerico.</font></html>");
			return false;
		}

		SimpleDateFormat df=new SimpleDateFormat("dd-MM-yyyy");
		Date d;
		try {
			d=df.parse(DataOrdine);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			errDataOrdine.setText("<html><font color='red'>Non è una data</font></html>");
			return false;
		}

		return true;
	}
	
	protected void SaveData(int Quantita, float Prezzo, int Sconto, String DataOrdine, int idDipendente, int idNegozio, long idArticolo, int Minimo) throws SQLException {
		Date Data;
		String d;
		SimpleDateFormat df=new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat df1=new SimpleDateFormat("yyyy-MM-dd");
		try {
			Data=df.parse(DataOrdine);
			d=df1.format(Data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			errOperazione.setText(e.getLocalizedMessage());
			return;
		}
		Statement st=conn.createStatement();
		st.execute("INSERT INTO Oggetti (Quantita, Prezzo, Sconto, DataOrdine, idDipendente, idNegozio, idArticolo, Minimo) VALUES ("+Quantita+", "+Prezzo+", " + Sconto + ", '"+d+"', "+idDipendente+", "+idNegozio+", "+idArticolo+", "+Minimo+");");
		st.close();
	}
	
	private void RiempiComboBox(Connection conn) {
		try {
			negozi = Negozio.getNegozi(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			errOperazione.setText(e.getLocalizedMessage());
			return;
		}
		for (int i=0; i<negozi.size(); i++)
			Negozi.addItem(negozi.get(i).getNome());
	}
}
