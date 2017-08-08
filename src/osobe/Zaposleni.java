package osobe;

import javax.naming.spi.ObjectFactoryBuilder;

import filmovi.Film;

public class Zaposleni extends Osobe {
	private String plata;
	private String usn;
	private String psw;
	private boolean obrisan;

	public Zaposleni() {
		super();
		this.plata ="";
		this.usn = "";
		this.psw = "";
		this.obrisan=false;

	}
	
	
	public Zaposleni(String ime, String prezime, String JMBG, String adresa, 
			 Pol pol, String plata, String usn, String psw, boolean obrisan) {
		super(ime, prezime, JMBG, adresa, pol);
		this.plata = plata;
		this.usn = usn;
		this.psw = psw;
		this.obrisan = obrisan;
	}




	public String getPlata() {
		return plata;
	}



	public void setPlata(String plata) {
		this.plata = plata;
	}



	public String getUsn() {
		return usn;
	}

	public void setUsn(String usn) {
		this.usn = usn;
	}

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}
	
	public boolean isObrisan() {
		return obrisan;
	}


	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}


	public boolean equals(Zaposleni other){
		//Film that = (Film) other;
		
		return this.JMBG.equals(other.getJMBG());
	}


	@Override
	public String toString() {
		return "\nIme: " + ime
				 + "\nPrezime: " + prezime 
				 + "\nJMBG: " + JMBG
				 + "\npol: " + pol
				 + "\nPlata: " + plata
				 + "\nUsername: " + usn;
	}
}
