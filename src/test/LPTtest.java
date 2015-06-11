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
import tasks.SharedSchedule;
import util.Job;
import util.Schedule;
import util.UpperBound;
import api.JobRunner;

public class LPTtest {
	
	private JobRunner<ResultSchedule> jr;
	
	@Before
	public void setUp() throws RemoteException, MalformedURLException, NotBoundException{
		String[] args = {"localhost"};
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
		
		SharedSchedule upperbound = UpperBound.makeLPTBound(schedule, jobs, m);
		assertNotEquals(6, upperbound.cost());
		ResultSchedule result = jr.run(new ScheduleLptTasks(m, jobs), upperbound );
		assertEquals(result.cost(), 6);
		result = jr.run(new ScheduleLptTasks(m, jobs), upperbound );
		assertEquals(result.cost(), 6);
		result = jr.run(new ScheduleLptTasks(m, jobs), upperbound );

	}
	@Test
	public void test1(){
		assertEquals(1, 1);
	}
	
	@After
	public void tearDown(){
		jr = null;
	}
	
}
