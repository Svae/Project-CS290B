package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JobList implements Serializable{

	private List<Job> jobs;
	private int maxLength;
	private boolean constraints;

	public JobList(){
		jobs = new ArrayList<Job>();
		maxLength = 0;
		this.constraints = false;
	}
	
	public JobList(boolean constraints){
		jobs = new ArrayList<Job>();
		maxLength = 0;
		this.constraints = constraints;
	}
	
	public JobList(JobList value) {
		this.jobs = new ArrayList<Job>(value.getJobs());
		this.maxLength = value.getMaxLength();
	}

	
	public void addJob(Job j){
		j.setStart(maxLength);
		jobs.add(j);
		maxLength += j.getTime();
		j.setStop(maxLength);
	}
	
	public int getMaxLength(){
		return maxLength;
	}
	
	public List<Job> getJobs(){
		return jobs;
	}
	
	public boolean hasConstraints(){
		return constraints;
	}
	
	@Override
	public String toString(){
		if(!jobs.isEmpty()){
			StringBuilder sb = new StringBuilder();
			for (Job job : jobs) {
				sb.append("(" + job.getId() + "," + job.getTime()+"),");
			}
			sb.setLength(sb.length()-1);
			sb.append(" : " + maxLength);
			return sb.toString();
		}else return " : 0";
	}
	
	public Job containsJob(int id){
		for(Job j: jobs){
			if(j.getId() == id) return j;
		}
		return null;
	}
}
