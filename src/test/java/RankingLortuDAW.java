import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


import org.junit.Before;
import org.junit.Test;

import java.util.List;

import dataAccess.DataAccess;
import domain.Registered;
import test.dataAccess.TestDataAccess;

public class RankingLortuDAW {
	
	//sut:system under test
	static DataAccess sut=new DataAccess();
		 
	//additional operations needed to execute the test 
	static TestDataAccess testDA=new TestDataAccess();
	

	private Registered registeredInDb1;
	private Registered registeredInDb2;

	@Before
	public void initialize() {
		
	}
	
	
	@Test
	public void testPath1() {
		try {
			testDA.deleteRegistered();
			List<Registered> orderedList = sut.rankingLortu();
			assertEquals(orderedList.size(), 0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		} 
	}
	
	@Test
	public void testPath2() {
		try {
			testDA.open();
			registeredInDb1 = testDA.addRegistered("fran", "contra123", 99999, 0.0);
			testDA.close();
			
			List<Registered> orderedList = sut.rankingLortu();
			assertEquals(orderedList.size(), 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		} finally {
			testDA.open();
			testDA.deleteRegistered();
	        testDA.close();
		}
	
	}
	
	@Test
	public void testPath3() {
		try {
			testDA.open();
			registeredInDb1 = testDA.addRegistered("fran", "contra123", 99999, 8.2);
			registeredInDb2 = testDA.addRegistered("andru", "contra123", 99998, 1.4);
			testDA.close();
			
			List<Registered> orderedList = sut.rankingLortu();
			double firstScore = orderedList.get(0).getIrabazitakoa();
			double SecondScore = orderedList.get(1).getIrabazitakoa();
			assertEquals(firstScore, registeredInDb2.getIrabazitakoa().doubleValue());
			assertEquals(orderedList.get(1), registeredInDb1);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		} finally {
			testDA.open();
			testDA.deleteRegistered();
	        testDA.close();
		}
	
	}
	

}