package com.jware.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.jware.exception.BaseException;
import com.jware.utils.TemplateLoader;

public class ShellScriptGenerator {

	public static final String INSTALL_DEFAULT_ROOT_PATH="/usr/local";
	public static final String SAVE_DEFAULT_PATH ="Desktop/lgfu";
	public static final String INSTALL_PATH ="#install-path#";
	public static final String SAVE_PATH ="#save-path#";
	public static final String FULL_NAME ="#full-name#";
	public static final String NAME ="#name#";
	public static final String PARAMETER ="#parameter#";
	
	public String generateShellScript(String fullName,String installPath,String savePath,String name,String parameter,String template){
		//String path = getClass().getProtectionDomain().getCodeSource().getLocation().toString().substring(6);
		BufferedReader bReader =  new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("com/jware/template/"+template)));
		//BufferedReader bReader = TemplateLoader.loadTemplate(path+"/com/jware/template/",template);
		String line = null;
		String command = null;
		try {
			while((line =bReader.readLine())!=null){
				if(command==null){
					command = line.replaceAll(INSTALL_PATH, installPath+"/"+name).replaceAll(SAVE_PATH, savePath).replaceAll(FULL_NAME, fullName).replace(NAME, name).replaceAll(PARAMETER, parameter);
				}else{
					command = command+" && "+line.replaceAll(INSTALL_PATH, installPath+"/"+name).replaceAll(SAVE_PATH, savePath).replaceAll(FULL_NAME, fullName).replace(NAME, name).replaceAll(PARAMETER, parameter);
				}
			}
		} catch (IOException e) {
			throw new BaseException(e);
		}
		return command;
	}
	
	public static void main(String[] args){
		ShellScriptGenerator shellScriptGenerator = new ShellScriptGenerator();
		//System.out.println(shellScriptGenerator.generateAPRScript("apr-1.4.5.tar.gz", "/usr/local", "Desktop/lgfu", "apr-1.4.5"));
		//System.out.println(shellScriptGenerator.generateAPRUtilUScript("apr-util-1.3.12.tar.gz", "/usr/local", "Desktop/lgfu", "apr-util-1.3.12","--with-apr="+"/usr/local"+"/"+"apr-1.4.5"));
		//System.out.println(shellScriptGenerator.generatePcreScript("pcre-8.10.zip", "/usr/local", "Desktop/lgfu", "pcre-8.10",""));
		//System.out.println(shellScriptGenerator.generateApacheScript("httpd-2.4.4.tar.gz", "/usr/local", "Desktop/lgfu", "httpd-2.4.4"));
		//System.out.println(shellScriptGenerator.generateJDKScript("jdk1.6.0_02.tgz", "/usr/local", "Desktop/lgfu", "jdk1.6.0_02"));
		//System.out.println(shellScriptGenerator.generateShellScript("jdk1.6.0_02.tgz", "/usr/local", "Desktop/lgfu", "jdk1.6.0_02","","jdk-install.lsh"));
		//System.out.println(shellScriptGenerator.generateShellScript("maven.tar", "/usr/local", "Desktop/lgfu", "maven","","free-installation.lsh"));
		System.out.println(shellScriptGenerator.generateShellScript("resin-3.0.23.tar.gz", "/usr/local", "Desktop/lgfu", "resin-3.0.23","","free-installation.lsh"));
	}
}
