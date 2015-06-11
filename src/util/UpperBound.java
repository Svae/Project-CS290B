package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tasks.SharedSchedule;

public class UpperBound {

	// 4/3 - 1/3m
	public static SharedSchedule makeLPTBound(Schedule schedule, List<Job> jobs, int m) {
		
		List<Job> joblist = new ArrayList<Job>(jobs);
		Collections.sort(joblist);
		int i = 0;
		for(Job job: joblist){
			i += job.getTime();
			schedule.addJob(job);
		}
		return new SharedSchedule(schedule, schedule.getMaxLength());
	}
	
	public static SharedSchedule makeInfBound(){
		return new SharedSchedule(null, Integer.MAX_VALUE);
	}
	
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
