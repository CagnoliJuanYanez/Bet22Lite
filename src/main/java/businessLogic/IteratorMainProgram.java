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
			date = sdf.parse("18/12/2022"); 
			blFacade.gertaerakSortu("aDescription1-aDescription1", date, "Futbol");
			blFacade.gertaerakSortu("aDescription2-aDescription2", date, "Futbol");
			blFacade.gertaerakSortu("aDescription3-aDescription3", date, "Futbol");
			blFacade.gertaerakSortu("aDescription4-aDescription4", date, "Futbol");
			
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
