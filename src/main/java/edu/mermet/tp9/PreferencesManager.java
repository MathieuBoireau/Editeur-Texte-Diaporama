package edu.mermet.tp9;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.ResourceBundle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class PreferencesManager {
	public static final String CONVERSION = "Conversion Celsius/Farenheit";
	public static final String SAISIE = "Saisie";
	public static final String DIAPO = "Diaporama";
	public static final String BOUTONS = "Boutons";
	public static final String NIVEAU = "Niveau";
	private static PreferencesManager prefManager;
	
	private String login;
	private Properties properties;

	public static PreferencesManager getPreferencesManager(String login){
		if(prefManager == null || !prefManager.login.equals(login)){
			if(login == null)
				login = System.getProperty("user.name");
			prefManager = new PreferencesManager(login);
		}
		return prefManager;
	}

	private PreferencesManager(String login){
		this.login = login;
		this.properties = new Properties();
		fillProperties();
	}

	private void fillProperties(){
		if(!Files.isDirectory(Paths.get(System.getProperty("user.home"), ".ihm"))){
			try {
				Files.createDirectory(Paths.get(System.getProperty("user.home"), ".ihm"));
			} catch (Exception e) {e.printStackTrace();}
		}

		File file = new File(System.getProperty("user.home")+"/.ihm/"+login+".xml");
		try {
			if(file.exists()){
				properties.loadFromXML(new FileInputStream(file));
			} else {
				properties.setProperty(CONVERSION, String.valueOf(DialogConfigMenus.AUTO));
				properties.setProperty(SAISIE, String.valueOf(DialogConfigMenus.AUTO));
				properties.setProperty(DIAPO, String.valueOf(DialogConfigMenus.AUTO));
				properties.setProperty(BOUTONS, String.valueOf(DialogConfigMenus.AUTO));
				properties.setProperty(NIVEAU, "1.0");

				ResourceBundle resourceSuggestions = ResourceBundle.getBundle("suggestions");
				for (String suggestion : resourceSuggestions.keySet()) {
					properties.setProperty(suggestion, "true");
				}

				file.createNewFile();
				properties.storeToXML(new FileOutputStream(file), "");
			}
		} catch (Exception e) {e.printStackTrace();}
	}

	public Properties getCopyProperties(){
		return copyProperties(properties);
	}

	public void save(Properties newProperties){
		File file = new File(System.getProperty("user.home")+"/.ihm/" + login + ".xml");
		try {
			newProperties.storeToXML(new FileOutputStream(file), "");
			properties = copyProperties(newProperties);
		} catch (Exception e) { e.printStackTrace(); }
	}

	private Properties copyProperties(Properties properties1){
		Properties propertiesCopy = new Properties();
		for (String key : properties1.stringPropertyNames()) {
			propertiesCopy.setProperty(key, properties1.getProperty(key));
		}
		return propertiesCopy;
	}
}