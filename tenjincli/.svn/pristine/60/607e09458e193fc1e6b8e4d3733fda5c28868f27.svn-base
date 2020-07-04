package com.ycs.tenjincli.command.help;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;

import org.reflections.Reflections;

import com.beust.jcommander.Parameter;
import com.ycs.tenjin.security.TenjinSecurityException;
import com.ycs.tenjincli.command.Command;
import com.ycs.tenjincli.command.Description;

import com.ycs.tenjincli.config.ConfigurationException;
import com.ycs.tenjincli.rest.RestException;

public class Help extends Command {

	public Help(String commandName, String[] parameters) {
		super(commandName, parameters);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() throws RestException, TenjinSecurityException, ConfigurationException {
		// TODO Auto-generated method stub

		try {
			
			//All the field details of commands
			Reflections reflections = new Reflections("com.ycs.tenjincli.command");
			System.out.println(); 
			Set<Class<? extends Command>> subTypes = reflections.getSubTypesOf(Command.class);
			for(Class<? extends Command> subType : subTypes) {

				String leftAlignFormat1 = "|%-20s|%-17s|%-117s|%n";
				String packgName=subType.getPackage().getName();
				String[] pckg=packgName.split("\\.");
				

				if(!packgName.equals("com.ycs.tenjincli.command.help"))
				{
					System.out.println("command: "+pckg[pckg.length-1]);
					
					Class argumentsClass =Class.forName(subType.getPackage().getName() + ".Arguments");
					
					Annotation anno=argumentsClass.getAnnotation(Description.class);
					if(anno instanceof Description)
					{
						Description desc=(Description)anno;
						System.out.println(desc.display());
					}
					System.out.println();
			        System.out.format("+--------------------+-----------------+---------------------------------------------------------------------------------------------------------------------+%n");
			        System.out.format("|NAME                | REQUIRED        | DESCRIPTION                                                                                                         |%n");
			        System.out.format("+--------------------+-----------------+---------------------------------------------------------------------------------------------------------------------+%n");
				
		        
		        
				Field[] fields=argumentsClass.newInstance().getClass().getDeclaredFields();
				for (Field field : fields) {
				    if (field.isAnnotationPresent(Parameter.class)) {
				        Parameter fAnno = field.getAnnotation(Parameter.class);
				        System.out.format(leftAlignFormat1, Arrays.toString(fAnno.names()).substring(1, Arrays.toString(fAnno.names()).length()-1 ),fAnno.required(),fAnno.description());
				     
				    }

				}
				System.out.format("+--------------------+-----------------+---------------------------------------------------------------------------------------------------------------------+%n");
				System.out.println("\n");
				}
			}
		}
		catch (Exception e) {
		// TODO Auto-generated catch block
				e.printStackTrace();
		}			
	}
			
		
	@Override
	public void loadParameters() {
		// TODO Auto-generated method stub
		}

}
