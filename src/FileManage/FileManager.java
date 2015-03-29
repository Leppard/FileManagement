
import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FileManager {
	public static JPanel getWindowsButtons()
	{
		if (OptionButtons == null) 
		{
			OptionButtons = new JPanel();
			OptionButtons.setLayout(null);
			OptionButtons.setBounds(new Rectangle(150, 30, 364, 68));
			OptionButtons.add(FileOption.getFolderCreate(), null);
			OptionButtons.add(FileOption.getFolerDelete(), null);
			OptionButtons.add(FileOption.getFolerChange(), null);
			OptionButtons.add(FileOption.getFileCreate(), null);
			OptionButtons.add(FileOption.getFileDelete(), null);
			OptionButtons.add(FileOption.getFileOpen(), null);
			OptionButtons.setBackground(Color.GRAY);
		}
		return OptionButtons;
	}
	public static JPanel getMyContentPane() 
	{
		if (MyContentPane == null)
		{
			MyContentPane = new JPanel();
			MyContentPane.setLayout(null);
			MyContentPane.add(getWindowsButtons(), null);
			MyContentPane.add(FileOption.getFileButtons(), null);
			MyContentPane.add(FileOption.getTextField(), null);
			MyContentPane.add(FileOption.getTextField2(), null);
			MyContentPane.add(FileOption.getCFloder(), null);
			MyContentPane.add(FileOption.getJEnter(), null);
			MyContentPane.add(FileOption.getJScrollPane(), null);
			MyContentPane.add(FileOption.getSysPanel(), null);
			MyContentPane.add(FileOption.getJTextlPane1(), null);
			MyContentPane.setBackground(Color.GRAY);
		}
		return MyContentPane;
	}
	public static JFrame getJFrame() 
	{
		if (MyFrame == null) 
		{
			MyFrame = new JFrame();
			MyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			MyFrame.setSize(1100, 650);
			MyFrame.setLocation(200,50);
			MyFrame.setContentPane(getMyContentPane());
			MyFrame.setTitle("文件管理系统");
			MyFrame.setUndecorated(true);
			MyFrame.getRootPane().setWindowDecorationStyle(3);
			Memory.go();
		}
		return MyFrame;
	}

	public static JFrame MyFrame = null; 
	public static JPanel MyContentPane = null;
	public static JPanel OptionButtons = null;
	public static JButton FolderCreate = null;
	public static JButton FolderDelete = null;
	public static JButton FolderChange = null;
	public static JButton FileCreate = null;
	public static JButton FileDelete = null;
	public static JPanel fileButtons = null;
	public static JButton FileSave = null;
	public static JButton FileClose = null;
	public static int openFlag = 0;
}
