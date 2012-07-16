package renewed.data.queue;

public class Event {
	
	String element;
	String action;

	public Event(String element, String action) {
		super();
		this.element = element;
		this.action = action;
	}
		
	public String getElement() {
		return element;
	}
	public void setElement(String element) {
		this.element = element;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
}
