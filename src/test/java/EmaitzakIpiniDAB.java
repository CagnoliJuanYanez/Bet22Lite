import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.ApustuAnitza;
import domain.Apustua;
import domain.Event;
import domain.Question;
import domain.Quote;
import domain.Registered;
import domain.Sport;
import exceptions.EventNotFinished;
import test.dataAccess.TestDataAccess;

public class EmaitzakIpiniDAB {

	// sut:system under test
	static DataAccess sut = new DataAccess();

	// additional operations needed to execute the test
	static TestDataAccess testDA = new TestDataAccess();

	private SimpleDateFormat sdf;
	private String eventDescription;

	@Before
	public void initialize() {
		eventDescription = "Villareal-Barcelona";
	}

	@Test
	public void testPath1() {
		testDA.open();
		testDA.deleteAll();

		Event eventInDb = testDA.addFinishedEventWithQuote(true);

		testDA.close();
		try {
			sut.EmaitzakIpini(eventInDb.getQuestions().get(0).getQuotes().get(0));
		} catch (EventNotFinished e) {
			fail();
		}

		testDA.open();
		eventInDb = testDA.getEvent(eventInDb.getEventNumber());
		testDA.close();

		Quote q = eventInDb.getQuestions().get(0).getQuotes().get(0);
		Apustua apu = q.getApustuak().get(0);
		assertEquals(apu.getEgoera(), "irabazita");
		sut.close();
		sut.open(false);
	}

	@Test
	public void testPath2() {
		testDA.open();
		testDA.deleteAll();
		testDA.close();

		try {
			sut.EmaitzakIpini(new Quote());
		} catch (EventNotFinished e) { 
			fail();
		}

		sut.close();
		sut.open(false);
	}
	
	@Test
	public void testPath3() {
		testDA.open();
		testDA.deleteAll();

		testDA.addEvent();
		testDA.close();

		try {
			sut.EmaitzakIpini(new Quote());
		} catch (EventNotFinished e) { // TODO Auto- catch block
			fail();
		}

		sut.close();
		sut.open(false);
	}
	
	@Test
	public void testPath4() {
		testDA.open();
		testDA.deleteAll();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date eventDate = new Date();
		try {
			eventDate = sdf.parse("15/4/2020");
		} catch (ParseException e) {
			fail();
		}
		Event eventInDb = testDA.addEventWithQuestion(eventDescription, eventDate, "query2", 0);
		testDA.close();

		try {
			sut.EmaitzakIpini(new Quote());
		} catch (EventNotFinished e) { // TODO Auto- catch block
			fail();
		}

		sut.close();
		sut.open(false);
	}
	

	@Test
	public void testPath5() {
		testDA.open();
		testDA.deleteAll();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date eventDate = new Date();
		try {
			eventDate = sdf.parse("15/4/2020");
		} catch (ParseException e) {
			fail();
		}
		Event eventInDb = testDA.addEventWithoutBets();
		testDA.close();

		try {
			sut.EmaitzakIpini(eventInDb.getQuestions().get(0).getQuotes().get(0));
		} catch (EventNotFinished e) { // TODO Auto- catch block
			fail();
		}

		Quote q = eventInDb.getQuestions().get(0).getQuotes().get(0);
		assertEquals(0, q.getApustuak().size());		
		sut.close();
		sut.open(false);
	}

}
