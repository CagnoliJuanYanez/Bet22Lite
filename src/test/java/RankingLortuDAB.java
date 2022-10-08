import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.Event;
import domain.Registered;
import domain.Sport;
import test.dataAccess.TestDataAccess;

public class RankingLortuDAB {
	
	//sut:system under test
	static DataAccess sut=new DataAccess();
			 
	//additional operations needed to execute the test 
	static TestDataAccess testDA=new TestDataAccess();
		
	private Registered registeredInDb1;
	private Registered registeredInDb2;
	private Registered registeredInDb3;
	private Registered registeredInDb4;
	private Registered registeredInDb5;
	private Registered registeredInDb6;
	private Registered registeredInDb7;

		
	@Before
	public void initialize() {
		
			
	}
	
	@Test
	public void testPath1() {
		testDA.open();
		testDA.deleteAll();
		testDA.close();
		try {
			List<Registered> orderedList = sut.rankingLortu();
			assertEquals(orderedList.size(), 0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		} 
	}
	
	
	@Test
	public void testPath2() {
		testDA.open();
		testDA.deleteAll();
        testDA.close();
		try {
			testDA.open();
			registeredInDb1 = testDA.addRegistered("fran", "contra123", 99999, 1.1);
			registeredInDb2 = testDA.addRegistered("andru", "contra123", 99998, -1.6);
			registeredInDb3 = testDA.addRegistered("herno", "contra123", 99997, 4.4);
			testDA.close();
			
			List<Registered> orderedList = sut.rankingLortu();
			double firstScore = orderedList.get(0).getIrabazitakoa();
			double SecondScore = orderedList.get(1).getIrabazitakoa();
			double ThirdScore = orderedList.get(2).getIrabazitakoa();
			assertEquals(firstScore, registeredInDb3.getIrabazitakoa().doubleValue(), 0);
			assertEquals(SecondScore, registeredInDb1.getIrabazitakoa().doubleValue(), 0);
			assertEquals(ThirdScore, registeredInDb2.getIrabazitakoa().doubleValue(), 0);


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
	public void testPath3() {
		testDA.open();
		testDA.deleteAll();
        testDA.close();
		try {
			testDA.open();
			registeredInDb4 = testDA.addRegistered("martin", "contra123", 99995, 3.3);
			registeredInDb5 = testDA.addRegistered("nico", "contra123", 99994, 8.7);
			registeredInDb6 = testDA.addRegistered("gaston", "contra123", 99993, 1.2);
			registeredInDb7 = testDA.addRegistered("fer", "contra123", 99992, 3.3);
			testDA.close();
			
			List<Registered> orderedList = sut.rankingLortu();
			double fourthScore = orderedList.get(0).getIrabazitakoa();
			double fifthScore = orderedList.get(1).getIrabazitakoa();
			double sixthScore = orderedList.get(2).getIrabazitakoa();
			double seventhScore = orderedList.get(3).getIrabazitakoa();
			
			assertEquals(fourthScore, registeredInDb5.getIrabazitakoa().doubleValue(), 0);
			assertEquals(fifthScore, registeredInDb4.getIrabazitakoa().doubleValue(), 0);
			assertEquals(sixthScore, registeredInDb7.getIrabazitakoa().doubleValue(), 0);
			assertEquals(seventhScore, registeredInDb6.getIrabazitakoa().doubleValue(), 0);


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
