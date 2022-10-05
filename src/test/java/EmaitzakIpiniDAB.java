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
		try {
			sdf = new SimpleDateFormat("dd/MM/yyyy");
			testDA.open();
			testDA.deleteAll();
			Date eventDate = sdf.parse("15/4/2026");
			Event eventInDb = testDA.addEventWithQuestion(eventDescription, eventDate, "query2", 0);
			Question question = new Question("query", 2, eventInDb);
			testDA.addQuestion(question);
			Quote quo = new Quote(1.2, "forecast2", question);
			Quote quoInDb = testDA.addQuote(quo);
			testDA.close();
			sut.EmaitzakIpini(quoInDb);
		} catch (EventNotFinished e) {
			assertTrue(true);
		} catch (Exception e) {
			fail();
		} finally {
			// force to close transaction with DB as the method does not close it
			// otherwise all following tests will fail.
			sut.close();
			sut.open(false);
		}
	}

	@Test
	public void testPath2() {
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		testDA.open();
		testDA.deleteAll();
		Date eventDate = new Date();
		try {
			eventDate = sdf.parse("15/4/2020");
		} catch (ParseException e) {
			fail();
		}
		Event eventInDb = testDA.addEventWithQuestion(eventDescription, eventDate, "query2", 0);
		Question question = new Question("query", 2, eventInDb);
		testDA.addQuestion(question);
		Quote quo = new Quote(1.2, "forecast2", question);
		Quote quoInDb = testDA.addQuote(quo);
		testDA.close();
		try {
			sut.EmaitzakIpini(quoInDb);
		} catch (EventNotFinished e) {
			fail();
		}
		assertTrue(question.getQuotes().isEmpty());
		sut.close();
		sut.open(false);
	}

	@Test
	public void testPath3() {
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		testDA.open();
		testDA.deleteAll();
		Date eventDate = new Date();
		try {
			eventDate = sdf.parse("15/4/2020");
		} catch (ParseException e) {
			fail();
		}
		Event eventInDb = testDA.addEventWithQuestion(eventDescription, eventDate, "query2", 0);
		Question question = new Question("query", 2, eventInDb);
		Quote q = question.addQuote(1.2, "forecast", question);
		testDA.addQuestion(question);
		testDA.close();
		try {
			sut.EmaitzakIpini(q);
		} catch (EventNotFinished e) {
			fail();
		}
		assertFalse(question.getQuotes().isEmpty());
		assertTrue(question.getQuotes().elementAt(0).getApustuak().isEmpty());
		sut.close();
		sut.open(false);
	}

	
	@Test
	public void testPath4() {
		testDA.open();
		testDA.deleteAll();
	
		Event eventInDb = testDA.addFinishedEventWithQuote();		
		
		testDA.close();
		try {
			sut.EmaitzakIpini(eventInDb.getQuestions().get(0).getQuotes().get(0));
		} catch (EventNotFinished e) {
			fail();
		}
		
		sut.close();
		sut.open(false);
	}
}
