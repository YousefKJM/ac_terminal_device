package daccess;

public class Account {

	private int id;
	private int badge;
	private String firstName;
	private String lastName;
	private boolean pending;

	private boolean isApproved;
	private boolean isAdmin;
	private String password;
	private String NFC;

	
	@Override
	public String toString()
	{
		return badge + " " + firstName + " " + isApproved + " " + isAdmin + " ";
	}
	
	public boolean isPending() {
		return pending;
	}

	public void setPending(boolean pending) {
		this.pending = pending;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public Account(int id, int badge, String firstName, String lastName, boolean isApproved, boolean isAdmin, String password, boolean isPending, String NFC)
	{
		this.id = id;
		this.badge = badge;
		this.firstName = firstName;
		this.lastName = lastName;
		this.isApproved = isApproved;
		this.isAdmin = isAdmin;
		this.password = password;
		this.pending = isPending;
		this.NFC = NFC;
	}
	
	public int getId() {
		return id;
	}
	
	public int getBadge() {
		return badge;
	}
	
	public void setBadge(int badge) {
		this.badge = badge;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNFC() {
		return NFC;
	}

	public void setNFC(String nFC) {
		NFC = nFC;
	}

}
