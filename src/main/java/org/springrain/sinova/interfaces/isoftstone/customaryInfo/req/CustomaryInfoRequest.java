package org.springrain.sinova.interfaces.isoftstone.customaryInfo.req;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value = "request")
public class CustomaryInfoRequest {

	@XStreamAlias(value = "congAccount")
	private String congAccount;
	@XStreamAlias(value = "firmID")
	private String firmID;
	@XStreamAlias(value = "channelID")
	private String channelID;
	@XStreamAlias(value = "sourceIP")
	private String sourceIP;
	@XStreamAlias(value = "destinationIP")
	private String destinationIP;
	@XStreamAlias(value = "destinationPort")
	private String destinationPort;
	@XStreamAlias(value = "extend")
	private Extend extend = new Extend();

	public String getCongAccount() {
		return congAccount;
	}

	public void setCongAccount(String congAccount) {
		this.congAccount = congAccount;
	}

	public String getFirmID() {
		return firmID;
	}

	public void setFirmID(String firmID) {
		this.firmID = firmID;
	}

	public String getChannelID() {
		return channelID;
	}

	public void setChannelID(String channelID) {
		this.channelID = channelID;
	}

	public String getSourceIP() {
		return sourceIP;
	}

	public void setSourceIP(String sourceIP) {
		this.sourceIP = sourceIP;
	}

	public String getDestinationIP() {
		return destinationIP;
	}

	public void setDestinationIP(String destinationIP) {
		this.destinationIP = destinationIP;
	}

	public String getDestinationPort() {
		return destinationPort;
	}

	public void setDestinationPort(String destinationPort) {
		this.destinationPort = destinationPort;
	}

	public Extend getExtend() {
		return extend;
	}

	public void setExtend(Extend extend) {
		this.extend = extend;
	}

}
