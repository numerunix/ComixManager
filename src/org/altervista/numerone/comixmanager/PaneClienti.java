package org.altervista.numerone.comixmanager;

import java.awt.Dialog;
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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PaneClienti extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3145180567361142067L;
	private Connection conn;
	protected JTextField Nome,
							Cognome,
							Indirizzo,
							DataNascita,
							Cap,
							Citta,
							Provincia,
							Telefono,
							Fax,
							Email,
							SitoWeb,
							InizioTesseramento,
							FineTesseramento,
							Casella,
							Sconto;
	protected JLabel errNome,
						errCognome,
						errIndirizzo,
						errDataNascita,
						errCap,
						errCitta,
						errProvincia,
						errTelefono,
						errFax,
						errEmail,
						errSitoWeb,
						errInizioTesseramento,
						errFineTesseramento,
						errCasella,
						errSconto;
	private JButton ok;
	private JTextArea Note;
	protected JLabel errOperazione;
	
	public PaneClienti(Connection conn) {
		//super(parent, "Aggiungi Cliente", Dialog.ModalityType.DOCUMENT_MODAL);
		setLayout(new GridBagLayout());
		this.conn=conn;
		GridBagConstraints c=new GridBagConstraints();
		setSize(900, 600);
		c.fill=GridBagConstraints.BOTH;
		c.gridheight=1;
		c.gridwidth=1;
		c.gridx=0;
		c.gridy=0;
		add(new JLabel("Nome"), c);
		c.gridy=1;
		add(new JLabel("Cognome"), c);
		c.gridy=2;
		add(new JLabel("Indirizzo"),c);
		c.gridy=3;
		add(new JLabel("Data di Nascita"),c);
		c.gridy=4;
		add(new JLabel("CAP"),c);
		c.gridy=5;
		add(new JLabel("Città"),c);
		c.gridy=6;
		add(new JLabel("Provincia"),c);
		c.gridy=7;
		add(new JLabel("Telefono"),c);
		c.gridy=8;
		add(new JLabel("Fax"),c);
		c.gridy=9;
		add(new JLabel("eMail"),c);
		c.gridy=10;
		add(new JLabel("Sito Web"),c);
		c.gridy=11;
		add(new JLabel("Inizio Tesseramento"),c);
		c.gridy=12;
		add(new JLabel("Fine Tesseramento"),c);
		c.gridy=13;
		add(new JLabel("Casella"),c);
		c.gridy=14;
		add(new JLabel("Sconto"), c);
		c.gridy=15;
		c.gridwidth=3;
		add(new JLabel ("Note"), c);
		c.gridwidth=1;
		
		Nome=new JTextField(45);
		Cognome=new JTextField(45);
		Indirizzo=new JTextField(45);
		DataNascita=new JTextField(45);
		Cap=new JTextField(45);
		Citta=new JTextField(45);
		Provincia=new JTextField(45);
		Telefono=new JTextField(45);
		Fax=new JTextField(45);
		Email=new JTextField(45);
		SitoWeb=new JTextField(45);
		InizioTesseramento=new JTextField(45);
		FineTesseramento=new JTextField(45);
		Casella=new JTextField(45);
		Note=new JTextArea(5, 5);
		Sconto=new JTextField(45);
		c.gridx=1;
		c.gridy=0;
		add(Nome, c);
		c.gridy=1;
		add(Cognome, c);
		c.gridy=2;
		add(Indirizzo, c);
		c.gridy=3;
		add(DataNascita, c);
		c.gridy=4;
		add(Cap, c);
		c.gridy=5;
		add(Citta, c);
		c.gridy=6;
		add(Provincia, c);
		c.gridy=7;
		add(Telefono, c);
		c.gridy=8;
		add(Fax, c);
		c.gridy=9;
		add(Email, c);
		c.gridy=10;
		add(SitoWeb, c);
		c.gridy=11;
		add(InizioTesseramento, c);
		c.gridy=12;
		add(FineTesseramento, c);
		c.gridy=13;
		add(Casella, c);
		c.gridy=14;
		add(Sconto, c);
		c.gridy=15;
		c.gridwidth=3;
		c.gridheight=5;
		add(Note, c);
		
		c.gridy=0;
;		c.gridx=3;
		add(errNome=new JLabel("Obbligatorio"), c);
		c.gridy=1;
		add(errCognome=new JLabel("Obbligatorio"), c);
		c.gridy=2;
		add(errIndirizzo=new JLabel("Obbligatorio"), c);
		c.gridy=3;
		add(errDataNascita=new JLabel("Obbligatorio"), c);
		c.gridy=4;
		add(errCap=new JLabel("Obbligatorio"), c);
		c.gridy=5;
		add(errCitta=new JLabel("Obbligatorio"), c);
		c.gridy=6;
		add(errProvincia=new JLabel("Obbligatorio"), c);
		c.gridy=7;
		add(errTelefono=new JLabel("Obbligatorio"), c);
		c.gridy=8;
		add(errFax=new JLabel(""), c);
		c.gridy=9;
		add(errEmail=new JLabel(""), c);
		c.gridy=10;
		add(errSitoWeb=new JLabel(""), c);
		c.gridy=11;
		add(errInizioTesseramento=new JLabel("Obbligatorio. In formato gg-mm-aaaa."), c);
		c.gridy=12;
		add(errFineTesseramento=new JLabel("Obbligatorio.  In formato gg-mm-aaaa."), c);
		c.gridy=13;
		add(errCasella=new JLabel("Obbligatorio"), c);
		c.gridy=14;
		add(errSconto=new JLabel(""));
		c.gridy=21;
		c.gridx=0;
		c.gridheight=1;
		c.gridwidth=1;
		ok=new JButton("Ok");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				errOperazione.setText("");
				if (ProcessData(Nome.getText(), Cognome.getText(), Indirizzo.getText(), DataNascita.getText(), 
						Cap.getText(), Citta.getText(), Provincia.getText(), Telefono.getText(),
						Fax.getText(), Email.getText(), SitoWeb.getText(),
						InizioTesseramento.getText(), FineTesseramento.getText(), Casella.getText(), Sconto.getText())) {
				try {
					long fax;
					int sconto;
					if (Fax.getText().isEmpty())
						fax=0;
					else
						fax=Long.parseLong(Fax.getText());
					if (Sconto.getText().isEmpty())
						sconto=0;
					else
						sconto=Integer.parseInt(Sconto.getText());
					SaveData(Nome.getText(), Cognome.getText(), Indirizzo.getText(), DataNascita.getText(), 
							Integer.parseInt(Cap.getText()), Citta.getText(), Provincia.getText(), Long.parseLong(Telefono.getText()),
							fax, Email.getText(), SitoWeb.getText(),
							InizioTesseramento.getText(), FineTesseramento.getText(), Integer.parseInt(Casella.getText()), Note.getText(), sconto);
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

		c.gridy=23;
		c.gridx=0;
		c.gridheight=1;
		c.gridwidth=3;
		add(errOperazione=new JLabel("<html><font color='green'>Ok.</font></html>"), c);
		//pack();
	}
	
	protected boolean ProcessData(String Nome, String Cognome, String Indirizzo, 
			String DataNascita, String Cap, String Citta, String Provincia,
			String Telefono, String Fax, String Email, 
			String SitoWeb, String InizioTesseramento, String FineTesseramento,
			String Casella, String Sconto) {
		boolean error=Nome.isEmpty() || Cognome.isEmpty() ||Indirizzo.isEmpty() || DataNascita.isEmpty() || 
				Cap.isEmpty() || Citta.isEmpty() || Provincia.isEmpty() || Telefono.isEmpty() ||
				InizioTesseramento.isEmpty() || FineTesseramento.isEmpty() || Casella.isEmpty();
		errNome.setText("");
		errCognome.setText("");
		errIndirizzo.setText("");
		errDataNascita.setText("");
		errCap.setText("");
		errCitta.setText("");
		errProvincia.setText("");
		errTelefono.setText("");
		errFax.setText("");
		errEmail.setText("");
		errSitoWeb.setText("");
		errInizioTesseramento.setText("");
		errFineTesseramento.setText("");
		errCasella.setText("");
		errSconto.setText("");
		if (error) {
			if (Nome.isEmpty())
				errNome.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (Cognome.isEmpty())
				errCognome.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (Indirizzo.isEmpty())
				errIndirizzo.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (DataNascita.isEmpty())
				errDataNascita.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (Cap.isEmpty())
				errCap.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (Citta.isEmpty())
				errCitta.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (Provincia.isEmpty())
				errProvincia.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (Telefono.isEmpty())
				errTelefono.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (InizioTesseramento.isEmpty())
				errInizioTesseramento.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (FineTesseramento.isEmpty())
				errFineTesseramento.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (Casella.isEmpty())
				errFineTesseramento.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
				
			return false;
		}
		
		try {
			Integer.parseInt(Cap);
		} catch (NumberFormatException e)  {
			errCap.setText("<html><font color='red'>Il campo non è numerico.</font></html>");
			return false;
		}		
		try {
			Long.parseLong(Telefono);
		} catch (NumberFormatException e)  {
			errTelefono.setText("<html><font color='red'>Il campo non è numerico.</font></html>");
			return false;
		}
		if (!Fax.isEmpty()) {
			try {
				Long.parseLong(Fax);
			} catch (NumberFormatException e)  {
				errFax.setText("<html><font color='red'>Il campo non è numerico.</font></html>");
				return false;
			}
		}
		try {
			Integer.parseInt(Casella);
		} catch (NumberFormatException e)  {
			errCasella.setText("<html><font color='red'>Il campo non è numerico.</font></html>");
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
		SimpleDateFormat df=new SimpleDateFormat("dd-MM-yyyy");
		Date di, dfn, dn;
		try {
			di=df.parse(InizioTesseramento);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			errInizioTesseramento.setText("<html><font color='red'>Non è una data</font></html>");
			return false;
		}
		try {
			dfn=df.parse(FineTesseramento);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			errFineTesseramento.setText("<html><font color='red'>Non è una data</font></html>");
			return false;
		}
		try {
			dn=df.parse(DataNascita);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			errDataNascita.setText("<html><font color='red'>Non è una data</font></html>");
			return false;
		}
		if (!Fax.isEmpty()) {
			try {
				Long.parseLong(Fax);
			} catch (NumberFormatException e)  {
				errFax.setText("<html><font color='red'>Il campo non è numerico.</font></html>");
				return false;
			}		
		}

		
		return true;
	}
	
	protected void SaveData(String Nome, String Cognome, String Indirizzo, String DataNascita, int Cap, String Citta, String Provincia, long Telefono, long Fax, String Email, String SitoWeb, String InizioTesseramento, String FineTesseramento, int Casella, String Note, int sconto) throws SQLException {

		SimpleDateFormat df1=new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat df2=new SimpleDateFormat("yyyy-MM-dd");
		Date dn, di, df;
		String d1, d2, d3;
		try {
			dn=df1.parse(DataNascita);
			di=df1.parse(InizioTesseramento);
			df=df1.parse(FineTesseramento);
			d1=df2.format(dn);
			d2=df2.format(di);
			d3=df2.format(df);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			errOperazione.setText(e.getLocalizedMessage());
			return;
		}
		
		
		
		Statement st=conn.createStatement();
		st.execute("INSERT INTO Clienti (Nome, Cognome, Indirizzo, DataNascita, Cap, Citta, Provincia, Telefono, Fax, eMail, SitoWeb, InizioTesseramento, FineTesseramento, Casella, Note, Sconto) "
				+ "VALUES (\""+Nome+"\", \""+Cognome+ "\",\""+Indirizzo+"\", \""+d1+"\", "+Cap+", \""+Citta+"\", \""+Provincia+"\", "+Telefono+", "+  Fax+", \""+Email+"\", \""+SitoWeb+"\", \""+d2+"\", \""+d3+ "\"," +Casella+", \""+Note+"\","+sconto+");");
		st.close();
		
		
	}

}
