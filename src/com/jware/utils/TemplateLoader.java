package com.jware.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.jware.exception.BaseException;

public class TemplateLoader {

	public static BufferedReader loadTemplate(String root,String fileName)throws BaseException {
		InputStreamReader ir;
		try {
			ir = new InputStreamReader(new FileInputStream(
					root+fileName));
			return new BufferedReader(ir);
		} catch (FileNotFoundException e) {
			throw new BaseException(e);
		}
	} 
	
	public static byte[] loadTar(String root,String fileName)throws BaseException{
		File file = new File(root + fileName);
		// File length
		int size = (int) file.length();
		if (size > Integer.MAX_VALUE) {
			System.out.println("File is to larger");
		}
		byte[] bytes = new byte[size];
		DataInputStream dis;
		try {
			dis = new DataInputStream(new FileInputStream(file));
			int read = 0;
			int numRead = 0;
			while (read < bytes.length
					&& (numRead = dis.read(bytes, read, bytes.length - read)) >= 0) {
				read = read + numRead;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	} 
	
	public static void main(String[] args){
		loadTar("E:\\远程工具\\","pcre-8.10.zip");
	}
}
