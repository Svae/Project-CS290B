package client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import tasks.ResultSchedule;
import tasks.ScheduleTasks;
import tasks.SharedSchedule;
import util.Job;
import util.Schedule;
import util.UpperBound;
import api.JobRunner;

public class ClientSchedule {
	
	
	
	
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		List<Job> jobs = new ArrayList<Job>();
		Random g = new Random(2);
		int m = 3;
		int r;
		int j = 14;
		int k = 0;
		System.out.println("Number of jobs: " + j);
		System.out.println("Number of computers: " + m);
		Schedule schedule;
		schedule = new Schedule(m);
		for (int i = 1; i <=j; i++) {
			r = g.nextInt(20) + 1;
			jobs.add(new Job(i,r));
			k += r;
		}
		// En med UB og uten, fill up som lb
//		SharedSchedule s = UpperBound.makeLPTBound(schedule, jobs, m);
		SharedSchedule s = new SharedSchedule(schedule, Integer.MAX_VALUE);
		System.out.println("Upperbound: " + s.cost());
		System.out.println("Total job time / number of computers: " + (k/(double)m));
		JobRunner<ResultSchedule> jr = new JobRunner<ResultSchedule>(args);
		ResultSchedule result = jr.run(new ScheduleTasks(m, jobs), s);
		System.out.println(result);
		//1. fill up, simple, zero lb
		//
		
	}

	
	
}
