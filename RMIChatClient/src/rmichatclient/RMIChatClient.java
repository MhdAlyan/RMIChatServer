/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmichatclient;

import SharedServices.IServerServices;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author mohammad
 */
public class RMIChatClient {

    /**
     * @param args the command line arguments
     */
    
    public  static  IServerServices  rem_obj=null;    
    
    static void print_rooms() throws RemoteException
    {
           String [] res =  rem_obj.ListAvailableRooms(); 
           
            for (int i = 0; i < res.length-1; i++) 
            {
                if(res[i]!=null) 
                    System.out.println(res[i]);
                
            }
    
    }
    
    public static void main1(String[] args) throws AccessException, NotBoundException 
    {
        try {
            ClientListener clientListener=new ClientListener("mohammad");
            
            Registry reg=LocateRegistry.getRegistry("localhost", 6000);
            
            Remote ref=  reg.lookup("rmiServer");
            
            rem_obj=(IServerServices)ref;
            
            rem_obj.AddChatRoom("123");
            
            rem_obj.AddChatRoom("456");
            
            rem_obj.AddChatRoom("ABCsdsd");
            
            
            print_rooms();
            
            System.out.println(rem_obj.removeChatRoom("456")); 
            
            
            print_rooms();
            
            
           
            rem_obj.signup("mohammad","123","M","A");
             
           
            rem_obj.signup("Ahmad","123","A","K");  
            
            rem_obj.signup("mahmoud_807","123","Mahmoud","shahoud");              
            
            
            
            System.out.println("before sign in");
             print_client_in_room("123");
             
             
           
           rem_obj.signin("mohammad","123","123");
           
           rem_obj.signin("Ahmad","123","123");
           
           rem_obj.signin("mahmoud_807","123","123");                         
           
           
           System.out.println("after sign in ");
           
           print_client_in_room("123");
           
           
           
           //rem_obj.signout("mohammad","123"); 
            
           //rem_obj.signout("Ahmad","123"); 
            
           
           //System.out.println("after sign out ");
           
           //print_client_in_room("123");
           
           
           rem_obj.AddReceiveListener(clientListener);
           
           
           rem_obj.AddReceiveListener(new ClientListener("Ahmad"));
           
           rem_obj.AddReceiveListener(new ClientListener("mahmoud_807"));
           
           
           
           
           
           
           
           //rem_obj.SendMessage("Hello","Ahmad","123");
           
           rem_obj.SendBroadCatMessage("mohammad", "Brodcast","123");
           
           
           
           
           
           
           
           
           
           
            
            
            
            
            
            
            
         
        } catch (RemoteException ex) 
        {
            Logger.getLogger(RMIChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    private static void print_client_in_room(String name) throws RemoteException 
    {
        String[] res= rem_obj.ListClientsInRoom(name);
        
        for (int i = 0; i < res.length; i++) 
        {
            if(res[i]!=null)
                System.out.println(res[i]); 
            
        }
        
    }
}
