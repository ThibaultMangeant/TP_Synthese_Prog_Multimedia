package metier;

import java.awt.image.BufferedImage;

/**
 * Classe pour gerer les teintes de couleurs
 * @author Equipe 5
 */
public class Teinte
{

	private ImageUtil imgUtil;
	private String    fichierDest;

	/**
	 * Constructeur
	 * 
	 * @param fichierSource Chemin de l'image source
	 * @param fichierDest Chemin du fichier destination
	 */
	public Teinte(String fichierSource, String fichierDest)
	{
		this.fichierDest   = fichierDest;
		this.imgUtil       = new ImageUtil(fichierSource);
	}

	/**
	 * Appliquer une teinte a l'image
	 * 
	 * @param teinteRouge Ajustement de la composante rouge (-255 a +255)
	 * @param teinteVerte Ajustement de la composante verte (-255 a +255)
	 * @param teinteBleue Ajustement de la composante bleue (-255 a +255)
	 */
	public void teinter(int teinteRouge, int teinteVerte, int teinteBleue)
	{
		BufferedImage src, out;

		src = this.imgUtil.getImage();
		out = appliquerTeinte(src, teinteRouge, teinteVerte, teinteBleue);
		this.imgUtil.setImage(out);
		this.imgUtil.sauvegarderImage(this.fichierDest);
	}

	/**
	 * Methode statique: applique une teinte RGB sur une image et retourne le resultat.
	 * @param src Image source
	 * @param teinteRouge delta rouge (-255..255)
	 * @param teinteVerte delta vert (-255..255)
	 * @param teinteBleue delta bleu (-255..255)
	 * @return Image teintee
	 */
	public static BufferedImage appliquerTeinte(BufferedImage src, int teinteRouge, int teinteVerte, int teinteBleue)
	{
		BufferedImage out;
		int largeur, hauteur;
		int a, r, g, b, nr, ng, nb, pixel, rgba;

		largeur = src.getWidth();
		hauteur = src.getHeight();
		out = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_ARGB);

		for (int x = 0; x < largeur; x++)
		{
			for (int y = 0; y < hauteur; y++)
			{
				pixel = src.getRGB(x, y);
				// Extraire RGBA correctement
				a = (pixel >>> 24) & 0xFF;
				r = (pixel >>> 16) & 0xFF;
				g = (pixel >>> 8)  & 0xFF;
				b = pixel & 0xFF;

				nr = Math.max(0, Math.min(255, r + teinteRouge));
				ng = Math.max(0, Math.min(255, g + teinteVerte));
				nb = Math.max(0, Math.min(255, b + teinteBleue));

				rgba = (a << 24) | (nr << 16) | (ng << 8) | (nb);
				out.setRGB(x, y, rgba);
			}
		}

		return out;
	}
}