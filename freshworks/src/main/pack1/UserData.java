package main.pack1;

import java.io.Serializable;
import org.json.*;

public class UserData implements Serializable {

	private int timeToLive;
	private static final long serialVersionUID = 1L;
	private String key;
	private transient JSONObject value;
	private long creationTime;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(int timeToLive) {
		this.timeToLive = timeToLive;
	}

	public JSONObject getValue() {
		return value;
	}

	public void setValue(JSONObject value) {
		this.value = value;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}
}

