import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Registered;

public class RankingLortuMockInt {
	DataAccess dataAccess;
	BLFacade sut;

	private Registered registeredInDb1;
	private Registered registeredInDb2;
	private Registered registeredInDb3;
	private Registered registeredInDb4;
	private Registered registeredInDb5;
	private Registered registeredInDb6;
	private Registered registeredInDb7;
	
	
	@Before
	public void initialize() {
		dataAccess = Mockito.mock(DataAccess.class);
		sut = new BLFacadeImplementation(dataAccess);
		
	}
	
	
	@Test
	public void testCase1() {
		Mockito.when(dataAccess.rankingLortu()).thenReturn(null);
		
		List<Registered> orderedList = sut.rankingLortu();

		verify(dataAccess).rankingLortu();

		
		assertNull( orderedList);
	}
	
	@Test
	public void testCase2() {
		
		registeredInDb1 = new Registered("martin", "contra123", 99999);
		registeredInDb1.setIrabazitakoa(1.1);
		registeredInDb2 = new Registered("fran", "contra123", 99998);
		registeredInDb2.setIrabazitakoa(-1.6);
		registeredInDb3 = new Registered("herno", "contra123", 99997);
		registeredInDb3.setIrabazitakoa(4.4);
		
		List<Registered> sortedList = new ArrayList<Registered>();
		
		sortedList.add(registeredInDb3);
		sortedList.add(registeredInDb1);
		sortedList.add(registeredInDb2);


		Mockito.when(dataAccess.rankingLortu()).thenReturn(sortedList);
		
		List<Registered> orderedList = sut.rankingLortu();

		verify(dataAccess).rankingLortu();
		
		assertEquals(4.4, orderedList.get(0).getIrabazitakoa().doubleValue(), 0);
		assertEquals(1.1, orderedList.get(1).getIrabazitakoa().doubleValue(), 0);
		assertEquals(-1.6, orderedList.get(2).getIrabazitakoa().doubleValue(), 0);


	}
	
	@Test
	public void testCase3() {
		
		registeredInDb1 = new Registered("nico", "contra123", 99996);
		registeredInDb1.setIrabazitakoa(8.7);
		registeredInDb2 = new Registered("andru", "contra123", 99995);
		registeredInDb2.setIrabazitakoa(3.3);
		registeredInDb3 = new Registered("fede", "contra123", 99994);
		registeredInDb3.setIrabazitakoa(3.3);
		registeredInDb4 = new Registered("walter", "contra123", 99993);
		registeredInDb4.setIrabazitakoa(1.2);
		
		List<Registered> sortedList = new ArrayList<Registered>();
		
		sortedList.add(registeredInDb1);
		sortedList.add(registeredInDb2);
		sortedList.add(registeredInDb3);
		sortedList.add(registeredInDb4);



		Mockito.when(dataAccess.rankingLortu()).thenReturn(sortedList);
		
		List<Registered> orderedList = sut.rankingLortu();

		verify(dataAccess).rankingLortu();

		
		assertEquals(8.7, orderedList.get(0).getIrabazitakoa().doubleValue(), 0);
		assertEquals(3.3, orderedList.get(1).getIrabazitakoa().doubleValue(), 0);
		assertEquals(3.3, orderedList.get(2).getIrabazitakoa().doubleValue(), 0);
		assertEquals(1.2, orderedList.get(3).getIrabazitakoa().doubleValue(), 0);



	}
	
	
	
}
