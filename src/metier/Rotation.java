package metier;

import java.awt.image.BufferedImage;

/**
 * Classe pour faire des rotations d'images
 * @author Equipe 5
 */
public class Rotation
{

	private ImageUtil imageUtil;
	private String    fichierDest;


	/**
	 * Constructeur
	 * 
	 * @param fichierSource Chemin de l'image source
	 * @param fichierDest Chemin du fichier destination
	 */
	public Rotation(String fichierSource, String fichierDest)
	{
		this.fichierDest   = fichierDest;
		this.imageUtil     = new ImageUtil(fichierSource);
	}

	/**
	 * Effectue une rotation de l'image d'un angle en degres,
	 * en centrant la rotation et en ajustant la taille de sortie pour
	 * eviter le decoupage.
	 * 
	 * Cette methode utilise l'echantillonnage inverse (inverse mapping) :
	 * Pour chaque pixel de l'image destination, on calcule quel pixel de l'image
	 * source doit etre copie. Cela evite les trous dans l'image resultante.
	 * 
	 * @param angle Angle en degres (sens horaire)
	 */
	public void rotation(double angle)
	{
		BufferedImage image;
		BufferedImage destination;

		image       = this.imageUtil.getImage();
		destination = Rotation.appliquerRotation(image, angle);
		
		this.imageUtil.setImage(destination);
		
		if (this.fichierDest != null && !this.fichierDest.isEmpty()) { this.imageUtil.sauvegarderImage(this.fichierDest); }
	}
	
	/**
	 * Calcule les dimensions de la boite englobante d'une image apres rotation.
	 * Cette methode determine la taille necessaire pour contenir toute l'image
	 * apres rotation, sans decoupage.
	 * 
	 * @param largeur Largeur de l'image source
	 * @param hauteur Hauteur de l'image source
	 * @param angleRad Angle de rotation en radians
	 * @return Tableau contenant [nouvelle largeur, nouvelle hauteur]
	 */
	private static int[] rotatedSize(int largeur, int hauteur, double angleRad)
	{
		double[] coordonneesX, coordonneesY;
		double   cosinus, sinus;
		double   minX, minY, maxX, maxY;
		double   x, y;
		double   xRotation, yRotation;
		int      nouvelleLargeur, nouvelleHauteur;
		int      k;

		// Coordonnees des 4 coins de l'image source par rapport au centre
		// Coin haut-gauche, haut-droit, bas-droit, bas-gauche
		coordonneesX = new double[] { -largeur/2.0,  largeur/2.0, largeur/2.0, -largeur/2.0 };
		coordonneesY = new double[] { -hauteur/2.0, -hauteur/2.0, hauteur/2.0,  hauteur/2.0 };
		
		// Precalcul du cosinus et sinus
		cosinus = Math.cos(angleRad);
		sinus   = Math.sin(angleRad);
		
		// Initialisation des limites min/max pour trouver la boite englobante
		minX = Double.POSITIVE_INFINITY;
		maxX = Double.NEGATIVE_INFINITY;
		minY = Double.POSITIVE_INFINITY;
		maxY = Double.NEGATIVE_INFINITY;
		
		// Pour chacun des 4 coins de l'image
		for (k = 0; k < 4; k++)
		{
			x = coordonneesX[k];
			y = coordonneesY[k];
			
			// Application de la rotation au coin
			// xr = x*cos(a) + y*sin(a)
			// yr = -x*sin(a) + y*cos(a)
			xRotation = x * cosinus + y * sinus;
			yRotation = -x * sinus + y * cosinus;
			
			// Mise a jour des limites min/max
			if (xRotation < minX) { minX = xRotation; }
			if (xRotation > maxX) { maxX = xRotation; }
			if (yRotation < minY) { minY = yRotation; }
			if (yRotation > maxY) { maxY = yRotation; }
		}
		
		// Calcul des nouvelles dimensions a partir des limites
		nouvelleLargeur = (int) Math.round(maxX - minX);
		nouvelleHauteur = (int) Math.round(maxY - minY);
		
		return new int[] { nouvelleLargeur, nouvelleHauteur };
	}

	/**
	 * Methode statique: applique la rotation en memoire et retourne l'image tournee.
	 */
	public static BufferedImage appliquerRotation(BufferedImage source, double angle)
	{
		int[]         taille;
		double        cosinus, sinus, angleRad;
		double        centreSourceX, centreSourceY;
		double        centreDestX, centreDestY;
		double        x, y;
		double        xPrime, yPrime;
		int           largeur, hauteur;
		int           iSource, jSource;
		int           nouvelleLargeur, nouvelleHauteur;
		int           rgb;
		BufferedImage destination;
		int           id, jd;

		// Conversion de l'angle de degres en radians
		angleRad = Math.toRadians(angle);

		largeur = source.getWidth ();
		hauteur = source.getHeight();

		taille            = Rotation.rotatedSize(largeur, hauteur, angleRad);
		nouvelleLargeur   = taille[0];
		nouvelleHauteur   = taille[1];

		destination = new BufferedImage(nouvelleLargeur, nouvelleHauteur, BufferedImage.TYPE_INT_ARGB);

		cosinus = Math.cos(angleRad);
		sinus   = Math.sin(angleRad);

		centreSourceX = largeur / 2.0;
		centreSourceY = hauteur / 2.0;
		centreDestX   = nouvelleLargeur / 2.0;
		centreDestY   = nouvelleHauteur / 2.0;

		for (jd = 0; jd < nouvelleHauteur; jd++)
		{
			for (id = 0; id < nouvelleLargeur; id++)
			{
				xPrime = id - centreDestX;
				yPrime = jd - centreDestY;

				x = xPrime * cosinus - yPrime * sinus;
				y = xPrime * sinus   + yPrime * cosinus;

				iSource = (int) Math.round(x + centreSourceX);
				jSource = (int) Math.round(y + centreSourceY);

				if (iSource >= 0 && iSource < largeur && jSource >= 0 && jSource < hauteur)
				{
					rgb = source.getRGB(iSource, jSource);
					destination.setRGB(id, jd, rgb);
				}
			}
		}

		return destination;
	}

	/**
	 * Rotation 90° (horaire)
	 */
	public void rotation90()
	{
		this.rotation(90.0);
	}

	/**
	 * Rotation 180°
	 */
	public void rotation180()
	{
		this.rotation(180.0);
	}

	/**
	 * Rotation 270° (horaire)
	 */
	public void rotation270()
	{
		this.rotation(270.0);
	}

	/**
	 * Aides statiques pour angles fixes
	 */
	public static BufferedImage appliquerRotation90(BufferedImage source)
	{
		return Rotation.appliquerRotation(source, 90.0);
	}
	
	public static BufferedImage appliquerRotation180(BufferedImage source)
	{
		return Rotation.appliquerRotation(source, 180.0);
	}
	
	public static BufferedImage appliquerRotation270(BufferedImage source)
	{
		return Rotation.appliquerRotation(source, 270.0);
	}

}
