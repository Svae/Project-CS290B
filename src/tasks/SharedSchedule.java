package tasks;

import java.util.HashMap;

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
		String s = "";
		HashMap<Integer, JobList> l = schedule.getSchedule();
		int i = 1;
		for (JobList list : l.values() ) {
			s += i + ": " + list.prettyprint() + "\n";
		}
		return s;
	}

}
