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
import filmovi.Primerak;
import gui.dodavanje.FilmDodavanjeDialog;
import gui.obrisani.FilmObrisani;
import videoteka.Videoteka;

public class FilmForma extends JFrame{

	private JToolBar mainToolbar;
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnDelete;
	private JButton btnObrisani;
	private JScrollPane tableScroll;
	private JTable filmoviTabela;
	private Videoteka videoteka;
	
	public FilmForma(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Prikaz Filmova");
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
		
		String[] zaglavlja = new String[] {"Srpski naslov", "Original naslov",
										"Godina izdanja", "Zanr",
										 "Reziser", "Opis", "Trajanje"};
		int brojFilm = 0;
		for (Film fi : videoteka.getFilmovi()) {
			if( fi.isObrisan() == false){
				
				brojFilm++;
			}
			
		}
		Object[][] podaci  = new Object[brojFilm]
										[zaglavlja.length];
		int j =0;
		for(int i=0; i<this.videoteka.getFilmovi().size(); i++) {
			
			Film film= this.videoteka.getFilmovi().get(i);
			if(film.isObrisan() == false){

			podaci[j][0] = film.getNaslovSRB();
			podaci[j][1] = film.getNaslovOriginal();
			podaci[j][2] = film.getGodinaIzdanja();
			podaci[j][3] = film.getZanr().getNaziv();
			podaci[j][4] = film.getImePrezimeRezisera();
			podaci[j][5] = film.getOpis();
			podaci[j][6] = film.getTrajanje();
			j++;
			}
			
		}
		j=0;
		DefaultTableModel model = new DefaultTableModel(podaci, zaglavlja);
		filmoviTabela = new JTable(model);
		filmoviTabela.setColumnSelectionAllowed(false);
		filmoviTabela.setRowSelectionAllowed(true);
		filmoviTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		filmoviTabela.setDefaultEditor(Object.class, null);
		
		tableScroll = new JScrollPane(filmoviTabela);
		add(tableScroll, BorderLayout.CENTER);
	}
	
	public JTable getFilmTabela() {
		return filmoviTabela;
	}

	private void initActions() {
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selektovaniRed = FilmForma.this.filmoviTabela.getSelectedRow();
				if(selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati film.");
				}else {
					int izbor = JOptionPane.showConfirmDialog(null, "Da li ste sigurni",
							"Brisanje filma", JOptionPane.YES_NO_OPTION);
					
					if(izbor == JOptionPane.YES_OPTION) {
						String nazivSrpski = (String)
								FilmForma.this.filmoviTabela.getValueAt(selektovaniRed, 0);
						Film film = FilmForma.this.videoteka.pronadjiFilm(nazivSrpski);
//						boolean flag = false;
//						for (Primerak prim: videoteka.getPrimerci()) {
//							if(prim.getFilm().getNaslovSRB().equals(nazivSrpski)){
//								JOptionPane.showMessageDialog(null, "Ne mozete obrisati film koji se nalazi na primercima");
//								flag = true;
//								break;
//							
//							}
//						}
						
//						if(!flag){
						DefaultTableModel model = (DefaultTableModel)
								FilmForma.this.filmoviTabela.getModel();
						film.setObrisan(true);
						//FilmForma.this.videoteka.getFilmovi().remove(film);
						FilmForma.this.videoteka.sacuvajFilmove();;
						model.removeRow(selektovaniRed);
						
						//}
					}
				}
			}
		});
		
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FilmDodavanjeDialog fdd = new FilmDodavanjeDialog(
													FilmForma.this.videoteka, 
													null, 
													FilmForma.this);
				fdd.setVisible(true);
			}
		});
		
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selektovaniRed = FilmForma.this.filmoviTabela.getSelectedRow();
				if(selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, 
							"Morate odabrati clana u tabeli.");
					
				}else {
					String nazivSrpski = (String)
							FilmForma.this.filmoviTabela.getValueAt(selektovaniRed, 0);
					Film film= FilmForma.this.videoteka.pronadjiFilm(nazivSrpski);
					FilmDodavanjeDialog fdd = new FilmDodavanjeDialog(
													FilmForma.this.videoteka, 
													film, 
													FilmForma.this);
						fdd.setVisible(true);
				}
			}
		});
		
		
		btnObrisani.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				FilmObrisani fo = new FilmObrisani(
						FilmForma.this.videoteka);
						fo.setVisible(true);
			}
		});
		
	}
}

