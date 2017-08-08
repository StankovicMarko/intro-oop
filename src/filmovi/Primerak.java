package filmovi;

public class Primerak {
	private String ID;
	private Film film;
	private Medijum medijum;
	private String brojMedijuma;
	private boolean izdat;
	private boolean obrisan; 
	
	
	public Primerak(){
		this.ID="";
		this.film=null;
		this.medijum=null;
		this.brojMedijuma="";
		this.izdat=false;
		this.obrisan=false;
		
	}


	public Primerak(String ID, Film film, Medijum medijum, String brojPrimeraka,
			boolean izdat ,boolean obrisan) {
		super();
		this.ID = ID;
		this.film = film;
		this.medijum = medijum;
		this.brojMedijuma = brojPrimeraka;
		this.izdat=izdat;
		this.obrisan=obrisan;
	}
	
	public Primerak(Primerak original) {
		super();
		this.ID = original.ID;
		this.film = original.film;
		this.medijum = original.medijum;
		this.brojMedijuma = original.brojMedijuma;
		this.izdat = original.izdat;
		this.obrisan = original.obrisan;
	}
	

	public String getID() {
		return ID;
	}


	public void setID(String iD) {
		ID = iD;
	}


	public Film getFilm() {
		return film;
	}


	public void setFilm(Film film) {
		this.film = film;
	}


	public Medijum getMedijum() {
		return medijum;
	}


	public void setMedijum(Medijum medijum) {
		this.medijum = medijum;
	}


	
	public String getBrojMedijuma() {
		return brojMedijuma;
	}


	public void setBrojMedijuma(String brojMedijuma) {
		this.brojMedijuma = brojMedijuma;
	}


	public boolean isObrisan() {
		return obrisan;
	}


	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}


	public boolean isIzdat() {
		return izdat;
	}


	public void setIzdat(boolean izdat) {
		this.izdat = izdat;
	}


	public boolean equals(Primerak other){

		
		return this.getFilm().equals(other.getFilm())
				&& this.getMedijum().equals(other.getMedijum());
				
		
	}
	
	@Override
	
	public String toString(){
		return "\nPIB: " + ID
				 + "\nFilm: " + film.getNaslovSRB()
				 + "\nMedijum: " + medijum
				 + "\nKolicina " + brojMedijuma;
	}
	
	
}
