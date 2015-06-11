package test;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import tasks.ResultSchedule;
import tasks.ScheduleLptTasks;
import tasks.SharedSchedule;
import util.Job;
import util.Schedule;
import api.JobRunner;

public class LPTtest {
	

	@Test
	public void test() throws RemoteException, MalformedURLException, NotBoundException {
		
		List<Job> jobs = new ArrayList<Job>();
		Random g = new Random();
		//int m = g.nextInt(2) + 2;
		int m = 2;
		Schedule schedule = new Schedule(m);
		int r = 0;
		int j = 13;
//		System.out.println("Number of jobs: " + (j - 1));
		System.out.println("Number of computers: " + m);
		/*for (int i = 1; i <=6; i++) {
			r = g.nextInt(20) + 1;
			jobs.add(new Job(i,r));
		}*/
		jobs.add(new Job(1,3));
		jobs.add(new Job(2,3));
		jobs.add(new Job(4,2));
		jobs.add(new Job(5,2));
		jobs.add(new Job(3,2));
		SharedSchedule s = makeLpt(schedule, jobs, m);
		JobRunner<ResultSchedule> jr = new JobRunner<ResultSchedule>(new String[0]);
		ResultSchedule result = jr.run(new ScheduleLptTasks(m, jobs), s );
		assertEquals(result.cost(), 6);
		result = jr.run(new ScheduleLptTasks(m, jobs), s );
		assertEquals(result.cost(), 6);
		result = jr.run(new ScheduleLptTasks(m, jobs), s );

	}
	@Test
	public void test1(){
		assertEquals(1, 1);
	}
	
private static SharedSchedule makeLpt(Schedule schedule, List<Job> jobs, int m) {
		
		List<Job> joblist = new ArrayList<Job>(jobs);
		Collections.sort(joblist);
		int i = 0;
		for(Job job: joblist){
			i += job.getTime();
			schedule.addJob(job);
		}
		System.out.println("Upper bound: " + schedule.getMaxLength());
//		return new SharedSchedule(schedule, Integer.MAX_VALUE);
		// 4/3 - 1/3m
		if(schedule.getMaxLength() == i/(double)m) System.out.println("Lpt is optimal");
		return new SharedSchedule(schedule, schedule.getMaxLength());
	}

}
