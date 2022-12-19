package edu.mermet.tp9.fenetres;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.ImageIcon;

import edu.mermet.tp9.Application;
import edu.mermet.tp9.Ressource;
import edu.mermet.tp9.RessourceManager;
import edu.mermet.tp9.actions.AbstractActionTraduisible;

/**
 *
 * @author brunomermet
 */
@SuppressWarnings("serial")
public class FenetreConversion extends AbstractFenetreInterne {
	private JTextField champCelsius;
	private JTextField champFarenheit;
	private JButton boutonConvertir;
	private Action actionConvertir;
	private boolean celsiusAFocus;
	public FenetreConversion(Application appli, Action action) {
		super(appli, action, Ressource.FEN_CONV_TITRE);
		this.setSize(new Dimension(100,50));
		this.setLayout(new GridLayout(3,1));
		JPopupMenu popupMenu = new JPopupMenu("Aide");

		JPanel ligneCelsius = new JPanel();
		ligneCelsius.setLayout(new FlowLayout(FlowLayout.TRAILING));
		JLabel labCelsius = new JLabel("Celsius :");
		champCelsius = new JTextField(15);
		JLabel lblCelsiusHelp = new JLabel(new ImageIcon("src/main/resources/images/help.png"));
		JMenuItem menuAideCelsius = new JMenuItem("Celsius");
		labCelsius.setLabelFor(champCelsius);
		String text = RessourceManager.getRessourceManager().getString(Ressource.FEN_CONV_AIDE_CELSIUS);
		labCelsius.setToolTipText(text);
		champCelsius.setToolTipText(text);
		lblCelsiusHelp.setToolTipText(text);
		lblCelsiusHelp.addMouseListener(new EcouteurClic(this, text));
		String textCelsius = new String(text);
		menuAideCelsius.addActionListener((ActionEvent e)->{
			showMessage(textCelsius);
		});
		ligneCelsius.add(labCelsius);
		ligneCelsius.add(champCelsius);
		ligneCelsius.add(lblCelsiusHelp);
		this.add(ligneCelsius);
		popupMenu.add(menuAideCelsius);
		celsiusAFocus = true;
		champCelsius.addFocusListener(new EcouteurFocus(true));

		JPanel ligneFarenheit = new JPanel();
		ligneFarenheit.setLayout(new FlowLayout(FlowLayout.TRAILING));
		JLabel labFarenheit = new JLabel("Farenheit :");
		champFarenheit = new JTextField(15);
		JLabel lblFarenheitHelp = new JLabel(new ImageIcon("src/main/resources/images/help.png"));
		JMenuItem menuAideFarenheit = new JMenuItem("Farenheit");
		labFarenheit.setLabelFor(champFarenheit);
		text = RessourceManager.getRessourceManager().getString(Ressource.FEN_CONV_AIDE_FARENHEIT);
		labFarenheit.setToolTipText(text);
		champFarenheit.setToolTipText(text);
		lblFarenheitHelp.setToolTipText(text);
		lblFarenheitHelp.addMouseListener(new EcouteurClic(this, text));
		String textFahrenheit = new String(text);
		menuAideFarenheit.addActionListener((ActionEvent e)->{
			showMessage(textFahrenheit);
		});
		ligneFarenheit.add(labFarenheit);
		ligneFarenheit.add(champFarenheit);
		ligneFarenheit.add(lblFarenheitHelp);
		this.add(ligneFarenheit);
		champFarenheit.addFocusListener(new EcouteurFocus(false));

		JPanel ligneValider = new JPanel();
		ligneValider.setLayout(new FlowLayout(FlowLayout.CENTER));
		actionConvertir = new ActionConvertir();
		boutonConvertir = new JButton(actionConvertir);
		ligneValider.add(boutonConvertir);
		this.add(ligneValider);
		popupMenu.add(menuAideFarenheit);

		setComponentPopupMenu(popupMenu);
		pack();
		getRootPane().setDefaultButton(boutonConvertir);
	}

	private void showMessage(String text){
		JOptionPane.showMessageDialog(this, text);
	}

	private class EcouteurFocus implements FocusListener {
		private boolean aStocker;

		public EcouteurFocus(boolean b) {
			aStocker = b;
		}

		@Override
		public void focusGained(FocusEvent fe) {
			celsiusAFocus = aStocker;
		}

		@Override
		public void focusLost(FocusEvent fe) {
			return;
		}
	}

	private class ActionConvertir extends AbstractActionTraduisible {

		public ActionConvertir() {
			super(Ressource.FEN_CONV_CONVERTIR);
		}

		@Override
		public void actionPerformed(ActionEvent ae) {
			double tempCelsius = 0;
			double tempFarenheit = 0;
			if (celsiusAFocus) {
				try {
					tempCelsius = Double.parseDouble(champCelsius.getText());
				tempFarenheit = 9./5*tempCelsius+32;
				champFarenheit.setText(""+tempFarenheit);
				}
				catch (NumberFormatException nfe) {
					champFarenheit.setText(RessourceManager.getRessourceManager().getString(Ressource.FEN_CONV_ERR_FORMAT));
				}
				}
			else {
				try {
					tempFarenheit = Double.parseDouble(champFarenheit.getText());
					tempCelsius = (tempFarenheit - 32) *5./9;
					champCelsius.setText(""+tempCelsius);
				}
				catch (NumberFormatException nfe) {
					champCelsius.setText(RessourceManager.getRessourceManager().getString(Ressource.FEN_CONV_ERR_FORMAT));
				}
				
			}
		}
	}

	private class EcouteurClic extends MouseAdapter {
		private FenetreConversion fen;
		private String text;
		
		public EcouteurClic(FenetreConversion fen, String text){
			super();
			this.fen = fen;
			this.text = text;
		}

		@Override
		public void mouseClicked(MouseEvent e){
			fen.showMessage(text);
		}
	}

}
