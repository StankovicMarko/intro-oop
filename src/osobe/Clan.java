package osobe;

public class Clan extends Osobe {
	private String brojClanskeKarte;
	private boolean aktivan;


	public Clan() {
		super();
		this.brojClanskeKarte = "";
		this.aktivan = true;

	}
	
	public Clan(String ime, String prezime, String JMBG, String adresa, 
			Pol pol, String brojClanskeKarte, boolean aktivan) {
		super(ime, prezime, JMBG, adresa, pol);
		this.brojClanskeKarte = brojClanskeKarte;
		this.aktivan=aktivan;
	}

	public String getBrojClanskeKarte() {
		return brojClanskeKarte;
	}

	public void setBrojClanskeKarte(String brojClanskeKarte) {
		this.brojClanskeKarte = brojClanskeKarte;
	}

	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}
	
	@Override
	public String toString() {
		return "\nIme: " + ime
				 + "\nPrezime: " + prezime 
				 + "\nClanska karta: " + brojClanskeKarte
				 + "\nAktivnost: " + aktivan;
	}
	
}
