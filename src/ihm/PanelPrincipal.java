package ihm;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/** Panel principal affichant l'image et gérant les interactions utilisateur
 * @author Equipe 5
 */
public class PanelPrincipal extends JPanel
{
	private BufferedImage bufferedImage;
	private FramePrincipale frame;
	private double zoom = 1.0;
	private java.awt.Cursor curseurNormal;
	private java.awt.Cursor curseurPotPeinture;

	public PanelPrincipal(FramePrincipale frame)
	{
		this.frame = frame;
		this.curseurNormal = java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR);
		this.curseurPotPeinture = java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.CROSSHAIR_CURSOR);

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
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			int drawW = (int) Math.round(this.bufferedImage.getWidth() * zoom);
			int drawH = (int) Math.round(this.bufferedImage.getHeight() * zoom);
			g2d.drawImage(this.bufferedImage, this.frame.getPosX(), this.frame.getPosY(), drawW, drawH, this);
		}
		
		// Dessiner le calque de superposition par dessus
		BufferedImage calqueSuperposition = this.frame.getCalqueSuperposition();
		if (calqueSuperposition != null)
		{
			int drawW = (int) Math.round(calqueSuperposition.getWidth() * zoom);
			int drawH = (int) Math.round(calqueSuperposition.getHeight() * zoom);
			g.drawImage(calqueSuperposition, this.frame.getCalqueSuperpositionX(), this.frame.getCalqueSuperpositionY(), drawW, drawH, this);
		}
		
		// Dessiner le calque texte par dessus
		BufferedImage calqueTexte = this.frame.getCalqueTexte();
		if (calqueTexte != null)
		{
			int drawW = (int) Math.round(calqueTexte.getWidth() * zoom);
			int drawH = (int) Math.round(calqueTexte.getHeight() * zoom);
			g.drawImage(calqueTexte, this.frame.getCalqueTexteX(), this.frame.getCalqueTexteY(), drawW, drawH, this);
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

	public void activerCurseurPotPeinture()
	{
		this.setCursor(curseurPotPeinture);
	}

	public void desactiverCurseurPotPeinture()
	{
		this.setCursor(curseurNormal);
	}

	public double getZoom()
	{
		return this.zoom;
	}

	public void zoomAvant()
	{
		if (this.bufferedImage != null)
		{
			this.zoom = Math.min(this.zoom * 1.2, 20.0);
			this.majIHM();
		}
	}

	public void zoomArriere()
	{
		if (this.bufferedImage != null)
		{
			this.zoom = Math.max(this.zoom / 1.2, 0.05);
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
			String modePot = PanelPrincipal.this.frame.getModePotPeinture();
			
			// Clic droit : désactiver le mode pot de peinture
			if (e.getButton() == MouseEvent.BUTTON3 && modePot != null)
			{
				PanelPrincipal.this.frame.desactiverModePotPeinture();
				PanelPrincipal.this.setCursor(curseurNormal);
				return;
			}
			
			if (modePot != null && PanelPrincipal.this.frame.contientAvecZoom(e.getX(), e.getY()))
			{
				// Convertir les coordonnées de l'écran vers l'image
				int imgX = e.getX() - PanelPrincipal.this.frame.getPosX();
				int imgY = e.getY() - PanelPrincipal.this.frame.getPosY();
				
				// Ajuster pour le zoom
				imgX = (int)(imgX / zoom);
				imgY = (int)(imgY / zoom);
				
				if (modePot.equals("remplir"))
				{
					PanelPrincipal.this.frame.appliquerPotPeintureRemplir(imgX, imgY);
				}
				else if (modePot.equals("retirer"))
				{
					PanelPrincipal.this.frame.appliquerPotPeintureRetirer(imgX, imgY);
				}
			}
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
			if (draggingTexte && PanelPrincipal.this.frame.contientAvecZoom(e.getX(), e.getY()))
			{
				PanelPrincipal.this.frame.fusionnerCalqueTexte();
			}
			// Si on relache la superposition sur l'image de fond, fusionner
			else if (draggingSuperposition && PanelPrincipal.this.frame.contientAvecZoom(e.getX(), e.getY()))
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

		public void mouseEntered(MouseEvent e)
		{
			if (PanelPrincipal.this.frame.getModePotPeinture() == null)
			{
				PanelPrincipal.this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			}
		}

		public void mouseExited(MouseEvent e)
		{
			if (PanelPrincipal.this.frame.getModePotPeinture() == null)
			{
				PanelPrincipal.this.setCursor(curseurNormal);
			}
		}
	}
}