import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Quote;
import exceptions.EventFinished;
import exceptions.EventNotFinished;

public class EmaitzakIpiniMockInt {
	DataAccess dataAccess;
	BLFacade sut;
	Quote quote;
	
	@Before
	public void initialize() {
		dataAccess = Mockito.mock(DataAccess.class);
		sut = new BLFacadeImplementation(dataAccess);
		
		quote = new Quote();
	}
	
	
	@Test
	public void testCase1() throws EventNotFinished {
		doNothing().when(dataAccess).EmaitzakIpini(quote);
		sut.EmaitzakIpini(quote);
		verify(dataAccess).EmaitzakIpini(quote);
	}
	
	@Test(expected = EventNotFinished.class)
	public void testCase2() throws EventNotFinished {
		doThrow(new EventNotFinished()).when(dataAccess).EmaitzakIpini(quote);
		sut.EmaitzakIpini(quote);
		verify(dataAccess).EmaitzakIpini(quote);
	}
	
	
}
