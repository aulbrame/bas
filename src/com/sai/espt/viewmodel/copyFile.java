package com.sai.espt.viewmodel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.sai.utils.SysUtils;


public class copyFile {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws FileNotFoundException {
		//
		// membuat source dan tujuan di copynya file
		//
		File source = new File("D:/Soe/Project/espt21/espt21/WebContent/jasper/espt.jasper");
		File destination = new File("D:/Soe/Project/espt21/espt21/WebContent/jasper/ezps.jasper");

		new FileInputStream(source);
		new FileOutputStream(destination);
		destination.delete();
		/*		try {
		fis = new FileInputStream(source);
		fos = new FileOutputStream(destination);

		//
		// menentukan besar dr buffer data
		//
		byte[] buffer = new byte[4096];
		int read;
		while ((read = fis.read(buffer)) != -1) {
		fos.write(buffer, 0, read);
		}
		} catch (IOException e) {
		e.printStackTrace();
		} finally {
		try {
		//
		// menyetop system
		//
		if (fis != null) {
		fis.close();
		}
		if (fos != null) {
		fos.close();
		}
		} catch (IOException e) {
		e.printStackTrace();
		}
		}*/
		}
}
