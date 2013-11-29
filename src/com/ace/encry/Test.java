package com.ace.encry;

import java.awt.Color;
import java.awt.Font;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import sun.misc.BASE64Encoder;


public class Test {

    /**
     * @param args the command line arguments
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
    	JFrame frame = new JFrame();
    	JTextArea jta = new JTextArea(10, 15);  
        jta.setTabSize(4);  
        jta.setFont(new Font("标楷体", Font.BOLD, 16));  
        jta.setLineWrap(true);// 激活自动换行功能  
        jta.setWrapStyleWord(true);// 激活断行不断字功能 
        jta.setBackground(Color.pink);  
        jta.setText("sdfsdf");
        JScrollPane jscrollPane = new JScrollPane(jta);
        jta.setAutoscrolls(true);
    	frame.add(jscrollPane);
    	frame.setSize(200, 300);
    	
    	frame.setVisible(true);
    }
    
    public static String md5(String psw) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(psw.getBytes());
			//将md5加密后的字节数组，采用base64算法处理，避免乱码
			BASE64Encoder base64 = new BASE64Encoder();
			String msg = base64.encode(bytes);
			return msg;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
