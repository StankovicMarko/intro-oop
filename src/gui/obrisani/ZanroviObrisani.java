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
import filmovi.Zanr;
import videoteka.Videoteka;

public class ZanroviObrisani extends JDialog{
	private Videoteka videoteka;
	private JScrollPane tableScroll;
	private JTable zanrTabela;
	private JToolBar mainToolbar;
	private JButton btnRestore;

	
	public ZanroviObrisani(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Obrisani Zanrovi");
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
		
		String[] zaglavlja = new String[] {"ID", "Naziv"};
		
		int brojZanrova = 0;
		for (Zanr z: videoteka.getZanrovi()) {
			if( z.isObrisan()){
				
				brojZanrova++;
			}
			
		}
		Object[][] podaci  = new Object[brojZanrova][zaglavlja.length];
		int j =0;
		for(int i=0; i<this.videoteka.getZanrovi().size(); i++) {
			Zanr zanr= this.videoteka.getZanrovi().get(i);
			if(zanr.isObrisan()){
			podaci[j][0] = zanr.getID();
			podaci[j][1] = zanr.getNaziv();
			j++;}
		}
		
		j=0;
		DefaultTableModel model = new DefaultTableModel(podaci, zaglavlja);
		zanrTabela = new JTable(model);
		zanrTabela.setColumnSelectionAllowed(false);
		zanrTabela.setRowSelectionAllowed(true);
		zanrTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		zanrTabela.setDefaultEditor(Object.class, null);
		
		tableScroll = new JScrollPane(zanrTabela);
		add(tableScroll, BorderLayout.CENTER);
	
	}
	private void initActions(){
		btnRestore.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selektovaniRed = ZanroviObrisani.this.zanrTabela.getSelectedRow();
				if(selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati zanr.");
				}else {
					int izbor = JOptionPane.showConfirmDialog(null, "Da li ste sigurni",
							"Restore zanra", JOptionPane.YES_NO_OPTION);
					
					if(izbor == JOptionPane.YES_OPTION) {
						String ID = (String)
								ZanroviObrisani.this.zanrTabela.getValueAt(selektovaniRed, 0);
						Zanr zanr= ZanroviObrisani.this.videoteka.pronadjiZanr(ID);
						DefaultTableModel model = (DefaultTableModel)
								ZanroviObrisani.this.zanrTabela.getModel();
						zanr.setObrisan(false);
						ZanroviObrisani.this.videoteka.sacuvajZanrove();
						model.removeRow(selektovaniRed);
					}
				}
				
			}

			
		});}
}

		
			
		
	

