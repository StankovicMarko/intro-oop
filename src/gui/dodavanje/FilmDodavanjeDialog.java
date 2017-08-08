package gui.dodavanje;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import filmovi.Film;
import filmovi.Primerak;
import filmovi.Zanr;
import gui.prikaz.FilmForma;
import net.miginfocom.swing.MigLayout;
import videoteka.Videoteka;

public class FilmDodavanjeDialog extends JDialog {

	private JLabel lblNaslovSrb;
	private JTextField txtNaslovSrb;
	private JLabel lblNaslovOriginal;
	private JTextField txtNaslovOriginal;
	private JLabel lblGodinaIzdanja;
	private JTextField txtGodinaIzdanja;
	private JLabel lblZanr;
	private JTextField txtZanr;
	private JLabel lblReziser;
	private JTextField txtReziser;
	private JLabel lblOpis;
	private JTextField txtOpis;
	private JLabel lblTrajanje;
	private JTextField txtTrajanje;
	private JButton btnOk;
	private JButton btnCancel;

	private Videoteka videoteka;
	private Film film;
	private FilmForma filmForma;
	
	public FilmDodavanjeDialog(Videoteka videoteka, Film film,
									FilmForma filmForma) {
		this.videoteka = videoteka;
		this.film = film;
		this.filmForma = filmForma;
		setTitle("Film - Dodavanje");
		if(film != null) {
			setTitle("Film - Izmena");
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
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[][][][][][][]20[]");
		setLayout(layout);
		
		lblNaslovSrb = new JLabel("Naslov - Srpski");
		txtNaslovSrb = new JTextField(20);
		lblNaslovOriginal = new JLabel("Naslov - Original");
		txtNaslovOriginal= new JTextField(20);
		lblGodinaIzdanja = new JLabel("Godina Izdanja");
		txtGodinaIzdanja = new JTextField(20);
		lblZanr = new JLabel("Zanr");
		txtZanr = new JTextField(20);
		lblReziser = new JLabel("Reziser");
		txtReziser = new JTextField(20);
		lblOpis= new JLabel("Opis");
		txtOpis = new JTextField(20);
		lblTrajanje= new JLabel("Trajanje");
		txtTrajanje = new JTextField(20);
		btnOk = new JButton("OK");
		btnCancel = new JButton("Cancel");
				
		
		if(film != null) {
			initValues();
		}
		
		add(lblNaslovSrb);
		add(txtNaslovSrb);
		add(lblNaslovOriginal);
		add(txtNaslovOriginal);
		add(lblGodinaIzdanja);
		add(txtGodinaIzdanja);
		add(lblZanr);
		add(txtZanr);
		add(lblReziser);
		add(txtReziser);
		add(lblOpis);
		add(txtOpis);
		add(lblTrajanje);
		add(txtTrajanje);
		add(new JLabel());
		add(btnOk, "split 2");
		add(btnCancel);
		
	}
	
	private void initActions() {
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String naslovSRB = txtNaslovSrb.getText().trim();
				String naslovOriginal = txtNaslovOriginal.getText().trim();
				String godinaIzdanja = txtGodinaIzdanja.getText().trim();
				String zanr = txtZanr.getText();
				boolean postojiZanr = false;
				Zanr zanrObjekat = null;
				for (Zanr objZanr : videoteka.getZanrovi()) {
					if (objZanr.getNaziv().equals(zanr)){
						zanrObjekat=objZanr;
						postojiZanr = true;
						break;
				}
				}
				if (postojiZanr==false){
					zanrObjekat = new Zanr(videoteka.odrediOznakuZanra(), zanr,false);
					videoteka.dodajZanr(zanrObjekat);
					videoteka.sacuvajZanrove();
				}
				String reziser = txtReziser.getText().trim();
				String opis = txtOpis.getText().trim();
				String trajanje = txtTrajanje.getText().trim();
				
				
				
				if(naslovSRB.equals("") || naslovOriginal.equals("") || 
						godinaIzdanja.equals("") || zanr.equals("")
						|| reziser.equals("") || opis.equals("") || trajanje.equals("")) {
					JOptionPane.showMessageDialog(null, "Niste uneli sve podatke");
				}else {
					DefaultTableModel model = (DefaultTableModel)
							FilmDodavanjeDialog.this.filmForma.getFilmTabela().getModel();
					if(film == null) {
						// DODAVANJE
						
						film = new Film(naslovSRB, naslovOriginal, godinaIzdanja, zanrObjekat, 
								reziser, opis, trajanje,false);
						if(FilmDodavanjeDialog.this.videoteka.sadrziFilm(film)){
							JOptionPane.showMessageDialog(null, "Film vec postoji");
							film=null;
							
						}else{
						FilmDodavanjeDialog.this.videoteka.getFilmovi().add(film);
						model.addRow(new Object[] {naslovSRB, naslovOriginal, godinaIzdanja,
								zanrObjekat.getNaziv(), reziser, opis, trajanje});
						FilmDodavanjeDialog.this.videoteka.sacuvajFilmove();}
					}else {
						// IZMENA
						String stariNaziv= film.getNaslovSRB();
						film.setNaslovSRB(naslovSRB);
						film.setNaslovOriginal(naslovOriginal);
						film.setGodinaIzdanja(godinaIzdanja);
						film.setZanr(zanrObjekat);
						film.setImePrezimeRezisera(reziser);
						film.setOpis(opis);
						film.setTrajanje(trajanje);
						
						for (Primerak prim: videoteka.getPrimerci()) {
							if(prim.getFilm().getNaslovSRB().equals(stariNaziv)){
								prim.setFilm(film);
								videoteka.sacuvajPrimerke();
							}
						}
						
						int red = FilmDodavanjeDialog.this.filmForma.getFilmTabela()
								.getSelectedRow();
						FilmDodavanjeDialog.this.filmForma.getFilmTabela()
								.setValueAt(naslovSRB, red, 0);
						FilmDodavanjeDialog.this.filmForma.getFilmTabela()
								.setValueAt(naslovOriginal, red, 1);
						FilmDodavanjeDialog.this.filmForma.getFilmTabela()
								.setValueAt(godinaIzdanja, red, 2);
						FilmDodavanjeDialog.this.filmForma.getFilmTabela()
								.setValueAt(zanrObjekat.getNaziv(), red, 3);
						FilmDodavanjeDialog.this.filmForma.getFilmTabela()
								.setValueAt(reziser, red, 4);
						FilmDodavanjeDialog.this.filmForma.getFilmTabela()
								.setValueAt(opis, red, 5);
						FilmDodavanjeDialog.this.filmForma.getFilmTabela()
								.setValueAt(trajanje, red, 6);
						
						FilmDodavanjeDialog.this.videoteka.sacuvajFilmove();
					}
					FilmDodavanjeDialog.this.setVisible(false);
					FilmDodavanjeDialog.this.dispose();
				}
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FilmDodavanjeDialog.this.setVisible(false);
				FilmDodavanjeDialog.this.dispose();
			}
		});
	}
	
	private void initValues() {
		txtNaslovSrb.setText(film.getNaslovSRB());
		txtNaslovOriginal.setText(film.getNaslovOriginal());
		txtGodinaIzdanja.setText(film.getGodinaIzdanja());
		txtZanr.setText(film.getZanr().getNaziv());
		txtReziser.setText(film.getImePrezimeRezisera());
		txtOpis.setText(film.getOpis());
		txtTrajanje.setText(film.getTrajanje());
	}
}
