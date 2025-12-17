package metier;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Classe de test pour le pot de peinture (remplissage et suppression de fond).
 * Par defaut utilise l'image du dossier src/images et produit deux fichiers:
 *  - src/images/david_tennant_remplir.png (remplissage avec couleur rouge)
 *  - src/images/david_tennant_retirer.png (suppression de fond)
 * @author Equipe 5
 */
public class TestPotPeinture
{
	public static void main(String[] args)
	{
		ImageUtil imgUtil1, imgUtil2;
		String    src, destRemplir, destRetirer;
		Point     positionRemplir, positionRetirer;
		Color     couleurRouge;
		int       tolerance;
		BufferedImage imgSource, imgARGB;

		src = args.length > 0 ? args[0] : "../src/images/asterix_couleur.png";
		destRemplir = args.length > 1 ? args[1] : "../src/images/asterix_couleur_remplir.png";
		destRetirer = args.length > 2 ? args[2] : "../src/images/asterix_couleur_retirer.png";

		System.out.println("[TestPotPeinture] Source: " + src);
		System.out.println("[TestPotPeinture] Dest remplir: " + destRemplir);
		System.out.println("[TestPotPeinture] Dest retirer: " + destRetirer);

		try
		{
			// Test 1: Remplissage avec une couleur
			// On clique sur un coin de l'image pour remplir le fond
			imgUtil1 = new ImageUtil(src);
			positionRemplir = new Point(10, 10); // Coin haut-gauche
			couleurRouge = new Color(255, 0, 0);
			tolerance = 30;
			
			PotPeinture.remplir(imgUtil1, positionRemplir, couleurRouge, tolerance);
			imgUtil1.sauvegarderImage(destRemplir);
			System.out.println("[TestPotPeinture] Remplissage OK");

			// Test 2: Suppression de fond (rendre transparent)
			// On clique au meme endroit pour retirer le fond
			// Important: il faut convertir l'image en ARGB pour supporter la transparence
			imgUtil2 = new ImageUtil(src);
			
			// Conversion en image avec canal alpha
			imgSource = imgUtil2.getImage();
			imgARGB = new BufferedImage(
				imgSource.getWidth(), 
				imgSource.getHeight(), 
				BufferedImage.TYPE_INT_ARGB
			);

			// Copie des pixels
			for (int y = 0; y < imgSource.getHeight(); y++)
			{
				for (int x = 0; x < imgSource.getWidth(); x++)
				{
					imgARGB.setRGB(x, y, imgSource.getRGB(x, y));
				}
			}
			
			imgUtil2.setImage(imgARGB);
			
			positionRetirer = new Point(10, 10);
			PotPeinture.retirerCouleur(imgUtil2, positionRetirer, tolerance);
			imgUtil2.sauvegarderImage(destRetirer);
			System.out.println("[TestPotPeinture] Suppression de fond OK");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		System.out.println("[TestPotPeinture] Termine.");
	}
}
