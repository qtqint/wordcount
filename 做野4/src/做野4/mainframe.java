package 做野4;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
 
public class mainframe extends JFrame{
	private JTextField textField;
	private JPanel panel = new JPanel();
	private JFileChooser fileChooser = new JFileChooser();		
	String selectedFile;
	//主窗体设置
	public mainframe() {
		setTitle("wc.exe");
		setBounds(400, 400, 600, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		final JLabel label = new JLabel();
		label.setText("文件：");
		panel.add(label);
		textField = new JTextField();
		textField.setColumns(20);
		panel.add(textField);

		JButton button = new JButton("选择文件");
		JButton _char = new JButton("-c");	//返回文件 file.c 的字符数
		JButton _word = new JButton("-w");	//返回文件 file.c 的词的数目
		JButton _line = new JButton("-l");	//返回文件 file.c 的行数
		JButton _other = new JButton("-a");	 //返回更复杂的数据（代码行 / 空行 / 注释行）
		
		_char.setMargin(new Insets(0,20,10,20));
		_word.setMargin(new Insets(0,10,10,20));
		_line.setMargin(new Insets(0,10,10,20));
		_other.setMargin(new Insets(0,10,10,20));
	
		
		JLabel labelin = new JLabel("递归遍历文件夹(-s)");
		JTextField textField1 = new JTextField(16);
		JButton inbutton = new JButton("确定");
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		contentPane.add(labelin);
		contentPane.add(textField1);
		contentPane.add(inbutton);
		
		button.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {				
				int i = fileChooser.showOpenDialog(getContentPane());// 显示文件选择对话框	
				if (i == JFileChooser.APPROVE_OPTION) {				
					selectedFile = fileChooser.getSelectedFile().getAbsolutePath();// 获得选中的文件对象
					String[] filehouzhui = selectedFile.split("\\.");
					int Index = filehouzhui.length -1;
			        System.out.println(filehouzhui[Index]);
					System.out.println(filehouzhui);
					textField.setText(selectedFile);// 显示选中文件的名称					
				}
			}
		});
		panel.add(button);
		panel.add(_char);
		panel.add(_word);
		panel.add(_line);
		panel.add(_other);
		
		add(panel, BorderLayout.NORTH);
		setVisible(true);
		
		_char.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
				int num = c(selectedFile);	//返回文件 file.c 的字符数
				JOptionPane.showMessageDialog(null, selectedFile+"字符数为"+num);
			}
		});
		_word.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int num = w(selectedFile);	//返回文件 file.c 的词的数目
				JOptionPane.showMessageDialog(null, selectedFile+"的词数为"+num);
			}
		});
		_line.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int num = l(selectedFile);	//返回文件 file.c 的行数
				JOptionPane.showMessageDialog(null, selectedFile+"的行数为"+num);
			}
		});
		_other.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n1 = o(selectedFile,1);	//空行
				int n2 = o(selectedFile,2);	//代码行
				int n3 = o(selectedFile,3);	//注释行
				JOptionPane.showMessageDialog(null, selectedFile+"的空行数为"+n1+'\n'+selectedFile+"代码行数为"+n2+'\n'+selectedFile+"注释行数为"+n3);
			}
		});		
		inbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = textField1.getText();
				listFile(str);			//递归遍历文件夹内所有文件	
			}
		});
	}
	

	public static void listFile(String path)  
    {  
		File dir = new File(path);
        File[] files=dir.listFiles();   //列出所有子文件  
        for(File file :files)  
        {  
            if(file.isFile())//是文件则输出文件名字  
            {  
            	due(path+"\\"+file.getName());
                System.out.println(path+"\\"+file.getName());  
            }else if(file.isDirectory())//是文件夹则输出文件夹的名字，并递归遍历  
            {   
                System.out.println(path+file.getName()); 
                listFile(path);
            }  
        }  
    }
	public static void due(String file) {	//-s   递归处理目录下符合条件的文件
		int n1 = c(file);
		int n2 = w(file);
		int n3 = l(file);
		int n4 = o(file,1);
		int n5 = o(file,2);
		int n6 = o(file,3);
		JOptionPane.showMessageDialog(null, file+"的字符数为"+n1+"\n"+file+"的单词数为"+n2+"\n"+file+"的行数为"
		+n3+"\n"+file+"的空行数为"+n4+"\n"+file+"的代码行数为"+n5+"\n"+file+"的注释行数为"+n6);
	}
	public static int o(String file,int t) {	//-a,t=1返回代码行 t=2返回空行 t=3返回注释行
		int konghang = 0;
		int daimahang = 0;
		int zhushihang = 0;
		int j = 0;	//j是标记
		String temp = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		    while ((temp = br.readLine()) != null) {
		    	temp = temp.replaceAll("\\{","").replaceAll("\\}","");
		    	if(j==0) {
		        	if((temp.trim().indexOf("/*")==0))	{zhushihang++;j=1;}
		        	else if((temp.trim().indexOf("//")==0))	zhushihang++;
		        	else if(temp.trim().length()>0)	daimahang++;
		        	else konghang++;
		    	}
		    	else{
		    		zhushihang++;
		        	if(temp.contains("*/")) 	j=0;		        	
		        }
		    	}
		    	//br.close();
		    } catch (FileNotFoundException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            br.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
			if(t==1)	return konghang;
			else if(t==2)	return daimahang;
			else	return zhushihang;
	}
	public static int l(String file) {	//返回文件 file.c 的行数
		int linenum = 0;
	    BufferedReader br = null;
	    try {
	    br = new BufferedReader(new FileReader(file));
	    String temp = null;
	    while (((temp = br.readLine()) != null)) {
	        	linenum++;
	    	}
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            br.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return linenum;
	}
	public static int w(String file) {	 //返回文件 file.c 的词的数目
		int wordnum = 0;int i,j=0;	//j是标记
		BufferedReader br = null;
	    try {
	    br = new BufferedReader(new FileReader(file));
	    String temp = "";
	    while ((temp = br.readLine()) != null) {
	        char[] des = temp.trim().toCharArray();
	        for (i=0;i<des.length;i++) {
	        	if (j==0&((des[i]>=48&des[i]<=57)|(des[i]>=65&des[i]<=90)|(des[i]>=97&des[i]<=122)))	{wordnum++;j=1;}
	        	if(des[i]<48|(des[i]>57&des[i]<65)|(des[i]>90&des[i]<97)|des[i]>122)	j=0;
	        }}
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            br.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return wordnum;
	}
	public static int c(String file) {	 //返回文件 file.c 的字符数
		int charnum = 0;int i;	
	    BufferedReader br = null;
	    try {
	    br = new BufferedReader(new FileReader(file));
	    String temp = "";
	    while ((temp = br.readLine()) != null) {
	        char[] des = temp.trim().toCharArray();
	        for (i=0;i<des.length;i++)	 
	        	if (des[i]!=' '&des[i]!='\t')	charnum++;
	        }
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            br.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return charnum;
	}

	public static void main(String[] args) {
		mainframe test = new mainframe();
	}
}
