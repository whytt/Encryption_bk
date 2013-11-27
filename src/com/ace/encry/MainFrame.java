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
 * ������
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
		setTitle("����/����");
		setContentPane(getJContentPane());
	}

	private void setLocationCenter() {
		Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();// �����ʾ����С����
		Dimension frameSize = getSize();// ��ô��ڴ�С����
		if (frameSize.width > displaySize.width)
			frameSize.width = displaySize.width;// ���ڵĿ�Ȳ��ܴ�����ʾ���Ŀ��
		if (frameSize.height > displaySize.height)
			frameSize.height = displaySize.height;// ���ڵĸ߶Ȳ��ܴ�����ʾ���ĸ߶�
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
		jPanel.add(new JLabel("����/���ܽ��:"));
		jPanel.add(getJOutputArea());
		jPanel.add(getCopyBtn());
		return jPanel;
	}
	
	private Component getClearBtn() {
		if (null == clearBtn) {
			clearBtn = new JButton("���");
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
			
			autoCopyCheckBox = new JCheckBox("���Ƶ�ճ����");
			autoCopyCheckBox.setSelected(true);
			/*copyBtn = new JButton("����");
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
		jPanel.add(new JLabel("����/�����ַ�:"));
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
		jPanel.add(new JLabel("��Կ:"));
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
			encryptBtn = new JButton("����");
			encryptBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String data = dataField.getText().trim();
					String key = new String(keyField.getPassword()).trim();
					msgLabel.setText("");
					if ("".equals(data)) {
						msgLabel.setText("�������ݲ���Ϊ��");
						/*JOptionPane.showMessageDialog(null, "�������ݲ���Ϊ��", "��Ϣ",
								JOptionPane.INFORMATION_MESSAGE);*/
					} else if ("".equals(key)) {
						msgLabel.setText("��Կ����Ϊ��");
						/*JOptionPane.showMessageDialog(null, "��Կ����Ϊ��", "��Ϣ",
								JOptionPane.INFORMATION_MESSAGE);*/
					} else {
						try {
							outputField.setText(Encryption.encrypt(data, key));
							if (autoCopyCheckBox.isSelected()) {
								Utils.copyText2Clipboard(outputField.getText());	
								msgLabel.setText("�Ѹ��ƽ����������");
							}
						} catch (Exception e) {
							msgLabel.setText("����ʧ��");
							/*JOptionPane.showMessageDialog(null, "����ʧ��", "��Ϣ",
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
			decryptBtn = new JButton("����");
			decryptBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String data = dataField.getText().trim();
					String key = new String(keyField.getPassword()).trim();
					msgLabel.setText("");
					if ("".equals(data)) {
						msgLabel.setText("�������ݲ���Ϊ��");
					} else if ("".equals(key)) {
						msgLabel.setText("��Կ����Ϊ�հ�");
					} else {
						try {
							outputField.setText(Encryption.decrypt(data, key));
							if (autoCopyCheckBox.isSelected()) {
								Utils.copyText2Clipboard(outputField.getText());	
							}
						} catch (Exception e) {
							msgLabel.setText("����ʧ�ܣ���ȷ����Կ�Ƿ���ȷ");
						}
					}
				}
			});
		}
		return decryptBtn;
	}

}
