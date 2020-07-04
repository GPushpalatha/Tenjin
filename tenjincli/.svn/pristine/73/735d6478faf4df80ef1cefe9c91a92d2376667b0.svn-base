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
* 03-May-2018		   Pushpalatha			   TENJINCG-350
* 19-06-2018           Padmavathi              T251IT-83
* 
*/

package com.ycs.tenjincli.command.execute;

import com.beust.jcommander.Parameter;
import com.ycs.tenjincli.command.Description;

/*@Description(display="Executes a particular testset.")*/
@Description(display="Executes a particular testset.\nSample command: execute -domain domain_prasad -project T24 -testset (or) -testcase Customercreation -client prasad -browser Google Chrome ")
public class Arguments {
	
	@Parameter(names="-domain", required=true, description="The name of the domain")
	private String domainName;
	
	@Parameter(names="-project", required=true, description="The name of the project")
	private String projectName;
	/*Added  and changed by Pushpalatha for TENJINCG-350 starts*/
	/*@Parameter(names="-testset", required=true, description="The name of the Test Set to execute")*/
	@Parameter(names="-testset", required=false, description="The name of the Test Set to execute")
	private String testSetName;
	
	@Parameter(names="-testcase", required=false, description="The name of the Test Case to execute")
	private String testCaseName;
	/*Added by Pushpalatha for TENJINCG-350 ends*/
	
	public String getTestCaseName() {
		return testCaseName;
	}

	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}
	/*Modified by padmavathi for T251IT-83 starts*/
	@Parameter(names="-browser", required=false, description="The Default browser to be used")
	/*@Parameter(names="-browser", required=true, description="The Default browser to be used")*/
	/*Modified by padmavathi for T251IT-83 ends*/
	private String browserType;
	
	@Parameter(names="-client", required=false, description="The name of the Client Machine to be used")
	private String clientName;
	
	@Parameter(names="-poll", required=false, description="Indicates if the console has to wait until the execution is complete. Valid values are Y and N")
	private String poolResult;
	
	/*Changed by Leelaprasad for the requirement of CDCI TENJINCG-562 starts*/
	@Parameter(names="-reportPath", required=false, description="The path of the run report")
	private String reportPath;
	
	public String getReportPath() {
		return reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}
	/*Changed by Leelaprasad for the requirement of CDCI TENJINCG-562 ends*/
	
	public String getPoolResult() {
		return poolResult;
	}

	public void setPoolResult(String poolResult) {
		this.poolResult = poolResult;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
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

	public void setBrowserType(String defaultBrowser) {
		this.browserType = defaultBrowser;
	}

	/*public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}*/
	
	
	
}
