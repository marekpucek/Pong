import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ball
{

	public int x, y, width = 25, height = 25;

	public int xMove, yMove;

	public Random random;

    public int speed = 10;

	private Game pong;

	public int colNumber;

	public Ball(Game pong)
	{
		this.pong = pong;

		this.random = new Random();

		spawn();
	}

	public void refresh(Bat bat1, Bat bat2)
	{


		this.x += xMove * speed;
		this.y += yMove * speed;

		if (this.y + height - yMove > pong.height || this.y + yMove < 0) //odbicie od ścian
		{
			if (this.yMove < 0)
			{
				this.y = 0;
				this.yMove = 1;  //zmiana kierunku

			}
			else
			{
				this.yMove = -1; //zmiana kierunku
				this.y = pong.height - height;
			}
		}

		if (ifCol(bat1) == 1)
		{
			this.xMove = 1;
			
			if(this.y + height > bat1.y  + 110)
			this.yMove = 1 + random.nextInt(1); //dolna czesc odbicie w dol
			if(this.y + height < bat1.y + 110)
			this.yMove = -1 - random.nextInt(1);
            speed ++; //zwiekszenie predkosci pilki
			colNumber++;
		}
		else if (ifCol(bat2) == 1) //odbicie od paletki
		{
			this.xMove = -1;
			if(this.y + height > bat2.y+100)
			this.yMove = 1 + random.nextInt(1);  // dolna czesc odbicie w dol
			if(this.y + height < bat2.y+100)
			this.yMove = -1 - random.nextInt(1);
			speed++;
			colNumber++;
		}

		if (ifCol(bat1) == 2)   //punkt
		{
			bat2.score++;
			speed = 10;
			spawn();
		}
		else if (ifCol(bat2) == 2)
		{
			bat1.score++;
			speed = 10;
			spawn();
		}
	}

	public void spawn()
	{
		this.colNumber = 0;
		this.x = pong.width / 2 - this.width / 2; // spawn na srodku
		this.y = pong.height / 2 - this.height / 2;

		this.yMove = -2 + random.nextInt(4); //randomowo gora dol

		if (yMove == 0)
		{
			yMove = 1;
		}

		if (random.nextBoolean()) // randomowo prawo lewo
		{
			xMove = 1;
		}
		else
		{
			xMove = -1;
		}
	}

	public int ifCol(Bat bat) //sprawdzanie kolizji
	{
		if (this.x < bat.x + bat.width && this.x + width > bat.x && this.y < bat.y + bat.height && this.y + height > bat.y)
		{
			return 1; 
		}
		else if ((bat.x > x && bat.batID == 1) || (bat.x < x - width && bat.batID == 2)) //uderzenie poza paletką
		{
			return 2; 
		}

		return 0;
	}

	public void render(Graphics g)
	{
		g.setColor(Color.ORANGE);  //rysowanie pilki
		g.fillOval(x, y, width, height);
	}

}
