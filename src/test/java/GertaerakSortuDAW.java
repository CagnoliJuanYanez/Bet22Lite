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

public class GertaerakSortuDAW {
	
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
	
	@Before
	public void initialize() {
		eventDescription = "Villareal-Barcelona";
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			eventDate = sdf.parse("15/4/2016");	
		} catch (ParseException pe) {
			fail();
		}
		
		eventSport = "Futbol";
	}
	
	@Test
	public void testPath1() {
		try {
			boolean eventAdded = sut.gertaerakSortu(eventDescription, eventDate, eventSport);
			
			assertFalse(eventAdded);
		} catch (Exception e) {
			fail();
		} finally {
			//force to close transaction with DB as the method does not close it
			//otherwise all following tests will fail.
			sut.close();
			sut.open(false);
			
		}
	}
	
	@Test
	public void testPath3() {
		try {
			testDA.open();
			sportInDb = testDA.addSport(eventSport);
			testDA.close();
			
			testDA.open();
			eventInDb = testDA.addEventWithQuestion(eventDescription, eventDate, "query2", 0);
			testDA.close();
			
			boolean eventAdded = sut.gertaerakSortu(eventDescription, eventDate, eventSport);
			
			assertFalse(eventAdded);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		} finally {
			testDA.open();
			testDA.deleteAll();
			testDA.close();
		}
	}

		
	@Test
	public void testPath4() {
		try {
			testDA.open();
			sportInDb = testDA.addSport(eventSport);
			testDA.close();
			
			
			boolean eventAdded = sut.gertaerakSortu(eventDescription, eventDate, eventSport);
			
			assertTrue(eventAdded);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		} finally {
			testDA.open();
			testDA.deleteAll();
			testDA.close();
		}
	}
	
	@Test
	public void testPath5() {
		try {
			testDA.open();
			eventInDb = testDA.addEventWithQuestion("Otro-Otro", sdf.parse("16/4/2016"), "query2", 0);
			testDA.close();
			
			testDA.open();
			sportInDb = testDA.addSport("Baloncesto");
			testDA.close();
			
			
			boolean eventAdded = sut.gertaerakSortu(eventDescription, eventDate, "Baloncesto");
			
			assertTrue(eventAdded);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		} finally {
			testDA.open();
			testDA.deleteAll();
			testDA.close();
		}
	}
	
	
	

}
