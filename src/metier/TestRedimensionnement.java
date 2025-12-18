package metier;

/**
 * Petite classe de test pour le redimensionnement d'image.
 * Par defaut utilise l'image du dossier src/images et produit deux fichiers:
 *  - src/images/david_tennant_resized_300x300.png
 *  - src/images/david_tennant_scaled_0_5.png
 * @author Equipe 5
 */
public class TestRedimensionnement
{
	public static void main(String[] args)
	{
		String src, destA, destB;
		Redimensionnement rA, rB;

		src   = args.length > 0 ? args[0] : "src/images/david_tennant.png";
		destA = args.length > 1 ? args[1] : "src/images/david_tennant_resized_300x300.png";
		destB = args.length > 2 ? args[2] : "src/images/david_tennant_scaled_0_5.png";

		System.out.println("[TestRedimensionnement] Source: " + src);
		System.out.println("[TestRedimensionnement] Dest A: " + destA);
		System.out.println("[TestRedimensionnement] Dest B: " + destB);

		try
		{
			rA = new Redimensionnement(src, destA);
			rA.redimensionner(300, 300);
			System.out.println("[TestRedimensionnement] Redimensionnement 300x300 OK");

			rB = new Redimensionnement(src, destB);
			rB.redimensionnerRatio(0.5);
			System.out.println("[TestRedimensionnement] Redimensionnement ratio 0.5 OK");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		System.out.println("[TestRedimensionnement] Terminé.");
	}
}
