package filmovi;

public class Zanr {
	
	private String ID;
	private String naziv;
	private boolean obrisan;
	
	
	public Zanr(){
		this.ID="";
		this.naziv="";
		this.obrisan=false;
		
	}


	public Zanr(String ID, String naziv, boolean obrisan) {
		super();
		this.ID = ID;
		this.naziv = naziv;
		this.obrisan=obrisan;
	}


	public String getID() {
		return ID;
	}


	public void setID(String ID) {
		this.ID = ID;
	}


	public String getNaziv() {
		return naziv;
	}


	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}


	public boolean isObrisan() {
		return obrisan;
	}


	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}
	
	

}
