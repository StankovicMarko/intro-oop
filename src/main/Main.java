package main;

import gui.LoginProzor;
import videoteka.Videoteka;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Videoteka videoteka = new Videoteka();
		videoteka.ucitajInformacije();
		videoteka.ucitajZaposlene();
		videoteka.ucitajClanove();
		videoteka.ucitajZanrove();
		videoteka.ucitajFilmove();
		videoteka.ucitajPrimerke();
		videoteka.ucitajIznajmljivanja();
		videoteka.ucitajVracenaIznajmljivanja();
		

		LoginProzor login = new LoginProzor(videoteka);
		login.setVisible(true);

	}

	
}
