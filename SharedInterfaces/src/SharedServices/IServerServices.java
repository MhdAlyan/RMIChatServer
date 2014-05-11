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

public interface IServerServices extends Remote
{
    int   AddChatRoom(String name)throws RemoteException ; 
    int   AddChatRoom(String name,int capacity)throws RemoteException;
    int   removeChatRoom(String name)throws RemoteException ;
    int   signup(String userName, String password ,String FName ,String LName)throws RemoteException;
    int   signin(String userName, String password,String roomName)throws RemoteException;
    int   signout(String userName,String roomName)throws RemoteException;
String [] ListAvailableRooms()throws RemoteException;
String [] ListClientsInRoom(String name)throws RemoteException;
    int   SendMessage(String m ,String to,String roomName)throws RemoteException;
    int   SendBroadCatMessage(String userName,String m,String roomName)throws RemoteException;   
    void  AddReceiveListener(IClientListener c)throws RemoteException;
    void  RemoveReceiveListener(IClientListener c)throws RemoteException;               
}
