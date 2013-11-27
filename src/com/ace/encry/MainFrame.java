package com.ace.encry;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.ace.encry.util.Utils;

/**
 * 主窗口
 * 
 * @author Ace
 * 
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 2746224826281328077L;
	private static final int HEIGHT = 250;
	private static final int WIDTH = 350;
	private JTextField dataField;
	private JPasswordField keyField;
	private JTextField outputField;
	private JButton encryptBtn;
	private JButton decryptBtn;
	private JButton clearBtn;
	private JCheckBox autoCopyCheckBox;
	private JLabel msgLabel;

	public MainFrame() {
		super();
		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setLocationCenter();
		setTitle("加密/解密");
		setContentPane(getJContentPane());
	}

	private void setLocationCenter() {
		Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();// 获得显示器大小对象
		Dimension frameSize = getSize();// 获得窗口大小对象
		if (frameSize.width > displaySize.width)
			frameSize.width = displaySize.width;// 窗口的宽度不能大于显示器的宽度
		if (frameSize.height > displaySize.height)
			frameSize.height = displaySize.height;// 窗口的高度不能大于显示器的高度
		setLocation((displaySize.width - frameSize.width) / 2,
				(displaySize.height - frameSize.height) / 2);
	}

	private Container getJContentPane() {
		JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jPanel.add(getPanelTop());
		jPanel.add(getPanelCenter());
		jPanel.add(getPanelCenter2());
		jPanel.add(getPanelBottom());
		jPanel.add(getPanelBottom2());
		jPanel.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
		return jPanel;
	}

	private Component getPanelBottom2() {
		JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jPanel.add(getMsgLabel());
		return jPanel;
	}

	private Component getMsgLabel() {
		if (null == msgLabel) {
			msgLabel = new JLabel();
		}
		return msgLabel;
	}

	private Component getPanelCenter2() {
		JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jPanel.add(new JLabel("加密/解密结果:"));
		jPanel.add(getJOutputArea());
		jPanel.add(getCopyBtn());
		return jPanel;
	}
	
	private Component getClearBtn() {
		if (null == clearBtn) {
			clearBtn = new JButton("清空");
			clearBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					dataField.setText("");					
				}
			});
		}
		return clearBtn;
	}

	private Component getCopyBtn() {
		if (null == autoCopyCheckBox) {
			
			autoCopyCheckBox = new JCheckBox("复制到粘贴版");
			autoCopyCheckBox.setSelected(true);
			/*copyBtn = new JButton("复制");
			copyBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Utils.copyText2Clipboard(outputField.getText());					
				}
			});*/
		}
		return autoCopyCheckBox;
	}

	private Component getPanelTop() {
		JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jPanel.add(new JLabel("加密/解密字符:"));
		jPanel.add(getJDataField());
		jPanel.add(getClearBtn());
		return jPanel;
	}

	private Component getJDataField() {
		if (dataField == null) {
			dataField = new JTextField(10);
		}
		return dataField;
	}

	private Component getPanelCenter() {
		JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jPanel.add(new JLabel("密钥:"));
		jPanel.add(getJPasswordField());
		return jPanel;
	}

	private Component getJOutputArea() {
		if (outputField == null) {
			// outputArea = new JTextArea();
			outputField = new JTextField(10);
			outputField.setEditable(false);
			// outputField.setSize(5000, 1000);
			// outputField.setAutoscrolls(true);
		}
		return outputField;
	}

	private Component getPanelBottom() {
		JPanel jPanel = new JPanel(new FlowLayout());
		jPanel.add(getEncryptBtn());
		jPanel.add(getDecryptBtn());
		return jPanel;
	}

	private Component getJPasswordField() {
		if (keyField == null) {
			keyField = new JPasswordField(10);
			keyField.setEchoChar('*');
		}
		return keyField;
	}

	private Component getEncryptBtn() {
		if (encryptBtn == null) {
			encryptBtn = new JButton("加密");
			encryptBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String data = dataField.getText().trim();
					String key = new String(keyField.getPassword()).trim();
					msgLabel.setText("");
					if ("".equals(data)) {
						msgLabel.setText("加密内容不能为空");
						/*JOptionPane.showMessageDialog(null, "加密内容不能为空", "消息",
								JOptionPane.INFORMATION_MESSAGE);*/
					} else if ("".equals(key)) {
						msgLabel.setText("密钥不能为空");
						/*JOptionPane.showMessageDialog(null, "密钥不能为空", "消息",
								JOptionPane.INFORMATION_MESSAGE);*/
					} else {
						try {
							outputField.setText(Encryption.encrypt(data, key));
							if (autoCopyCheckBox.isSelected()) {
								Utils.copyText2Clipboard(outputField.getText());	
								msgLabel.setText("已复制结果到剪贴版");
							}
						} catch (Exception e) {
							msgLabel.setText("加密失败");
							/*JOptionPane.showMessageDialog(null, "加密失败", "消息",
									JOptionPane.ERROR_MESSAGE);*/
						}
					}
				}
			});
		}
		return encryptBtn;
	}

	private Component getDecryptBtn() {
		if (decryptBtn == null) {
			decryptBtn = new JButton("解密");
			decryptBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String data = dataField.getText().trim();
					String key = new String(keyField.getPassword()).trim();
					msgLabel.setText("");
					if ("".equals(data)) {
						msgLabel.setText("解密内容不能为空");
					} else if ("".equals(key)) {
						msgLabel.setText("密钥不能为空版");
					} else {
						try {
							outputField.setText(Encryption.decrypt(data, key));
							if (autoCopyCheckBox.isSelected()) {
								Utils.copyText2Clipboard(outputField.getText());	
							}
						} catch (Exception e) {
							msgLabel.setText("解密失败，请确认密钥是否正确");
						}
					}
				}
			});
		}
		return decryptBtn;
	}

}
