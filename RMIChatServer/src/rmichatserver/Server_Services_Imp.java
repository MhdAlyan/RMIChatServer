/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmichatserver;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import  SharedServices.*;

/**
 *
 * @author mohammad
 */
public class Server_Services_Imp extends UnicastRemoteObject implements IServerServices 
{
    public static Vector<ServerChatRoom>Rooms=new Vector<ServerChatRoom>(0);
    
    public static  Vector<IClientListener>Listeners=new Vector<IClientListener>(0);
    
    public static  Vector<UserAccount>offlineUserAccounts=new Vector<UserAccount>(0);
    
 
    public Server_Services_Imp()throws RemoteException
    {
        
        super();
        
    }
    
    
    @Override
    public    int AddChatRoom(String name) 
    {
         ServerChatRoom chatRoom=new ServerChatRoom(name);
         
              
         if(!Rooms.contains(chatRoom))
         {
             Rooms.add(chatRoom);
                          
             return 1;
         }
         
         return  -1;  
    }

    @Override   
    public  int AddChatRoom(String name, int capacity) 
    {
         ServerChatRoom chatRoom=new ServerChatRoom(name,capacity);
         
         if(!Rooms.contains(chatRoom))
         {
             Rooms.add(chatRoom); //add method is synchronized
             return 1;
         }
         
         return  -1;      
         
    }

    @Override
    public  int  removeChatRoom(String name) 
    {
        ServerChatRoom chatRoom=new ServerChatRoom(name);
        
         if(Rooms.contains(chatRoom))
         {
             Rooms.removeElement(chatRoom);
             return 1;
         }
         
         return  -1; 
         
    }
    

    @Override
    public int signup(String userName, String password, String FName, String LName) 
    {
        UserAccount userAccount=new UserAccount(userName, password, FName, LName);
        
        //Write_object from DB
        
        // if username is used from another account ==>  signup fail
        if(offlineUserAccounts.contains(userAccount))
             return  -1;
        else
        {
            offlineUserAccounts.add(userAccount);
            
            System.out.println("Client "+userAccount +" registered Successfully");
            
            return  1;
        }
    }

    @Override
    public int signin(String userName, String password,String roomName) 
    {
            UserAccount userAccount=new UserAccount(userName, password);
            
            //getData from DB and Matching with username, password
        
            //if ok  check is the roomName is Exisit 
        
            ServerChatRoom room=new ServerChatRoom(roomName);
            
            if ( (Rooms.contains(room)))
                
            {
                int i=Rooms.indexOf(room);
                
                int j=offlineUserAccounts.indexOf(userAccount);
                
                if(j!=-1)
                {
                    UserAccount obj=offlineUserAccounts.elementAt(j);
                    
                    if ((obj.getuserName().equals(userName)) && (obj.getPassword().equals(password)))
                    {
                        
                        userAccount.setFirstName(obj.getFirstName());
                        
                        userAccount.setLastName(obj.getLastName());
                        
                        
                        
                        //add user to online user in this room 
                        Rooms.elementAt(i).addUser(userAccount);
                        
                        System.out.println("Client "+obj +" logged in  Successfully");                      
                    }
                    else
                        return  -1;
                }
                
               //broadcast to all , That is a new user is signed in c 
            }
            else 
            {
                return  -1;
            }
           
            return  1;
    }

    
    
    
    

    @Override
    public int signout(String userName,String roomName) 
    {
           UserAccount userAccount=new UserAccount(userName,"");
            
            //getData from DB and Matching with username, password
        
            //if ok  check is the roomName is Exisit 
        
            ServerChatRoom room=new ServerChatRoom(roomName);
            
            if (Rooms.contains(room))
            {
                 int i=Rooms.indexOf(room);
                
                int j=offlineUserAccounts.indexOf(userAccount);
                
                if(j!=-1)
                {
                    UserAccount obj=offlineUserAccounts.elementAt(j);
                    if ((obj.getuserName().equals(userName)))
                         Rooms.elementAt(i).removeUser(userAccount); //remove  user from online user in this room 
                    
                  System.out.println("Client "+obj +" signed out Successfully");
                }
                
               //broadcast to all , That is a new user is signed  out
                
               return  1;
            }
            else 
            {   
                return  -1;
            }
            
    }

    
    
    
    
    @Override
    public String[] ListAvailableRooms() 
    {
        String [] res=new String[Rooms.capacity()];
        
        int i=0;
        
        for (Iterator<ServerChatRoom> it = Rooms.iterator(); it.hasNext(); ) 
        {
            ServerChatRoom serverChatRoom = it.next();
            
             res[i]=serverChatRoom.getRoomName();
             
             i++;
        }
        
        return  res;
    }

    @Override
    public String[] ListClientsInRoom(String roomName) 
    {
          ServerChatRoom chatRoom=new ServerChatRoom(roomName);
          
          //if room is exsist
          
          String  [] res=null;
          
          if (Rooms.contains(chatRoom))
          {
              int i=Rooms.indexOf(chatRoom);
              
              int size=Rooms.elementAt(i).getOnline_users().size();
              
              int j=0;
              
              res=new String[size];
              
              for (Iterator<UserAccount> it =(Rooms.elementAt(i).getOnline_users().iterator()); it.hasNext();) 
              {
                  UserAccount userAccount = it.next();
                  
                  res[j]=userAccount.toString();
                 
                  j++; 
              }
  
          }          
          return  res;
       
    }
     
    //Return index of the user in a specific room 
     int search4userinroom(String userName,String roomName)
     {
        ServerChatRoom chatRoom=new ServerChatRoom(roomName);
                
          if (Rooms.contains(chatRoom)) // must be override equal emethod 
          {
              int i=Rooms.indexOf(chatRoom);
               
              int j=0;
              
              for (Iterator<UserAccount> it =(Rooms.elementAt(i).getOnline_users().iterator()); it.hasNext();) 
              {
                  UserAccount userAccount = it.next();
                  
                  if(userAccount.getuserName().equals(userName))
                         return j;
                  j++;
              }
          }
          
          return  -1;
     }
    
    
    @Override
    
    public int SendMessage(String m, String DesUeruserName,String roomName) throws RemoteException 
    {
        
        //Search for DesuseruserName in Room that have room name
        int  index=search4userinroom(DesUeruserName, roomName);
        
        if(index!=-1)
        {
            //Search for user in Listeners List
            //and Call Recive messeage 
                       
            //System.out.println("index= "+index);
            
           IClientListener des=getClientListener(DesUeruserName);
           
           
           //System.out.println("iclient lister= "+des.GetID());
           
           des.ReceiveMessage(m);
           
           return  1; 
        }
        
        return  -1;
    }
    
    
    
    @Override
    public int SendBroadCatMessage(String userName, String m,String roomName) throws RemoteException
    {
        //Search for DesuseruserName in Room that have room name
        
        int i=Rooms.indexOf(new ServerChatRoom(roomName));
        
        if(i!=-1)
        {
                       
          Iterator<IClientListener> i1=(Iterator<IClientListener>)(Listeners.iterator());
          
          IClientListener iClientListener=null;
          
          while(i1.hasNext())
          {
            iClientListener=i1.next();
            
            if(!iClientListener.GetID().equals(userName))
                 iClientListener.ReceiveMessage(m);
             
          }
          
          return  1; 
        }
       
        return  -1;
    }
    
    

    @Override
    public void AddReceiveListener(IClientListener c)throws RemoteException
    {

        Listeners.add(c);
        
        System.out.println("adding listeners ");
        
          Iterator<IClientListener> i1=(Iterator<IClientListener>)(Listeners.iterator());
          
          IClientListener iClientListener=null;
          
          while(i1.hasNext())
          {
              iClientListener=i1.next();
            
              iClientListener.getListeners(Listeners);
          }
    }

    @Override
    public void RemoveReceiveListener(IClientListener c) throws RemoteException 
    {
        Listeners.remove(c);
        
        System.out.println("Reoving listeners ");        
        
          Iterator<IClientListener> i1=(Iterator<IClientListener>)(Listeners.iterator());
          
          IClientListener iClientListener=null;
          
          while(i1.hasNext())
          {
              iClientListener=i1.next();
            
              iClientListener.getListeners(Listeners);
          }
        
        
        
    }
    
    IClientListener getClientListener(String userName) throws RemoteException
    {
        for (Iterator<IClientListener> it = Listeners.iterator(); it.hasNext();) 
        {
            IClientListener iClientListener = it.next();
            
            if(iClientListener.GetID().equals(userName))
                 return  iClientListener;
        }
        
        return  null;
    }

    
    
    
    public static void main(String[] args)
    {
        try 
        {

            Registry reg = LocateRegistry.createRegistry(6000)  ;
                        
            Server_Services_Imp obj=new Server_Services_Imp();            

            String name = "rmiServer";

            reg.rebind(name,obj);
            
            
  
            System.out.println("Server ready mode ... ");
            
                        
    
        }
        catch (Exception ex) 
        {
            System.out.println(ex);
        }
    }

    
}

