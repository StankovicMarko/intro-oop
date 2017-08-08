package gui.obrisani;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import filmovi.Film;
import osobe.Clan;
import videoteka.Videoteka;

public class ClanoviObrisani extends JDialog{
	private Videoteka videoteka;
	private JScrollPane tableScroll;
	private JTable clanTabela;
	private JToolBar mainToolbar;
	private JButton btnRestore;

	
	public ClanoviObrisani(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Obrisani Clanovi");
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setModal(true);
		setSize(700, 500);
		setResizable(true);
		initGUI();
		initActions();
	}
	
	private void initGUI() {
		mainToolbar = new JToolBar();
		ImageIcon iconAdd = new ImageIcon(getClass().getResource("/slike/redo.png"));
		btnRestore = new JButton(iconAdd);
		mainToolbar.add(btnRestore);
		add(mainToolbar, BorderLayout.NORTH);
		
		String[] zaglavlja = new String[] {"Br. clanske karte", "Ime", "Prezime", "Pol",
				 "JMBG", "Adresa", "Aktivan"};

		int brojClanova = 0;
		for (Clan c: videoteka.getClanovi()) {
		if( c.isAktivan() == false){
		
			brojClanova++;
			}
		
		}
		
		Object[][] podaci  = new Object[brojClanova]
						[zaglavlja.length];
		int j =0;
		for(int i=0; i<this.videoteka.getClanovi().size(); i++) {
			Clan clanovi = this.videoteka.getClanovi().get(i);
			if(clanovi.isAktivan()==false){
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
		clanTabela = new JTable(model);
		clanTabela.setColumnSelectionAllowed(false);
		clanTabela.setRowSelectionAllowed(true);
		clanTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		clanTabela.setDefaultEditor(Object.class, null);
		
		tableScroll = new JScrollPane(clanTabela);
		add(tableScroll, BorderLayout.CENTER);
		}
	private void initActions(){
		btnRestore.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selektovaniRed = ClanoviObrisani.this.clanTabela.getSelectedRow();
				if(selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati clana.");
				}else {
					int izbor = JOptionPane.showConfirmDialog(null, "Da li ste sigurni",
							"Restore clana", JOptionPane.YES_NO_OPTION);
					
					if(izbor == JOptionPane.YES_OPTION) {
						String brojCK = (String)
								ClanoviObrisani.this.clanTabela.getValueAt(selektovaniRed, 0);
						Clan clan= ClanoviObrisani.this.videoteka.pronadjiClana(brojCK);
						DefaultTableModel model = (DefaultTableModel)
								ClanoviObrisani.this.clanTabela.getModel();
						clan.setAktivan(true);
						ClanoviObrisani.this.videoteka.sacuvajClanove();
						model.removeRow(selektovaniRed);
					}
				}
				
			}

			
		});}
}

		
			
		
	

