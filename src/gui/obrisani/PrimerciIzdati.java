package gui.obrisani;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import filmovi.Film;
import filmovi.Primerak;
import videoteka.Videoteka;

public class PrimerciIzdati  extends JDialog{
	private Videoteka videoteka;
	private JScrollPane tableScroll;
	private JTable izdatiTabela;

	
	public PrimerciIzdati(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Izdati primerci");
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setModal(true);
		setSize(700, 500);
		setResizable(true);
		initGUI();
		
	}
	
	private void initGUI() {
		String[] zaglavlja = new String[] {"Oznaka(ID)", "Ime Filma", "Medijum", "Kolicina"};
		
		int brojIzdatih = 0;
		for (Primerak pri : videoteka.getPrimerci()) {
			if( pri.isIzdat()){
				brojIzdatih++;
			}
			
		}
		
		
		Object[][] podaci  = new Object[brojIzdatih]
										[zaglavlja.length];
		int j =0;
		for(int i=0; i<this.videoteka.getPrimerci().size(); i++) {
			Primerak primerak = this.videoteka.getPrimerci().get(i);
			if(primerak.isObrisan() == false && primerak.isIzdat()){
			podaci[j][0] = primerak.getID();
			podaci[j][1] = primerak.getFilm().getNaslovSRB();
			podaci[j][2] = primerak.getMedijum();
			podaci[j][3] = primerak.getBrojMedijuma();
			j++;
			}

			
		}
		j=0;
		DefaultTableModel model = new DefaultTableModel(podaci, zaglavlja);
		izdatiTabela = new JTable(model);
		izdatiTabela.setColumnSelectionAllowed(false);
		izdatiTabela.setRowSelectionAllowed(true);
		izdatiTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		izdatiTabela.setDefaultEditor(Object.class, null);
		
		tableScroll = new JScrollPane(izdatiTabela);
		add(tableScroll, BorderLayout.CENTER);
	}}