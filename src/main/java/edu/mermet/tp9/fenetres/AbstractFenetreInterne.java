package edu.mermet.tp9.fenetres;

import javax.swing.Action;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import edu.mermet.tp9.Application;
import edu.mermet.tp9.Ressource;
import edu.mermet.tp9.RessourceManager;
import edu.mermet.tp9.Traduisible;

/**
 *
 * @author brunomermet
 */
@SuppressWarnings("serial")
public abstract class AbstractFenetreInterne extends JInternalFrame implements Traduisible {
	private Action action;
	private Ressource nom;
	private RessourceManager ressourceManager;
	public AbstractFenetreInterne(Application appli, Action monAction, Ressource nom) {
		super(RessourceManager.getRessourceManager().getString(nom), true,true,true,true);
		this.nom = nom;
		ressourceManager = RessourceManager.getRessourceManager();
		ressourceManager.addTraduisible(this);
		action = monAction;
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.addInternalFrameListener(new EcouteurFenetre());
	}

	@Override
	public void traduire() {
		setTitle(ressourceManager.getString(nom));
	}
	
	private class EcouteurFenetre extends InternalFrameAdapter {
		@Override
		public void internalFrameClosing(InternalFrameEvent ife) {
			action.setEnabled(true);
		}
		@Override
		public void internalFrameActivated(InternalFrameEvent ife) {
			action.setEnabled(false);
		}
	}
}
