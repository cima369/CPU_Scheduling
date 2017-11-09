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
	public String[][] MultiQue = new String [9000][2];
	public String[][] MQBackground = new String [9000][2];
	public String[][] MQForeground = new String [9000][2];

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
	 * runSJF method showing the status of each process when it is run within the schedule
	 * In place to show the difference between preemptive and non preemptive shortest job
	 * first Scheduling
	 */
	
	public void runSJF(String Schedule) {
		if (Schedule == "nonPreSJF") {
			nonPreSJF();
			int i = 0;
			while (true) {
				setProcess(nonPreSJF[i][0], 1); // Ready
				if ((i > 0) && nonPreSJF[i - 1][0] != null) {
					setProcess(nonPreSJF[i - 1][0], 5); // Terminate the process
														// before it starts
														// another
				}
				setProcess(nonPreSJF[i][0], 2); // Running
				i++;
				if (nonPreSJF[i][0] != null) {
					break;
				}
			}
			if (Schedule == "SJF") {
				SJF();
				int j = 0;
				while (true) {
					setProcess(SJF[j - 1][0], 1); // Ready
					if ((j > 0) && (SJF[j - 1] != null)) {
						setProcess(nonPreSJF[j - 1][0], 4); // Waiting
					}
					setProcess(SJF[i][0], 2); // Running
					j++;
					if (SJF[i][0] != null) {
						for (int z = 0; z <= j; z++) {
							setProcess(SJF[j][0], 5); // Terminating all
												      // processes
						}
						break;
					}
				}

			}
			else {
				System.out.println("Not nonPreSJF or SJF");
			}

		}
	}
	
	/*
	 * Non-preemptive Priority Scheduling
	 * 
	 * 
	 *  Processes are dispatched in a FIFO manner but are given a limited amount of time called quantum or time-slice.
	 *	If the process is not able to execute completely in given quantum time then the process is preempted and is placed at the back of the ready list.
	 *	Now CPU is given to the next process in ready state.
	 *	Same steps goes until all the processes are finished.
	 * 
	 * 
	 */
	public void nonPrePriority() {

		int size = 3; //number of processes
		
		//[burst_time][arrival time][priority]
		int[][] ProcessPriority = new int[100][4];
		int[] Waiting_Time = new int[100];
		int[] Turning_Time = new int[100];
		int Average_Waiting_Time = 0;
		int Average_Turning_Time = 0;
		
		int position;
		int sum = 0;
		
		ProcessPriority[0][0] = 3; 
		ProcessPriority[0][1] = 0; 
		ProcessPriority[0][2] = 3; 
		
		ProcessPriority[1][0] = 2; 
		ProcessPriority[1][1] = 1; 
		ProcessPriority[1][2] = 2; 
		
		ProcessPriority[2][0] = 1; 
		ProcessPriority[2][1] = 2; 
		ProcessPriority[2][2] = 4; 
		
		ProcessPriority[3][0] = 1; 
		ProcessPriority[3][1] = 3; 
		ProcessPriority[3][2] = 2; 

		int tmp = 0;
	    for(int i=0;i<size;i++)
	    {
	    	   position=i;
	           for(int j=i+1;j<size;j++)
	           {
	               if(ProcessPriority[j][2]<ProcessPriority[position][2])
	                   position=j;
	           }
	    
	           //priority
	           tmp=ProcessPriority[i][2];
	           ProcessPriority[i][2]=ProcessPriority[position][2];
	           ProcessPriority[position][2]=tmp;
	    
	           //Burst time of process
	           tmp=ProcessPriority[i][0];
	           ProcessPriority[i][0]=ProcessPriority[position][0];
	           ProcessPriority[position][0]=tmp;
	    
	           //arrival time
	           tmp=ProcessPriority[i][1];
	           ProcessPriority[i][1]=ProcessPriority[position][1];
	           ProcessPriority[position][1]=tmp;;
	    }
	    
	    Waiting_Time[0] = 0;
		
	    for(int i = 1; i < size;i++)
	    {
	    	Waiting_Time[0] = 0;
	    	for(int j = 0; j < i; j++)
            {
	    		Waiting_Time[i] = Waiting_Time[i] + ProcessPriority[j][0];
            }
            sum += Waiting_Time[i];
	    }
	    
	    Average_Waiting_Time = sum / size;
	    sum = 0;
	    
	    for(int i = 0; i < size; i++)
	      {
	    		//turning time = burst time + waiting time;
	    		Turning_Time[i] = ProcessPriority[i][0] + Waiting_Time[i];
	            sum = sum + Turning_Time[i];   
	      }
	    
	    Average_Turning_Time = sum / size;
	    
//		for(int x = 0; x < size;x++)
//		{
//			Waiting_Time[x] =((Waiting_Time[x]) - (ProcessPriority[x][1])) ;
//			Turning_Time[x] =((Waiting_Time[x]) - (ProcessPriority[x][1]) +(ProcessPriority[x][0])) ;
//			
//			Average_Waiting_Time += Average_Waiting_Time + Waiting_Time[x];
//			Average_Turning_Time += Average_Turning_Time + Turning_Time[x];
//		}
//		
//		Average_Waiting_Time = Average_Waiting_Time / size;
//		Average_Turning_Time = Average_Turning_Time / size;
		
	}

	/*
	 * Preemptive Priority Scheduling
	 * 
	 *  Waiting Time (Waiting time – Arrival Time)
	 *  Turning Time ((Waiting time – Arrival Time)+Burst Time)
	 *  
	 */
	public void priority() {
		
		int size = 3; //number of processes
		
		//[burst_time][arrival time][priority]
		int[][] ProcessPriority = new int[100][4];
		int[] Waiting_Time = new int[100];
		int[] Turning_Time = new int[100];
		int Average_Waiting_Time = 0;
		int Average_Turning_Time = 0;
		
		//
		
		int position;
		
		
		ProcessPriority[0][0] = 3; 
		ProcessPriority[0][1] = 0; 
		ProcessPriority[0][2] = 3; 
		
		ProcessPriority[1][0] = 2; 
		ProcessPriority[1][1] = 1; 
		ProcessPriority[1][2] = 2; 
		
		ProcessPriority[2][0] = 1; 
		ProcessPriority[2][1] = 2; 
		ProcessPriority[2][2] = 4; 
		
		ProcessPriority[3][0] = 1; 
		ProcessPriority[3][1] = 3; 
		ProcessPriority[3][2] = 2; 
		

		int tmp = 0;
	    for(int i=0;i<size;i++)
	    {
	    	   position=i;
	           for(int j=i+1;j<size;j++)
	           {
	               if(ProcessPriority[j][2]<ProcessPriority[position][2])
	                   position=j;
	           }
	    
	           //priority
	           tmp=ProcessPriority[i][2];
	           ProcessPriority[i][2]=ProcessPriority[position][2];
	           ProcessPriority[position][2]=tmp;
	    
	           //Burst time of process
	           tmp=ProcessPriority[i][0];
	           ProcessPriority[i][0]=ProcessPriority[position][0];
	           ProcessPriority[position][0]=tmp;
	    
	           //arrival time
	           tmp=ProcessPriority[i][1];
	           ProcessPriority[i][1]=ProcessPriority[position][1];
	           ProcessPriority[position][1]=tmp;;
	    }
	    
	    
		
		for(int x = 0; x < size;x++)
		{
			if(x == 0)
			{
				Waiting_Time[0] = 0;
			}
			Waiting_Time[x] =((Waiting_Time[x]) - (ProcessPriority[x][1])) ;
			Turning_Time[x] =((Waiting_Time[x]) - (ProcessPriority[x][1]) +(ProcessPriority[x][0])) ;
			
			Average_Waiting_Time += Average_Waiting_Time + Waiting_Time[x];
			Average_Turning_Time += Average_Turning_Time + Turning_Time[x];
		}
		
		Average_Waiting_Time = Average_Waiting_Time / size;
		Average_Turning_Time = Average_Turning_Time / size;
		
	    
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
				   
				   //runProcess(i,burst_times[i]); //process i is executed for the amount specified in the burst

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
				//runProcess(i,burst_times[i]); //process i is executed for 10ms
				
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
	public void multiQue(int timeUnit) {
		int i = 0;
		int store = 0;
		while (Process[i][0] != null) { // placing values into the schedule
										// first
			if ((i > 0) && (Process[i][0] == Process[i - 1][0]) && (Process[i][1] != "Process_Priority")) {
				store += Integer.parseInt(Process[i][2]);
			} else if (Process[i][0] != Process[i - 1][0]) {
				MultiQue[i][0] = Process[i - 1][0];
				MultiQue[i][1] = Integer.toString(store);
				store = Integer.parseInt(Process[i][2]);
			}
		}
	}

	/*
	 * Multilevel Feedback Queue Scheduling
	 */
	public void multiFeedQue() {

		/*
		 * Loop:
    IF process exists in blocking queue:
        Work on removing each process from blocking queue in FCFS basis
        When a process is removed, add it back to the highest priority cpu queue
    
    Move from top priority cpu queue to lowest cpu priority queue until we find a process
    IF a process is found:
        work on process until end of timeslice:
            do work
            IF the process becomes blocked:
                remove it from the cpu queue
                place it on the blocked queue
                restart the timeslice with a new process

        IF the end of the timeslice has been reached:
            remove the process from the top of the queue
            IF the process is not finished:
                IF process is already in lowest priority queue:
                    add process to back of queue
                ELSE:
                    add the process to the back of the next lower priority queue
		 * 
		 * 
		 * 
		 */

	}	

}
