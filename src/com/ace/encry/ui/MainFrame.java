package com.ace.encry.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.ace.encry.util.EncryptUtil;
import com.ace.encry.util.Utils;

/**
 * ������
 * 
 * @author Ace
 * 
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 2746224826281328077L;
	private static final int HEIGHT = 450;
	private static final int WIDTH = 500;
	private JTextArea inputArea;
	private JTextArea outputArea;
	private JPasswordField keyField;
	private JButton encryptBtn;
	private JButton decryptBtn;
	private JButton clearBtn;
	private JButton copyBtn;
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
		setIcon();
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

	private void setIcon(){
		URL url = this.getClass().getClassLoader().getResource("icon.png");
		Image image=Toolkit.getDefaultToolkit().createImage(url);
		this.setIconImage(image);
	}

	private Container getJContentPane() {
		JPanel jPanel = new JPanel(new BorderLayout());
		jPanel.add(getPanelNorth(), BorderLayout.NORTH);
		jPanel.add(getPanelCenter(), BorderLayout.CENTER);
		jPanel.add(getPanelBottom(), BorderLayout.SOUTH);
		jPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		return jPanel;
	}

	/** ��Կ����Ϣ */
	private Component getPanelNorth() {
		JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jPanel.add(new JLabel("��Կ:"));
		jPanel.add(getJPasswordField());
		jPanel.add(getMsgLabel());
		return jPanel;
	}

	private Component getJPasswordField() {
		if (keyField == null) {
			keyField = new JPasswordField(15);
			keyField.setEchoChar('*');
		}
		return keyField;
	}

	private Component getMsgLabel() {
		if (null == msgLabel) {
			msgLabel = new JLabel();
		}
		return msgLabel;
	}

	/** ��������� */

	private Component getPanelCenter() {
		JPanel jPanel = new JPanel(new GridLayout(1, 2));
		jPanel.add(getPanelInput());
		jPanel.add(getPanelOutput());
		return jPanel;
	}

	private Component getPanelInput() {
		JPanel jPanel = new JPanel(new BorderLayout());
		jPanel.add(new JLabel("����:"), BorderLayout.NORTH);
		jPanel.add(getInputArea(), BorderLayout.CENTER);
		jPanel.add(getPanelClear(), BorderLayout.SOUTH);
		return jPanel;
	}

	private Component getInputArea() {
		if (inputArea == null) {
			inputArea = new JTextArea();
			inputArea.setTabSize(4);
			inputArea.setRows(5);
			inputArea.setLineWrap(true);// �����Զ����й���
			inputArea.setWrapStyleWord(true);// ������в����ֹ���
			inputArea.setBackground(Color.white);
			inputArea.setAutoscrolls(true);
		}
		JScrollPane inputScroll = new JScrollPane(inputArea);
		return inputScroll;
	}

	private Component getPanelClear() {
		JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jPanel.add(getClearBtn(), BorderLayout.SOUTH);
		return jPanel;
	}

	private Component getClearBtn() {
		if (null == clearBtn) {
			clearBtn = new JButton("���");
			clearBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					inputArea.setText("");
					inputArea.requestFocus();
				}
			});
		}
		return clearBtn;
	}

	private Component getPanelOutput() {
		JPanel jPanel = new JPanel(new BorderLayout());
		jPanel.add(new JLabel("���:"), BorderLayout.NORTH);
		jPanel.add(getJOutputArea(), BorderLayout.CENTER);
		jPanel.add(getPanelCopy(), BorderLayout.SOUTH);
		return jPanel;
	}

	private Component getJOutputArea() {
		if (outputArea == null) {
			outputArea = new JTextArea();
			outputArea.setTabSize(4);
			outputArea.setRows(5);
			outputArea.setLineWrap(true);// �����Զ����й���
			outputArea.setWrapStyleWord(true);// ������в����ֹ���
			outputArea.setBackground(Color.white);
			outputArea.setEditable(false);
			outputArea.setAutoscrolls(true);
		}
		JScrollPane outputScroll = new JScrollPane(outputArea);
		return outputScroll;
	}

	private Component getPanelCopy() {
		JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jPanel.add(getCopyBtn(), BorderLayout.SOUTH);
		return jPanel;
	}

	private Component getCopyBtn() {
		if (null == copyBtn) {
			copyBtn = new JButton("����");
			copyBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Utils.copyText2Clipboard(outputArea.getText());
					msgLabel.setForeground(null);
					msgLabel.setText("�Ѹ��ƽ����������");
				}
			});
		}
		return copyBtn;
	}

	/** �ײ���ť */

	private Component getPanelBottom() {
		JPanel jPanel = new JPanel(new FlowLayout());
		jPanel.add(getEncryptBtn());
		jPanel.add(getDecryptBtn());
		return jPanel;
	}

	private Component getEncryptBtn() {
		if (encryptBtn == null) {
			encryptBtn = new JButton("����");
			encryptBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					action(true);
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
					action(false);
				}
			});
		}
		return decryptBtn;
	}

	/**
	 * ���ܽ����¼�
	 * 
	 * @param flag
	 *            Ϊtrueʱ��ʾ���� false��ʾ����
	 */
	private void action(boolean flag) {
		String data = inputArea.getText().trim();
		String key = new String(keyField.getPassword()).trim();
		msgLabel.setText("");
		msgLabel.setForeground(null);
		if ("".equals(data)) {
			msgLabel.setForeground(Color.red);
			msgLabel.setText("���벻��Ϊ��");
			return;
		}
		if ("".equals(key)) {
			msgLabel.setForeground(Color.red);
			msgLabel.setText("��Կ����Ϊ��");
			return;
		}

		String result = null;
		/** ���� */
		if (flag) {
			try {
				result = EncryptUtil.encrypt(data, key);
			} catch (Exception e) {
				msgLabel.setForeground(Color.red);
				msgLabel.setText("����ʧ��");
				return;
			}
		} else {
			/** ���� */
			try {
				result = EncryptUtil.decrypt(data, key);
			} catch (Exception e) {
				msgLabel.setForeground(Color.red);
				msgLabel.setText("����ʧ�ܣ���ȷ����Կ�Ƿ���ȷ");
				return;
			}
		}
		outputArea.setText(result);
	}
}
