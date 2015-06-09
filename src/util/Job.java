package util;

import java.util.List;

public class Job {

	private int id;
	private int time;
	private List<Integer> dependences;
	private int start;
	private int stop;
	
	public Job(int id, int time) {
		this.id = id;
		this.time = time;
	}
	
	public Job(int id, int time, List<Integer> dependences){
		this.id = id;
		this.time = time;
		this.dependences = dependences;
	}

	public int getId() {
		return id;
	}

	public int getTime() {
		return time;
	}
	
	public List<Integer> getDependences(){
		return dependences;
	}
	
	public boolean hasDependences(){
		return dependences != null && !dependences.isEmpty();
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getStop() {
		return stop;
	}

	public void setStop(int stop) {
		this.stop = stop;
	}
	
	public void addProccessTime(int extra){
		time +=extra;
	}
	
}
