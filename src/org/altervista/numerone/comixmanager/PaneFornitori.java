package org.altervista.numerone.comixmanager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PaneFornitori extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7504426687976680021L;
	private Connection conn;
	protected JTextField Nome,
							Indirizzo,
							Cap,
							Citta,
							Provincia,
							Telefono,
							Fax,
							eMail,
							SitoWeb;
	protected JLabel errNome,
						errIndirizzo,
						errCap,
						errCitta,
						errProvincia,
						errTelefono,
						errFax,
						errEmail,
						errSitoWeb;
	private JButton ok;
	protected JLabel errOperazione;
	
	
	public PaneFornitori(Connection conn) {
		//super(parent, "Aggiungi Fornitore", Dialog.ModalityType.DOCUMENT_MODAL);
		setLayout(new GridBagLayout());
		this.conn=conn;
		GridBagConstraints c=new GridBagConstraints();
		setSize(700, 400);
		c.fill=GridBagConstraints.BOTH;
		c.gridheight=1;
		c.gridwidth=1;
		c.gridx=0;
		c.gridy=0;
		add(new JLabel("Nome"), c);
		c.gridy=1;
		add(new JLabel("Indirizzo"), c);
		c.gridy=2;
		add(new JLabel("CAP"),c);
		c.gridy=3;
		add(new JLabel("Città"),c);
		c.gridy=4;
		add(new JLabel("Provincia"),c);
		c.gridy=5;
		add(new JLabel("Telefono"),c);
		c.gridy=6;
		add(new JLabel("Fax"),c);
		c.gridy=7;
		add(new JLabel("E-Mail"),c);
		c.gridy=8;
		add(new JLabel("Sito Web"),c);
		
		Nome=new JTextField(45);
		Indirizzo=new JTextField(45);
		Cap=new JTextField(45);
		Citta=new JTextField(45);
		Provincia=new JTextField(45);
		Telefono=new JTextField(45);
		Fax=new JTextField(45);
		eMail=new JTextField(45);
		SitoWeb=new JTextField(45);
		
		c.gridx=1;
		c.gridy=0;
		add(Nome, c);
		c.gridy=1;
		add(Indirizzo, c);
		c.gridy=2;
		add(Cap, c);
		c.gridy=3;
		add(Citta, c);
		c.gridy=4;
		add(Provincia, c);
		c.gridy=5;
		add(Telefono, c);
		c.gridy=6;
		add(Fax, c);
		c.gridy=7;
		add(eMail, c);
		c.gridy=8;
		add(SitoWeb, c);
		
		c.gridy=0;
;		c.gridx=2;
		add(errNome=new JLabel("Obbligatorio"), c);
		c.gridy=1;
		add(errIndirizzo=new JLabel("Obbligatorio"), c);
		c.gridy=2;
		add(errCap=new JLabel("Obbligatorio"), c);
		c.gridy=3;
		add(errCitta=new JLabel("Obbligatorio"), c);
		c.gridy=4;
		add(errProvincia=new JLabel("Obbligatorio"), c);
		c.gridy=5;
		add(errTelefono=new JLabel("Obbligatorio"), c);
		c.gridy=6;
		add(errFax=new JLabel(""), c);
		c.gridy=7;
		add(errEmail=new JLabel(""), c);
		c.gridy=8;
		add(errSitoWeb=new JLabel(""), c);
		c.gridy=9;
		c.gridx=0;
		c.gridheight=1;
		c.gridwidth=1;
		ok=new JButton("Ok");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				errOperazione.setText("");
				Long fax;
				try {
					fax=Long.parseLong(Fax.getText());
				} catch (java.lang.NumberFormatException e) {
					fax=(long) 0;
				}
				if (ProcessData(Nome.getText(), Indirizzo.getText(), Cap.getText(), Citta.getText(), Provincia.getText(), Telefono.getText(), Fax.getText(), eMail.getText(), SitoWeb.getText())) {
				try {
						SaveData(Nome.getText(), Indirizzo.getText(), Integer.parseInt(Cap.getText()), Citta.getText(), Provincia.getText(), Long.parseLong(Telefono.getText()), fax, eMail.getText(), SitoWeb.getText());
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

		c.gridy=10;
		c.gridx=0;
		c.gridheight=1;
		c.gridwidth=3;
		add(errOperazione=new JLabel("<html><font color='green'>Ok.</font></html>"), c);
		//pack();
	}
	
	protected boolean ProcessData(String Nome, String Indirizzo, String Cap, String Citta, String Provincia, String Telefono, String Fax, String Email, String SitoWeb) {
		boolean error=Nome.isEmpty() || Indirizzo.isEmpty() || Cap.isEmpty() || Citta.isEmpty() || Provincia.isEmpty() || Telefono.isEmpty();
		errNome.setText("");
		errIndirizzo.setText("");
		errCap.setText("");
		errCitta.setText("");
		errProvincia.setText("");
		errTelefono.setText("");
		errCap.setText("");
		errTelefono.setText("");
		errFax.setText("");
		if (error) {

			if (Nome.isEmpty())
				errNome.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (Indirizzo.isEmpty())
				errIndirizzo.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (Cap.isEmpty())
				errCap.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (Citta.isEmpty())
				errCitta.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (Provincia.isEmpty())
				errProvincia.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (Telefono.isEmpty())
				errTelefono.setText("<html><font color='red'>Il campo è vuoto.</font></html>");

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
		return true;
	}
	protected void SaveData(String Nome, String Indirizzo, int Cap, String Citta, String Provincia, long Telefono, long Fax, String Email, String SitoWeb) throws SQLException {
		Statement st=conn.createStatement();
		st.execute("INSERT INTO Fornitori (Nome, Indirizzo, Cap, Citta, Provincia, Telefono, Fax, eMail, SitoWeb) VALUES (\""+Nome+"\", \""+Indirizzo+"\", "+Cap+", \""+Citta+"\", \""+Provincia+"\", "+Telefono+", "+ Fax+", \""+Email+"\", \""+SitoWeb+"\");");
		st.close();
		
		
	}
}
