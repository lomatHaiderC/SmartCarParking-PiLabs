
/**
 * Check which user is logged in
 */
public class LoginSession {
	static String loggedinName, loggedinNameEmployee;
	static String[] users = new String[100];
	static String[] usersE = new String[100];
	static String[] address = new String[100];
	int position = 0;

	public LoginSession() {

	}

	public void setPosition(int n) {
		position = n;
		users = null;
	}

	public int getPosition() {
		return position;
	}

	public void setName(String name) {
		users[position] = name;
	}

	public void setOutName(int n) {
		users[n] = null;
	}

	public void setOutEName(int n) {
		usersE[n] = null;
	}

	public String getName(int n) {
		return users[n];
		// return loggedinName;
	}

	public void setEName(String name) {
		usersE[position] = name;
		// position++;
		// loggedinNameEmployee=name;
	}

	public String getEName(int n) {
		return usersE[n];
		// return loggedinNameEmployee;
	}

}
