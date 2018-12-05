
public class User {
	private int userId;
	private String userName;
	private String password;
	public User(String userName, String password ) {
		this.userName = userName;
		this.password = password;
	}
	public User() {
		
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setuserId(int userId) {
		this.userId = userId;
	}
	public int getuserId() {
		return userId;
	}
}
