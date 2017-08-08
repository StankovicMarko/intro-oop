package gui.dodavanje;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import gui.prikaz.ClanoviForma;
import net.miginfocom.swing.MigLayout;
import osobe.Clan;
import osobe.Pol;
import videoteka.Videoteka;

public class ClanoviDodavanjeDialog extends JDialog{
		private JLabel lblIme;
		private JTextField txtIme;
		private JLabel lblPrezime;
		private JTextField txtPrezime;
		private JLabel lblPol;
		private JRadioButton rbtnZenski;
		private JRadioButton rbtnMuski;
		private ButtonGroup polGrupa;
		private JRadioButton rbtnTrue;
		private JRadioButton rbtnFalse;
		private ButtonGroup aktivanGrupa;
		private JButton btnOk;
		private JButton btnCancel;
		private JLabel lblJMBG;
		private JTextField txtJMBG;
		private JLabel lblAdresa;
		private JTextField txtAdresa;
		
		
		private Videoteka videoteka;
		private Clan clanovi;
		private ClanoviForma clanoviForma;
		
		public ClanoviDodavanjeDialog(Videoteka videoteka, Clan clanovi,
										ClanoviForma clanoviForma) {
			this.videoteka = videoteka;
			this.clanovi = clanovi;
			this.clanoviForma = clanoviForma;
			setTitle("Clanovi - Dodavanje");
			if(clanovi != null) {
				setTitle("Clanovi - Izmena");
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
			MigLayout layout = new MigLayout("wrap 2", "[][]", "[][][][][][]20[]");
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
			rbtnTrue = new JRadioButton("Aktivan");
			rbtnFalse = new JRadioButton("Neaktivan");
			aktivanGrupa = new ButtonGroup();
			aktivanGrupa.add(rbtnTrue);
			aktivanGrupa.add(rbtnFalse);
			lblJMBG= new JLabel("JMBG");
			txtJMBG = new JTextField(20);
			lblAdresa= new JLabel("Adresa");
			txtAdresa = new JTextField(20);
			btnOk = new JButton("OK");
			btnCancel = new JButton("Cancel");
					
			
			if(clanovi != null) {
				initValues();
			}
			
			add(lblIme);
			add(txtIme);
			add(lblPrezime);
			add(txtPrezime);
			add(lblPol);
			add(rbtnZenski, "split 2");
			add(rbtnMuski);
			add(new JLabel());
			add(rbtnTrue, "split 2");
			add(rbtnFalse);
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
					String brojCK = videoteka.odrediBrojClanskeKarte();
					boolean aktivan = rbtnTrue.isSelected() ? true : false;
					Pol pol = rbtnZenski.isSelected() ? Pol.ZENSKI : Pol.MUSKI;
					String adresa = txtAdresa.getText().trim();
					String JMBG = txtJMBG.getText().trim();
					
					
					
					if(ime.equals("") || prezime.equals("") || 
							brojCK.equals("") || adresa.equals("") || JMBG.equals("")) {
						JOptionPane.showMessageDialog(null, "Niste uneli sve podatke");
					}else {
						DefaultTableModel model = (DefaultTableModel)
								ClanoviDodavanjeDialog.this.clanoviForma.getClanoviTabela().getModel();
						if(clanovi == null) {
							// DODAVANJE
							boolean flag = false;
							for (Clan c: videoteka.getClanovi()) {
								if (c.getJMBG().equals(JMBG)){
									JOptionPane.showMessageDialog(null, "Clan vec postoji");
									flag = true;
								}
							}
							if(!flag){
							clanovi = new Clan(ime, prezime, JMBG, adresa, 
									 pol, brojCK, aktivan);
							ClanoviDodavanjeDialog.this.videoteka.getClanovi().add(clanovi);
							model.addRow(new Object[] {brojCK, ime, prezime, pol,
									JMBG, adresa, aktivan});
							ClanoviDodavanjeDialog.this.videoteka.sacuvajClanove();}
							
						}else {
							// IZMENA
							String brojCKarte = clanovi.getBrojClanskeKarte();
							clanovi.setIme(ime);
							clanovi.setPrezime(prezime);
							clanovi.setBrojClanskeKarte(brojCKarte);
							clanovi.setPol(pol);
							clanovi.setAktivan(aktivan);;
							clanovi.setJMBG(JMBG);
							clanovi.setAdresa(adresa);
							
							int red = ClanoviDodavanjeDialog.this.clanoviForma.getClanoviTabela()
									.getSelectedRow();
							ClanoviDodavanjeDialog.this.clanoviForma.getClanoviTabela()
									.setValueAt(brojCKarte, red, 0);
							ClanoviDodavanjeDialog.this.clanoviForma.getClanoviTabela()
									.setValueAt(ime, red, 1);
							ClanoviDodavanjeDialog.this.clanoviForma.getClanoviTabela()
									.setValueAt(prezime, red, 2);
							ClanoviDodavanjeDialog.this.clanoviForma.getClanoviTabela()
									.setValueAt(pol, red, 3);
							ClanoviDodavanjeDialog.this.clanoviForma.getClanoviTabela()
									.setValueAt(JMBG, red, 4);
							ClanoviDodavanjeDialog.this.clanoviForma.getClanoviTabela()
									.setValueAt(adresa, red, 5);
							ClanoviDodavanjeDialog.this.clanoviForma.getClanoviTabela()
							.setValueAt(aktivan, red, 6);
							
							ClanoviDodavanjeDialog.this.videoteka.sacuvajClanove();
						}
						ClanoviDodavanjeDialog.this.setVisible(false);
						ClanoviDodavanjeDialog.this.dispose();
					}
				}
			});
			
			btnCancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ClanoviDodavanjeDialog.this.setVisible(false);
					ClanoviDodavanjeDialog.this.dispose();
				}
			});
		}
		
		private void initValues() {
			txtIme.setText(clanovi.getIme());
			txtPrezime.setText(clanovi.getPrezime());
//			txtbrojCK.setText(clanovi.getBrojClanskeKarte());
			txtAdresa.setText(clanovi.getAdresa());
			txtJMBG.setText(clanovi.getJMBG());
			if(clanovi.isAktivan() == true) {
				rbtnTrue.setSelected(true);
			}else {
				rbtnFalse.setSelected(true);
			}
			if(clanovi.getPol() == Pol.ZENSKI) {
				rbtnZenski.setSelected(true);
			}else {
				rbtnMuski.setSelected(true);
			}
		}
	}