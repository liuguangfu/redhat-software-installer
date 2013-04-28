package com.jware.command;

import java.io.File;

import com.jware.utils.SSH2UpLoader;


public class SoftwareInstaller {
	
	public static final String INSTALL_DEFAULT_ROOT_PATH="/usr/local";
	public static final String SAVE_DEFAULT_PATH ="Desktop/JSoft";
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
    
    private String localFileURL;
    
    private SSHAgent sshAgent;
    
    
    
    public SoftwareInstaller( String hostname, String username, String password,String localFileURL)
    {
        this.localFileURL = localFileURL;
        this.sshAgent = new SSHAgent(hostname,username,password);
    }
    
	public void downloadSoftware(){
		
	}

	
    public String getInstallScript(String savePath){
    	String fileName = localFileURL.substring(localFileURL.lastIndexOf(File.separator) + 1,localFileURL.length()); 
    	String name =null;
    	if(fileName.lastIndexOf(".t")>0){
    	  name = fileName.substring(0,fileName.lastIndexOf(".t"));
    	}else{
    	 name = fileName.substring(0,fileName.lastIndexOf(".zip"));	
    	}
    	ShellScriptGenerator shellScriptGenerator = new ShellScriptGenerator();
    	if(name!=null&&name.contains("apr")&&!name.contains("apr-util")){
    		return shellScriptGenerator.generateShellScript(fileName, INSTALL_DEFAULT_ROOT_PATH, savePath, name, "", "apache-prepare-tar.lsh");
    	}
    	
    	if(name!=null&&name.contains("apr-util")){
    		return shellScriptGenerator.generateShellScript(fileName, INSTALL_DEFAULT_ROOT_PATH, savePath, name, "--with-apr="+INSTALL_DEFAULT_ROOT_PATH+"/"+"apr-1.4.5", "apache-prepare-tar.lsh");
    	}
    	
    	if(name!=null&&name.contains("pcre")){
    		return shellScriptGenerator.generateShellScript(fileName, INSTALL_DEFAULT_ROOT_PATH, savePath, name, "", "apache-prepare-zip.lsh");
    	}
    	
    	if(name!=null&&name.contains("httpd")){
    		return shellScriptGenerator.generateShellScript(fileName, INSTALL_DEFAULT_ROOT_PATH, savePath, name, "--with-apr="+INSTALL_DEFAULT_ROOT_PATH+"/"+"apr-1.4.5"+" --with-apr-util="+INSTALL_DEFAULT_ROOT_PATH+"/"+"apr-util-1.3.12"+" --with-pcre="+INSTALL_DEFAULT_ROOT_PATH+"/"+"pcre-8.10", "apache-install.lsh");
    	}
    	
    	if(name!=null&&name.contains("jdk")){
    		return shellScriptGenerator.generateShellScript(fileName, INSTALL_DEFAULT_ROOT_PATH, savePath, name, "", "jdk-install.lsh");
    	}
    	
    	if(name!=null&&name.contains("maven")){
    		return shellScriptGenerator.generateShellScript(fileName, INSTALL_DEFAULT_ROOT_PATH, savePath, name, "", "free-installation-tar.lsh");
    	}
    	if(name!=null&&name.contains("resin")){
    		return shellScriptGenerator.generateShellScript(fileName, INSTALL_DEFAULT_ROOT_PATH, savePath, name, "", "free-installation-tar-gz.lsh");
    	}
    	return null;
    }
    
    public String install(String savePath){
    	 String result = null;
    	 if(sshAgent.connect()) 
         {
    		 result = sshAgent.executeCommand(getInstallScript(savePath));
         }
    	 sshAgent.logout();
    	 return result;
    }
    
    
    
    public void logout(){
    	sshAgent.logout();
    }
    
    public static void main(String[] args){
//    	SSH2UpLoader.uploadSoftware("172.19.5.10", "root", "rootroot", "E:\\远程工具\\软件\\maven.tar", "maven.tar", "/root/Desktop/JSoft");
//    	SoftwareInstaller softwareInstaller = new SoftwareInstaller("172.19.5.10", "root", "rootroot","E:\\远程工具\\软件\\maven.tar");
//    	softwareInstaller.install();
//    	SSH2UpLoader.uploadSoftware("172.19.5.10", "root", "rootroot", "E:\\远程工具\\软件\\resin-3.0.23.tar.gz", "resin-3.0.23.tar.gz", "/root/Desktop/JSoft");
//    	SoftwareInstaller softwareInstaller = new SoftwareInstaller("172.19.5.10", "root", "rootroot","E:\\远程工具\\软件\\resin-3.0.23.tar.gz");
//    	System.out.println(softwareInstaller.getInstallScript());
//    	SSH2UpLoader.uploadSoftware("172.19.5.10", "root", "rootroot", "E:\\远程工具\\软件\\jdk1.6.0_02.tgz", "jdk1.6.0_02.tgz", "/root/Desktop/JSoft");
//    	SoftwareInstaller softwareInstaller = new SoftwareInstaller("172.19.5.10", "root", "rootroot","E:\\远程工具\\软件\\jdk1.6.0_02.tgz");
//    	System.out.println(softwareInstaller.getInstallScript());
//    	SSH2UpLoader.uploadSoftware("172.19.5.10", "root", "rootroot", "E:\\远程工具\\apr-1.4.5.tar.gz", "apr-1.4.5.tar.gz", "/root/Desktop/JSoft");
//    	SoftwareInstaller softwareInstaller = new SoftwareInstaller("172.19.5.10", "root", "rootroot","E:\\远程工具\\apr-1.4.5.tar.gz");
//    	System.out.println(softwareInstaller.getInstallScript());
//    	SSH2UpLoader.uploadSoftware("172.19.5.10", "root", "rootroot", "E:\\远程工具\\apr-util-1.3.12.tar.gz", "apr-util-1.3.12.tar.gz", "/root/Desktop/JSoft");
//    	SoftwareInstaller softwareInstaller = new SoftwareInstaller("172.19.5.10", "root", "rootroot","E:\\远程工具\\apr-util-1.3.12.tar.gz");
//    	System.out.println(softwareInstaller.getInstallScript());
//    	SSH2UpLoader.uploadSoftware("172.19.5.10", "root", "rootroot", "E:\\远程工具\\pcre-8.10.zip", "pcre-8.10.zip", "/root/Desktop/JSoft");
//    	SoftwareInstaller softwareInstaller = new SoftwareInstaller("172.19.5.10", "root", "rootroot","E:\\远程工具\\pcre-8.10.zip");
//    	System.out.println(softwareInstaller.getInstallScript());
//    	SSH2UpLoader.uploadSoftware("172.19.5.10", "root", "rootroot", "E:\\远程工具\\软件\\httpd-2.2.8.tar.gz", "httpd-2.2.8.tar.gz", "/root/Desktop/JSoft");
//    	SoftwareInstaller softwareInstaller = new SoftwareInstaller("172.19.5.10", "root", "rootroot","E:\\远程工具\\软件\\httpd-2.2.8.tar.gz");
//    	System.out.println(softwareInstaller.getInstallScript());
    }
    
   

}
