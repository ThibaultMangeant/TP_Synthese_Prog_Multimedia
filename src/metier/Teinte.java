package metier;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Classe pour gérer les teintes de couleurs
 */
public class Teinte
{

	ImageUtil imgUtil;

	public Teinte(String srcImg)
	{
		this.imgUtil = new ImageUtil(srcImg);
	}

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
				int nouveauRouge = rgb.get(0) + teinteRouge;
				int nouveauBleu  = rgb.get(1) + teinteBleue;
				int nouveauVert  = rgb.get(2) + teinteVerte;

				Color nouvelleCouleur = new Color(nouveauRouge,nouveauBleu,nouveauVert);

				img.setRGB(x, y, nouvelleCouleur.getRGB()); 
			}
		}
	}
}
