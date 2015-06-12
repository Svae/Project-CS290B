package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tasks.SharedSchedule;

public class UpperBound {

	/**
     * Compute the LPT approximation for a given task list and a given 
     * number of computers. The approximation is bounded by Fapprox/Foptimal = 4/3 - 1/3m
     *  
     * @param schedule schedule that is used in the computation
     * @param jobs list of jobs to be scheduled
     * @return A SharedSchedule containing the LPT approx. schedule
     */
	
	public static SharedSchedule makeLPTBound(Schedule schedule, List<Job> jobs) {
		
		List<Job> joblist = new ArrayList<Job>(jobs);
		Collections.sort(joblist);
		for(Job job: joblist){
			schedule.addJob(job);
		}
		return new SharedSchedule(schedule, schedule.getMaxLength());
	}
	
	/**
     * Compute the LPT approximation for a given task list and a given 
     * number of computers.
     * 
     * @return A SharedSchedule with cost Integer.MAX_VALUE
     */
	public static SharedSchedule makeInfBound(){
		return new SharedSchedule(null, Integer.MAX_VALUE);
	}
	
	
	/**
    * Compute the List approximation for a given task list and a given 
    * number of computers. The approximation is bounded by Fapprox/Foptimal = 2 - 1/m
    *  
    * @param schedule schedule that is used in the computation
    * @param jobs list of jobs to be scheduled
    * @return A SharedSchedule containing the List approx. schedule
    */
	
	public static SharedSchedule makeListBound(Schedule schedule, List<Job> jobs){
		
		List<Job> joblist = new ArrayList<Job>(jobs);
		Job newJob = null;
		int minId;
		while(joblist.size() >0){
			newJob = ScheduleUtil.findAvailableJob(joblist, schedule);
			minId = schedule.firstDone();
			if(newJob.hasDependences()){
				if(newJob.getStart() > schedule.getListMax(minId)){
					int diff = newJob.getStart() - schedule.getListMax(minId);
					schedule.addJob(minId, new Job(0,diff));

				}
			}
			joblist.remove(newJob);
			schedule.addJob(minId, newJob);
		}
		return new SharedSchedule(schedule, schedule.getMaxLength());
	}
	
}
