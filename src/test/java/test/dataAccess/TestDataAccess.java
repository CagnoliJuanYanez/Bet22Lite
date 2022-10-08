package test.dataAccess;

import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.Query;

import configuration.ConfigXML;
import domain.ApustuAnitza;
import domain.Apustua;
import domain.Event;
import domain.Question;
import domain.Quote;
import domain.Registered;
import domain.Sport;

public class TestDataAccess {
	protected EntityManager db;
	protected EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();

	public TestDataAccess() {

		System.out.println("Creating TestDataAccess instance");

		open();

	}

	public void open() {

		System.out.println("Opening TestDataAccess instance ");

		String fileName = c.getDbFilename();

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}

	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	public boolean removeEvent(Event ev) {
		System.out.println(">> DataAccessTest: removeEvent");
		Event e = db.find(Event.class, ev.getEventNumber());
		if (e != null) {
			db.getTransaction().begin();
			db.remove(ev);
			db.getTransaction().commit();
			return true;

		} else
			return false;
	}

	public boolean removeSport(Sport sport) {
		System.out.println(">> DataAccessTest: removeSport");
		Sport spo = db.find(Sport.class, sport);
		if (spo != null) {
			db.getTransaction().begin();
			db.remove(spo);
			db.getTransaction().commit();
			return true;
		} else
			return false;
	}

	public Event addEventWithQuestion(String desc, Date d, String question, float qty) {
		System.out.println(">> DataAccessTest: addEvent");
		Event ev = null;
		db.getTransaction().begin();
		try {
			ev = new Event(desc, d);
			ev.addQuestion(question, qty);
			db.persist(ev);
			db.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ev;
	}

	public boolean existQuestion(Event ev, Question q) {
		System.out.println(">> DataAccessTest: existQuestion");
		Event e = db.find(Event.class, ev.getEventNumber());
		if (e != null) {
			return e.DoesQuestionExists(q.getQuestion());
		} else
			return false;

	}

	public boolean existEvent(Event ev) {
		System.out.println(">> DataAccessTest: existQuestion");
		Event e = db.find(Event.class, ev.getEventNumber());
		if (e != null) {
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

	public Quote addQuote(Double num, String forecast, Question question, Boolean add) {
		System.out.println(">> DataAccessTest: addQuote");
		db.getTransaction().begin();
		Quote quote = new Quote();
		try {
			if (add) {
				Question q = db.find(Question.class, question.getQuestionNumber());
				quote = q.addQuote(num, forecast, question);
				db.merge(q);
			} else {
				quote = new Quote(num, forecast, question);
				db.persist(quote);
			}
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

	public Question addQuestionToEvent(String question, Event e) {

		db.getTransaction().begin();
		Event event = db.find(Event.class, e.getEventNumber());
		Question q = event.addQuestion(question, 1);
		db.persist(q);

		db.merge(e);
		db.getTransaction().commit();

		return q;
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

	public void changeEventYear(Event eventInDb, int setYear) {
		db.getTransaction().begin();
		Date date = eventInDb.getEventDate();
		date.setYear(setYear);
		Event e = db.find(Event.class, eventInDb.getEventNumber());
		e.setEventDate(date);
		db.merge(e);
	}

	public Apustua addApustua(Apustua apustua) {
		System.out.println(">> DataAccessTest: addApustua");
		try {
			db.getTransaction().begin();
			db.persist(apustua);
			db.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return apustua;
	}

	public Event addFinishedEventWithQuote(Boolean sameQuote) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date eventDate = new Date();
		try {
			eventDate = sdf.parse("15/4/2020");
		} catch (ParseException e) {
			fail();
		}

		Event ev = null;
		List<ApustuAnitza> list = new ArrayList();
		db.getTransaction().begin();
		Registered us = new Registered("username", "password", 123123, false);
		ApustuAnitza ap = new ApustuAnitza(us, 1.1);
		list.add(ap);
		db.persist(us);
		db.persist(ap);
		db.getTransaction().commit();

		db.getTransaction().begin();
		try {
			ev = new Event("Evento", eventDate);
			Question question = ev.addQuestion("question", 1.0);
			Quote q = question.addQuote(1.6, "forecast", question);
			Quote q2 = question.addQuote(1.2, "forecast2", question);
			if(sameQuote) q.addApustua(new Apustua(ap, q));
			else q.addApustua(new Apustua(ap, q2));
			
			db.persist(ev);

			db.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ev;
	}

	public Event getEvent(Integer eventNumber) {
		return db.find(Event.class, eventNumber);
	}
	
	public boolean removeRegistered(Registered registered) {
		System.out.println(">> DataAccessTest: removeRegistered");
		Registered reg = db.find(Registered.class, registered);
		if (reg!=null) {
			db.getTransaction().begin();
			db.remove(reg);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
	}

	public void deleteRegistered() {
		db.getTransaction().begin();
		try {
			db.createQuery("DELETE FROM domain.Registered").executeUpdate();
			db.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean existEvent(Date date, String description) {
		System.out.println(">> DataAccessTest: existEventCompositeKey");
		TypedQuery<Event> Equery = db.createQuery("SELECT e FROM Event e WHERE eventDate=?1 AND description=?2",Event.class);
		Equery.setParameter(1, date);
		Equery.setParameter(2, description);
		return Equery.getResultList().size() > 0;
	}
	

	public Registered addRegistered(String username, String password, Integer bankAcount, double score) {
		System.out.println(">> DataAccessTest: addRegistered");
		Registered registeredToAdd = null;
		db.getTransaction().begin();
		try {
			registeredToAdd = new Registered(username, password, bankAcount);
			registeredToAdd.setIrabazitakoa(score);
			db.persist(registeredToAdd);
			db.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return registeredToAdd;
	}
	
	public Event addEvent() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date eventDate = new Date();
		try {
			eventDate = sdf.parse("15/4/2020");
		} catch (ParseException e) {
			fail();
		}

		Event ev = null;
		db.getTransaction().begin();
		try {
			ev = new Event("Evento", eventDate);
			db.persist(ev);
			db.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ev;
	}
	
	public Event addEventWithoutBets() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date eventDate = new Date();
		try {
			eventDate = sdf.parse("15/4/2020");
		} catch (ParseException e) {
			fail();
		}

		Event ev = null;

		db.getTransaction().begin();
		try {
			ev = new Event("Evento", eventDate);
			Question question = ev.addQuestion("question", 1.0);
			Quote q = question.addQuote(1.6, "forecast", question);
			db.persist(ev);
			db.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ev;
	}


}
