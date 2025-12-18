package ihm;

import controleur.Controleur;
import ihm.BarMenu.BarMenu;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

/** Frame principale de l'application
 * @author Equipe 5
 */
public class FramePrincipale extends JFrame
{
	private PanelPrincipal panelPrincipal;
	private BarreOutil     barreOutil;
	private Controleur     controleur;

	public FramePrincipale(Controleur ctrl)
	{
		this.controleur = ctrl;
		
		this.panelPrincipal = new PanelPrincipal(this);
		this.barreOutil     = new BarreOutil    (ctrl);
		
		this.setTitle("Application Multimedia");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLocation(200, 200);

		this.setLayout(new BorderLayout());
		this.setJMenuBar(new BarMenu(this.controleur));

		this.add(this.barreOutil    , BorderLayout.NORTH );
		this.add(this.panelPrincipal, BorderLayout.CENTER);
		this.setVisible(true);
	}

	public boolean contient(int x, int y)
	{
		return this.controleur.contient(x, y);
	}

	public boolean contientAvecZoom(int x, int y)
	{
		double zoom = this.getZoom();
		int posX = this.getPosX();
		int posY = this.getPosY();
		BufferedImage img = this.getImage();
		
		if (img == null) return false;
		
		int largeurZoomee = (int)(img.getWidth() * zoom);
		int hauteurZoomee = (int)(img.getHeight() * zoom);
		
		return (x >= posX && x < posX + largeurZoomee && y >= posY && y < posY + hauteurZoomee);
	}

	public int getPosX()
	{
		return this.controleur.getPosX();
	}

	public int getPosY()
	{
		return this.controleur.getPosY();
	}

	public void setPosX(int x)
	{
		this.controleur.setPosX(x);
	}

	public void setPosY(int y)
	{
		this.controleur.setPosY(y);
	}

	public BufferedImage getImage()
	{
		return this.controleur.getImage();
	}

	public void afficherImage(String path)
	{
		this.panelPrincipal.chargerImage();
	}

	public void afficherImage(BufferedImage image)
	{
		this.panelPrincipal.setImage(image);
	}

	public double getZoom()
	{
		return this.panelPrincipal.getZoom();
	}

	public void zoomAvant()
	{
		this.panelPrincipal.zoomAvant();
	}

	public void zoomArriere()
	{
		this.panelPrincipal.zoomArriere();
	}

	public BufferedImage getCalqueTexte()
	{
		return this.controleur.getCalqueTexte();
	}

	public int getCalqueTexteX()
	{
		return this.controleur.getCalqueTexteX();
	}

	public int getCalqueTexteY()
	{
		return this.controleur.getCalqueTexteY();
	}

	public void setCalqueTexteX(int x)
	{
		this.controleur.setCalqueTexteX(x);
	}

	public void setCalqueTexteY(int y)
	{
		this.controleur.setCalqueTexteY(y);
	}

	public boolean contientCalqueTexte(int x, int y)
	{
		return this.controleur.contientCalqueTexte(x, y);
	}

	public void fusionnerCalqueTexte()
	{
		this.controleur.fusionnerCalqueTexte();
	}

	public BufferedImage getCalqueSuperposition()
	{
		return this.controleur.getCalqueSuperposition();
	}

	public int getCalqueSuperpositionX()
	{
		return this.controleur.getCalqueSuperpositionX();
	}

	public int getCalqueSuperpositionY()
	{
		return this.controleur.getCalqueSuperpositionY();
	}

	public void setCalqueSuperpositionX(int x)
	{
		this.controleur.setCalqueSuperpositionX(x);
	}

	public void setCalqueSuperpositionY(int y)
	{
		this.controleur.setCalqueSuperpositionY(y);
	}

	public boolean contientCalqueSuperposition(int x, int y)
	{
		return this.controleur.contientCalqueSuperposition(x, y);
	}

	public void fusionnerCalqueSuperposition()
	{
		this.controleur.fusionnerCalqueSuperposition();
	}

	public String getModePotPeinture()
	{
		return this.controleur.getModePotPeinture();
	}

	public void appliquerPotPeintureRemplir(int x, int y)
	{
		this.controleur.appliquerPotPeintureRemplir(x, y);
	}

	public void appliquerPotPeintureRetirer(int x, int y)
	{
		this.controleur.appliquerPotPeintureRetirer(x, y);
	}

	public void desactiverModePotPeinture()
	{
		this.controleur.desactiverModePotPeinture();
	}

	public void activerCurseurPotPeinture()
	{
		this.panelPrincipal.activerCurseurPotPeinture();
	}

	public void desactiverCurseurPotPeinture()
	{
		this.panelPrincipal.desactiverCurseurPotPeinture();
	}

	public void majIHM()
	{
		this.panelPrincipal.majIHM();
	}

	/* Ponts pour le mode Découpage */
	public boolean isModeDecoupageActif()
	{
		return this.controleur.isModeDecoupageActif();
	}

	public void enregistrerPointDecoupage(int imgX, int imgY)
	{
		this.controleur.enregistrerPointDecoupage(imgX, imgY);
	}

	public void activerModeDecoupage()
	{
		this.controleur.activerModeDecoupage();
	}

	public void desactiverModeDecoupage()
	{
		this.controleur.desactiverModeDecoupage();
	}

	public String choisirImageInitiale()
	{
		JFileChooser selecteurFichier;
		int          resultat;
		String       cheminImage;

		selecteurFichier = new JFileChooser();
		selecteurFichier.setAcceptAllFileFilterUsed(false);
		selecteurFichier.setDialogTitle("Sélectionner une image à ouvrir");
		
		selecteurFichier.setFileFilter(new FileNameExtensionFilter(
			"Images (png)", "png"
		));

		resultat = selecteurFichier.showOpenDialog(this);

		if (resultat == JFileChooser.APPROVE_OPTION)
		{
			cheminImage = selecteurFichier.getSelectedFile().getAbsolutePath();
			return cheminImage;
		}
		else if (resultat == JFileChooser.CANCEL_OPTION)
		{
			System.exit(0);
		}
		else if (resultat == JFileChooser.ERROR_OPTION)
		{
			JOptionPane.showMessageDialog(
				this,
				"Une erreur est survenue lors de la sélection de l'image.",
				"Erreur",
				JOptionPane.ERROR_MESSAGE
			);
		}
		
		return null;
	}
}