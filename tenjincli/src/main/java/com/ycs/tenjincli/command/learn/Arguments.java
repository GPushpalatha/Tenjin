package com.ycs.tenjincli.command.learn;

import com.beust.jcommander.Parameter;
import com.ycs.tenjincli.command.Description;

/*@Description(display="Learns specific function codes.")*/
@Description(display="Learns specific function codes.\nSample command: learn -aut Flexcube -funcs STDCIF -client prasad -browser Google Chrome -autUser Maker \n"
		+ "Note:If the data contains spaces you have to include them in double quotes")
public class Arguments {
	@Parameter(names="-aut", required=true, description="Name of the Application Under Test")
	private String applicationName;
	/*added by Padmavathi for TENJINCG-460,462 starts*/
	@Parameter(names="-autid", required=false, description="Id of the Application Under Test")
	private int applicationId;
	/*Added by Padmavathi for TENJINCG-460,462 ends*/
	@Parameter(names="-funcs", required=false, description="Function codes that are to be learnt, separated by comma. Ex: FCODE1, FCODE2, etc. ")
	private String functionCodes;
	
	@Parameter(names="-browser", required=false, description="Browser to be used for learning. Valid values are [Google Chrome, Mozilla Firefox, Microsoft Internet Explorer]. Required only if -funcs is specified.")
	private String browserType;
	
	@Parameter(names="-autuser", required=false, description="The AUT user type that has to be used for this learning. Required only if -funcs is specified.")
	private String autUserType;
	
	@Parameter(names="-client", required=false, description="The name of the client machine configured in Tenjin where this learning has to be performed. Required only if -funcs is specified.")
	private String clientName;
	
	@Parameter(names="-api", required=false, description="API code that has to be learnt.")
	private String apiCode;
	
	@Parameter(names="-operations", required=false,description="The API Operations that have to be learnt. Specify ALL to learn all operations. Required only if -api is specified.")
	private String operations;
	
	@Parameter(names="-learntype", required=false, description="The method to be used to learn API Operations. Valid values are URL,MESSAGE. Required only if -api is specified.")
	private String apiLearnType;
	
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

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getFunctionCodes() {
		return functionCodes;
	}

	public void setFunctionCodes(String functionCodes) {
		this.functionCodes = functionCodes;
	}

	public String getBrowserType() {
		return browserType;
	}

	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}

	public String getAutUserType() {
		return autUserType;
	}

	public void setAutUserType(String autUserType) {
		this.autUserType = autUserType;
	}
	
	/*added by Padmavathi for TENJINCG-460,462 starts*/
	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}
	/*added by Padmavathi for TENJINCG-460,462 ends*/
	
}
