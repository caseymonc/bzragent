/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bzragent;

import bzragent.commands.Command;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author caseymoncur
 */
public class BZRSocket {
  private Socket socket;
  OutputStream out = null;
  InputStream in = null;
  
  public BZRSocket(String host, int port) throws UnknownHostException, IOException {
    socket = new Socket(host, port);
    socket.setKeepAlive(true);
    out = socket.getOutputStream();
    in = socket.getInputStream();
    
    byte[] buffer = new byte[500];

	
    in.read(buffer);
    String userInput = new String(buffer);
    out.write("agent 1\n".getBytes());
	
  }
  
  public BZRSocket() {
    
  }
  
  public void sendCommand(Command command) throws IOException {
      String commandString = command.toCommandString();
      out.write(command.toCommandString().getBytes());
      
      byte[] buffer = new byte[500];
      
      StringBuilder s = new StringBuilder();
      while(!command.canParse(s.toString())){
        int length = in.read(buffer);
        s.append(new String(buffer, 0, length));
      }
      String responseStart = s.toString();
      parseResponse(responseStart, command);
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
    out.close();
    in.close();
    socket.close();
    out = null;
    in = null;
    socket = null;
  }
}
