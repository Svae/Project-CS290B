package tasks;

import java.util.List;

import util.Job;

public class LowerBoundZero implements LowerBound {
	
	
	public LowerBoundZero(Integer computers, List<Job> jobs) {}
	
	@Override
	public double cost() {
		return 0;
	}

	@Override
	public LowerBound make(ScheduleLptTasks parent, Job newJob, int id) {
		return new LowerBoundZero(id, null);
	}
	

}
