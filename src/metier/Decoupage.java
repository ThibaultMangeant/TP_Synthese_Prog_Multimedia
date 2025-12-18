package metier;

import java.awt.image.BufferedImage;

/**
 * Opérations de découpage/recadrage sur une image
 */
public class Decoupage
{

    /**
     * Découpe l'image dans le rectangle défini par deux points.
     * Les coordonnées sont exprimées dans l'espace de l'image (pas l'écran).
     * Les bornes sont automatiquement bornées dans l'image.
     *
     * @param img Image source
     * @param x1  x du premier point
     * @param y1  y du premier point
     * @param x2  x du second point
     * @param y2  y du second point
     * @return Sous-image recadrée (ou l'image d'origine si rectangle vide)
     */
    public static BufferedImage decouper(BufferedImage img, int x1, int y1, int x2, int y2)
    {
        int minX, minY, maxX, maxY;
        int largeur, hauteur;

		if (img == null) { return null; }
		minX = Math.min(x1, x2);
        minY = Math.min(y1, y2);
        maxX = Math.max(x1, x2);
        maxY = Math.max(y1, y2);

        // Borne dans l'image
        minX = Math.max(0, Math.min(minX, img.getWidth()  - 1));
        minY = Math.max(0, Math.min(minY, img.getHeight() - 1));
        maxX = Math.max(0, Math.min(maxX, img.getWidth()  - 1));
        maxY = Math.max(0, Math.min(maxY, img.getHeight() - 1));

        largeur = Math.max(0, maxX - minX + 1);
        hauteur = Math.max(0, maxY - minY + 1);

		// Rectangle vide: retourner l'image originale pour éviter les erreurs
		if (largeur <= 0 || hauteur <= 0) { return img; }        // getSubimage attend des dimensions valides
        return img.getSubimage(minX, minY, largeur, hauteur);
    }
}
