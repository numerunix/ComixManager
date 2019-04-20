package org.altervista.numerone.comixmanager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class PaneDipendenti extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1880258538704792289L;

	protected Connection conn;

	protected JTextField Nome,
				Cognome,
				Cellulare,
				NomeUtente;
	protected JPasswordField Password;
	protected JLabel errNome,
			errCognome,
			errCellulare,
			errNomeUtente,
			errPassword,
			errNegozio,
			errOperazione;
	protected JComboBox<String> Negozi;
	
	private JButton ok;
	
	protected 	LinkedList<Negozio> n;

	public PaneDipendenti(Connection conn) {
		//super(parent, "Aggiungi Dipendente", Dialog.ModalityType.DOCUMENT_MODAL);
		setLayout(new GridBagLayout());
		this.conn=conn;
		GridBagConstraints c=new GridBagConstraints();
		setSize(400, 400);
		c.fill=GridBagConstraints.BOTH;
		c.gridheight=1;
		c.gridwidth=1;
		c.gridx=0;
		c.gridy=0;
		add(new JLabel("Nome"), c);
		c.gridy=1;
		add(new JLabel("Cognome"), c);
		c.gridy=2;
		add(new JLabel("Cellulare"), c);
		c.gridy=3;
		add(new JLabel("Negozio"), c);
		c.gridy=4;
		add(new JLabel("Nome  Utente"), c);
		c.gridy=5;
		add(new JLabel("Password"), c);
		
		Nome=new JTextField(45);
		Cognome=new JTextField(45);
		Cellulare=new JTextField(45);
		NomeUtente=new JTextField(45);
		Password=new JPasswordField(45);
		Negozi=new JComboBox<String>();
		
		c.gridx=1;
		c.gridy=0;
		add(Nome, c);
		c.gridy=1;
		add(Cognome, c);
		c.gridy=2;
		add(Cellulare, c);
		c.gridy=3;
		add(Negozi, c);
		c.gridy=4;
		add(NomeUtente, c);
		c.gridy=5;
		add(Password, c);
		
		c.gridx=2;
		c.gridy=0;
		add(errNome=new JLabel("Obbligatorio"), c);
		c.gridy=1;
		add(errCognome=new JLabel("Obbligatorio"), c);		
		c.gridy=2;
		add(errCellulare=new JLabel("Obbligatorio"), c);	
		c.gridy=3;
		add(errNegozio=new JLabel("Obbligatorio"), c);
		c.gridy=4;
		add(errNomeUtente=new JLabel("Obbligatorio"), c);
		c.gridy=5;
		add(errPassword=new JLabel("Obbligatorio"), c);
		
		c.gridy=6;
		c.gridx=0;
		
		ok=new JButton("Ok");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				errOperazione.setText("");
				String password=new String(Password.getPassword());
				if (ProcessData(Nome.getText(), Cognome.getText(), Cellulare.getText(), NomeUtente.getText(), password)) {
				try {
						SaveData(Nome.getText(), Cognome.getText(), Cellulare.getText(), n.get(Negozi.getSelectedIndex()).getId(), NomeUtente.getText(), password);
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

		c.gridy=7;
		c.gridx=0;
		c.gridheight=1;
		c.gridwidth=2;
		add(errOperazione=new JLabel("<html><font color='green'>Ok.</font></html>"), c);
		//pack();
		RiempiComboBox(conn);
		if (n.size()==0) {
			errOperazione.setText("<html><font color='red'>Non ci sono negozi salvate.</font></html>");
			ok.setEnabled(false);
			return;
		}
	}
	
	private void RiempiComboBox(Connection conn) {
		try {
			n = Negozio.getNegozi(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			errOperazione.setText(e.getLocalizedMessage());
			return;
		}
		for (int i=0; i<n.size(); i++)
			Negozi.addItem(n.get(i).getNome());
	}
	
	protected boolean ProcessData(String Nome, String Cognome, String Cellulare,String NomeUtente, String Password) {
		boolean error=Nome.isEmpty() || Cognome.isEmpty() || Cellulare.isEmpty() || NomeUtente.isEmpty() || Password.isEmpty();
		errNome.setText("");
		errCognome.setText("");
		errCellulare.setText("");
		errNomeUtente.setText("");
		errPassword.setText("");
		if (error) {

			if (Nome.isEmpty())
				errNome.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (Cognome.isEmpty())
				errCognome.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (Cellulare.isEmpty())
				errCellulare.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (NomeUtente.isEmpty())
				errNomeUtente.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (Password.isEmpty())
				errPassword.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			return false;
		}
		try {
			Long.parseLong(Cellulare);
		} catch (NumberFormatException e)  {
			errCellulare.setText("<html><font color='red'>Il campo non è numerico.</font></html>");
			return false;
		}
		return true;
	}
	
	protected void SaveData(String Nome, String Cognome, String Cellulare, int Negozio, String NomeUtente, String Password) throws SQLException {
		Statement st=conn.createStatement();
		st.execute("INSERT INTO Dipendenti (Nome, Cognome, Cellulare, idNegozio, UserName, Password) VALUES (\""+Nome+"\", \""+Cognome+"\", \""+Cellulare+"\", "+Negozio+", \""+NomeUtente+"\", \""+Password+"\");");
		st.execute("CREATE USER '"+NomeUtente+"' IDENTIFIED BY '"+Password+"';");
		st.execute("GRANT USAGE ON *.* TO '"+NomeUtente+"'@localhost IDENTIFIED BY '"+Password+"';");
		st.execute("GRANT ALL privileges ON `fumetteria`.* TO '"+NomeUtente+"'@localhost;");
		st.execute("FLUSH PRIVILEGES;");
		st.close();
		
		
	}
}
