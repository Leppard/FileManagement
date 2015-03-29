import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class Memory {
	public static FloderStr[] floderCol = new FloderStr[100];  
	public static int[] spaces  = new int[100];  
	public static String[] block = new String[100]; 
	public static String curFloderName;  
	public static int curFloderPos;  
	public static JPanel sysPanel = null;
	public static JScrollPane jTextlPane1 = null;
	public static JButton outPut = null;
	public static JButton inPut = null;
	public static JLabel MyLabel8 = null;
	public static JLabel MyLabel9 = null;

	public static  void go()
	{
		for(int i=0;i<100;i++)
		{
			floderCol[i] = new FloderStr();
			spaces[i] = -1;
			block[i] =new String("");
		}
		floderCol[0].flag = "folder";
		floderCol[0].name = "LZL:";
		floderCol[0].upFloder = 0;
		curFloderName = floderCol[0].name;
		FileOption.floderectory.setText(curFloderName);
		curFloderPos = 0;
		tableRefresh(0);

	}
	public static void tableRefresh(int a)
	{
		FileOption.rowData.clear();
		Integer tempFloder;
		for(int i=0;i<floderCol[a].items.size();i++)
		{
			Vector temp = new Vector();
			tempFloder = (Integer) floderCol[a].items.elementAt(i);
			temp.add(floderCol[tempFloder].name);
			temp.add(floderCol[tempFloder].flag);
			temp.add(floderCol[tempFloder].size);
			FileOption.rowData.add(temp);
		}
		FileOption.jTable.updateUI();
	}
	public static void goToFloder(int a)
	{
		curFloderPos = a;	
		tableRefresh(curFloderPos);
		curFloderName = curFloderName+"\\"+floderCol[a].name;
		FileOption.floderectory.setText(curFloderName);
	}
	public static int findFreeFCB()
	{
		for(int i=1;i<100;i++)
		{
			if ( floderCol[i].upFloder == -1 )
			{
				return i;
			}
		}
		return 100;
	}
	public static void createFile(String fileNew,int fileSize)
	{

		int position = findFreeFCB();
		if (position == 100)
		{
			JOptionPane.showInputDialog(null,"空间已满！");
		}
		else
		{
			floderCol[position].name = fileNew;
			floderCol[position].flag = "file";
			floderCol[position].upFloder = curFloderPos;
			floderCol[position].size = fileSize;

			int blockNum = fileSize / 1000 + 1;	
			int control = 0;
			int cur = -1;
			int next= -1;
			cur = nextFreeBlock();
			spaces[cur] = cur;
			floderCol[position].position = cur;
			if (blockNum > 1)
			{
				while (control < blockNum-1)	
				{
					next = nextFreeBlock();
					spaces[cur] = next;
					spaces[next] = next;
					cur = next;
					if (next == -1)	
					{
						JOptionPane.showInputDialog(null,"存储空间已满！");
					}
					control++;
				}
			}
			spaces[cur] = 100;  

			floderCol[curFloderPos].items.add(position);	
			tableRefresh(curFloderPos);

		}

	}
	public static void createFloder(String name)
	{
		int position = findFreeFCB();
		if (position == 100)
		{
			JOptionPane.showInputDialog(null,"FCB空间已满!");
		}
		else
		{
			floderCol[position].flag = "floder";
			floderCol[position].name = name;
			floderCol[position].upFloder = curFloderPos;
			floderCol[curFloderPos].items.add(position);
			tableRefresh(curFloderPos);
		}
	}

	public  static int nextFreeBlock()
	{
		for (int i=0;i<100;i++)
		{
			if (spaces[i] == -1)
			{
				return i;
			}
		}
		return -1;
	}
	public  static void deleteFile(int i)
	{
		int next = floderCol[i].position;
		int cur;
		cur = next;
		while(spaces[cur] != 100)
		{
			block[cur] = "";
			next = spaces[cur];
			spaces[cur] = -1;
			cur = next;
		}
		block[cur] = "";
		spaces[cur] = -1;

		floderCol[curFloderPos].items.remove((Object)i);  
		floderCol[i].clear();   
	}

	public  static void deleteFloder(int position)
	{
		int tempFloder;
		for(int i=0;i<floderCol[position].items.size();i++)	
		{
			tempFloder = (Integer) floderCol[position].items.elementAt(i);
			if(floderCol[tempFloder].flag.equals( "file"))	
			{
				deleteFile(tempFloder);
			}
			else	
			{
				deleteFloder(tempFloder);
			}
		}
		floderCol[curFloderPos].items.remove((Object)position);  
		floderCol[position].clear();
	}
	public  static void fileOpen(int pos)
	{
		int cur;
		FileManager.openFlag = pos;
		cur = floderCol[pos].position; 
		FileOption.fInput.setText(block[cur]);	
		while(spaces[cur] != 100)	
		{
			cur = spaces[cur];
			FileOption.fInput.append(block[cur]);
		}
	}

	public  static void fileSave()
	{
		String content;
		content = FileOption.fInput.getText();
		int cur;
		cur = floderCol[FileManager.openFlag].position;
		while(content.length() > 1000)	
		{
			if (cur != 100) 
			{
				String temp = content.substring(0, 1000);	
				block[cur] = temp;
				cur = spaces[cur];
				content = content.substring(1000, content.length());	
			}
			else
			{
				JOptionPane.showMessageDialog(null, "文件大小已超过预定义的块数，多余部分将无法保存");
				break;
			}
		}
		if (cur != 100)
		{
			block[cur] = content;
			JOptionPane.showMessageDialog(null, "文件已保存");
		}
		else
		{
			JOptionPane.showMessageDialog(null, "文件大小已超过预定义的块数，多余部分将无法保存");
		}

	}
	public  static void fileClose()
	{
		FileOption.fInput.setText("");
		FileManager.openFlag = 0;
		JOptionPane.showMessageDialog(null, "文件已关闭");
		FileOption.fInput.setEditable(false);	
	}

	public  static void sysSave(String name)
	{
		try
		{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(name));

			out.writeObject(floderCol);
			out.writeObject(spaces);
			out.writeObject(block);

			out.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public  static void sysOpen(String name)
	{
		try
		{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(name));

			floderCol = (FloderStr[]) in.readObject();
			spaces = (int[]) in.readObject();
			block = (String[]) in.readObject();

			curFloderName = floderCol[0].name;
			FileOption.floderectory.setText(curFloderName);
			curFloderPos = 0;
			tableRefresh(0);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static boolean ifSameName(String name,String type)
	{
		Integer tempFloder;
		for(int i=0;i<floderCol[curFloderPos].items.size();i++)
		{
			tempFloder = (Integer) floderCol[curFloderPos].items.elementAt(i);
			if(floderCol[tempFloder].name.equals(name) && floderCol[tempFloder].flag.equals(type))
				return true;
		}
		return false;
	}
}


class FloderStr implements Serializable
{
	public Vector items = new Vector(); 
	String flag;    
	String name;  	
	int position=-1;  
	int size=0;    
	int upFloder=-1;       
	public void clear()	
	{
		items.clear();
		flag = "";
		name = "";
		position = -1;
		size = 0;
		upFloder = -1;
	}
}
