package com.ycs.tenjincli.command.schedule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.ycs.tenjin.security.TenjinSecurityException;
import com.ycs.tenjincli.CommandLineException;
import com.ycs.tenjincli.command.Command;
import com.ycs.tenjincli.config.Configuration;
import com.ycs.tenjincli.config.ConfigurationException;
import com.ycs.tenjincli.rest.RestClient;
import com.ycs.tenjincli.rest.RestException;
import com.ycs.tenjincli.rest.RestResponse;
import com.ycs.tenjincli.util.Utilities;

public class Schedule extends Command {
	
	private static final Logger logger = LoggerFactory.getLogger(Schedule.class);
	boolean flag=true;
	public Schedule(String commandName, String[] parameters) {
		super(commandName, parameters);
		// TODO Auto-generated constructor stub
	}

	
	private Arguments arguments = new Arguments();
	private static String SCHEDULER_PATH;
	
	@Override
	public void execute() throws RestException, TenjinSecurityException, ConfigurationException {
		// TODO Auto-generated method stub
		
		String task = Utilities.trim(this.arguments.getType());
		
		String scheduleName = Utilities.trim(this.arguments.getName());
		
		if(Utilities.trim(scheduleName).contains(" ")) {
			logger.error("Schedule Name [{}] contains spaces.", scheduleName);
			System.err.println("Schedule name cannot contain spaces. Please review your input and try again.");
			return;
		}
		
		logger.info("Attempting to create a scheduled task with name [{}]", scheduleName);
		
		this.checkSchedulerPath();
		//Changed by Padmavathi for the Requirement TENJINCG-299 ON 20-07-2017 starts*/
		/*
		if(this.isScheduleNameDuplicate(scheduleName)) {
			System.err.println("A schedule with the specified name already exists. Please choose another name.");
			return;
		}
		*/
		//Changed by Padmavathi for the Requirement TENJINCG-299 ON 20-07-2017 ends*/
		if(Utilities.trim(this.arguments.getScheduleTime()).toLowerCase().contains("am")||Utilities.trim(this.arguments.getScheduleTime()).toLowerCase().contains("pm")){
			System.err.println("Invalid date format. Please specify timestamp as YYYY-MM-DD HH:mm:ss");
			return;
		}
		//Changed by Padmavathi for the Requirement TENJINCG-264 ON 19-07-2017 starts*/
		if(!task.equalsIgnoreCase("learn")&&!task.equalsIgnoreCase("LearnAPI")&&!task.equalsIgnoreCase("execute")&&!task.equalsIgnoreCase("extract")){
			System.err.println("Invalid task .Please enter task as either learn or learnAPI or execute or extract. ");
			return;
		} 
		//Changed by Padmavathi for the Requirement TENJINCG-264 ON 19-07-2017 ends*/
		
		logger.info("validating schedule arguments");
		try {
			//Changed by Padmavathi for the Requirement TENJINCG-264 ON 13-07-2017 starts*/
			/*if(task.equalsIgnoreCase("learn")) {*/
			if(task.equalsIgnoreCase("learn")||task.equalsIgnoreCase("LearnAPI")||(task.equalsIgnoreCase("extract"))) {
				//this.validateScheduleInputForLearning();
				this.validateScheduleInputFields();
				/*Changed by Padmavathi for the Requirement TENJINCG-264 ON 13-07-2017 ends*/
			}
			else if(task.equalsIgnoreCase("execute")) {
				this.validateScheduleInputForExecution();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			return;
		}
		
		/*
		logger.info("Executing command");
		String commandText = "";
		try {
			if(task.equalsIgnoreCase("learn")) {
				commandText = this.generateCommandForLearning();
			}
			else if(task.equalsIgnoreCase("execute")) {
		        commandText = this.generateCommandForExecution();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("An unknown error occurred. Please contact Tenjin Support.");
			return;
		}*/
		
	/*	logger.info("Generated command is {}", commandText);
		
		logger.info("Creating batch file"); */
		try {
			//this.createCommandFile(commandText, scheduleName);
			
			logger.info("Creating schedule");
			this.createSchedule(scheduleName);
			//Changed by Padmavathi for the Requirement TENJINCG-264 ON 20-07-2017 starts*/
			if(flag){
		/*	System.out.println("Task scheduled successfully with name ["+scheduleName+"]");*/
				//Changed by Padmavathi for the Requirement TENJINCG-264 ON 20-07-2017 ends*/
		}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("An unknown error occurred. Please contact Tenjin Support.");
			return;
		}
		
		
	}

	
	/*Changed by Gangadhar Badagi starts*/
	private void createSchedule(String scheduleName) throws ConfigurationException, RestException {
		try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date scheduleDate = format.parse(this.arguments.getScheduleTime());
	
				SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
				String schTime = timeFormat.format(scheduleDate);
	
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
				String schDate = dateFormat.format(scheduleDate);
				
				ScheduledTask scheduledTask=new ScheduledTask();
				
				scheduledTask.setTaskName(this.arguments.getName());
				scheduledTask.setSch_date(schDate);
				scheduledTask.setSch_time(schTime);
				/*Changed by Padmavathi for the Requirement TENJINCG-264 ON 13-07-2017 starts*/
				/*scheduledTask.setAction(this.arguments.getType());
				scheduledTask.setBrowserType(this.arguments.getBrowserType());
				scheduledTask.setAppName(this.arguments.getApplicationName());*/
				scheduledTask.setReg_client(this.arguments.getClientName());
				scheduledTask.setAction(this.arguments.getType());
				scheduledTask.setBrowserType(this.arguments.getBrowserType());
				if(this.arguments.getType().trim().equalsIgnoreCase("Learn")||(this.arguments.getType().trim().equalsIgnoreCase("Extract"))||(this.arguments.getType().trim().equalsIgnoreCase("LearnAPI"))){
					scheduledTask.setAppName(this.arguments.getApplicationName());
					
					//scheduledTask.setFunctions(this.arguments.getFunctions());
					
					if(!this.arguments.getType().trim().equalsIgnoreCase("LearnAPI")){
						
						ArrayList<String> myList = new ArrayList<String>(Arrays.asList(this.arguments.getFunctionCodes().split(",")));
						scheduledTask.setAutLoginType(this.arguments.getAutUserType());
						scheduledTask.setFunctions(myList);
						}
					if(this.arguments.getType().trim().equalsIgnoreCase("LearnAPI")){
						scheduledTask.setApiCode(this.arguments.getApiCode());
						scheduledTask.setApiLearnType(this.arguments.getApiLearnType());
						ArrayList<String> myList1 = new ArrayList<String>(Arrays.asList(this.arguments.getOperations().split(",")));
						scheduledTask.setFunctions(myList1);
					}
					if(this.arguments.getType().trim().equalsIgnoreCase("Extract")){
						ArrayList<String> myList2 = new ArrayList<String>(Arrays.asList(this.arguments.getTestDataPath().split(",")));
						scheduledTask.setTestDataPath(myList2);
					}
				}
				if(this.arguments.getType().trim().equalsIgnoreCase("Execute")){
					scheduledTask.setDomainName(this.arguments.getDomainName());
					scheduledTask.setProjectName(this.arguments.getProjectName());
					scheduledTask.setScreenShotOption(this.arguments.getScreenshotoptionId());
					/*Changed by Padmavathi for the Requirement TENJINCG-292 ON 24-07-2017 starts*/
					scheduledTask.setType(this.arguments.getTestType());
					if(this.arguments.getTestType().trim().equalsIgnoreCase("TestSet")){
					scheduledTask.setTestSetName(this.arguments.getTestSetName());
					}
					else if(this.arguments.getTestType().trim().equalsIgnoreCase("TestCase")){
						scheduledTask.setTestCaseName(this.arguments.getTestCaseName());
						}
				}
			
				/*Changed by Padmavathi for the Requirement TENJINCG-292 ON 24-07-2017 ends*/
				String resourcePath=null;
				
				String jsonRequest = new Gson().toJson(scheduledTask);
				if(this.arguments.getType().trim().equalsIgnoreCase("Execute")){
					resourcePath = "/rest/domains/"+scheduledTask.getDomainName()+"/projects/"+scheduledTask.getProjectName()+"/schedules";
				} else if((this.arguments.getType().trim().equalsIgnoreCase("Learn"))||(this.arguments.getType().trim().equalsIgnoreCase("LearnAPI"))){
					 resourcePath = "/rest/learner/schedules";
				}
				else if(this.arguments.getType().trim().equalsIgnoreCase("Extract")){
					resourcePath = "/rest/extractor/schedules";
				}
				/*Changed by Padmavathi for the Requirement TENJINCG-264 ON 13-07-2017 ends*/
			    Configuration configuration = Configuration.getConfiguration();
				RestResponse response = RestClient.post(configuration.getBaseUrl(), resourcePath, configuration.getUserKey(), jsonRequest);
				
				if(response.getStatus() != 200 && response.getStatus() != 201) {
					 flag=false;
					throw new CommandLineException(response.getMessage());
				}else{
					//Changed by Padmavathi for the Requirement TENJINCG-264 ON 19-07-2017 starts*/
					System.out.println(response.getMessage()+" with name "+scheduleName);
				    System.out.println("Schedule ID is:"+response.getContent());
					//Changed by Padmavathi for the Requirement TENJINCG-264 ON 19-07-2017 ends*/
				}
	
			}
		//Changed by Padmavathi for the Requirement TENJINCG-264 ON 19-07-2017 starts*/
		catch(CommandLineException e){
			System.err.println(e.getMessage());
		   	}
		
		catch (ParseException e) {
				// TODO Auto-generated catch block
			    flag=false;
				//e.printStackTrace();
				System.err.println("Invalid date format. Please specify timestamp as YYYY-MM-DD HH:mm:ss");
			}
		//Changed by Padmavathi for the Requirement TENJINCG-264 ON 19-07-2017 ends*/
		}
		
	/*Changed by Gangadhar Badagi ends*/
	/*private void createSchedule(String scheduleName) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date scheduleDate = format.parse(this.arguments.getScheduleTime());

			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
			String schTime = timeFormat.format(scheduleDate);

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			String schDate = dateFormat.format(scheduleDate);

			List<String> commands = new ArrayList<String>();

			commands.add("schtasks.exe");
			commands.add("/CREATE");
			commands.add("/TN");
			commands.add("\""+scheduleName+"\"");
			commands.add("/TR");
			commands.add("\""+ SCHEDULER_PATH + File.separator + scheduleName + ".bat" +"\"");
			commands.add("/SC");
			commands.add("once");
			commands.add("/ST");
			commands.add(schTime);
			commands.add("/SD");
			commands.add(schDate);

			ProcessBuilder builder = new ProcessBuilder(commands);
			Process p;

			p = builder.start();
			p.waitFor();

			System.out.println(p.exitValue());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	@Override
	public void loadParameters() {
		// TODO Auto-generated method stub
		_loadParameters(this.arguments, this.parameters);
	}
	
	/*public static void main(String[] args) throws ParseException {
		String d = "2017-06-07 17:12:00";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date scheduleDate = format.parse(d);
		
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		System.out.println(timeFormat.format(scheduleDate));
		
		
	}*/
	
	private void validateScheduleInputForExecution() throws Exception{
		if(Utilities.trim(this.arguments.getDomainName()).length() < 1 || Utilities.trim(this.arguments.getProjectName()).length() < 1) {
			logger.error("Domain Name or Project Name is blank");
			throw new Exception("Both Domain and Project names are mandatory to schedule an execution. Please try again!");
		}
		if(Utilities.trim(this.arguments.getClientName()).length() < 1) {
			logger.error("Client is blank");
			throw new Exception("Client is mandatory to schedule execution. Please try again!");
		}
		/*Changed by Padmavathi for the Requirement TENJINCG-292 ON 24-07-2017 starts*/
		if(Utilities.trim(this.arguments.getTestType()).length() < 1) {
			logger.error("testType is blank");
			throw new Exception("testType is mandatory to schedule execution. Please try again!");
		}
		if(this.arguments.getTestType().trim().equalsIgnoreCase("TestSet")){
		if(Utilities.trim(this.arguments.getTestSetName()).length() < 1) {
			logger.error("Test Set Name is mandatory to schedule an execution. Please try again!");
			throw new Exception("Test Set Name is mandatory to schedule an execution. Please try again!");
		}
		}
		if(this.arguments.getTestType().trim().equalsIgnoreCase("TestCase")){
			if(Utilities.trim(this.arguments.getTestCaseName()).length() < 1) {
				logger.error("TestCase Name is mandatory to schedule an execution. Please try again!");
				throw new Exception("TestCaseName is mandatory to schedule an execution. Please try again!");
			}
			}
		/*Changed by Padmavathi for the Requirement TENJINCG-292 ON 24-07-2017 ends*/
		
	}
	/*Changed by Padmavathi for the Requirement TENJINCG-264 ON 13-07-2017 starts*/
	private void validateScheduleInputFields() throws Exception{
		if(Utilities.trim(this.arguments.getApplicationName()).length() < 1) {
			logger.error("Application name is blank");
			throw new Exception("Application is mandatory to schedule. Please try again!");
		}
		
		if(!this.arguments.getType().trim().equalsIgnoreCase("LearnAPI")){
		if(Utilities.trim(this.arguments.getClientName()).length() < 1) {
			logger.error("Client is blank");
			throw new Exception("Client is mandatory to schedule. Please try again!");
		}
		}
		if(this.arguments.getType().trim().equalsIgnoreCase("Learn")){
		if(Utilities.trim(this.arguments.getAutUserType()).length() < 1) {
			logger.error("AUT User type is blank");
			throw new Exception("AUT User type is mandatory to schedule. Please try again!");
		}
		if(Utilities.trim(this.arguments.getFunctionCodes()).length() < 1) {
			logger.error("FunctionCodes is blank");
			throw new Exception("FunctionCodes is mandatory to schedule. Please try again!");
		}
		}
		
		if(this.arguments.getType().trim().equalsIgnoreCase("LearnAPI")){
			if(Utilities.trim(this.arguments.getApiCode()).length() < 1) {
				logger.error("ApiCode is blank");
				throw new Exception("ApiCode is mandatory to schedule. Please try again!");
			}
			if(Utilities.trim(this.arguments.getOperations()).length() < 1) {
				logger.error("Operations is blank");
				throw new Exception("Operations is mandatory to schedule. Please try again!");
			}
		}
		if(this.arguments.getType().trim().equalsIgnoreCase("Extract")){
		if(this.arguments.getTestDataPath()==null) {
			logger.error("TestDataPath codes is blank");
			throw new Exception("TestDataPath are mandatory to schedule. Please try again!");
		}
		}
		
		
	}

	/*Changed by Padmavathi for the Requirement TENJINCG-264 ON 13-07-2017 ends*/
	/*private String generateCommandForExecution() throws Exception{
		String commandString= "";
		try {
			commandString += "\""+ this.getJavaHomePath() + "\"" + File.separator + "java -jar " + "\"" + Configuration.getExecutablePath() "C:\\Users\\Sriram\\desktop\\cli.jar" + "\"";
			commandString += "\""+ this.getJavaHomePath() + "\"" + File.separator + "java -jar " + "\"" + Configuration.getExecutablePath()  + "\"";
			commandString += " execute -domain " + "\"" + this.arguments.getDomainName()+ "\""
							+ " -project " + "\"" + this.arguments.getProjectName()+ "\""
							+ " -testset " + "\"" + this.arguments.getTestSetName()+ "\""
							+ " -browser " + "\"" + this.arguments.getBrowserType()+"\""
							+ " -client" + this.arguments.getClientName();
						
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("ERROR creating command for execution",e );
			throw new Exception("Could not create command.");
		}
		return commandString;
	}
	
	private String generateCommandForLearning() throws Exception {
		String commandString= "";
		try {
			commandString += "\""+ this.getJavaHomePath() + "\"" +  File.separator + "java -jar " +"\"" + Configuration.getExecutablePath() + "\"";
			commandString += " learn -aut " + "\"" + this.arguments.getApplicationName() + "\""
							+ " -functions  " + "\"" + this.arguments.getFunctionCodes() + "\""
							+ " -browser " + "\"" + this.arguments.getBrowserType()  + "\""
							+ " -client" + this.arguments.getClientName()  + "\""
							+ " -autlogintype" + this.arguments.getAutUserType();
		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("ERROR creating command for learning",e );
			throw new Exception("Could not create command.");
		}
		return commandString;	
	}*/
	/*
	private String getJavaHomePath() {
		return System.getenv("JAVA_HOME") + File.separator + "bin";
	}*/
/*	
	private void createCommandFile(String commandText, String scheduleName) throws Exception {
		File file = new File(SCHEDULER_PATH + File.separator + scheduleName + ".bat");
		
		try {
			OutputStream os = new FileOutputStream(file);
			os.write(commandText.getBytes());
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("ERROR creating executable",e);
			throw new Exception("Could not create executable.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("ERROR creating executable",e);
			throw new Exception("Could not create executable.");
		}
		
	}*/
	
	private void checkSchedulerPath() {
		File file = new File(Configuration.getTenjinConsoleHome() + File.separator + "scheduled_tasks");
		if(!file.exists()){
			file.mkdirs();
		}
		
		SCHEDULER_PATH = file.getAbsolutePath();
	}
	//Changed by Padmavathi for the Requirement TENJINCG-299 ON 20-07-2017 starts*/
	/*private boolean isScheduleNameDuplicate(String scheduleName) {
		File file = new File(SCHEDULER_PATH + File.separator + scheduleName + ".bat");
		if(file.exists()){
			return true;
		}else{
			return false;
		}
	}*/
	//Changed by Padmavathi for the Requirement TENJINCG-299 ON 20-07-2017 starts*/
}
