package domain;

import java.util.List;

public class ExtendedIteratorEvents implements ExtendedIterator<Event> {
	List<Event> events;
	int position = 0;
	
	public ExtendedIteratorEvents(List<Event> evs) {
		this.events = evs;
	}
	
	public boolean hasNext() {
		return position < events.size();
	}

	public Event next() {
		Event event = events.get(position);
		position = position + 1;
		return event;
	}

	public Event previous() {
		Event event = events.get(position);
		position = position - 1;
		return event;
	}

	public boolean hasPrevious() {
		return position > 0;
	}

	public void goFirst() {
		position = 0;
		
	}

	public void goLast() {
		position = events.size() - 1;
		
	}
	
}
