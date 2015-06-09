package tasks;


import java.util.ArrayList;
import java.util.List;

import util.Job;
import util.Schedule;

public class LowerBoundLpt implements LowerBound{

	private double lowerBound;
	
	
	public LowerBoundLpt(int computers, List<Job> jobs) {
		int jobLength = getRemainingJobLength(jobs);
		lowerBound = ((double)jobLength)/computers;
		System.out.println("Lower bound: " + lowerBound);
	}

	
	
	public LowerBoundLpt(ScheduleLptTasks parent, Job newJob, int id) {
		List<Job> jobs = parent.getJobs();
		Schedule schedule = parent.getSchedule();
		schedule.addJob(id, newJob);
		int max = schedule.getMaxLength();
		int remaining = getRemainingJobLength(jobs);
		List<Integer> schedules = schedule.getAllJobLengths();
		int diff = 0;
		for (Integer len : schedules) {
			diff += max - len;
		}
		
		if(remaining<diff) lowerBound = max;
		else lowerBound = max + (remaining-diff)/(double)parent.getNumberOfComputers();
		
	}

	
	private int getRemainingJobLength(List<Job> jobs) {
		int jobLength = 0;
		for (Job job : jobs) {
			jobLength += job.getTime();
		}
		return jobLength;
	}
	

	@Override
	public double cost() {
		return lowerBound;
	}



	@Override
	public LowerBound make(ScheduleLptTasks parent, Job newJob, int id) {
		return new LowerBoundLpt(parent, newJob, id);
	}



	

}
