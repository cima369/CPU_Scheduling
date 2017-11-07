package cpuScheduling;

import java.util.HashMap;
import java.util.Map;

public class CPUScheduler {

	private String[][] Process = new String[9000][3];
	private Map<String, Integer> Type = new HashMap<String, Integer>();
	public String[][] fcfs = new String[9000][2];
	public String[][] nonPreSJF = new String[9000][2];
	public String[][] SJF = new String[9000][2];
	public String[][] NonPrePrioritySchedule = new String[9000][2];
	public String[][] PrioritySchedule = new String[9000][2];
	public String[][] RoundRobinSchedule = new String[9000][2];
	

	/*
	 * Adding process in order of input and storing them as string in a two
	 * dimension array Input(process) in the format written in the document: (1)
	 * CPU_Time (2) I/O_Time (3) Process_Priority -> only needed when Priority
	 * Scheduling is applied. Input(value) written in numbers(integer)
	 * 
	 * 
	 */

	public void addProcess(String name, String processType, String value) {
		int j = Integer.parseInt(value);
		if (j == (int) j) {

			int i = 0;
			while (true) {
				if (Process[i][0] == null) {
					Process[i][0] = name;
					Process[i][1] = processType;
					Process[i][2] = value;
					break;
				} else {
					i++;
				}
			}
		} else {
			System.out.println("Value is not a number(integer)");
		}
	}

	/*
	 * Setting type of the process at that point of time (a) New: P has not
	 * arrived yet; (b) Running: P has been selected by the CPU scheduler for
	 * execution on the CPU, and is currently executing on the CPU; (c) Ready: P
	 * has already arrived, and P is currently waiting in the ready queue to be
	 * scheduled by the CPU Scheduler for execution on the CPU; (d) Waiting for
	 * I/O: P had previously made an I/O request to the I/O scheduler in order
	 * to perform execution on the CPU, and P is either currently performing I/O
	 * on an I/O device or is currently waiting in an I/O queue so that it can
	 * be scheduled by the I/O Scheduler to perform I/O on an I/O device; (e)
	 * Terminated: P has completed its sequence of CPU computation time and I/O
	 * time requirements. Integer 1 stand for New, 2 stands for Running, 3 stand
	 * for Ready, 4 stands for Waiting for I/O, and 5 stand for terminated
	 */
	public void setProcess(String name, int type) {
		if (Type.isEmpty()) {
			Type.put(name, type);
		} else {
			Type.remove(name);
			Type.put(name, type);
		}
	}

	/*
	 * Non-preemptive First-Come, First-Served (FCFS) Scheduling. In the order
	 * of input the processes will be processed in an orderly fashion. Starting
	 * from 1st input process to the lastly placed process.
	 */
	public void nonPreFCFS() {
		int i = 0;
		int store = 0;
		while (Process[i][0] != null) {
			setProcess(Process[i][0], 1); // Ready
			if ((i > 0) && (Process[i][0] == Process[i - 1][0]) && (Process[i][1] != "Process_Priority")) {
				setProcess(Process[i][0], 2); // Running
				store += Integer.parseInt(Process[i][2]);
			} else if (Process[i][0] != Process[i - 1][0]) {
				fcfs[i][0] = Process[i - 1][0];
				fcfs[i][1] = Integer.toString(store);
				setProcess(Process[i - 1][0], 5); // Terminated
				store = Integer.parseInt(Process[i][2]);
			}
			i++;
		}
	}

	/*
	 * Non-preemptive Shortest-Job-First (SJF) Scheduling. By adding up the time
	 * it takes to finish a single process, the process with the lowest time to
	 * finish will come first and the most time consuming being the last process
	 * to be completed. Initially placed the values into the array, then sorted
	 * with bubble sort. As it is non-preemptive, processes can only go from
	 * running state to waiting state and terminate. Run method will allow
	 * easier representation of differentiation between non-preemptive and
	 * preemptive.
	 */
	public void nonPreSJF() {
		int i = 0;
		int store = 0;
		while (Process[i][0] != null) { // placing values into the schedule
										// first
			if ((i > 0) && (Process[i][0] == Process[i - 1][0]) && (Process[i][1] != "Process_Priority")) {
				store += Integer.parseInt(Process[i][2]);
			} else if (Process[i][0] != Process[i - 1][0]) {
				nonPreSJF[i][0] = Process[i - 1][0];
				nonPreSJF[i][1] = Integer.toString(store);
				store = Integer.parseInt(Process[i][2]);
			}
		}
		i = 0;
		int count = 0;
		while (true) { // sorting to shortest Job First
			int first = Integer.valueOf(nonPreSJF[i][1]);
			int second = Integer.valueOf(nonPreSJF[i + 1][1]);
			if (first > second) {
				String tempValue = nonPreSJF[i][1];
				String tempName = nonPreSJF[i][0];

				nonPreSJF[i][0] = nonPreSJF[i + 1][1];
				nonPreSJF[i][1] = nonPreSJF[i + 1][1];

				nonPreSJF[i + 1][0] = tempName;
				nonPreSJF[i + 1][0] = tempValue;

				count++;
			}
			i++;
			if (nonPreSJF[i][0] != null) {
				i = 0;
				count = 0;
			}
			if (count == 0) {
				break;
			}
		}
	}

	/*
	 * Preemptive SJF(Shortest-Remaining-Time-First) Scheduling SJF_Schedule. By
	 * adding up the time it takes to finish a single process, the process with
	 * the lowest time to finish will come first and the most time consuming
	 * being the last process to be completed. Initially placed the values into
	 * the array, then sorted with bubble sort. As it is preemptive, processes
	 * can go from running state to ready state and go from waiting state to
	 * ready state. Run method will allow easier representation of
	 * differentiation between non-preemptive and preemptive.
	 */
	public void SJF() {
		int i = 0;
		int store = 0;
		while (Process[i][0] != null) { // placing values into the schedule
										// first
			if ((i > 0) && (Process[i][0] == Process[i - 1][0]) && (Process[i][1] != "Process_Priority")) {
				store += Integer.parseInt(Process[i][2]);
			} else if (Process[i][0] != Process[i - 1][0]) {
				SJF[i][0] = Process[i - 1][0];
				SJF[i][1] = Integer.toString(store);
				store = Integer.parseInt(Process[i][2]);
			}
		}
		i = 0;
		int count = 0;
		while (true) { // sorting to shortest Job First
			int first = Integer.valueOf(SJF[i][1]);
			int second = Integer.valueOf(SJF[i + 1][1]);
			if (first > second) {
				String tempValue = SJF[i][1];
				String tempName = SJF[i][0];

				SJF[i][0] = SJF[i + 1][1];
				SJF[i][1] = SJF[i + 1][1];

				SJF[i + 1][0] = tempName;
				SJF[i + 1][0] = tempValue;

				count++;
			}
			i++;
			if (SJF[i][0] != null) {
				i = 0;
				count = 0;
			}
			if (count == 0) {
				break;
			}
		}
	}

	/*
	 * Non-preemptive Priority Scheduling
	 */
	public void nonPrePriority() {

	}

	/*
	 * Preemptive Priority Scheduling
	 */
	public void priority() {

	}

	/*
	 *  Preemptive Round-Robin (RR) Scheduling
	 * 
	 *  Round Robin is the preemptive process scheduling algorithm.
	 *  Each process is provided a fix time to execute, it is called a quantum.
	 *	Once a process is executed for a given time period, it is preempted and other process executes for a given time period.
	 *	Context switching is used to save states of preempted processes.
	 
	 *  Process[i][0] = name;
	 *  Process[i][1] = processType;
	 *  Process[i][2] = value;
	 * 
	 *  Integer 1 stand for New, 2 stands for Running, 3 stand
	 *  for Ready, 4 stands for Waiting for I/O, and 5 stand for terminated
	 * 
	 */
	public void RoundRobin() {
		
		
		//Implement time quantum. 
		int i = 0;
		int store = 0;
		int size = 0;
		int burst_times[] = new int[size]; 
		int quantum_times[] = new int[size];
		
		burst_times[0] = 10;
		quantum_times[0] = 15;
		
		
		
		// BT - Burst Time  TQ - time quantum 
		//time quantum is the amount of time that the specific process will be executed for. 
		//every process is given time quantum
		
		while (Process[i][0] != null) {
			setProcess(Process[i][0], 1); // Ready state
			
			//YES
			if (burst_times[i] < quantum_times[i]) {
				// if yes then execute till completion
				   setProcess(Process[i][0], 2);  //running
				   
				   runProcess(i,burst_times[i]); //process i is executed for the amount specified in the burst

					//execute until completion
					RoundRobinSchedule[i][0] = Process[i-1][0];
					RoundRobinSchedule[i][1] = Integer.toString(store);
					setProcess(Process[i - 1][0], 5); // Terminated
			} 
			//NO
			else 
			{
				// if no, execute for time quantum
				//run for quantum_times[i] amount of time
				// TQ expires?
				setProcess(Process[i][0], 2);  //running
				runProcess(i,burst_times[i]); //process i is executed for 10ms
				
				// check that process execution is completed
				// if completed then terminate
				if(true)
				{
					RoundRobinSchedule[i][0] = Process[i-1][0];
					RoundRobinSchedule[i][1] = Integer.toString(store);
					setProcess(Process[i - 1][0], 5); // Terminated
				}
				
				// if not completed the go back to ready state
				if(false)
				{
					//went back to ready state
					setProcess(Process[i][0], 1); // Ready state
				}
				
			}
		}
		
//		while (Process[i][0] != null) {
//			setProcess(Process[i][0], 1); // Ready state
//
//			if ((i > 0) && (Process[i][0] == Process[i - 1][0]) && (Process[i][1] != "Process_Priority")) {
//				setProcess(Process[i][0], 2); // Running
//				store += Integer.parseInt(Process[i][2]);
//			}
//			
//			else if(Process[i][0] != Process[i - 1][0]){
//				
//				RoundRobinSchedule[i][0] = Process[i-1][0];
//				RoundRobinSchedule[i][1] = Integer.toString(store);
//				setProcess(Process[i - 1][0], 5); // Terminated
//				store = Integer.parseInt(Process[i][2]);	
//			}
//			
//			i++;
//		}
	}

	/*
	 * Multilevel Queue Scheduling
	 */
	public void multiQue() {

	}

	/*
	 * Multilevel Feedback Queue Scheduling
	 */
	public void multiFeedQue() {

	}
	
	/* 
	 * This simulates the running of the process. 
	 * 
	 */
	public void runProcess(int id, double time)
	{
		//process by id is executed  for time amount of time. 
	}
}
