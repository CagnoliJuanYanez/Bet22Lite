package test.dataAccess;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import configuration.ConfigXML;
import domain.Event;
import domain.Question;
import domain.Quote;
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
		Event e = db.find(Event.class, ev.getEventNumber());
		if (e!=null) {
			db.getTransaction().begin();
			db.remove(e);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
	
	public boolean removeSport(Sport sport) {
		System.out.println(">> DataAccessTest: removeSport");
		Sport spo = db.find(Sport.class, sport);
		if (spo!=null) {
			db.getTransaction().begin();
			db.remove(spo);
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
		
		public Quote addQuote(Quote quote) {
			System.out.println(">> DataAccessTest: addQuote");
			db.getTransaction().begin();
			try {
				db.persist(quote);
				db.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return quote;
		}
		
		public Question addQuestion(Question question) {
			System.out.println(">> DataAccessTest: addQuestion");
			db.getTransaction().begin();
			try {
				db.persist(question);
				db.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return question;
		}


		public void deleteAll() {
			db.getTransaction().begin();
			try {
				db.createQuery("DELETE FROM domain.Sport").executeUpdate();
				db.createQuery("DELETE FROM domain.Quote").executeUpdate();
				db.createQuery("DELETE FROM domain.Question").executeUpdate();
				db.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
}

