package edu.mermet.tp9.fenetres;

import java.awt.FlowLayout;
import java.util.Properties;

import javax.swing.Action;
import javax.swing.JButton;

import edu.mermet.tp9.Application;
import edu.mermet.tp9.Ressource;
import edu.mermet.tp9.PreferencesManager;
import edu.mermet.tp9.DialogConfigMenus;
import edu.mermet.tp9.actions.ActionAfficherConversion;
import edu.mermet.tp9.actions.ActionAfficherTexte;
import edu.mermet.tp9.actions.ActionAfficherDiaporama;

/**
 *
 * @author brunomermet
 * @author Mathieu Boireau
 */
@SuppressWarnings("serial")
public class FenetreBoutons extends AbstractFenetreInterne {
	private JButton boutonTexte;
	private JButton boutonDiaporama;
	private JButton boutonDegres;
	
	public FenetreBoutons(Application appli, Action action, Properties properties) {
		super(appli, action, Ressource.FEN_BOUTONS_TITRE);
		setLayout(new FlowLayout());
		boutonDegres = new JButton(appli.getActionAfficherConversion());
		boutonTexte = new JButton(appli.getActionAfficherTexte());
		boutonDiaporama = new JButton(appli.getActionAfficherDiaporama());
		boutonDegres = new JButton(appli.getActionAfficherConversion());
		init(properties);
	}

	public void init(Properties properties){
		remove(boutonDegres);
		remove(boutonDiaporama);
		remove(boutonTexte);
		if(
			Integer.parseInt(properties.getProperty(PreferencesManager.CONVERSION)) == DialogConfigMenus.AUTO &&
			Double.parseDouble(properties.getProperty(PreferencesManager.NIVEAU)) >= ActionAfficherConversion.NIV ||
			Integer.parseInt(properties.getProperty(PreferencesManager.CONVERSION)) == DialogConfigMenus.AFFICHE
		){
			add(boutonDegres);
		}

		if(
			Integer.parseInt(properties.getProperty(PreferencesManager.SAISIE)) == DialogConfigMenus.AUTO &&
			Double.parseDouble(properties.getProperty(PreferencesManager.NIVEAU)) >= ActionAfficherTexte.NIV ||
			Integer.parseInt(properties.getProperty(PreferencesManager.SAISIE)) == DialogConfigMenus.AFFICHE
		){
			add(boutonTexte);
		}

		if(
			Integer.parseInt(properties.getProperty(PreferencesManager.DIAPO)) == DialogConfigMenus.AUTO &&
			Double.parseDouble(properties.getProperty(PreferencesManager.NIVEAU)) >= ActionAfficherDiaporama.NIV ||
			Integer.parseInt(properties.getProperty(PreferencesManager.DIAPO)) == DialogConfigMenus.AFFICHE
		){
			add(boutonDiaporama);
		}

		pack();
	}
}
