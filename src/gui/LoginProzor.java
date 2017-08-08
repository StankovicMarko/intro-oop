package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import osobe.Zaposleni;
import videoteka.Videoteka;

public class LoginProzor extends JFrame {

	private JLabel lblPoruka;
	private JLabel lblKorisnickoIme;
	private JTextField txtKorisnickoIme;
	private JLabel lblSifra;
	private JPasswordField pfSifra;
	private JButton btnOK;
	private JButton btnCancel;
	private Videoteka videoteka;
	
	public LoginProzor(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Prijava");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		initGUI();
		initActions();
		pack();
		setLocationRelativeTo(null);
	}
	
	private void initGUI() {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[]20[][]20[]");
		setLayout(layout);
		
		this.lblPoruka = new JLabel("Dobrodošli. Molimo da se prijavite.");
		this.lblKorisnickoIme = new JLabel("Korisničko ime");
		this.txtKorisnickoIme = new JTextField(20);
		this.lblSifra = new JLabel("Šifra");
		this.pfSifra = new JPasswordField(20);
		this.btnOK = new JButton("OK");
		this.btnCancel = new JButton("Cancel");
		
		this.getRootPane().setDefaultButton(btnOK);
		
		add(lblPoruka, "span 2");
		add(lblKorisnickoIme);
		add(txtKorisnickoIme);
		add(lblSifra);
		add(pfSifra);
		add(new JLabel());
		add(btnOK, "split 2");
		add(btnCancel);
	}
	
	private void initActions() {
		
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String korisnickoIme = txtKorisnickoIme.getText().trim();
				String sifra = new String(pfSifra.getPassword());
				
				if(korisnickoIme.equals("") || sifra.equals("")) {
					JOptionPane.showMessageDialog(null, "Niste uneli sve podatke.");
				}else {
					Zaposleni zaposleni = videoteka.login(korisnickoIme, sifra);
					if(zaposleni != null) {
						LoginProzor.this.setVisible(false);
						LoginProzor.this.dispose();
						GlavniProzor glavni = new GlavniProzor(videoteka, zaposleni);
						glavni.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Pogrešni login podaci!");
					}
				}
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginProzor.this.setVisible(false);
				LoginProzor.this.dispose();
			}
		});
		
	}
}