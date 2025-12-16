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
		
		// Dessiner l'image de fond
		if (this.bufferedImage != null)
		{
			g.drawImage(this.bufferedImage, this.frame.getPosX(), this.frame.getPosY(), this.bufferedImage.getWidth(), this.bufferedImage.getHeight(), this);
		}
		
		// Dessiner le calque texte par dessus
		BufferedImage calqueTexte = this.frame.getCalqueTexte();
		if (calqueTexte != null)
		{
			g.drawImage(calqueTexte, this.frame.getCalqueTexteX(), this.frame.getCalqueTexteY(), calqueTexte.getWidth(), calqueTexte.getHeight(), this);
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
		private boolean draggingTexte = false;

		@Override
		public void mouseClicked(MouseEvent e)
		{
			System.out.println("Clic en position: (" + e.getX() + ", " + e.getY() + ")");
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			lastX = e.getX();
			lastY = e.getY();
			
			// Priorité au texte si on clique dessus
			if (PanelPrincipal.this.frame.contientCalqueTexte(e.getX(), e.getY()))
			{
				draggingTexte = true;
				dragging = false;
			}
			else
			{
				dragging = true;
				draggingTexte = false;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			// Si on relâche le texte sur l'image de fond, fusionner
			if (draggingTexte && PanelPrincipal.this.frame.contient(e.getX(), e.getY()))
			{
				PanelPrincipal.this.frame.fusionnerCalqueTexte();
			}
			
			dragging = false;
			draggingTexte = false;
		}

		@Override
		public void mouseDragged(MouseEvent e)
		{
			int deltaX = e.getX() - lastX;
			int deltaY = e.getY() - lastY;
			
			if (draggingTexte)
			{
				// Déplacer le texte
				PanelPrincipal.this.frame.setCalqueTexteX(PanelPrincipal.this.frame.getCalqueTexteX() + deltaX);
				PanelPrincipal.this.frame.setCalqueTexteY(PanelPrincipal.this.frame.getCalqueTexteY() + deltaY);
			}
			else if (dragging)
			{
				// Déplacer l'image de fond
				PanelPrincipal.this.frame.setPosX(PanelPrincipal.this.frame.getPosX() + deltaX);
				PanelPrincipal.this.frame.setPosY(PanelPrincipal.this.frame.getPosY() + deltaY);
			}
			
			lastX = e.getX();
			lastY = e.getY();
			
			PanelPrincipal.this.majIHM();
		}
	}
}