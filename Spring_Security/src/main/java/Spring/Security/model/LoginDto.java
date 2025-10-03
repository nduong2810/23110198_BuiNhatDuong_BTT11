package Spring.Security.model;

public class LoginDto {
	private String usernameOrEmail;
	private String password;

	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}

	public void setUsernameOrEmail(String v) {
		this.usernameOrEmail = v;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
