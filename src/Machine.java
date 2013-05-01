import java.util.ArrayList;


public class Machine implements Cloneable {
	public int id;
	public int speed;
	public ArrayList<Task> tasks = new ArrayList<Task>();

	public Object clone() throws CloneNotSupportedException{
		
			Machine cloned = (Machine)super.clone();
			cloned.tasks = (ArrayList<Task>) tasks.clone();
			return cloned;
		
	}
}
