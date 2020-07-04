package com.ycs.tenjincli.command.schedule;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;
import com.ycs.tenjincli.command.Description;

/*@Description(display="Schedules learning or execution or extraction.")*/
@Description(display="Schedules learning or execution or extraction.\n\nsampleScheduleExecuteCommand :schedule -name Execute_task -task execute -domain Domain1 -project T24 -testtype TestCase(or) TestSet -testset (or) -testcase Customercreation "
		+ "-client prasad -browser Google Chrome -timestamp yyyy-MM-dd HH:mm:ss \n\nsampleScheduleLearnCommand :schedule -name Execute_task -task learn -aut Flexcube -funcs STDCIF -client prasad -browser Google Chrome -autlogintype Maker -timestamp yyyy-MM-dd HH:mm:ss ")

public class Arguments {
	@Parameter(names="-name" , required=true, description="Name of the scheduled task")
	private String name;
	
	@Parameter(names="-task" , required=true, description="The task that has to be executed. Valid values are Learn,execute,extract,learnapi")
	private String type;
	
	@Parameter(names="-domain", required=false, description="The name of the domain")
	private String domainName;
	
	@Parameter(names="-project", required=false, description="The name of the project")
	private String projectName;
	
	@Parameter(names="-testset", required=false, description="The name of the Test Set to execute")
	private String testSetName;
	
	@Parameter(names="-browser", required=false, description="The Default browser to be used")
	private String browserType;
	/*Changed by Padmavathi for the Requirement TENJINCG-264 ON 13-07-2017 starts*/
	/*@Parameter(names="-host", required=true, description="The Host name or IP Address of the machine on which execution should be done")
	private String targetIp;

	@Parameter(names="-port", required=false, description="The Port on the host where selenium is running")
	private int port =4444;
	*/
	@Parameter(names="-client", required=false, description="The name of the Client Machine to be used")
	private String clientName;
	
	@Parameter(names="-testdatapath", required=false, description="TestData path of the functions")
	private String testDataPath;
	
	@Parameter(names="-apiLearnType", required=false, description="Learn Type that has to be used for learning valid values are Url,Request_xml")
	private String apiLearnType;
	
	@Parameter(names="-apiCode", required=false, description="ApiCode that has to be used for learning")
	private String apiCode;
	/*Changed by Padmavathi for the Requirement TENJINCG-264 ON 13-07-2017 ends*/
	
	@Parameter(names="-aut", required=false, description="Name of the Application Under Test")
	private String applicationName;
	
	@Parameter(names="-funcs", required=false, description="Function codes that are to be learnt, separated by comma. Ex: FCODE1, FCODE2, etc.")
	private String functionCodes;
	
	@Parameter(names="-operations", required=false,description="The API Operations that have to be learnt. Specify ALL to learn all operations. Required only if -api is specified.")
	private String operations;
	
	@Parameter(names="-autlogintype", required=false, description="The AUT user type that has to be used for this learning")
	private String autUserType;

	@Parameter(names="-timestamp", required=true, description="The timestamp which defines when the task has to start")
	private String scheduleTime;

	/*Changed by Gangadhar Badagi starts*/
	
	/*
	@Parameter(names="-functions", required=false, description="Functions for the Application or Operation Name for the apicodes")
	private ArrayList<String> functions;
	*/
	@Parameter(names="-screenshotoption", required=false, description="Screen Shot Option")
	private int screenshotoptionId;
	
	/*Changed by Padmavathi for the Requirement TENJINCG-292 ON 24-07-2017 starts*/
	/*Modified by Preeti for TJN262R2-80 starts*/
	/*@Parameter(names="-testtype", required=true, description="Schedule at Test Set level or Test Case Level. Valid values are TestSet or TestCase")
	private String testType;*/
	@Parameter(names="-testtype", required=false, description="Schedule at Test Set level or Test Case Level. Valid values are TestSet or TestCase")
	private String testType;
	/*Modified by Preeti for TJN262R2-80 ends*/
	
	@Parameter(names="-testcase", required=false, description="The name of the Test Case to execute")
	private String testCaseName;
	/*Changed by Padmavathi for the Requirement TENJINCG-292 ON 24-07-2017 ends*/
	
	/*Changed by Padmavathi for the Requirement TENJINCG-264 ON 13-07-2017 starts*/
	public String getTestDataPath() {
		return testDataPath;
	}

	public void setTestDataPath(String testDataPath) {
		this.testDataPath = testDataPath;
	}
	public String getApiLearnType() {
		return apiLearnType;
	}

	public void setApiLearnType(String apiLearnType) {
		this.apiLearnType = apiLearnType;
	}

	public String getApiCode() {
		return apiCode;
	}
	public void setApiCode(String apiCode) {
		this.apiCode = apiCode;
	}
	public String getOperations() {
		return operations;
	}

	public void setOperations(String operations) {
		this.operations = operations;
	}

	/*Changed by Padmavathi for the Requirement TENJINCG-264 ON 13-07-2017 ends*/

	/*Changed by Gangadhar Badagi ends*/
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getTestSetName() {
		return testSetName;
	}

	public void setTestSetName(String testSetName) {
		this.testSetName = testSetName;
	}

	public String getBrowserType() {
		return browserType;
	}

	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}
/*Changed by Padmavathi for the Requirement TENJINCG-264 ON 13-07-2017 starts*/
	/*public String getTargetIp() {
		return targetIp;
	}

	public void setTargetIp(String targetIp) {
		this.targetIp = targetIp;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}*/
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
/*Changed by Padmavathi for the Requirement TENJINCG-264 ON 13-07-2017 ends*/
	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
/*Changed by Padmavathi for the Requirement TENJINCG-264 ON 13-07-2017 starts*/
	public String getFunctionCodes() {
		return functionCodes;
	}

	public void setFunctionCodes(String functionCodes) {
		this.functionCodes = functionCodes;
	}
	/*Changed by Padmavathi for the Requirement TENJINCG-264 ON 13-07-2017 ends*/

	public String getAutUserType() {
		return autUserType;
	}

	public void setAutUserType(String autUserType) {
		this.autUserType = autUserType;
	}

	public String getScheduleTime() {
		return scheduleTime;
	}

	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/*public ArrayList<String> getFunctions() {
		return functions;
	}

	public void setFunctions(ArrayList<String> functions) {
		this.functions = functions;
	}*/

	public int getScreenshotoptionId() {
		return screenshotoptionId;
	}

	public void setScreenshotoptionId(int screenshotoptionId) {
		this.screenshotoptionId = screenshotoptionId;
	}
	/*Changed by Gangadhar Badagi ends*/
	/*Changed by Padmavathi for the Requirement TENJINCG-292 ON 24-07-2017 starts*/
	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public String getTestCaseName() {
		return testCaseName;
	}

	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}

	/*Changed by Padmavathi for the Requirement TENJINCG-292 ON 24-07-2017 ends*/
	
	
	
	
	
}
