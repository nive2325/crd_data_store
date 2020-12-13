package main;

import java.io.Serializable;
import java.util.Date;
import org.json.*;

import main.pack1.UserData;
import main.Controller.Control;


public final class Crd implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 121344L;
	private String dataStoreLoc = "";
	private String dataStoreName = "";

	
	public Crd() {
		try {
			dataStoreLoc = "D:\\UserData";
			dataStoreName = "datastore-" + Control.getProcessName();
		} catch (Exception exception) {
			
		}
	}

	  
	public Crd(String filePath) {
		try {
			dataStoreLoc = filePath;
			dataStoreName = "datastore-" + Control.getProcessName();
		} catch (Exception exception) {
			
		}

	}


	public synchronized String create(String key, JSONObject value) {
		try {
			return create(key, value, -1);
		} catch (Exception exception) {
			return "Creation Failed";
		}
	}

	
	public synchronized String create(String key, JSONObject value,
			int timeToLive) {
		try {
			String filePath = dataStoreLoc + "\\" + dataStoreName;
			
			if (!Control.isKeyNameValid(key)) {
				return "Key length exceeded";
			}
			if (Control.isKeyExists(key, filePath)) {
				return "Key exists";
			}
		
			UserData data = new UserData();
			data.setKey(key);
			if (timeToLive > 0) {
				data.setTimeToLive(timeToLive);
			} else {
				data.setTimeToLive(-1);
			}
			data.setValue(value);
			data.setCreationTime(new Date().getTime());

			if (Control.writeData(data, filePath)) {
				return "Creation Successful";
			} else {
				return "Creation Failed";
			}
		} catch (Exception exception) {
			return "Creation Failed";
		}
	}

	
	public synchronized Object read(String key) {
		try {
			String filePath = dataStoreLoc + "/" + dataStoreName;
		
			if (!Control.isKeyNameValid(key)) {
				return "Key length exceeded";
			}
			if (!Control.isKeyExists(key, filePath)) {
				return "Key Not Available";
			}
		

			UserData data = Control.readData(key, filePath);
			if (null != data) {
				return data.getValue();
			}
			return "Cannot Read";
		} catch (Exception exception) {
			exception.printStackTrace();
			return "Cannot Read";
		}
	}

	
	public synchronized Object delete(String key) {
		try {
			String filePath = dataStoreLoc + "/" + dataStoreName;
			
			if (!Control.isKeyNameValid(key)) {
				return "Key Length Exceeded";
			}
			if (!Control.isKeyExists(key, filePath)) {
				return "Key not Available";
			}
			

			if (Control.deleteData(key, filePath)) {
				return "Delete Successful";
			}
			return "Delete Operation Failed";
		} catch (Exception exception) {
			exception.printStackTrace();
			return "Delete Operation Failed";
		}
	}
}