package edu.mermet.tp9.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import edu.mermet.tp9.Ressource;
import edu.mermet.tp9.RessourceManager;
import edu.mermet.tp9.Traduisible;

@SuppressWarnings("serial")
public class AbstractActionTraduisible extends AbstractAction implements Traduisible {

	private Ressource nom;

	public AbstractActionTraduisible(Ressource nom) {
		super(RessourceManager.getRessourceManager().getString(nom));
		this.nom = nom;
		RessourceManager.getRessourceManager().addTraduisible(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// to be overriden
	}

	@Override
	public void traduire() {
		putValue(Action.NAME, RessourceManager.getRessourceManager().getString(nom));
	}
	
}
