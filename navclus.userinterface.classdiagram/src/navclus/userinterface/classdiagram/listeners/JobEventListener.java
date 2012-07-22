package navclus.userinterface.classdiagram.listeners;
/*******************************************************************************
 * Copyright (c) 2007 UBC, SPL
 * All rights reserved.
 * 
 * Contributors:
 *     Seonah Lee - initial implementation
 *******************************************************************************/

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;

public class JobEventListener implements IJobChangeListener {

	List<Job> joblist = new ArrayList<Job>();
	int iCount = 0;

	public void scheduled(IJobChangeEvent event) {		
		if (event.getJob().getClass().toString().contains("org.eclipse.ui.internal.texteditor.quickdiff.DocumentLineDiffer"))
		{			
			iCount++;
			event.getJob().wakeUp();
			
			if (iCount > 0) {
				IJobManager jobMan = Job.getJobManager();
				Job[] jobs = jobMan.find("addingJobFamily");
				for (Job job: jobs) {
					joblist.add(job);
				}
				jobMan.cancel("addingJobFamily");
			}
		}
		else if (event.getJob().belongsTo("addingJobFamily")) {
			if (iCount > 0) {
				joblist.add(event.getJob());
				event.getJob().cancel();
			}
		}
	}
	
	public void aboutToRun(IJobChangeEvent event) {
	}

	public void awake(IJobChangeEvent event) {
	}

	public void running(IJobChangeEvent event) {
	}
	
	public void done(IJobChangeEvent event) {
		if (event.getResult().isOK()) {
			if (event.getJob().getClass().toString().contains("org.eclipse.ui.internal.texteditor.quickdiff.DocumentLineDiffer"))
			{
				iCount--;

				if ((iCount == 0) && (joblist.size() > 0)) {
					Job keptJob = joblist.get(0);
					joblist.remove(0);
					keptJob.schedule();
				}
				else if (iCount < 0) {
					iCount = 0;
				}
			}
			else if (event.getJob().belongsTo("addingJobFamily")) {
				if ((iCount == 0) && (joblist.size() > 0)) {
					Job keptJob = joblist.get(0);
					joblist.remove(0);
					keptJob.schedule();
				}			
			}
		}
	}

	public void sleeping(IJobChangeEvent event) {
	}
}
