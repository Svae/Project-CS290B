package tasks;

import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import util.Job;
import util.Schedule;

public class LowerBoundSimpleConstraints implements LowerBoundList{

	private double lowerBound;
	
	
	public LowerBoundSimpleConstraints(int computers, List<Job> jobs) {
		int jobLength = 0;
		for (Job job : jobs) {
			jobLength += job.getTime();
		}
		lowerBound = ((double)jobLength)/computers;
		System.out.println("Lower bound: " + lowerBound);
	}
	
	public LowerBoundSimpleConstraints(ScheduleListTasks parent, Job newJob, int id) {
			Schedule schedule = parent.getSchedule();
			if(newJob.hasDependences()){
				if(newJob.getStart() > schedule.getListMax(id)){
					int diff = newJob.getStart() - schedule.getListMax(id);
					newJob.addProccessTime(diff);
				}
			}			
			schedule.addJob(id, newJob);
			lowerBound = schedule.getMaxLength();
	}

	@Override
	public double cost() {
		return lowerBound;
	}

	@Override
	public LowerBoundList make(ScheduleListTasks parent, Job newJob, int id) {
		return new LowerBoundSimpleConstraints(parent, newJob, id);
	}

}
