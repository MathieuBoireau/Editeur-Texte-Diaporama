package edu.mermet.tp9.actions;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Properties;

import javax.swing.Action;
import javax.swing.KeyStroke;

import edu.mermet.tp9.Application;
import edu.mermet.tp9.PreferencesManager;
import edu.mermet.tp9.Ressource;

@SuppressWarnings("serial")
public class ActionQuitter extends AbstractActionTraduisible {
	public static final double EXP_DONNEE = 0.1;
	public static final double NIV_MAX    = 4.0;
	private final Application application;

	public ActionQuitter(Application application) {
		super(Ressource.ACTION_QUITTER);
		this.application = application;
		putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
		putValue(Action.MNEMONIC_KEY,KeyEvent.VK_Q);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		PreferencesManager preferencesManager = PreferencesManager.getPreferencesManager(application.getLogin());
		Properties properties = preferencesManager.getCopyProperties();
		double niveau = Double.parseDouble(properties.getProperty(PreferencesManager.NIVEAU));
		if(niveau < ActionQuitter.NIV_MAX){
			niveau += ActionQuitter.EXP_DONNEE;
			properties.setProperty(PreferencesManager.NIVEAU, String.valueOf(niveau));
			preferencesManager.save(properties);
		}
		System.exit(0);
	}
}
