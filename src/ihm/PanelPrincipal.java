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

		GestionSouris gestionSouris = new GestionSouris();
		this.addMouseListener(gestionSouris);
		this.addMouseMotionListener(gestionSouris);
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if (this.bufferedImage != null)
		{
			g.drawImage(this.bufferedImage, this.frame.getPosX(), this.frame.getPosY(), this.bufferedImage.getWidth(), this.bufferedImage.getHeight(), this);
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
		private int lastX, lastY;
		private boolean dragging = false;

		@Override
		public void mouseClicked(MouseEvent e)
		{
			System.out.println("Clic en position: (" + e.getX() + ", " + e.getY() + ")");
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			// Toujours permettre de commencer le drag n'importe ou
			lastX = e.getX();
			lastY = e.getY();
			dragging = true;
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			dragging = false;
		}

		@Override
		public void mouseDragged(MouseEvent e)
		{
			if (dragging)
			{
				int deltaX = e.getX() - lastX;
				int deltaY = e.getY() - lastY;
				
				PanelPrincipal.this.frame.setPosX(PanelPrincipal.this.frame.getPosX() + deltaX);
				PanelPrincipal.this.frame.setPosY(PanelPrincipal.this.frame.getPosY() + deltaY);
				
				lastX = e.getX();
				lastY = e.getY();
				
				PanelPrincipal.this.majIHM();
			}
		}
	}
}