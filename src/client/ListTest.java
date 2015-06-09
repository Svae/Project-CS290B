package client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tasks.ScheduleLptTasks;
import tasks.SharedSchedule;
import util.Job;
import util.Schedule;
import api.JobRunner;

public class ListTest {
	
	
	
	
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		List<Job> jobs = new ArrayList<Job>();
		int m = 2;
		Schedule schedule = new Schedule(2);
		int r = 0;
		int j = 10;
		System.out.println("Number of jobs: " + j);
		System.out.println("Number of computers: " + m);
		for (int i = 1; i < j; i++) {
			//r = g.nextInt(20) + 1;
			jobs.add(new Job(i,i));
		}
		
		//
		
		new JobRunner<Schedule>("Bla", args).run(new ScheduleLptTasks(m, jobs), makeLpt(schedule, jobs));
	}

	private static SharedSchedule makeLpt(Schedule schedule, List<Job> jobs) {
		for(Job job: jobs){
			schedule.addJob(job);
		}
		System.out.println("UB" + schedule.getMaxLength());
		return new SharedSchedule(schedule, schedule.getMaxLength());
	}
}
