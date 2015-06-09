package tasks;

import java.util.List;

import util.Job;
import util.Schedule;

public class LowerBoundSimple implements LowerBound{

	private double lowerBound;
	
	
	public LowerBoundSimple(int computers, List<Job> jobs) {
		int jobLength = 0;
		for (Job job : jobs) {
			jobLength += job.getTime();
		}
		lowerBound = ((double)jobLength)/computers;
		System.out.println("Lower bound: " + lowerBound);
	}
	
	public LowerBoundSimple(ScheduleLptTasks parent, Job newJob, int id) {
			Schedule schedule = parent.getSchedule();
			schedule.addJob(id, newJob);
			lowerBound = schedule.getMaxLength();
	}


	@Override
	public double cost() {
		return lowerBound;
	}

	@Override
	public LowerBound make(ScheduleLptTasks parent, Job newJob, int id) {
		return new LowerBoundSimple(parent, newJob, id);
	}

}
