import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.Event;
import domain.Sport;
import test.dataAccess.TestDataAccess;

public class GertaerakSortuDAB {
	
	//sut:system under test
	static DataAccess sut=new DataAccess();
			 
	//additional operations needed to execute the test 
	static TestDataAccess testDA=new TestDataAccess();
		
	private String eventDescription;
	private SimpleDateFormat sdf;
	private Date eventDate;
	private String eventSport;
	private Event eventInDb;
	private Sport sportInDb;
		
//	@Before
//	public void initialize() {
//		eventDescription = "Real Madrid-Barcelona";
//		sdf = new SimpleDateFormat("dd/MM/yyyy");
//			
//		try {
//			eventDate = sdf.parse("28/10/2022");	
//		} catch (ParseException pe) {
//			fail();
//		}
//			
//	}
//	
//	//different sports will be used for each test as if not, Optimistic lock failed for object domain.Sport is thrown.
//	
//	@Test
//	public void testCase1() {
//		try {		
//			eventSport = "Futbol1";
//			
//			testDA.open();
//			sportInDb = testDA.addSport(eventSport);
//			testDA.close();
//			
//			boolean addedEvent = sut.gertaerakSortu(eventDescription, eventDate, eventSport);
//			
//			assertTrue(addedEvent);
//			
//			testDA.open();
//			boolean existsEvent = testDA.existEvent(eventDate, eventDescription);
//			testDA.close();
//			
//			assertTrue(existsEvent);
//			
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			fail();
//		} finally {
//			testDA.open();
//			testDA.deleteAll();
//			testDA.close();
//		}
//	}
//	
//	//this is test case 8 as well (overlaps)
//	@Test
//	public void testCase1LimitValueForDateYesterday() {
//		try {
//			eventSport = "Futbol1.1";
//			
//			testDA.open();
//			sportInDb = testDA.addSport(eventSport);
//			testDA.close();
//			
//			Date yesterday = new Date(System.currentTimeMillis()-24*60*60*1000);
//			
//			boolean addedEvent = sut.gertaerakSortu(eventDescription, yesterday, eventSport);
//			
//			assertFalse(addedEvent);
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			fail();
//		} finally {
//			testDA.open();
//			testDA.deleteAll();
//			testDA.close();
//		}
//	}
//	
//	@Test
//	public void testCase2() {
//		try {
//			eventSport = "Futbol2";
//			
//			testDA.open();
//			sportInDb = testDA.addSport(eventSport);
//			testDA.close();
//			
//			boolean addedEvent = sut.gertaerakSortu("Real Madrid-", eventDate, eventSport);
//			
//			assertFalse(addedEvent);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			fail();
//		} finally {
//			testDA.open();
//			testDA.deleteAll();
//			testDA.close();
//			
//			//force to close transaction with DB as the method does not close it
//			//otherwise all following tests will fail.
//			sut.close();
//			sut.open(false);
//		}
//	}
//	
//	@Test
//	public void testCase3() {
//		try {			
//			boolean addedEvent = sut.gertaerakSortu(eventDescription, eventDate, "Badmington");
//			
//			assertFalse(addedEvent);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			fail();
//		} finally {
//			testDA.open();
//			testDA.deleteAll();
//			testDA.close();
//			
//			//force to close transaction with DB as the method does not close it
//			//otherwise all following tests will fail.
//			sut.close();
//			sut.open(false);
//		}
//	}
//	
//	@Test
//	public void testCase4() {
//		try {
//			eventSport = "Futbol4";
//			
//			testDA.open();
//			sportInDb = testDA.addSport(eventSport);
//			testDA.close();
//			
//			boolean addedEvent = sut.gertaerakSortu(null, eventDate, eventSport);
//			
//			assertFalse(addedEvent);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			fail();
//		} finally {
//			testDA.open();
//			testDA.deleteAll();
//			testDA.close();
//			
//			//force to close transaction with DB as the method does not close it
//			//otherwise all following tests will fail.
//			sut.close();
//			sut.open(false);
//		}
//	}
//	
//	@Test
//	public void testCase5() {
//		try {
//			eventSport = "Futbol5";
//			
//			testDA.open();
//			sportInDb = testDA.addSport(eventSport);
//			testDA.close();
//			
//			boolean addedEvent = sut.gertaerakSortu(eventDescription, null, eventSport);
//			
//			assertFalse(addedEvent);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			fail();
//		} finally {
//			testDA.open();
//			testDA.deleteAll();
//			testDA.close();
//			
//			//force to close transaction with DB as the method does not close it
//			//otherwise all following tests will fail.
//			sut.close();
//			sut.open(false);
//		}
//	}
//	
//	@Test
//	public void testCase6() {
//		try {
//			boolean addedEvent = sut.gertaerakSortu(eventDescription, eventDate, null);
//			
//			assertFalse(addedEvent);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			fail();
//		} finally {
//			testDA.open();
//			testDA.deleteAll();
//			testDA.close();
//			
//			//force to close transaction with DB as the method does not close it
//			//otherwise all following tests will fail.
//			sut.close();
//			sut.open(false);
//		}
//	}
//	
//	@Test
//	public void testCase7() {
//		try {
//			eventSport = "Futbol7";
//			
//			testDA.open();
//			sportInDb = testDA.addSport(eventSport);
//			testDA.close();
//			
//			testDA.open();
//			eventInDb = testDA.addEventWithQuestion(eventDescription, eventDate, "query", 0);
//			testDA.close();
//			
//			boolean addedEvent = sut.gertaerakSortu(eventDescription, eventDate, eventSport);
//			
//			assertFalse(addedEvent);
//			
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			fail();
//		} finally {
//			testDA.open();
//			testDA.deleteAll();
//			testDA.close();
//		}
//	}
 
}
