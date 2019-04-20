package org.altervista.numerone.comixmanager;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FrameConnessione extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8551674912968390435L;
	protected Connection conn;

	protected JTextField IP,
				Database,
				User;
	protected JPasswordField
				Password;
	protected JLabel errIp,
			errDatabase,
			errUsername,
			errPassword,
			errOperazione;
	
	private JButton ok;
	
	private Dipendente dipendente;
	
	public FrameConnessione(JFrame parent, Connection conn) {
		super(parent, "Connetti", Dialog.ModalityType.DOCUMENT_MODAL);
		setLayout(new GridBagLayout());
		this.conn=conn;
		GridBagConstraints c=new GridBagConstraints();
		setSize(400, 400);
		c.fill=GridBagConstraints.BOTH;
		c.gridheight=1;
		c.gridwidth=1;
		c.gridx=0;
		c.gridy=0;
		add(new JLabel("Ip"), c);
		c.gridy=1;
		add(new JLabel("Database"), c);
		c.gridy=2;
		add(new JLabel("User"), c);
		c.gridy=3;
		add(new JLabel("Password"), c);
		
		IP=new JTextField("127.0.0.1");
		Database=new JTextField("fumetteria");
		User=new JTextField("root");
		Password=new JPasswordField(45);
		
		c.gridx=1;
		c.gridy=0;
		add(IP, c);
		c.gridy=1;
		add(Database, c);
		c.gridy=2;
		add(User, c);
		c.gridy=3;
		add(Password, c);
		
		c.gridx=2;
		c.gridy=0;
		add(errIp=new JLabel("Obbligatorio"), c);
		c.gridy=1;
		add(errDatabase=new JLabel("Obbligatorio"), c);		
		c.gridy=2;
		add(errUsername=new JLabel("Obbligatorio"), c);	
		c.gridy=3;
		add(errPassword=new JLabel("Obbligatorio"), c);
		
		c.gridy=4;
		c.gridx=0;
		
		ok=new JButton("Ok");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String pwd=new String(Password.getPassword());
				errOperazione.setText("");
				if (ProcessData(IP.getText(), Database.getText(), User.getText(), pwd)) {
					try {
						try {
							Connect(IP.getText(), Database.getText(), User.getText(), pwd);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							errOperazione.setText("<html><font color='red'>"+e.getLocalizedMessage()+"</font></html>");
							Password.setText("");
							return;
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						errOperazione.setText("<html><font color='red'>"+e.getLocalizedMessage()+"</font></html>");
						Password.setText("");
						return;
					}
				dispose();
				}
			}
		});
		c.gridwidth=3;
		add(ok, c);
		c.gridwidth=1;
		c.gridx=1;
		c.gridy=5;
		c.gridx=0;
		c.gridheight=1;
		c.gridwidth=2;
		add(errOperazione=new JLabel("<html><font color='green'>Ok.</font></html>"), c);
		pack();
		Password.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ok.doClick();
			}
		});
	}
	
	protected boolean ProcessData(String IP, String Database, String User, String Password) {
		boolean error=IP.isEmpty() || Database.isEmpty() || User.isEmpty() || Password.isEmpty();
		errIp.setText("");
		errDatabase.setText("");
		errUsername.setText("");
		errPassword.setText("");
		if (error) {

			if (IP.isEmpty())
				errIp.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (Database.isEmpty())
				errDatabase.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (User.isEmpty())
				errUsername.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			if (Password.isEmpty())
				errPassword.setText("<html><font color='red'>Il campo è vuoto.</font></html>");
			return false;
		}
		return true;
	}
	
	protected void Connect(String ip, String db, String User, String pwd) throws ClassNotFoundException, SQLException  {
		Class.forName("org.mariadb.jdbc.Driver");
			// TODO Auto-generated catch block
		conn=DriverManager.getConnection("jdbc:mariadb://"+ip+"/"+db, User, pwd);
		if (!User.equals("root")) {
			Statement s=conn.createStatement();
			ResultSet rs=s.executeQuery("SELECT idDipendente, Nome, Cognome, Cellulare, idNegozio FROM Dipendenti WHERE UserName=\""+User+"\" AND Password=\""+pwd+"\";");
			rs.next();
			dipendente=new Dipendente(rs.getInt("idDipendente"), rs.getString("Nome"), rs.getString("Cognome"), rs.getLong("Cellulare"), rs.getInt("idNegozio"));
			rs.close();
			s.close();
		} else {
			dipendente=new Dipendente(0, "root", "", 0, 0);
		}
	}
	
	public Connection GetConn() {
		return conn;
	}
	
	public Dipendente getDipendente() {
		return dipendente;
	}
	
}
