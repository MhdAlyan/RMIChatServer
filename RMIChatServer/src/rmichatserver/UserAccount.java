/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmichatserver;

/**
 *
 * @author mohammad
 */
public class UserAccount 
{
    
    private String userName;
    
    private String Password;
    
    private String FirstName;
    
    private String LastName;

    public UserAccount() 
    {   
        
    }
    
    public UserAccount(String userName, String Password, String FirstName, String LastName) 
    {
        this.userName = userName;
        this.Password = Password;
        this.FirstName = FirstName;
        this.LastName = LastName;
    }
    
    public UserAccount(String userName, String Password) 
    {
        this.userName = userName;
        
        this.Password = Password;
    }
    
    

    /**
     * @return the Email
     */
    public String getuserName() {
        return userName;
    }

    /**
     * @param Email the Email to set
     */
    public void setuserName(String Email) {
        this.userName = Email;
    }

    /**
     * @return the Password
     */
    public String getPassword() {
        return Password;
    }

    /**
     * @param Password the Password to set
     */
    public void setPassword(String Password) {
        this.Password = Password;
    }

    /**
     * @return the FirstName
     */
    public String getFirstName() {
        return FirstName;
    }

    /**
     * @param FirstName the FirstName to set
     */
    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    /**
     * @return the LastName
     */
    public String getLastName() {
        return LastName;
    }

    /**
     * @param LastName the LastName to set
     */
    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    @Override
    public String toString() 
    {
        return userName;
    }

    @Override
    public boolean equals(Object obj) 
    {
           if((this.userName.equals(((UserAccount)obj).getuserName()))) 
               return  true;
           
           return  false;
               
    }

    
    
   
}
