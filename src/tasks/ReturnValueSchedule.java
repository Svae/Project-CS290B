package tasks;

import javax.swing.JLabel;

import system.Task;
import util.Schedule;
import api.ReturnValue;

public class ReturnValueSchedule extends ReturnValue<ResultSchedule> {

	public ReturnValueSchedule(Task task, ResultSchedule value) {
		super(task, value);
	}
}
