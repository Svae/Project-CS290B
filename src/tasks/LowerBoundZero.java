package tasks;

import util.Job;

public class LowerBoundZero implements LowerBound {
	
	
	@Override
	public double cost() {
		return 0;
	}

	@Override
	public LowerBound make(ScheduleLptTasks parent, Job newJob, int id) {
		return new LowerBoundZero();
	}
	

}
