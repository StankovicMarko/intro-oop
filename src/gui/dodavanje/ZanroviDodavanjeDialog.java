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
import filmovi.Zanr;
import gui.prikaz.ZanroviForma;
import net.miginfocom.swing.MigLayout;
import videoteka.Videoteka;

public class ZanroviDodavanjeDialog extends JDialog {

	private JLabel lblNaziv;
	private JTextField txtNaziv;
	private JButton btnOk;
	private JButton btnCancel;

	
	private Videoteka videoteka;
	private Zanr zanr;
	private ZanroviForma zanroviForma;
	
	public ZanroviDodavanjeDialog(Videoteka videoteka, Zanr zanr,
									ZanroviForma zanroviForma) {
		this.videoteka = videoteka;
		this.zanr = zanr;
		this.zanroviForma = zanroviForma;
		setTitle("Zanrovi - Dodavanje");
		if(zanr != null) {
			setTitle("Zanrovi - Izmena");
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
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[][]20[]");
		setLayout(layout);
		
		lblNaziv = new JLabel("Naziv");
		txtNaziv = new JTextField(20);
		btnOk = new JButton("OK");
		btnCancel = new JButton("Cancel");
				
		
		if(zanr != null) {
			initValues();
		}
		
		add(lblNaziv);
		add(txtNaziv);
		add(new JLabel());
		add(btnOk, "split 2");
		add(btnCancel);
		
	}
	
	private void initActions() {
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String naziv = txtNaziv.getText().trim();
				
				
				if(naziv.equals("")) {
					JOptionPane.showMessageDialog(null, "Niste uneli sve podatke");
				}else {
					DefaultTableModel model = (DefaultTableModel)
							ZanroviDodavanjeDialog.this.zanroviForma.getZanroviTabela().getModel();
					if(zanr == null) {
						// DODAVANJE
						String ID  = videoteka.odrediOznakuZanra();
						Zanr zanr1 = new Zanr(ID , naziv,false);
						boolean flag = false;
						for (Zanr z: videoteka.getZanrovi()) {
							if (z.getNaziv().equals(naziv)){
								JOptionPane.showMessageDialog(null, "Zanr vec postoji");
								flag=true;
							}
						}
						if (!flag) {
						ZanroviDodavanjeDialog.this.videoteka.getZanrovi().add(zanr1);
						model.addRow(new Object[] {ID, naziv});
						ZanroviDodavanjeDialog.this.videoteka.sacuvajZanrove();
						}
					}else {
						// IZMENA
						String ID = zanr.getID();
						zanr.setID(ID);
						zanr.setNaziv(naziv);
						
						int red = ZanroviDodavanjeDialog.this.zanroviForma.getZanroviTabela()
								.getSelectedRow();
						ZanroviDodavanjeDialog.this.zanroviForma.getZanroviTabela()
								.setValueAt(ID, red, 0);
						ZanroviDodavanjeDialog.this.zanroviForma.getZanroviTabela()
								.setValueAt(naziv, red, 1);
					
						ZanroviDodavanjeDialog.this.videoteka.sacuvajZanrove();
					}
					ZanroviDodavanjeDialog.this.setVisible(false);
					ZanroviDodavanjeDialog.this.dispose();
				}
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ZanroviDodavanjeDialog.this.setVisible(false);
				ZanroviDodavanjeDialog.this.dispose();
			}
		});
	}
	
	private void initValues() {
//		txtID.setText(zanr.getID());
		txtNaziv.setText(zanr.getNaziv());
		
	}
}

