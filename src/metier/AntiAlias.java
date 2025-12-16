package metier;

import java.awt.image.BufferedImage;

/**
 * Classe pour l'anti-aliasing (lissage)
 */
public class AntiAlias
{
	ImageUtil imgUtil;
	String fichierSource;
	String fichierDest;

	/**
	 * Constructeur fichier -> fichier (mode sauvegarde).
	 * @param fichierSource chemin image source
	 * @param fichierDest   chemin image destination
	 */
	public AntiAlias(String fichierSource, String fichierDest)
	{
		this.fichierSource = fichierSource;
		this.fichierDest   = fichierDest;
		this.imgUtil       = new ImageUtil(fichierSource);
	}

	/**
	 * Applique un lissage (anti-aliasing) de type gaussien 3x3 et sauvegarde.
	 */
	public void lisser()
	{
		BufferedImage src = this.imgUtil.getImage();
		BufferedImage out = appliquerAntiAliasing(src);
		this.imgUtil.setImage(out);
		this.imgUtil.sauvegarderImage(this.fichierDest);
	}

	/**
	 * Méthode statique: applique un lissage gaussien 3x3 (anti-aliasing) et retourne l'image lissée.
	 * Noyau utilisé (normalisation 1/16):
	 * 1 2 1
	 * 2 4 2
	 * 1 2 1
	 * Les composantes A,R,G,B sont traitées et clampées dans [0,255].
	 * @param src image source
	 * @return image lissée
	 */
	public static BufferedImage appliquerAntiAliasing(BufferedImage src)
	{
		int w = src.getWidth();
		int h = src.getHeight();
		BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

		int[] k = {1,2,1,
				 2,4,2,
				 1,2,1};
		int kSum = 16;

		for (int y = 0; y < h; y++)
		{
			for (int x = 0; x < w; x++)
			{
				int aAcc=0, rAcc=0, gAcc=0, bAcc=0;
				int ki = 0;
				for (int j = -1; j <= 1; j++)
				{
					int yy = y + j;
					if (yy < 0) yy = 0; if (yy >= h) yy = h - 1; // clamp bord
					for (int i = -1; i <= 1; i++)
					{
						int xx = x + i;
						if (xx < 0) xx = 0; if (xx >= w) xx = w - 1; // clamp bord
						int pixel = src.getRGB(xx, yy);
						int a = (pixel >>> 24) & 0xFF;
						int r = (pixel >>> 16) & 0xFF;
						int g = (pixel >>> 8)  & 0xFF;
						int b = pixel & 0xFF;

						int kv = k[ki++];
						aAcc += kv * a;
						rAcc += kv * r;
						gAcc += kv * g;
						bAcc += kv * b;
					}
				}

				int na = aAcc / kSum;
				int nr = rAcc / kSum;
				int ng = gAcc / kSum;
				int nb = bAcc / kSum;

				if (na < 0) na = 0; else if (na > 255) na = 255;
				if (nr < 0) nr = 0; else if (nr > 255) nr = 255;
				if (ng < 0) ng = 0; else if (ng > 255) ng = 255;
				if (nb < 0) nb = 0; else if (nb > 255) nb = 255;

				int rgba = (na << 24) | (nr << 16) | (ng << 8) | nb;
				out.setRGB(x, y, rgba);
			}
		}

		return out;
	}
}
