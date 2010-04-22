import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

public class Server {

	static ServerSocket serverSock = null;

	private static Hashtable<String, String> users = new Hashtable<String, String>();

	private static ArrayList<String> loggedInUsers = new ArrayList<String>();

	public static void main(String[] args) throws IOException {

		loadUsers();

		serverSock = new ServerSocket(6666);

		while (true) {
			Socket sock = serverSock.accept();

			Thread tr = new ServerRunningThread(sock);

			tr.start();
		}

	}

	public static void loadUsers() {
		users.put("jose", "lemuria");
		users.put("sharon", "ojos");
	}

	public static boolean isUserLoggedIn(String user) {

		for (String currentUser : loggedInUsers) {
			if (currentUser.equals(user)) {
				return true;
			}
		}

		return false;
	}

	static void addLoggedUser(String user) {
		loggedInUsers.add(user);
	}

	public static void removeLoggedUser(String user) {
		for (String currentUser : loggedInUsers) {
			if (currentUser.equals(user)) {
				loggedInUsers.remove(currentUser);
				break;
			}
		}
	}

	public static boolean login(String user, String passwd) {

		try {
			String realPasswd = users.get(user);

			if (realPasswd.equals(passwd)) {
				addLoggedUser(user);
				return true;
			}
			
		} catch (Exception ex) {}

		return false;
	}

}
