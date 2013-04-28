package com.jware.ui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.jware.command.SoftwareInstaller;
import com.jware.utils.PropertiesLoader;
import com.jware.utils.SSH2UpLoader;
import com.jware.utils.TemplateLoader;

public class TerminalUI {

	private JFrame frame;
	private JTextField userNameField;
	private JTextField jdkField;
	private JTextField apacheField;
	private JTextField ipField;
	private JPasswordField passwordField;
	private JTextField mavenField;
	private JTextField resionField;
	private JEditorPane editorPane;
	private JCheckBox jdkCkBox;
	private JCheckBox apcheCkBox;
	private JCheckBox mavenCkBox ;
	private JCheckBox resinCkBox;
	
	private static String APR_SOFTWARE_NAME = "apr-1.4.5.tar.gz";
	private static String APR_UTIL_SOFTWARE_NAME = "apr-util-1.3.12.tar.gz";
	private static String PCRE_SOFTWARE_NAME = "pcre-8.10.zip";
	private JLabel lblNewLabel;
	private JTextField serverPathField;
	private JButton btnNewButton;
	private Preferences prefs;



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TerminalUI window = new TerminalUI();
					window.frame.setVisible(true);
					window.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TerminalUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setSize(480, 480);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0, 0, 0, 0, 0, 10};
		gridBagLayout.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 10};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		prefs = Preferences.userRoot().node(this.getClass().getName());
		lblNewLabel = new JLabel("服务器文件路径");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		frame.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		serverPathField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 0;
		frame.getContentPane().add(serverPathField, gbc_textField);
		serverPathField.setColumns(10);
		serverPathField.setText(prefs.get("serverPath",null));
		
		btnNewButton = new JButton("保存配置");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				prefs.put("serverPath", serverPathField.getText());
				prefs.put("ip", ipField.getText());
				prefs.put("username", userNameField.getText());
				prefs.put("password", String.valueOf(passwordField.getPassword()));
				prefs.put("jdkPath", jdkField.getText());
				prefs.put("apachePath", apacheField.getText());
				prefs.put("mavenPath", mavenField.getText());
				prefs.put("resinPath", resionField.getText());
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 5;
		gbc_btnNewButton.gridy = 0;
		frame.getContentPane().add(btnNewButton, gbc_btnNewButton);
		
		JLabel lblIp = new JLabel("IP地址:");
		lblIp.setVerticalAlignment(SwingConstants.TOP);
		lblIp.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblIp = new GridBagConstraints();
		gbc_lblIp.anchor = GridBagConstraints.EAST;
		gbc_lblIp.insets = new Insets(0, 0, 5, 5);
		gbc_lblIp.gridx = 1;
		gbc_lblIp.gridy = 1;
		frame.getContentPane().add(lblIp, gbc_lblIp);
		
		ipField = new JTextField();
		GridBagConstraints gbc_ipField = new GridBagConstraints();
		gbc_ipField.insets = new Insets(0, 0, 5, 5);
		gbc_ipField.fill = GridBagConstraints.HORIZONTAL;
		gbc_ipField.gridx = 2;
		gbc_ipField.gridy = 1;
		frame.getContentPane().add(ipField, gbc_ipField);
		ipField.setColumns(10);
		ipField.setText(prefs.get("ip",null));
		
		JButton upLoadBtn = new JButton("安装");
		upLoadBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				upLoadEvent(e);
			}
		});
		upLoadBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_upLoadBtn = new GridBagConstraints();
		gbc_upLoadBtn.insets = new Insets(0, 0, 5, 0);
		gbc_upLoadBtn.gridx = 5;
		gbc_upLoadBtn.gridy = 1;
		frame.getContentPane().add(upLoadBtn, gbc_upLoadBtn);
		
		JLabel label = new JLabel("用户名:");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.gridx = 1;
		gbc_label.gridy = 2;
		frame.getContentPane().add(label, gbc_label);
		
		userNameField = new JTextField();
		userNameField.setToolTipText("JDK本地路径");
		GridBagConstraints gbc_userNameField = new GridBagConstraints();
		gbc_userNameField.insets = new Insets(0, 0, 5, 5);
		gbc_userNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_userNameField.gridx = 2;
		gbc_userNameField.gridy = 2;
		frame.getContentPane().add(userNameField, gbc_userNameField);
		userNameField.setColumns(10);
		userNameField.setText(prefs.get("username",null));
		
		JLabel label_1 = new JLabel("密码:");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.anchor = GridBagConstraints.EAST;
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 3;
		frame.getContentPane().add(label_1, gbc_label_1);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 3;
		frame.getContentPane().add(passwordField, gbc_passwordField);
		passwordField.setText(prefs.get("password",null));
		
		JLabel lblJdk = new JLabel("JDK路径:");
		GridBagConstraints gbc_lblJdk = new GridBagConstraints();
		gbc_lblJdk.insets = new Insets(0, 0, 5, 5);
		gbc_lblJdk.anchor = GridBagConstraints.EAST;
		gbc_lblJdk.gridx = 1;
		gbc_lblJdk.gridy = 4;
		frame.getContentPane().add(lblJdk, gbc_lblJdk);
		
		jdkField = new JTextField();
		jdkField.setToolTipText("Tomcat本地路径");
		GridBagConstraints gbc_jdkField = new GridBagConstraints();
		gbc_jdkField.insets = new Insets(0, 0, 5, 5);
		gbc_jdkField.fill = GridBagConstraints.HORIZONTAL;
		gbc_jdkField.gridx = 2;
		gbc_jdkField.gridy = 4;
		frame.getContentPane().add(jdkField, gbc_jdkField);
		jdkField.setColumns(10);
		jdkField.setText(prefs.get("jdkPath",null));
		
		jdkCkBox = new JCheckBox("");
		GridBagConstraints gbc_jdkCkBox = new GridBagConstraints();
		gbc_jdkCkBox.insets = new Insets(0, 0, 5, 5);
		gbc_jdkCkBox.gridx = 3;
		gbc_jdkCkBox.gridy = 4;
		frame.getContentPane().add(jdkCkBox, gbc_jdkCkBox);
		
		JLabel lblApache = new JLabel("Apache路径:");
		GridBagConstraints gbc_lblApache = new GridBagConstraints();
		gbc_lblApache.insets = new Insets(0, 0, 5, 5);
		gbc_lblApache.anchor = GridBagConstraints.EAST;
		gbc_lblApache.gridx = 1;
		gbc_lblApache.gridy = 5;
		frame.getContentPane().add(lblApache, gbc_lblApache);
		
		apacheField = new JTextField();
		apacheField.setToolTipText("Resin本地路径");
		GridBagConstraints gbc_apacheField = new GridBagConstraints();
		gbc_apacheField.insets = new Insets(0, 0, 5, 5);
		gbc_apacheField.fill = GridBagConstraints.HORIZONTAL;
		gbc_apacheField.gridx = 2;
		gbc_apacheField.gridy = 5;
		frame.getContentPane().add(apacheField, gbc_apacheField);
		apacheField.setColumns(10);
		apacheField.setText(prefs.get("apachePath",null));
		
		apcheCkBox = new JCheckBox("");
		GridBagConstraints gbc_apcheCkBox = new GridBagConstraints();
		gbc_apcheCkBox.insets = new Insets(0, 0, 5, 5);
		gbc_apcheCkBox.gridx = 3;
		gbc_apcheCkBox.gridy = 5;
		frame.getContentPane().add(apcheCkBox, gbc_apcheCkBox);
		
		JLabel lblTomcat = new JLabel("Maven路径:");
		GridBagConstraints gbc_lblTomcat = new GridBagConstraints();
		gbc_lblTomcat.anchor = GridBagConstraints.EAST;
		gbc_lblTomcat.insets = new Insets(0, 0, 5, 5);
		gbc_lblTomcat.gridx = 1;
		gbc_lblTomcat.gridy = 6;
		frame.getContentPane().add(lblTomcat, gbc_lblTomcat);
		
		mavenField = new JTextField();
		GridBagConstraints gbc_mavenField = new GridBagConstraints();
		gbc_mavenField.insets = new Insets(0, 0, 5, 5);
		gbc_mavenField.fill = GridBagConstraints.HORIZONTAL;
		gbc_mavenField.gridx = 2;
		gbc_mavenField.gridy = 6;
		frame.getContentPane().add(mavenField, gbc_mavenField);
		mavenField.setColumns(10);
		mavenField.setText(prefs.get("mavenPath",null));
		
		mavenCkBox = new JCheckBox("");
		GridBagConstraints gbc_mavenCkBox = new GridBagConstraints();
		gbc_mavenCkBox.insets = new Insets(0, 0, 5, 5);
		gbc_mavenCkBox.gridx = 3;
		gbc_mavenCkBox.gridy = 6;
		frame.getContentPane().add(mavenCkBox, gbc_mavenCkBox);
		
		JLabel lblResion = new JLabel("Resin路径:");
		GridBagConstraints gbc_lblResion = new GridBagConstraints();
		gbc_lblResion.anchor = GridBagConstraints.EAST;
		gbc_lblResion.insets = new Insets(0, 0, 5, 5);
		gbc_lblResion.gridx = 1;
		gbc_lblResion.gridy = 7;
		frame.getContentPane().add(lblResion, gbc_lblResion);
		
		resionField = new JTextField();
		GridBagConstraints gbc_resionField = new GridBagConstraints();
		gbc_resionField.insets = new Insets(0, 0, 5, 5);
		gbc_resionField.fill = GridBagConstraints.HORIZONTAL;
		gbc_resionField.gridx = 2;
		gbc_resionField.gridy = 7;
		frame.getContentPane().add(resionField, gbc_resionField);
		resionField.setColumns(10);
		resionField.setText(prefs.get("resinPath",null));
		
		resinCkBox = new JCheckBox("");
		GridBagConstraints gbc_resinCkBox = new GridBagConstraints();
		gbc_resinCkBox.insets = new Insets(0, 0, 5, 5);
		gbc_resinCkBox.gridx = 3;
		gbc_resinCkBox.gridy = 7;
		frame.getContentPane().add(resinCkBox, gbc_resinCkBox);
		
		JLabel label_2 = new JLabel("控制台");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 0, 5);
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 8;
		frame.getContentPane().add(label_2, gbc_label_2);
		
		editorPane = new JEditorPane();
		GridBagConstraints gbc_editorPane = new GridBagConstraints();
		gbc_editorPane.insets = new Insets(0, 0, 0, 5);
		gbc_editorPane.fill = GridBagConstraints.BOTH;
		gbc_editorPane.gridx = 2;
		gbc_editorPane.gridy = 8;
		frame.getContentPane().add(editorPane, gbc_editorPane);
	}
	
	public void upLoadEvent(MouseEvent e){
		SoftwareInstaller softwareInstaller =null;
		SoftwareInstaller aprInstaller = null;
		SoftwareInstaller aprUtilInstaller = null;
		SoftwareInstaller pcreInstaller = null;
		SoftwareInstaller apacheInstaller = null;
		SoftwareInstaller resinInstaller =null;
		SoftwareInstaller mavenInstaller = null;
		try{
		String errorMsgs = "";
		List<String> errorMsgList = validateInput();
		if(errorMsgList.size()>0){
			for(String msg:errorMsgList){
				errorMsgs = errorMsgs+"\n"+msg;
			}
			JOptionPane.showMessageDialog(null, errorMsgs,"My Message", JOptionPane.ERROR_MESSAGE);
		}else{
			String[] ipArray = ipField.getText().split(";");
			
			for(String ip:ipArray){
				SSH2UpLoader.isDirectoryExist(ip, userNameField.getText(), String.valueOf(passwordField.getPassword()), serverPathField.getText());
				if(jdkCkBox.isSelected()){
				   SSH2UpLoader.uploadSoftware(ip, userNameField.getText(), String.valueOf(passwordField.getPassword()), jdkField.getText(), jdkField.getText().substring(jdkField.getText().lastIndexOf(File.separator) + 1,jdkField.getText().length()),  serverPathField.getText());
		    	   softwareInstaller = new SoftwareInstaller(ip, userNameField.getText(), String.valueOf(passwordField.getPassword()),jdkField.getText());
		    	   editorPane.setText(editorPane.getText()+"\n"+"------------------install script-----------------"+"\n"+softwareInstaller.getInstallScript(serverPathField.getText()));
		    	   String result = softwareInstaller.install(serverPathField.getText());
		    	   editorPane.setText(editorPane.getText()+"\n"+"---------------install result---------------"+"\n"+result);
		    	   softwareInstaller.logout();
				}
				
				if(apcheCkBox.isSelected()){
					   //apr-1.4.5.tar.gz install
					   String aprpath = getClass().getProtectionDomain().getCodeSource().getLocation().toString().substring(6);
					   byte[] aprReader = TemplateLoader.loadTar(aprpath+"com/jware/dependment/software/",APR_SOFTWARE_NAME);
					   SSH2UpLoader.uploadSoftware(ip, userNameField.getText(), String.valueOf(passwordField.getPassword()), aprReader, APR_SOFTWARE_NAME, serverPathField.getText());
				 	   aprInstaller = new SoftwareInstaller(ip, userNameField.getText(), String.valueOf(passwordField.getPassword()),APR_SOFTWARE_NAME);
				 	   editorPane.setText(editorPane.getText()+"\n"+"---------------install script---------------"+"\n"+aprInstaller.getInstallScript(serverPathField.getText()));
				 	   String aprResult = aprInstaller.install(serverPathField.getText());
				 	   editorPane.setText(editorPane.getText()+"\n"+"---------------install result---------------"+"\n"+aprResult);
				 	   aprInstaller.logout();
				 	   
				 	   //apr-util-1.3.12.tar.gz install
					   String aprUtilPath = getClass().getProtectionDomain().getCodeSource().getLocation().toString().substring(6);
					   byte[] aprUtilReader = TemplateLoader.loadTar(aprUtilPath+"com/jware/dependment/software/",APR_UTIL_SOFTWARE_NAME);
					   SSH2UpLoader.uploadSoftware(ip, userNameField.getText(), String.valueOf(passwordField.getPassword()), aprUtilReader, APR_UTIL_SOFTWARE_NAME, serverPathField.getText());
				 	   aprUtilInstaller = new SoftwareInstaller(ip, userNameField.getText(), String.valueOf(passwordField.getPassword()),APR_UTIL_SOFTWARE_NAME);
				 	   editorPane.setText(editorPane.getText()+"\n"+"---------------install script---------------"+"\n"+aprUtilInstaller.getInstallScript(serverPathField.getText()));
				 	   String aprUtilResult = aprUtilInstaller.install(serverPathField.getText());
				 	   editorPane.setText(editorPane.getText()+"\n"+"---------------install result---------------"+"\n"+aprUtilResult);
				 	   aprUtilInstaller.logout();
				 	  
				 	   //pcre-8.10.zip install
				 	   String pcrePath = getClass().getProtectionDomain().getCodeSource().getLocation().toString().substring(6);
					   byte[] pcreReader = TemplateLoader.loadTar(pcrePath+"com/jware/dependment/software/",PCRE_SOFTWARE_NAME);
					   SSH2UpLoader.uploadSoftware(ip, userNameField.getText(), String.valueOf(passwordField.getPassword()), pcreReader, PCRE_SOFTWARE_NAME, serverPathField.getText());
				 	   pcreInstaller = new SoftwareInstaller(ip, userNameField.getText(), String.valueOf(passwordField.getPassword()),PCRE_SOFTWARE_NAME);
				 	   editorPane.setText(editorPane.getText()+"\n"+"---------------install script---------------"+"\n"+pcreInstaller.getInstallScript(serverPathField.getText()));
				 	   String pcreResult = pcreInstaller.install(serverPathField.getText());
				 	   editorPane.setText(editorPane.getText()+"\n"+"---------------install result---------------"+"\n"+pcreResult);
				 	   pcreInstaller.logout();
				 	   
					   SSH2UpLoader.uploadSoftware(ip, userNameField.getText(), String.valueOf(passwordField.getPassword()), apacheField.getText(), apacheField.getText().substring(apacheField.getText().lastIndexOf(File.separator) + 1,apacheField.getText().length()), serverPathField.getText());
			    	   apacheInstaller = new SoftwareInstaller(ip, userNameField.getText(), String.valueOf(passwordField.getPassword()),apacheField.getText());
			    	   editorPane.setText(editorPane.getText()+"\n"+"---------------install script---------------"+"\n"+apacheInstaller.getInstallScript(serverPathField.getText()));
			    	   String apacheInstallerResult = apacheInstaller.install(serverPathField.getText());
			    	   editorPane.setText(editorPane.getText()+"\n"+"---------------install result---------------"+"\n"+apacheInstallerResult);
			    	   apacheInstaller.logout();
				}
				
				if(resinCkBox.isSelected()){
					   SSH2UpLoader.uploadSoftware(ip, userNameField.getText(), String.valueOf(passwordField.getPassword()), resionField.getText(), resionField.getText().substring(resionField.getText().lastIndexOf(File.separator) + 1,resionField.getText().length()), serverPathField.getText());
			    	   resinInstaller = new SoftwareInstaller(ip, userNameField.getText(), String.valueOf(passwordField.getPassword()),resionField.getText());
			    	   editorPane.setText(editorPane.getText()+"\n"+"----------------install script----------------"+"\n"+resinInstaller.getInstallScript(serverPathField.getText()));
			    	   String result = resinInstaller.install(serverPathField.getText());
			    	   editorPane.setText(editorPane.getText()+"\n"+"---------------install result---------------"+"\n"+result);
			    	   resinInstaller.logout();
				}
				
				if(mavenCkBox.isSelected()){
					   SSH2UpLoader.uploadSoftware(ip, userNameField.getText(), String.valueOf(passwordField.getPassword()), mavenField.getText(), mavenField.getText().substring(mavenField.getText().lastIndexOf(File.separator) + 1,mavenField.getText().length()), serverPathField.getText());
			    	   mavenInstaller = new SoftwareInstaller(ip, userNameField.getText(), String.valueOf(passwordField.getPassword()),mavenField.getText());
			    	   editorPane.setText(editorPane.getText()+"\n"+"-----------------install script---------------"+"\n"+mavenInstaller.getInstallScript(serverPathField.getText()));
			    	   String result = mavenInstaller.install(serverPathField.getText());
			    	   editorPane.setText(editorPane.getText()+"\n"+"---------------install result---------------"+"\n"+result);
			    	   mavenInstaller.logout();
				}
				
			}
		}
		}catch(Exception ex){
			if(softwareInstaller!=null){
				softwareInstaller.logout();
			}
			if(aprInstaller!=null){
				aprInstaller.logout();
			}
			if(aprUtilInstaller!=null){
				aprUtilInstaller.logout();
			}
			if(pcreInstaller!=null){
				pcreInstaller.logout();
			}
			if(apacheInstaller!=null){
				apacheInstaller.logout();
			}
			if(resinInstaller!=null){
				resinInstaller.logout();
			}
			if(mavenInstaller!=null){
				mavenInstaller.logout();
			}
			 StringBuilder sb = new StringBuilder();
			 for (StackTraceElement element : ex.getStackTrace()) {
			        sb.append(element.toString());
			        sb.append("\n");
			    }
			editorPane.setText(editorPane.getText()+sb.toString());
		}finally{
			if(softwareInstaller!=null){
				softwareInstaller.logout();
			}
			if(aprInstaller!=null){
				aprInstaller.logout();
			}
			if(aprUtilInstaller!=null){
				aprUtilInstaller.logout();
			}
			if(pcreInstaller!=null){
				pcreInstaller.logout();
			}
			if(apacheInstaller!=null){
				apacheInstaller.logout();
			}
			if(resinInstaller!=null){
				resinInstaller.logout();
			}
			if(mavenInstaller!=null){
				mavenInstaller.logout();
			}
		}
	}
	
	
	private List validateInput(){
		
		List<String> errorMsgList = new ArrayList<String>();
		
		if(serverPathField.getText()==null||"".equals(serverPathField.getText())){
			errorMsgList.add("服务器路径不能为空");
	    }  
		if(ipField.getText()==null||"".equals(ipField.getText())){
			errorMsgList.add("IP地址不能为空");
	    }  
		
		if(userNameField.getText()==null||"".equals(userNameField.getText())){
				errorMsgList.add("用户名不能为空");
		}
		if(passwordField.getPassword()==null||"".equals(passwordField.getPassword())){
			errorMsgList.add("密码不能为空");
	    }  
		if(!jdkCkBox.isSelected()&&!apcheCkBox.isSelected()&&!mavenCkBox.isSelected()&&!resinCkBox.isSelected()){
			errorMsgList.add("没有选择需要安装的软件");
		}
		//JDK验证
		if(jdkCkBox.isSelected()){
		 if(jdkField.getText()==null||"".equals(jdkField.getText())){
			errorMsgList.add("JDK安装文件路径为空");
		 }
		 if(!new File(jdkField.getText()).exists()){
			errorMsgList.add("JDK安装文件路径不存在");
		 }
		 String fileName = jdkField.getText().substring(jdkField.getText().lastIndexOf(File.separator) + 1,jdkField.getText().length()); 
		 if(!fileName.toUpperCase().contains("JDK")){
			errorMsgList.add("JDK安装文件错误");
		 }
		}
		
		//Apache验证
		if(apcheCkBox.isSelected()){
		if(apacheField.getText()==null||"".equals(apacheField.getText())){
			errorMsgList.add("Apache安装文件路径为空");
		}
		if(!new File(apacheField.getText()).exists()){
			errorMsgList.add("Apache安装文件路径不存在");
		}
		String fileName = apacheField.getText().substring(apacheField.getText().lastIndexOf(File.separator) + 1,apacheField.getText().length()); 
		if(!fileName.toUpperCase().contains("HTTPD-")){
			errorMsgList.add("Apache安装文件错误");
		}
		}
		
		//Resin验证
		if(resinCkBox.isSelected()){
		if(resionField.getText()==null||"".equals(resionField.getText())){
			errorMsgList.add("Resin安装文件路径为空");
		}
		if(!new File(resionField.getText()).exists()){
			errorMsgList.add("Resin安装文件路径不存在");
		}
		String fileName = resionField.getText().substring(resionField.getText().lastIndexOf(File.separator) + 1,resionField.getText().length()); 
		if(!fileName.toUpperCase().contains("RESIN")){
			errorMsgList.add("Resin安装文件错误");
		}
		}
		
		//Tomcat验证
		if(mavenCkBox.isSelected()){
		if(mavenField.getText()==null||"".equals(mavenField.getText())){
			errorMsgList.add("Tomcat安装文件路径为空");
		}
		if(!new File(mavenField.getText()).exists()){
			errorMsgList.add("Tomcat安装文件路径不存在");
		}
		String fileName = mavenField.getText().substring(mavenField.getText().lastIndexOf(File.separator) + 1,mavenField.getText().length()); 
		if(!fileName.toUpperCase().contains("MAVEN")){
			errorMsgList.add("Tomcat安装文件错误");
		}
		}
		return errorMsgList;
	}

}
