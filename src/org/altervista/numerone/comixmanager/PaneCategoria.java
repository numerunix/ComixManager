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
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PaneCategoria extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection conn;
	protected JTextField Nome;
	private JTextArea Note;
	protected JLabel errNome;
	private JButton ok;
	protected JLabel errOperazione;
	
	public PaneCategoria(Connection conn) {
		//super(parent, "Aggiungi Categoria", Dialog.ModalityType.DOCUMENT_MODAL);
		setLayout(new GridBagLayout());
		this.conn=conn;
		GridBagConstraints c=new GridBagConstraints();
		//setSize(700, 400);
		c.fill=GridBagConstraints.BOTH;
		c.gridheight=1;
		c.gridwidth=1;
		c.gridx=0;
		c.gridy=0;
		add(new JLabel("Nome"), c);
		c.gridy=1;
		add(new JLabel("Note"), c);
		c.gridy=2;
		
		Nome=new JTextField(45);
		c.gridx=1;
		c.gridy=0;
		add(Nome, c);
		c.gridy=0;
		c.gridx=2;
		add(errNome=new JLabel("Obbligatorio"), c);
		c.gridy=2;
		c.gridx=0;
		c.gridwidth=3;
		c.gridheight=5;
		Note=new JTextArea(5, 5);
		add(Note, c);

		c.gridy=9;
		c.gridx=0;
		c.gridheight=1;
		c.gridwidth=1;
		ok=new JButton("Ok");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				errOperazione.setText("");
				if (ProcessData(Nome.getText())) {
				try {
						SaveData(Nome.getText(), Note.getText());
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
	}
	
	protected boolean ProcessData(String Nome) {
		boolean error=Nome.isEmpty();
		errNome.setText("");
		if (error) {

			if (Nome.isEmpty())
				errNome.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			return false;
		}
		return true;
	}
	
	protected void SaveData(String Nome, String Note) throws SQLException {
		Statement st=conn.createStatement();
		st.execute("INSERT INTO Categoria (Nome, Note) VALUES (\""+Nome+"\", \""+Note+"\");");
		st.close();
		
		
	}
}
