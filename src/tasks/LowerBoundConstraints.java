package tasks;

import java.io.Serializable;
import java.util.List;

import util.Job;
import util.Schedule;
import util.ScheduleUtil;

public class LowerBoundConstraints implements LowerBoundList, Serializable{

	private double lowerBound;
	
	/**
     * Compute a lower bound for a given task list and a given 
     * number of computers.
     *  
     * @param computers number of computers
     * @param jobs list of jobs to be scheduled
     * @return the minimal completion time for list of jobs
     */
	
	public LowerBoundConstraints(int computers, List<Job> jobs) {
		
		lowerBound = ScheduleUtil.getRemainingJobLength(jobs)/(double)computers;
	}
	
	
	/**
     * Compute a lower bound for a given partial schedule and a job that
     * is being added to the schedule. It divides the remaining job time among the computers
     * This lower bound is tighter then LowerBoundSimpleConstraints
     *  
     * @param parent the task for which the lower bound is computed for
     * @param newJob the next job that is being scheduled
     * @param id the id of the computer for which newJob is being scheduled to
     * @return the minimal completion time for this partial schedule
     */
	
	public LowerBoundConstraints(ScheduleListTasks parent, Job newJob, int id) {
			Schedule schedule = parent.getSchedule();
			if(newJob.hasDependences()){
				if(newJob.getStart() > schedule.getListMax(id)){
					int diff = newJob.getStart() - schedule.getListMax(id);
					schedule.addJob(id, new Job(0,diff));
				}
			}	
			schedule.addJob(id, newJob);
			
			
			List<Job> jobs = parent.getJobs();
			int max = schedule.getMaxLength();
			int remaining = ScheduleUtil.getRemainingJobLength(jobs);
			int diff = ScheduleUtil.getDifference(schedule, max);
			
			if(remaining<diff) lowerBound = max;
			else lowerBound = max + (remaining-diff)/(double)parent.getNumberOfComputers();
	}
	
	@Override
	public double cost() {
		return lowerBound;
	}

	@Override
	public LowerBoundList make(ScheduleListTasks parent, Job newJob, int id) {
		return new LowerBoundConstraints(parent, newJob, id);
	}

}
