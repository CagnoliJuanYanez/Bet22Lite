import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import dataAccess.DataAccess;
import domain.Event;
import domain.Registered;
import domain.Sport;
import test.dataAccess.TestDataAccess;

public class RankingLortuDAW {
	
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
		
	}
	
	
	@Test
	public void testPath3() {
		try {
			testDA.deleteAll();
			
			List<Registered> orderedList = sut.rankingLortu();
			assertEquals(orderedList.size(), 0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		} 
	}
	
	
	

}
