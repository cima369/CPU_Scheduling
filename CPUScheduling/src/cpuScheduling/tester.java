package cpuScheduling;

public class tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		CPUScheduler abc = new CPUScheduler();
		
		abc.addProcess("P", "type", "10");
		
		abc.nonPreSJF();
		abc.RoundRobin();
		
	}

}
