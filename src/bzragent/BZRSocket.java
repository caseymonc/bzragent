/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bzragent;

import bzragent.commands.Command;
import bzragent.commands.CommandQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Queue;

/**
 *
 * @author caseymoncur
 */
public class BZRSocket {
  private Socket socket;
  PrintWriter out = null;
  BufferedReader in = null;
  
  public BZRSocket(String host, int port) throws UnknownHostException, IOException {
    socket = new Socket(host, port);
    out = new PrintWriter(socket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(
                                socket.getInputStream()));
    BufferedReader stdIn = new BufferedReader(
                                   new InputStreamReader(System.in));
	String userInput;

	while ((userInput = stdIn.readLine()) != null) {
	    out.println(userInput);
	    System.out.println("echo: " + in.readLine());
	}

	out.close();
	in.close();
	stdIn.close();
	socket.close();
  }
  
  public BZRSocket() {
    
  }
  
  public String sendCommand(Command command) {
    return null;
  }
  
  public void parseResponse(String response, Command command) {
    command.parseAckResponse(response);
    response = response.substring(response.indexOf("\n") + 1);
    if(response.startsWith("begin")){
      response = response.substring(response.indexOf("\n") + 1, response.indexOf("end"));
    }
    
    command.parseResponse(response);
    
  }
  
  public void close() throws IOException {
    socket.close();
    socket = null;
  }
}
