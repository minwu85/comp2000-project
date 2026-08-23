import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Timer;

public class Panel extends Frame{

    int width = 1280; //screen width
    int height = 720;  //screen height

    int majorStation = 40; //Big station 
    int normalStation= 20;//Normal station

    // Train + passenger example, driven by a clock started with space.
    Routes route = Routes.line1();
    int stationIndex = 0;

    int trainWidth = 30;
    int trainHeight = 16;

    int clockTicks = 0; // 1 real second = 5 ticks
    Timer clock;

    Image dbImage;
    Graphics dbGraphics;


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

        setFocusable(true);
        requestFocusInWindow();

        // Ticks every 200ms (5 ticks/sec); every 5th tick moves the train.
        clock = new Timer(200, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clockTicks = clockTicks + 1;
                if (clockTicks % 5 == 0) {
                    moveTrainToNextStation();
                }
                repaint();
            }
        });

        // One space press starts the clock; the train then moves on its own.
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !clock.isRunning()) {
                    clock.start();
                }
            }
        });
    }

    // Steps forward one stop, looping back to the start after the last stop.
    public void moveTrainToNextStation() {
        int lastIndex = route.stations.size() - 1;
        if (stationIndex < lastIndex) {
            stationIndex = stationIndex + 1;
        } else if (stationIndex == lastIndex) {
            stationIndex = 0;
        }
    }

    // Draws to an off-screen image first, then blits it in one go, so the
    // clock/train redraw doesn't flash the whole panel.
    public void update(Graphics g) {
        if (dbImage == null) {
            dbImage = createImage(width, height);
            dbGraphics = dbImage.getGraphics();
        }
        dbGraphics.setColor(getBackground());
        dbGraphics.fillRect(0, 0, width, height);
        paint(dbGraphics);
        g.drawImage(dbImage, 0, 0, this);
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

        //Vehicles
        g.setColor(Color.black);
        g.drawString("Time: " + clockTicks, 20, 50);
        drawTrainAndPassenger(g);

    }

    // Train sits on the route at the current station; passenger is drawn inside it.
    public void drawTrainAndPassenger(Graphics g) {
        Stops currentStop = route.stations.get(stationIndex);

        int trainX = currentStop.x - trainWidth / 2;
        int trainY = currentStop.y - trainHeight / 2;

        g.setColor(Color.white);
        g.fillRect(trainX, trainY, trainWidth, trainHeight);
        g.setColor(Color.black);
        g.drawRect(trainX, trainY, trainWidth, trainHeight);

        int passengerSize = 8;
        int passengerX = trainX + (trainWidth - passengerSize) / 2;
        int passengerY = trainY + (trainHeight - passengerSize) / 2;

        g.setColor(Color.orange);
        g.fillOval(passengerX, passengerY, passengerSize, passengerSize);
    }

    public static void main(String[] args) {
        new Panel();
    }
}