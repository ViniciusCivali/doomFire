
/*
* By D-Kin
* Futura game engine basica
*
*/
import java.awt.Canvas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {

    private static final long serialVersionUID = 1L;
    public static JFrame frame;
    private Thread thread;
    private boolean isRunning = true;
    public static final int WIDTH = 300;
    public static final int HEIGHT = 320;
    private final int SCALE = 3;

    private BufferedImage image;
    
    //Instanciando classe fogo
    Fogo fogo = new Fogo();

    public Game() {

        addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        initFrame();
        //Inicializando objetos.

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    }

    public void initFrame() {
        frame = new JFrame("Game #1");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public synchronized void start() {
        thread = new Thread(this);
        @SuppressWarnings("unused")
        boolean isRunning = true;
        thread.start();

    }

    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        Game game = new Game();
        game.start();
    }

    public void tick() {
    	//
    	fogo.tick();
    }

    @SuppressWarnings("null")
    public void render() {
    	BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        /*Render do jogo*/

        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        
        g.setColor(Color.red);
        g.drawString("Renderização do jogo" , 600, 20);
     
        g.setColor(Color.white);
        g.setFont(new Font("consolas" , Font.BOLD, 20));
        //g.drawString("Teste render tela" , 20, 580);
        //FIM DO RENDER
        
        //Utilizando método render da classe fogo
        fogo.renderFogo(g);
        
        
        bs.show();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 30.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();
        requestFocus();
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                tick();
                render();
                frames++;
                delta--;
            }

            if (System.currentTimeMillis() - timer >= 100) {
                //System.out.println("FPS: " + frames);
                frames = 0;
                timer += 1000;
            }
        }

        stop();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
