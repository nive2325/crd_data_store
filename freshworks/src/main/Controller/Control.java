package main.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONObject;

import main.pack1.UserData;


public class Control implements Serializable {
	static int MAXLENGTH=32;
	static int MILLISECONDS=1000;
	private static final long serialVersionUID = -725L;

	public static String getProcessName() {
		String processName = ManagementFactory.getRuntimeMXBean().getName();
		return processName;
	}

	
	public static boolean isKeyNameValid(String key) {
		if (key.length() > MAXLENGTH) {
			return false;
		}
		return true;
	}

	
	@SuppressWarnings("unchecked")
	public static boolean isKeyExists(String key, String filePath) {
		boolean isKeyExists = false;
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		HashMap<String, String> dataMap = new HashMap<String, String>();
		try {
			File file = new File(filePath);
			
			if (file.exists()) {
				fileInputStream = new FileInputStream(file);
				objectInputStream = new ObjectInputStream(fileInputStream);
				dataMap = (HashMap<String, String>) objectInputStream
						.readObject();
				
				if (dataMap.containsKey(key)) {
					isKeyExists = true;
				}

				fileInputStream.close();
				objectInputStream.close();
			}

			if (isKeyExists) {
				String str = dataMap.get(key);
				JSONObject data = new JSONObject(str);
				long currentTime = new Date().getTime();
				if (data.getInt("timeToLive") > 0
						&& (currentTime - data
								.getLong("creationTime")) >= (data
								.getInt("timeToLive") * MILLISECONDS)) {
					
					dataMap.remove(key);
					fileOutputStream = new FileOutputStream(file);
					objectOutputStream = new ObjectOutputStream(
							fileOutputStream);
					objectOutputStream.writeObject(dataMap);
					fileOutputStream.close();
					objectOutputStream.close();

					isKeyExists = false;
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			if (objectInputStream != null) {
				try {
					objectInputStream.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
		return isKeyExists;
	}

	
	@SuppressWarnings("unchecked")
	public static boolean writeData(UserData data, String filePath) {
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		HashMap<String, String> dataMap = null;
		try {
			File file = new File(filePath);
			if (file.exists()) {
				if(isKeyExists(data.getKey(),filePath) || file.length()/(1024*1024)>1024)
					return false;
				fileInputStream = new FileInputStream(file);
				objectInputStream = new ObjectInputStream(fileInputStream);
				dataMap = (HashMap<String,String>) objectInputStream
						.readObject();
				
				fileInputStream.close();
				objectInputStream.close();
				JSONObject d =data.getValue();
				d.put("timeToLive", data.getTimeToLive());
				d.put("creationTime", data.getCreationTime());
				dataMap.put(data.getKey(), d.toString());

				fileOutputStream = new FileOutputStream(file);
				objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(dataMap);
				fileOutputStream.close();
				objectOutputStream.close();

				return true;
			} else {
				dataMap = new HashMap<String, String>();
				JSONObject d = data.getValue();
				d.put("timeToLive", data.getTimeToLive());
				d.put("creationTime", data.getCreationTime());
				dataMap.put(data.getKey(), d.toString());
					
				fileOutputStream = new FileOutputStream(file);
				
				objectOutputStream = new ObjectOutputStream(fileOutputStream);
	
				objectOutputStream.writeObject(dataMap);
				
				fileOutputStream.close();
				objectOutputStream.close();

				return true;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println(exception);
			return false;
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
				
					e.printStackTrace();
				}
			}
			if (objectInputStream != null) {
				try {
					objectInputStream.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			if (objectOutputStream != null) {
				try {
					objectOutputStream.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}


	}

	
	@SuppressWarnings("unchecked")
	public static UserData readData(String key, String filePath) {
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		HashMap<String, String> dataMap = null;
		try {
			File file = new File(filePath);
			if (file.exists()) {
				if(isKeyExists(key,filePath)){
				fileInputStream = new FileInputStream(file);
				objectInputStream = new ObjectInputStream(fileInputStream);
				dataMap = (HashMap<String, String>) objectInputStream
						.readObject();
				
				fileInputStream.close();
				objectInputStream.close();
				String res = dataMap.get(key);
				JSONObject json = new JSONObject(res);
				UserData user = new UserData();
				user.setKey(key);
				user.setCreationTime(json.getLong("creationTime"));
				user.setTimeToLive(json.getInt("timeToLive"));
				json.remove("creationTime");
				json.remove("timeToLive");
				user.setValue(json);
				return user;
				}
				else return null;
			} else {
				return null;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			if (objectInputStream != null) {
				try {
					objectInputStream.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static boolean deleteData(String key, String filePath) {

		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		HashMap<String, String> dataMap = null;
		try {
			File file = new File(filePath);
			if (file.exists()) {
			
				fileInputStream = new FileInputStream(file);
				objectInputStream = new ObjectInputStream(fileInputStream);
				dataMap = (HashMap<String, String>) objectInputStream
						.readObject();

				fileInputStream.close();
				objectInputStream.close();

				dataMap.remove(key);

				fileOutputStream = new FileOutputStream(file);
				objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(dataMap);
				fileOutputStream.close();
				objectOutputStream.close();

				return true;
			}
			return false;
		} catch (Exception exception) {
			return false;
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			if (objectInputStream != null) {
				try {
					objectInputStream.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			if (objectOutputStream != null) {
				try {
					objectOutputStream.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}

	}
}