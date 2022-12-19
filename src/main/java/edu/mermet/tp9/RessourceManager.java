package edu.mermet.tp9;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class RessourceManager {

	private static RessourceManager gestionnaire;

	private Locale lieu;
	private ResourceBundle ressources;
	private List<Traduisible> traduisibles;

	private RessourceManager() {
		lieu = Locale.getDefault();
		ressources = ResourceBundle.getBundle("text");
		traduisibles = new ArrayList<>();
	}

	public static RessourceManager getRessourceManager() {
		if (gestionnaire == null) {
			gestionnaire = new RessourceManager();
		}
		return gestionnaire;
	}

	public String getString(Ressource res) {
		return ressources.getString(res.getNom());
	}

	public void definirLieu(Locale lieu) {
		this.lieu = lieu;
		ressources = ResourceBundle.getBundle("text", lieu);
		fireTraduire();
	}

	public Locale getLieu() {
		return lieu;
	}

	public void addTraduisible(Traduisible trad) {
		traduisibles.add(trad);
	}

	public void removeTraduisible(Traduisible trad) {
		traduisibles.remove(trad);
	}

	private void fireTraduire() {
		for (Traduisible trad: traduisibles) {
			trad.traduire();
		}
	}
}
