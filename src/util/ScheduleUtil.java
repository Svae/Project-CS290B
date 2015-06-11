package util;

import java.util.List;

public class ScheduleUtil {

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
	
}
