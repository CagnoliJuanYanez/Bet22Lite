import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import exceptions.EventFinished;

public class GertaerakSortuMockInt {
	DataAccess dataAccess;
	BLFacade sut;
	String eventDescription;
	Date eventDate;
	SimpleDateFormat sdf;
	String eventSport;
	
	@Before
	public void initialize() {
		dataAccess = Mockito.mock(DataAccess.class);
		sut = new BLFacadeImplementation(dataAccess);
		eventDescription = "Real Madrid-Barcelona";
		
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			eventDate = sdf.parse("28/10/2022");	
		} catch (ParseException pe) {
			fail();
		}
		
		eventSport = "Futbol";
		
	}
	
	
	@Test
	public void testCase1() throws EventFinished {
		Mockito.when(dataAccess.gertaerakSortu(eventDescription, eventDate, eventSport)).thenReturn(true);
		
		boolean eventAdded = sut.gertaerakSortu(eventDescription, eventDate, eventSport);
		
		assertTrue(eventAdded);
		verify(dataAccess).gertaerakSortu(eventDescription, eventDate, eventSport);
	}
	
	//wrong description format
	@Test(expected = Exception.class)
	public void testCase2() throws EventFinished {
		Mockito.when(dataAccess.gertaerakSortu("Real Madrid-", eventDate, eventSport)).thenThrow(Exception.class);
		
		sut.gertaerakSortu("Real Madrid-", eventDate, eventSport);
		
		verify(dataAccess).gertaerakSortu("Real Madrid-", eventDate, eventSport);
	}
	
	//non existent sport
	@Test
	public void testCase3() throws EventFinished {
		Mockito.when(dataAccess.gertaerakSortu(eventDescription, eventDate, "badmington")).thenReturn(false);
		
		boolean addedEvent = sut.gertaerakSortu(eventDescription, eventDate, "badmington");
		
		assertFalse(addedEvent);
		verify(dataAccess).gertaerakSortu(eventDescription, eventDate, "badmington");
	}
	
	//null parameter description
	@Test(expected = Exception.class)
	public void testCase4() throws EventFinished {
		Mockito.when(dataAccess.gertaerakSortu(null, eventDate, eventSport)).thenThrow(Exception.class);
		
		sut.gertaerakSortu(null, eventDate, eventSport);
		
		verify(dataAccess).gertaerakSortu(null, eventDate, eventSport);
	}
	
	//null parameter date
	@Test(expected = Exception.class)
	public void testCase5() throws EventFinished {
		Mockito.when(dataAccess.gertaerakSortu(eventDescription, null, eventSport)).thenThrow(Exception.class);
			
		sut.gertaerakSortu(eventDescription, null, eventSport);
			
		verify(dataAccess).gertaerakSortu(eventDescription, null, eventSport);
	}
	
	//null parameter sport
	@Test(expected = Exception.class)
	public void testCase6() throws EventFinished {
		Mockito.when(dataAccess.gertaerakSortu(eventDescription, eventDate, null)).thenThrow(Exception.class);
				
		sut.gertaerakSortu(eventDescription, eventDate, null);
				
		verify(dataAccess).gertaerakSortu(eventDescription, eventDate, null);
	}
	
	//repeated event 
	@Test
	public void testCase7() throws EventFinished {
		Mockito.when(dataAccess.gertaerakSortu(eventDescription, eventDate, eventSport)).thenReturn(false);
				
		boolean addedEvent = sut.gertaerakSortu(eventDescription, eventDate, eventSport);
				
		assertFalse(addedEvent);
		verify(dataAccess).gertaerakSortu(eventDescription, eventDate, eventSport);
	}
	
	//date before today
	@Test(expected = EventFinished.class)
	public void testCase8() throws EventFinished {
		Date yesterday = new Date(System.currentTimeMillis()-24*60*60*1000);
		
		boolean addedEvent = sut.gertaerakSortu(eventDescription, yesterday, eventSport);
				
		assertFalse(addedEvent);
	}
}
