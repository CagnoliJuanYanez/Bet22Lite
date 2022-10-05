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
import domain.Question;
import domain.Quote;
import domain.Sport;
import exceptions.EventNotFinished;
import test.dataAccess.TestDataAccess;

public class EmaitzakIpiniDAB {

	// sut:system under test
	static DataAccess sut = new DataAccess();

	// additional operations needed to execute the test
	static TestDataAccess testDA = new TestDataAccess();

	private SimpleDateFormat sdf;
	private Date eventDate;
	private String eventSport;
	private Event eventInDb;
	private Sport sportInDb;
	private String eventDescription;
	private Quote quoInDb;
	private Question question;

	@Before
	public void initialize() {
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		eventDescription = "Villareal-Barcelona";
		
			testDA.open();
			testDA.deleteAll();
			eventDate = new Date();
			eventDate.setYear(eventDate.getYear()+1);
			eventSport = "Futbol";
			eventInDb = testDA.addEventWithQuestion(eventDescription, eventDate, "query2", 0);
			question = new Question("query?", 2, eventInDb);
			testDA.addQuestion(question);	
			Quote quo = new Quote(1.0, "forecast", question);
			sportInDb = testDA.addSport(eventSport);
			quoInDb = testDA.addQuote(quo);
		
			testDA.close();
		
	}

	@Test
	public void testPath1() {
		try {
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

}
