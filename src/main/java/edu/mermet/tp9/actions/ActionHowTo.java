package edu.mermet.tp9.actions;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import edu.mermet.tp9.Application;
import edu.mermet.tp9.Ressource;

@SuppressWarnings("serial")
public class ActionHowTo extends AbstractActionTraduisible {
	private final Application application;

	public ActionHowTo(Application application) {
		super(Ressource.ACTION_HOWTO);
		this.application = application;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		application.afficherHowTo();
	}
}
