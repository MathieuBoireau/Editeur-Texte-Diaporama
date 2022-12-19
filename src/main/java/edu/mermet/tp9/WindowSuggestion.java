package edu.mermet.tp9;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class WindowSuggestion extends JWindow {

	public WindowSuggestion(JFrame app, String login){
		super(app);
		if(!init(login))
			return;
		setSize(500, 150);
		setVisible(true);
		setLocationRelativeTo(app);
	}

	private boolean init(String login){
		PreferencesManager preferencesManager = PreferencesManager.getPreferencesManager(login);
		Properties properties = preferencesManager.getCopyProperties();
		ArrayList<String> listSuggestions = new ArrayList<String>();
		for (Object key : properties.keySet()) {
			if(properties.getProperty((String)key).equals("true")){
				double niveau = Double.parseDouble(properties.getProperty(PreferencesManager.NIVEAU));
				double niveauMin = Double.parseDouble(ResourceBundle.getBundle("suggestionsNiveaux").getString((String)key));
				if(niveau >= niveauMin)
					listSuggestions.add((String)key);
			}
		}

		if(listSuggestions.isEmpty()){
			dispose();
			return false;
		}

		int random = (int)(Math.random()*listSuggestions.size());
		String suggestion = ResourceBundle.getBundle("suggestions").getString(listSuggestions.get(random));
		JEditorPane editorPane = new JEditorPane();
		editorPane.setEditable(false);
		editorPane.setContentType("text/html");
		editorPane.setText("<html>"+suggestion+"</html>");
		add(editorPane, BorderLayout.CENTER);

		JPanel panelBtn = new JPanel();
		panelBtn.setLayout(new FlowLayout(FlowLayout.TRAILING));
		JButton btnFermer = new JButton("Fermer");
		btnFermer.addActionListener((ActionEvent ae)->{ dispose(); });
		JButton btnPlusAfficher = new JButton("Ne plus afficher");
		btnPlusAfficher.addActionListener((ActionEvent ae)->{
			properties.setProperty(listSuggestions.get(random), "false");
			preferencesManager.save(properties);
			dispose();
		});
		panelBtn.add(btnFermer);
		panelBtn.add(btnPlusAfficher);
		add(panelBtn, BorderLayout.SOUTH);
		
		return true;
	}
}