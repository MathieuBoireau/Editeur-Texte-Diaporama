package edu.mermet.tp9;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Properties;
import java.util.ResourceBundle;

import edu.mermet.tp9.PreferencesManager;
import edu.mermet.tp9.actions.ActionAfficherConversion;
import edu.mermet.tp9.actions.ActionAfficherDiaporama;
import edu.mermet.tp9.actions.ActionAfficherTexte;

public class DialogConfigMenus extends JDialog {
	public static final int AUTO = 0;
	public static final int AFFICHE = 1;
	public static final int CACHE = 2;

	private Properties properties;
	private JRadioButton[] btnsConversion;
	private JRadioButton[] btnsSaisie;
	private JRadioButton[] btnsDiaporama;
	private JRadioButton[] btnsBoutons;
	private String login;
	private Application app;
	
	public DialogConfigMenus(Application app, String login){
		super(app, RessourceManager.getRessourceManager().getString(Ressource.ACTION_CONFIG), true);
		this.app = app;
		this.login = login;

		init();
		pack();
		setLocationRelativeTo(app);
		setVisible(true);
	}

	private void init(){
		PreferencesManager preferencesManager = PreferencesManager.getPreferencesManager(login);
		properties = preferencesManager.getCopyProperties();

		JPanel panelConfig = new JPanel();
		panelConfig.setLayout(new BoxLayout(panelConfig, BoxLayout.Y_AXIS));

		panelConfig.add(initPanel(PreferencesManager.CONVERSION));
		panelConfig.add(initPanel(PreferencesManager.SAISIE));
		panelConfig.add(initPanel(PreferencesManager.DIAPO));
		panelConfig.add(initPanel(PreferencesManager.BOUTONS));

		JPanel panelValid = new JPanel();
		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener((ActionEvent)->{
			saveConfigMenus();
		});
		panelValid.add(btnValider);
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener((ActionEvent)->{
			properties = preferencesManager.getCopyProperties();
			dispose();
		});
		panelValid.add(btnAnnuler);

		add(panelConfig, BorderLayout.CENTER);
		add(panelValid, BorderLayout.SOUTH);
	}

	private JPanel initPanel(String key){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		panel.add(new JLabel(key));
		JRadioButton[] btns = new JRadioButton[3];
		btns[0] = new JRadioButton("Auto");
		btns[1] = new JRadioButton("Affiché");
		btns[2] = new JRadioButton("Caché");
		ButtonGroup btnGroup = new ButtonGroup();
		for (int i = 0; i < btns.length; i++) {
			btnGroup.add(btns[i]);
			panel.add(btns[i]);
			
			btns[Integer.parseInt(properties.getProperty(key))].setSelected(true);
		}

		switch (key) {
			case PreferencesManager.CONVERSION:
				btnsConversion = btns;
				break;
			case PreferencesManager.SAISIE:
				btnsSaisie = btns;
				break;
			case PreferencesManager.DIAPO:
				btnsDiaporama = btns;
				break;
			case PreferencesManager.BOUTONS:
				btnsBoutons = btns;
				break;
		}

		return panel;
	}

	private void saveConfigMenus(){
		for (int i = 0; i < btnsConversion.length; i++) {
			if(btnsConversion[i].isSelected()){
				properties.setProperty(PreferencesManager.CONVERSION, String.valueOf(i));
				break;
			}
		}

		for (int i = 0; i < btnsSaisie.length; i++) {
			if(btnsSaisie[i].isSelected()){
				properties.setProperty(PreferencesManager.SAISIE, String.valueOf(i));
				break;
			}
		}

		for (int i = 0; i < btnsDiaporama.length; i++) {
			if(btnsDiaporama[i].isSelected()){
				properties.setProperty(PreferencesManager.DIAPO, String.valueOf(i));
				break;
			}
		}

		for (int i = 0; i < btnsBoutons.length; i++) {
			if(btnsBoutons[i].isSelected()){
				properties.setProperty(PreferencesManager.BOUTONS, String.valueOf(i));
				break;
			}
		}

		PreferencesManager preferencesManager = PreferencesManager.getPreferencesManager(login);
		preferencesManager.save(properties);
		app.changeApp(properties);
		app.setAppsVisible(properties, true);
		dispose();
	}
}