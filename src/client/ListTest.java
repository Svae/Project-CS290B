package client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tasks.ScheduleListTasks;
import tasks.ScheduleLptTasks;
import tasks.SharedSchedule;
import util.Job;
import util.Schedule;
import api.JobRunner;

public class ListTest {
	
	
	static List<Job> jobs;
	
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		jobs = new ArrayList<Job>();
		generateSchedule();
		int m = 3;
		new JobRunner<Schedule>("Bla", args).run(new ScheduleListTasks(m, jobs), new SharedSchedule(null, Integer.MAX_VALUE));
	}
	
	private static void generateSchedule(){
		List<Integer> dep = new ArrayList<Integer>();
		dep.add(1);
		List<Integer> dep1 = new ArrayList<Integer>();
		dep1.add(4);
		
		List<Integer> dep2 = new ArrayList<Integer>();
		dep2.add(1);
		dep2.add(4);



		
		jobs.add(new Job(1, 1));
		jobs.add(new Job(2, 6));
		jobs.add(new Job(6, 8));
		//1
		jobs.add(new Job(3, 10, dep));
		jobs.add(new Job(4, 1));
		jobs.add(new Job(5, 4, dep1));
		/*
		jobs.add(new Job(1,5));
		jobs.add(new Job(2,3));
		jobs.add(new Job(3,4));
		jobs.add(new Job(4,3));
		jobs.add(new Job(5,5, dep1));
		jobs.add(new Job(6,5, dep1));
		jobs.add(new Job(7,3, dep1));
		jobs.add(new Job(8,3, dep1));
		jobs.add(new Job(9,8, dep));*/

	}

}
