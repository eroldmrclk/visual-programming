package P1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class denemep6 extends JFrame{
	private DrawingPanel dp;
	private int ws;
	private int w,h,i=0;
	private FileInputStream fis;
	private int [] Image;
	private Color [] ImageColor;
	private Timer timer;
	denemep6(){
		timer=new Timer(1000,new TimerListener());

		try {
			fis = new FileInputStream(PFile.filename);
			String mn = getMagicNumber();
			System.out.println(mn);
			
			skipWhitespace();
			
			w = readNumber();
			
			skipWhitespace();
			
			h = readNumber();
						
			skipWhitespace();
			
			int maxNum = readNumber();
			
			Image=new int[w*h*3];
			
			for(int i=0;i<w*h*3;i++) {
				Image[i]=fis.read();					

			}
			
			ImageColor=new Color[w*h];
			for(int i=0;i<w*h;i++) {
				ImageColor[i] = new Color(Image[3*i],Image[3*i+1],Image[3*i+2]);
			
			}
			dp=new DrawingPanel();
			this.add(dp);
			this.setSize(400,400);
			this.setVisible(true);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		catch(IOException e2) {}

	}
	
	
	private String getMagicNumber() {
		byte [] magicNum = new byte[2];
		try {
			fis.read(magicNum);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(magicNum);
	}
	
	
	private void skipWhitespace() {
		try {
			ws = fis.read();

			while(Character.isWhitespace(ws))
				ws = fis.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	private int readNumber() {
		String wstr="";
		try {
			while(!Character.isWhitespace(ws)) {
					wstr = wstr + (ws-'0');
					ws = fis.read();
				}
		}catch(IOException e2) {}
		
		System.out.println(wstr);
		return Integer.parseInt(wstr);
	}
	
	
class TimerListener implements ActionListener{
		
		public void actionPerformed(ActionEvent evt) {
			i++;
			if(i<6) {
			   dp.repaint();
			}   

		
		}
}


	class DrawingPanel extends JPanel{
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			for(int a=0; a<(w/5)+(i*w/5); a++)
				for(int b=0; b<h; b++) {
					if(a<w && b<h) {
						g.setColor(ImageColor[a*h+b]);
						g.fillRect(b, a, 1, 1);
					}
				}
			timer.start();
		}
	}
	public static void main(String [] args) {
		new denemep6();
	}
	
}