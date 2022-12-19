package edu.mermet.tp9.actions;

import java.util.Properties;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import edu.mermet.tp9.Application;
import edu.mermet.tp9.Ressource;
import edu.mermet.tp9.PreferencesManager;

@SuppressWarnings("serial")
public class ActionAfficherConversion extends AbstractActionTraduisible {
	public static final double NIV = 4.0;
	private final Application application;

	public ActionAfficherConversion(Application application) {
		super(Ressource.ACTION_CONVERSION);
		this.application = application;
		putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		putValue(Action.MNEMONIC_KEY,KeyEvent.VK_C);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		PreferencesManager preferencesManager = PreferencesManager.getPreferencesManager(application.getLogin());
		Properties properties = preferencesManager.getCopyProperties();
		double niveau = Double.parseDouble(properties.getProperty(PreferencesManager.NIVEAU));
		if(niveau < 4*ActionAfficherConversion.NIV){
			niveau += ActionAfficherConversion.NIV/10.0;
			properties.setProperty(PreferencesManager.NIVEAU, String.valueOf(niveau));
			preferencesManager.save(properties);

			application.changeApp(properties);
		}
		application.setVisibleConversion(true);
	}
}
