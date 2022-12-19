package edu.mermet.tp9.actions;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import edu.mermet.tp9.Application;
import edu.mermet.tp9.Ressource;

@SuppressWarnings("serial")
public class ActionConfigMenus extends AbstractActionTraduisible {
	private final Application application;

	public ActionConfigMenus(Application application) {
		super(Ressource.ACTION_CONFIG);
		this.application = application;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		application.afficherConfigMenus();
	}
}
