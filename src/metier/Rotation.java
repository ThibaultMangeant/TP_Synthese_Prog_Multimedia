package metier;

import java.awt.image.BufferedImage;

/**
 * Classe pour faire des rotations d'images
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
		BufferedImage img, dest;

		img  = this.imageUtil.getImage();
		dest = appliquerRotation(img, angle);
		this.imageUtil.setImage(dest);
		if (this.fichierDest != null && !this.fichierDest.isEmpty())
		{
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
	private static int[] rotatedSize(int w, int h, double a)
	{
		double[] xs, ys;
		double   ca, sa;
		double   minX, minY, maxX, maxY;
		double   x, y;
		double   xr, yr;
		int      newW, newH;

		// Coordonnees des 4 coins de l'image source par rapport au centre
		// Coin haut-gauche, haut-droit, bas-droit, bas-gauche
		xs = new double[] { -w/2.0, w/2.0, w/2.0, -w/2.0 };
		ys = new double[] { -h/2.0, -h/2.0, h/2.0, h/2.0 };
		
		// Precalcul du cosinus et sinus
		ca = Math.cos(a);
		sa = Math.sin(a);
		
		// Initialisation des limites min/max pour trouver la boite englobante
		minX = Double.POSITIVE_INFINITY; maxX = Double.NEGATIVE_INFINITY;
		minY = Double.POSITIVE_INFINITY; maxY = Double.NEGATIVE_INFINITY;
		
		// Pour chacun des 4 coins de l'image
		for (int k = 0; k < 4; k++)
		{
			x = xs[k];
			y = ys[k];
			
			// Application de la rotation au coin
			// xr = x*cos(a) + y*sin(a)
			// yr = -x*sin(a) + y*cos(a)
			xr = x * ca + y * sa;
			yr = -x * sa + y * ca;
			
			// Mise a jour des limites min/max
			if (xr < minX) minX = xr;
			if (xr > maxX) maxX = xr;
			if (yr < minY) minY = yr;
			if (yr > maxY) maxY = yr;
		}
		
		// Calcul des nouvelles dimensions a partir des limites
		newW = (int) Math.round(maxX - minX);
		newH = (int) Math.round(maxY - minY);
		
		return new int[] { newW, newH };
	}

	/**
	 * Methode statique: applique la rotation en memoire et retourne l'image tournee.
	 */
	public static BufferedImage appliquerRotation(BufferedImage src, double angle)
	{
		int[]    size;
		double   ca, sa, a;
		double   i0, j0, i0r, j0r;
		double   x, y;
		double   yp, xp;
		int      w, h;
		int      is, js;
		int      newW, newH;
		int      rgb;

		BufferedImage dest;

		// Conversion de l'angle de degres en radians
		a = Math.toRadians(angle);

		w = src.getWidth();
		h = src.getHeight();

		size = rotatedSize(w, h, a);
		newW = size[0];
		newH = size[1];

		dest = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

		ca = Math.cos(a);
		sa = Math.sin(a);

		i0 = w / 2.0;
		j0 = h / 2.0;
		i0r = newW / 2.0;
		j0r = newH / 2.0;

		for (int jd = 0; jd < newH; jd++)
		{
			for (int id = 0; id < newW; id++)
			{
				xp = id - i0r;
				yp = jd - j0r;

				x = xp * ca - yp * sa;
				y = xp * sa + yp * ca;

				is = (int) Math.round(x + i0);
				js = (int) Math.round(y + j0);

				if (is >= 0 && is < w && js >= 0 && js < h)
				{
					rgb = src.getRGB(is, js);
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
	public static BufferedImage appliquerRotation90 (BufferedImage src) { return Rotation.appliquerRotation(src,  90.0); }
	public static BufferedImage appliquerRotation180(BufferedImage src) { return Rotation.appliquerRotation(src, 180.0); }
	public static BufferedImage appliquerRotation270(BufferedImage src) { return Rotation.appliquerRotation(src, 270.0); }

}
