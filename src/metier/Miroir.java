package metier;

import java.awt.image.BufferedImage;

/**
 * Classe pour les effets miroir (flip horizontal/vertical)
 */
public class Miroir
{
    String fichierSource;
    String fichierDest;
    ImageUtil imgUtil;

    /**
     * Constructeur de la classe Miroir
     * @param fichierSource Chemin du fichier image source
     * @param fichierDest Chemin du fichier image de destination (sauvegarde)
     */
    public Miroir(String fichierSource, String fichierDest)
    {
        this.fichierSource = fichierSource;
        this.fichierDest   = fichierDest;
        this.imgUtil       = new ImageUtil(fichierSource);
    }

    /**
     * Retourne l'image comme dans un miroir horizontal (gauche <-> droite)
     * Effectue une symétrie axiale verticale.
     */
    public void miroirHorizontal()
    {
        // Récupération de l'image source et de ses dimensions
        BufferedImage src = this.imgUtil.getImage();
        int w = src.getWidth();
        int h = src.getHeight();

        // Création de l'image de sortie (gestion du type d'image pour la transparence)
        BufferedImage out = new BufferedImage(w, h,
                src.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : src.getType());

        // Parcours de chaque pixel de l'image
        for (int y = 0; y < h; y++)
        {
            for (int x = 0; x < w; x++)
            {
                // Récupérer la couleur du pixel source
                int rgb = src.getRGB(x, y);
                
                // Inverser la position X (gauche devient droite)
                out.setRGB(w - 1 - x, y, rgb);
            }
        }

        // Mise à jour de l'image dans l'utilitaire et sauvegarde
        this.imgUtil.setImage(out);
        this.imgUtil.sauvegarderImage(this.fichierDest);
    }

    /**
     * Retourne l'image comme dans un miroir vertical (haut <-> bas)
     * Effectue une symétrie axiale horizontale.
     */
    public void miroirVertical()
    {
        // Récupération de l'image source et de ses dimensions
        BufferedImage src = this.imgUtil.getImage();
        int w = src.getWidth();
        int h = src.getHeight();

        // Création de l'image de sortie (gestion du type d'image pour la transparence)
        BufferedImage out = new BufferedImage(w, h,
                src.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : src.getType());

        // Parcours de chaque pixel de l'image
        for (int y = 0; y < h; y++)
        {
            for (int x = 0; x < w; x++)
            {
                // Récupérer la couleur du pixel source
                int rgb = src.getRGB(x, y);
                
                // Inverser la position Y (haut devient bas)
                out.setRGB(x, h - 1 - y, rgb);
            }
        }

        // Mise à jour de l'image dans l'utilitaire et sauvegarde
        this.imgUtil.setImage(out);
        this.imgUtil.sauvegarderImage(this.fichierDest);
    }
}