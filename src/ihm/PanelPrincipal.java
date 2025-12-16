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
		
		// Charger l'image initiale
		this.chargerImage(this.frame.getImage());
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

	public void chargerImage(String path)
	{
		try
		{
			bufferedImage = ImageIO.read(new File(path));
			this.majIHM();
		}
		catch (IOException e)
		{
			System.err.println("Erreur: impossible de charger l'image: " + path);
			e.printStackTrace();
		}
	}
}