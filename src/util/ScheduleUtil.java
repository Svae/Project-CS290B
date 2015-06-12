package util;

import java.io.Serializable;
import java.util.List;

public class ScheduleUtil implements Serializable{
	/**
	 * Finds an available job in a job list
	 * 
	 * @param jobs list of jobs
	 * @param schedule partial schedule
	 * @return
	 */
	
	
	public static Job findAvailableJob(List<Job>jobs, Schedule schedule) {
		Job temp = null;
		for(Job job : jobs){
			if(job.hasDependences()){
				temp = checkDependences(job, schedule);
				if(temp != null){
					job.setStart(temp.getStop());
					return job;
				}
			} else {
				return job;
			}
		}
		return null;
	}
	
	/**
	 * Checks if the dependences for a task has been scheduled and changes 
	 * job start time to the earliest time it can be scheduled
	 * @param job the job of which the dependences will be checked
	 * @param schedule the partial schedule that will be used to check dependences
	 * @return the job
	 */
	
	public static Job checkDependences(Job job, Schedule schedule){
		Job temp = null;
		Job max = null;
		for(Integer id : job.getDependences()){
			temp = schedule.containsJob(id);
			if(temp != null){
				if(max == null) max = temp;
				else if(max.getStop() < temp.getStop()) max = temp;
			} else return null;
		}
		return max;
	}
	
	/**
     * Finds the total job time for the remaining jobs
     * 
     * @param jobs list of remaining jobs
     * @return remaining job time
     */
	public static int getRemainingJobLength(List<Job> jobs) {
		int jobLength = 0;
		for (Job job : jobs) {
			jobLength += job.getTime();
		}
		return jobLength;
	}
	
	/**
     * Finds the difference between the computer with 
     * the longest completion time and the other computers
     * 
     * @param schedule schedule containing each computers job list
     * @param max the completion time of one schedule
     * @return the minimal completion time for list of jobs
     */
	
	public static int getDifference(Schedule schedule, int max) {
		int diff = 0;
		for (Integer len : schedule.getAllJobLengths()) {
			diff += max - len;
		}
		return diff;
	}
	
}
