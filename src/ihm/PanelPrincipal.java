package ihm;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

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
		this.addMouseWheelListener(gestionSouris);
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		// Dessiner l'image de fond
		if (this.bufferedImage != null)
		{
			g.drawImage(this.bufferedImage, this.frame.getPosX(), this.frame.getPosY(), this.bufferedImage.getWidth(), this.bufferedImage.getHeight(), this);
		}
		
		// Dessiner le calque de superposition par dessus
		BufferedImage calqueSuperposition = this.frame.getCalqueSuperposition();
		if (calqueSuperposition != null)
		{
			g.drawImage(calqueSuperposition, this.frame.getCalqueSuperpositionX(), this.frame.getCalqueSuperpositionY(), calqueSuperposition.getWidth(), calqueSuperposition.getHeight(), this);
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

	public void zoomAvant()
	{
		if (this.bufferedImage != null)
		{
			int newWidth  = (int)(this.bufferedImage.getWidth()  * 1.2);
			int newHeight = (int)(this.bufferedImage.getHeight() * 1.2);
			BufferedImage resized = new BufferedImage(newWidth, newHeight, this.bufferedImage.getType());
			Graphics g = resized.getGraphics();
			g.drawImage(this.bufferedImage, 0, 0, newWidth, newHeight, null);
			g.dispose();
			this.bufferedImage = resized;
			this.majIHM();
		}
	}

	public void zoomArriere()
	{
		if (this.bufferedImage != null)
		{
			int newWidth  = (int)(this.bufferedImage.getWidth()  / 1.2);
			int newHeight = (int)(this.bufferedImage.getHeight() / 1.2);
			BufferedImage resized = new BufferedImage(newWidth, newHeight, this.bufferedImage.getType());
			Graphics g = resized.getGraphics();
			g.drawImage(this.bufferedImage, 0, 0, newWidth, newHeight, null);
			g.dispose();
			this.bufferedImage = resized;
			this.majIHM();
		}
	}

	private class GestionSouris extends MouseAdapter
	{
		private int lastX, lastY;
		private boolean dragging = false;
		private boolean draggingTexte = false;
		private boolean draggingSuperposition = false;

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
			
			// Priorite au texte (il est au-dessus)
			if (PanelPrincipal.this.frame.contientCalqueTexte(e.getX(), e.getY()))
			{
				draggingTexte = true;
				dragging = false;
				draggingSuperposition = false;
			}
			// Puis la superposition
			else if (PanelPrincipal.this.frame.contientCalqueSuperposition(e.getX(), e.getY()))
			{
				draggingSuperposition = true;
				dragging = false;
				draggingTexte = false;
			}
			// Sinon l'image de fond
			else
			{
				dragging = true;
				draggingTexte = false;
				draggingSuperposition = false;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			// Si on relache le texte sur l'image de fond, fusionner
			if (draggingTexte && PanelPrincipal.this.frame.contient(e.getX(), e.getY()))
			{
				PanelPrincipal.this.frame.fusionnerCalqueTexte();
			}
			// Si on relache la superposition sur l'image de fond, fusionner
			else if (draggingSuperposition && PanelPrincipal.this.frame.contient(e.getX(), e.getY()))
			{
				PanelPrincipal.this.frame.fusionnerCalqueSuperposition();
			}
			
			dragging = false;
			draggingTexte = false;
			draggingSuperposition = false;
		}

		@Override
		public void mouseDragged(MouseEvent e)
		{
			int deltaX = e.getX() - lastX;
			int deltaY = e.getY() - lastY;
			
			if (draggingTexte)
			{
				// Deplacer le texte
				PanelPrincipal.this.frame.setCalqueTexteX(PanelPrincipal.this.frame.getCalqueTexteX() + deltaX);
				PanelPrincipal.this.frame.setCalqueTexteY(PanelPrincipal.this.frame.getCalqueTexteY() + deltaY);
			}
			else if (draggingSuperposition)
			{
				// Deplacer la superposition
				PanelPrincipal.this.frame.setCalqueSuperpositionX(PanelPrincipal.this.frame.getCalqueSuperpositionX() + deltaX);
				PanelPrincipal.this.frame.setCalqueSuperpositionY(PanelPrincipal.this.frame.getCalqueSuperpositionY() + deltaY);
			}
			else if (dragging)
			{
				// Deplacer l'image de fond
				PanelPrincipal.this.frame.setPosX(PanelPrincipal.this.frame.getPosX() + deltaX);
				PanelPrincipal.this.frame.setPosY(PanelPrincipal.this.frame.getPosY() + deltaY);
			}
			
			lastX = e.getX();
			lastY = e.getY();
			
			PanelPrincipal.this.majIHM();
		}

		public void mouseWheelMoved(MouseWheelEvent e)
		{
			// Zoomer ou dezoomer en fonction de la direction de la molette
			if (e.getWheelRotation() < 0)
			{
				PanelPrincipal.this.zoomAvant();
			}
			else
			{
				PanelPrincipal.this.zoomArriere();
			}
		}
	}
}