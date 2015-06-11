package tasks;

import java.io.Serializable;
import java.util.List;

import util.Job;

public abstract class LowerboundSchedule<T> implements LowerBound, Serializable{
	
	private double lowerBound;
	
	public LowerboundSchedule(int computers, List<Job> jobs) {
		int jobLength = 0;
		for (Job job : jobs) {
			jobLength += job.getTime();
		}
		lowerBound = ((double)jobLength)/computers;
	}
	
	@Override
	public double cost() {
		return lowerBound;
	}

	
}
