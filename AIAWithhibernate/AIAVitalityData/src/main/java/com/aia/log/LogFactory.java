package com.aia.log;

import org.apache.log4j.PropertyConfigurator;

public class LogFactory {
	
	//PropertiesConfigurator is used to configure logger from properties file
	static
	{
		PropertyConfigurator.configure("log4j.properties");
	}
}
