package gui.prikaz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import filmovi.Film;
import filmovi.Primerak;
import gui.dodavanje.PrimerciDodavanjeDialog;
import gui.obrisani.PrimerciIzdati;
import gui.obrisani.PrimerciObrisani;
import gui.obrisani.ZanroviObrisani;
import videoteka.Videoteka;

public class PrimerciForma extends JFrame {

	private JToolBar mainToolbar;
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnDelete;
	private JButton btnObrisani;
	private JButton btnIzdati;
	private JScrollPane tableScroll;
	private JTable primerciTabela;
	private Videoteka videoteka;
	
	public PrimerciForma(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Prikaz Primeraka");
		setSize(700, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initGUI();
		initActions();
	}

	private void initGUI() {
		mainToolbar = new JToolBar();
		ImageIcon iconAdd = new ImageIcon(getClass().getResource("/slike/add.gif"));
		btnAdd = new JButton(iconAdd);
		mainToolbar.add(btnAdd);
		ImageIcon editIcon = new ImageIcon(getClass().getResource("/slike/edit.gif"));
		btnEdit = new JButton(editIcon);
		mainToolbar.add(btnEdit);
		ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/slike/remove.gif"));
		btnDelete = new JButton(deleteIcon);
		mainToolbar.add(btnDelete);
		btnObrisani = new JButton("Prikaz obrisanih");
		mainToolbar.add(btnObrisani);
		btnIzdati = new JButton("Prikaz izdatih");
		mainToolbar.add(btnIzdati);
		
		
		add(mainToolbar, BorderLayout.NORTH);
		
		String[] zaglavlja = new String[] {"Oznaka(ID)", "Ime Filma", "Medijum", "Kolicina"};
		
		int brojPrimeraka = 0;
		for (Primerak p: videoteka.getPrimerci()) {
			if( p.isObrisan() == false && p.isIzdat() == false){
				
				brojPrimeraka++;
			}
			
		}
		
		Object[][] podaci  = new Object[brojPrimeraka]
										[zaglavlja.length];
		
		int j =0;
		for(int i=0; i<this.videoteka.getPrimerci().size(); i++) {
			Primerak primerak = this.videoteka.getPrimerci().get(i);
			if(primerak.isObrisan() == false && primerak.isIzdat() == false){
			podaci[j][0] = primerak.getID();
			podaci[j][1] = primerak.getFilm().getNaslovSRB();
			podaci[j][2] = primerak.getMedijum();
			podaci[j][3] = primerak.getBrojMedijuma();
			j++;}

			
		}
		j=0;
		DefaultTableModel model = new DefaultTableModel(podaci, zaglavlja);
		primerciTabela = new JTable(model);
		primerciTabela.setColumnSelectionAllowed(false);
		primerciTabela.setRowSelectionAllowed(true);
		primerciTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		primerciTabela.setDefaultEditor(Object.class, null);
		
		tableScroll = new JScrollPane(primerciTabela);
		add(tableScroll, BorderLayout.CENTER);
	}
	
	public JTable getPrimerciTabela() {
		return primerciTabela;
	}

	private void initActions() {
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selektovaniRed = PrimerciForma.this.primerciTabela.getSelectedRow();
				if(selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati primerak.");
				}else {
					int izbor = JOptionPane.showConfirmDialog(null, "Da li ste sigurni",
							"Brisanje primerka", JOptionPane.YES_NO_OPTION);
					
					if(izbor == JOptionPane.YES_OPTION) {
						String id = (String)
								PrimerciForma.this.primerciTabela.getValueAt(selektovaniRed, 0);
						Primerak primerak= PrimerciForma.this.videoteka.pronadjiPrimerak(id);
						DefaultTableModel model = (DefaultTableModel)
								PrimerciForma.this.primerciTabela.getModel();
						primerak.setObrisan(true);
						//PrimerciForma.this.videoteka.getPrimerci().remove(primerak);
						PrimerciForma.this.videoteka.sacuvajPrimerke();
						model.removeRow(selektovaniRed);
					}
				}
			}
		});
		
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PrimerciDodavanjeDialog pdd = new PrimerciDodavanjeDialog(
												PrimerciForma.this.videoteka, 
													null, 
													PrimerciForma.this);
				pdd.setVisible(true);
			}
		});
		
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selektovaniRed = PrimerciForma.this.primerciTabela.getSelectedRow();
				if(selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, 
							"Morate odabrati primerak u tabeli.");
					
				}else {
					String id = (String)
							PrimerciForma.this.primerciTabela.getValueAt(selektovaniRed, 0);
					Primerak primerak= PrimerciForma.this.videoteka.pronadjiPrimerak(id);
					PrimerciDodavanjeDialog pdd = new PrimerciDodavanjeDialog(
												PrimerciForma.this.videoteka, 
													primerak, 
													PrimerciForma.this);
						pdd.setVisible(true);
				}
			}
		});
		
		btnObrisani.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				PrimerciObrisani po = new PrimerciObrisani(
						PrimerciForma.this.videoteka);
						po.setVisible(true);
			}
		});
		
		btnIzdati.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				PrimerciIzdati pi = new PrimerciIzdati(
						PrimerciForma.this.videoteka);
						pi.setVisible(true);
			}
		});
		
	}
}