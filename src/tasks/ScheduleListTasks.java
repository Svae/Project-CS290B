package tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;

import system.Return;
import util.Job;
import util.JobList;
import util.Schedule;
import util.ScheduleUtil;
import api.ReturnDecomposition;
import api.ReturnValue;
import api.TaskRecursive;

public class ScheduleListTasks extends TaskRecursive<ResultSchedule> {
	
	
	private static final int JOBFACTOR = 30;
	private List<Job> jobs;
	private int computers;
	private Schedule schedule;
	private LowerBoundList lowerBound;
	
	
	public ScheduleListTasks(int computers, List<Job> jobs) {
		this.computers = computers;
		this.jobs = jobs;
		schedule = new Schedule(computers);
		lowerBound = new LowerBoundConstraints(computers, jobs);
	}
	
	

	public ScheduleListTasks(int computers, ScheduleListTasks parent, List<Job> jobs, int id) {
		this.computers = computers;
		this.jobs = jobs;
		HashMap<Integer, JobList> copy = new HashMap<Integer, JobList>();
		
		for(Map.Entry<Integer,JobList> entry : parent.getSchedule().getSchedule().entrySet()){
		    copy.put(entry.getKey(), new JobList(entry.getValue()));
		}
		
		this.schedule = new Schedule(computers, copy , parent.getSchedule().getMaxLength());
		Job newJob = ScheduleUtil.findAvailableJob(jobs, schedule);
		lowerBound = parent.lowerBound().make(this, newJob, id);
		jobs.remove(newJob);
	}


	@Override
	public boolean isAtomic() {
		return jobs.size() <= JOBFACTOR/computers;
	}
	
	public LowerBoundList lowerBound(){ return lowerBound; }
	
	public double cost() { return lowerBound.cost(); }
	
	public Schedule getSchedule(){ return schedule; }

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
//        System.out.println("Solve cost: " + shortestScheduleCost);
        
		shared( new SharedSchedule(shortestSchedule, shortestSchedule.getMaxLength()));
		return new ReturnValueSchedule(this, new ResultSchedule(shortestSchedule, shortestSchedule.getMaxLength()));
	}

	@Override
	public Return divideAndConquer() {
		SharedSchedule localShared = (SharedSchedule) shared();
		List<ScheduleListTasks> children = children(( localShared ).cost() );
		if(children.size() == 0){
			return new ReturnValueSchedule(this, new ResultSchedule(localShared.schedule(), localShared.cost()));
		}
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



	public List<Job> getJobs() {
		return jobs;
	}



	public double getNumberOfComputers() {
		return computers;
	}
	
}
