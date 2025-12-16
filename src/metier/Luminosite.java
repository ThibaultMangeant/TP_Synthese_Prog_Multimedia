package metier;

public class Luminosite
{	
	ImageUtil imgUtil;
	String cheminImage;

	/**
	 * Constructeur
	 * 
	 * @param cheminImage Chemin de l'image
	 */
	public Luminosite(String cheminImage)
	{
		this.cheminImage = cheminImage;
		this.imgUtil = new ImageUtil(cheminImage);
	}

}
