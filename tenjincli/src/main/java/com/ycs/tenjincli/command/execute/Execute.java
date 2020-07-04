/***

Yethi Consulting Private Ltd. CONFIDENTIAL


Name of this file:  TestCase.java


Module: TENJIN - INTELLIGENT ENTERPRISE TESTING ENGINE


Copyright © 2016-17 by Yethi Consulting Private Ltd.


This source file is part of the TENJIN Software Product and System 
and is copyrighted by Yethi Consulting Private Ltd.

All rights reserved.  No part of this work may be reproduced, copied, 
duplicated, adopted, distributed, reverse engineered, stored in a retrieval  
system, transmitted in any form or by any means, electronic, 
mechanical, photographic, graphic, optic recording or otherwise, translated 
in any language or computer language, sold, rented, leased without the prior 
written permission of Yethi Consulting Services Private Ltd.

Notice: All information and source code contained in this file is, and remains 
the property of Yethi Consulting Services Private Ltd., and its suppliers, if any. 
The intellectual and technical concepts contained herein are proprietary to Yethi 
Consulting Services Private Ltd., and its suppliers and may be covered under patents 
and patents in process and are protected by trade secret or copyright laws. Dissemination 
of this information or reproduction of this material is strictly forbidden unless prior 
written permission is obtained from Yethi Consulting Services Private Ltd. 


Yethi Consulting Private Ltd.
# 1308, 4th Floor, Shetty Plaza,JB Nagar Main Road,
HAL 3rd Stage, Bangalore - 560 075,
Karnataka-560075,India
	 
*	 
*/

/******************************************
* CHANGE HISTORY
* ==============
*
* DATE                 CHANGED BY              DESCRIPTION
* 03-05-2018		   Pushpalatha			   TENJINCG-350
* 19-06-2018           Padmavathi              T251IT-91 
* 
* 
*/

package com.ycs.tenjincli.command.execute;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ycs.tenjin.security.TenjinSecurityException;
import com.ycs.tenjincli.CommandLineException;
import com.ycs.tenjincli.command.Command;
import com.ycs.tenjincli.config.Configuration;
import com.ycs.tenjincli.config.ConfigurationException;
import com.ycs.tenjincli.rest.RestClient;
import com.ycs.tenjincli.rest.RestException;
import com.ycs.tenjincli.rest.RestResponse;
import com.ycs.tenjincli.util.Utilities;

public class Execute extends Command {
	
	private Arguments arguments = new Arguments();

	public Execute(String commandName, String[] parameters) {
		super(commandName, parameters);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() throws RestException, TenjinSecurityException, ConfigurationException {
		// TODO Auto-generated method stub
		String resourcePath = "/rest/domains/";
		/*Changed by Pushpalatha for TENJINCG-350 starts*/
		String jsonRequest="";
		if(this.arguments.getTestSetName()!=null&& this.arguments.getTestCaseName()==null){
			
			ExecutorInput input = new ExecutorInput();
			input.setBrowserType(this.arguments.getBrowserType());
			input.setDomainName(this.arguments.getDomainName());
			input.setProjectName(this.arguments.getProjectName());
			input.setTestSetName(this.arguments.getTestSetName());
			input.setClientName(this.arguments.getClientName());
		
			resourcePath = resourcePath + input.getDomainName() + "/projects/" + input.getProjectName() + "/testsets/" + input.getTestSetName() + "/execute";
			jsonRequest = new Gson().toJson(input);
		}
		else if(this.arguments.getTestSetName()==null&& this.arguments.getTestCaseName()!=null){

			TestCaseExecutorInput input = new TestCaseExecutorInput();
			input.setBrowserType(this.arguments.getBrowserType());
			input.setDomainName(this.arguments.getDomainName());
			input.setProjectName(this.arguments.getProjectName());
			input.setClientName(this.arguments.getClientName());
			input.setTestCaseName(this.arguments.getTestCaseName());
			
				resourcePath = resourcePath + input.getDomainName() + "/projects/" + input.getProjectName() + "/testcases/" + input.getTestCaseName() + "/execute";
				jsonRequest = new Gson().toJson(input);
		}else if(this.arguments.getTestSetName()!=null&& this.arguments.getTestCaseName()!=null){
		/*	Modified by Padmavathi for T251IT-91 starts*/
			/*throw new CommandLineException("Foe execute command you can provide either -testset or -testcase");*/
			throw new CommandLineException("For execute command you can provide either -testset or -testcase");
			/*	Modified by Padmavathi for T251IT-91 ends*/
		}else{
			throw new CommandLineException("Argument -testset or -testcase is mandatory.");
		}
		/*Changed by Pushpalatha for TENJINCG-350 ends*/
		Configuration configuration = Configuration.getConfiguration();
		RestResponse response = RestClient.post(configuration.getBaseUrl(), resourcePath, configuration.getUserKey(), jsonRequest);
	
		
		if(response.getStatus() != 200) {
			throw new CommandLineException(response.getMessage());
		}else{
			System.out.println(response.getMessage());
			System.out.println("Run ID is: " + response.getContent());
			
			if(Utilities.trim(this.arguments.getPoolResult()).equalsIgnoreCase("Y")) {
				System.out.print("Waiting for Tenjin to finish the execution...");
				int interval = 2;
				String runStatus = "";
				String status = "";
				System.out.println("checking for run completion......");
				while(!Utilities.trim(runStatus).equalsIgnoreCase("complete") && !Utilities.trim(runStatus).equalsIgnoreCase("\"complete\"")) {
					System.out.print(".");
					resourcePath = "/rest/runs/" + response.getContent();
					RestResponse pollResponse = RestClient.get(configuration.getBaseUrl(), resourcePath, configuration.getUserKey());
					
					JsonObject resJson = new Gson().fromJson(pollResponse.getContent(), JsonObject.class);
					runStatus = Utilities.trim(resJson.get("completionStatus").toString()).replace("\"", "");
					
					if(runStatus.equalsIgnoreCase("complete") || runStatus.equalsIgnoreCase("\"complete\"")) {
						System.out.println("run completed");
						status = Utilities.trim(resJson.get("status").toString());
						//System.out.println("Run " + response.getContent() + " completed with status [" + status + "]");
						break;
					}
					
					try {
						Thread.sleep(interval*1000);
					} catch (InterruptedException e) {
						
					} 
				}
				System.out.println();
				if(!Utilities.trim(status).replace("\"", "").equalsIgnoreCase("pass")) {
					/*Changed by Leelaprasad for the requirement of CDCI TENJINCG-562 starts*/
					if(!Utilities.trim(this.arguments.getReportPath()).equalsIgnoreCase("")){
						System.out.println("Tenjin report is generating for run :"+response.getContent());
						this.generateReport(response, configuration);
						
						
					}
					/*Changed by Leelaprasad for the requirement of CDCI TENJINCG-562 ends*/
					/*throw new CommandLineException("Tenjin Execution of Run [" + response.getContent() +  "] finished with status " + status);*/
					System.out.println("Tenjin Execution of Run [" + response.getContent() +  "] finished with status " + status);
				}else{
					/*Changed by Leelaprasad for the requirement of CDCI TENJINCG-562 starts*/
					if(!Utilities.trim(this.arguments.getReportPath()).equalsIgnoreCase("")){
						System.out.println("Tenjin report is generating for run :"+response.getContent());
						this.generateReport(response, configuration);
						
					}
					/*Changed by Leelaprasad for the requirement of CDCI TENJINCG-562 ends*/
					System.out.println("Tenjin Execution of Run [" + response.getContent() +  "] finished with status " + status);
				}
				
			}
		}
	}

	@Override
	public void loadParameters() {
		// TODO Auto-generated method stub
		/*JCommander.newBuilder().addObject(this.arguments).build().parse(this.parameters);*/
		_loadParameters(this.arguments, this.parameters);
	}
	/*Changed by Leelaprasad for the requirement of CDCI TENJINCG-562 starts*/
	public void generateReport(RestResponse response,Configuration configuration) throws RestException{
		String resourcePath= "/rest/runs/" + response.getContent()+"/runReport";
		
		ExecutorInput repInput = new ExecutorInput();
		repInput.setReportPath(this.arguments.getReportPath());
		String repRequest = new Gson().toJson(repInput);
		RestResponse reportResponse = RestClient.post(configuration.getBaseUrl(), resourcePath, configuration.getUserKey(), repRequest);
		if(reportResponse.getStatus() != 201) {
			throw new CommandLineException("Unable to generate the run report for run "+response.getContent());
		}else{
			System.out.println("Tenjin report for run:"+response.getContent()+ " is created in "+this.arguments.getReportPath());
		}
		
		
	}
	/*Changed by Leelaprasad for the requirement of CDCI TENJINCG-562 ends*/
}
