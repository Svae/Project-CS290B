package tasks;

import java.io.Serializable;
import java.util.List;

import util.Job;
import util.Schedule;

public class LowerBoundConstraints implements LowerBoundList, Serializable{

	private double lowerBound;
	
	
	public LowerBoundConstraints(int computers, List<Job> jobs) {
		int jobLength = 0;
		for (Job job : jobs) {
			jobLength += job.getTime();
		}
		lowerBound = ((double)jobLength)/computers;
		System.out.println("Lower bound: " + lowerBound);
	}
	
	public LowerBoundConstraints(ScheduleListTasks parent, Job newJob, int id) {
			Schedule schedule = parent.getSchedule();
			if(newJob.hasDependences()){
				if(newJob.getStart() > schedule.getListMax(id)){
					int diff = newJob.getStart() - schedule.getListMax(id);
					newJob.addProccessTime(diff);
				}
			}	
			List<Job> jobs = parent.getJobs();
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
			lowerBound = schedule.getMaxLength();
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
	public LowerBoundList make(ScheduleListTasks parent, Job newJob, int id) {
		return new LowerBoundConstraints(parent, newJob, id);
	}

}
