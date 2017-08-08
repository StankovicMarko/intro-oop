package filmovi;

import javax.print.attribute.standard.OrientationRequested;

public class Film {
	private String naslovSRB;
	private String naslovOriginal;
	private String godinaIzdanja;
	private Zanr zanr;
	private String imePrezimeRezisera;
	private String opis;
	private String trajanje;
	private boolean obrisan;

	public Film() {
		this.naslovSRB = "";
		this.naslovOriginal = "";
		this.godinaIzdanja = "";
		this.zanr = null;
		this.imePrezimeRezisera = "";
		this.opis="";
		this.trajanje = "";
		this.obrisan=false;

	}

	public Film(String naslovSRB, String naslovOriginal, String godinaIzdanja,
			Zanr zanr, String imePrezimeRezisera, String opis, String trajanje,
			boolean obrisan){
		this.naslovSRB=naslovSRB;
		this.naslovOriginal =naslovOriginal;
		this.godinaIzdanja =godinaIzdanja;
		this.zanr =zanr;
		this.imePrezimeRezisera =imePrezimeRezisera;
		this.opis=opis;
		this.trajanje =trajanje;
		this.obrisan=obrisan;
		
	}

	public String getNaslovSRB() {
		return naslovSRB;
	}

	public void setNaslovSRB(String naslovSRB) {
		this.naslovSRB = naslovSRB;
	}

	public String getNaslovOriginal() {
		return naslovOriginal;
	}

	public void setNaslovOriginal(String naslovOriginal) {
		this.naslovOriginal = naslovOriginal;
	}

	public String getGodinaIzdanja() {
		return godinaIzdanja;
	}

	public void setGodinaIzdanja(String godinaIzdanja) {
		this.godinaIzdanja = godinaIzdanja;
	}

	public Zanr getZanr() {
		return zanr;
	}

	public void setZanr(Zanr zanr) {
		this.zanr = zanr;
	}

	public String getImePrezimeRezisera() {
		return imePrezimeRezisera;
	}

	public void setImePrezimeRezisera(String imePrezimeRezisera) {
		this.imePrezimeRezisera = imePrezimeRezisera;
	}

	
	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(String trajanje) {
		this.trajanje = trajanje;
	}
	
	
	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}

	public boolean equals(Film other){
		//Film that = (Film) other;
		
		return this.getNaslovSRB().equals(other.getNaslovSRB())
				&& this.getNaslovOriginal().equals(other.getNaslovOriginal()) 
				&& this.getGodinaIzdanja().equals(other.getGodinaIzdanja())
				&& this.getZanr().equals(other.getZanr())
				&& this.getImePrezimeRezisera().equals(other.getImePrezimeRezisera());

		
	}
	@Override

	public String toString(){
		return "\nnaslovSRB: " + naslovSRB
				 + "\nnaslovOriginal: " + naslovOriginal
				 + "\ngodinaIzdanja: " + godinaIzdanja
				 + "\nzanr: " + zanr.getNaziv()
				 + "\reziser: " + imePrezimeRezisera
				 + "\nopis: " + opis
				 + "\ntrajanje: " + trajanje; 
		
	}
	
	

}
