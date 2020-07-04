package com.ycs.tenjincli.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.ycs.tenjincli.TenjinConsole;
import com.ycs.tenjincli.util.Utilities;

public class Configuration {
	
	private static final Logger logger = LoggerFactory.getLogger(Configuration.class);
	
	private Configuration() {
		
	}
	
	private static Configuration configuration;
	
	private String baseUrl;
	private String userKey;
	private String userName;
	
	
	
	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/* Initiate Singleton object */
	public static Configuration getConfiguration() throws ConfigurationException {
		if(configuration == null) {
			logger.debug("Initializing configuration singleton");
			initialize();
			logger.debug("Done");
		}
		return configuration;
	}

	
	public static Configuration create(String baseUrl, String key) throws ConfigurationException {
		File configFile = getConfigurationFile();
		updateConfiguration(configFile, baseUrl, key);
		initialize();
		return configuration;
	}
	
	public static void configureLogging() {
		String logHome = getLogFilePath();
		/*System.setProperty("tjncli.log.path", logHome);
		MDC.put("tjncli.log.path", logHome);
		String logFileName = "console_" + Utilities. + ".log";
		System.setProperty("tjncli.log.name", logFileName);
		MDC.put("tjncli.log.name", logFileName);*/
		
		MDC.put("logFilePath", logHome + File.separator + Utilities.getLogPathTimestamp());
		
	}
	
	//Initialize - Load configuration data from config file
	private static void initialize() throws ConfigurationException {
		File configFile = getConfigurationFile();
		if(configFile == null || !configFile.exists()){
			logger.error("Configuration file does not exist");
			try {
				logger.debug("Creating new configuration file");
				configFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("ERROR creating configuration file", e);
				System.err.println("Unable to create new configuration file");
				e.printStackTrace();
			}
			throw new ConfigurationException("No configuration found.");
		}
		
		loadConfiguration(configFile);
		
		if(Utilities.trim(configuration.getBaseUrl()).length() < 1) {
			throw new ConfigurationException("Configuration is not complete. BASE_URL was not found.");
		}
		
		if(Utilities.trim(configuration.getUserKey()).length() < 1) {
			throw new ConfigurationException("Configuration is not complete. USER_KEY was not found.");
		}
		
		String userName = getUserNameFromKey(configuration.getUserKey());
		if(Utilities.trim(userName).length() < 1) {
			throw new ConfigurationException("Configuration is not complete. User name was not found.");
		}
		
		configuration.setUserName(userName);
		
	}
	
	private static String getUserNameFromKey(String key) {
		String userName = "";
		
		try{
			String decryptedKey = new String(Base64.decodeBase64(key.getBytes()));
			if(Utilities.trim(decryptedKey).contains(":")){
				String[] split = decryptedKey.split(":");
				userName = split[0];
			}else{
				System.err.println("Invalid User Key found in configuration file.");
			}
		}catch(Exception e){
			System.err.println("Invalid User Key found in configuration file.");
		}
		
		return userName;
	}
	
	private static void loadConfiguration(File configFile) {
		configuration = new Configuration();
		Properties props = new Properties();
		try {
			InputStream input = new FileInputStream(configFile);
			props.load(input);
			
			configuration.setBaseUrl(props.getProperty("BASE_URL"));
			configuration.setUserKey(props.getProperty("USER_KEY"));
			
			input.close();
		} catch (FileNotFoundException e) {
			System.err.println("Unable to read configuration file");
		} catch (IOException e) {
			System.err.println("Unable to read configuration file");
		}
	}
	
	private static void updateConfiguration(File configFile, String baseUrl, String key) {
		Properties props = new Properties();
		try {
			InputStream input = new FileInputStream(configFile);
			props.load(input);
			
			props.setProperty("BASE_URL", baseUrl);
			props.setProperty("USER_KEY", key);
			
			input.close();
			
			OutputStream output = new FileOutputStream(configFile);
			props.store(output, "Updated on " + new Date().toString());
			output.close();
			
		} catch (FileNotFoundException e) {
			System.err.println("Unable to read configuration file");
		} catch (IOException e) {
			System.err.println("Unable to read configuration file");
		}
	}
	
	private static File getConfigurationFile() {
		try {
			File homeDir = getTenjinConsoleHome();
			if(!homeDir.exists()) {
				homeDir.mkdirs();
			}
			
			File configFile = new File(homeDir.getAbsolutePath() + File.separator + "tenjin.config");
			return configFile;
		} catch(Exception e) {
			System.err.println("ERROR --> Could not load configuration.");
			e.printStackTrace();
			return null;
		}
	}
	
	public static File getTenjinConsoleHome() {
		System.out.println("Loading Tenjin Console configuration from " + getUserLocalHome().getAbsolutePath() + File.separator + "tenjin_console");
		logger.info("Loading Tenjin Console configuration from {}", getUserLocalHome().getAbsolutePath() + File.separator + "tenjin_console");
		File tenjinConsoleHome = new File(getUserLocalHome().getAbsolutePath() + File.separator + "tenjin_console");
		if(!tenjinConsoleHome.exists()) {
			tenjinConsoleHome.mkdirs();
		}
		
		return tenjinConsoleHome;
	}
	
	public static void main(String[] args) {
		System.out.println(System.getenv("ALLUSERSPROFILE"));
	}
	
	private static File getUserLocalHome() {
		/* Change by Sriram to use global app data folder instead of local user configuration */
		/*return new File(System.getProperty("user.home"));*/
		/*added by Padmavathi for infosys fix starts
		String path="F:\\ProgramData";
		added by Padmavathi for infosys fix ends*/
		return new File(System.getenv("ALLUSERSPROFILE"));
		/*return new File(path);*/
	}
	
	private static String getLogFilePath() {
		String logFilePath = getTenjinConsoleHome().getAbsolutePath() + File.separator + "logs";
		File logDir = new File(logFilePath);
		if(!logDir.exists()) {
			logDir.mkdirs();
		}
		
		return logFilePath;
	}
	
	private static String getOSName() {
		return System.getProperty("os.name");
	}
	
	public static String getExecutablePath() {
		try {
			String exePath = TenjinConsole.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			String osName = getOSName();
			if(Utilities.trim(osName).toLowerCase().contains("win")) {
				if(exePath.startsWith("/")) {
					exePath = exePath.substring(1);
				}
			}
			
			return exePath;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			logger.warn("Could not get exe path", e);
			return "";
		}
	}
}
