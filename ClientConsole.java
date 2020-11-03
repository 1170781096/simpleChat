// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.Scanner;

import client.*;
import common.*;

/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole 
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge  
 * @author Dr Robert Lagani&egrave;re
 * @version September 2020
 */
public class ClientConsole implements ChatIF 
{
  //Class variables *************************************************
  
  /**
   * The default port to connect on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   */
  ChatClient client;
  
  
  
  /**
   * Scanner to read from the console
   */
  Scanner fromConsole; 

  
  //Constructors ****************************************************

  /**
   * Constructs an instance of the ClientConsole UI.
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   */
  public ClientConsole(String loginID,String host, int port) 
  {
    try 
    {
      client= new ChatClient(loginID,host, port, this);
      
      
    } 
    catch(IOException exception) 
    {
      System.out.println("Error: Can't setup connection!"
                + " Terminating client.");
      System.exit(1);
    }
    
    // Create scanner object to read from console
    fromConsole = new Scanner(System.in); 
  }

  
  //Instance methods ************************************************
  
  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the client's message handler.
   */
  public void accept() 
  {
    try
    {
      client.handleMessageFromClientUI(String.format("#login<%s>", client.loginID));
      String message;

      while (true) 
      {
        message = fromConsole.nextLine();
        int ifCommand = message.indexOf("#");
        if(ifCommand != 0) {
        client.handleMessageFromClientUI(message);
        }  
        if(ifCommand == 0) {
        	String host1;
        	int port1;
        	if (message.equals("#quit")) {client.quit();System.exit(0);}
        	if (message.equals("#logoff")) {client.closeConnection();}
        	if (message.equals("#login")) {
        		if (client.isConnected()==false)
        			{client.openConnection();} else {System.out.println("the client is  already connected");}}
        	if(message.equals("#gethost")) {System.out.println(client.getHost());}
        	if(message.equals("#getport")) {System.out.println(client.getPort());}
        	if(message.indexOf("#sethost")!=-1) {
        		if(client.isConnected()==true) {System.out.println("the client is not logged off");} else {
        		host1 = message.substring(9,message.indexOf(">"));
        		client.setHost(host1);
        		System.out.println("Host set to: "+host1);
        	}
        	}
        	if(message.indexOf("#setport")!=-1) {
        		if(client.isConnected()==true) {System.out.println("the client is not logged off");} else {
        		port1 = Integer.parseInt(message.substring(9,message.indexOf(">")));
        		client.setPort(port1);
        		System.out.println("Port set to: "+port1);
        		}
        	}
        }	
        
        
      }
    } 
    catch (Exception ex) 
    {
      System.out.println
        ("Unexpected error while reading from console!");
    }
  }

  /**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  public void display(String message) 
  {
    System.out.println("> " + message);
  }

  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of the Client UI.
   *
   * @param args[0] The host to connect to.
   */
  public static void main(String[] args) 
  {
	String id = "";
    String host = "";
    String port = "0";
    
    int portInt = Integer.parseInt(port);
    if(args.length==0) {System.out.println("No login ID specified.  Connection aborted.");System.exit(0);}
    if(args.length==1) {
    	id = args[0];
    	host = "localhost";
        portInt = DEFAULT_PORT;
    } else if (args.length==2) {
    	id = args[0];
    	host = args[1];
        portInt = DEFAULT_PORT;
    } else {

    try
    {
      id   = args[0];
      host = args[1];
      port = args[2];
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
      host = "localhost";
      portInt = DEFAULT_PORT;
    }
  }
    ClientConsole chat= new ClientConsole(id,host, portInt);
    chat.accept();  //Wait for console data
  }
}
//End of ConsoleChat class
