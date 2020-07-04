/***

Yethi Consulting Private Ltd. CONFIDENTIAL


Name of this file:  TestCaseExecutorInput.java


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
* 03-05-2018		   Pushpalatha			   Newly Added 
*/

package com.ycs.tenjincli.command.execute;

public class TestCaseExecutorInput {

	//TENJINCG-458
	private String testCaseId;
	private String clientName;
	private String browserType;
	private String domainName;
	private String projectName;
	private String testCaseName;
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
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getBrowserType() {
		return browserType;
	}
	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}
	public String getTestCaseId() {
		return testCaseId;
	}
	public void setTestCaseId(String testCaseId) {
		this.testCaseId = testCaseId;
	}
	//TENJINCG-458 Ends
	public String getTestCaseName() {
		return testCaseName;
	}
	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}
}
