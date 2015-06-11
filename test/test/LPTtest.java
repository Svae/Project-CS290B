package test;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tasks.ResultSchedule;
import tasks.ScheduleLptTasks;
import tasks.ScheduleListTasks;
import tasks.SharedSchedule;
import util.Job;
import util.Schedule;
import util.UpperBound;
import api.JobRunner;

public class LPTtest {
	
	private JobRunner<ResultSchedule> jr;
	
	@Before
	public void setUp() throws RemoteException, MalformedURLException, NotBoundException{
		String[] args = {};  //{"localhost"};
		jr = new JobRunner<ResultSchedule>(args);
	}
	
	@Test
	public void test() throws RemoteException, MalformedURLException, NotBoundException {
		
		List<Job> jobs = new ArrayList<Job>();
		int m = 2;
		Schedule schedule = new Schedule(m);
		
		jobs.add(new Job(1,3));
		jobs.add(new Job(2,3));
		jobs.add(new Job(4,2));
		jobs.add(new Job(5,2));
		jobs.add(new Job(3,2));
		jobs.add(new Job(7,3));
		jobs.add(new Job(8,3));

		SharedSchedule upperbound = UpperBound.makeLPTBound(schedule, jobs, m);
		assertEquals(10, upperbound.cost());
		ResultSchedule result = jr.run(new ScheduleLptTasks(m, jobs), upperbound );
		assertEquals(result.cost(), 9);
		
	}
	
	@Test
	public void test3() throws RemoteException, MalformedURLException, NotBoundException {
		
		List<Job> jobs = new ArrayList<Job>();
		int m = 2;
		Schedule schedule = new Schedule(m);
		
		jobs.add(new Job(1,3));
		jobs.add(new Job(2,5));
		jobs.add(new Job(4,1));
		jobs.add(new Job(5,7));
		jobs.add(new Job(3,7));
		jobs.add(new Job(6,8));
		jobs.add(new Job(7,4));
		jobs.add(new Job(8,2));
		jobs.add(new Job(9,2));
		
		SharedSchedule upperbound = UpperBound.makeLPTBound(schedule, jobs, m);
		assertEquals(20, upperbound.cost());
		ResultSchedule result = jr.run(new ScheduleLptTasks(m, jobs), upperbound );
		assertEquals(result.cost(), 20);
		

	}

	
	@Test
	public void randomSchedule() throws RemoteException{
		List<Job> jobs = new ArrayList<Job>();
		Random g = new Random();
		int m = g.nextInt(2) + 2;
		int r;
		int j = 13;
		int k = 0;
		System.out.println("Number of jobs: " + j);
		System.out.println("Number of computers: " + m);
		SharedSchedule upperbound;
		Schedule schedule;
			k = 0;
			schedule = new Schedule(m);
			for (int i = 1; i <=j; i++) {
				r = g.nextInt(20) + 1;
				jobs.add(new Job(i,r));
				k += r;
			}
			upperbound = UpperBound.makeLPTBound(schedule, jobs, m);

		double lb = (k/(double)m);
		System.out.println("Upperbound: " + upperbound.cost());

		System.out.println("Lowerbound: " + lb );
		ResultSchedule result = jr.run(new ScheduleLptTasks(m, jobs), upperbound);
		System.out.println("Result cost: " + result.cost());
		assertTrue(upperbound.cost()>=result.cost());
		assertTrue(lb <= result.cost());
		
	}
	@Test
	public void listTest() throws RemoteException, MalformedURLException, NotBoundException {
		
		List<Job> jobs = new ArrayList<Job>();
		int m = 2;
		Schedule schedule = new Schedule(m);
		
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
		
		jobs.add(new Job(1,3));
		jobs.add(new Job(2,1));
		jobs.add(new Job(3,4));
		jobs.add(new Job(4,3));
		jobs.add(new Job(5,5, dep1));
		jobs.add(new Job(6,5, dep1));
		jobs.add(new Job(7,3, dep1));
		jobs.add(new Job(8,3, dep1));
		jobs.add(new Job(9,8, dep));
		
		SharedSchedule upperbound = UpperBound.makeLPTBound(schedule, jobs, m);
		assertEquals(18, upperbound.cost());
		ResultSchedule result = jr.run(new ScheduleListTasks(m, jobs), upperbound );
		assertEquals(result.cost(), 18);
		result = jr.run(new ScheduleListTasks(m, jobs), upperbound );
		

	}	
	
	@After
	public void tearDown(){
		jr = null;
	}
	
}
