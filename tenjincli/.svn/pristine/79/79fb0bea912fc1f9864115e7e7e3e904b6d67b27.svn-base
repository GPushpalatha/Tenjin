package com.ycs.tenjincli;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.ycs.tenjin.security.Authenticator;
import com.ycs.tenjin.security.TenjinSecurityException;
import com.ycs.tenjincli.command.Command;
import com.ycs.tenjincli.config.Configuration;
import com.ycs.tenjincli.config.ConfigurationException;
import com.ycs.tenjincli.rest.RestException;
import com.ycs.tenjincli.util.Utilities;

public class TenjinConsole {
	
	private static final String PROMPT = "tenjin>";
	private static boolean CONTINUE=true;
	private static final Logger logger = LoggerFactory.getLogger(TenjinConsole.class);
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static void main(String[] args) throws IOException {
		
		Configuration.configureLogging();
		
		logger.info("Console launch -- {}", new Date());
		
		String javaHome = System.getenv("JAVA_HOME");
		String jarFilePath = Configuration.getExecutablePath();
		System.out.println("Tenjin console accessed from path: " + jarFilePath);
		
		if(Utilities.trim(javaHome).length() < 0) {
			logger.error("Invalid JAVA_HOME --> [{}]", javaHome);
			System.err.println("JAVA_HOME is not set. Please set JAVA_HOME property to a maximum JDK version of 1.7 to proceed.");
			return;
		}else{
			logger.info("Found JAVA_HOME to be {}", javaHome);
			System.out.println("JAVA_HOME is set to " + javaHome);
		}
		
		System.out.println();
		System.out.println("-------------------------------------------");
		System.out.println("Welcome to Tenjin Console!");
		System.out.println("Version " + Utilities.getProperty("product.version"));
		System.out.println("-------------------------------------------");
		System.out.println();
		System.out.println();
		
		Configuration configuration = null;
		try {
			logger.info("Loading configuration");
			configuration = Configuration.getConfiguration();
			logger.info("Got existing configuration");
			logger.info("Base URL --> {}", configuration.getBaseUrl());
			logger.info("Current User --> {}", configuration.getUserName());
			logger.info("User Key --> {}", configuration.getUserKey());
			System.out.println();
			/*System.out.println();
			System.out.println("-------------------------------------------");
			System.out.println("Welcome to Tenjin Console!");
			System.out.println("Version " + Utilities.getProperty("product.version"));
			System.out.println("-------------------------------------------");
			System.out.println();
			System.out.println();*/
			System.out.println("Tenjin URL: " + configuration.getBaseUrl());
			System.out.println("Current User: " + configuration.getUserName());
			System.out.println();
			showPrompt(args);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			logger.warn("Configuration exception caught", e);
			newConfiguration(br, args);
			
		}
		
		return;
	}
	
	private static void newConfiguration(BufferedReader br, String[] args) throws IOException {
		logger.info("Creating new configuration");
		Configuration configuration = null;
		/*Added by padmavathi for T25IT-12 starts*/
		String password = "";
		String userName=null;
		/*Changed by padmavathi for T25IT-12 ends*/
		System.out.print("Enter the Tenjin URL: ");

		String baseUrl = br.readLine();
		//System.out.println();
		/*Changed by padmavathi for T25IT-12 starts*/
		try{

			if(baseUrl.length() < 1) {
				throw new TenjinSecurityException("Please enter Url");
			}
			System.out.print("Tenjin User Name: ");

		 userName = br.readLine();
		 if(userName.length() < 1) {
				
				throw new TenjinSecurityException("Please enter UserName");
			}
		/*Changed by padmavathi for T25IT-12 ends*/
		//System.out.println();
		
		/*System.out.print("Password: ");*/
		/*String password = br.readLine();*/
	/*Changed by padmavathi for T25IT-12 ends*/
	}
		catch(TenjinSecurityException e1){
			System.err.println(e1.getMessage());
			return;
		}
		/*Changed by padmavathi for T25IT-12 starts*/
		try {
			password = acceptPassword();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("ERROR - Console not available. Unable to mask password.");
			System.out.print("Password: ");
			password = br.readLine();
		}
		//System.out.println();
		
		try {
			/*Changed by padmavathi for T25IT-12 starts*/
			if(password.length() < 1) {
				throw new TenjinSecurityException("Please enter Password");
			}
			/*Changed by padmavathi for T25IT-12 ends*/
			logger.info("Authenticating with Server");
			String key = Authenticator.authenticate(baseUrl, userName, password);
			logger.info("Done");
			
			logger.info("Updating configuration");
			configuration = Configuration.create(baseUrl, key);
			logger.info("Done");
			
			showPrompt(args);
		} catch (TenjinSecurityException e1) {
			// TODO Auto-generated catch block
			System.err.println(e1.getMessage());
		} catch (RestException e1) {
		
			// TODO Auto-generated catch block
			System.err.println(e1.getMessage());
		} catch (ConfigurationException e1) {
			// TODO Auto-generated catch block
			System.err.println(e1.getMessage());
		}
	}
	
	private static void showPrompt(String[] args) throws IOException {
		if(args.length > 0) {
			String s = "";
			for(String a : args) {
				if(a.contains(" ")) {
					s += "\"" + a + "\"" + " ";
				}else{
					s+= a + " ";
				}
			}
			if(!s.toLowerCase().startsWith("exit")) {
				if(s.equalsIgnoreCase("config")) {
					newConfiguration(br, args);
				}else{
					Command command = Command.create(s);
					try {
						command.runCommand();
					} catch (CommandLineException e) {
						// TODO Auto-generated catch block
						System.err.println(e.getMessage());
						System.exit(-1); //Added for Jenkins to determine if an execution is a failure
					}
					/* Show prompt commented by sriram to allow the program to exit in case of an in-line command */
					//showPrompt();
				
				}
			}else{
				System.out.println();
				/* Show prompt commented by sriram to allow the program to exit in case of an in-line command */
				//showPrompt();
			}
			
		}else{
			showPrompt();
		}
	}
	
	private static void showPrompt() throws IOException {
		
		while(CONTINUE) {
			System.out.print(PROMPT);
			String s = Utilities.trim(br.readLine());
			if(s.length() < 1) {
				continue;
			}else if(!s.toLowerCase().startsWith("exit")) {
				if(s.equalsIgnoreCase("config")) {
					String[] args = {};
					newConfiguration(br, args);
				}else{
					Command command = Command.create(s);
					try {
						command.runCommand();
					} catch (CommandLineException e) {
						// TODO Auto-generated catch block
						logger.error("ERROR executing command", e);
						System.err.println(e.getMessage());
					}
					showPrompt();
				
				}
			}else{
				/*System.out.println("Goodbye!");
				CONTINUE = false;*/
				exit();
			}
			
		}
		
		br.close();
		
		return;
	}
	
	private static void exit() {
		logger.info("Console exit -- {}", new Date());
		MDC.remove("logFilePath");
		System.out.println("Goodbye!");
		CONTINUE = false;
	}
	
	private static String acceptPassword() throws Exception{
		try{
			Console console = System.console();
			console.printf("Password: ");
			char[] passwordArray = console.readPassword();
			String p = new String(passwordArray);
//			System.out.println(p);
			return p;
		}catch(Exception e){
			throw e;
		}
	}
}
	