package gui;

import java.awt.Color;
import java.net.URL;
import java.util.Locale;

import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import businessLogic.BLFactory;
import businessLogic.BLFactory.BL_IMPLEMENTATIONS;
import configuration.ConfigXML;
import dataAccess.DataAccess;

public class ApplicationLauncher {

	public static void main(String[] args) {

		ConfigXML c = ConfigXML.getInstance();
		BLFactory blFactory = new BLFactory(c);
		System.out.println(c.getLocale());

		Locale.setDefault(new Locale(c.getLocale()));

		System.out.println("Locale: " + Locale.getDefault());

		MainGUI a = new MainGUI();
		a.setVisible(false);

		MainUserGUI b = new MainUserGUI();
		b.setVisible(true);

		try {
			BLFacade appFacadeInterface;
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			if (c.isBusinessLogicLocal()) {
				appFacadeInterface = blFactory.createBLFacade(BL_IMPLEMENTATIONS.LOCAL);
			} else { // If remote
				appFacadeInterface = blFactory.createBLFacade(BL_IMPLEMENTATIONS.REMOTE);
			}

			MainGUI.setBussinessLogic(appFacadeInterface);

		} catch (Exception e) {
			a.jLabelSelectOption.setText("Error: " + e.toString());
			a.jLabelSelectOption.setForeground(Color.RED);

			System.out.println("Error in ApplicationLauncher: " + e.toString());
		}

	}

}
