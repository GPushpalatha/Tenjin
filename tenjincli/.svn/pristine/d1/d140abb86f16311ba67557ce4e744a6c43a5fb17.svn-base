package com.ycs.tenjincli.command;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.ycs.tenjin.security.TenjinSecurityException;
import com.ycs.tenjincli.CommandLineException;
import com.ycs.tenjincli.config.ConfigurationException;
import com.ycs.tenjincli.rest.RestException;
import com.ycs.tenjincli.util.Utilities;

public abstract class Command {

	protected String[] parameters;
	protected String commandName;

	private static final Logger logger = LoggerFactory.getLogger(Command.class);
	
	public Command(String commandName, String[] parameters) {
		this.commandName = commandName;
		this.parameters = parameters;
	}
	
	public static Command create(String consoleInput) throws CommandLineException{
		//String[] commandAndParameters = Utilities.trim(consoleInput).split(" ");
		logger.info("Creating command...");
		String[] commandAndParameters = Utilities.getParameters(consoleInput);
		String expPackageName = "com.ycs.tenjincli.command";
		String[] params = null;
		String commandName = null;
		if(commandAndParameters != null && commandAndParameters.length > 0) {
			expPackageName += "." + commandAndParameters[0];
			commandName = commandAndParameters[0];
			List<String> paramList = new ArrayList<String>();
			for(int i=1; i<commandAndParameters.length; i++) {
				paramList.add(commandAndParameters[i]);
			}
			params = (String[]) paramList.toArray(new String[0]);
		}
		
		logger.debug("Command Name --> {}", commandName);
		logger.debug("Arguments --> {}", params.toString());
		
		if(Utilities.trim(commandName).equalsIgnoreCase("")) {
			return null;
		}
		
		Reflections reflections = new Reflections(expPackageName);
		Set<Class<? extends Command>> subTypes = reflections.getSubTypesOf(Command.class);
		for(Class<? extends Command> subType : subTypes ) {
			try {
				Constructor<?> constructor = subType.getConstructor(String.class, String[].class);
			
				return (Command) constructor.newInstance(commandName, params);
			} catch (Exception e) {
				logger.error("ERROR initializing command [{}]", commandName, e);
				throw new CommandLineException("Could not initialize command [" + commandName + "]", e);
			} 
		}
		
		
		throw new CommandLineException("Invalid command [" + commandName + "]");
		
	
	}
	
	
	public void runCommand() {
		logger.info("Beginning to execute command [{}]", this.commandName);
		try {
			logger.debug("Loading parameters");
			this.loadParameters();
		} catch (ParameterException e1) {
			// TODO Auto-generated catch block
			logger.error("ERROR loading parameters for [{}]", this.commandName,  e1);
			throw new CommandLineException("Some or all of the arguments for this command are invalid.\n" + e1.getMessage());
		}
		try {
			logger.debug("Executing command [{}]", this.commandName);
			this.execute();
		} catch (RestException e) {
			// TODO Auto-generated catch block
			logger.error("ERROR executing command [{}]", this.commandName, e);
			throw new CommandLineException(e.getMessage(), e);
		} catch (TenjinSecurityException e) {
			// TODO Auto-generated catch block
			logger.error("ERROR executing command [{}]", this.commandName, e);
			throw new CommandLineException(e.getMessage(),e);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			logger.error("ERROR executing command [{}]", this.commandName, e);
			throw new CommandLineException(e.getMessage(), e);
		}
	}
	
	public void _loadParameters(Object argumentsObject, String[] arguments) {
		new JCommander(argumentsObject, arguments);
	}
	
	public abstract void execute() throws RestException, TenjinSecurityException, ConfigurationException;
	
	public abstract void loadParameters();
	
	
	public String getName() {
		return this.commandName;
	}
	
	public String[] getParameters() {
		return this.parameters;
	}
	
}
