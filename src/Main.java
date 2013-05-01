import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Main {
	public int numTasks, numMachines;
	public int outNumMachines;
	public double average;
	public ArrayList<Task> taskArray = new ArrayList<Task>();
	public ArrayList<Machine> machineArray = new ArrayList<Machine>();
	public ArrayList<Machine> machineSolutionArray = new ArrayList<Machine>();
	public ArrayList<Machine> validationMachines = new ArrayList<Machine>();
	public int indexFastest;
	
	
	
	public void loadFile(String fileName) throws FileNotFoundException {
		Scanner scan = new Scanner(new FileReader(fileName));
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
		int max = 0;
		int i = 0;
		for (Machine m: machineArray){
			if (m.speed > max){
				max = m.speed;
				indexFastest = i;
			}
			++i;
		}
		
	}
	
	public void allocation() {
		int c = 0;
		int max = 0;
		int i = 0;
		int index = 0;
		
		for (Machine m: machineArray){
			m.tasks.clear();
		}
		
		Random rand = new Random();
		for (Task t: taskArray){
			if (c == 0 || (numMachines%c) == 0){
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
	
	public void printAllocations(BufferedWriter bw) throws IOException {
		
		for (Machine m: machineSolutionArray) {
			for (Task t: m.tasks){
				System.out.print(t.id + " ");
				bw.write(t.id + " ");
			}
			
			if (m.tasks.size() == 0) {
				System.out.print("NONE");
				bw.write("");
			}
			bw.write('\n');
			System.out.println();
		}
		
	}
	
	public double findSolution() throws CloneNotSupportedException {
		double min = Double.MAX_VALUE;
		double temp;
		for (int i = 0; i < 20000000; ++i) {
			allocation();
			temp = findExecutionTime();
			if (temp < min) {
				min = temp;
				machineSolutionArray.clear();
				for (Machine m: machineArray) {
					machineSolutionArray.add((Machine) m.clone());
				}
			}
		}
		return min;
	}
	
	public boolean validation(String filename) throws FileNotFoundException {
		Scanner scan = new Scanner(new FileReader(filename));
		double actualValue;
		double inputValue;
		loadFile("group5.txt");
		for(int i = 0; i < numMachines; i++) {
			String line = scan.nextLine();
			String [] tasks = line.split(" ");
			Machine m = new Machine();
			for (String t: tasks){
				if (t.equals("")) {
					m.id = machineArray.get(i).id;
					m.speed = machineArray.get(i).speed;
				}
				else {
					int index = Integer.parseInt(t);

					m.tasks.add(taskArray.get(index));
					m.id = machineArray.get(i).id;
					m.speed = machineArray.get(i).speed;
				}
			}
			validationMachines.add(m);
		}
		
		inputValue = Double.parseDouble(scan.nextLine());
		
		actualValue = 0;
		for (Machine m: validationMachines) {
			int sum = 0;
			double temp;
			for(Task t: m.tasks) {
				sum = sum + t.speed;
			}
			temp = sum / (double)m.speed;
			if (temp > actualValue) {
				actualValue = temp;
			}
		}
		String actual = String.format("%.2f", actualValue);
		actualValue = Double.parseDouble(actual);
		if (actualValue == inputValue) {
			return true;
		}
		
		else {
			return false;
		}

	}
	
	
	public static void main(String[] args) throws CloneNotSupportedException, IOException {
		double min;
		Main main = new Main();
//		main.loadFile("group5.txt");
//		FileWriter fw = new FileWriter("output.txt");
//		BufferedWriter bw = new BufferedWriter(fw);
//		min = main.findSolution();
//		main.printAllocations(bw);
//		System.out.printf("%.2f", min);
//		String newMin = String.format("%.2f", min);
//		bw.write(newMin);
//		bw.close();
//		System.out.println()
		boolean valid = main.validation("output.txt");
		
		if (valid) {
			System.out.println("Solution is valid");
		}
		else {
			System.out.println("Solution is not valid");
		}
	}
}
