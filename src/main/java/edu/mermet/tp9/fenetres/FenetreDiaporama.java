package edu.mermet.tp9.fenetres;

import java.awt.BorderLayout;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.mermet.tp9.Application;
import edu.mermet.tp9.Ressource;

/**
 *
 * @author brunomermet
 */
@SuppressWarnings("serial")
public class FenetreDiaporama extends AbstractFenetreInterne {
	ImageIcon [] images;
	String[] textes;
	JLabel affichage;
	Defilement defilement;
	private int indiceCourant = 0;
	public FenetreDiaporama(Application appli, Action action) {
		super(appli, action, Ressource.FEN_DIAPO_TITRE);
		images = new ImageIcon[3];
		try {
			images[0] = new ImageIcon(new ImageIcon(new URL("http://bruno.mermet.pagesperso-orange.fr/Personnel/Anes/Randos/TourDeLaHague/10bocage.jpg")).getImage().getScaledInstance(300, -1, Image.SCALE_DEFAULT));
			images[1] = new ImageIcon(new ImageIcon(new URL("http://bruno.mermet.pagesperso-orange.fr/Personnel/Anes/Randos/TourDeLaHague/12baieEcalgrain.jpg")).getImage().getScaledInstance(300, -1, Image.SCALE_DEFAULT));
			images[2] = new ImageIcon(new ImageIcon(new URL("http://bruno.mermet.pagesperso-orange.fr/Personnel/Anes/Randos/TourDeLaHague/15cote.jpg")).getImage().getScaledInstance(300, -1, Image.SCALE_DEFAULT));
		} catch (MalformedURLException ex) {
			images[0] = null;
			images[1] = null;
			images[2] = null;
		}
		
		JPanel panneauTexte = new JPanel();
		affichage = new JLabel();
		panneauTexte.add(affichage);
		affichage.setIcon(images[0]);
		JScrollPane ascenseurs = new JScrollPane(affichage);
		add(ascenseurs,BorderLayout.CENTER);
		setSize(300,300);
		
	}
	class Defilement implements Runnable {
		private boolean arrete;
		public Defilement() {
			arrete = false;
		}
		@Override
		public void run() {
			while (!arrete) {
				try {
					Thread.sleep(2000);
				}
				catch (InterruptedException iex) {
				}
				indiceCourant++;
				indiceCourant = indiceCourant % 3;
				affichage.setIcon(images[indiceCourant]);
			}
		}
		public void arreter() {
			arrete = true;
		}
	}
	
	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		if (b) {
			defilement = new Defilement();
			Thread thread = new Thread(defilement);
			thread.start();
		}
		else {
			if (defilement != null) {
				defilement.arreter();
			}
		}
	}

}
