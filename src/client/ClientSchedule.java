package client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tasks.ScheduleTasks;
import tasks.SharedSchedule;
import util.Job;
import util.Schedule;
import api.JobRunner;

public class ClientSchedule {
	
	
	
	
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		List<Job> jobs = new ArrayList<Job>();
		/*Random g = new Random();
		int m = g.nextInt(1) + 2;
		*/Schedule schedule = new Schedule(2);/*
		int r = 0;
		int j = 23;
		System.out.println("Number of jobs: " + j);
		System.out.println("Number of computers: " + m);
		for (int i = 1; i < j; i++) {
			//r = g.nextInt(20) + 1;
			jobs.add(new Job(i,i));
		}*/
		List<Integer> dep = new ArrayList<Integer>();
		dep.add(0);
		jobs.add(new Job(0, 2, null));
		jobs.add(new Job(1, 3, null));
		jobs.add(new Job(2, 2, dep));


		
		//makeLpt(schedule);
		
		new JobRunner<Schedule>("Bla", args).run(new ScheduleTasks(2, jobs, true), new SharedSchedule(null, Integer.MAX_VALUE));
	}

	private static SharedSchedule makeLpt(Schedule schedule, List<Job> jobs) {
		for(Job job: jobs){
			schedule.addJob(job);
		}
		System.out.println("UB" + schedule.getMaxLength());
		return new SharedSchedule(schedule, schedule.getMaxLength());
	}
}
