package capstone_project.av_service.model;

import java.util.List;

import com.google.gson.JsonElement;

public class CuckooResDto {
	private JsonElement info;
	private JsonElement target;
	private JsonElement network;
	private JsonElement behavior;
	private List<JsonElement> signatures;
	
	public JsonElement getInfo() {
		return info;
	}
	public void setInfo(JsonElement info) {
		this.info = info;
	}
	
	public List<JsonElement> getSignatures() {
		return signatures;
	}
	public void setSignatures(List<JsonElement> signatures) {
		this.signatures = signatures;
	}
	public JsonElement getNetwork() {
		return network;
	}
	public void setNetwork(JsonElement network) {
		this.network = network;
	}
	public JsonElement getBehavior() {
		return behavior;
	}
	public void setBehavior(JsonElement behavior) {
		this.behavior = behavior;
	}
	public JsonElement getTarget() {
		return target;
	}
	public void setTarget(JsonElement target) {
		this.target = target;
	}
}
