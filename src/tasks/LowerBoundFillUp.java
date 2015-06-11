package tasks;


import java.io.Serializable;
import java.util.List;

import util.Job;
import util.Schedule;

public class LowerBoundFillUp implements LowerBound, Serializable{

	private double lowerBound;
	
	
	public LowerBoundFillUp(int computers, List<Job> jobs) {
		lowerBound = getRemainingJobLength(jobs)/(double)computers;
	}

	
	
	public LowerBoundFillUp(ScheduleLptTasks parent, Job newJob, int id) {
		List<Job> jobs = parent.getJobs();
		Schedule schedule = parent.getSchedule();
		schedule.addJob(id, newJob);
		int max = schedule.getMaxLength();
		int remaining = getRemainingJobLength(jobs);
		int diff = getDifference(schedule, max);
		if(remaining<diff) lowerBound = max;
		else lowerBound = max + ((remaining-diff)/(double)parent.getNumberOfComputers()); 
		
	}



	private int getDifference(Schedule schedule, int max) {
		int diff = 0;
		for (Integer len : schedule.getAllJobLengths()) {
			diff += max - len;
		}
		return diff;
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
		return new LowerBoundFillUp(parent, newJob, id);
	}



	

}
