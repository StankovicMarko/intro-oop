package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import filmovi.Medijum;
import filmovi.Primerak;
import gui.dodavanje.IznamljivanjeDodavanje;
import gui.dodavanje.PrimerciDodavanjeDialog;
import gui.obrisani.EvidencijaIznamljivanja;
import gui.prikaz.ClanoviForma;
import gui.prikaz.FilmForma;
import gui.prikaz.PrimerciForma;
import gui.prikaz.VideotekaForma;
import gui.prikaz.ZanroviForma;
import gui.prikaz.ZaposleniForma;
import iznajmljivanje.Iznajmljivanje;
import osobe.Zaposleni;
import videoteka.Videoteka;

public class GlavniProzor extends JFrame {

	private JMenuBar mainMenu;
	private JMenu prikazMenu;
	private JMenuItem clanoviItem;
	private JMenuItem filmoviItem;
	private JMenuItem zanroviItem;
	private JMenuItem primerciItem;
	private JMenu osobljeMenu;
	private JMenuItem videotekaItem;
	private JMenuItem zaposleniItem;
	
	private JToolBar mainToolbar;
	private JButton btnAdd;
	private JButton btnDelete;
	private JButton btnEvidencijaIznamljivanja;
	private JScrollPane tableScroll;
	private JTable iznamljivanjeTabela;
	
	private Videoteka videoteka;
	private Zaposleni prijavljeniKorisnik;
	
	public GlavniProzor(Videoteka videoteka, Zaposleni prijavljeniKorisnik) {
		this.videoteka = videoteka;
		this.prijavljeniKorisnik = prijavljeniKorisnik;
		setTitle("Videoteka - " + prijavljeniKorisnik.getUsn());
		setSize(1000, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		initMenu();
		initActions();
	}
	
	private void initMenu() {
		mainToolbar = new JToolBar();
		ImageIcon iconAdd = new ImageIcon(getClass().getResource("/slike/au.png"));
		btnAdd = new JButton(iconAdd);
		mainToolbar.add(btnAdd);
		ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/slike/cm.png"));
		btnDelete = new JButton(deleteIcon);
		mainToolbar.add(btnDelete);
		btnEvidencijaIznamljivanja = new JButton("Vracena Iznamljivanja");
		mainToolbar.add(btnEvidencijaIznamljivanja);
		
		add(mainToolbar, BorderLayout.NORTH);
		
		String[] zaglavlja = new String[] {"Oznaka(ID)", "Zaposleni", "Clan",
				"Datum izdavanja", "Datum vracanja", "Primerci", "Cena"};
		Object[][] podaci  = new Object[this.videoteka.getIznajmljivanje().size()]
					[zaglavlja.length];
		
		for(int i=0; i<this.videoteka.getIznajmljivanje().size(); i++) {
			Iznajmljivanje iznajmljivanje = this.videoteka.getIznajmljivanje().get(i);
			if (iznajmljivanje.getDatumVracanja()!=null){
			podaci[i][0] = iznajmljivanje.getID();
			podaci[i][1] = iznajmljivanje.getZaposleni().getIme()+"-"+
			iznajmljivanje.getZaposleni().getJMBG();
			podaci[i][2] = iznajmljivanje.getClan().getIme()+"-"+
					iznajmljivanje.getClan().getBrojClanskeKarte();
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String datumUzimanje = df.format(iznajmljivanje.getDatumUzimanja());
			podaci[i][3] = datumUzimanje;
			//String datumVracanje = df.format(iznajmljivanje.getDatumVracanja());
			String datumVracanje = " ";
			podaci[i][4] = datumVracanje;
			
			String primerci = "";
			for (Primerak prim: iznajmljivanje.getPrimerci()) {
				primerci += prim.getFilm().getNaslovSRB()+",";
				
			}
			podaci[i][5] = primerci;
			podaci[i][6] = iznajmljivanje.getCena();
		}}
		
		this.mainMenu = new JMenuBar();
		this.prikazMenu = new JMenu("Prikazi...");
		this.clanoviItem = new JMenuItem("Clanove");
		this.filmoviItem = new JMenuItem("Filmove");
		this.zanroviItem = new JMenuItem("Zanrove");
		this.primerciItem = new JMenuItem("Primerke");
		this.osobljeMenu = new JMenu("Videoteka");
		this.videotekaItem = new JMenuItem("Prikaži informacije videoteke");
		this.zaposleniItem = new JMenuItem("Prikaži zaposlene");
		
		this.prikazMenu.add(clanoviItem);
		this.prikazMenu.add(filmoviItem);
		this.prikazMenu.add(zanroviItem);
		this.prikazMenu.add(primerciItem);
		this.osobljeMenu.add(videotekaItem);
		this.osobljeMenu.add(zaposleniItem);
		
		this.mainMenu.add(prikazMenu);
		this.mainMenu.add(osobljeMenu);
		
		setJMenuBar(this.mainMenu);
		
		DefaultTableModel model = new DefaultTableModel(podaci, zaglavlja);
		iznamljivanjeTabela = new JTable(model);
		iznamljivanjeTabela.setColumnSelectionAllowed(false);
		iznamljivanjeTabela.setRowSelectionAllowed(true);
		iznamljivanjeTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		iznamljivanjeTabela.setDefaultEditor(Object.class, null);
		
		tableScroll = new JScrollPane(iznamljivanjeTabela);
		add(tableScroll, BorderLayout.CENTER);
	}
	
	public JTable getIznajmljivanjeTabela() {
		return iznamljivanjeTabela;
	}


	
	private void initActions() {
		videotekaItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VideotekaForma vf = new VideotekaForma(GlavniProzor.this.videoteka);
				vf.setVisible(true);
			}
		});
		
		zaposleniItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ZaposleniForma zf = new ZaposleniForma(GlavniProzor.this.videoteka);
				zf.setVisible(true);
			}
		});
		
		clanoviItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ClanoviForma cf = new ClanoviForma(GlavniProzor.this.videoteka);
				cf.setVisible(true);
			}
		});
		filmoviItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FilmForma ff = new FilmForma(GlavniProzor.this.videoteka);
				ff.setVisible(true);
			}
		});
	
		zanroviItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ZanroviForma zf = new ZanroviForma(GlavniProzor.this.videoteka);
				zf.setVisible(true);
				
			}
		});
		
		primerciItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				PrimerciForma pf = new PrimerciForma(GlavniProzor.this.videoteka);
				pf.setVisible(true);
			}
		});
		
		btnEvidencijaIznamljivanja.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				EvidencijaIznamljivanja ei = new EvidencijaIznamljivanja(GlavniProzor.this.videoteka);
				ei.setVisible(true);
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selektovaniRed = GlavniProzor.this.iznamljivanjeTabela.getSelectedRow();
				if(selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati polje.");
				}else {
					int izbor = JOptionPane.showConfirmDialog(null, "Da li ste sigurni",
							"Vracanje iznamljivanih primeraka", JOptionPane.YES_NO_OPTION);
					
					if(izbor == JOptionPane.YES_OPTION) {
						String ID = (String)
								GlavniProzor.this.iznamljivanjeTabela.getValueAt(selektovaniRed, 0);
						Iznajmljivanje iznamljivanje= GlavniProzor.this.videoteka.pronadjiIznamljivanje(ID);
						DefaultTableModel model = (DefaultTableModel)
								GlavniProzor.this.iznamljivanjeTabela.getModel();
						Date datumVracanja = new Date();
						iznamljivanje.setDatumVracanja(datumVracanja);
						int ukCena = 0;
						long ms =  iznamljivanje.getDatumVracanja().getTime() - iznamljivanje.getDatumUzimanja().getTime();
						int dani = (int)(TimeUnit.DAYS.convert(ms, TimeUnit.MILLISECONDS));
						
						for (Primerak prim: iznamljivanje.getPrimerci()) {
							prim.setIzdat(false);
							ukCena += Medijum.toInt(prim.getMedijum())*10*dani;
//							
						}
								
						iznamljivanje.setCena(Integer.toString(ukCena));
						JOptionPane.showMessageDialog(null, "Cena iznosi: "+ukCena);
						
						GlavniProzor.this.videoteka.getIznajmljivanje().remove(iznamljivanje);
						GlavniProzor.this.videoteka.sacuvajIznajmljivanje();
						GlavniProzor.this.videoteka.getVracenaIznajmljivanja().add(iznamljivanje);
						GlavniProzor.this.videoteka.sacuvajVracenaIznajmljivanja();
						GlavniProzor.this.videoteka.sacuvajPrimerke();
						model.removeRow(selektovaniRed);
					}
				}
			}
		});
		
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				IznamljivanjeDodavanje id = new IznamljivanjeDodavanje(
													GlavniProzor.this.videoteka,
													
													prijavljeniKorisnik,
													GlavniProzor.this);
				id.setVisible(true);
			}
		});
		
	
	}
}