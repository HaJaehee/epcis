package org.oliot.epcis.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.json.JSONObject;
import org.oliot.epcis.service.subscription.MongoSubscription;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * Copyright (C) 2014-2016 Jaewook Byun
 *
 * This project is part of Oliot open source (http://oliot.org). Oliot EPCIS
 * v1.2.x is Java Web Service complying with Electronic Product Code Information
 * Service (EPCIS) v1.2.
 *
 * @author Jaewook Byun, Ph.D student
 * 
 *         Korea Advanced Institute of Science and Technology (KAIST)
 * 
 *         Real-time Embedded System Laboratory(RESL)
 * 
 *         bjw0829@kaist.ac.kr, bjw0829@gmail.com
 */

public class Configuration implements ServletContextListener {

	public static Logger logger;
	public static String webInfoPath;
	public static String wsdlPath;
	public static boolean isCaptureVerfificationOn;
	public static String facebookAppID;
	public static String adminID;
	public static String adminScope;
	public static boolean isQueryAccessControlOn;
	public static boolean isTriggerSupported;
	public static MongoClient mongoClient;
	public static MongoDatabase mongoDatabase;
	public static String backend_ip;
	public static int backend_port;
	public static String databaseName;
	public static JSONObject json;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

		// Set Logger
		setLogger();

		// Set Basic Configuration with Configuration.json
		setBasicConfiguration(servletContextEvent.getServletContext());

		// load existing subscription
		loadExistingSubscription();
	}

	private void setLogger() {
		// Log4j Setting
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.INFO);
		Configuration.logger = Logger.getRootLogger();
	}

	private void setBasicConfiguration(ServletContext context) {
		String path = context.getRealPath("/WEB-INF");
		try {
			// Get Configuration.json
			File file = new File(path + "/Configuration.json");
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);

			String data = "";
			String line = null;
			while ((line = reader.readLine()) != null) {
				data += line;
			}
			reader.close();
			json = new JSONObject(data);

			Configuration.webInfoPath = context.getRealPath("/WEB-INF");
			Configuration.wsdlPath = context.getRealPath("/wsdl");

			// Set up capture_verification
			String captureVerification = json.getString("capture_verification");
			if (captureVerification == null) {
				Configuration.logger.error(
						"capture_verification is null, please make sure Configuration.json is correct, and restart.");
			}
			captureVerification = captureVerification.trim();
			if (captureVerification.equals("on")) {
				Configuration.isCaptureVerfificationOn = true;
				Configuration.logger.info("Capture_Verification - ON ");
			} else if (captureVerification.equals("off")) {
				Configuration.isCaptureVerfificationOn = false;
				Configuration.logger.info("Capture_Verification - OFF ");
			} else {
				Configuration.logger.error(
						"capture_verification should be (on|off), please make sure Configuration.json is correct, and restart.");
			}

			// Query Access Control
			// Set up capture_verification
			String queryAC = json.getString("query_access_control");
			if (queryAC == null) {
				Configuration.logger
						.error("query_access_control, please make sure Configuration.json is correct, and restart.");
			}
			queryAC = queryAC.trim();
			if (queryAC.equals("on")) {
				Configuration.isQueryAccessControlOn = true;
				Configuration.logger.info("Query_AccessControl - ON ");
			} else if (queryAC.equals("off")) {
				Configuration.isQueryAccessControlOn = false;
				Configuration.logger.info("Query_AccessControl - OFF ");
			} else {
				Configuration.logger.error(
						"query_access_control should be (on|off), please make sure Configuration.json is correct, and restart.");
			}

			// Facebook Application ID
			String fai = json.getString("facebook_app_id");
			if (fai == null) {
				Configuration.logger
						.error("facebook_app_id, please make sure Configuration.json is correct, and restart.");
			}
			facebookAppID = fai.trim();

			// Admin Facebook ID
			String aID = json.getString("admin_facebook_id");
			if (aID == null) {
				Configuration.logger
						.error("admin_facebook_id, please make sure Configuration.json is correct, and restart.");
			}
			adminID = aID.trim();

			// Admin Scope
			String aScope = json.getString("admin_scope");
			if (aScope == null) {
				Configuration.logger.error("admin_scope, please make sure Configuration.json is correct, and restart.");
			}
			adminScope = aScope.trim();

			setMongoDB(json);

			// Trigger Support
			String triggerSupport = json.getString("trigger_support");
			if (triggerSupport == null || triggerSupport.trim().equals("on")) {
				isTriggerSupported = true;
			} else {
				isTriggerSupported = false;
			}

		} catch (Exception ex) {
			Configuration.logger.error(ex.toString());
		}
	}

	private void setMongoDB(JSONObject json) {
		if (json.isNull("backend_ip")) {
			backend_ip = "localhost";
		} else {
			backend_ip = json.getString("backend_ip");
		}
		if (json.isNull("backend_port")) {
			backend_port = 27017;
		} else {
			backend_port = json.getInt("backend_port");
		}
		if (json.isNull("backend_database_name")){
			databaseName = "epcis";
		} else {
			databaseName = json.getString("backend_database_name");
		}
		mongoClient = new MongoClient(backend_ip, backend_port);
		mongoDatabase = mongoClient.getDatabase(databaseName);
	}

	private void loadExistingSubscription() {
		MongoSubscription ms = new MongoSubscription();
		ms.init();
	}
}
