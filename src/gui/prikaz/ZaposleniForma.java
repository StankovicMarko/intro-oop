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
import gui.dodavanje.ZaposleniDodavanjeDialog;
import gui.obrisani.FilmObrisani;
import gui.obrisani.ZaposleniObrisani;
import iznajmljivanje.Iznajmljivanje;
import osobe.Zaposleni;
import videoteka.Videoteka;

public class ZaposleniForma extends JFrame {

	private JToolBar mainToolbar;
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnDelete;
	private JButton btnObrisani;
	private JScrollPane tableScroll;
	private JTable zaposleniTabela;
	private Videoteka videoteka;
	
	public ZaposleniForma(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Prikaz Zaposlenih");
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
		
		String[] zaglavlja = new String[] {"Korisničko ime", "Ime", "Prezime", "Pol",
											"Plata", "JMBG", "Adresa"};
		
		int brojZaposlenih= 0;
		for (Zaposleni z : videoteka.getZaposleni()) {
			if( z.isObrisan() == false){
				
				brojZaposlenih++;
			}
			
		}
		Object[][] podaci  = new Object[brojZaposlenih]
										[zaglavlja.length];
		int j =0;
		for(int i=0; i<this.videoteka.getZaposleni().size(); i++) {
			Zaposleni zaposleni = this.videoteka.getZaposleni().get(i);
			
			if(zaposleni.isObrisan() == false){
				podaci[j][0] = zaposleni.getUsn();
				podaci[j][1] = zaposleni.getIme();
				podaci[j][2] = zaposleni.getPrezime();
				podaci[j][3] = zaposleni.getPol();
				podaci[j][4] = zaposleni.getPlata();
				podaci[j][5] = zaposleni.getJMBG();
				podaci[j][6] = zaposleni.getAdresa();
				j++;
			
		}}
		j=0;
		DefaultTableModel model = new DefaultTableModel(podaci, zaglavlja);
		zaposleniTabela = new JTable(model);
		zaposleniTabela.setColumnSelectionAllowed(false);
		zaposleniTabela.setRowSelectionAllowed(true);
		zaposleniTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		zaposleniTabela.setDefaultEditor(Object.class, null);
		
		tableScroll = new JScrollPane(zaposleniTabela);
		add(tableScroll, BorderLayout.CENTER);
	}
	
	public JTable getZaposleniTabela() {
		return zaposleniTabela;
	}

	private void initActions() {
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selektovaniRed = ZaposleniForma.this.zaposleniTabela.getSelectedRow();
				if(selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati zaposlenog.");
				}else {
					int izbor = JOptionPane.showConfirmDialog(null, "Da li ste sigurni",
							"Brisanje zaposlenog", JOptionPane.YES_NO_OPTION);
					
					if(izbor == JOptionPane.YES_OPTION) {
						String korisnickoIme = (String)
								ZaposleniForma.this.zaposleniTabela.getValueAt(selektovaniRed, 0);
						Zaposleni zaposleni = ZaposleniForma.this.videoteka.nadjiZaposlenog(korisnickoIme);
//						boolean izdao= false;
//						for (Iznajmljivanje izna: videoteka.getIznajmljivanje()) {
//							Zaposleni zap = izna.getZaposleni();
//							if(zap.equals(zaposleni)){
//								izdao=true;
//								JOptionPane.showMessageDialog(null, "Nemozete obrisati zaposlenog koji je izdavao");
//								break;
//								
//							}
//						}
//						if(!izdao){
						DefaultTableModel model = (DefaultTableModel)
								ZaposleniForma.this.zaposleniTabela.getModel();
						//ZaposleniForma.this.videoteka.getZaposleni().remove(zaposleni);
						zaposleni.setObrisan(true);
						ZaposleniForma.this.videoteka.sacuvajZaposlene();
						model.removeRow(selektovaniRed);}
					//}
				}
			}
		});
		
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ZaposleniDodavanjeDialog zdd = new ZaposleniDodavanjeDialog(
													ZaposleniForma.this.videoteka, 
													null, 
													ZaposleniForma.this);
				zdd.setVisible(true);
			}
		});
		
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selektovaniRed = ZaposleniForma.this.zaposleniTabela.getSelectedRow();
				if(selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, 
							"Morate odabrati zaposlenog u tabeli.");
					
				}else {
					String username = (String)
							ZaposleniForma.this.zaposleniTabela.getValueAt(selektovaniRed, 0);
					Zaposleni zaposleni= ZaposleniForma.this.videoteka.nadjiZaposlenog(username);
					ZaposleniDodavanjeDialog zdd = new ZaposleniDodavanjeDialog(
													ZaposleniForma.this.videoteka, 
													zaposleni, 
													ZaposleniForma.this);
						zdd.setVisible(true);
				}
			}
		});
		
		btnObrisani.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ZaposleniObrisani zo = new ZaposleniObrisani(
						ZaposleniForma.this.videoteka);
						zo.setVisible(true);
			}
		});
		
	}
}