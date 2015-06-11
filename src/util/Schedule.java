package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Schedule implements Serializable{
	private HashMap<Integer, JobList> schedule;

	private int maxLength;
	
	private int computers;
	private int numberOfJobs;
	
	public Schedule(int computers) {
		this.computers = computers;
		numberOfJobs = 0;
		maxLength = 0;
		schedule = new HashMap<Integer, JobList>();
		init();
	}
	
	public Schedule(int computers, HashMap schedule, int maxLength){
		this.computers = computers;
		this.schedule = schedule;
		this.maxLength = maxLength;
	}
	
	public Schedule(HashMap schedule, int maxLength){
		this.schedule = schedule;
		this.maxLength = maxLength;
	}
	
	
	
	
	private void init() {
		for (int i = 1; i <= computers; i++) {
			schedule.put(i, new JobList());
		}
	}


	public void addJob(int m, Job job){
		JobList list = schedule.get(m);
		list.addJob(job);
		if(list.getMaxLength() > maxLength){
			maxLength = list.getMaxLength();
		}
		numberOfJobs++;
	}
	
	public void addJob(Job job){
		
		addJob(firstDone(), job);	
	}
	
	public int getMaxLength(){
		return maxLength;
	}
	
	public List<Integer> getAllJobLengths(){
		List<Integer> lengths = new ArrayList<Integer>();
		for (JobList list : schedule.values()) {
			lengths.add(list.getMaxLength());
		}
		return lengths;
	}


	public int getNumberOfJobs() {
		return numberOfJobs;
	}
	
	public int getListMax(int id){
		return schedule.get(id).getMaxLength();
	}
	
	public HashMap<Integer, JobList> getSchedule(){
		return schedule;
	}
	
	public Job containsJob(int id){
		Job j;
		for (Entry<Integer, JobList> l : schedule.entrySet()) {
			j = l.getValue().containsJob(id);
			if( j != null){
				return j;
			}
		}
		return null;
	}
	
	public Integer firstDone(){
		int minLength = Integer.MAX_VALUE;
		int minId = -1;
		for (Map.Entry<Integer, JobList> entry : schedule.entrySet()) {
			if(entry.getValue().getMaxLength() < minLength){
				minLength = entry.getValue().getMaxLength();
				minId = entry.getKey();
			}
		}
		return minId;
	}
	
	@Override
	public String toString() {
    	StringBuilder sb = new StringBuilder();
		for (Map.Entry<Integer, JobList> entry : this.schedule.entrySet()) {
			sb.append(entry.getKey() + ": " + entry.getValue()+ "\n");
		}
		sb.append("-------------------------");
		return sb.toString();
	}
}
