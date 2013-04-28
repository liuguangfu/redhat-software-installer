package com.jware.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.jware.command.SSHAgent;
import com.jware.exception.BaseException;
import com.jware.exception.SSHException;

public class SSH2UpLoader {
	
	public static void uploadSoftware(String hostname,String username,String password,String localFileURL,String name,String remoteSavePath){
		SSHAgent sshAgent = new SSHAgent(hostname,username,password);
   	 try{
            File file = new File(localFileURL);
            if( sshAgent.connect() ) 
            {
               FileInputStream fin = new FileInputStream(localFileURL);
               byte fileContent[] = new byte[(int)file.length()];
               fin.read(fileContent);
               sshAgent.putFile(fileContent,name,remoteSavePath,"0755");
               sshAgent.logout();
            }
        }catch( SSHException e ){
           throw new BaseException(e);
        }catch (FileNotFoundException e) {
       	throw new BaseException(e);
	    }catch (IOException e) {
	    	throw new BaseException(e);
		}finally{
			 sshAgent.logout();
		}
	}
	
	public static void uploadSoftware(String hostname,String username,String password,byte[] fileContent,String name,String remoteSavePath){
		 SSHAgent sshAgent = new SSHAgent(hostname,username,password);
	   	 try{
	            if( sshAgent.connect() ) 
	            {
	               sshAgent.putFile(fileContent,name,remoteSavePath,"0755");
	               sshAgent.logout();
	            }
	        }catch( SSHException e ){
	           throw new BaseException(e);
	        }catch (FileNotFoundException e) {
	       	throw new BaseException(e);
		     }catch (IOException e) {
		    	throw new BaseException(e);
			 }finally{
				 sshAgent.logout();
			 }
		}
	
	public static void downloadFile(String hostname,String username,String password,String remoteFile,String localTargetDirectory){
		SSHAgent sshAgent = new SSHAgent(hostname,username,password);
		try{
            if( sshAgent.connect() ) 
            {
               sshAgent.getFile(remoteFile, localTargetDirectory);
               sshAgent.logout();
            }
        }catch( SSHException e ){
           throw new BaseException(e);
        }catch (FileNotFoundException e) {
       	throw new BaseException(e);
	    }catch (IOException e) {
	    	throw new BaseException(e);
		}finally{
			 sshAgent.logout();
		 }
	}
	
	public static void isDirectoryExist(String hostname,String username,String password,String remoteTargetDirectory){
		 SSHAgent sshAgent = new SSHAgent(hostname,username,password);
		 try{
		 if(sshAgent.connect()) 
         {
    		  sshAgent.executeCommand("rm -rf "+remoteTargetDirectory+" && "+"mkdir "+remoteTargetDirectory);
         }
    	 sshAgent.logout();
		 }finally{
			 sshAgent.logout();
		 }
	}
	
	public static void main(String[] args){
		SSH2UpLoader.downloadFile("172.19.5.10","root", "rootroot", ".bashrc", "E:\\远程工具\\软件");
	}

}
