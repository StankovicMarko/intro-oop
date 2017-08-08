package videoteka;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import filmovi.Film;
import filmovi.Medijum;
import filmovi.Primerak;
import filmovi.Zanr;
import iznajmljivanje.Iznajmljivanje;
import osobe.Clan;
import osobe.Pol;
import osobe.Zaposleni;
import util.Util;

public class Videoteka {

	private String PIB;
	private String naziv;
	private String adresa;

	private ArrayList<Zaposleni> zaposleni;
	private ArrayList<Clan> clanovi;
	private ArrayList<Film> filmovi;
	private ArrayList<Zanr> zanrovi;
	private ArrayList<Primerak> primerci;
	private ArrayList<Iznajmljivanje> iznajmljivanje;
	private ArrayList<Iznajmljivanje> vracenaIznajmljivanja;

	
	public Videoteka(){
		this.PIB = "";
		this.naziv = "";
		this.adresa = "";
		this.zaposleni = new ArrayList<Zaposleni>();
		this.clanovi= new ArrayList<Clan> ();
		this.filmovi = new ArrayList<Film> ();
		this.zanrovi= new ArrayList<Zanr>();
		this.primerci = new ArrayList<Primerak> (); 
		this.iznajmljivanje= new ArrayList<Iznajmljivanje>();
		this.vracenaIznajmljivanja= new ArrayList<Iznajmljivanje>();
		
	}
	
	
	public Videoteka(String PIB, String naziv, String adresa, ArrayList<Zaposleni> zaposleni, ArrayList<Clan> clanovi,
			ArrayList<Film> filmovi, ArrayList<Zanr> zanrovi, ArrayList<Primerak> primerci,
			ArrayList<Primerak> izdatiPrimerci,
			ArrayList<Iznajmljivanje> iznajmljivanje) {
		super();
		this.PIB = PIB;
		this.naziv = naziv;
		this.adresa = adresa;
		this.zaposleni = zaposleni;
		this.clanovi = clanovi;
		this.filmovi = filmovi;
		this.zanrovi = zanrovi;
		this.primerci = primerci;
		this.iznajmljivanje = iznajmljivanje;
	}

	public void ucitajInformacije(){
		ArrayList<String> lines = Util.readFile("src/fajlovi/videoteka.txt");
		for (String line : lines) {
			String[] l = line.split("\\|");
			this.setPIB(l[0]);
			this.setNaziv(l[1]);
			this.setAdresa(l[2]);
			break;
		
		}
	}

	public void ucitajZaposlene() {
		ArrayList<String> lines = Util.readFile("src/fajlovi/zaposleni.txt");

		for (String line : lines) {
			String[] l = line.split("\\|");
			Zaposleni z = new Zaposleni(l[0], l[1], l[2], l[3], 
					Pol.fromInt(Integer.parseInt(l[4])), 
					l[5], l[6], l[7],Boolean.parseBoolean(l[8]));
			zaposleni.add(z);
		}

	}
	
	public void ucitajClanove() {
		ArrayList<String> lines = Util.readFile("src/fajlovi/clanovi.txt");

		for (String line : lines) {
			String[] l = line.split("\\|");
			Clan c = new Clan(l[0], l[1], l[2], l[3], 
					Pol.fromInt(Integer.parseInt(l[4])), l[5],
					Boolean.parseBoolean(l[6]));
			clanovi.add(c);
		}

	}
	
	public void ucitajZanrove() {
		ArrayList<String> lines = Util.readFile("src/fajlovi/zanr.txt");

		for (String line : lines) {
			String[] l = line.split("\\|");
			boolean obrisan = Boolean.valueOf(l[2]);
			Zanr z = new Zanr(l[0], l[1], obrisan);
			zanrovi.add(z);
		}
	}
	
	public void ucitajFilmove() {
		ArrayList<String> lines = Util.readFile("src/fajlovi/filmovi.txt");

		for (String line : lines) {
			String[] l = line.split("\\|");
			for (Zanr zanr : zanrovi) {
				if (zanr.getID().equals(l[3])) {
					boolean obrisan = Boolean.valueOf(l[7]);
					Film f = new Film(l[0], l[1], l[2], zanr, l[4], l[5], l[6],obrisan);
					filmovi.add(f);
				}

			}
		}
	}
	
	public void ucitajPrimerke() {
		ArrayList<String> lines = Util.readFile("src/fajlovi/primerci.txt");

		for (String line : lines) {
			String[] l = line.split("\\|");

			for (Film film : filmovi) {
				if (l[1].equals(film.getNaslovSRB())) {
					boolean obrisan = Boolean.valueOf(l[5]);
					boolean izdat = Boolean.valueOf(l[4]);
					Primerak primerak = new Primerak(l[0], 
							film, Medijum.fromInt(Integer.parseInt(l[2])), 
							l[3],izdat,obrisan);
					primerci.add(primerak);
				}
			}

		}
	}
	
	
	public void ucitajIznajmljivanja() {
		ArrayList<String> lines = Util.readFile(
				"src/fajlovi/iznajmljivanje.txt");

		for (String line : lines) {
			String[] l = line.split("\\|");

			Zaposleni zaposleniIzFajla = null;
			Clan clanIzFajla = null;
			ArrayList<Primerak> primerciIzFajla = new ArrayList<Primerak>();

			for (Zaposleni z : zaposleni) {
				if (l[1].equals(z.getJMBG())) {
					zaposleniIzFajla = z;
					break;
				}
			}

			for (Clan c : clanovi) {
				if (l[2].equals(c.getBrojClanskeKarte())) {
					clanIzFajla = c;
					break;
				}
			}

			String[] brojeviPrimeraka = l[5].split(",");
			for (Primerak p : primerci) {
				for (String b : brojeviPrimeraka) {
					if (b.equals(p.getID())) {
						primerciIzFajla.add(p);
					}

				}
			}
		    DateFormat df = new SimpleDateFormat("ddd/MM/yyyy"); 
		    Date datumIznamljivanja=null;
		    try {
		    	datumIznamljivanja = df.parse(l[3]);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    
		    Date datumVracanja=null;
		    try {
		    	datumVracanja = df.parse(l[4]);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
	      
			Iznajmljivanje iz = new Iznajmljivanje(l[0], 
					zaposleniIzFajla, clanIzFajla, datumIznamljivanja, datumVracanja,
					primerciIzFajla, l[6]);

			iznajmljivanje.add(iz);
		}
	}
	
	public void ucitajVracenaIznajmljivanja() {
		ArrayList<String> lines = Util.readFile(
				"src/fajlovi/vracenoIznajmljivanje.txt");

		for (String line : lines) {
			String[] l = line.split("\\|");

			Zaposleni zaposleniIzFajla = null;
			Clan clanIzFajla = null;
			ArrayList<Primerak> primerciIzFajla = new ArrayList<Primerak>();

			for (Zaposleni z : zaposleni) {
				if (l[1].equals(z.getJMBG())) {
					zaposleniIzFajla = z;
					break;
				}
			}

			for (Clan c : clanovi) {
				if (l[2].equals(c.getBrojClanskeKarte())) {
					clanIzFajla = c;
					break;
				}
			}

			String[] brojeviPrimeraka = l[5].split(",");
			for (Primerak p : primerci) {
				for (String b : brojeviPrimeraka) {
					if (b.equals(p.getID())) {
						primerciIzFajla.add(p);
					}

				}
			}
		    DateFormat df = new SimpleDateFormat("ddd/MM/yyyy"); 
		    Date datumIznamljivanja=null;
		    try {
		    	datumIznamljivanja = df.parse(l[3]);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    
		    Date datumVracanja=null;
		    try {
		    	datumVracanja = df.parse(l[4]);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
	      
			Iznajmljivanje iz = new Iznajmljivanje(l[0], 
					zaposleniIzFajla, clanIzFajla, datumIznamljivanja, datumVracanja,
					primerciIzFajla, l[6]);

			vracenaIznajmljivanja.add(iz);
		}
	}
	
	public void sacuvajIznajmljivanje() {

		try {
			File fajl = new File("src/fajlovi/iznajmljivanje.txt");
			String content = "";

			for (Iznajmljivanje iz : iznajmljivanje) {
				String contentPrimerci = "";
				for (Primerak p : iz.getPrimerci()) {
					contentPrimerci += p.getID() + ",";
				}
				Date datumUzmanja = iz.getDatumUzimanja();
				Date datumVracanja = iz.getDatumVracanja();
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				String datumUz = df.format(datumUzmanja);
				String datumVr = "00/00/0000";
				if(datumVracanja != null){
					datumVr = df.format(datumVracanja);
				}
	
				content += iz.getID()+ "|" + iz.getZaposleni().getJMBG() 
						+ "|" + iz.getClan().getBrojClanskeKarte()
						+ "|" + datumUz
						+ "|" + datumVr + "|" + contentPrimerci 
						+ "|"+ iz.getCena() + "\n";
			}

			BufferedWriter bf = new BufferedWriter(new FileWriter(fajl));
			bf.write(content);
			bf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void sacuvajVracenaIznajmljivanja() {

		try {
			File fajl = new File("src/fajlovi/vracenoIznajmljivanje.txt");
			String content = "";

			for (Iznajmljivanje iz : vracenaIznajmljivanja) {
				String contentPrimerci = "";
				for (Primerak p : iz.getPrimerci()) {
					contentPrimerci += p.getID() + ",";
				}
				
				Date datumUzmanja = iz.getDatumUzimanja();
				Date datumVracanja = iz.getDatumVracanja();
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				String datumUz = df.format(datumUzmanja);
				String datumVr = df.format(datumVracanja);

				content += iz.getID()+ "|" + iz.getZaposleni().getJMBG() 
						+ "|" + iz.getClan().getBrojClanskeKarte()
						+ "|" + datumUz 
						+ "|" + datumVr + "|" + contentPrimerci 
						+ "|"+ iz.getCena() + "\n";
			}

			BufferedWriter bf = new BufferedWriter(new FileWriter(fajl));
			bf.write(content);
			bf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sacuvajPrimerke() {

		try {
			File f = new File("src/fajlovi/primerci.txt");
			String content = "";

			for (Primerak p : primerci) {
				content += p.getID() + "|" + p.getFilm().getNaslovSRB() 
						+ "|" + Medijum.toInt(p.getMedijum()) 
						+ "|"+ p.getBrojMedijuma()
						+ "|" + p.isIzdat()
						+ "|" + p.isObrisan() + "\n";
			}

			BufferedWriter bf = new BufferedWriter(new FileWriter(f));
			bf.write(content);
			bf.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void sacuvajClanove() {
		try {
			File f = new File("src/fajlovi/clanovi.txt");
			String content = "";

			for (Clan clan : clanovi) {
				content += clan.getIme() + "|" + clan.getPrezime() + 
						"|" + clan.getJMBG() + "|" + clan.getAdresa() 
						+ "|" + Pol.toInt(clan.getPol()) 
						+ "|" + clan.getBrojClanskeKarte() + "|"
						+ clan.isAktivan() + "\n";
			}

			BufferedWriter bf = new BufferedWriter(new FileWriter(f));
			bf.write(content);
			bf.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sacuvajZanrove() {

		try {
			File f = new File("src/fajlovi/zanr.txt");
			String content = "";

			for (Zanr z : zanrovi) {
				content += z.getID() + "|" + z.getNaziv() +
						"|" + z.isObrisan() + "\n";
			}

			BufferedWriter bf = new BufferedWriter(new FileWriter(f));
			bf.write(content);
			bf.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void sacuvajFilmove() {
		try {
			File fajl = new File("src/fajlovi/filmovi.txt");
			String content = "";

			for (Film f : filmovi) {
				content += f.getNaslovSRB() + "|" + f.getNaslovOriginal() 
						+ "|" + f.getGodinaIzdanja() + "|"
						+ f.getZanr().getID() 
						+ "|" + f.getImePrezimeRezisera() 
						+ "|" + f.getOpis() + "|"
						+ f.getTrajanje() 
						+ "|" + f.isObrisan() + "\n";
			}

			BufferedWriter bf = new BufferedWriter(new FileWriter(fajl));
			bf.write(content);
			bf.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sacuvajZaposlene() {
		try {
			File f = new File("src/fajlovi/zaposleni.txt");
			String content = "";

			for (Zaposleni z : zaposleni) {
				content += z.getIme() + "|" + z.getPrezime() 
						+ "|" + z.getJMBG() + "|" + z.getAdresa() 
						+ "|"+ Pol.toInt(z.getPol()) + "|" + z.getPlata() 
						+ "|" + z.getUsn() + "|" + z.getPsw()  
						+ "|" + z.isObrisan() +"\n";

			}

			BufferedWriter bf = new BufferedWriter(new FileWriter(f));
			bf.write(content);
			bf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sacuvajInformacije() {
		try {
			File fajl = new File("src/fajlovi/videoteka.txt");
			String content = "";

			content += this.getPIB()
					+ "|" + this.getNaziv()
					+ "|" + this.getAdresa() + "\n";
			

			BufferedWriter bf = new BufferedWriter(new FileWriter(fajl));
			bf.write(content);
			bf.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void dodajZanr(Zanr novi) {
		this.zanrovi.add(novi);
	}
	
	public ArrayList<Zaposleni> getZaposleni() {
		return zaposleni;
	}

	public void dodajZaposlenog(Zaposleni novi) {
		this.zaposleni.add(novi);
	}
	
	public void obrisiZaposlenog(Zaposleni zaposlenog) {
		this.zaposleni.remove(zaposlenog);
	}

	public Zaposleni nadjiZaposlenog(String usn) {
		for (Zaposleni zaposlen : zaposleni) {
			if(zaposlen.getUsn().equals(usn)) {
				return zaposlen;
			}
		}
		return null;
	}
	
	public Zaposleni login(String usn, String psw) {
		for (Zaposleni zaposlen : zaposleni) {
			if(zaposlen.getUsn().equals(usn) &&
					zaposlen.getPsw().equals(psw)) {
				return zaposlen;
			}
		}
		return null;
	}
	
	public Clan pronadjiClana(String brojCK) {
		for (Clan clan : clanovi) {
			if (clan.getBrojClanskeKarte().equals(brojCK)) {
				return clan;
			}
		}
		return null;
	}
	
	public Zanr pronadjiZanr(String id) {

		for (Zanr z : zanrovi) {
			if (z.getID().equals(id)) {
				return z;
			}
		}
		return null;

	}
	
	public Primerak pronadjiPrimerak(String id) {
		for (Primerak p : primerci) {
			if (p.getID().equals(id)) {
				return p;
			}
		}
		return null;
	}
	
	public String getPIB() {
		return PIB;
	}
	
	public boolean sadrziFilm(Film film) {
		for (Film f : filmovi) {
			if (f.equals(film)) {
				return true;
			}
		}
		return false;
	}
	
	public Film pronadjiFilm(String nazivSrpski) {
		for (Film f : filmovi) {
			if (f.getNaslovSRB().equals(nazivSrpski)) {
				return f;
			}
		}
		return null;
	}
	
	public Zaposleni pronadjiZaposlenog(String username){
		for (Zaposleni z : zaposleni) {
			if (z.getUsn().equals(username)) {
				return z;
			}
		}
		return null;
	}
	
	public String odrediIDIznajmljivanja() {
		return Integer.toString(iznajmljivanje.size()+vracenaIznajmljivanja.size()
		+ 1);

	}
	
	public String odrediBrojClanskeKarte() {

		int broj = clanovi.size() + 1;

		return String.valueOf(broj);
	}
	
	public String odrediOznakuZanra() {
		 
		int broj =  Integer.parseInt(zanrovi.get(zanrovi.size() - 1).getID()) + 1;

		return String.valueOf(broj);
	}
	
	public String odrediIdPrimerka() {
		int id = primerci.size() + 1;
		return Integer.toString(id);

	}
	
	public Iznajmljivanje pronadjiIznamljivanje(String ID){
		for (Iznajmljivanje i : iznajmljivanje) {
			if (i.getID().equals(ID)) {
				return i;
			}
		}
		return null;
	}


	public void setPIB(String PIB) {
		this.PIB = PIB;
	}


	public String getNaziv() {
		return naziv;
	}


	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}


	public String getAdresa() {
		return adresa;
	}


	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	
	public ArrayList<Primerak> getPrimerci() {
		return primerci;
	}
	
	public void dodajPrimerak(Primerak primerak) {
		this.primerci.add(primerak);
	}
	
	public void obrisiPrimerak(Primerak primerak) {
		this.primerci.remove(primerak);
	}
	
	
	public ArrayList<Clan> getClanovi() {
		return clanovi;
	}


	public ArrayList<Film> getFilmovi() {
		return filmovi;
	}


	public ArrayList<Zanr> getZanrovi() {
		return zanrovi;
	}


	public ArrayList<Iznajmljivanje> getIznajmljivanje() {
		return iznajmljivanje;
	}
	

	public ArrayList<Iznajmljivanje> getVracenaIznajmljivanja() {
		return vracenaIznajmljivanja;
	}

	


	@Override
	public String toString(){
		return "\nPIB: " + PIB
				 + "\nNaziv: " + naziv
				 + "\nAdresa: " + adresa;
	}

}
