package test.dataAccess;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import domain.Event;
import domain.Question;
import domain.Sport;

public class TestDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();


	public TestDataAccess()  {
		
		System.out.println("Creating TestDataAccess instance");

		open();
		
	}

	
	public void open(){
		
		System.out.println("Opening TestDataAccess instance ");

		String fileName=c.getDbFilename();
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		
	}
	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}

	public boolean removeEvent(Event ev) {
		System.out.println(">> DataAccessTest: removeEvent");
		if (ev!=null) {
			db.getTransaction().begin();
			db.remove(ev);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
		
		public Event addEventWithQuestion(String desc, Date d, String question, float qty) {
			System.out.println(">> DataAccessTest: addEvent");
			Event ev=null;
				db.getTransaction().begin();
				try {
				    ev=new Event(desc, d);
				    ev.addQuestion(question, qty);
					db.persist(ev);
					db.getTransaction().commit();
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return ev;
	    }
		
		public boolean existQuestion(Event ev,Question q) {
			System.out.println(">> DataAccessTest: existQuestion");
			Event e = db.find(Event.class, ev.getEventNumber());
			if (e!=null) {
				return e.DoesQuestionExists(q.getQuestion());
			} else 
			return false;
			
		}
		
		public boolean existEvent(Event ev) {
			System.out.println(">> DataAccessTest: existQuestion");
			Event e = db.find(Event.class, ev.getEventNumber());
			if (e!=null) {
				return true;
			} else 
			return false;
			
		}
		
		public boolean existEvent(Date date, String description) {
			System.out.println(">> DataAccessTest: existEventCompositeKey");
			TypedQuery<Event> Equery = db.createQuery("SELECT e FROM Event e WHERE eventDate=?1 AND description=?2",Event.class);
			Equery.setParameter(1, date);
			Equery.setParameter(2, description);
			return Equery.getResultList().size() > 0;
		}
		
		public Sport addSport(String sport) {
			System.out.println(">> DataAccessTest: addSport");
			Sport sportToAdd = null;
			db.getTransaction().begin();
			try {
				sportToAdd = new Sport(sport);
				db.persist(sportToAdd);
				db.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return sportToAdd;
		}
		
		public Event getEvent(Date date, String description) {
			System.out.println(">> DataAccessTest: getEventCompositeKey");
			
			TypedQuery<Event> Equery = db.createQuery("SELECT e FROM Event e WHERE eventDate=?1 AND description=?2",Event.class);
			Equery.setParameter(1, date);
			Equery.setParameter(2, description);
			
			if (Equery.getResultList().size() != 0) {
				return Equery.getResultList().get(0);
			} else {
				return null;
			}
		}
		
		public void deleteAll() {
			db.getTransaction().begin();
			try {
				db.createQuery("DELETE FROM domain.Sport").executeUpdate();
				db.createQuery("DELETE FROM domain.Quote").executeUpdate();
				db.createQuery("DELETE FROM domain.Question").executeUpdate();
				db.createQuery("DELETE FROM domain.Registered").executeUpdate();
				db.createQuery("DELETE FROM domain.Apustua").executeUpdate();
				db.createQuery("DELETE FROM domain.Event").executeUpdate();
				db.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
}

