package tasks;

import util.Schedule;
import api.ReturnValue;
import api.TaskCompose;

public class CompareSchedules extends TaskCompose<ResultSchedule>{

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
