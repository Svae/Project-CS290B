package client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import tasks.ResultSchedule;
import tasks.ScheduleLptTasks;
import tasks.SharedSchedule;
import util.Job;
import util.Schedule;
import util.UpperBound;
import api.JobRunner;

public class LptTest {
	
	
	
	
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		List<Job> jobs = new ArrayList<Job>();
		Random g = new Random();
		//int m = g.nextInt(2) + 2;
		int m = 3;
		int r = 0;
		int j = 13;
		int k = 0;
//		System.out.println("Number of jobs: " + (j - 1));
		System.out.println("Number of computers: " + m);
		SharedSchedule s;
		Schedule schedule;
			k = 0;
			schedule = new Schedule(m);
			for (int i = 1; i <=18; i++) {
				r = g.nextInt(20) + 1;
				jobs.add(new Job(i,r));
				k += r;
			}
			s = UpperBound.makeLPTBound(schedule, jobs, m);

		
//		jobs.add(new Job(1,4));
//		jobs.add(new Job(2,4));
//		jobs.add(new Job(3,3));
//		jobs.add(new Job(4,2));
//		jobs.add(new Job(5,3));
//		jobs.add(new Job(6,4));

		
		System.out.println("Upperbound: " + s.cost());

		System.out.println("Lowerbound: " + (k/(double)m));
		JobRunner<ResultSchedule> jr = new JobRunner<ResultSchedule>(args);
		ResultSchedule result = jr.run(new ScheduleLptTasks(m, jobs), s);
		System.out.println(result);
		result = jr.run(new ScheduleLptTasks(m, jobs), s);
		System.out.println(result);
	}

	
	
}
