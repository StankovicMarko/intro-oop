package gui.dodavanje;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import filmovi.Primerak;
import gui.GlavniProzor;
import iznajmljivanje.Iznajmljivanje;
import net.miginfocom.swing.MigLayout;
import osobe.Clan;
import osobe.Zaposleni;
import videoteka.Videoteka;

public class IznamljivanjeDodavanje extends JDialog {


	private JLabel lblClan;
	private JComboBox<String> cbClan;
	private JLabel lblPrimerci;
	private JComboBox<String> cbPrimerci;
	private JLabel lblDodatiPrimerci;
	private JComboBox<String> cbDodatiPrimerci;
	private JButton btnDodajPrimerak;
	private JButton btnOduzmiPrimerak;
	private JButton btnOk;
	private JButton btnCancel;
	private ArrayList<Primerak> primerci;

	private Videoteka videoteka;
	private GlavniProzor glProz;
	private Zaposleni prijavljeniKorisnik;
	
	public IznamljivanjeDodavanje(Videoteka videoteka,
									Zaposleni prijavljeniKorisnik,
											GlavniProzor glProz) {
		this.videoteka = videoteka;
		this.prijavljeniKorisnik = prijavljeniKorisnik;
		this.glProz = glProz;
		this.primerci =  new ArrayList<>();
		setTitle("Iznamljivanje");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);
		setSize(500, 200);
		initGUI();
		initActions();
		//pack();
	}
	
	private void initGUI() {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[][][][]20[]");
		setLayout(layout);
		

		lblClan = new JLabel("Izaberite clana");
		cbClan = new JComboBox<>();
		for (Clan clan: videoteka.getClanovi()) {
			if (clan.isAktivan()){
				cbClan.addItem(clan.getBrojClanskeKarte()+" - "+clan.getIme());
			}
			
		}
		lblPrimerci = new JLabel("Izaberite primerke");
		cbPrimerci = new JComboBox<>();
		for (Primerak prim: videoteka.getPrimerci()) {
			if ((prim.isObrisan()==false)){
				cbPrimerci.addItem(prim.getID()+" - "+prim.getFilm().getNaslovSRB()+" - "+prim.getMedijum());
			}
			
		}
		btnDodajPrimerak = new JButton("Dodaj primerak");
		btnOduzmiPrimerak = new JButton("Oduzmi primerak");
		lblDodatiPrimerci= new JLabel("Dodati primerci");
		cbDodatiPrimerci= new JComboBox<>();
		
		btnOk = new JButton("OK");
		btnCancel = new JButton("Cancel");

		add(lblClan);
		add(cbClan);
		add(lblPrimerci);
		add(cbPrimerci);
		add(new JLabel());
		add(btnDodajPrimerak,"split 2");
		add(btnOduzmiPrimerak);
		add(lblDodatiPrimerci);
		add(cbDodatiPrimerci);
		
		add(new JLabel());
		add(btnOk, "split 2");
		add(btnCancel);
		
	}
	
	private void initActions() {
		
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ID = videoteka.odrediIDIznajmljivanja();
				Zaposleni zaposleni = prijavljeniKorisnik;
				String brojCk = ((String) cbClan.getSelectedItem()).split("-")[0].trim();
				Clan clan= videoteka.pronadjiClana(brojCk);
				Date datumUzimanja = new Date();
				Date datumVracanja = null;
				
//				for (int i = 0; i < cbDodatiPrimerci.getItemCount(); i++) {
//					String id = ((String) cbDodatiPrimerci.getItemAt(i)).split("-")[0].trim();
//					Primerak prim = videoteka.pronadjiPrimerak(id);
//					primerci.add(prim);
//				}
				String cena = " ";
	
				
				if(primerci.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Niste uneli sve podatke");
				}else {
					DefaultTableModel model = (DefaultTableModel)
							glProz.getIznajmljivanjeTabela().getModel();
		
					Iznajmljivanje iznamljivanje = new Iznajmljivanje(ID, zaposleni
							,clan, datumUzimanja, datumVracanja, primerci, cena);
					IznamljivanjeDodavanje.this.videoteka.getIznajmljivanje().add(iznamljivanje);
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					String datumUz = df.format(datumUzimanja);
					
					String pri = "";
					for (Primerak prim: primerci) {
						//System.out.println(prim);
						pri += prim.getFilm().getNaslovSRB()+",";
						
					}
					model.addRow(new Object[] {ID, 
							zaposleni.getIme()+"-"+zaposleni.getJMBG(), 
							clan.getIme()+"-"+clan.getBrojClanskeKarte(),
							datumUz, datumVracanja,pri, cena});
					IznamljivanjeDodavanje.this.videoteka.sacuvajIznajmljivanje();
					IznamljivanjeDodavanje.this.videoteka.sacuvajPrimerke();
					IznamljivanjeDodavanje.this.setVisible(false);
					IznamljivanjeDodavanje.this.dispose();
				}}
		});
		
		btnDodajPrimerak.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String izabraniID = ((String) cbPrimerci.getSelectedItem()).split("-")[0].trim();
				cbPrimerci.removeItemAt(cbPrimerci.getSelectedIndex());
				Primerak primerak = videoteka.pronadjiPrimerak(izabraniID);
				primerak.setIzdat(true);
//				int brojPrimeraka = Integer.parseInt(primerak.getBrojPrimeraka());
//				if(brojPrimeraka <= 0){
//					JOptionPane.showMessageDialog(null, "Nema vise primeraka");
//
//				}else{
//				primerak.setBrojPrimeraka(Integer.toString(brojPrimeraka-1));
				cbDodatiPrimerci.addItem(primerak.getID()+" - "+primerak.getFilm().getNaslovSRB()+" - "+primerak.getMedijum());
				primerci.add(primerak);
//			}
			}
		});
		
		btnOduzmiPrimerak.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String izabraniID = ((String) cbDodatiPrimerci.getSelectedItem()).split("-")[0].trim();
				cbDodatiPrimerci.removeItemAt(cbDodatiPrimerci.getSelectedIndex());
				Primerak primerak = videoteka.pronadjiPrimerak(izabraniID);
				primerak.setIzdat(false);
				cbPrimerci.addItem(primerak.getID()+" - "+primerak.getFilm().getNaslovSRB()+" - "+primerak.getMedijum());
				primerci.remove(primerak);
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				IznamljivanjeDodavanje.this.setVisible(false);
				IznamljivanjeDodavanje.this.dispose();
				for (int i = 0; i < cbDodatiPrimerci.getItemCount(); i++) {
					String izabraniID = ((String) cbPrimerci.getItemAt(i)).split("-")[0].trim();
					Primerak primerak = videoteka.pronadjiPrimerak(izabraniID);
					primerak.setIzdat(false);
					
				}
			}
		});
	}
	
}