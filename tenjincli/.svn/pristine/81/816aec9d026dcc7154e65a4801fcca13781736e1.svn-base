package com.ycs.tenjincli.command.learn;

import com.beust.jcommander.JCommander;
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

public class Learn extends Command{

	private Arguments arguments = new Arguments();
	
	public Learn(String commandName, String[] parameters) {
		super(commandName, parameters);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() throws RestException, TenjinSecurityException, ConfigurationException {
		// TODO Auto-generated method stub
		String resourcePath = "";
		LearnerInput learnerInput = new LearnerInput();
		/*Chaned by Padmavathi for T25IT-258-starts*/
		/*if(Utilities.trim(this.arguments.getFunctionCodes()).length() > 0 && Utilities.trim(this.arguments.getApiCode()).length() > 0) {*/
		/*Chaned by Padmavathi for T25IT-258-ends*/
		if(!(Utilities.trim(this.arguments.getFunctionCodes()).length() > 0) &&!(Utilities.trim(this.arguments.getApiCode()).length() > 0)) {
			throw new CommandLineException("Please specify either function codes (-funcs) or API Code (-api) only");
		}else if(Utilities.trim(this.arguments.getFunctionCodes()).length() > 0) {
			/*Changed by Padmavathi for TENJINCG-460,462 starts*/
			/*resourcePath = "/rest/learner/functions";*/
			resourcePath="/rest/applications/"+this.arguments.getApplicationId()+"/functions/learn";
			/*Changed by Padmavathi for TENJINCG-460,462 ends*/
		}else if(Utilities.trim(this.arguments.getApiCode()).length() > 0 && Utilities.trim(this.arguments.getOperations()).length() < 1){
			throw new CommandLineException("Please specify list of operations (-operations) to learn in the specified API. Type help for more information.");
		}/*Added by Padmavathi for T251IT-100 starts*/
		else if(Utilities.trim(this.arguments.getApiCode()).length() > 0 && Utilities.trim(this.arguments.getApiLearnType()).length() < 1){
			throw new CommandLineException("Please specify learn type(-learntype) to learn in the specified API. Type help for more information.");
		}/*Added by Padmavathi for T251IT-100 ends*/
		else{
			/*Changed by Padmavathi for TENJINCG-460,462 starts*/
			/*resourcePath = "/rest/learner/apis";*/
			resourcePath="/rest/applications/"+this.arguments.getApplicationId()+"/apis/learn";
			/*Changed by Padmavathi for TENJINCG-460,462 ends*/
		}
		
		learnerInput.setApplicationName(this.arguments.getApplicationName());
		learnerInput.setFunctionCodes(this.arguments.getFunctionCodes());
		learnerInput.setAutUserType(this.arguments.getAutUserType());
		learnerInput.setBrowserType(this.arguments.getBrowserType());
		learnerInput.setClientName(this.arguments.getClientName());
		learnerInput.setApiCode(this.arguments.getApiCode());
		learnerInput.setOperations(this.arguments.getOperations());
		learnerInput.setApiLearnType(this.arguments.getApiLearnType());
		
		String jsonRequest = new Gson().toJson(learnerInput);
		
		Configuration configuration = Configuration.getConfiguration();
		RestResponse response = RestClient.post(configuration.getBaseUrl(), resourcePath, configuration.getUserKey(), jsonRequest);
		
		if(response.getStatus() != 200) {
			throw new CommandLineException(response.getMessage());
		}else{
			System.out.println(response.getMessage());
			/*Changed by Padmavathi for T25IT-160 starts*/
			/*System.out.println(response.getContent());*/
			System.out.println("Run ID is: " + response.getContent());
			/*Changed by Padmavathi for T25IT-160 starts*/
		}
	}

	@Override
	public void loadParameters() {
		// TODO Auto-generated method stub
		/*JCommander.newBuilder().addObject(this.arguments).build().parse(this.parameters);*/
		_loadParameters(this.arguments, this.parameters);
	}
	
}
