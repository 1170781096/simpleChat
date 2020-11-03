// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;

import common.ChatIF;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
   ChatIF serverUI;
   boolean ifClosed = false;
   String ClientInfo;
   
   
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port,ChatIF serverUI) 
  {
    super(port);
    this.serverUI = serverUI;
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
	if(msg.toString().indexOf("#login")!=-1) {
		String id1;
		id1 = msg.toString().substring(7,msg.toString().indexOf(">"));
		setInfo(id1);
	}
    System.out.println("Message received "+"from "+ClientInfo +":"+ msg + " from " + client);
    this.sendToAllClients(getInfo()+":"+msg);
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
    ifClosed = false;
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
    ifClosed = true;
  }
  
  public void setInfo(String ID) {
	  ClientInfo = ID;
  }
  public String getInfo() {
	  return ClientInfo;
  }
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
//  public static void main(String[] args) 
//  {
  //    int port = 0; //Port to listen on
  //
  //    try
  //   {
  //     port = Integer.parseInt(args[0]); //Get port from command line
  //   }
  //   catch(Throwable t)
  //   {
  //    port = DEFAULT_PORT; //Set port to 5555
  //   }
  //
  //  EchoServer sv = new EchoServer(port);
  //  
  //  try 
  //  {
  //    sv.listen(); //Start listening for connections
  //  } 
  //  catch (Exception ex) 
  //  {
  //    System.out.println("ERROR - Could not listen for clients!");
  // }
  // }
  
  protected void clientConnected(ConnectionToClient client) {
	  System.out.println("A client connected");
  }
  
  synchronized protected void clientDisconnected(
		    ConnectionToClient client) {
	  System.out.println("A client disconnected");
  }
  
  
  public void handleMessageFromServerUI(String message)
  {
	
    try
    {
    sendToAllClients("SERVER MSG>"+message);
    serverUI.display(message);
    }
    catch(Exception e)
    {
      serverUI.display
        ("Could not send message to server.");
      
    }
  }
  
  protected void connectionClosed() {
	  ifClosed = true;
  }



}




//End of EchoServer class
