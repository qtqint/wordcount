package ��Ұ4;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
 
public class mainframe extends JFrame{
	private JTextField textField;
	private JPanel panel = new JPanel();
	private JFileChooser fileChooser = new JFileChooser();		
	String selectedFile;
	//����������
	public mainframe() {
		setTitle("wc.exe");
		setBounds(400, 400, 600, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		final JLabel label = new JLabel();
		label.setText("�ļ���");
		panel.add(label);
		textField = new JTextField();
		textField.setColumns(20);
		panel.add(textField);

		JButton button = new JButton("ѡ���ļ�");
		JButton _char = new JButton("-c");	//�����ļ� file.c ���ַ���
		JButton _word = new JButton("-w");	//�����ļ� file.c �Ĵʵ���Ŀ
		JButton _line = new JButton("-l");	//�����ļ� file.c ������
		JButton _other = new JButton("-a");	 //���ظ����ӵ����ݣ������� / ���� / ע���У�
		
		_char.setMargin(new Insets(0,20,10,20));
		_word.setMargin(new Insets(0,10,10,20));
		_line.setMargin(new Insets(0,10,10,20));
		_other.setMargin(new Insets(0,10,10,20));
	
		
		JLabel labelin = new JLabel("�ݹ�����ļ���(-s)");
		JTextField textField1 = new JTextField(16);
		JButton inbutton = new JButton("ȷ��");
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		contentPane.add(labelin);
		contentPane.add(textField1);
		contentPane.add(inbutton);
		
		button.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {				
				int i = fileChooser.showOpenDialog(getContentPane());// ��ʾ�ļ�ѡ��Ի���	
				if (i == JFileChooser.APPROVE_OPTION) {				
					selectedFile = fileChooser.getSelectedFile().getAbsolutePath();// ���ѡ�е��ļ�����
					String[] filehouzhui = selectedFile.split("\\.");
					int Index = filehouzhui.length -1;
			        System.out.println(filehouzhui[Index]);
					System.out.println(filehouzhui);
					textField.setText(selectedFile);// ��ʾѡ���ļ�������					
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
				int num = c(selectedFile);	//�����ļ� file.c ���ַ���
				JOptionPane.showMessageDialog(null, selectedFile+"�ַ���Ϊ"+num);
			}
		});
		_word.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int num = w(selectedFile);	//�����ļ� file.c �Ĵʵ���Ŀ
				JOptionPane.showMessageDialog(null, selectedFile+"�Ĵ���Ϊ"+num);
			}
		});
		_line.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int num = l(selectedFile);	//�����ļ� file.c ������
				JOptionPane.showMessageDialog(null, selectedFile+"������Ϊ"+num);
			}
		});
		_other.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n1 = o(selectedFile,1);	//����
				int n2 = o(selectedFile,2);	//������
				int n3 = o(selectedFile,3);	//ע����
				JOptionPane.showMessageDialog(null, selectedFile+"�Ŀ�����Ϊ"+n1+'\n'+selectedFile+"��������Ϊ"+n2+'\n'+selectedFile+"ע������Ϊ"+n3);
			}
		});		
		inbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = textField1.getText();
				listFile(str);			//�ݹ�����ļ����������ļ�	
			}
		});
	}
	

	public static void listFile(String path)  
    {  
		File dir = new File(path);
        File[] files=dir.listFiles();   //�г��������ļ�  
        for(File file :files)  
        {  
            if(file.isFile())//���ļ�������ļ�����  
            {  
            	due(path+"\\"+file.getName());
                System.out.println(path+"\\"+file.getName());  
            }else if(file.isDirectory())//���ļ���������ļ��е����֣����ݹ����  
            {   
                System.out.println(path+file.getName()); 
                listFile(path);
            }  
        }  
    }
	public static void due(String file) {	//-s   �ݹ鴦��Ŀ¼�·����������ļ�
		int n1 = c(file);
		int n2 = w(file);
		int n3 = l(file);
		int n4 = o(file,1);
		int n5 = o(file,2);
		int n6 = o(file,3);
		JOptionPane.showMessageDialog(null, file+"���ַ���Ϊ"+n1+"\n"+file+"�ĵ�����Ϊ"+n2+"\n"+file+"������Ϊ"
		+n3+"\n"+file+"�Ŀ�����Ϊ"+n4+"\n"+file+"�Ĵ�������Ϊ"+n5+"\n"+file+"��ע������Ϊ"+n6);
	}
	public static int o(String file,int t) {	//-a,t=1���ش����� t=2���ؿ��� t=3����ע����
		int konghang = 0;
		int daimahang = 0;
		int zhushihang = 0;
		int j = 0;	//j�Ǳ��
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
	public static int l(String file) {	//�����ļ� file.c ������
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
	public static int w(String file) {	 //�����ļ� file.c �Ĵʵ���Ŀ
		int wordnum = 0;int i,j=0;	//j�Ǳ��
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
	public static int c(String file) {	 //�����ļ� file.c ���ַ���
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
