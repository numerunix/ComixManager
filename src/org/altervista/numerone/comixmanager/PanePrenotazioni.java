package org.altervista.numerone.comixmanager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanePrenotazioni extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -485842523580328035L;
	protected JTextField CodiceABarre,
							IdCasella,
							Acconto;
	protected JComboBox<String> Negozi;
	protected JLabel NomeArticolo,
					Nome,
					Cognome,
					errCodiceABarre,
					errCasella,
					errNome,
					errCognome,
					errAcconto,
					errNegozi,
					errOperazione;
	protected Connection Conn;
	protected Dipendente dipendente;
	protected Articolo articolo;
	protected Cliente cliente;
	protected JButton ok;
	protected LinkedList<Negozio> negozi;
	
	public PanePrenotazioni(Connection Conn, Dipendente dip) {
		setLayout(new GridBagLayout());
		this.Conn=Conn;
		dipendente=dip;
		articolo=null;
		cliente=null;
		GridBagConstraints c=new GridBagConstraints();
		//setSize(700, 400);
		c.fill=GridBagConstraints.BOTH;
		c.gridheight=1;
		c.gridwidth=1;
		c.gridx=0;
		c.gridy=0;
		add(new JLabel("Codice a Barre"), c);
		c.gridy=1;
		add(new JLabel("Nome articolo"), c);
		c.gridy=2;
		add(new JLabel("Id Casella"), c);
		c.gridy=3;
		add(new JLabel("Nome"), c);
		c.gridy=4;
		add(new JLabel("Cognome"), c);
		c.gridy=5;
		add(new JLabel("Acconto"), c);
		c.gridy=6;
		add(new JLabel("Negozio"), c);
		c.gridx=1;
		c.gridy=0;
		add(CodiceABarre=new JTextField(45), c);
		c.gridy=1;
		add(NomeArticolo=new JLabel("Non disponibile"), c);
		c.gridy=2;
		add(IdCasella=new JTextField(45), c);
		IdCasella.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				errOperazione.setText("");
				errCasella.setText("");
				int casella;
				try {
					casella=Integer.parseInt(IdCasella.getText());
				} catch (java.lang.NumberFormatException e) {
					errCasella.setText("<html><font color='red'>Il numero non è intero</font></html>");
					return;
				}
				try {
					cliente=Cliente.getCliente(Conn, casella);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					errOperazione.setText("<html><font color='red'>"+e.getLocalizedMessage()+"</font></html>");
					return;
				}
				if (cliente==null) {
					Nome.setText("Non trovato");
					Cognome.setText("Non trovato");
					ok.setEnabled(false);
					
				} else {
					Nome.setText(cliente.getNome());
					Cognome.setText(cliente.getCognome());
					ok.setEnabled(articolo!=null);
				}
			}
		});
		c.gridy=3;
		add(Nome=new JLabel("Non selezionato"), c);
		c.gridy=4;
		add(Cognome=new JLabel("Non selezionato"), c);
		c.gridy=5;
		add(Acconto=new JTextField(45), c);
		c.gridy=6;
		add(Negozi=new JComboBox(), c);
		c.gridx=2;
		c.gridy=0;
		add(errCodiceABarre=new JLabel("Obbligatorio"), c);
		c.gridy=2;
		add(errCasella=new JLabel("Obbligatorio"), c);
		c.gridy=3;
		add(errNome=new JLabel("Obbligatorio"), c);
		c.gridy=4;
		add(errCognome=new JLabel("Obbligatorio"), c);
		c.gridy=5;
		add(errAcconto=new JLabel("Obbligatorio"), c);
		
		CodiceABarre.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				errOperazione.setText("");
				errCodiceABarre.setText("");
				articolo=Articolo.searchFromCodice(Conn, CodiceABarre.getText());
				if (articolo==null) {
					NomeArticolo.setText("Codice a barre non trovato");
					ok.setEnabled(false);
					
				} else {
					NomeArticolo.setText(articolo.getDescrizione()); 
					ok.setEnabled(cliente!=null);
				}
			}
		});
		
		ok=new JButton("Ok");
		c.gridx=0;
		c.gridy=7;
		add(ok, c);
		c.gridx=0;
		c.gridy=8;
		c.gridwidth=3;
		add(errOperazione=new JLabel("<html><font color='green'>Ok</font></html>"), c);
		RiempiComboBox(Conn);
		ok.setEnabled(false);
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ProcessData(Acconto.getText());
				try {
					SaveData(cliente, articolo, negozi.get(Negozi.getSelectedIndex()).getId(), Integer.parseInt(Acconto.getText()));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					errOperazione.setText("<html><font color='red'>"+e.getLocalizedMessage()+"</font></html>");
					return;
				}
				errOperazione.setText("<html><font color='green'>Operazione effettuata.</font></color>");
			}
		});		
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
	
	protected boolean ProcessData(String Acconto) {
		boolean error=articolo==null || cliente==null || Acconto.isEmpty();
		errAcconto.setText("");
		errCodiceABarre.setText("");
		errNome.setText("");
		if (error) {

			if (articolo==null)  {
				errCodiceABarre.setText("<html> <fonto color='red'>Il campo è vuoto.</font></html>");
				return false;
			}
			if (cliente==null) {
				errNome.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
				return false;
			}
			if (Acconto.isEmpty()) {
				errAcconto.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
				return false;				
			}
		}
		try {
			Integer.parseInt(Acconto);
		} catch (java.lang.NumberFormatException e) {
			errAcconto.setText("<html><font color='red'>Il campo non è numerico.</font></html>");
			return false;
		}
		
		return true;
	}
	
	protected void SaveData(Cliente c, Articolo a, int negozio, int acconto) throws SQLException {
		Statement st=Conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT idOggetto FROM Oggetti WHERE idArticolo="+a.getId() + " AND  idNegozio="+negozio+ ";");
		if (!rs.next()) {
			errOperazione.setText("<html><font color='red'>Impossibile trovare l'oggetto</font></html>");
			rs.close();
			return;
		}
		long idOggetto=rs.getLong("idOggetto");
		rs.close();
		st.execute("INSERT INTO Prenotazioni (DataPrenotazione, Acconto, idCliente, idTransazione, idOggetto, Ritirata) VALUES ((SELECT NOW()), "+acconto+", "+cliente.getId()+", "+System.currentTimeMillis()+", "+idOggetto+", 0);");
		st.close();
	}
}


