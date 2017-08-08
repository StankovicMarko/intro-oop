package gui.obrisani;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import filmovi.Primerak;

import iznajmljivanje.Iznajmljivanje;

import videoteka.Videoteka;

public class EvidencijaIznamljivanja extends JDialog{
	private Videoteka videoteka;
	private JScrollPane tableScroll;
	private JTable iznajmljivanjeTabela;

	
	public EvidencijaIznamljivanja(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Evidencija iznamljivanja");
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setModal(true);
		setSize(700, 500);
		setResizable(true);
		initGUI();
		
	}
	
	private void initGUI() {
		String[] zaglavlja = new String[] {"Oznaka(ID)", "Zaposleni", "Clan",
				"Datum izdavanja", "Datum vracanja", "Primerci", "Cena"};
		Object[][] podaci  = new Object[this.videoteka.getVracenaIznajmljivanja().size()]
					[zaglavlja.length];
		
		for(int i=0; i<this.videoteka.getVracenaIznajmljivanja().size(); i++) {
			Iznajmljivanje iznajmljivanje = this.videoteka.getVracenaIznajmljivanja().get(i);
			if (iznajmljivanje.getDatumVracanja()!=null){
			podaci[i][0] = iznajmljivanje.getID();
			podaci[i][1] = iznajmljivanje.getZaposleni().getIme()+"-"+
			iznajmljivanje.getZaposleni().getJMBG();
			podaci[i][2] = iznajmljivanje.getClan().getIme()+"-"+
					iznajmljivanje.getClan().getBrojClanskeKarte();
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String datumUzimanje = df.format(iznajmljivanje.getDatumUzimanja());
			podaci[i][3] = datumUzimanje;
			String datumVracanje = df.format(iznajmljivanje.getDatumVracanja());
			podaci[i][4] = datumVracanje;
			
			String primerci = "";
			for (Primerak prim: iznajmljivanje.getPrimerci()) {
				primerci += prim.getFilm().getNaslovSRB()+",";
				
			}
			podaci[i][5] = primerci;
			podaci[i][6] = iznajmljivanje.getCena();
		}}
		DefaultTableModel model = new DefaultTableModel(podaci, zaglavlja);
		iznajmljivanjeTabela = new JTable(model);
		iznajmljivanjeTabela.setColumnSelectionAllowed(false);
		iznajmljivanjeTabela.setRowSelectionAllowed(true);
		iznajmljivanjeTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		iznajmljivanjeTabela.setDefaultEditor(Object.class, null);
		
		tableScroll = new JScrollPane(iznajmljivanjeTabela);
		add(tableScroll, BorderLayout.CENTER);
	
}}