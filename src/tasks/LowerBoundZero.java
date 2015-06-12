package tasks;

import java.util.List;

import util.Job;
import util.ScheduleUtil;

public class LowerBoundZero implements LowerBound {
	
	/**
	 * Currently not working
	 */
	
	private double lowerBound; 
	
	public LowerBoundZero(Integer computers, List<Job> jobs) {
		lowerBound = ScheduleUtil.getRemainingJobLength(jobs)/(double)computers;
	}
	
	public LowerBoundZero() {
		lowerBound = 0;
	}

	@Override
	public double cost() {
		return lowerBound;
	}

	@Override
	public LowerBound make(ScheduleTasks parent, Job newJob, int id) {
		return new LowerBoundZero();
	}
	

}
