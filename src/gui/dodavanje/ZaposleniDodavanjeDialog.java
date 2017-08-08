package gui.dodavanje;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import gui.prikaz.ZaposleniForma;
import net.miginfocom.swing.MigLayout;
import osobe.Clan;
import osobe.Pol;
import osobe.Zaposleni;
import videoteka.Videoteka;

public class ZaposleniDodavanjeDialog extends JDialog {

	private JLabel lblIme;
	private JTextField txtIme;
	private JLabel lblPrezime;
	private JTextField txtPrezime;
	private JLabel lblPol;
	private JRadioButton rbtnZenski;
	private JRadioButton rbtnMuski;
	private ButtonGroup polGrupa;
	private JLabel lblKorisnickoIme;
	private JTextField txtKorisnickoIme;
	private JLabel lblSifra;
	private JPasswordField pfSifra;
	private JButton btnOk;
	private JButton btnCancel;
	private JLabel lblPlata;
	private JTextField txtPlata;
	private JLabel lblJMBG;
	private JTextField txtJMBG;
	private JLabel lblAdresa;
	private JTextField txtAdresa;
	
	
	private Videoteka videoteka;
	private Zaposleni zaposleni;
	private ZaposleniForma zaposleniForma;
	
	public ZaposleniDodavanjeDialog(Videoteka videoteka, Zaposleni zaposleni,
									ZaposleniForma zaposleniForma) {
		this.videoteka = videoteka;
		this.zaposleni = zaposleni;
		this.zaposleniForma = zaposleniForma;
		setTitle("Zaposleni - Dodavanje");
		if(zaposleni != null) {
			setTitle("Zaposleni - Izmena");
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);
		initGUI();
		initActions();
		pack();
	}
	
	private void initGUI() {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[][][][][][][][]20[]");
		setLayout(layout);
		
		lblIme = new JLabel("Ime");
		txtIme = new JTextField(20);
		lblPrezime = new JLabel("Prezime");
		txtPrezime = new JTextField(20);
		lblPol = new JLabel("Pol");
		rbtnZenski = new JRadioButton("Ženski");
		rbtnMuski = new JRadioButton("Muški");
		polGrupa = new ButtonGroup();
		polGrupa.add(rbtnZenski);
		polGrupa.add(rbtnMuski);
		lblKorisnickoIme = new JLabel("Korisničko ime");
		txtKorisnickoIme = new JTextField(20);
		lblSifra = new JLabel("Šifra");
		pfSifra = new JPasswordField(20);
		lblPlata= new JLabel("Plata");
		txtPlata = new JTextField(20);
		lblJMBG= new JLabel("JMBG");
		txtJMBG = new JTextField(20);
		lblAdresa= new JLabel("Adresa");
		txtAdresa = new JTextField(20);
		btnOk = new JButton("OK");
		btnCancel = new JButton("Cancel");
				
		
		if(zaposleni != null) {
			initValues();
		}
		
		add(lblIme);
		add(txtIme);
		add(lblPrezime);
		add(txtPrezime);
		add(lblPol);
		add(rbtnZenski, "split 2");
		add(rbtnMuski);
		add(lblKorisnickoIme);
		add(txtKorisnickoIme);
		add(lblSifra);
		add(pfSifra);
		add(lblPlata);
		add(txtPlata);
		add(lblJMBG);
		add(txtJMBG);
		add(lblAdresa);
		add(txtAdresa);
		add(new JLabel());
		add(btnOk, "split 2");
		add(btnCancel);
		
	}
	
	private void initActions() {
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ime = txtIme.getText().trim();
				String prezime = txtPrezime.getText().trim();
				String korisnickoIme = txtKorisnickoIme.getText().trim();
				String sifra = new String(pfSifra.getPassword());
				Pol pol = rbtnZenski.isSelected() ? Pol.ZENSKI : Pol.MUSKI;
				String plata = txtPlata.getText().trim();
				String adresa = txtAdresa.getText().trim();
				String JMBG = txtJMBG.getText().trim();
				
				
				
				if(ime.equals("") || prezime.equals("") || 
						korisnickoIme.equals("") || sifra.equals("")
						|| plata.equals("") || adresa.equals("") || JMBG.equals("")) {
					JOptionPane.showMessageDialog(null, "Niste uneli sve podatke");
				}else {
					DefaultTableModel model = (DefaultTableModel)
							ZaposleniDodavanjeDialog.this.zaposleniForma.getZaposleniTabela().getModel();
					if(zaposleni == null) {
						// DODAVANJE
						boolean flag = false;
						for (Zaposleni z: videoteka.getZaposleni()) {
							if (z.getJMBG().equals(JMBG)){
								JOptionPane.showMessageDialog(null, "Ova osoba radi kod nas");
								flag = true;
							}
						}
						if (!flag){
						zaposleni = new Zaposleni(ime, prezime, JMBG, adresa, 
								 pol, plata, korisnickoIme ,sifra,false);
						ZaposleniDodavanjeDialog.this.videoteka.getZaposleni().add(zaposleni);
						model.addRow(new Object[] {korisnickoIme, ime, prezime, pol, plata,
								JMBG, adresa, sifra});
						ZaposleniDodavanjeDialog.this.videoteka.sacuvajZaposlene();}
					}else {
						// IZMENA
						zaposleni.setIme(ime);
						zaposleni.setPrezime(prezime);
						zaposleni.setUsn(korisnickoIme);
						zaposleni.setPsw(sifra);
						zaposleni.setPol(pol);
						zaposleni.setPlata(plata);
						zaposleni.setJMBG(JMBG);
						zaposleni.setAdresa(adresa);
						
						int red = ZaposleniDodavanjeDialog.this.zaposleniForma.getZaposleniTabela()
								.getSelectedRow();
						ZaposleniDodavanjeDialog.this.zaposleniForma.getZaposleniTabela()
								.setValueAt(korisnickoIme, red, 0);
						ZaposleniDodavanjeDialog.this.zaposleniForma.getZaposleniTabela()
								.setValueAt(ime, red, 1);
						ZaposleniDodavanjeDialog.this.zaposleniForma.getZaposleniTabela()
								.setValueAt(prezime, red, 2);
						ZaposleniDodavanjeDialog.this.zaposleniForma.getZaposleniTabela()
								.setValueAt(pol, red, 3);
						ZaposleniDodavanjeDialog.this.zaposleniForma.getZaposleniTabela()
								.setValueAt(plata, red, 4);
						ZaposleniDodavanjeDialog.this.zaposleniForma.getZaposleniTabela()
								.setValueAt(JMBG, red, 5);
						ZaposleniDodavanjeDialog.this.zaposleniForma.getZaposleniTabela()
								.setValueAt(adresa, red, 6);
						
						ZaposleniDodavanjeDialog.this.videoteka.sacuvajZaposlene();
					}
					ZaposleniDodavanjeDialog.this.setVisible(false);
					ZaposleniDodavanjeDialog.this.dispose();
				}
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ZaposleniDodavanjeDialog.this.setVisible(false);
				ZaposleniDodavanjeDialog.this.dispose();
			}
		});
	}
	
	private void initValues() {
		txtIme.setText(zaposleni.getIme());
		txtPrezime.setText(zaposleni.getPrezime());
		txtKorisnickoIme.setText(zaposleni.getUsn());
		pfSifra.setText(zaposleni.getPsw());
		txtAdresa.setText(zaposleni.getAdresa());
		txtJMBG.setText(zaposleni.getJMBG());
		txtPlata.setText(zaposleni.getPlata());
		if(zaposleni.getPol() == Pol.ZENSKI) {
			rbtnZenski.setSelected(true);
		}else {
			rbtnMuski.setSelected(true);
		}
	}
}