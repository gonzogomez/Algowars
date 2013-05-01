import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Main {
	public int numTasks, numMachines;
	public double average;
	public ArrayList<Task> taskArray = new ArrayList<Task>();
	public ArrayList<Machine> machineArray = new ArrayList<Machine>();
	
	
	public void loadFile() throws FileNotFoundException {
		Scanner scan = new Scanner(new FileReader("algoWars"));
		numTasks = scan.nextInt();
		scan.nextLine();
		numMachines = scan.nextInt();
		scan.nextLine();
		for (int i = 0; i < numTasks; ++i) {
			Task t = new Task();
			t.id = i;
			t.speed = scan.nextInt();
			taskArray.add(t);
		}
		scan.nextLine();
		for (int i = 0; i < numMachines; ++i) {
			Machine m = new Machine();
			m.id = i;
			m.speed = scan.nextInt();
			machineArray.add(m);
		}
		double sum = 0;
		for (Task t: taskArray) {
			sum = sum + t.speed;
		}
		average = sum / numTasks;
		
	}
	
	public void allocation() {
		int c = 0;
		int max = 0;
		int i = 0;
		int index = 0;
		
		for (Machine m: machineArray){
			if (m.speed > max){
				max = m.speed;
				index = i;
			}
			++i;
		}
		
		Random rand = new Random();
		for (Task t: taskArray){
			if (t.speed > average){
				machineArray.get(index).tasks.add(t);
			}
			else {
				int ranIndex = rand.nextInt(numMachines);
				machineArray.get(ranIndex).tasks.add(t);
			}
			++c;
		}
	}
	
	public double findExecutionTime(){
		double max = 0;
		for (Machine m: machineArray) {
			int sum = 0;
			double temp;
			for(Task t: m.tasks) {
				sum = sum + t.speed;
			}
			temp = sum / (double)m.speed;
			if (temp > max) {
				max = temp;
			}
		}
		return max;
	}
	
	public void printAllocations() {
		for (Machine m: machineArray) {
			for (Task t: m.tasks){
				System.out.print(t.id+1 + " ");
			}
			if (m.tasks.size() == 0) {
				System.out.print("NONE");
			}
			System.out.println();
		}
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		double min;
		Main main = new Main();
		main.loadFile();
		main.allocation();
		main.printAllocations();
		System.out.println(main.findExecutionTime());
	}
}
