package tasks;

import java.util.List;

import util.Job;
import util.Schedule;

public class LowerBoundSimpleConstraints implements LowerBound{

	private double lowerBound;
	
	
	public LowerBoundSimpleConstraints(int computers, List<Job> jobs) {
		int jobLength = 0;
		for (Job job : jobs) {
			jobLength += job.getTime();
		}
		lowerBound = ((double)jobLength)/computers;
	}
	
	public LowerBoundSimpleConstraints(Schedule parent, Job newJob, int id, boolean constraints) {
			int diff = newJob.getStart() - parent.getListMax(id);
			newJob.addProccessTime(diff);
			parent.addJob(id, newJob);
			lowerBound = parent.getMaxLength();
	}

	

	@Override
	public double cost() {
		return lowerBound;
	}

	@Override
	public LowerBound make(Schedule parent, Job newJob, int id, boolean constraints) {
		return new LowerBoundSimpleConstraints(parent, newJob, id, constraints);
	}

}
