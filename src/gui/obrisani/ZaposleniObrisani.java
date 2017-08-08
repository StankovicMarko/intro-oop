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
import osobe.Zaposleni;
import videoteka.Videoteka;

public class ZaposleniObrisani  extends JDialog{
	private Videoteka videoteka;
	private JScrollPane tableScroll;
	private JTable zaposleniTabela;
	private JToolBar mainToolbar;
	private JButton btnRestore;

	
	public ZaposleniObrisani(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Obrisani zaposleni");
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
		
		String[] zaglavlja = new String[] {"Korisničko ime", "Ime", "Prezime", "Pol",
				"Plata", "JMBG", "Adresa"};

		int brojZaposlenih= 0;
		for (Zaposleni z : videoteka.getZaposleni()) {
			if( z.isObrisan()){
		
				brojZaposlenih++;
			}
		
		}
		Object[][] podaci  = new Object[brojZaposlenih]
					[zaglavlja.length];
		int j =0;
		for(int i=0; i<this.videoteka.getZaposleni().size(); i++) {
			Zaposleni zaposleni = this.videoteka.getZaposleni().get(i);
		
			if(zaposleni.isObrisan()){
				podaci[j][0] = zaposleni.getUsn();
				podaci[j][1] = zaposleni.getIme();
				podaci[j][2] = zaposleni.getPrezime();
				podaci[j][3] = zaposleni.getPol();
				podaci[j][4] = zaposleni.getPlata();
				podaci[j][5] = zaposleni.getJMBG();
				podaci[j][6] = zaposleni.getAdresa();
				j++;
		
		}}
		j=0;
		DefaultTableModel model = new DefaultTableModel(podaci, zaglavlja);
		zaposleniTabela = new JTable(model);
		zaposleniTabela.setColumnSelectionAllowed(false);
		zaposleniTabela.setRowSelectionAllowed(true);
		zaposleniTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		zaposleniTabela.setDefaultEditor(Object.class, null);
		
		tableScroll = new JScrollPane(zaposleniTabela);
		add(tableScroll, BorderLayout.CENTER);
	
	}
	private void initActions(){
		btnRestore.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selektovaniRed = ZaposleniObrisani.this.zaposleniTabela.getSelectedRow();
				if(selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati zaposlenog.");
				}else {
					int izbor = JOptionPane.showConfirmDialog(null, "Da li ste sigurni",
							"Restore zaposlenog", JOptionPane.YES_NO_OPTION);
					
					if(izbor == JOptionPane.YES_OPTION) {
						String username = (String)
								ZaposleniObrisani.this.zaposleniTabela.getValueAt(selektovaniRed, 0);
						Zaposleni zaposleni = ZaposleniObrisani.this.videoteka.pronadjiZaposlenog(username);
						DefaultTableModel model = (DefaultTableModel)
								ZaposleniObrisani.this.zaposleniTabela.getModel();
						zaposleni.setObrisan(false);
						ZaposleniObrisani.this.videoteka.sacuvajZaposlene();
						model.removeRow(selektovaniRed);
					}
				}
				
			}

			
		});}
}

		
			
		
	


