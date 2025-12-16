package metier;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * Classe pour ajouter du texte avec fond image
 */
public class TexteImage
{
	ImageUtil imgUtil;
	String cheminImage;

	/**
	 * Constructeur
	 * 
	 * @param cheminImage Chemin de l'image de fond
	 */
	public TexteImage(String cheminImage)
	{
		this.cheminImage = cheminImage;
		this.imgUtil = new ImageUtil(cheminImage);
	}
}
