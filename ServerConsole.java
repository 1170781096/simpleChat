import common.ChatIF;
import java.io.*;
import java.util.Scanner;

import client.ChatClient;

public class ServerConsole implements ChatIF {

	@Override
	public void display(String message) {
		System.out.println("SERVER MSG> " + message);

	}
	final public static int DEFAULT_PORT = 5555;
	  
	  //Instance variables **********************************************
	  
	  /**
	   * The instance of the client that created this ConsoleChat.
	   */
	  EchoServer server;
	  
	  
	  
	  
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
	  public ServerConsole(int port) 
	  {
		  server = new EchoServer(port,this);
		    try 
		    {
		      server.listen(); //Start listening for connections
		    } 
		    catch (Exception ex) 
		    {
		      System.out.println("ERROR - Could not listen for clients!");
		    }
	    
	    // Create scanner object to read from console
	    fromConsole = new Scanner(System.in); 
	  }
	  
	  public void accept() 
	  {
	    try
	    {

	      String message;

	      while (true) 
	      {
	        message = fromConsole.nextLine();
	        int ifCommand = message.indexOf("#");
	        if(ifCommand != 0) {
	        server.handleMessageFromServerUI(message);
	        }  
	        if(ifCommand == 0) {
	        	
	        	int port2;
	        	if (message.equals("#close")) {server.close();}
	        	if (message.equals("#stop")) {server.stopListening();}
	        	if (message.equals("#quit")) {server.close();System.exit(0);}
	        	if (message.equals("#start")) {
	        		if(server.isListening() == true) {System.out.println("the server is not stop listening");} else {
	        			server.listen();
	        		}
	        	}
	        	if(message.equals("#getport")) {System.out.println(server.getPort());}
	        	
	        	if(message.indexOf("#setport")!=-1) {
	        		if(server.ifClosed==false) {System.out.println("the server is not closed");} else {
	        		port2 = Integer.parseInt(message.substring(9,message.indexOf(">")));
	        		server.setPort(port2);
	        		System.out.println("Port set to: "+port2);
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
	  
	  public static void main(String[] args) 
	  {
		
	    
	    int port = 0;
	    

	    try
	    {
	    
	    	port = Integer.parseInt(args[0]);
	    }
	    catch(Throwable t)
	    {
	      port = DEFAULT_PORT; //Set port to 5555
	    }
	    
	    
	   
	    
	    ServerConsole svConsole = new ServerConsole(port);
	    svConsole.accept();
	    
	  }
	
	
	
	
	
	
	
	
	
	
}
