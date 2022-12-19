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
public class ActionAfficherDiaporama extends AbstractActionTraduisible {
	public static final double NIV = 2.0;
	private final Application application;

	public ActionAfficherDiaporama(Application application) {
		super(Ressource.ACTION_DIAPORAMA);
		this.application = application;
		putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));
		putValue(Action.MNEMONIC_KEY,KeyEvent.VK_D);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		PreferencesManager preferencesManager = PreferencesManager.getPreferencesManager(application.getLogin());
		Properties properties = preferencesManager.getCopyProperties();
		double niveau = Double.parseDouble(properties.getProperty(PreferencesManager.NIVEAU));
		if(niveau < 4*ActionAfficherDiaporama.NIV){
			niveau += ActionAfficherDiaporama.NIV/10.0;
			properties.setProperty(PreferencesManager.NIVEAU, String.valueOf(niveau));
			preferencesManager.save(properties);

			application.changeApp(properties);
		}
		application.setVisibleDiaporama(true);
	}
}
