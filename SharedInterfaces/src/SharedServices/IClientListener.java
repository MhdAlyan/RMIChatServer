/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SharedServices;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

/**
 *
 * @author mohammad
 */

public interface IClientListener  extends Remote
{
     void ReceiveMessage(String s)throws RemoteException;
     String GetID()throws RemoteException;
     void getListeners(Vector<IClientListener>listeners)throws RemoteException;
}
