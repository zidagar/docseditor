import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {

	
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket sock = new Socket("localhost", 6666);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()), true);
		
		//
		
		writer.println("LOGIN jose lemuria");
		
		Socket sock2 = new Socket("localhost", 6666);
		
		BufferedReader reader2 = new BufferedReader(new InputStreamReader(sock2.getInputStream()));
		PrintWriter writer2 = new PrintWriter(new OutputStreamWriter(sock2.getOutputStream()), true);
		
		writer2.println("CREATEFILE jajajajajaj myNewTXT.txt jose");
		
		System.out.println(reader2.readLine());
		
		/*	
		if(reader2.readLine().equals("AVLVM")){
			System.out.println(readFile(reader2));
		}else{
			//Handle error
		}
		*/
		
	}
	
	
	public static String  readFile(BufferedReader br) throws IOException{
		
		String currentLine = br.readLine();
		String document = "";
		
		
		currentLine = br.readLine();
		while (!currentLine.equals("SALVM")) {

			document += currentLine + "\n";

			currentLine = br.readLine();
		}

		
		return document;		
	}
		
	public void sendFile(String document, PrintWriter writer){
		writer.println(document);
	}
	
}
