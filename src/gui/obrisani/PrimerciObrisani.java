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
import filmovi.Primerak;
import videoteka.Videoteka;

public class PrimerciObrisani  extends JDialog{
	private Videoteka videoteka;
	private JScrollPane tableScroll;
	private JTable primerakTabela;
	private JToolBar mainToolbar;
	private JButton btnRestore;

	
	public PrimerciObrisani(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Obrisani Primerci");
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
		
		String[] zaglavlja = new String[] {"Oznaka(ID)", "Ime Filma", "Medijum", "Kolicina"};
		
		int brojPrimeraka = 0;
		for (Primerak p: videoteka.getPrimerci()) {
			if( p.isObrisan()){
				
				brojPrimeraka++;
			}
			
		}
		
		Object[][] podaci  = new Object[brojPrimeraka]
										[zaglavlja.length];
		
		int j =0;
		for(int i=0; i<this.videoteka.getPrimerci().size(); i++) {
			Primerak primerak = this.videoteka.getPrimerci().get(i);
			if(primerak.isObrisan()){
			podaci[j][0] = primerak.getID();
			podaci[j][1] = primerak.getFilm().getNaslovSRB();
			podaci[j][2] = primerak.getMedijum();
			podaci[j][3] = primerak.getBrojMedijuma();
			j++;}

			
		}
		j=0;
		DefaultTableModel model = new DefaultTableModel(podaci, zaglavlja);
		primerakTabela = new JTable(model);
		primerakTabela.setColumnSelectionAllowed(false);
		primerakTabela.setRowSelectionAllowed(true);
		primerakTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		primerakTabela.setDefaultEditor(Object.class, null);
		
		tableScroll = new JScrollPane(primerakTabela);
		add(tableScroll, BorderLayout.CENTER);
	}
	
	
	private void initActions(){
		btnRestore.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selektovaniRed = PrimerciObrisani.this.primerakTabela.getSelectedRow();
				if(selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati primerak.");
				}else {
					int izbor = JOptionPane.showConfirmDialog(null, "Da li ste sigurni",
							"Restore primerka", JOptionPane.YES_NO_OPTION);
					
					if(izbor == JOptionPane.YES_OPTION) {
						String ID = (String)
								PrimerciObrisani.this.primerakTabela.getValueAt(selektovaniRed, 0);
						Primerak primerak= PrimerciObrisani.this.videoteka.pronadjiPrimerak(ID);
						DefaultTableModel model = (DefaultTableModel)
								PrimerciObrisani.this.primerakTabela.getModel();
						primerak.setObrisan(false);
						PrimerciObrisani.this.videoteka.sacuvajPrimerke();
						model.removeRow(selektovaniRed);
					}
				}
				
			}

			
		});}
}

		
			
		
	

