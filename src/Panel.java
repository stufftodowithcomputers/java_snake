import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 886405317485320997L;

	static final int SCREEN_WIDTH = 750;
	static final int SCREEN_HEIGHT = 750;
	static final int SIZE = 25;
	static final int UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/SIZE;
	static final int DELAY = 75;
	final int x[] = new int[UNITS];
	final int y[] = new int[UNITS];
	
	char direction = 'D';
	int bodyParts = 6;
	int appleEatens;
	int appleX;
	int appleY;
	boolean running = false;
	Timer timer;
	Random random;
	
	Panel() {
		random = new Random();
		setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setBackground(Color.black);
		setFocusable(true);
		addKeyListener(new MyKeyAdapter());
		
		start();
	}
	
	public void start() {
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
		
	}
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		draw(graphics);
		
	}
	public void draw(Graphics graphics) {
		if(running) {
//			graphics.setColor(Color.pink);
//			for (int i = 0; i < SCREEN_HEIGHT / SIZE; i++) {
//				graphics.drawLine(i*SIZE, 0, i*SIZE, SCREEN_HEIGHT);
//				graphics.drawLine(0, i*SIZE, SCREEN_WIDTH, i*SIZE);			
//			}	
			
			graphics.setColor(Color.DARK_GRAY);
			graphics.setFont(new Font("Sans Serif", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(graphics.getFont());
			graphics.drawString("Score: "+appleEatens, (SCREEN_WIDTH - metrics.stringWidth("Score: "+appleEatens)) / 2, SCREEN_HEIGHT / 5);
			
			
			graphics.setColor(Color.green);
			graphics.fillOval(appleX, appleY, SIZE, SIZE);	
			
			for(int i=0; i<bodyParts; i++) {
				if(i == 0) {
					graphics.setColor(Color.red);
					graphics.fillRect(x[i], y[i], SIZE, SIZE);
				} else {
					graphics.setColor(Color.cyan);
					graphics.fillRect(x[i], y[i], SIZE, SIZE);				
				}
			}			
		} else {
			gameOver(graphics);
		}

	}
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH / SIZE))*SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT / SIZE))*SIZE;
	}
	public void move() {
		for(int i = bodyParts; i > 0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch (direction) {
		case 'W': {
			y[0] = y[0] - SIZE;
			return;
		}
		case 'S': {
			y[0] = y[0] + SIZE;
			return;
		}
		case 'A': {
			x[0] = x[0] - SIZE;
			return;
		}
		case 'D': {
			x[0] = x[0] + SIZE;
			return;
		}
		default:
			return;
		}
	}
	public void checkApple() {
		if(x[0] == appleX && y[0] == appleY) {
			bodyParts++;
			appleEatens++;
			newApple();
		}
	}
	public void checkCollisions() {
		// Collision with body
		for(int i = bodyParts; i>0; i--) {
			if(x[0] == x[i] && y[0] == y[i]) {
				running = false;
			}
		}
		
		// Collision with border
		if(x[0] < 0 || x[0] > SCREEN_WIDTH) {
			running = false;
		}
		if(y[0] < 0 || y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
		
	}
	public void gameOver(Graphics graphics) {
		graphics.setColor(Color.red);
		graphics.setFont(new Font("Sans Serif", Font.BOLD, 75));
		FontMetrics title = getFontMetrics(graphics.getFont());
		graphics.drawString("Game Over", (SCREEN_WIDTH - title.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 5);
		
		graphics.setColor(Color.green);
		graphics.setFont(new Font("Sans Serif", Font.BOLD, 45));
		FontMetrics text = getFontMetrics(graphics.getFont());
		graphics.drawString("Press space to restart", (SCREEN_WIDTH - text.stringWidth("Press space to restart")) / 2, SCREEN_HEIGHT / 5 * 3);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
					
	}
	
	public class MyKeyAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			char input;
			
			if(running) {
				input = Character.toUpperCase(e.getKeyChar());
				switch(input) {
				case 'W':
					if(direction == 'S') return;
					direction = input;
					return;
				case 'S':
					if(direction == 'W') return;
					direction = input;
					return;
				case 'A':
					if(direction == 'D') return;
					direction = input;
					return;
				case 'D':
					if(direction == 'A') return;
					direction = input;
					return;
				default:
					return;
				}				
			} 
		}
	}
}
