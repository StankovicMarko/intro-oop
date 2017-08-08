package gui.dodavanje;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import filmovi.Film;
import filmovi.Medijum;
import filmovi.Primerak;
import gui.prikaz.PrimerciForma;
import net.miginfocom.swing.MigLayout;
import videoteka.Videoteka;

public class PrimerciDodavanjeDialog extends JDialog {


	private JLabel lblFilm;
	private JComboBox<String> cbFilm;
	private JLabel lblMedijum;
	private JLabel lblBrojMedijuma;
	private JTextField txtBrojMedijuma;
	private JButton btnOk;
	private JButton btnCancel;
	
	private JRadioButton rbtnVHS;
	private JRadioButton rbtnDVD;
	private JRadioButton rbtnBlueRay;
	private ButtonGroup medijumGrupa;
	
	
	
	private Videoteka videoteka;
	private Primerak primerak;
	private PrimerciForma primerciForma;
	
	public PrimerciDodavanjeDialog(Videoteka videoteka, Primerak primerak,
											PrimerciForma primerciForma) {
		this.videoteka = videoteka;
		this.primerak = primerak;
		this.primerciForma = primerciForma;
		setTitle("Primerak - Dodavanje");
		if(primerak != null) {
			setTitle("Primerak - Izmena");
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
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[][][]20[]");
		setLayout(layout);
		

		lblFilm = new JLabel("Naslov Filma");
		cbFilm = new JComboBox<>();
		for (Film film : videoteka.getFilmovi()) {
			cbFilm.addItem(film.getNaslovSRB());
		}
		lblMedijum = new JLabel("Medijum");
		rbtnVHS = new JRadioButton("VHS");
		rbtnDVD = new JRadioButton("DVD");
		rbtnBlueRay = new JRadioButton("Blue Ray");
		medijumGrupa = new ButtonGroup();
		medijumGrupa.add(rbtnVHS);
		medijumGrupa.add(rbtnDVD);
		medijumGrupa.add(rbtnBlueRay);
		lblBrojMedijuma = new JLabel("Broj medijuma");
		txtBrojMedijuma = new JTextField(20);
		btnOk = new JButton("OK");
		btnCancel = new JButton("Cancel");
				
		
		if(primerak != null) {
			initValues();
		}
		

		add(lblFilm);
		add(cbFilm);
		add(lblMedijum);
		add(rbtnVHS, "split 3");
		add(rbtnDVD);
		add(rbtnBlueRay);
		add(lblBrojMedijuma);
		add(txtBrojMedijuma);
		add(new JLabel());
		add(btnOk, "split 2");
		add(btnCancel);
		
	}
	
	private void initActions() {
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ID = null;
				String naslovFilma = (String) cbFilm.getSelectedItem();
				Medijum medijum = null;
				if(rbtnVHS.isSelected()){
					medijum = Medijum.VHS;}
				else if(rbtnDVD.isSelected()){
					medijum = Medijum.DVD;}
				else{
					medijum = Medijum.BlueRay;
				}
				String brojMedijuma = txtBrojMedijuma.getText().trim();
				
				
				try {
				int kol = Integer.parseInt(brojMedijuma);
				if(kol<=0){
					JOptionPane.showMessageDialog(null, "Niste uneli sve podatke");
				}else{
				if(brojMedijuma.equals("")) {
					JOptionPane.showMessageDialog(null, "Niste uneli sve podatke");
				}else {
					DefaultTableModel model = (DefaultTableModel)
							PrimerciDodavanjeDialog.this.primerciForma.getPrimerciTabela().getModel();
					if(primerak == null) {
						// DODAVANJE
						//dodaj film i ubaci u konstrutkor
						ID = videoteka.odrediIdPrimerka();
						Film film = PrimerciDodavanjeDialog.this.videoteka.pronadjiFilm(naslovFilma);
						primerak = new Primerak(ID, film, medijum, brojMedijuma,false,false);
//						boolean postojiPrimerak = false;
//						for (Primerak prim : videoteka.getPrimerci()) {
//							if (prim.equals(primerak)){
//								int novaKol = Integer.parseInt(prim.getBrojPrimeraka())
//										+ Integer.parseInt(brojMedijuma);
//								prim.setBrojPrimeraka(Integer.toString(novaKol));
//								postojiPrimerak=true;
//								PrimerciDodavanjeDialog.this.videoteka.sacuvajPrimerke();
//								for (int i = 0; i < videoteka.getPrimerci().size(); i++) {
//									if(PrimerciDodavanjeDialog.this.primerciForma.getPrimerciTabela()
//								.getValueAt(i, 1) == naslovFilma && 
//								PrimerciDodavanjeDialog.this.primerciForma.getPrimerciTabela()
//								.getValueAt(i, 2) == medijum){
//										PrimerciDodavanjeDialog.this.primerciForma.getPrimerciTabela()
//										.setValueAt(novaKol, i, 3);
//									}
//										
//								}
////								int red = PrimerciDodavanjeDialog.this.primerciForma.getPrimerciTabela()
////										.getSelectedRow();
////								PrimerciDodavanjeDialog.this.primerciForma.getPrimerciTabela()
////								.setValueAt(novaKol, red, 3);
//							}
//						}
//						if (!postojiPrimerak){
							PrimerciDodavanjeDialog.this.videoteka.getPrimerci().add(primerak);
							model.addRow(new Object[] {ID, naslovFilma, medijum, brojMedijuma});
							PrimerciDodavanjeDialog.this.videoteka.sacuvajPrimerke();
//						}
						
							
					}else {
						// IZMENA
						ID = primerak.getID();
						primerak.setID(ID);
						primerak.setFilm(videoteka.pronadjiFilm(naslovFilma));
						medijum = primerak.getMedijum();
						primerak.setMedijum(medijum);
						primerak.setBrojMedijuma(brojMedijuma);
					
						int red = PrimerciDodavanjeDialog.this.primerciForma.getPrimerciTabela()
								.getSelectedRow();
						PrimerciDodavanjeDialog.this.primerciForma.getPrimerciTabela()
								.setValueAt(ID, red, 0);
						PrimerciDodavanjeDialog.this.primerciForma.getPrimerciTabela()
								.setValueAt(naslovFilma, red, 1);
						PrimerciDodavanjeDialog.this.primerciForma.getPrimerciTabela()
								.setValueAt(medijum, red, 2);
						PrimerciDodavanjeDialog.this.primerciForma.getPrimerciTabela()
								.setValueAt(brojMedijuma, red, 3);
						
						
						PrimerciDodavanjeDialog.this.videoteka.sacuvajPrimerke();
					}
					PrimerciDodavanjeDialog.this.setVisible(false);
					PrimerciDodavanjeDialog.this.dispose();
				}}
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "Kolicina mora biti broj");
				}}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PrimerciDodavanjeDialog.this.setVisible(false);
				PrimerciDodavanjeDialog.this.dispose();
			}
		});
	}
	
	private void initValues() {
		if(primerak.getMedijum() == Medijum.VHS) {
			rbtnVHS.setSelected(true);
		}else if(primerak.getMedijum() == Medijum.DVD){
			rbtnDVD.setSelected(true);
		}else{
			rbtnBlueRay.setSelected(true);
		}
		txtBrojMedijuma.setText(primerak.getBrojMedijuma());
		cbFilm.removeAllItems();
		cbFilm.addItem(primerak.getFilm().getNaslovSRB());
		

	}
}