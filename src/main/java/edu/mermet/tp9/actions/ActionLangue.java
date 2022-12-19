package edu.mermet.tp9.actions;

import java.awt.event.ActionEvent;
import java.util.Locale;

import edu.mermet.tp9.Application;
import edu.mermet.tp9.Ressource;
import edu.mermet.tp9.RessourceManager;

@SuppressWarnings("serial")
public class ActionLangue extends AbstractActionTraduisible {
	private final Application application;

	private Locale lieu;

	public ActionLangue(Application application, Ressource nom, Locale lieu) {
		super(nom);
		this.application = application;
		this.lieu = lieu;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		RessourceManager.getRessourceManager().definirLieu(lieu);
	}
}