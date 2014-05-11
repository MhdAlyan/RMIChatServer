/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmichatserver;


import java.util.Vector;

/**
 *
 * @author mohammad
 */
public class ServerChatRoom 
{
    private String roomName;
    
    private  int capacity=0;//optional
    
    private Vector<UserAccount>online_users =new Vector<UserAccount>();
    
    
    public ServerChatRoom(String rname) 
    {
        this.roomName=rname;
        
    }
    
    public ServerChatRoom(String roomName, int capacity) 
    {
        this.roomName = roomName;
        
        this.capacity = capacity;
    }
    
    /**
     * @return the roomName
     */
    public String getRoomName() 
    {
        return roomName;
    }

    /**
     * @param roomName the roomName to set
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    
    
    /**
     * @param capacity the capacity to set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

 
    
    
    
    //------------------------------------
    @Override
    public boolean equals(Object obj) 
    {
        
        if(this.roomName.equals(((ServerChatRoom)obj).getRoomName() ))
            return true;
        
        return  false;
    }
    
    
    
    
    public  void addUser(UserAccount u)
    {
        if(!online_users.contains(u))
            online_users.add(u);
        else
            System.out.println("Duplicated username");
    }

    public  void removeUser(UserAccount u)
    {
        if(online_users.contains(u))
            online_users.remove(u);
        else
            System.out.println("username Was Not found");
    }
    
    
    
    /**
     * @return the online_users
     */
    public Vector<UserAccount> getOnline_users() 
    {
        return online_users;
    }
    
    
    
    
       
}
