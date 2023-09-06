
package chattingapplication;

import java.awt.Image;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import javax.swing.border.*;
//for the scrollpane editing 
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

import java.util.Calendar;
import java.text.DateFormat; 

/**
 *
 * @author Sushil
 */
public class Server  implements ActionListener {
 
    
    JPanel p1;
    JTextField t1;
    JButton b1;
    static JPanel a1;
    
    static JFrame f1= new JFrame();
    
   static Box vertical = Box.createVerticalBox();
    
    //Socketing
   static ServerSocket skt;
   static Socket s;
   static DataInputStream din;
   static DataOutputStream dout;
   
   //it is  for changing active now to typing...
   Boolean typing;//by default it is false
   
    Server(){ 
        f1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 450, 50);
        f1.add(p1);
        
      ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("chattingapplication/icons/3.png"));
      Image i2=i1.getImage().getScaledInstance(30,30 , Image.SCALE_DEFAULT);
      ImageIcon i3=new ImageIcon(i2);
      JLabel l1= new JLabel(i3);
        l1.setBounds(5,10,30,30);
        p1.add(l1);
    
        //working on the back  button to exit the window 
        l1.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent ae){
        System.exit(0);
        }
        });
      
        
           ImageIcon i4= new ImageIcon(ClassLoader.getSystemResource("chattingapplication/icons/1.png"));
      Image i5=i4.getImage().getScaledInstance(30,30 , Image.SCALE_DEFAULT);
      ImageIcon i6=new ImageIcon(i5);
      JLabel l2= new JLabel(i6);
        l2.setBounds(40,10,30,30);
        p1.add(l2);
        
        //1
         
           ImageIcon i7= new ImageIcon(ClassLoader.getSystemResource("chattingapplication/icons/video.png"));
      Image i8=i7.getImage().getScaledInstance(30,30 , Image.SCALE_DEFAULT);
      ImageIcon i9=new ImageIcon(i8);
      JLabel l5= new JLabel(i9);
        l5.setBounds(320,10,30,30);
        p1.add(l5);
        //2
         
     
        ImageIcon i11= new ImageIcon(ClassLoader.getSystemResource("chattingapplication/icons/phone.png"));
      Image i12=i11.getImage().getScaledInstance(30,30 , Image.SCALE_DEFAULT);
      ImageIcon i13=new ImageIcon(i12);
      JLabel l6= new JLabel(i13);
        l6.setBounds(370,12,30,30);
        p1.add(l6);
        //3
         
           ImageIcon i14= new ImageIcon(ClassLoader.getSystemResource("chattingapplication/icons/3icon.png"));
      Image i15=i14.getImage().getScaledInstance(10,25 , Image.SCALE_DEFAULT);
      ImageIcon i16=new ImageIcon(i15);
      JLabel l7= new JLabel(i16);
        l7.setBounds(410,10,22,30);
        p1.add(l7);
      
        
        JLabel l3 = new JLabel("JasbirSingh ");
        l3.setFont( new Font("Serif",Font.BOLD,20) );
        l3.setForeground(Color.WHITE);
        l3.setBounds(79, 10, 100, 18);
        p1.add(l3);
        
         
        JLabel l4 = new JLabel("Active Now");
        l4.setFont( new Font("Serif",Font.BOLD,12) );
        l4.setForeground(Color.WHITE);
        l4.setBounds(79, 26, 100, 20);
        p1.add(l4);
        
       //timer used
        Timer t=new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
               if(!typing){
                   l4.setText("Active Now");
               }
            }
        });
        t.setInitialDelay(2000);//takes time in the millisecond
        
        //object for the textarea
        a1=new JPanel();
        a1.setBackground(Color.WHITE);
       // a1.setBounds(3, 52,433,560);//this is commented because we make these things in scrollpane now
        a1.setFont( new Font("Serif",Font.PLAIN,16) );
        //f1.add(a1);//as we make this in scroll pane
        
        //adding scroll bar to panel
        JScrollPane sp = new JScrollPane(a1);
        sp.setBounds(3, 52,433,560);
        sp.setBorder(BorderFactory.createEmptyBorder());//we have to remove the border 
        
        
        //editing of scrollpane(increase,decrease,track)
        //so we have to import the abstract class ScrollBarUi and make object of its child class
        ScrollBarUI ui= new BasicScrollBarUI(){
        protected JButton createDecreaseButton(int orientation){
        
            JButton button = super.createDecreaseButton(orientation);
            button.setBackground(new Color( 7, 94, 84));
            button.setForeground(Color.WHITE);
            this.thumbColor = new Color( 7, 94, 84);//this is used to change color of the scroll
           // this.trackColor = new Color(7,83,93);//to change the track color 
            return button;
        }
        protected JButton createIncreaseButton(int orientation){
        
            JButton button = super.createIncreaseButton(orientation);
            button.setBackground(new Color( 7, 94, 84));
            button.setForeground(Color.WHITE);
           this.thumbColor = new Color( 7, 94, 84);
            return button;
        }
        
        };
        sp.getVerticalScrollBar().setUI(ui);
        f1.add(sp);
        
        //for the textfield to write it into
        t1= new JTextField();//object for the textfeild
        t1.setBounds(5,615,310,40);
        t1.setFont( new Font("Serif",Font.PLAIN,16));
        f1.add(t1);
        
        //working on the textfield working to change the active status to typing....
        t1.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent ke){
        l4.setText("typing...");
        
        t.stop();
        typing=true;
         }
         public void keyReleased(KeyEvent ke){
              
         typing=false; 
         
         if(!t.isRunning()){
         t.start();//this is used as when the typing is stopped then the timer will be started for 2sec and then the Active now again appears
         }
         }   }
        );
        
        //button which play a role for sending the text
        b1=new JButton ("Send");
        b1.setBounds(320,615,114,40);
        b1.setBackground(new Color(7, 94, 84));
        b1.setForeground(Color.WHITE);
        b1.setFont( new Font("Serif",Font.PLAIN,16));
       b1.addActionListener(this);
        f1.add(b1);
        
        
        
  //CHANGING THE BACKGROUND THEME 
      f1.getContentPane().setBackground(Color.WHITE);
       f1.setLayout(null);              
      f1.setSize(455,700);
      f1.setLocation(100,100);
  //IS TO REMOVE THE TOP DEFAULT BORDER WHICH CONTAIN THE OPTION OF MINMIZE ETX...
      //setUndecorated(true);
      f1.setVisible(true);

  
  
}    
 
public void actionPerformed(ActionEvent ae)
{ try {
     String out = t1.getText();
     sendTextToFile(out);//the textarea message is sending for storing into the file
     JPanel p2 = formatLabel(out);//for giving the output in formated string for bubble 
     
     a1.setLayout(new BorderLayout());
     
     JPanel right=new JPanel(new BorderLayout());
     right.add(p2,BorderLayout.LINE_END);
     vertical.add(right);
     vertical.add(Box.createVerticalStrut(15));
     
     a1.add(vertical,BorderLayout.PAGE_START);
     
     //a1.add(p2);
     dout.writeUTF(out);
     t1.setText(" ");
        
    } catch (Exception e) {
   
    }

}

public void  sendTextToFile(String message) throws FileNotFoundException {
    try(FileWriter f = new FileWriter("chat.txt",true);//we have to pass the true value as if we dont pss it it dont allow us to append in the file..
            PrintWriter p = new PrintWriter (new BufferedWriter(f));){//buffered is used to take every single character from the sending message
            p.println("Jasbir:" + message);        
        
        
        }catch(Exception e){
        e.printStackTrace();
        }
        
        
}

//funtion for the panel buble layout
        public static JPanel formatLabel(String out){
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3,BoxLayout.Y_AXIS ));
        
        JLabel l1 = new JLabel("<html><p style=\"width:150px\">"+out+"</p></html>");
        l1.setFont(new Font("Comic-Sans",Font.PLAIN,18));
        l1.setBackground(new Color( 37, 211, 102));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(15,15,15,15));
        
        Calendar cal =Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));
        
        p3.add(l2);//as label (l2) is of time-format it is our wish where we have to print it in the above label which print the sended text or below the text                       
        
        p3.add(l1);

        return p3;
        }
        
    
    public static void main (String[] args)
{
new Server().f1.setVisible(true);

String msginput = "";
 
    try {
       skt = new ServerSocket(6001);
       while(true){
       s = skt.accept();
       
       din= new DataInputStream(s.getInputStream());
       dout =new DataOutputStream(s.getOutputStream());
           
       
       while(true){
           msginput=din.readUTF();
           JPanel p2 = formatLabel(msginput);
           JPanel left= new JPanel(new BorderLayout());
           left.add(p2,BorderLayout.LINE_START);
           vertical.add(left);
           f1.validate();
       }
       } 
    } catch (Exception e) {
    }
    
}
}
