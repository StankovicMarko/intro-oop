package gui.obrisani;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
import gui.prikaz.FilmForma;
import iznajmljivanje.Iznajmljivanje;
import videoteka.Videoteka;

public class FilmObrisani extends JDialog{
	private Videoteka videoteka;
	private JScrollPane tableScroll;
	private JTable filmTabela;
	private JToolBar mainToolbar;
	private JButton btnRestore;

	
	public FilmObrisani(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Obrisani Filmovi");
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
		
		String[] zaglavlja = new String[] {"Srpski naslov", "Original naslov",
				"Godina izdanja", "Zanr",
				 "Reziser", "Opis", "Trajanje"};
		
		int brojFilm = 0;
		for (Film fi : videoteka.getFilmovi()) {
			if( fi.isObrisan()){
				brojFilm++;
			}
			
		}
		
		
		Object[][] podaci  = new Object[brojFilm]
				[zaglavlja.length];

		
		int j =0;
		for(int i=0; i<this.videoteka.getFilmovi().size(); i++) {
			Film film= this.videoteka.getFilmovi().get(i);
			if(film.isObrisan()){
				podaci[j][0] = film.getNaslovSRB();
				podaci[j][1] = film.getNaslovOriginal();
				podaci[j][2] = film.getGodinaIzdanja();
				podaci[j][3] = film.getZanr().getNaziv();
				podaci[j][4] = film.getImePrezimeRezisera();
				podaci[j][5] = film.getOpis();
				podaci[j][6] = film.getTrajanje();
				j++;
				
		}}
		j=0;
		DefaultTableModel model = new DefaultTableModel(podaci, zaglavlja);
		filmTabela = new JTable(model);
		filmTabela.setColumnSelectionAllowed(false);
		filmTabela.setRowSelectionAllowed(true);
		filmTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		filmTabela.setDefaultEditor(Object.class, null);
		
		tableScroll = new JScrollPane(filmTabela);
		add(tableScroll, BorderLayout.CENTER);
	
	}
	private void initActions(){
		btnRestore.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selektovaniRed = FilmObrisani.this.filmTabela.getSelectedRow();
				if(selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati film.");
				}else {
					int izbor = JOptionPane.showConfirmDialog(null, "Da li ste sigurni",
							"Restore filma", JOptionPane.YES_NO_OPTION);
					
					if(izbor == JOptionPane.YES_OPTION) {
						String nazivSrpski = (String)
								FilmObrisani.this.filmTabela.getValueAt(selektovaniRed, 0);
						Film film = FilmObrisani.this.videoteka.pronadjiFilm(nazivSrpski);
						DefaultTableModel model = (DefaultTableModel)
								FilmObrisani.this.filmTabela.getModel();
						film.setObrisan(false);
						FilmObrisani.this.videoteka.sacuvajFilmove();
						model.removeRow(selektovaniRed);
					}
				}
				
			}

			
		});}
}

		
			
		
	

