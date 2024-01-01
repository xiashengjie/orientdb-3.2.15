package com.orientechnologies.benchMark;

import com.orientechnologies.utils.OrientdbEnum;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//import com.arangodb.*;

/**
 * @author xsj
 */
public class Executor implements Job{

	private static final Logger log = LoggerFactory.getLogger(Executor.class);

	public static void main(String[] args) {
		executeQuery();
	}

	@Override
	public void execute(JobExecutionContext jobExecutionContext){
		executeQuery();
	}

	public static void executeQuery(){
		try {
			QueryUtils.printInfo();
			String [] queryList = new String[]{"Q1","Q2","Q3","Q4","Q5","Q6","Q8","Q9","Q10"};
			long currentTimeMillis = System.currentTimeMillis();
			String taskName = "task"+currentTimeMillis;
			//并发数
			int threads = 0;
			for (String query : queryList) {
				QueryUtils.refreshEnv();
				//QueryUtils.resultToFile(taskName,OrientdbEnum.SF30,query,threads, true,false);
				Thread.sleep(60000);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}


}

