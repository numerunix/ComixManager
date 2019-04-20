package org.altervista.numerone.comixmanager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PaneAcquisti extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -485842523580328035L;
	protected JTextField CodiceABarre,
							IdCasella,
							Prezzo,
							PrezzoScontato;
	protected JComboBox<String> Negozi;
	protected JLabel Nome,
					Cognome,
					errCodiceABarre,
					errCasella,
					errNome,
					errCognome,
					errPrezzo,
					errPrezzoScontato,
					errNegozi,
					errOperazione;
	protected JList<String> Oggetti;
	protected Connection Conn;
	protected Dipendente dipendente;
	protected LinkedList<Articolo> articoli;
	protected LinkedList<Oggetto> oggetti;
	protected Cliente cliente;
	protected JButton ok;
	protected LinkedList<Negozio> negozi;
	protected DefaultListModel<String> ModelloArticoli;
	
	public PaneAcquisti(Connection Conn, Dipendente dip) {
		setLayout(new GridBagLayout());
		this.Conn=Conn;
		dipendente=dip;
		ModelloArticoli=new DefaultListModel<String>();
		articoli=new LinkedList<Articolo>();
		oggetti=new LinkedList<Oggetto>();
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
		add(new JLabel("Id Casella"), c);
		c.gridy=2;
		add(new JLabel("Nome"), c);
		c.gridy=3;
		add(new JLabel("Cognome"), c);
		c.gridy=4;
		add(new JLabel("Prezzo"), c);
		c.gridy=5;
		add(new JLabel("Prezzo Scontato"), c);
		c.gridy=6;
		add(new JLabel("Negozio"), c);
		c.gridx=1;
		c.gridy=0;
		add(CodiceABarre=new JTextField(45), c);
		c.gridy=1;
		add(IdCasella=new JTextField(45), c);
		IdCasella.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				float prezzo=0, prezzoScontato=0;
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
					ok.setEnabled(articoli.size()>0);
					for (int i=0; i<oggetti.size(); i++) {
						prezzo+=oggetti.get(i).getPrezzo()*oggetti.get(i).getVendita();
						prezzoScontato=prezzoScontato+(oggetti.get(i).getPrezzo()-oggetti.get(i).getPrezzo()*(oggetti.get(i).getSconto()+cliente.getSconto())/100)*oggetti.get(i).getVendita();
					}
					PrezzoScontato.setText(prezzoScontato+"");
				}
			}
			
		});
		c.gridy=2;
		add(Nome=new JLabel("Non selezionato"), c);
		c.gridy=3;
		add(Cognome=new JLabel("Non selezionato"), c);
		c.gridy=4;
		add(Prezzo=new JTextField(45), c);
		c.gridy=5;
		add(PrezzoScontato=new JTextField(45), c);
		c.gridy=6;
		add(Negozi=new JComboBox(), c);
		Negozi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				float prezzo=0,
						prezzoScontato=0;
				Oggetto o=null;
				oggetti.clear();
				ModelloArticoli.clear();
				try {
					for (int i=0; i<articoli.size(); i++) {
						o=Oggetto.getOggetto(Conn, articoli.get(i).getId(), negozi.get(Negozi.getSelectedIndex()).getId());
						if (o==null || o.getQuantita()==0) {
							errOperazione.setText("<html><font color='red'>Trovati oggetti non presenti nel negozio.</font></html>");
						} else {
							oggetti.add(o);
							ModelloArticoli.addElement(articoli.get(i).getDescrizione());
						}
					}
				} catch (SQLException e) {
						// TODO Auto-generated catch block
					errOperazione.setText(e.getLocalizedMessage());
					return;
				}
				for (int i=0; i<oggetti.size(); i++) {
					prezzo+=o.getPrezzo()*o.getVendita();
					prezzoScontato=prezzoScontato+(o.getPrezzo()-o.getPrezzo()*(o.getSconto()+cliente.getSconto())/100)*o.getVendita();
				}
				Prezzo.setText(prezzo+"");
				PrezzoScontato.setText(prezzoScontato+"");
			}});
		Oggetti=new JList<String>(ModelloArticoli);
		Oggetti.setVisibleRowCount(1);
		Oggetti.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		c.gridx=2;
		c.gridy=0;
		add(errCodiceABarre=new JLabel("Obbligatorio"), c);
		c.gridy=1;
		add(errCasella=new JLabel("Obbligatorio"), c);
		c.gridy=2;
		add(errNome=new JLabel(""), c);
		c.gridy=3;
		add(errCognome=new JLabel(""), c);
		c.gridy=4;
		add(errPrezzo=new JLabel("Obbligatorio"), c);
		c.gridy=5;
		add(errPrezzoScontato=new JLabel(""), c);
		CodiceABarre.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Articolo articolo;
				Oggetto o=null;
				float prezzo=0,
						prezzoScontato=0;
				int	 i=0;
				errOperazione.setText("");
				errCodiceABarre.setText("");
				articolo=Articolo.searchFromCodice(Conn, CodiceABarre.getText());
				if (articolo==null) {
					//errArticolo.setText("Codice a barre non trovato");
					ok.setEnabled(oggetti.size()>0);
					return;
				} else {
					for (i=0; i<articoli.size() && articoli.get(i).getId()!=articolo.getId(); i++);
					if (i==articoli.size() || articoli.size()==0) {
						try {
							o=Oggetto.getOggetto(Conn, articolo.getId(), negozi.get(Negozi.getSelectedIndex()).getId());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							errOperazione.setText("<html><font color='red'>"+e.getLocalizedMessage()+"</font></html>");
							return;
						}
						if (o==null || o.getQuantita()==0) {
							errOperazione.setText("<html><font color='red'>Oggetto non presente nel negozio.</font></html>");
							return;
						} else {
							articoli.add(articolo);
							ModelloArticoli.addElement(articolo.getDescrizione()+" "+articolo.getNumero());
							o.incrementaVendita();
							oggetti.add(o);
							ok.setEnabled(oggetti.size()>0);
						}
					} else {
						o=oggetti.get(i);
						if (!o.incrementaVendita()) {
							errOperazione.setText("<html><font color=red>La quantità non è disponibile.</font></html>");
							return;
						}
					}
				}				

				if (!Prezzo.getText().isEmpty())
					prezzo=Float.parseFloat(Prezzo.getText());
				prezzo+=o.getPrezzo()*o.getVendita();
				Prezzo.setText(prezzo+"");				
				if (!PrezzoScontato.getText().isEmpty())
					prezzoScontato=Float.parseFloat(PrezzoScontato.getText());
				prezzoScontato=prezzoScontato+(o.getPrezzo()-o.getPrezzo()*(o.getSconto()+cliente.getSconto())/100)*o.getVendita();
				PrezzoScontato.setText(prezzoScontato+"");
			}
		});
		
		ok=new JButton("Ok");
		c.gridx=0;
		c.gridy=7;
		add(ok, c);
		c.gridy=8;
		c.gridwidth=3;
		add(errOperazione=new JLabel("<html><font color='green'>Ok</font></html>"), c);
		RiempiComboBox(Conn);
		ok.setEnabled(false);
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				float prezzo, prezzoscontato=0;
				ProcessData(Prezzo.getText(), PrezzoScontato.getText());
				prezzo=Float.parseFloat(Prezzo.getText());
				if (!PrezzoScontato.getText().isEmpty())
					prezzoscontato=Float.parseFloat(PrezzoScontato.getText());					
				try {
					SaveData(cliente, oggetti, prezzo, prezzoscontato);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					errOperazione.setText("<html><font color='red'>"+e.getLocalizedMessage()+"</font></html>");
					return;
				}
				errOperazione.setText("<html><font color='green'>Operazione effettuata.</font></color>");
			}
		});
		
		c.gridy=11;
		c.gridx=0;
		c.gridheight=1;
		c.gridwidth=1;
		Oggetti.setVisibleRowCount(5);
		add(Oggetti, c);
		Oggetti.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				float prezzo=0, prezzoScontato=0;
				oggetti.remove(Oggetti.getSelectedIndex());
				articoli.remove(Oggetti.getSelectedIndex());
				ModelloArticoli.remove(Oggetti.getSelectedIndex());
				ok.setEnabled(oggetti.size()>0);
				for (int i=0; i<oggetti.size(); i++) {
					prezzo+=oggetti.get(i).getPrezzo()*oggetti.get(i).getVendita();
					prezzoScontato=prezzoScontato+(oggetti.get(i).getPrezzo()-oggetti.get(i).getPrezzo()*(oggetti.get(i).getSconto()+cliente.getSconto())/100)*oggetti.get(i).getVendita();
				}
				PrezzoScontato.setText(prezzoScontato+"");
				Prezzo.setText(prezzo+"");
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}});
		try {
			cliente=Cliente.getCliente(Conn, 0);
			Nome.setText(cliente.getNome());
			Cognome.setText(cliente.getCognome());
			IdCasella.setText("0");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			errOperazione.setText("<html><font color='red'>Impossibile caricare il cliente 0</font></html>");
		}

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
	
	protected boolean ProcessData(String Prezzo, String PrezzoScontato) {
		boolean error=articoli.size()==0 || Prezzo.isEmpty();
		errPrezzo.setText("");
		errPrezzoScontato.setText("");
		errCodiceABarre.setText("");
		errNome.setText("");
		if (error) {

			if (articoli.size()==0)  {
				errCodiceABarre.setText("<html> <fonto color='red'>Nessun articolo impostato.</font></html>");
				return false;
			}
			if (Prezzo.isEmpty()) {
				errPrezzo.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
				return false;				
			}
		}
		try {
			Integer.parseInt(Prezzo);
		} catch (java.lang.NumberFormatException e) {
			errPrezzo.setText("<html><font color='red'>Il campo non è numerico.</font></html>");
			return false;
		}
		if (!PrezzoScontato.isEmpty()) {
			try {
				Integer.parseInt(PrezzoScontato);
			} catch (java.lang.NumberFormatException e) {
				errPrezzoScontato.setText("<html><font color='red'>Il campo non è numerico.</font></html>");
				return false;
			}
			try {
				Integer.parseInt(PrezzoScontato);
			} catch (java.lang.NumberFormatException e) {
				errPrezzoScontato.setText("<html><font color='red'>Il campo non è numerico.</font></html>");
				return false;
			}
		}
		try {
			Integer.parseInt(Prezzo);
		} catch (java.lang.NumberFormatException e) {
			errPrezzo.setText("<html><font color='red'>Il campo non è numerico.</font></html>");
			return false;
		}
		return true;
	}
	
	protected void SaveData(Cliente c, LinkedList<Oggetto> o, float Prezzo, float PrezzoScontato) throws SQLException {
		Statement st=Conn.createStatement();
		long idTransazione=System.currentTimeMillis();
		int cli=0;
		if (cliente!=null)
			cli=cliente.getId();
		for (int i=0; i<o.size(); i++) {
			st.execute("INSERT INTO Acquisti (DataAcquisto, idCliente, idTransazione, idOggetto, Prezzo, PrezzoScontato) VALUES ((SELECT NOW()), "+cli+", "+idTransazione+", "+o.get(i).getId()+", "+Prezzo+", "+PrezzoScontato+");");
			st.execute("UPDATE Oggetti SET Quantita="+(o.get(i).getQuantita()-o.get(i).getVendita())+ " WHERE idOggetto="+o.get(i).getId()+";");
		}
		st.close();
	}
}



