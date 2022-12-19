package edu.mermet.tp9;

import javax.swing.JFrame;
import javax.swing.JDialog;

import java.util.List;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class DialogHowTo extends JDialog {
	private JEditorPane editorPane;
	
	public DialogHowTo(JFrame app){
		super(app, RessourceManager.getRessourceManager().getString(Ressource.ACTION_HOWTO), false);

		init();
		setSize(800, 400);
		setVisible(true);
		setLocationRelativeTo(app);
	}

	private void init(){
		JList list = new JList(new String[1]);
		editorPane = new JEditorPane();
		try{
			ResourceBundle resourceBundleTitres = ResourceBundle.getBundle("HowTo/titres");
			// Liste à gauche
			DefaultListModel<String> l1 = new DefaultListModel<>();
			List<String> keys = resourceBundleTitres.keySet().stream().collect(Collectors.toList());
			Collections.sort(keys);
			for (String key : keys) {
				l1.addElement(resourceBundleTitres.getString(key));
			}
			list = new JList(l1);
			list.addListSelectionListener(new EcouteurChangeAide(this,list));

			// Contenu à droite
			editorPane.setEditable(false);
			ResourceBundle resourceBundleTextes = ResourceBundle.getBundle("HowTo/textes");
			editorPane.setContentType("text/html");
			editorPane.setText("<html>"+resourceBundleTextes.getString("defaut")+"</html>");
		}catch(Exception e){e.printStackTrace();}

		JSplitPane panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, list, editorPane);

		add(panel);
	}

	private void changeTexte(String key){
		ResourceBundle resourceBundleTextes = ResourceBundle.getBundle("HowTo/textes");
		editorPane.setContentType("text/html");
		editorPane.setText("<html>"+resourceBundleTextes.getString(key)+"</html>");
	}

	private class EcouteurChangeAide implements ListSelectionListener {
		private DialogHowTo dialog;
		private JList liste;

		public EcouteurChangeAide(DialogHowTo dialog, JList liste){
			this.dialog = dialog;
			this.liste = liste;
		}

		public void valueChanged(ListSelectionEvent e){
			ResourceBundle resourceBundleTitres = ResourceBundle.getBundle("HowTo/titres");
			for (String key : resourceBundleTitres.keySet()) {
				if(liste.getSelectedValue().equals(resourceBundleTitres.getString(key))){
					dialog.changeTexte(key);
					break;
				}
			}
		}
	}
}