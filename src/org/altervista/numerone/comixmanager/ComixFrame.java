package org.altervista.numerone.comixmanager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;

public class ComixFrame extends JFrame {

	/**
	 * 
	 */

	Dipendente dipendente;
	
	private static final long serialVersionUID = 6693223865201805102L;
	private static Connection Conn;
	private JLabel nome;
	private JTree albero;
	private JTabbedPane pannello;
	private JButton chiudiScheda;
	public ComixFrame() {
		super("ComixManager 0.1");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(new GridBagLayout());
		GridBagConstraints c=new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		//pack();
		final JFrame parent=this;
		JMenuBar b=new JMenuBar();
		JMenu file=new JMenu("File");
		JMenuItem connetti=new JMenuItem("Connetti");
		connetti.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dipendente=OnFrameConnessione();
				nome.setText("Benvenuto "+dipendente.getNome()+" "+dipendente.getCognome());				
			}});
		file.add(connetti);
		JMenuItem disconnetti=new JMenuItem("Disconnetti");
		disconnetti.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (Conn!=null)
					try {
						Conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
					}
				
			}});
		file.add(disconnetti);
		JMenuItem esci=new JMenuItem("Esci");
		esci.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
					dispose();
				
			}});
		file.add(esci);
		b.add(file);
		JMenu about=new JMenu("?");
		JMenuItem info=new JMenuItem("Informazioni");
		info.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				OnInfo();
			}
			
		});
		about.add(info);
		b.add(about);
		setJMenuBar(b);
		
		DefaultMutableTreeNode aggiungi=new DefaultMutableTreeNode("Aggiungi");
		aggiungi.add(new DefaultMutableTreeNode("Articolo"));
		aggiungi.add(new DefaultMutableTreeNode("Casa Editrice"));
		aggiungi.add(new DefaultMutableTreeNode("Categoria"));
		aggiungi.add(new DefaultMutableTreeNode("Clienti"));
		aggiungi.add(new DefaultMutableTreeNode("Collana"));
		aggiungi.add(new DefaultMutableTreeNode("Dipendente"));
		aggiungi.add(new DefaultMutableTreeNode("Editore"));
		aggiungi.add(new DefaultMutableTreeNode("Fornitore"));
		aggiungi.add(new DefaultMutableTreeNode("Negozio"));
		aggiungi.add(new DefaultMutableTreeNode("Oggetto"));
		aggiungi.add(new DefaultMutableTreeNode("Prenotazione"));
		aggiungi.add(new DefaultMutableTreeNode("Acquisti"));
		albero=new JTree(aggiungi);
		
		dipendente=OnFrameConnessione();
		
		try {
			nome=new JLabel("Benvenuto "+dipendente.getNome()+" "+ dipendente.getCognome());
			add(nome, c);
		} catch (java.lang.NullPointerException e) {
			JOptionPane.showMessageDialog(this, "Non sei connesso, il programma si chiuderà.", "Errore", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		c.gridy=1;
		pannello=new JTabbedPane();
		albero.addTreeSelectionListener(new HandlerAlbero(this, Conn, dipendente, pannello));
		JScrollPane p=new JScrollPane();
		p.getViewport().add(albero);
		add(p,c);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (Conn!=null)
					try {
						Conn.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, e1.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
					}
			}
		});
		c.gridx=1;
		c.gridy=0;
		c.gridwidth=4;
		c.gridheight=4;
		add(pannello, c);
		chiudiScheda=new JButton("x");
		chiudiScheda.setBorder(BorderFactory.createEmptyBorder());
		chiudiScheda.setFocusPainted(false);
		chiudiScheda.setBorderPainted(false);
		chiudiScheda.setContentAreaFilled(false);
		chiudiScheda.setRolloverEnabled(false);
		chiudiScheda.setPreferredSize(new Dimension(16, 16));
		chiudiScheda.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					pannello.removeTabAt(pannello.getSelectedIndex());
				} catch (java.lang.IndexOutOfBoundsException e) {
					return;
				}
			}});
		c.gridx=5;
		c.gridy=0;
		add(chiudiScheda, c);
		PaneAcquisti f = new PaneAcquisti(Conn, dipendente);
		pannello.addTab("Acquisti", f);
		pannello.setSelectedIndex(pannello.getTabCount()-1);
		parent.pack();
	}
	
	protected void OnInfo() {
		JPanel p=new JPanel(new BorderLayout(5,5));
		JPanel l=new JPanel(new GridLayout(0,1,4,4));
		l.add(new JLabel("ComixManager ", SwingConstants.RIGHT));
		l.add(new JLabel("Copyright ", SwingConstants.RIGHT));
		l.add(new JLabel("Autore: ", SwingConstants.RIGHT));

		l.add(new JLabel("Licenza: ", SwingConstants.RIGHT));
		JPanel l1=new JPanel(new GridLayout(0,1,4,4));
		l1.add(new JLabel("0.1", SwingConstants.LEFT));
		l1.add(new JLabel("2019", SwingConstants.LEFT));
		l1.add(new JLabel("Giulio Sorrentino", SwingConstants.LEFT));
		l1.add(new JLabel("GPL v3 o, stando alla tua opinione, qualsiasi versione successiva", SwingConstants.LEFT));
        p.add(l, BorderLayout.WEST);
        p.add(l1, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(this, p, "Informazioni", JOptionPane.INFORMATION_MESSAGE);
	}
	
	protected Dipendente OnFrameConnessione() {
		FrameConnessione f=new FrameConnessione(this,Conn);
		f.setVisible(true);
		Conn=f.GetConn();
		return f.getDipendente();
	}
	
	public static void main(String[] args) {
		ComixFrame f=new ComixFrame();
		f.setVisible(true);
	}
	
}
