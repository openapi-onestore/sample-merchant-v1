package com.skplanet.openapi.external.framework;

public class ManagerProducer {
		
	public static AbstractManagerFactory getFactory(Environment env, String logPropetyFilePath) {
		return new ManagerFactory(env, logPropetyFilePath);
	}
	
}
