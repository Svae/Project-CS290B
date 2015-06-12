package tasks;

import api.ReturnValue;
import api.TaskCompose;

public class CompareSchedules extends TaskCompose<ResultSchedule>{

	/**
    * Compares schedules based on the longest time for it to be done
    * 
    * @return returns the schedule with the shortest max length
    */
	
	@Override
	public ReturnValue call() {
		ResultSchedule bestSchedule = args().remove(0);
		for (ResultSchedule schedule : args()) {
			if( schedule.cost()< bestSchedule.cost()){
				bestSchedule = schedule;
			}
		}
		return new ReturnValueSchedule(this, bestSchedule);
	}

}
