import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Game implements ActionListener, KeyListener
{

	public static Game pong;
	public JFrame jframe;
	public int width = 1200, height = 700;
	public Bat p1;
	public Bat p2;
	public Ball ball;
	public Renderer renderer;
	public boolean w, s, up, down;
	public Random random;
	public int maxScore = 11;
	public boolean menu = true;         //4 możliwe stany gry: menu playing paused win
	public boolean playing = false;
	public boolean paused = false;
	public boolean win = false;
	public int winner; 

	public Game()
	{
		Timer timer = new Timer(15, this);
		random = new Random();

		jframe = new JFrame("Ping-Pong");

		renderer = new Renderer();

		jframe.setSize(width + 9 , height + 25);
		jframe.setVisible(true);	
		jframe.add(renderer);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.addKeyListener(this);

		timer.start();
	}

	public void start()  //rozpoczecie trybu playing
	{
		menu = false;
		playing = true;
		p1 = new Bat(this, 1);
		p2 = new Bat(this, 2);
		ball = new Ball(this);
	}

	public void refresh()
	{
		if (p1.score >= maxScore) //zwyciestwo p1
		{
			winner = 1;
			playing = false;
			win = true;
		}

		if (p2.score >= maxScore)
		{
			playing = false;
			win = true;
			winner = 2;
		}

		if (w)
		{
			p1.move(true);
		}
		if (s)
		{
			p1.move(false);
		}

		if (up)
		{
			p2.move(true);
		}
		if (down)
		{
			p2.move(false);
		}
		

		ball.refresh(p1, p2);
	}

	public void render(Graphics2D g)
	{
		g.setColor(new Color(0, 100, 0));
		g.fillRect(0, 0, width, height);

		if (menu)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Bauhaus 93", 1, 100));
			g.drawString("PING", width / 2 - 110, 100);
			g.drawString("PONG", width / 2 - 125, 200);
			g.setFont(new Font("Arial", 1, 30));
			g.drawString("Wcisnij ENTER aby zaczac", width / 2 - 175, height / 2 - 25);
			g.drawString("STEROWANIE:	 	Gracz 1: W, S      Gracz 2: UP, DOWN      Pauza: P     Menu: ESC", 40, height / 2 + 330);
			}
		

		if (paused)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 50));
			g.drawString("Wcisnij P aby kontynuowac", width / 2 - 280, height / 2 - 25);
		}

		if (playing || paused)
		{
			g.setColor(Color.WHITE);
			g.drawLine(width / 2, 0, width / 2, height);
			g.setFont(new Font("Bauhaus 93", 1, 50));
			g.drawString("Gracz 1:   " + String.valueOf(p1.score), width / 2 - 600, 50);
			g.drawString("Gracz 2:   " + String.valueOf(p2.score), width / 2 + 260, 50);

			p1.render(g);
			p2.render(g);
			ball.render(g);
		}

		if (win)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Bauhaus 93", 1, 50));
			g.drawString("PONG", width / 2 - 75, 50);
			g.drawString("Gracz" + winner + " wygrywa!", width / 2 - 190, 200);
			g.setFont(new Font("Arial", 1, 30));
			g.drawString("Wcisnij ENTER aby zagrac ponownie", width / 2 - 230, height / 2 - 25);
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		if (playing)
		{
			refresh();
		}

		renderer.repaint();
	}

	public static void main(String[] args)
	{
		pong = new Game();
	}

	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W)
		{
			w = true;
		}
		else if (key == KeyEvent.VK_S)
		{
			s = true;
		}
		else if (key == KeyEvent.VK_UP)
		{
			up = true;
		}
		else if (key == KeyEvent.VK_DOWN)
		{
			down = true;
		}
		else if (key == KeyEvent.VK_ESCAPE && (playing || win))
		{
		    playing = false;
		    win = false;
			menu = true;
		}
		else if (key == KeyEvent.VK_P){
			
			if (paused)
			{
				paused = false;
				playing = true;
			}
			else if (playing)
			{
			    playing = false;
				paused = true;
			}
		}
		else if (key == KeyEvent.VK_ENTER)
		{
			if (menu  || win)
			{
			    menu = false;
			    win = false;
				start();
			}
			
		}
	}
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W)
		{
			w = false;
		}
		else if (key == KeyEvent.VK_S)
		{
			s = false;
		}
		else if (key == KeyEvent.VK_UP)
		{
			up = false;
		}
		else if (key == KeyEvent.VK_DOWN)
		{
			down = false;
		}
	}
	public void keyTyped(KeyEvent e)
	{

	}
}
