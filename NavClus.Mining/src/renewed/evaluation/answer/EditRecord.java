package renewed.evaluation.answer;

public class EditRecord {
	
	String time;
	String element;
	
	public EditRecord(String time, String element) {
		this.time = time;
		this.element = element;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getElement() {
		return element;
	}
	
	public void setElement(String element) {
		this.element = element;
	}

	public void println() {
		System.out.println(time + "/	" + element);
	}
}
