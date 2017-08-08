package gui.dodavanje;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import gui.prikaz.VideotekaForma;
import net.miginfocom.swing.MigLayout;
import videoteka.Videoteka;

public class VideotekaEditovanjeDialog extends JDialog{
		private JLabel lblPIB;
		private JTextField txtPIB;
		private JLabel lblNaziv;
		private JTextField txtNaziv;
		private JLabel lblAdresa;
		private JTextField txtAdresa;
		private JButton btnOk;
		private JButton btnCancel;
		
		private Videoteka videoteka;
		private VideotekaForma videotekaForma;
	
		
		public VideotekaEditovanjeDialog(Videoteka videoteka, 
				VideotekaForma videotekaTabela) {
			this.videoteka = videoteka;
			this.videotekaForma=videotekaTabela;
			setTitle("Videoteka - Menjanje podataka");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setLocationRelativeTo(null);
			setModal(true);
			setResizable(false);
			initGUI();
			initActions();
			pack();
		}
		
		private void initGUI() {
			MigLayout layout = new MigLayout("wrap 2", "[][]", "[][][]20[]");
			setLayout(layout);
			
			lblPIB = new JLabel("Ime");
			txtPIB = new JTextField(20);
			lblNaziv = new JLabel("Prezime");
			txtNaziv = new JTextField(20);
			lblAdresa= new JLabel("Adresa");
			txtAdresa = new JTextField(20);
			btnOk = new JButton("OK");
			btnCancel = new JButton("Cancel");
					
			initValues();
			add(lblPIB);
			add(txtPIB);
			add(lblNaziv);
			add(txtNaziv);
			add(lblAdresa);
			add(txtAdresa);
			add(new JLabel());
			add(btnOk, "split 2");
			add(btnCancel);
			
		}
		
		private void initActions() {
			btnOk.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String PIB = txtPIB.getText().trim();
					String naziv = txtNaziv.getText().trim();
					String adresa = txtAdresa.getText().trim();
					
					if(PIB.equals("") || naziv.equals("") || adresa.equals("")) {
						JOptionPane.showMessageDialog(null, "Niste uneli sve podatke");
						}else {
		
			
							videoteka.setPIB(PIB);
							videoteka.setNaziv(naziv);
							videoteka.setAdresa(adresa);
							

							VideotekaEditovanjeDialog.this.videotekaForma.getVideotekaTabela()
									.setValueAt(PIB, 0, 0);
							VideotekaEditovanjeDialog.this.videotekaForma.getVideotekaTabela()
									.setValueAt(naziv, 0, 1);
							VideotekaEditovanjeDialog.this.videotekaForma.getVideotekaTabela()
									.setValueAt(adresa, 0, 2);
							
							VideotekaEditovanjeDialog.this.videoteka.sacuvajInformacije();
						}
						VideotekaEditovanjeDialog.this.setVisible(false);
						VideotekaEditovanjeDialog.this.dispose();
					}
			});
			
			btnCancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					VideotekaEditovanjeDialog.this.setVisible(false);
					VideotekaEditovanjeDialog.this.dispose();
				}
			});
		}
		
		private void initValues() {
			txtPIB.setText(videoteka.getPIB());
			txtNaziv.setText(videoteka.getNaziv());
			txtAdresa.setText(videoteka.getAdresa());
			
		}
	}