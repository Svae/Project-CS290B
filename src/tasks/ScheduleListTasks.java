package tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;

import util.Job;
import util.JobList;
import util.Schedule;
import api.ReturnDecomposition;
import api.ReturnValue;
import api.TaskRecursive;

public class ScheduleListTasks extends TaskRecursive<ResultSchedule> {
	
	
	private List<Job> jobs;
	private int computers;
	private Schedule schedule;
	private LowerBoundList lowerBound;
	
	
	public ScheduleListTasks(int computers, List<Job> jobs) {
		this.computers = computers;
		this.jobs = jobs;
		schedule = new Schedule(computers);
		lowerBound = new LowerBoundSimpleConstraints(computers, jobs);
	}
	
	

	public ScheduleListTasks(int computers, ScheduleListTasks parent, List<Job> jobs, int id) {
		this.computers = computers;
		this.jobs = jobs;


		HashMap<Integer, JobList> copy = new HashMap<Integer, JobList>();
		for(Map.Entry<Integer,JobList> entry : parent.getSchedule().getSchedule().entrySet()){
		    copy.put(entry.getKey(), new JobList(entry.getValue()));
		}
		this.schedule = new Schedule(computers, copy , parent.getSchedule().getMaxLength());
		Job  temp = findAvailableJobs();
		lowerBound = new LowerBoundSimpleConstraints(this, temp, id);

	}


	@Override
	public boolean isAtomic() {
		return jobs.size() <= 4;
	}
	
	public LowerBoundList lowerBound(){ return lowerBound; };
	
	public double cost() { return lowerBound().cost(); }
	
	public Schedule getSchedule(){ return schedule; };

	@Override
	public ReturnValue<ResultSchedule> solve() {
		final Stack<ScheduleListTasks> stack = new Stack<>();
        stack.push( this );
        SharedSchedule sharedSchedule = ( SharedSchedule ) shared();
        Schedule shortestSchedule = sharedSchedule.schedule();
        double shortestScheduleCost = sharedSchedule.cost();
        while ( ! stack.isEmpty() ) 
        {
        	ScheduleListTasks currentTask = stack.pop();
            List<ScheduleListTasks> children = currentTask.children( shortestScheduleCost );
            for ( ScheduleListTasks child : children )
            {   // child lower bound < upper bound.
                if ( child.isComplete() )
                { 
                    shortestSchedule = child.getSchedule();
                    shortestScheduleCost = child.lowerBound().cost();
                } 
                else 
                { 
                    stack.push( child );
                } 
            }  
        } 
		shared( new SharedSchedule(shortestSchedule, shortestSchedule.getMaxLength()));
		return new ReturnValueSchedule(this, new ResultSchedule(shortestSchedule, shortestSchedule.getMaxLength()));
	}

	@Override
	public ReturnDecomposition divideAndConquer() {
		return new ReturnDecomposition(new CompareSchedules(), children(( (SharedSchedule) shared()).cost() ));
	}

	private List<ScheduleListTasks> children(double cost) {
		List<ScheduleListTasks> children = new LinkedList<>();
		for (int i = 1; i <= computers; i++) {
			List<Job> jobList = new ArrayList<Job>();
			for(Job job : jobs){
				jobList.add(new Job(job));
			}
			ScheduleListTasks child = new ScheduleListTasks(computers, this, jobList, i);
			if(child.lowerBound().cost() < cost){
				children.add(child);
			}
		}
		return children;
	}
	
	private boolean isComplete() { return jobs.isEmpty(); }


	
	
	private Job findAvailableJobs() {
		Job temp = null;
		for(Job job : jobs){
			if(job.hasDependences()){
				temp = checkDependences(job);
				if(temp != null){
					job.setStart(temp.getStop());
					jobs.remove(job);
					return job;
				}
			} else {
				jobs.remove(job);
				return job;
			}
		}
		return null;
	}

	private Job checkDependences(Job job){
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
