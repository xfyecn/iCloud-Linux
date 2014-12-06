package icloud.contacts;

public class Contact {

	private String UID;
	private String revision;
	private String period;
	private String version;

	public Contact(String UID, String revision, String period, String version) {

		UID = this.UID;
		revision = this.revision;
		period = this.period;
		version = this.version;
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
