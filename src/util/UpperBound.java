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
	
	public static SharedSchedule makeListBound(Schedule schedule, List<Job> jobs, int m){
		List<Job> joblist = new ArrayList<Job>(jobs);
		Job temp = null;
		while(!joblist.isEmpty()){
			temp = ScheduleUtil.findAvailableJob(jobs, schedule);
			jobs.remove(temp);
			schedule.addJob(temp);
		}
		return new SharedSchedule(schedule, schedule.getMaxLength());
	}
}
