package edu.mermet.tp9;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;
import java.net.URL;
import java.io.File;
import java.io.IOException;

import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JSplitPane;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import edu.mermet.tp9.actions.ActionAfficherBoutons;
import edu.mermet.tp9.actions.ActionAfficherConversion;
import edu.mermet.tp9.actions.ActionAfficherDiaporama;
import edu.mermet.tp9.actions.ActionAfficherTexte;
import edu.mermet.tp9.actions.ActionLangue;
import edu.mermet.tp9.actions.ActionQuitter;
import edu.mermet.tp9.actions.ActionHowTo;
import edu.mermet.tp9.actions.ActionConfigMenus;
import edu.mermet.tp9.fenetres.AbstractFenetreInterne;
import edu.mermet.tp9.fenetres.FenetreBoutons;
import edu.mermet.tp9.fenetres.FenetreConversion;
import edu.mermet.tp9.fenetres.FenetreDiaporama;
import edu.mermet.tp9.fenetres.FenetreTexte;

@SuppressWarnings("serial")
public class Application extends JFrame implements Traduisible {
	AbstractFenetreInterne conversion;
	AbstractFenetreInterne texte;
	AbstractFenetreInterne diaporama;
	AbstractFenetreInterne boutons;
	private Action actionQuitter;
	private Action actionAfficherConversion;
	private Action actionAfficherTexte;
	private Action actionAfficherDiaporama;
	private Action actionAfficherBoutons;
	private Action actionHowTo;
	private Action actionConfigMenus;
	private ActionLangue actionLangueParDefaut;
	private ActionLangue actionLangueFrancais;
	private ActionLangue actionLangueAnglais;
	private JMenu menuFichier;
	private JMenu menuApplications;
	private JMenu menuLangues;
	private JMenu menuAide;
	RessourceManager ressourceManager;
	private String login;
	
	public Application(String login) {
		this.login = login;
		ressourceManager = RessourceManager.getRessourceManager();
		ressourceManager.addTraduisible(this);
		Properties properties = PreferencesManager.getPreferencesManager(login).getCopyProperties();
		this.setContentPane(new JDesktopPane());
		setTitle(ressourceManager.getString(Ressource.TITRE));
		// ****** Barre de menu ******
		JMenuBar barre = new JMenuBar();
		// ------ menu Fichier ------
		menuFichier = new JMenu(ressourceManager.getString(Ressource.MENU_FICHIER));
		menuFichier.setMnemonic(KeyEvent.VK_F);
		actionQuitter = new ActionQuitter(this);
		JMenuItem quitter = new JMenuItem(actionQuitter);
		menuFichier.add(quitter);
		barre.add(menuFichier);
		this.setJMenuBar(barre);
		// ------ menu Applications ------
		menuApplications = new JMenu(ressourceManager.getString(Ressource.MENU_APPLICATIONS));
		menuApplications.setMnemonic(KeyEvent.VK_A);
		actionAfficherConversion = new ActionAfficherConversion(this);
		actionAfficherTexte = new ActionAfficherTexte(this);
		actionAfficherDiaporama = new ActionAfficherDiaporama(this);
		actionAfficherBoutons = new ActionAfficherBoutons(this);
		barre.add(menuApplications);
		// ------ menu Langues ------
		menuLangues = new JMenu(ressourceManager.getString(Ressource.MENU_LANGUES));
		menuLangues.setMnemonic(KeyEvent.VK_L);
		actionLangueParDefaut = new ActionLangue(this, Ressource.LANGUE_DEFAUT, Locale.getDefault());
		actionLangueFrancais = new ActionLangue(this, Ressource.LANGUE_FRANCAIS, new Locale("fr", "FR"));
		actionLangueAnglais = new ActionLangue(this, Ressource.LANGUE_ANGLAIS, new Locale("en", "GB"));
		menuLangues.add(new JMenuItem(actionLangueParDefaut));
		menuLangues.add(new JMenuItem(actionLangueFrancais));
		menuLangues.add(new JMenuItem(actionLangueAnglais));
		barre.add(menuLangues);

		// ------ menu Aide ------
		menuAide = new JMenu(ressourceManager.getString(Ressource.MENU_AIDE));
		menuAide.setMnemonic(KeyEvent.VK_H);
		actionHowTo = new ActionHowTo(this);
		menuAide.add(new JMenuItem(actionHowTo));
		actionConfigMenus = new ActionConfigMenus(this);
		menuAide.add(new JMenuItem(actionConfigMenus));
		barre.add(menuAide);
		// ****** Fin barre de menu ******
		
		// ****** Création des fenêtres ******
		// ------ fenêtre conversion ------
		conversion = new FenetreConversion(this, actionAfficherConversion);
		this.add(conversion);
		// ------ fenêtre texte ------
		texte = new FenetreTexte(this, actionAfficherTexte);
		this.add(texte);
		// ------ fenêtre diaporama ------
		diaporama = new FenetreDiaporama(this, actionAfficherDiaporama);
		this.add(diaporama);
		// ------ fenêtre boutons ------
		boutons = new FenetreBoutons(this,actionAfficherBoutons, properties);
		this.add(boutons);
		new WindowSuggestion(this, login);

		// ****** Affichage des fenêtres et activation des actions ******
		initApplications(properties);
		setAppsVisible(properties, true);

		// ****** Fin affichage fenêtres ******
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600,300);
		this.setLocationRelativeTo(null);
		setVisible(true);
		System.out.println("Niveau de l'utilisateur : "+properties.getProperty(PreferencesManager.NIVEAU));
	}

	private void initApplications(Properties properties){
		if(
			Integer.parseInt(properties.getProperty(PreferencesManager.CONVERSION)) == DialogConfigMenus.AUTO &&
			Double.parseDouble(properties.getProperty(PreferencesManager.NIVEAU)) >= ActionAfficherConversion.NIV ||
			Integer.parseInt(properties.getProperty(PreferencesManager.CONVERSION)) == DialogConfigMenus.AFFICHE
		){
			JMenuItem itemConversion = new JMenuItem(actionAfficherConversion);
			menuApplications.add(itemConversion);
		}

		if(
			Integer.parseInt(properties.getProperty(PreferencesManager.SAISIE)) == DialogConfigMenus.AUTO &&
			Double.parseDouble(properties.getProperty(PreferencesManager.NIVEAU)) >= ActionAfficherTexte.NIV ||
			Integer.parseInt(properties.getProperty(PreferencesManager.SAISIE)) == DialogConfigMenus.AFFICHE
		){
			JMenuItem itemTexte = new JMenuItem(actionAfficherTexte);
			menuApplications.add(itemTexte);
		}

		if(
			Integer.parseInt(properties.getProperty(PreferencesManager.DIAPO)) == DialogConfigMenus.AUTO &&
			Double.parseDouble(properties.getProperty(PreferencesManager.NIVEAU)) >= ActionAfficherDiaporama.NIV ||
			Integer.parseInt(properties.getProperty(PreferencesManager.DIAPO)) == DialogConfigMenus.AFFICHE
		){
			JMenuItem itemDiaporama = new JMenuItem(actionAfficherDiaporama);
			menuApplications.add(itemDiaporama);
		}

		if(Integer.parseInt(properties.getProperty(PreferencesManager.BOUTONS)) == DialogConfigMenus.AUTO &&
		   Double.parseDouble(properties.getProperty(PreferencesManager.NIVEAU)) >= ActionAfficherBoutons.NIV ||
		   Integer.parseInt(properties.getProperty(PreferencesManager.BOUTONS)) == DialogConfigMenus.AFFICHE
		){
			JMenuItem itemBoutons = new JMenuItem(actionAfficherBoutons);
			menuApplications.add(itemBoutons);
		}
	}

	public void setAppsVisible(Properties properties, boolean afficher){
		boolean conv = Integer.parseInt(properties.getProperty(PreferencesManager.CONVERSION)) == DialogConfigMenus.AUTO &&
			           Double.parseDouble(properties.getProperty(PreferencesManager.NIVEAU)) >= ActionAfficherConversion.NIV ||
					   Integer.parseInt(properties.getProperty(PreferencesManager.CONVERSION)) == DialogConfigMenus.AFFICHE;
		setVisibleConversion(conv);
		if(conv){
			actionAfficherConversion.setEnabled(false);
		}

		boolean saisie = Integer.parseInt(properties.getProperty(PreferencesManager.SAISIE)) == DialogConfigMenus.AUTO &&
			             Double.parseDouble(properties.getProperty(PreferencesManager.NIVEAU)) >= ActionAfficherTexte.NIV ||
					     Integer.parseInt(properties.getProperty(PreferencesManager.SAISIE)) == DialogConfigMenus.AFFICHE;
		setVisibleTexte(saisie);
		if(saisie){
			actionAfficherTexte.setEnabled(false);
		}

		boolean diapo = Integer.parseInt(properties.getProperty(PreferencesManager.DIAPO)) == DialogConfigMenus.AUTO &&
			            Double.parseDouble(properties.getProperty(PreferencesManager.NIVEAU)) >= ActionAfficherDiaporama.NIV ||
					    Integer.parseInt(properties.getProperty(PreferencesManager.DIAPO)) == DialogConfigMenus.AFFICHE;
		setVisibleDiaporama(diapo);
		if(diapo){
			actionAfficherDiaporama.setEnabled(false);
		}

		boolean btns = Integer.parseInt(properties.getProperty(PreferencesManager.BOUTONS)) == DialogConfigMenus.AUTO &&
			           Double.parseDouble(properties.getProperty(PreferencesManager.NIVEAU)) >= ActionAfficherBoutons.NIV ||
					   Integer.parseInt(properties.getProperty(PreferencesManager.BOUTONS)) == DialogConfigMenus.AFFICHE;
		setVisibleBoutons(btns);
		if(btns){
			actionAfficherBoutons.setEnabled(false);
		}
	}

	public String getLogin(){
		return login;
	}

	public void changeApp(Properties properties){
		menuApplications.removeAll();
		initApplications(properties);
		((FenetreBoutons)boutons).init(properties);
	}

	public Action getActionAfficherConversion() {
		return actionAfficherConversion;
	}

	public Action getActionAfficherTexte() {
		return actionAfficherTexte;
	}

	public Action getActionAfficherDiaporama() {
		return actionAfficherDiaporama;
	}
	
	public static void main(String[] args) {
		if(args.length == 1 && !args[0].contains("/"))
			SwingUtilities.invokeLater(()->new Application(args[0]));
		else
			SwingUtilities.invokeLater(()->new Application(null));
	}

	public void setVisibleBoutons(boolean b) {
		boutons.setVisible(b);
	}
	public void setVisibleConversion(boolean b) {
		conversion.setVisible(b);
	}
	public void setVisibleDiaporama(boolean b) {
		diaporama.setVisible(b);
	}
	public void setVisibleTexte(boolean b) {
		texte.setVisible(b);
	}
	public void afficherHowTo(){
		new DialogHowTo(this);
	}
	public void afficherConfigMenus(){
		new DialogConfigMenus(this, login);
	}

	@Override
	public void traduire() {
		RessourceManager ressourceManager = RessourceManager.getRessourceManager();
		setTitle(ressourceManager.getString(Ressource.TITRE));
		menuFichier.setText(ressourceManager.getString(Ressource.MENU_FICHIER));
		menuApplications.setText(ressourceManager.getString(Ressource.MENU_APPLICATIONS));
		menuLangues.setText(ressourceManager.getString(Ressource.MENU_LANGUES));
		menuAide.setText(ressourceManager.getString(Ressource.MENU_AIDE));
	}
}
