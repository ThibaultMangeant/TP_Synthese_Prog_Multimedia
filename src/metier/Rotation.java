package metier;

import java.awt.image.BufferedImage;

/**
 * Classe pour faire des rotations d'images
 */
public class Rotation
{

	public ImageUtil imageUtil;
	String fichierSource;
	String fichierDest;


	/**
	 * Constructeur
	 * 
	 * @param fichierSource Chemin de l'image source
	 * @param fichierDest Chemin du fichier destination
	 */
	public Rotation(String fichierSource, String fichierDest)
	{
		this.fichierSource = fichierSource;
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
		BufferedImage img = this.imageUtil.getImage();
		BufferedImage dest = appliquerRotation(img, angle);
		this.imageUtil.setImage(dest);
		if (this.fichierDest != null && !this.fichierDest.isEmpty()) {
			this.imageUtil.sauvegarderImage(this.fichierDest);
		}
	}
	
	/**
	 * Calcule les dimensions de la boite englobante d'une image apres rotation.
	 * Cette methode determine la taille necessaire pour contenir toute l'image
	 * apres rotation, sans decoupage.
	 * 
	 * @param w Largeur de l'image source
	 * @param h Hauteur de l'image source
	 * @param a Angle de rotation en radians
	 * @return Tableau contenant [nouvelle largeur, nouvelle hauteur]
	 */
	private static int[] rotatedSize(int w, int h, double a) {
		// Coordonnees des 4 coins de l'image source par rapport au centre
		// Coin haut-gauche, haut-droit, bas-droit, bas-gauche
		double[] xs = new double[] { -w/2.0, w/2.0, w/2.0, -w/2.0 };
		double[] ys = new double[] { -h/2.0, -h/2.0, h/2.0, h/2.0 };
		
		// Precalcul du cosinus et sinus
		double ca = Math.cos(a);
		double sa = Math.sin(a);
		
		// Initialisation des limites min/max pour trouver la boite englobante
		double minX = Double.POSITIVE_INFINITY, maxX = Double.NEGATIVE_INFINITY;
		double minY = Double.POSITIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY;
		
		// Pour chacun des 4 coins de l'image
		for (int k = 0; k < 4; k++) {
			double x = xs[k];
			double y = ys[k];
			
			// Application de la rotation au coin
			// xr = x*cos(a) + y*sin(a)
			// yr = -x*sin(a) + y*cos(a)
			double xr = x * ca + y * sa;
			double yr = -x * sa + y * ca;
			
			// Mise a jour des limites min/max
			if (xr < minX) minX = xr;
			if (xr > maxX) maxX = xr;
			if (yr < minY) minY = yr;
			if (yr > maxY) maxY = yr;
		}
		
		// Calcul des nouvelles dimensions a partir des limites
		int newW = (int) Math.round(maxX - minX);
		int newH = (int) Math.round(maxY - minY);
		
		return new int[] { newW, newH };
	}

	/**
	 * Methode statique: applique la rotation en memoire et retourne l'image tournee.
	 */
	public static BufferedImage appliquerRotation(BufferedImage src, double angle)
	{
		// Conversion de l'angle de degres en radians
		double a = Math.toRadians(angle);

		int w = src.getWidth();
		int h = src.getHeight();

		int[] size = rotatedSize(w, h, a);
		int newW = size[0];
		int newH = size[1];

		BufferedImage dest = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

		double ca = Math.cos(a);
		double sa = Math.sin(a);

		double i0 = w / 2.0;
		double j0 = h / 2.0;
		double i0r = newW / 2.0;
		double j0r = newH / 2.0;

		for (int jd = 0; jd < newH; jd++) {
			for (int id = 0; id < newW; id++) {
				double xp = id - i0r;
				double yp = jd - j0r;

				double x = xp * ca - yp * sa;
				double y = xp * sa + yp * ca;

				int is = (int) Math.round(x + i0);
				int js = (int) Math.round(y + j0);

				if (is >= 0 && is < w && js >= 0 && js < h) {
					int rgb = src.getRGB(is, js);
					dest.setRGB(id, jd, rgb);
				}
			}
		}

		return dest;
	}

	/**
	 * Rotation 90° (horaire)
	 */
	public void rotation90()
	{
		rotation(90.0);
	}

	/**
	 * Rotation 180°
	 */
	public void rotation180()
	{
		rotation(180.0);
	}

	/**
	 * Rotation 270° (horaire)
	 */
	public void rotation270()
	{
		rotation(270.0);
	}

	/**
	 * Aides statiques pour angles fixes
	 */
	public static BufferedImage appliquerRotation90(BufferedImage src) { return appliquerRotation(src, 90.0); }
	public static BufferedImage appliquerRotation180(BufferedImage src) { return appliquerRotation(src, 180.0); }
	public static BufferedImage appliquerRotation270(BufferedImage src) { return appliquerRotation(src, 270.0); }

}
