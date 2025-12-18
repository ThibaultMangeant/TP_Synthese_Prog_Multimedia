package metier;

/**
 * Petite classe de test pour la rotation d'image.
 * Par defaut utilise l'image du dossier src/images et produit quatre fichiers:
 *  - src/images/david_tennant_rot90.png
 *  - src/images/david_tennant_rot180.png
 *  - src/images/david_tennant_rot270.png
 *  - src/images/david_tennant_rot45.png
 * @author Equipe 5
 */
public class TestRotation
{

	public static void main(String[] args)
	{
		String src, out90, out180, out270, out45;
		Rotation r90, r180, r270, r45;

		src    = args.length > 0 ? args[0] : "src/images/david_tennant.png";
		out90  = args.length > 1 ? args[1] : "src/images/david_tennant_rot90.png";
		out180 = args.length > 2 ? args[2] : "src/images/david_tennant_rot180.png";
		out270 = args.length > 3 ? args[3] : "src/images/david_tennant_rot270.png";
		out45  = args.length > 4 ? args[4] : "src/images/david_tennant_rot45.png";
		System.out.println("[TestRotation] Source: " + src);

		try
		{
			r90 = new Rotation(src, out90);
			r90.rotation90();
			System.out.println("[TestRotation] Rotation 90° OK");

			r180 = new Rotation(src, out180);
			r180.rotation180();
			System.out.println("[TestRotation] Rotation 180° OK");

			r270 = new Rotation(src, out270);
			r270.rotation270();
			System.out.println("[TestRotation] Rotation 270° OK");

			r45 = new Rotation(src, out45);
			r45.rotation(45.0);
			System.out.println("[TestRotation] Rotation 45° OK");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		System.out.println("[TestRotation] Terminé.");
	}
}
