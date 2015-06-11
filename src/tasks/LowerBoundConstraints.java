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
					//TODO: Add blank job
					schedule.addJob(id, new Job(0,diff));
//					newJob.addProccessTime(diff);
				}
			}	
			schedule.addJob(id, newJob);
			
			
			List<Job> jobs = parent.getJobs();
			int max = schedule.getMaxLength();
			int remaining = getRemainingJobLength(jobs);
			int diff = getDifference(schedule, max);
			
			if(remaining<diff) lowerBound = max;
			else lowerBound = max + (remaining-diff)/(double)parent.getNumberOfComputers();
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
	public LowerBoundList make(ScheduleListTasks parent, Job newJob, int id) {
		return new LowerBoundConstraints(parent, newJob, id);
	}

}
