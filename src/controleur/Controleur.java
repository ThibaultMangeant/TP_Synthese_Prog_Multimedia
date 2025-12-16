package controleur;

import ihm.FramePrincipale;
import metier.*;
import java.awt.image.BufferedImage;

public class Controleur
{
    private FramePrincipale framePrincipale;
    private ImageUtil imageUtil;
    private String cheminImageCourant;

    public Controleur()
    {
        // Initialisation avec l'image par defaut
        // Chemin relatif depuis le dossier bin/
        this.cheminImageCourant = "../src/images/david_tennant.png";
        this.imageUtil = new ImageUtil(this.cheminImageCourant);
        this.framePrincipale = new FramePrincipale(this);
    }

    public String getCheminImageCourant()
    {
        return this.cheminImageCourant;
    }

    public BufferedImage getImage()
    {
        return this.imageUtil.getImage();
    }

    /**
     * Méthode importée depuis la version GitHub.
     * Vérifie si les coordonnées sont dans l'image.
     */
    public boolean contient(int x, int y)
    {
        return this.imageUtil.contient(x, y);
    }

    public void ouvrirImage(String path)
    {
        this.cheminImageCourant = path;
        this.imageUtil = new ImageUtil(path);
        this.framePrincipale.afficherImage(path);
    }

    public void sauvegarderImageSous(String path)
    {
        this.imageUtil.sauvegarderImage(path);
    }

    public void sauvegarderImage()
    {
        this.imageUtil.sauvegarderImage(this.cheminImageCourant);
    }

    public void miroirHorizontal()
    {
        BufferedImage src = this.imageUtil.getImage();
        BufferedImage out = Miroir.appliquerMiroirHorizontal(src);
        this.imageUtil.setImage(out);
        this.framePrincipale.afficherImage(out);
    }

    public void miroirVertical()
    {
        BufferedImage src = this.imageUtil.getImage();
        BufferedImage out = Miroir.appliquerMiroirVertical(src);
        this.imageUtil.setImage(out);
        this.framePrincipale.afficherImage(out);
    }

    /** * Redimensionne l'image courante aux dimensions spécifiées et met à jour l'affichage. 
     * (Logique mémoire conservée de votre version)
     */
    public void redimensionner(int newWidth, int newHeight)
    {
        BufferedImage src = this.imageUtil.getImage();
        BufferedImage out = Redimensionnement.redimensionner(src, newWidth, newHeight);
        this.imageUtil.setImage(out);
        this.framePrincipale.afficherImage(out);
    }

    /** * Redimensionne l'image courante en conservant le ratio via un facteur d'échelle. 
     */
    public void redimensionnerRatio(double scale)
    {
        BufferedImage src = this.imageUtil.getImage();
        BufferedImage out = Redimensionnement.redimensionnerRatio(src, scale);
        this.imageUtil.setImage(out);
        this.framePrincipale.afficherImage(out);
    }

    public void appliquerAntiAliasing()
    {
        System.out.println("Anti-aliasing appliqué.");
    }

    /** * Effectue une rotation en mémoire et met à jour l'affichage.
     * (Ajout de votre version)
     */
    public void rotation(int angle)
    {
        BufferedImage src = this.imageUtil.getImage();
        BufferedImage out = Rotation.appliquerRotation(src, angle);
        this.imageUtil.setImage(out);
        this.framePrincipale.afficherImage(out);
    }

    /**
     * Version wrapper pour compatibilité.
     * Utilise désormais la logique en mémoire (votre modification) 
     * au lieu de passer par image_temp.png (version Github).
     */
    public void appliquerRotation(int angle)
    {
        rotation(angle);
    }

    /**
     * Version wrapper pour compatibilité.
     * Utilise désormais la logique en mémoire (votre modification).
     */
    public void appliquerRedimensionnement(int nouvelleLargeur, int nouvelleHauteur)
    {
        redimensionner(nouvelleLargeur, nouvelleHauteur);
    }

    public void appliquerPotPeinture()
    {
        System.out.println("Pot de peinture appliqué.");
    }

    // Les méthodes ci-dessous utilisent encore la logique de fichier temporaire
    // car elles n'avaient pas été modifiées dans votre version locale.

    public void appliquerTeinte(int teinteRouge, int teinteVerte, int teinteBleue)
    {
        Teinte teinte = new Teinte(this.cheminImageCourant, "image_temp.png");
        teinte.teinter(teinteRouge, teinteVerte, teinteBleue);

        // Mettre à jour l'affichage
        this.framePrincipale.afficherImage("image_temp.png");
    }

    public void appliquerContraste(int valeurContraste)
    {
        Contraste contraste = new Contraste(this.cheminImageCourant, "image_temp.png");
        contraste.appliquerContraste(valeurContraste);

        // Mettre à jour l'affichage
        this.framePrincipale.afficherImage("image_temp.png");
    }

    public void appliquerSuperpositionImages()
    {
        System.out.println("Superposition d'images appliquée.");
    }

    public void appliquerCreationImageTexte()
    {
        System.out.println("Création d'un texte avec une image appliquée.");
    }

    public static void main(String[] args)
    {
        new Controleur();
    }
}