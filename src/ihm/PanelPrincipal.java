package ihm;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelPrincipal extends JPanel
{
	private BufferedImage bufferedImage;
	private FramePrincipale frame;

	public PanelPrincipal(FramePrincipale frame)
	{
		this.frame = frame;

		this.setLayout(new BorderLayout());
		
		// Charger l'image initiale
		this.chargerImage();

		this.addMouseListener(new GestionSouris());
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if (this.bufferedImage != null)
		{
			g.drawImage(this.bufferedImage, 50, 50, this.bufferedImage.getWidth(), this.bufferedImage.getHeight(), this);
		}
	}

	public void majIHM()
	{
		this.repaint();
	}

	public void setImage(BufferedImage image)
	{
		this.bufferedImage = image;
		this.majIHM();
	}

	public void chargerImage()
	{
		this.bufferedImage = this.frame.getImage();
		this.majIHM();
	}

	private class GestionSouris extends MouseAdapter
	{
		@Override
		public void mouseClicked(MouseEvent e)
		{
			System.out.println("Clic en position: (" + e.getX() + ", " + e.getY() + ")");
		}

		@Override
		public void mouseDragged(MouseEvent e)
		{
			System.out.println("Souris déplacée en position: (" + e.getX() + ", " + e.getY() + ")");
		}
	}
}