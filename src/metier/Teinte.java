package metier;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Classe pour gerer les teintes de couleurs
 */
public class Teinte
{

	ImageUtil imgUtil;
	String fichierSource;
	String fichierDest;

	/**
	 * Constructeur
	 * 
	 * @param fichierSource Chemin de l'image source
	 * @param fichierDest Chemin du fichier destination
	 */
	public Teinte(String fichierSource, String fichierDest)
	{
		this.fichierSource = fichierSource;
		this.fichierDest = fichierDest;
		this.imgUtil = new ImageUtil(fichierSource);
	}

	/**
	 * Appliquer une teinte a l'image
	 * 
	 * @param teinteRouge Ajustement de la composante rouge (-255 a +255)
	 * @param teinteVerte Ajustement de la composante verte (-255 a +255)
	 * @param teinteBleue Ajustement de la composante bleue (-255 a +255)
	 */
	public void teinter(int teinteRouge, int teinteVerte, int teinteBleue)
	{
		BufferedImage img = this.imgUtil.getImage();

		int hauteur = img.getHeight();
		int largeur = img.getWidth();

		for (int x = 0; x < largeur; x++)
		{
			for (int y = 0; y < hauteur; y++)
			{
				int pixel = img.getRGB(x, y);
				List<Integer> rgb = imgUtil.afficherDetailCouleur(pixel);
				
				// Appliquer les teintes et borner entre 0 et 255
				int nouveauRouge = Math.max(0, Math.min(255, rgb.get(0) + teinteRouge));
				int nouveauVert  = Math.max(0, Math.min(255, rgb.get(1) + teinteVerte));
				int nouveauBleu  = Math.max(0, Math.min(255, rgb.get(2) + teinteBleue));

				Color nouvelleCouleur = new Color(nouveauRouge, nouveauVert, nouveauBleu);
				img.setRGB(x, y, nouvelleCouleur.getRGB()); 
			}
		}
		
		// Mise a jour de l'image et sauvegarde
		this.imgUtil.setImage(img);
		this.imgUtil.sauvegarderImage(this.fichierDest);
	}
}
