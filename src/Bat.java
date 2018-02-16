import java.awt.Color;
import java.awt.Graphics;

public class Bat
{

	public int batID;

	public int x, y, width = 20, height = 200;

	public int score;

	public Bat(Game pong, int batID)
	{
		this.batID = batID;

		if (batID == 1)  //tworzenie paletki po lewej
		{
			this.x = 0;
		}

		if (batID == 2) //po prawej
		{
			this.x = pong.width - width;
		}

		this.y = pong.height / 2 - this.height / 2;
	}

	public void render(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);
	}

	public void move(boolean up)
	{
		int s = 15; //predkosc ruchu paletka

		if (up)
		{
			if (y - s > 0)
			{
				y -= s;
			}
			else 			//przesuwanie aż do konca okna
			{
				y = 0;
			}
		}
		else
		{
			if (y + height + s < Game.pong.height)
			{
				y += s;
			}
			else
			{
				y = Game.pong.height - height;
			}
		}
	}

}
