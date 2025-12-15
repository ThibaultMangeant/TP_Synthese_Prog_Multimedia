package metier;



/**
 * Classe pour faire des rotations d'images
 */
public class Rotation
{

	public ImageUtil imageUtil;
	String fichierSource;


	/**
	 * Constructeur
	 * 
	 * @param fichierSource
	 */
	public Rotation(String fichierSource)
	{
		this.fichierSource = fichierSource;
		this.imageUtil     = new ImageUtil(fichierSource);
	}
	

}
