package metier;

import java.awt.image.BufferedImage;

/**
 * Opérations de découpage/recadrage sur une image
 */
public class Decoupage {

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
    public static BufferedImage decouper(BufferedImage img, int x1, int y1, int x2, int y2) {
        if (img == null) return null;

        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        int maxX = Math.max(x1, x2);
        int maxY = Math.max(y1, y2);

        // Borne dans l'image
        minX = Math.max(0, Math.min(minX, img.getWidth()  - 1));
        minY = Math.max(0, Math.min(minY, img.getHeight() - 1));
        maxX = Math.max(0, Math.min(maxX, img.getWidth()  - 1));
        maxY = Math.max(0, Math.min(maxY, img.getHeight() - 1));

        int width  = Math.max(0, maxX - minX + 1);
        int height = Math.max(0, maxY - minY + 1);

        if (width <= 0 || height <= 0) {
            // Rectangle vide: retourner l'image originale pour éviter les erreurs
            return img;
        }

        // getSubimage attend des dimensions valides
        return img.getSubimage(minX, minY, width, height);
    }
}
