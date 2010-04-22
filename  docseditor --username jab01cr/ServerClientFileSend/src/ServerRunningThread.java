import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.filechooser.FileSystemView;

	class ServerRunningThread extends Thread {
	
		Socket sock = null;
		
		public ServerRunningThread(Socket sock){
			System.out.println("Instance created");
			this.sock = sock;
			
			System.out.println(sock.getLocalPort());
		}
		
		public void run(){
			try {
				System.out.println("Instance Running");
				BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				
				PrintWriter writter = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()), true);
				
				System.out.println("Instance going to start");
				
				processRequest(br, writter);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
				
		public void sendFile(BufferedReader br, PrintWriter writter, String file) throws IOException{
			
			File toSend = new File("UserDocs" + File.separator + file);
			BufferedReader reader = new BufferedReader(new FileReader(toSend));
			
			String currentLine = null;
			
			writter.println("AVLVM");
			System.out.println("Starting sending file");
			
			while((currentLine = reader.readLine()) != null){
				
				writter.println(currentLine);
				
			}
			System.out.println("File Sent");
			writter.println("SALVM");
			sock.close();
			
		}
		
		public void processRequest(BufferedReader br, PrintWriter writter) throws IOException{
			String request = br.readLine();
			String [] params = request.split(" ");
						
			if(params[0].toUpperCase().equals("GIVEMETHEFILE")){	
				String user = params[params.length-1];
				if(!handleUserLoggedin(writter, user)) {
					return;
				}
				sendFile(br, writter, params[1]);
			}if(params[0].toUpperCase().equals("GETUSERFILES")){	
				String user = params[params.length-1];
				if(!handleUserLoggedin(writter, user)) {
					return;
				}
				writter.println(getFilesForUser(user));
			}if(params[0].toUpperCase().equals("LOGIN")){
				String currentUser = params[1];
				String passwd = params[2];
				loginUser(writter, currentUser, passwd);
			}if(params[0].toUpperCase().equals("CREATEFILE")){
				String user = params[params.length-1];
				if(!handleUserLoggedin(writter, user)) {
					return;
				}
				String fileContents = params[1];
				String fileName =  params[2];
				createNewFile(fileContents, fileName, user);
			}if(params[0].toUpperCase().equals("LOGOUT")){
				//loginUser(br, writter);
			}		
			
		}
		

		
		public void loginUser(PrintWriter writer, String user, String passwd) throws IOException{
			
			if(Server.login(user, passwd)){
				writer.println("USERLOGGED");
				System.out.println("New User logged in: " + user);
			}else{
				writer.println("USERNOTLOGGED");
				System.out.println("Login fail for: " + user);
			}
			sock.close();
		}
		
		
		public boolean handleUserLoggedin(PrintWriter writter, String user) throws IOException{
			if(!Server.isUserLoggedIn(user)){
				writter.println("ERR_NOTLOGGEDIN");
				System.out.println("User not logged in");
				sock.close();
				return false;
			}
			
			return true;
		}
		
		public String getFilesForUser(String user){
			
			File docsFolder = new File("UserDocs");
			final String localUser = user;
			
			FilenameFilter filter = new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String name) {
					if(name.startsWith(localUser)){
						return true;
					}
					return false;
				}
			};
			
			File [] userFiles = docsFolder.listFiles(filter);
			
			String files = "";
			
			for (int i = 0; i < userFiles.length; i++) {
				files += userFiles[i].getName() + "***";
			}
			
			return files;
		}
		
		public void createNewFile(String fileContents, String filename, String user) throws IOException{
			File newFile = new File("UserDocs" + File.separator + user + "_" + filename);
			
			newFile.createNewFile();
			
			PrintWriter fwriter = new PrintWriter(new FileWriter(newFile));
			
			fwriter.println(fileContents);
			
			fwriter.flush();
			fwriter.close();
			
			System.out.println("New file created: +\n" + fileContents);
		}
		
	}