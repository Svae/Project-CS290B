package tasks;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;

import util.JobList;
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		HashMap<Integer, JobList> l = schedule.getSchedule();
		for (Entry<Integer, JobList> list : l.entrySet() ) {
			sb.append(list.getKey() + ": " + list.getValue().toString() + "\n");
		}
		return sb.toString();
	}
}
