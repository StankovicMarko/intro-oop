package osobe;

public abstract class Osobe {
	protected String ime;
	protected String prezime;
	protected String JMBG;
	protected String adresa;
	protected Pol pol;

	public Osobe() {
		this.ime = "";
		this.prezime = "";
		this.JMBG = "";
		this.adresa = "";
		this.pol = Pol.ZENSKI;
	}
	
	public Osobe(String ime, String prezime, String JMBG,
			String adresa, Pol pol) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.JMBG = JMBG;
		this.adresa = adresa;
		this.pol = pol;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getJMBG() {
		return JMBG;
	}

	public void setJMBG(String jMBG) {
		JMBG = jMBG;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public Pol getPol() {
		return pol;
	}

	public void setPol(Pol pol) {
		this.pol = pol;
	}
	
}