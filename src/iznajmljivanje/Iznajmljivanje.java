package iznajmljivanje;

import java.util.ArrayList;
import java.util.Date;

import filmovi.Primerak;
import osobe.Clan;
import osobe.Zaposleni;

public class Iznajmljivanje {
	private String ID;
	private Zaposleni zaposleni;
	private Clan clan;
	private Date datumUzimanja;
	private Date datumVracanja;
	private ArrayList<Primerak> primerci;
	private String cena;
	
	
	public Iznajmljivanje(){
		this.ID="";
		this.zaposleni=null;
		this.clan=null;
		this.datumUzimanja=null;
		this.datumVracanja=null;
		this.primerci=new ArrayList<Primerak>();
		this.cena="";
	}


	public Iznajmljivanje(String iD, Zaposleni zaposleni, Clan clan, Date datumUzimanja, Date datumVracanja,
			ArrayList<Primerak> primerci, String cena) {
		super();
		ID = iD;
		this.zaposleni = zaposleni;
		this.clan = clan;
		this.datumUzimanja = datumUzimanja;
		this.datumVracanja = datumVracanja;
		this.primerci = primerci;
		this.cena = cena;
	}


	public String getID() {
		return ID;
	}


	public void setID(String iD) {
		ID = iD;
	}


	public Zaposleni getZaposleni() {
		return zaposleni;
	}


	public void setZaposleni(Zaposleni zaposleni) {
		this.zaposleni = zaposleni;
	}


	public Clan getClan() {
		return clan;
	}


	public void setClan(Clan clan) {
		this.clan = clan;
	}


	public Date getDatumUzimanja() {
		return datumUzimanja;
	}


	public void setDatumUzimanja(Date datumUzimanja) {
		this.datumUzimanja = datumUzimanja;
	}


	public Date getDatumVracanja() {
		return datumVracanja;
	}


	public void setDatumVracanja(Date datumVracanja) {
		this.datumVracanja = datumVracanja;
	}


	public ArrayList<Primerak> getPrimerci() {
		return primerci;
	}


	public void setPrimerci(ArrayList<Primerak> primerci) {
		this.primerci = primerci;
	}


	public String getCena() {
		return cena;
	}


	public void setCena(String cena) {
		this.cena = cena;
	}



	

}
