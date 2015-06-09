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
	}
	
	public LowerBoundSimple(Schedule parent, Job newJob, int id, boolean constraints) {
			parent.addJob(id, newJob);
			lowerBound = parent.getMaxLength();
	}

	

	@Override
	public double cost() {
		return lowerBound;
	}

	@Override
	public LowerBound make(Schedule parent, Job newJob, int id, boolean constraints) {
		return new LowerBoundSimple(parent, newJob, id, constraints);
	}

}
