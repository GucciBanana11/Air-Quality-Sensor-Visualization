import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import java.util.List;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Visualization extends JFrame {
    private BufferedImage canvas, overlay;
    private JPanel canvasPanel;
    private JSlider time;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Visualization(List<LocalDateTime> extremityDates, double timeScale) {
        setTitle("Air Quality Sensor Visualization");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        canvas = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB); // Blank canvas, background
        overlay = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB); // Overlay
        Graphics2D g2d = canvas.createGraphics();
        g2d.setPaint(Color.BLACK);
        g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g2d.setColor(Color.WHITE);
        g2d.drawRect(75, 75, 650, 450);

        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        g2d.drawString("40.85°", 15, 110); // Left North
        g2d.drawString("40.12°", 15, 240); // Left NNS
        g2d.drawString("39.83°", 15, 370); // Left NSS
        g2d.drawString("38.65°", 15, 500); // Left South
        g2d.drawString("40.85°", 740, 110); // Right North
        g2d.drawString("40.12°", 740, 240); // Right NNS
        g2d.drawString("39.83°", 740, 370); // Right NSS
        g2d.drawString("38.65°", 740, 500); // Right South
        g2d.drawString("-106.55°", 95, 50); // Up West
        g2d.drawString("-105.78°", 231, 50); // Up WWWE
        g2d.drawString("-105.00°", 368, 50); // Up WWEE
        g2d.drawString("-104.23°", 504, 50); // Up WEEE
        g2d.drawString("-103.45°", 640, 50); // Up East
        g2d.drawString("-106.55°", 95, 550); // Down West
        g2d.drawString("-105.78°", 231, 550); // Down WWWE
        g2d.drawString("-105.00°", 368, 550); // Down WWEE
        g2d.drawString("-104.23°", 504, 550); // Down WEEE
        g2d.drawString("-103.45°", 640, 550); // Down East

        g2d.drawString(extremityDates.get(0).format(formatter), 10, 590); // Start datetime
        g2d.drawString(extremityDates.get(1).format(formatter), 655, 590); // End datetime
        g2d.drawString("Date-time:", 280, 590);

        g2d.setColor(Color.gray); // Basic reference lines
        g2d.drawLine(75,110,725,110);
        g2d.drawLine(75,240,725,240);
        g2d.drawLine(75,370,725,370);
        g2d.drawLine(75,500,725,500);
        g2d.drawLine(120,75,120,525);
        g2d.drawLine(250,75,250,525);
        g2d.drawLine(390,75,390,525);
        g2d.drawLine(530,75,530,525);
        g2d.drawLine(670,75,670,525);
        g2d.dispose();

        canvasPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(canvas, 0, 0, null); // Background
                g.drawImage(overlay, 0, 0, null); // Overlay
            }
        };

        canvasPanel.setPreferredSize(new Dimension(800, 600));
        canvasPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                
            }
        });

        // Slider
        time = new JSlider(0, 100, 100); // Value to 100 so we can update it later
        time.setBorder(BorderFactory.createTitledBorder("Time"));
        time.addChangeListener(e -> {
            int currentTime = time.getValue();
    
            Graphics2D o = overlay.createGraphics(); // Clear overlay
            o.setComposite(AlphaComposite.Clear);
            o.fillRect(0, 0, overlay.getWidth(), overlay.getHeight());
            
            // o.setComposite(AlphaComposite.SrcOver);
            // o.setColor(new Color(255,currentTime,currentTime));
            // int centerX = 500;
            // int centerY = 300;
            // int outerRadius = 50;
            // int innerRadius = 20;

            // o.fillOval(centerX - outerRadius, centerY - outerRadius, outerRadius * 2, outerRadius * 2);

            // o.setComposite(AlphaComposite.Clear); // Erase pixels
            // o.fillOval(centerX - innerRadius, centerY - innerRadius, innerRadius * 2, innerRadius * 2);

            o.setComposite(AlphaComposite.SrcOver);
            o.setColor(Color.WHITE);
            o.drawString(extremityDates.get(0).plusSeconds(Long.valueOf(Math.round(currentTime * timeScale))).format(formatter), 350, 590); // Add current timescale seconds

            o.dispose();
            canvasPanel.repaint();
        });

        time.setValue(0); // Update time so it shows zeroed data when window is opened

        JPanel sliderPanel = new JPanel(new GridLayout(1, 1));
        sliderPanel.add(time, BorderLayout.CENTER);

        add(canvasPanel, BorderLayout.CENTER);
        add(sliderPanel, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }
}
