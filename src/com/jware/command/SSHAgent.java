package com.jware.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import com.jware.exception.SSHException;


/**
 * The SSHAgent allows a Java application to execute commands on a remote server via SSH
 * 
 * @author shaines
 *
 */
public class SSHAgent {
    
    /**
     * The hostname (or IP address) of the server to connect to
     */
    private String hostname;
    
    /**
     * The username of the user on that server
     */
    private String username;
    
    /**
     * The password of the user on that server
     */
    private String password;
    
    /**
     * A connection to the server
     */
    private Connection connection;
    
    /**
     * Creates a new SSHAgent
     * 
     * @param hostname
     * @param username
     * @param password
     */
    public SSHAgent( String hostname, String username, String password )
    {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
    }
    
    /**
     * Connects to the server
     * 
     * @return        True if the connection succeeded, false otherwise
     */
    public boolean connect() throws SSHException
    {
        try
        {
            // Connect to the server
            connection = new Connection( hostname );
            connection.connect();
            
            // Authenticate
            boolean result = connection.authenticateWithPassword( username, password );
            System.out.println( "Connection result: " + result );
            return result;
        }
        catch( Exception e )
        {
            throw new SSHException( "An exception occurred while trying to connect to the host: " + hostname + ", Exception=" + e.getMessage(), e ); 
        }
    }
    
    /**
     * Executes the specified command and returns the response from the server
     *  
     * @param command        The command to execute
     * @return               The response that is returned from the server (or null)
     */
    public String executeCommand( String command ) throws SSHException 
    {
        try
        {
            // Open a session
            Session session = connection.openSession();
            
            // Execute the command
            session.execCommand( command );
            
            // Read the results
            StringBuilder sb = new StringBuilder();
            InputStream stdout = new StreamGobbler( session.getStdout() );
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
            String line = br.readLine();
            while( line != null )
            {
                sb.append( line + "\n" );
                line = br.readLine();
            }

            // DEBUG: dump the exit code
            System.out.println( "ExitCode: " + session.getExitStatus() );

            // Close the session
            session.close();
            
            // Return the results to the caller
            return sb.toString();
        }
        catch( Exception e )
        {
            throw new SSHException( "An exception occurred while executing the following command: " + command + ". Exception = " + e.getMessage(), e );
        }
    }

    /**
     * Logs out from the server
     * @throws SSHException
     */
    public void logout() throws SSHException
    {
        try
        {
            connection.close();
        }
        catch( Exception e )
        {
            throw new SSHException( "An exception occurred while closing the SSH connection: " + e.getMessage(), e );
        }
    }
    
    /**
     * Returns true if the underlying authentication is complete, otherwise returns false
     * @return
     */
    public boolean isAuthenticationComplete()
    {
        return connection.isAuthenticationComplete();
    }
    
    public void putFile(byte[] data,String remoteFileName,String remoteTargetDirectory,String mode) throws IOException{
        if(this.connect()){
              SCPClient scp = new SCPClient(connection);
              scp.put(data, remoteFileName, remoteTargetDirectory,mode);
        }
    }
    
    public void getFile(String remoteFile,String localTargetDirectory) throws IOException{
        if(this.connect()){
              SCPClient scp = new SCPClient(connection);
              scp.get(remoteFile, localTargetDirectory);
        }
    }

    
    public static void main( String[] args ) throws IOException 
    {
        try
        {
            SSHAgent sshAgent = new SSHAgent( "172.19.5.10", "root", "rootroot" );
            File file = new File("C://FileIO//ReadString.txt");
            if( sshAgent.connect() ) 
            {
               FileInputStream fin = new FileInputStream("E:\\远程工具\\detail.htm");
               byte fileContent[] = new byte[(int)file.length()];
               fin.read(fileContent);
               sshAgent.putFile(fileContent, "detail.htm", "/root/Desktop/liuguangfu", "0755");
               sshAgent.logout();
            }
        }
        catch( SSHException e )
        {
            e.printStackTrace();
        }
    }
}
