package tasks;

import java.util.List;

import util.Job;
import util.Schedule;

public class LowerBoundSimple implements LowerBound{

	private double lowerBound; 
	
	public LowerBoundSimple(int computers, List<Job> jobs) {
		int jobLength = getRemainingJobLength(jobs);
		lowerBound = ((double)jobLength)/computers;
	}
	
	public LowerBoundSimple(ScheduleLptTasks parent, Job newJob, int id) {
			Schedule schedule = parent.getSchedule();
			schedule.addJob(id, newJob);
			lowerBound = schedule.getMaxLength();
	}


	@Override
	public LowerBoundSimple make(ScheduleLptTasks parent, Job newJob, int id) {
		return new LowerBoundSimple(parent, newJob, id);
	}

	@Override
	public double cost() {
		// TODO Auto-generated method stub
		return lowerBound;
	}
	
	
	private int getRemainingJobLength(List<Job> jobs) {
		int jobLength = 0;
		for (Job job : jobs) {
			jobLength += job.getTime();
		}
		return jobLength;
	}
	

}
