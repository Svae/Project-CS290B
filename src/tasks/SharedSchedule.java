package tasks;

import java.util.HashMap;
import java.util.Map.Entry;

import util.JobList;
import util.Schedule;
import api.Shared;

public class SharedSchedule extends Shared<SharedSchedule> {

	private final Schedule schedule;
	private final int cost;
	
	public SharedSchedule(final Schedule schedule, final int cost) {
		this.schedule = schedule;
		this.cost = cost;
	}
	
	
	@Override
	public boolean isOlderThan(SharedSchedule that) {
		return cost > that.cost();
	}
	
	public Schedule schedule(){ return schedule; }
	
	public int cost(){ return cost; }
	
	
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
