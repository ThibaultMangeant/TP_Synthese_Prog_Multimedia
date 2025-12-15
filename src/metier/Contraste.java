package metier;

import java.awt.Color;
import java.awt.image.BufferedImage;


/**
 * Classe pour gérer le contraste et la lumière
 */
public class Contraste
{

	String    fichierSource;
	String    fichierDest;
	ImageUtil imgUtil;

	public Contraste(String fichierSource, String fichierDest)
	{
		this.fichierSource = fichierSource;
		this.fichierDest   = fichierDest;
		this.imgUtil       = new ImageUtil(fichierSource);
	}

	public void appliquerContraste(int contraste)
	{
		int couleur;
		BufferedImage imgModif = this.imgUtil.getImage();

		for (int x = 0; x < imgModif.getWidth(); x++)
		{
			for (int y = 0; y < imgModif.getHeight(); y++)
			{
				couleur = imgModif.getRGB(x, y) & 0xFFFFFF;
				Color c = new Color(couleur);
				int r = Math.min(255, Math.max(0, c.getRed()  + (contraste * (c.getRed()  - 127)) / 100));
				int v = Math.min(255, Math.max(0, c.getGreen()+ (contraste * (c.getGreen()- 127)) / 100));
				int b = Math.min(255, Math.max(0, c.getBlue() + (contraste * (c.getBlue() - 127)) / 100));
				imgModif.setRGB(x, y, new Color(r, v, b).getRGB());
			}
		}

		this.imgUtil.setImage(imgModif);
		this.imgUtil.sauvegarderImage(this.fichierDest);
	}
}
