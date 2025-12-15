package metier;

import java.awt.Image;
import java.awt.image.BufferedImage;

import metier.ImageUtil;

/**
 * Classe pour faire des rotations d'images
 */
public class Rotation
{

	public ImageUtil imageUtil;
	String fichierSource;

	public Rotation(String fichierSource)
	{
		this.fichierSource = fichierSource;
		this.imageUtil = new ImageUtil(fichierSource);
	}
	

}
