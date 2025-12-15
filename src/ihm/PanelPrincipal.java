package ihm;

import javax.swing.*;
import javax.imageio.ImageIO;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PanelPrincipal extends JPanel
{
	private BufferedImage bufferedImage;
	private FramePrincipale frame;

	public PanelPrincipal(FramePrincipale frame)
	{
		this.frame = frame;

		this.setLayout(new BorderLayout());
		
		// Charger l'image
		try
		{
			bufferedImage = ImageIO.read(new File(this.frame.getImage()));
		}
		catch (IOException e)
		{
			System.err.println("Erreur: impossible de charger l'image");
			e.printStackTrace();
		}
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if (bufferedImage != null)
		{
			g.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), this);
		}
	}

	public void majIHM()
	{
		this.repaint();
	}
}