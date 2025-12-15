package metier;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * Classe utilitaire pour les images
 */
public class ImageUtil
{
	public BufferedImage img;

	public ImageUtil(String srcImg)
	{
		try
		{
			this.img = ImageIO.read(new File(srcImg));
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	public BufferedImage getImage() {
		return this.img;
	}

	public void setImage(BufferedImage img) {
		this.img = img;
	}

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


	public static void afficherDetailCouleur(String nomCouleur, int couleur)
	{
		int rouge = couleur / (256*256); 
		int vert  = couleur / 256 % 256;
		int bleu  = couleur % 256    ;

		System.out.println(String.format("%s%12d [%3d,%3d,%3d]\n", nomCouleur, couleur, rouge, vert, bleu));
	}

	public static int luminance( Color c, int numAlgo)
	{
		int min,max;
		int rouge = c.getRed();
		int vert = c.getGreen();
		int bleu = c.getBlue();

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
