package metier;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * Classe utilitaire pour les images
 */
public class ImageUtil
{
	public BufferedImage img;
	public String cheminImage;
	public int largeur;
	public int hauteur;

	/**
	 * Constructeur
	 * 
	 * @param srcImg
	 */
	public ImageUtil(String srcImg)
	{
		this.cheminImage = srcImg;
		try
		{
			this.img = ImageIO.read(new File(cheminImage));
			this.hauteur = img.getHeight();
			this.largeur = img.getWidth();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	public int getLargeur()
	{
		return this.largeur;
	}

	public int getHauteur()
	{
		return this.hauteur;
	}

	/**
	 * Obtenir le chemin de l'image
	 * @return String chemin
	 */
	public String getCheminImage()
	{
		return this.cheminImage;
	}

	/**
	 * Obtenir l'image
	 * @return BufferedImage
	 */
	public BufferedImage getImage()
	{
		return this.img;
	}

	public void setImage(BufferedImage img)
	{
		this.img = img;
	}

	/**
	 * Sauvegarder l'image au format PNG
	 * @param destImg
	 */
	public void sauvegarderImage(String destImg)
	{
		this.sauvegarderImage(destImg, "png");
	}

	public void sauvegarderImage(String destImg, String format)
	{
		try
		{
			ImageIO.write(this.img, format, new File(destImg));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Afficher le détail des couleurs d'un pixel
	 * 
	 * @param couleur
	 * @return List<Integer> des composantes RGB
	 */
	public  List<Integer> afficherDetailCouleur(int couleur)
	{
		List<Integer> rgb = new ArrayList<>();
		int rouge = couleur / (256*256); 
		int vert  = couleur / 256 % 256;
		int bleu  = couleur % 256    ;

		rgb.add(rouge);
		rgb.add(vert);
		rgb.add(bleu);

		return rgb;
	}

	/**
	 * Calculer la luminance d'une couleur selon un algorithme donné
	 * 
	 * @param c
	 * @param numAlgo
	 * @return int luminance
	 */
	public static int luminance( Color c, int numAlgo)
	{
		int min,max;
		int rouge = c.getRed();
		int vert  = c.getGreen();
		int bleu  = c.getBlue();

		switch (numAlgo) {
			
			case 2: return (rouge+vert+bleu)/3;

			case 3: return (rouge*299 + vert*587 + bleu*114) / 1000;

			default:

				min = Math.min(vert, bleu);
				min = Math.min(min,  rouge);
				max = Math.max(vert, bleu);
				max = Math.max(max,  rouge);

				return (min+max)/2;
		}
	}
}
