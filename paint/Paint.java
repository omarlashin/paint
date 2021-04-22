package paint;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.util.*;

abstract class Shape {
    protected int x;
    protected int y;
    protected Color c;
    
    public Shape(int x,int y,Color c){
        this.x=x;
        this.y=y;
        this.c=c;
    }
    
    public abstract void draw(Graphics g);
}
class Rectangle extends Shape {
    private int width;
    private int height;
    private boolean filled;
    
    public Rectangle(int x,int y,Color c,int w,int h,boolean f){
        super(x,y,c);
        width=w;
        height=h;
        filled=f;
    }
    
    @Override
    public void draw(Graphics g){
        g.setColor(c);
        if(filled)
            g.fillRect(x,y,width,height);
        else
            g.drawRect(x,y,width,height);
    }
}
class Circle extends Shape {
    private int width;
    private int height;
    private boolean filled;
    
    public Circle(int x,int y,Color c,int w,int h,boolean f){
        super(x,y,c);
        width=w;
        height=h;
        filled=f;
    }
    
    @Override
    public void draw(Graphics g){
        g.setColor(c);
        if(filled)
            g.fillOval(x,y,width,height);
        else
            g.drawOval(x,y,width,height);
    }
}
class Line extends Shape {
    private int x2;
    private int y2;
    
    public Line(int x,int y,Color c,int x2,int y2){
        super(x,y,c);
        this.x2=x2;
        this.y2=y2;
    }
    
    @Override
    public void draw(Graphics g){
        g.setColor(c);
        g.drawLine(x,y,x2,y2);
    }
}

public class Paint {
    
    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch(Exception e){}
        PaintFrame PF=new PaintFrame();
        PF.setVisible(true);
    }
    
}

class PaintPanel extends JPanel {
    ArrayList<Shape> Shapes=new ArrayList<>();
    private BufferedImage img=null;
    
    public void setImage(BufferedImage img){
        this.img=img;
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);
        if(img!=null)
            g.drawImage(img,0,0,null);
        for(int i=0;i<Shapes.size();i++)
            Shapes.get(i).draw(g);
    }
}

enum DrawingStyle{FREE,LINE,RECT,CIRC}

class PaintFrame extends JFrame implements ActionListener, AdjustmentListener {
    private Color col=Color.BLACK;
    private DrawingStyle sty=DrawingStyle.FREE;
    private int x1;
    private int y1;
    private Shape oldShape=null;
    private JMenuBar mnuBar;
    private JMenu mnuFile;
    private JMenu mnuColor;
    private JMenu mnuShape;
    private JMenu mnuHelp;
    private JMenuItem mnuItmNew;
    private JMenuItem mnuItmOpen;
    private JMenuItem mnuItmSave;
    private JMenuItem mnuItmExit;
    private JMenuItem mnuItmBlack;
    private JMenuItem mnuItmRed;
    private JMenuItem mnuItmGreen;
    private JMenuItem mnuItmBlue;
    private JMenuItem mnuItmErase;
    private JMenuItem mnuItmOther;
    private JMenuItem mnuItmFree;
    private JMenuItem mnuItmLine;
    private JMenuItem mnuItmRect;
    private JMenuItem mnuItmCirc;
    private JCheckBoxMenuItem mnuItmFilled;
    private JMenuItem mnuItmHelp;
    private JMenuItem mnuItmAbout;
    private PaintPanel paintPnl;
    private JPanel optPnl;
    private JPanel colPnl;
    private JPanel shpPnl;
    private JButton btnFree;
    private JButton btnLine;
    private JButton btnRect;
    private JButton btnCirc;
    private JCheckBox chkFilled;
    private JPanel colorPnl;
    private JButton btnBlack;
    private JButton btnRed;
    private JButton btnGreen;
    private JButton btnBlue;
    private JButton btnErase;
    private JButton btnOther;
    private JPanel selPnl;
    private JPanel lblPnl;
    private JPanel sbPnl;
    private JPanel txtPnl;
    private JLabel lblRed;
    private JLabel lblGreen;
    private JLabel lblBlue;
    private JScrollBar sbRed;
    private JScrollBar sbGreen;
    private JScrollBar sbBlue;
    private JTextField txtRed;
    private JTextField txtGreen;
    private JTextField txtBlue;
    
    public PaintFrame(){
        initComponents();
    }
    private void initComponents(){
        setTitle("Paint");
        setBounds(100,100,800,600);
        setMinimumSize(new Dimension(400,300));
        setDefaultCloseOperation(3);
        Container c=getContentPane();
        c.setLayout(new BorderLayout());
        mnuFile=new JMenu("File");
        mnuItmNew=new JMenuItem("New");
        mnuItmOpen=new JMenuItem("Open...");
        mnuItmSave=new JMenuItem("Save...");
        mnuItmExit=new JMenuItem("Exit");
        mnuFile.add(mnuItmNew);
        mnuFile.add(mnuItmOpen);
        mnuFile.add(mnuItmSave);
        mnuFile.add(mnuItmExit);
        mnuColor=new JMenu("Color");
        mnuItmBlack=new JMenuItem("Black");
        mnuItmRed=new JMenuItem("Red");
        mnuItmGreen=new JMenuItem("Green");
        mnuItmBlue=new JMenuItem("Blue");
        mnuItmErase=new JMenuItem("Erase");
        mnuItmOther=new JMenuItem("Other...");
        mnuColor.add(mnuItmBlack);
        mnuColor.add(mnuItmRed);
        mnuColor.add(mnuItmGreen);
        mnuColor.add(mnuItmBlue);
        mnuColor.addSeparator();
        mnuColor.add(mnuItmErase);
        mnuColor.addSeparator();
        mnuColor.add(mnuItmOther);
        mnuShape=new JMenu("Shape");
        mnuItmFree=new JMenuItem("Free");
        mnuItmLine=new JMenuItem("Line");
        mnuItmRect=new JMenuItem("Rectangle");
        mnuItmCirc=new JMenuItem("Circle");
        mnuItmFilled=new JCheckBoxMenuItem("Filled");
        mnuShape.add(mnuItmFree);
        mnuShape.add(mnuItmLine);
        mnuShape.add(mnuItmRect);
        mnuShape.add(mnuItmCirc);
        mnuShape.addSeparator();
        mnuShape.add(mnuItmFilled);
        mnuHelp=new JMenu("Help");
        mnuItmHelp=new JMenuItem("Help...");
        mnuItmAbout=new JMenuItem("About...");
        mnuHelp.add(mnuItmHelp);
        mnuHelp.add(mnuItmAbout);
        mnuBar=new JMenuBar();
        mnuBar.add(mnuFile);
        mnuBar.add(mnuColor);
        mnuBar.add(mnuShape);
        mnuBar.add(mnuHelp);
        setJMenuBar(mnuBar);
        paintPnl=new PaintPanel();
        paintPnl.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                x1=e.getX();
                y1=e.getY();
            }
            @Override
            public void mouseReleased(MouseEvent e){
                oldShape=null;
            }
        });
        paintPnl.addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseDragged(MouseEvent e){
                if(sty!=DrawingStyle.FREE){
                    if(oldShape!=null)
                        paintPnl.Shapes.remove(oldShape);
                    int x2=e.getX();
                    int y2=e.getY();
                    Shape s;
                    switch(sty){
                        case LINE:s=new Line(x1,y1,col,x2,y2); break;
                        case RECT:s=new Rectangle(Math.min(x1,x2),Math.min(y1,y2),col,Math.abs(x1-x2),Math.abs(y1-y2),chkFilled.isSelected()); break;
                        default:s=new Circle(Math.min(x1,x2),Math.min(y1,y2),col,Math.abs(x1-x2),Math.abs(y1-y2),chkFilled.isSelected()); break;
                    }
                    paintPnl.Shapes.add(s);
                    paintPnl.repaint();
                    oldShape=s;
                }
                else{
                    int x2=e.getX();
                    int y2=e.getY();
                    Shape s=new Line(x1,y1,col,x2,y2);
                    paintPnl.Shapes.add(s);
                    x1=x2;
                    y1=y2;
                    paintPnl.repaint();
                }
            }
        });
        paintPnl.setBackground(Color.WHITE);
        optPnl=new JPanel();
        optPnl.setLayout(new GridLayout(2,1));
        shpPnl=new JPanel();
        shpPnl.setLayout(new GridLayout(5,1));
        shpPnl.setBorder(BorderFactory.createTitledBorder("Shape"));
        colorPnl=new JPanel();
        colorPnl.setLayout(new GridLayout(7,1));
        colorPnl.setBorder(BorderFactory.createTitledBorder("Color"));
        optPnl.add(shpPnl);
        optPnl.add(colorPnl);
        colPnl=new JPanel();
        colPnl.setLayout(new BorderLayout());
        colPnl.setPreferredSize(new Dimension(800,90));
        lblPnl=new JPanel();
        lblPnl.setLayout(new GridLayout(3,1));
        sbPnl=new JPanel();
        sbPnl.setLayout(new GridLayout(3,1));
        txtPnl=new JPanel();
        txtPnl.setLayout(new GridLayout(3,1));
        colPnl.add(lblPnl,BorderLayout.WEST);
        colPnl.add(sbPnl,BorderLayout.CENTER);
        colPnl.add(txtPnl,BorderLayout.EAST);
        c.add(paintPnl,BorderLayout.CENTER);
        c.add(optPnl,BorderLayout.EAST);
        c.add(colPnl,BorderLayout.SOUTH);
        btnFree=new JButton("Free");
        btnLine=new JButton("Line");
        btnRect=new JButton("Rectangle");
        btnCirc=new JButton("Circle");
        chkFilled=new JCheckBox("Filled");
        shpPnl.add(btnFree);
        shpPnl.add(btnLine);
        shpPnl.add(btnRect);
        shpPnl.add(btnCirc);
        shpPnl.add(chkFilled);
        btnBlack=new JButton("Black");
        btnRed=new JButton("Red");
        btnGreen=new JButton("Green");
        btnBlue=new JButton("Blue");
        btnErase=new JButton("Erase");
        btnOther=new JButton("Other...");
        selPnl=new JPanel();
        selPnl.setBackground(Color.BLACK);
        colorPnl.add(btnBlack);
        colorPnl.add(btnRed);
        colorPnl.add(btnGreen);
        colorPnl.add(btnBlue);
        colorPnl.add(btnErase);
        colorPnl.add(btnOther);
        colorPnl.add(selPnl);
        lblRed=new JLabel("Red");
        lblGreen=new JLabel("Green");
        lblBlue=new JLabel("Blue");
        sbRed=new JScrollBar(0,0,10,0,265);
        sbGreen=new JScrollBar(0,0,10,0,265);
        sbBlue=new JScrollBar(0,0,10,0,265);
        txtRed=new JTextField("0",3);
        txtGreen=new JTextField("0",3);
        txtBlue=new JTextField("0",3);
        lblPnl.add(lblRed);
        lblPnl.add(lblGreen);
        lblPnl.add(lblBlue);
        sbPnl.add(sbRed);
        sbPnl.add(sbGreen);
        sbPnl.add(sbBlue);
        txtPnl.add(txtRed);
        txtPnl.add(txtGreen);
        txtPnl.add(txtBlue);
        btnFree.addActionListener(this);
        btnLine.addActionListener(this);
        btnRect.addActionListener(this);
        btnCirc.addActionListener(this);
        btnBlack.addActionListener(this);
        btnRed.addActionListener(this);
        btnGreen.addActionListener(this);
        btnBlue.addActionListener(this);
        btnErase.addActionListener(this);
        btnOther.addActionListener(this);
        mnuItmNew.addActionListener(this);
        mnuItmOpen.addActionListener(this);
        mnuItmSave.addActionListener(this);
        mnuItmExit.addActionListener(this);
        mnuItmBlack.addActionListener(this);
        mnuItmRed.addActionListener(this);
        mnuItmGreen.addActionListener(this);
        mnuItmBlue.addActionListener(this);
        mnuItmErase.addActionListener(this);
        mnuItmOther.addActionListener(this);
        mnuItmFree.addActionListener(this);
        mnuItmLine.addActionListener(this);
        mnuItmRect.addActionListener(this);
        mnuItmCirc.addActionListener(this);
        mnuItmHelp.addActionListener(this);
        mnuItmAbout.addActionListener(this);
        chkFilled.addActionListener(this);
        mnuItmFilled.addActionListener(this);
        sbRed.addAdjustmentListener(this);
        sbGreen.addAdjustmentListener(this);
        sbBlue.addAdjustmentListener(this);
        txtRed.addActionListener(this);
        txtGreen.addActionListener(this);
        txtBlue.addActionListener(this);
    }
    private void setColor(Color c){
        col=c;
        selPnl.setBackground(c);
        int r=c.getRed();
        int g=c.getGreen();
        int b=c.getBlue();
        sbRed.setValue(r);
        sbGreen.setValue(g);
        sbBlue.setValue(b);
        txtRed.setText(r+"");
        txtGreen.setText(g+"");
        txtBlue.setText(b+"");
    }
    @Override
    public void actionPerformed(ActionEvent e){
        Object o=e.getSource();
        if(o==mnuItmExit){
            int choice=JOptionPane.showConfirmDialog(this,"Are you sure?","Select an option",0);
            if(choice==0)
                System.exit(0);
        }
        else if(o==mnuItmNew){
            paintPnl.Shapes.clear();
            paintPnl.setImage(null);
            paintPnl.repaint();
        }
        else if(o==mnuItmSave){
            JFileChooser chooser=new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            String name=JOptionPane.showInputDialog(this,"Choose image name","Image Name",3);
            int choice=chooser.showSaveDialog(this);
            if(choice==0){
                name=chooser.getSelectedFile().toString()+"\\"+name+".png";
                File file=new File(name);
                BufferedImage img=new BufferedImage(paintPnl.getWidth(),paintPnl.getHeight(),2);
                Graphics g=img.createGraphics();
                paintPnl.paint(g);
                try{ImageIO.write(img,"png",file);}
                catch(Exception exception){}
            }
        }
        else if(o==mnuItmOpen){
            JFileChooser chooser=new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PNG Images","png"));
            chooser.setAcceptAllFileFilterUsed(false);
            int choice=chooser.showOpenDialog(this);
            if(choice==0){
                File file=chooser.getSelectedFile();
                BufferedImage img=null;
                try{
                    img=ImageIO.read(file);
                    paintPnl.setImage(img);
                    paintPnl.Shapes.clear();
                    paintPnl.repaint();
                }
                catch(Exception exception){}
            }
        }
        else if(o==mnuItmHelp){
            String help="Images can be saved as \"PNG\" Files.\nNote that \"Open\" automatically creates\nnew painting area, so any editing"
                    +" should\nbe done after opening the selected image.";
            JOptionPane.showConfirmDialog(this,help,"Help",-1,1);
        }
        else if(o==mnuItmAbout){
            String about="Developers:\nOmar Lashin, Amr Hammad\nAyman Yasser, Ahmed Shalaby\nAhmed Mohamed";
            JOptionPane.showConfirmDialog(this,about,"About",-1,1);
        }
        else if(o==mnuItmBlack||o==btnBlack)
            setColor(Color.BLACK);
        else if(o==mnuItmRed||o==btnRed)
            setColor(Color.RED);
        else if(o==mnuItmGreen||o==btnGreen)
            setColor(Color.GREEN);
        else if(o==mnuItmBlue||o==btnBlue)
            setColor(Color.BLUE);
        else if(o==mnuItmErase||o==btnErase)
            setColor(Color.WHITE);
        else if(o==mnuItmOther||o==btnOther){
            Color c=JColorChooser.showDialog(this,"Select Color",col);
            if(c!=null)
                setColor(c);
        }
        else if(o==mnuItmFree||o==btnFree)
            sty=DrawingStyle.FREE;
        else if(o==mnuItmLine||o==btnLine)
            sty=DrawingStyle.LINE;
        else if(o==mnuItmRect||o==btnRect)
            sty=DrawingStyle.RECT;
        else if(o==mnuItmCirc||o==btnCirc)
            sty=DrawingStyle.CIRC;
        else if(o==chkFilled)
            mnuItmFilled.setSelected(chkFilled.isSelected());
        else if(o==mnuItmFilled)
            chkFilled.setSelected(mnuItmFilled.isSelected());
        else if(o==txtRed||o==txtGreen||o==txtBlue){
            int r=Integer.parseInt(txtRed.getText());
            int g=Integer.parseInt(txtGreen.getText());
            int b=Integer.parseInt(txtBlue.getText());
            setColor(new Color(r,g,b));
        }
    }
    @Override
    public void adjustmentValueChanged(AdjustmentEvent e){
        int r=sbRed.getValue();
        int g=sbGreen.getValue();
        int b=sbBlue.getValue();
        setColor(new Color(r,g,b));
    }
}