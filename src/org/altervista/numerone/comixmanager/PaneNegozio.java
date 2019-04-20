package org.altervista.numerone.comixmanager;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class PaneNegozio extends Panel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5484638905585574486L;
	protected Connection conn;

	protected JTextField Nome,
				Indirizzo,
				Citta,
				Telefono;
	protected JLabel errNome,
			errIndirizzo,
			errCitta,
			errTelefono,
			errOperazione;
	
	private JButton ok;

	public PaneNegozio(Connection conn) {
		//super(parent, "Aggiungi Negozio", Dialog.ModalityType.DOCUMENT_MODAL);
		setLayout(new GridBagLayout());
		this.conn=conn;
		GridBagConstraints c=new GridBagConstraints();
		setSize(700, 300);
		c.fill=GridBagConstraints.BOTH;
		c.gridheight=1;
		c.gridwidth=1;
		c.gridx=0;
		c.gridy=0;
		add(new JLabel("Nome"), c);
		c.gridy=1;
		add(new JLabel("Indirizzo"), c);
		c.gridy=2;
		add(new JLabel("Città"), c);
		c.gridy=3;
		add(new JLabel("Telefono"), c);
		
		Nome=new JTextField(45);
		Indirizzo=new JTextField(45);
		Citta=new JTextField(45);
		Telefono=new JTextField(45);
		
		c.gridx=1;
		c.gridy=0;
		add(Nome, c);
		c.gridy=1;
		add(Indirizzo, c);
		c.gridy=2;
		add(Citta, c);
		c.gridy=3;
		add(Telefono, c);
		
		c.gridx=2;
		c.gridy=0;
		add(errNome=new JLabel("Obbligatorio"), c);
		c.gridy=1;
		add(errIndirizzo=new JLabel("Obbligatorio"), c);		
		c.gridy=2;
		add(errCitta=new JLabel("Obbligatorio"), c);	
		c.gridy=3;
		add(errTelefono=new JLabel("Obbligatorio"), c);
		
		c.gridy=4;
		c.gridx=0;
		
		ok=new JButton("Ok");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				errOperazione.setText("");
				if (ProcessData(Nome.getText(), Indirizzo.getText(), Citta.getText(), Telefono.getText())) {
					try {
						SaveData(Nome.getText(), Indirizzo.getText(), Citta.getText(), Telefono.getText());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						errOperazione.setText("<html><font color='red'>"+e.getLocalizedMessage()+"</font></html>");
						return;
					}
			//		dispose();
					errOperazione.setText("<html><font color='green'>Ok</font></html>");

				}
			}
		});
		add(ok, c);

		c.gridy=5;
		c.gridx=0;
		c.gridheight=1;
		c.gridwidth=2;
		add(errOperazione=new JLabel("<html><font color='green'>Ok.</font></html>"), c);
		//pack();
	}
	
	protected boolean ProcessData(String Nome, String Indirizzo, String Citta, String Telefono) {
		boolean error=Nome.isEmpty() || Indirizzo.isEmpty() || Citta.isEmpty() || Telefono.isEmpty();
		errNome.setText("");
		errIndirizzo.setText("");
		errCitta.setText("");
		errTelefono.setText("");
		if (error) {

			if (Nome.isEmpty())
				errNome.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (Indirizzo.isEmpty())
				errIndirizzo.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (Citta.isEmpty())
				errCitta.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (Telefono.isEmpty())
				errTelefono.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			return false;
		}
		try {
			Integer.parseInt(Telefono);
		} catch (NumberFormatException e)  {
			errTelefono.setText("<html><font color='red'>Il campo non è numerico.</font></html>");
			return false;
		}
		return true;
	}
	
	protected void SaveData(String Nome, String Indirizzo, String Citta, String Telefono) throws SQLException {
				Statement st=conn.createStatement();
				st.execute("INSERT INTO Negozi (Nome, Indirizzo, Citta, Telefono) VALUES (\""+Nome+"\", \""+Indirizzo+"\", \""+Citta+"\", \""+Telefono+"\");");
				st.close();
	}

}
