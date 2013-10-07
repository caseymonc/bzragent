/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bzragent.commands;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author caseymoncur
 */
public class CommandQueue {
  private HashMap<String, Queue<Command>> queue;
  
  public CommandQueue() {
    queue = new HashMap<String, Queue<Command>>();
  }
  
  public void push(Command command) {
    Queue<Command> commandQueue = queue.get(command.toCommandString());
    if(commandQueue == null) {
      commandQueue = new LinkedList<Command>();
      queue.put(command.getClass().getName(), commandQueue);
    }
    commandQueue.offer(command);
  }
  
  public Command pop(String name) {
    Queue<Command> commandQueue = queue.get(name);
    if(commandQueue == null) return null;
    return commandQueue.poll();
  }
}
