package businessLogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import businessLogic.BLFactory.BL_IMPLEMENTATIONS;
import configuration.ConfigXML;
import domain.Event;
import domain.ExtendedIterator;
import exceptions.EventFinished;

public class IteratorMainProgram {

	public static void main(String[] args) {
		BLFacade blFacade = new BLFactory(ConfigXML.getInstance()).createBLFacade(BL_IMPLEMENTATIONS.LOCAL);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
		
		Date date;
		try {
			date = sdf.parse("17/12/2022"); 
			blFacade.gertaerakSortu("aDescription1-aDescription1", sdf.parse("17/12/2022"), "Futbol");
			blFacade.gertaerakSortu("aDescription2-aDescription2", sdf.parse("17/12/2022"), "Futbol");
			blFacade.gertaerakSortu("aDescription3-aDescription3", sdf.parse("17/12/2022"), "Futbol");
			blFacade.gertaerakSortu("aDescription4-aDescription4", sdf.parse("17/12/2022"), "Futbol");
			
			ExtendedIterator<Event> eventIterator = blFacade.getEventsIterator(date); 
			
			System.out.println("HACIA ATRAS:"); 
			eventIterator.goLast(); 
			Event actual;
			while (eventIterator.hasPrevious()) {
				actual = eventIterator.previous();
				System.out.println(actual.toString()); 
			}
			
			System.out.println("HACIA ADELANTE"); 
			eventIterator.goFirst(); 
			while (eventIterator.hasNext()) {
				actual = eventIterator.next();
				System.out.println(actual.toString()); 
			}
		} catch (ParseException e1) {
			System.out.println("Problem has occured: " + e1.getMessage());
		} catch (EventFinished e2) {
			System.out.println("Problem has occured: " + e2.getMessage());
		}
	}
}
