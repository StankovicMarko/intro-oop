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
import filmovi.Zanr;
import gui.dodavanje.VideotekaEditovanjeDialog;
import gui.dodavanje.ZanroviDodavanjeDialog;
import gui.dodavanje.ZaposleniDodavanjeDialog;
import gui.obrisani.FilmObrisani;
import gui.obrisani.ZanroviObrisani;
import osobe.Zaposleni;
import videoteka.Videoteka;

public class ZanroviForma extends JFrame{

	private JToolBar mainToolbar;
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnDelete;
	private JScrollPane tableScroll;
	private JTable zanroviTabela;
	private Videoteka videoteka;
	private JButton btnObrisani;
	
	public ZanroviForma(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Prikaz Zanrova");
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
		
		String[] zaglavlja = new String[] {"ID", "Naziv"};
		
		int brojZanrova = 0;
		for (Zanr z: videoteka.getZanrovi()) {
			if( z.isObrisan() == false){
				
				brojZanrova++;
			}
			
		}
		Object[][] podaci  = new Object[brojZanrova][zaglavlja.length];
		int j =0;
		for(int i=0; i<this.videoteka.getZanrovi().size(); i++) {
			Zanr zanr= this.videoteka.getZanrovi().get(i);
			if(zanr.isObrisan() == false){
			podaci[j][0] = zanr.getID();
			podaci[j][1] = zanr.getNaziv();
			j++;
		}}
		
		j=0;
		DefaultTableModel model = new DefaultTableModel(podaci, zaglavlja);
		zanroviTabela = new JTable(model);
		zanroviTabela.setColumnSelectionAllowed(false);
		zanroviTabela.setRowSelectionAllowed(true);
		zanroviTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		zanroviTabela.setDefaultEditor(Object.class, null);
		
		tableScroll = new JScrollPane(zanroviTabela);
		add(tableScroll, BorderLayout.CENTER);
	}
	
	public JTable getZanroviTabela() {
		return zanroviTabela;
	}

	private void initActions() {
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selektovaniRed = ZanroviForma.this.zanroviTabela.getSelectedRow();
				if(selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati polje.");
				}else {
					int izbor = JOptionPane.showConfirmDialog(null, "Da li ste sigurni",
							"Brisanje zanra", JOptionPane.YES_NO_OPTION);
					
					if(izbor == JOptionPane.YES_OPTION) {
						String id = (String)
								ZanroviForma.this.zanroviTabela.getValueAt(selektovaniRed, 0);
						Zanr zanr = ZanroviForma.this.videoteka.pronadjiZanr(id);
						DefaultTableModel model = (DefaultTableModel)
								ZanroviForma.this.zanroviTabela.getModel();
						zanr.setObrisan(true);
//						ZanroviForma.this.videoteka.getZanrovi().remove(zanr);
						ZanroviForma.this.videoteka.sacuvajZanrove();
						model.removeRow(selektovaniRed);
					}
				}
			}
		});
		
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ZanroviDodavanjeDialog cdd = new ZanroviDodavanjeDialog(
													ZanroviForma.this.videoteka, 
													null, 
													ZanroviForma.this);
				cdd.setVisible(true);
			}
		});
		
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selektovaniRed = ZanroviForma.this.zanroviTabela.getSelectedRow();
				if(selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, 
							"Morate odabrati polje.");
					
				}else {
					String ID = (String)
							ZanroviForma.this.zanroviTabela.getValueAt(selektovaniRed, 0);
					Zanr zanr= ZanroviForma.this.videoteka.pronadjiZanr(ID);
					ZanroviDodavanjeDialog pdd = new ZanroviDodavanjeDialog(
													ZanroviForma.this.videoteka, 
													zanr, 
													ZanroviForma.this);
						pdd.setVisible(true);
				}
			}
		});
		
		btnObrisani.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ZanroviObrisani zo = new ZanroviObrisani(
						ZanroviForma.this.videoteka);
						zo.setVisible(true);
			}
		});
		
	}
}
