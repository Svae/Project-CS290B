package client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import tasks.ScheduleLptTasks;
import tasks.SharedSchedule;
import util.Job;
import util.Schedule;
import api.JobRunner;

public class LptTest {
	
	
	
	
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		List<Job> jobs = new ArrayList<Job>();
		Random g = new Random();
		//int m = g.nextInt(2) + 2;
		int m = 3;
		Schedule schedule = new Schedule(m);
		int r = 0;
		int j = 13;
		System.out.println("Number of jobs: " + (j - 1));
		System.out.println("Number of computers: " + m);
		for (int i = 1; i <= 13; i++) {
			r = g.nextInt(20) + 1;
			jobs.add(new Job(i,r));
		}
				
		new JobRunner<Schedule>("Bla", args).run(new ScheduleLptTasks(m, jobs), makeLpt(schedule, jobs));

	}

	private static SharedSchedule makeLpt(Schedule schedule, List<Job> jobs) {
		
		List<Job> joblist = new ArrayList<Job>(jobs);
		Collections.sort(joblist);
		for(Job job: joblist){
			schedule.addJob(job);
		}
		
		System.out.println("Upper bound: " + schedule.getMaxLength());
//		return new SharedSchedule(schedule, Integer.MAX_VALUE);
		// 4/3 - 1/3m
		return new SharedSchedule(schedule, schedule.getMaxLength());
	}
}
