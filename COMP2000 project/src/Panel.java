import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Panel extends Frame{

    int width = 1280; //screen width
    int height = 720;  //screen height

    int majorStation = 40; //Big station 
    int normalStation= 20;//Normal station


    public Panel() {
        setTitle("Transit Sim");
        setSize(width, height);
        setVisible(true);

        // Window closing adapter to safely close the application
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    //@Override
    public void paint(Graphics g) {
        // Set Stroke Thickness
        Graphics2D g2d=(Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));
        //Draw Train Lines 

        //train line 1 (blue)
        g.setColor(new Color(100, 150, 200));
        g.drawLine(width/2 - 300, height/2 + 300, width/2 - 500, height/2 + 300);
        g.drawLine(width/2 - 300, height/2 + 300, width/2 - 400, height/2 + 300);
        g.drawLine(width/2 - 200, height/2 + 200, width/2 - 300, height/2 + 300);
        g.drawLine(width/2 - 200, height/2, width/2 - 200, height/2 + 200);
        g.drawLine(width/2 - 200, height/2, width/2 - 200, height/2 + 100);
        g.drawLine(width/2, height/2, width/2 - 200, height/2);
        g.drawLine(width/2, height/2, width/2 - 100, height/2);
        g.drawLine(width/2, height/2, width/2 + 100, height/2);
        g.drawLine(width/2, height/2, width/2 + 200, height/2);
        g.drawLine(width/2, height/2, width/2 + 300, height/2);
        g.drawLine(width/2 + 300, height/2, width/2 + 400, height/2 - 100);
        g.drawLine(width/2 + 400, height/2 - 100, width/2 + 400, height/2 - 200);
        g.drawLine(width/2 + 400, height/2 - 100, width/2 + 400, height/2 - 300);

        //train line 2 (red)
        g.setColor(Color.red);
        g.drawLine(width/2 + 2, height/2, width/2 + 2, height/2 - 300);
        g.drawLine(width/2 + 2, height/2, width/2 + 2, height/2 - 200);
        g.drawLine(width/2 + 2, height/2, width/2 + 2, height/2 - 100);
        g.drawLine(width/2, height/2 + 5, width/2 + 100, height/2 + 5);
        g.drawLine(width/2, height/2 + 5, width/2 + 200, height/2 + 5);
        g.drawLine(width/2, height/2 + 5, width/2 + 300, height/2 + 5);
        g.drawLine(width/2 + 300, height/2, width/2 + 400, height/2 + 100);
        g.drawLine(width/2 + 400, height/2 + 100, width/2 + 400, height/2 + 200);

        //train line 3 (green)
        g.setColor(Color.green);
        g.drawLine(width/2 - 300, height/2 - 100, width/2 - 400, height/2 - 200);
        g.drawLine(width/2 - 200, height/2, width/2 - 300, height/2 - 100);
        g.drawLine(width/2, height/2 - 5, width/2 - 200, height/2 - 5);
        g.drawLine(width/2, height/2 - 5, width/2 - 100, height/2 - 5);

        //train line 4 (purple)
        g.setColor(new Color(191, 0, 255));
        g.drawLine(width/2 - 2, height/2, width/2 - 2, height/2 - 300);
        g.drawLine(width/2 - 2, height/2, width/2 - 2, height/2 - 200);
        g.drawLine(width/2 - 2, height/2, width/2 - 2, height/2 - 100);
        g.drawLine(width/2, height/2 + 5, width/2 - 100, height/2 + 5);
        g.drawLine(width/2, height/2 + 5, width/2 - 200, height/2 + 5);
        g.drawLine(width/2 - 195, height/2 + 5, width/2 - 195, height/2 + 100);
        g.drawLine(width/2 - 195, height/2 + 100, width/2 - 195, height/2 + 200);


        
        g2d.setStroke(new BasicStroke(5)); //Stroke Thickness 
        g.setColor(Color.black);

        //Draw Stations 
        //Central Station
        g.drawString("Central", width/2 -majorStation/2, height/2 -majorStation/2 + 60);
        g.setColor(Color.gray);
        g.fillOval(width/2 -majorStation/2, height/2 -majorStation/2, majorStation, majorStation);  
        g.setColor(Color.black);
        g.drawOval(width/2 -majorStation/2, height/2 -majorStation/2, majorStation, majorStation);
        //line 1
        //station 1
        //provide a name for the station east1
        g.drawString("East1", width/2 -normalStation/2 + 100 - 10, height/2 -normalStation/2 - 10);
        g.setColor(Color.gray);
        g.fillOval(width/2 -normalStation/2 + 100, height/2 -normalStation/2, normalStation, normalStation);  
        g.setColor(Color.black);
        g.drawOval(width/2 -normalStation/2 + 100, height/2 -normalStation/2, normalStation, normalStation);
        //station 2
        g.setColor(Color.gray);
        g.fillOval(width/2 -normalStation/2 + 200, height/2 -normalStation/2, normalStation, normalStation);  
        g.setColor(Color.black);
        g.drawOval(width/2 -normalStation/2 + 200, height/2 -normalStation/2, normalStation, normalStation);
        //station 3
        g.drawString("Percy Port", width/2 -normalStation/2 + 335, height/2 -normalStation/2 + 15);
        g.setColor(Color.gray);
        g.fillOval(width/2 -normalStation/2 + 300, height/2 -normalStation/2, normalStation, normalStation);  
        g.setColor(Color.black);
        g.drawOval(width/2 -normalStation/2 + 300, height/2 -normalStation/2, normalStation, normalStation);
        //station 4
        g.setColor(Color.gray);
        g.fillOval(width/2 -normalStation/2 + 400, height/2 -normalStation/2 - 100, normalStation, normalStation);  
        g.setColor(Color.black);
        g.drawOval(width/2 -normalStation/2 + 400, height/2 -normalStation/2 - 100, normalStation, normalStation);
        //station 5
        g.setColor(Color.gray);
        g.fillOval(width/2 -normalStation/2 + 400, height/2 -normalStation/2 - 200, normalStation, normalStation);  
        g.setColor(Color.black);
        g.drawOval(width/2 -normalStation/2 + 400, height/2 -normalStation/2 - 200, normalStation, normalStation);   
        //station 6
        g.setColor(Color.gray);
        g.fillOval(width/2 -normalStation/2 + 400, height/2 -normalStation/2 - 300, normalStation, normalStation);  
        g.setColor(Color.black);
        g.drawOval(width/2 -normalStation/2 + 400, height/2 -normalStation/2 - 300, normalStation, normalStation);             
        
       
        //line 2  
        //station 2.1
        g.setColor(Color.gray);
        g.fillOval(width/2 -normalStation/2 + 400, height/2 -normalStation/2 + 100, normalStation, normalStation);  
        g.setColor(Color.black);
        g.drawOval(width/2 -normalStation/2 + 400, height/2 -normalStation/2 + 100, normalStation, normalStation);
        //station 2.2
        g.setColor(Color.gray);
        g.fillOval(width/2 -normalStation/2 + 400, height/2 -normalStation/2 + 200, normalStation, normalStation);  
        g.setColor(Color.black);
        g.drawOval(width/2 -normalStation/2 + 400, height/2 -normalStation/2 + 200, normalStation, normalStation); 
        //Station 2.3 
        g.setColor(Color.gray);
        g.fillOval(width/2 -normalStation/2 - 100, height/2 -normalStation/2, normalStation, normalStation);  
        g.setColor(Color.black);
        g.drawOval(width/2 -normalStation/2 - 100, height/2 -normalStation/2, normalStation, normalStation); 
        //Station 2.4
        g.setColor(Color.gray);
        g.fillOval(width/2 -normalStation/2 - 200, height/2 -normalStation/2, normalStation, normalStation);  
        g.setColor(Color.black);
        g.drawOval(width/2 -normalStation/2 - 200, height/2 -normalStation/2, normalStation, normalStation); 
        //Station 2.5
        g.setColor(Color.gray);
        g.fillOval(width/2 -normalStation/2 -300, height/2 -normalStation/2 - 100, normalStation, normalStation);  
        g.setColor(Color.black);
        g.drawOval(width/2 -normalStation/2 -300, height/2 -normalStation/2 - 100, normalStation, normalStation); 
        //Station 2.6
        g.setColor(Color.gray);
        g.fillOval(width/2 -normalStation/2 -400, height/2 -normalStation/2 - 200, normalStation, normalStation);  
        g.setColor(Color.black);
        g.drawOval(width/2 -normalStation/2 -400, height/2 -normalStation/2 - 200, normalStation, normalStation); 
        
        //Station3.1
        g.setColor(Color.gray);
        g.fillOval(width/2 -normalStation/2 , height/2 -normalStation/2 - 100, normalStation, normalStation);  
        g.setColor(Color.black);
        g.drawOval(width/2 -normalStation/2 , height/2 -normalStation/2 - 100, normalStation, normalStation); 
        //Station 3.2
        g.setColor(Color.gray);
        g.fillOval(width/2 -normalStation/2 , height/2 -normalStation/2 - 200, normalStation, normalStation);  
        g.setColor(Color.black);
        g.drawOval(width/2 -normalStation/2 , height/2 -normalStation/2 - 200, normalStation, normalStation); 
        //Station 3.3
        g.setColor(Color.gray);
        g.fillOval(width/2 -majorStation/2 , height/2 -majorStation/2 - 300, majorStation, majorStation);  
        g.setColor(Color.black);
        g.drawOval(width/2 -majorStation/2 , height/2 -majorStation/2 - 300, majorStation, majorStation); 
        
        //Station 4.1
        g.drawString("Tiger Bay", width/2 -normalStation/2 - 200 + 30, height/2 -normalStation/2 + 100 + 15);
        g.setColor(Color.gray);
        g.fillOval(width/2 -normalStation/2 -200, height/2 -normalStation/2 +100, normalStation, normalStation);  
        g.setColor(Color.black);
        g.drawOval(width/2 -normalStation/2 -200, height/2 -normalStation/2 +100, normalStation, normalStation); 
        //Station 4.2
        g.drawString("Troll-upon-Bridge", width/2 -majorStation/2 - 200 + 50, height/2 -majorStation/2 + 200 + 25);
        g.setColor(Color.gray);
        g.fillOval(width/2 -majorStation/2 -200, height/2 -majorStation/2 +200, majorStation, majorStation);  
        g.setColor(Color.black);
        g.drawOval(width/2 -majorStation/2 -200, height/2 -majorStation/2 +200, majorStation, majorStation); 
        //Station 4.2
        g.setColor(Color.gray);
        g.fillOval(width/2 -normalStation/2 -300, height/2 -normalStation/2 +300, normalStation, normalStation);  
        g.setColor(Color.black);
        g.drawOval(width/2 -normalStation/2 -300, height/2 -normalStation/2 +300, normalStation, normalStation); 
        
        
        //Station 5.1
        g.setColor(Color.gray);
        g.fillOval(width/2 -normalStation/2 -500, height/2 -normalStation/2 +300, normalStation, normalStation);  
        g.setColor(Color.black);
        g.drawOval(width/2 -normalStation/2 -500, height/2 -normalStation/2 +300, normalStation, normalStation);         
        //Station 5.2
        g.setColor(Color.gray);
        g.fillOval(width/2 -normalStation/2 -400, height/2 -normalStation/2 +300, normalStation, normalStation);  
        g.setColor(Color.black);
        g.drawOval(width/2 -normalStation/2 -400, height/2 -normalStation/2 +300, normalStation, normalStation); 
        
        //count up to 16




    }

    public static void main(String[] args) {
        new Panel();
    }
}

