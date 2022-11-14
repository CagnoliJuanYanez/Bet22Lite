package businessLogic;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import configuration.ConfigXML;
import dataAccess.DataAccess;

public class BLFactory {
	public enum BL_IMPLEMENTATIONS {
		LOCAL, REMOTE
	}

	private ConfigXML c;
	
	public BLFactory (ConfigXML config) {
		this.c = config;
	}
	
	public BLFacade createBLFacade(BL_IMPLEMENTATIONS imp) {
		if (imp == null)
			return null;

		switch (imp) {
		case LOCAL:
			return createLocalBL();
		case REMOTE:
			return createRemoteBL();
		default:
			throw new IllegalArgumentException("Unknown option " + imp);
		}
	}

	private BLFacade createLocalBL() {
		DataAccess da = new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
		return new BLFacadeImplementation(da);
	}

	private BLFacade createRemoteBL() {
		String serviceName = "http://" + c.getBusinessLogicNode() + ":" + c.getBusinessLogicPort() + "/ws/"
				+ c.getBusinessLogicName() + "?wsdl";
		URL url;
		try {
			url = new URL(serviceName);
		} catch (MalformedURLException e) {
			return null;
		}
		QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");
		Service service = Service.create(url, qname);
		return service.getPort(BLFacade.class);
	}
}
