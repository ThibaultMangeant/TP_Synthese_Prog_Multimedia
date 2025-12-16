package metier;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * Classe pour ajouter du texte avec fond image
 */
public class TexteImage
{
	ImageUtil imgUtil;
	String fichierSource;
	String texte;
	String fichierDest;

	/**
	 * Constructeur
	 * 
	 * @param fichierSource Chemin de l'image de fond
	 */
	public TexteImage(String fichierSource, String texte, String fichierDest)
	{
		this.fichierSource = fichierSource;
		this.texte = texte;
		this.fichierDest = fichierDest;
		this.imgUtil = new ImageUtil(fichierSource);
	}

	
}
