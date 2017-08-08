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
import gui.dodavanje.ClanoviDodavanjeDialog;
import gui.obrisani.ClanoviObrisani;
import gui.obrisani.FilmObrisani;
import osobe.Clan;
import videoteka.Videoteka;

public class ClanoviForma extends JFrame{

	private JToolBar mainToolbar;
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnDelete;
	private JButton btnObrisani;
	private JScrollPane tableScroll;
	private JTable clanoviTabela;
	private Videoteka videoteka;
	
	public ClanoviForma(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Prikaz Clanova");
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
		
		add(mainToolbar, BorderLayout.NORTH);
		
		String[] zaglavlja = new String[] {"Br. clanske karte", "Ime", "Prezime", "Pol",
										 "JMBG", "Adresa", "Aktivan"};
		
		int brojClanova = 0;
		for (Clan c: videoteka.getClanovi()) {
			if( c.isAktivan()){
				
				brojClanova++;
			}
			
		}
		
		Object[][] podaci  = new Object[brojClanova]
										[zaglavlja.length];
		int j =0;
		for(int i=0; i<this.videoteka.getClanovi().size(); i++) {
			Clan clanovi = this.videoteka.getClanovi().get(i);
			if(clanovi.isAktivan()){
			podaci[j][0] = clanovi.getBrojClanskeKarte();
			podaci[j][1] = clanovi.getIme();
			podaci[j][2] = clanovi.getPrezime();
			podaci[j][3] = clanovi.getPol();
			podaci[j][4] = clanovi.getJMBG();
			podaci[j][5] = clanovi.getAdresa();
			podaci[j][6] = clanovi.isAktivan();
			
			j++;
		}}
		j=0;
		DefaultTableModel model = new DefaultTableModel(podaci, zaglavlja);
		clanoviTabela = new JTable(model);
		clanoviTabela.setColumnSelectionAllowed(false);
		clanoviTabela.setRowSelectionAllowed(true);
		clanoviTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		clanoviTabela.setDefaultEditor(Object.class, null);
		
		tableScroll = new JScrollPane(clanoviTabela);
		add(tableScroll, BorderLayout.CENTER);
	}
	
	public JTable getClanoviTabela() {
		return clanoviTabela;
	}

	private void initActions() {
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selektovaniRed = ClanoviForma.this.clanoviTabela.getSelectedRow();
				if(selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati clana.");
				}else {
					int izbor = JOptionPane.showConfirmDialog(null, "Da li ste sigurni",
							"Brisanje clana", JOptionPane.YES_NO_OPTION);
					
					if(izbor == JOptionPane.YES_OPTION) {
						String brojCK = (String)
								ClanoviForma.this.clanoviTabela.getValueAt(selektovaniRed, 0);
						Clan clan = ClanoviForma.this.videoteka.pronadjiClana(brojCK);
						DefaultTableModel model = (DefaultTableModel)
								ClanoviForma.this.clanoviTabela.getModel();
						clan.setAktivan(false);
						//model.setValueAt(false, selektovaniRed, 6);
						ClanoviForma.this.videoteka.sacuvajClanove();
						
						model.removeRow(selektovaniRed);
					}
				}
			}
		});
		
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ClanoviDodavanjeDialog cdd = new ClanoviDodavanjeDialog(
													ClanoviForma.this.videoteka, 
													null, 
													ClanoviForma.this);
				cdd.setVisible(true);
			}
		});
		
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selektovaniRed = ClanoviForma.this.clanoviTabela.getSelectedRow();
				if(selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, 
							"Morate odabrati clana u tabeli.");
					
				}else {
					String brojCK = (String)
							ClanoviForma.this.clanoviTabela.getValueAt(selektovaniRed, 0);
					Clan clan= ClanoviForma.this.videoteka.pronadjiClana(brojCK);
					ClanoviDodavanjeDialog cdd = new ClanoviDodavanjeDialog(
													ClanoviForma.this.videoteka, 
													clan, 
													ClanoviForma.this);
						cdd.setVisible(true);
				}
			}
		});
		
		
		btnObrisani.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ClanoviObrisani co = new ClanoviObrisani(
						ClanoviForma.this.videoteka);
						co.setVisible(true);
			}
		});
		
	}
}