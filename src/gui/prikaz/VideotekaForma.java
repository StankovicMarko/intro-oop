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

import gui.dodavanje.VideotekaEditovanjeDialog;
import videoteka.Videoteka;

public class VideotekaForma extends JFrame{

	private JToolBar mainToolbar;
	private JButton btnEdit;
	private JScrollPane tableScroll;
	private JTable videotekaTabela;
	private Videoteka videoteka;
	
	public VideotekaForma(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Prikaz Informacija Videoteke");
		setSize(700, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initGUI();
		initActions();
	}

	private void initGUI() {
		mainToolbar = new JToolBar();
		ImageIcon editIcon = new ImageIcon(getClass().getResource("/slike/edit.gif"));
		btnEdit = new JButton(editIcon);
		mainToolbar.add(btnEdit);

		
		add(mainToolbar, BorderLayout.NORTH);
		
		String[] zaglavlja = new String[] {"PIB", "Naziv", "Adresa"};
		Object[][] podaci  = new Object[1][zaglavlja.length];
		
		podaci[0][0] = videoteka.getPIB();
		podaci[0][1] = videoteka.getNaziv();
		podaci[0][2] = videoteka.getAdresa();
		
		
		DefaultTableModel model = new DefaultTableModel(podaci, zaglavlja);
		videotekaTabela = new JTable(model);
		videotekaTabela.setColumnSelectionAllowed(false);
		videotekaTabela.setRowSelectionAllowed(true);
		videotekaTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		videotekaTabela.setDefaultEditor(Object.class, null);
		
		tableScroll = new JScrollPane(videotekaTabela);
		add(tableScroll, BorderLayout.CENTER);
	}
	
	public JTable getVideotekaTabela() {
		return videotekaTabela;
	}

	private void initActions() {
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selektovaniRed = VideotekaForma.this.videotekaTabela.getSelectedRow();
				if(selektovaniRed == -1) {
					JOptionPane.showMessageDialog(null, 
							"Morate odabrati polje.");
					
				}else {
					VideotekaEditovanjeDialog ved = new VideotekaEditovanjeDialog(
													VideotekaForma.this.videoteka,
													VideotekaForma.this);
						ved.setVisible(true);
				}
			}
		});
		
	}
}