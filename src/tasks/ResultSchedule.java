package tasks;


import java.io.Serializable;

import util.Schedule;

public class ResultSchedule implements Serializable, Comparable<ResultSchedule> {

	private final Schedule schedule;
	private final int cost;
	
	public ResultSchedule(final Schedule schedule, final int cost) {
		this.schedule = schedule;
		this.cost = cost;
	}
	
	
	public Schedule schedule(){ return schedule; }
	
	public int cost(){ return cost; }


	@Override
	public int compareTo(ResultSchedule o) {
		return 0;
	}

}
