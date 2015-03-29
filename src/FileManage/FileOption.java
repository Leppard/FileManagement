import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class FileOption {
	public static void createFloder(String name)
	{
		int position = Memory.findFreeFCB();
		if (position == 100)
		{
			JOptionPane.showInputDialog(null,"FCB空间已满!");
		}
		else
		{
			Memory.floderCol[position].flag = "floder";
			Memory.floderCol[position].name = name;
			Memory.floderCol[position].upFloder = Memory.curFloderPos;

			Memory.floderCol[Memory.curFloderPos].items.add(position);
			Memory.tableRefresh(Memory.curFloderPos);
		}
	}
	public static boolean ifSameName(String name,String type)
	{
		Integer tempFloder;
		for(int i=0;i<Memory.floderCol[Memory.curFloderPos].items.size();i++)
		{
			tempFloder = (Integer) Memory.floderCol[Memory.curFloderPos].items.elementAt(i);
			if(Memory.floderCol[tempFloder].name.equals(name) && Memory.floderCol[tempFloder].flag.equals(type))
				return true;
		}
		return false;
	}
	public static JButton getFolderCreate() {
		if (FileManager.FolderCreate == null) 
		{
			FileManager.FolderCreate = new JButton("新建文件夹");
			FileManager.FolderCreate.setBounds(new Rectangle(0, 6,100,20));

			FileManager.FolderCreate.addActionListener(new java.awt.event.ActionListener() 
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					String name=JOptionPane.showInputDialog(null,"请输入文件夹名：");
					if (name.equals(""))  
					{
						JOptionPane.showMessageDialog(null,"名字不能为空！");
					}
					else
					{
						if (!ifSameName(name,"floder"))   
							createFloder(name);		   
						else
							JOptionPane.showMessageDialog(null,"当前文件夹下已有同名文件夹！");
					}
				}
			});
		}
		return FileManager.FolderCreate;
	}
	
	public static JButton getFolerDelete() {
		if (FileManager.FolderDelete == null) {
			FileManager.FolderDelete = new JButton("删除文件夹");
			FileManager.FolderDelete.setBounds(new Rectangle(110, 6, 100, 20));

			FileManager.FolderDelete.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					int choice=JOptionPane.showConfirmDialog(null, "删除该文件/文件夹？","",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
					if (choice == JOptionPane.OK_OPTION)  
					{
						String tempName = (String)jTable.getValueAt(jTable.getSelectionModel().getLeadSelectionIndex(), 0); 
						String type = (String)jTable.getValueAt(jTable.getSelectionModel().getLeadSelectionIndex(), 1);  
						if ( !type.equals("floder") )
						{
							JOptionPane.showMessageDialog(null,"删除文件，请按“删除文件”键！");
							
						}
						else
						{
							Integer tempFloder;

							for(int i=0;i<Memory.floderCol[Memory.curFloderPos].items.size();i++)
							{
								tempFloder = (Integer) Memory.floderCol[Memory.curFloderPos].items.elementAt(i);
								if(Memory.floderCol[tempFloder].name.equals(tempName) && Memory.floderCol[tempFloder].flag.equals("floder"))
									Memory.deleteFloder(tempFloder);
							}
						}
					}
					Memory.tableRefresh(Memory.curFloderPos);
					System.out.println("actionPerformed()"); 
				}
			});
		}
		return FileManager.FolderDelete;
	}

	public static JButton getFolerChange() {
		if (FileManager.FolderChange == null) {
			FileManager.FolderChange = new JButton("更改名称");
			FileManager.FolderChange.setBounds(new Rectangle(220, 6, 100, 20));

			FileManager.FolderChange.addActionListener(new java.awt.event.ActionListener() 
			{
				public void actionPerformed(java.awt.event.ActionEvent e) 
				{
					String name=JOptionPane.showInputDialog(null,"请输入新文件/文件夹名：");
					String tempName = (String)jTable.getValueAt(jTable.getSelectionModel().getLeadSelectionIndex(), 0); 
					String type = (String) jTable.getValueAt(jTable.getSelectionModel().getLeadSelectionIndex(), 1);  

					if (!name.equals("")) 
						if (!ifSameName(name,type))  
						{
							Integer tempFloder;
							for(int i=0;i<Memory.floderCol[Memory.curFloderPos].items.size();i++)
							{
								tempFloder = (Integer) Memory.floderCol[Memory.curFloderPos].items.elementAt(i);
								if(Memory.floderCol[tempFloder].name.equals(tempName) && Memory.floderCol[tempFloder].flag.equals(type))
									Memory.floderCol[tempFloder].name = name;
							}
							Memory.tableRefresh(Memory.curFloderPos); 
						}
						else
							JOptionPane.showMessageDialog(null,"名字不能重复！");
					else
						JOptionPane.showMessageDialog(null,"名字不能为空！");
				}
			});
		}
		return FileManager.FolderChange;
	}

	public static JButton getFileCreate() {
		if (FileManager.FileCreate == null) {
			FileManager.FileCreate = new JButton("创建文件");
			FileManager.FileCreate.setBounds(new Rectangle(0, 40, 100, 20));

			FileManager.FileCreate.addActionListener(new java.awt.event.ActionListener() 
			{
				public void actionPerformed(java.awt.event.ActionEvent e) 
				{
					String name=JOptionPane.showInputDialog(null,"请输入文件名：");

					if (!name.equals(""))	
						if (!ifSameName(name,"file"))  
						{
							Memory.createFile(name,1000); 

						}
						else
							JOptionPane.showMessageDialog(null,"名字不能重复！");
					else
						JOptionPane.showMessageDialog(null,"名字不能为空！");
				}

			});
		}
		return FileManager.FileCreate;
	}

	public static JButton getFileDelete() {
		if (FileManager.FileDelete == null) {
			FileManager.FileDelete = new JButton("删除文件");
			FileManager.FileDelete.setBounds(new Rectangle(110, 40, 100, 20));

			FileManager.FileDelete.addActionListener(new java.awt.event.ActionListener() 
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					int choice=JOptionPane.showConfirmDialog(null, "删除该文件/文件夹？","",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
					if (choice == JOptionPane.OK_OPTION)
					{
						String tempName = (String)jTable.getValueAt(jTable.getSelectionModel().getLeadSelectionIndex(), 0);
						String type = (String)jTable.getValueAt(jTable.getSelectionModel().getLeadSelectionIndex(), 1);  
						if (type.equals("file") )	
						{
							Integer tempFloder;
							for(int i=0;i<Memory.floderCol[Memory.curFloderPos].items.size();i++)
							{
								tempFloder = (Integer) Memory.floderCol[Memory.curFloderPos].items.elementAt(i);
								if(Memory.floderCol[tempFloder].name.equals(tempName) && Memory.floderCol[tempFloder].flag.equals("file"))
									Memory.deleteFile(tempFloder);  
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null,"你要删除的是文件夹，请按“删除文件夹”键！");
						}
					}
					Memory.tableRefresh(Memory.curFloderPos);
					System.out.println("actionPerformed()"); 
				}
			});
		}
		return FileManager.FileDelete;
	}

	public static JPanel getFileButtons() {
		if (FileManager.fileButtons == null) {
			FileManager.fileButtons = new JPanel();
			FileManager.fileButtons.setLayout(null);
			FileManager.fileButtons.setBounds(new Rectangle(20, 390, 64, 128));
			FileManager.fileButtons.setBackground(Color.GRAY);
		}
		return FileManager.fileButtons;
	}

	public static JButton getFSave() {
		if (FileManager.FileSave == null)
		{
			FileManager.FileSave = new JButton();
			FileManager.FileSave.setBounds(new Rectangle(3, 38, 52, 52));
			FileManager.FileSave.addActionListener(new java.awt.event.ActionListener() 
			{
				public void actionPerformed(java.awt.event.ActionEvent e) 
				{
					Memory.fileSave();  
					System.out.println("actionPerformed()");
				}
			});
		}
		return FileManager.FileSave;
	}

	public static JButton getFClose() {
		if (FileManager.FileClose == null)
		{
			FileManager.FileClose = new JButton("关闭文件");
			FileManager.FileClose.setBounds(new Rectangle(3, 93, 52, 52));
			FileManager.FileClose.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Memory.fileClose();	
					System.out.println("actionPerformed()"); 
				}
			});
		}
		return FileManager.FileClose;
	}

	public static JPanel getTextField() {
		if (textField == null) {
			MyLabel7 = new JLabel();
			MyLabel7.setBounds(new Rectangle(300, 1, 60, 32));
			MyLabel6 = new JLabel();
			MyLabel6.setBounds(new Rectangle(240, 1, 60, 32));
			MyLabel5 = new JLabel();
			MyLabel5.setBounds(new Rectangle(180, 1, 60, 32));
			MyLabel4 = new JLabel();
			MyLabel4.setBounds(new Rectangle(120, 1, 60, 32));
			MyLabel3 = new JLabel();
			MyLabel3.setBounds(new Rectangle(60, 1, 60, 32));
			MyLabel2 = new JLabel();
			MyLabel2.setBounds(new Rectangle(0, 1, 60, 32));
			MyLabel1 = new JLabel();
			MyLabel1.setText("进入文件夹");
			MyLabel1.setBounds(new Rectangle(8, 19, 90, 18));
			textField = new JPanel();
			textField.setLayout(null);
			textField.setBounds(new Rectangle(79, 79, 365, 35));
			textField.setBackground(Color.GRAY);
			textField.add(MyLabel2, null);
			textField.add(MyLabel3, null);
			textField.add(MyLabel4, null);
			textField.add(MyLabel5, null);
			textField.add(MyLabel6, null);
			textField.add(MyLabel7, null);
		}
		return textField;
	}

	public static JPanel getTextField2() 
	{
		if (textField2 == null) {
			MyLabel13 = new JLabel();
			MyLabel13.setBounds(new Rectangle(3, 3, 60, 32));
			MyLabel13.setText("保存文件");
			MyLabel12 = new JLabel();
			MyLabel12.setBounds(new Rectangle(3, 153, 60, 32));
			MyLabel12.setText("关闭文件");
			textField2 = new JPanel();
			textField2.setLayout(null);
			textField2.setBackground(Color.GRAY);
			textField2.setBounds(new Rectangle(578, 260, 128, 200));
			textField2.add(MyLabel12, null);
			textField2.add(MyLabel13, null);
			textField2.add(getFSave(), null);
			textField2.add(getFClose(), null);
			textField2.setOpaque(false);
		}
		return textField2;
	}
	public static JTextArea getFInput() 
	{
		if (fInput == null) {
			fInput = new JTextArea();
			fInput.setText("");
			fInput.setLineWrap(true);
			fInput.setEditable(false);
		}
		return fInput;
	}


	public static JPanel getCFloder()
	{
		if (cFloder == null) {
			MyLabel00 = new JLabel();
			MyLabel00.setBounds(new Rectangle(156, 62, 51, 26));
			MyLabel = new JLabel();
			MyLabel.setText("上一层");
			MyLabel.setBounds(new Rectangle(15, 158, 45, 27));
			currentFloder = new JLabel();
			currentFloder.setBounds(new Rectangle(3, 8, 69, 29));
			currentFloder.setText("当前文件夹");
			cFloder = new JPanel();
			cFloder.setLayout(null);
			cFloder.setBounds(new Rectangle(111, 110, 344, 50));
			cFloder.add(currentFloder, null);
			cFloder.add(getFloderectory(), null);
			cFloder.setBackground(Color.GRAY);
		}
		return cFloder;
	}


	public static JTextField getFloderectory() 
	{
		if (floderectory == null) {
			floderectory = new JTextField();
			floderectory.setBounds(new Rectangle(71, 5, 244, 27));
			floderectory.setEditable(false);
		}
		return floderectory;
	}

	public static JButton getUpFloder() 
	{
		if (upFloder == null) {
			upFloder = new JButton();
			upFloder.setBounds(new Rectangle(10, 106, 50, 50));
			upFloder.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					Memory.curFloderPos=Memory.floderCol[Memory.curFloderPos].upFloder;  
					Memory.tableRefresh(Memory.curFloderPos);
					int a = Memory.curFloderName.lastIndexOf("\\");
					Memory.curFloderName = Memory.curFloderName.substring(0, a);
					floderectory.setText(Memory.curFloderName);
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return upFloder;
	}


	public static JPanel getJEnter() 
	{
		if (jEnter == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			jEnter = new JPanel();
			jEnter.setLayout(null);
			jEnter.setBounds(new Rectangle(17, 139, 68, 237));
			jEnter.add(MyLabel1, gridBagConstraints);
			jEnter.add(getSEnter(), null);
			jEnter.add(MyLabel, null);
			jEnter.add(getUpFloder(), null);
			jEnter.setBackground(Color.GRAY);
		}
		return jEnter;
	}

	public static JButton getSEnter()
	{
		if (sEnter == null) {
			sEnter = new JButton("进入文件夹");
			sEnter.setBounds(new Rectangle(10, 44, 50, 50));

			sEnter.addActionListener(new java.awt.event.ActionListener() 
			{
				public void actionPerformed(java.awt.event.ActionEvent e) 
				{
					String tempName = (String)jTable.getValueAt(jTable.getSelectionModel().getLeadSelectionIndex(), 0);  
					String type = (String)jTable.getValueAt(jTable.getSelectionModel().getLeadSelectionIndex(), 1);  
					if (type.equals("floder") ) 
					{
						Integer tempFloder;
						for(int i=0;i<Memory.floderCol[Memory.curFloderPos].items.size();i++)
						{
							tempFloder = (Integer) Memory.floderCol[Memory.curFloderPos].items.elementAt(i);
							if(Memory.floderCol[tempFloder].name.equals(tempName) && Memory.floderCol[tempFloder].flag.equals(type))
								Memory.goToFloder(tempFloder);  
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null,"请选择一个文件夹！");
					}
					System.out.println("actionPerformed()");
				}
			});
		}
		return sEnter;
	}

	public static JScrollPane getJScrollPane()
	{
		if (jScrollPane == null)
		{
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(108, 168, 450, 400));
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	public static Vector rowData;
	public static JTable getJTable()
	{
		if (jTable == null) 
		{
			String names[] = { "名称", "类型","大小(Byte)" };
			Vector columnNames = new Vector( Arrays.asList( names ) );
			rowData = new Vector();
			jTable = new JTable(rowData, columnNames);
			jTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);  
		}
		return jTable;
	}


	public static JButton getFileOpen() {
		if (fOpen == null) {
			fOpen = new JButton("打开文件");
			fOpen.setBounds(new Rectangle(220, 40, 100, 20));

			fOpen.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (FileManager.openFlag == 0)   
					{
						String tempName = (String) jTable
								.getValueAt(jTable.getSelectionModel()
										.getLeadSelectionIndex(), 0);	
						String type = (String) jTable
								.getValueAt(jTable.getSelectionModel()   
										.getLeadSelectionIndex(), 1);
						if (type.equals( "file"))
						{
							fInput.setEditable(true);
							fInput.requestFocusInWindow();
							JOptionPane.showMessageDialog(null, "文件打开，已可进行编辑");
							Integer tempFloder;
							for (int i = 0; i < Memory.floderCol[Memory.curFloderPos].items.size(); i++)
							{
								tempFloder = (Integer) Memory.floderCol[Memory.curFloderPos].items
										.elementAt(i);
								if (Memory.floderCol[tempFloder].name.equals( tempName))
								{
									Memory.fileOpen(tempFloder);  
								}

							}
						}
						else {
							JOptionPane.showMessageDialog(null, "先选择一个文件再打开！");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "请先关闭已打开的文件，再打开其他文件！");
					}
					System.out.println("actionPerformed()"); 
				}
			});
		}
		return fOpen;
	}
	public static JButton getSFormat() {
		if (sFormat == null) {
			sFormat = new JButton("格式化");
			sFormat.setBounds(new Rectangle(10, 58, 100, 30));

			sFormat.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int choice=JOptionPane.showConfirmDialog(null, "格式化？","",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
					if (choice == JOptionPane.OK_OPTION)
					{
						for(int i=0;i<100;i++)  
						{
							Memory.floderCol[i].clear();
							Memory.spaces[i] = -1;
							Memory.block[i] = "";
						}
						Memory.floderCol[0].flag = "floder";
						Memory.floderCol[0].name = "LZL:";
						Memory.floderCol[0].upFloder = 0;
						Memory.curFloderName = Memory.floderCol[0].name;
						floderectory.setText(Memory.curFloderName);
						Memory.curFloderPos = 0;
						Memory.tableRefresh(0);
					}
					JOptionPane.showMessageDialog(null,"格式化完毕！");
					System.out.println("actionPerformed()"); 
				}
			});
		}
		return sFormat;
	}

	public static JPanel getSysPanel() 
	{
		if (Memory.sysPanel == null)
		{
			Memory.MyLabel9 = new JLabel();
			Memory.MyLabel9.setBounds(new Rectangle(70, 62, 58, 26));
			Memory.MyLabel8 = new JLabel();
			Memory.MyLabel8.setBounds(new Rectangle(10, 62, 60, 26));
			Memory.sysPanel = new JPanel();
			Memory.sysPanel.setLayout(null);
			Memory.sysPanel.setBounds(new Rectangle(700, 11, 203, 95));
			Memory.sysPanel.add(getSFormat(), null);
			Memory.sysPanel.add(MyLabel00, null);
			Memory.sysPanel.add(getOutPut(), null);
			Memory.sysPanel.add(getInPut(), null);
			Memory.sysPanel.add(Memory.MyLabel8, null);
			Memory.sysPanel.add(Memory.MyLabel9, null);
			Memory.sysPanel.setBackground(Color.GRAY);
		}
		return Memory.sysPanel;
	}
	public static JScrollPane getJTextlPane1() 
	{
		if (Memory.jTextlPane1 == null)
		{
			Memory.jTextlPane1 = new JScrollPane();
			Memory.jTextlPane1.setBounds(new Rectangle(700, 168, 350, 400));
			Memory.jTextlPane1.setViewportView(getFInput());
			Memory.jTextlPane1.setBackground(Color.GRAY);
		}
		return Memory.jTextlPane1;
	}
	public static JButton getOutPut() {
		if (Memory.outPut == null) {
			Memory.outPut = new JButton("输出系统");
			Memory.outPut.setBounds(new Rectangle(120, 8, 88, 30));

			Memory.outPut.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String name=JOptionPane.showInputDialog(null,"请输入要保存至的文件名： (将存放在与程序同一目录下)");
					Memory.sysSave(name);  
					JOptionPane.showMessageDialog(null,"系统输出至文件完毕！");
					System.out.println("actionPerformed()"); 
				}
			});
		}
		return Memory.outPut;
	}
	public static JButton getInPut() {
		if (Memory.inPut == null) {
			Memory.inPut = new JButton("读入系统");
			Memory.inPut.setBounds(new Rectangle(10, 8, 100, 30));
			Memory.inPut.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String name=JOptionPane.showInputDialog(null,"请输入要读入的文件名： (需存放在与程序同一目录下)");
					Memory.sysOpen(name); 
					JOptionPane.showMessageDialog(null,"读入系统的文件完毕！");
					System.out.println("actionPerformed()"); 
				}
			});
		}
		return Memory.inPut;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				FileManager application = new FileManager();
				application.getJFrame().setVisible(true);
			}
		});
	}
	public static JPanel textField = null;
	public static JLabel MyLabel1 = null;
	public static JLabel MyLabel2 = null;
	public static JLabel MyLabel3 = null;
	public static JLabel MyLabel4 = null;
	public static JLabel MyLabel5 = null;
	public static JLabel MyLabel6 = null;
	public static JPanel textField2 = null;
	public static JLabel MyLabel12 = null;
	public static JLabel MyLabel13 = null;
	public static JTextArea fInput = null;
	public static JPanel cFloder = null;
	public static JLabel currentFloder = null;
	public static JTextField floderectory = null;
	public static JButton upFloder = null;
	public static JLabel MyLabel = null;
	public static JLabel MyLabel00 = null;
	public static JPanel jEnter = null;
	public static JButton sEnter = null;
	public static JScrollPane jScrollPane = null;
	public static JTable jTable = null;
	public static JLabel MyLabel7 = null;
	public static JButton fOpen = null;
	public static JButton sFormat = null;
	
}
