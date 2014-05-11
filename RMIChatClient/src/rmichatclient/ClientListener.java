/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmichatclient;

import  SharedServices.IClientListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
/**
 *
 * @author mohammad
 */
public class ClientListener extends  UnicastRemoteObject  implements  IClientListener
{    
   private String userName;
   
   private String Fname;
   
   private String Lname;
   

    public ClientListener(String userName) throws RemoteException 
    {
        this.userName = userName;
    }

    
    @Override
    public void ReceiveMessage(String s) 
    {        
        //System.out.println(s);
        
        if(RMIChatClientForm.ta==null)
            System.out.println("Error in ");
        else

        RMIChatClientForm.ta.append(s+"\n");
    }

    
    /**
     * @return the userName
     */
    public String getuserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setuserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String GetID() 
    {
        return userName;
    }

    @Override
    public void getListeners(Vector<IClientListener>listeners) throws RemoteException 
    {
        RMIChatClientForm.Listeners=listeners;
        
        //System.out.println("Listeners List has been updated");
        
    }
    
}
