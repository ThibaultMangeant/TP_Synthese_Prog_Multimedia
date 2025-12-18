package metier;

/**
 * Classe de test pour l'ajout de texte sur une image.
 * Par defaut utilise l'image du dossier src/images et produit deux fichiers:
 *  - src/images/david_tennant_texte.png (texte positionne)
 *  - src/images/david_tennant_texte_centre.png (texte centre)
 */
public class TestTexteImage
{
	public static void main(String[] args)
	{
		String src, destTexte, destCentre;
		TexteImage txt1, txt2;

		src        = args.length > 0 ? args[0] : "src/images/david_tennant.png";
		destTexte  = args.length > 1 ? args[1] : "src/images/david_tennant_texte.png";
		destCentre = args.length > 2 ? args[2] : "src/images/david_tennant_texte_centre.png";

		System.out.println("[TestTexteImage] Source: " + src);
		System.out.println("[TestTexteImage] Dest texte: " + destTexte);
		System.out.println("[TestTexteImage] Dest centre: " + destCentre);

		try
		{
			// Test 1: Creer un texte avec l'image en fond
			txt1 = new TexteImage(src, destTexte);
			txt1.creerTexteImage("DOCTOR WHO", 120);
			System.out.println("[TestTexteImage] Texte avec image OK");

			// Test 2: Un autre texte
			txt2 = new TexteImage(src, destCentre);
			txt2.creerTexteImage("TENNANT", 150);
			System.out.println("[TestTexteImage] Texte 2 avec image OK");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		System.out.println("[TestTexteImage] Termine.");
	}
}
