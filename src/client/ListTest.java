package client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tasks.ResultSchedule;
import tasks.ScheduleListTasks;
import tasks.ScheduleLptTasks;
import tasks.SharedSchedule;
import util.Job;
import util.Schedule;
import util.UpperBound;
import api.JobRunner;

public class ListTest {
	
	
	static List<Job> jobs;
	
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		jobs = new ArrayList<Job>();
		generateSchedule();
		int m = 3;
		Schedule schedule = new Schedule(m);
		
		SharedSchedule s = UpperBound.makeListBound(schedule, jobs);
		schedule = new Schedule(m);
		System.out.println("Upperbound: " + s.cost());
		ResultSchedule result = new JobRunner<ResultSchedule>(args).run(new ScheduleListTasks(m, jobs), s);
		System.out.println(result.toString());
	}
	
	private static void generateSchedule(){
		List<Integer> dep = new ArrayList<Integer>();
		dep.add(3);
		
		
		List<Integer> dep1 = new ArrayList<Integer>();
		dep1.add(1);
		dep1.add(2);
		dep1.add(4);
		
		List<Integer> dep2 = new ArrayList<Integer>();
		dep2.add(3);
		dep2.add(4);
		dep2.add(7);



		/*
		jobs.add(new Job(1, 1));
		jobs.add(new Job(2, 1));
		jobs.add(new Job(6, 8));
		//1
		jobs.add(new Job(3, 10));
		//3
		jobs.add(new Job(4, 10, dep));
		//4
		jobs.add(new Job(5, 1, dep1));
		
		jobs.add(new Job(7, 1, dep));
		jobs.add(new Job(8, 8, dep2));
		//3,4,7
		jobs.add(new Job(9, 8, dep2));
		*/
		
		jobs.add(new Job(1,5));
		jobs.add(new Job(2,3));
		jobs.add(new Job(3,4));
		jobs.add(new Job(4,3));
		jobs.add(new Job(5,5, dep1));
		jobs.add(new Job(6,5, dep1));
		jobs.add(new Job(7,3, dep1));
		jobs.add(new Job(8,3, dep1));
		jobs.add(new Job(9,8, dep));

	}

}
