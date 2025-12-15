package metier;

/**
 * Petite classe de test pour la rotation d'image.
 * Par défaut utilise l'image du dossier images et produit quatre fichiers:
 *  - images/david_tennant_rot90.png
 *  - images/david_tennant_rot180.png
 *  - images/david_tennant_rot270.png
 *  - images/david_tennant_rot45.png
 */
public class TestRotation {

    public static void main(String[] args) {
        String src = args.length > 0 ? args[0] : "images/david_tennant.png";
        String out90 = args.length > 1 ? args[1] : "images/david_tennant_rot90.png";
        String out180 = args.length > 2 ? args[2] : "images/david_tennant_rot180.png";
        String out270 = args.length > 3 ? args[3] : "images/david_tennant_rot270.png";
        String out45 = args.length > 4 ? args[4] : "images/david_tennant_rot45.png";

        System.out.println("[TestRotation] Source: " + src);

        try {
            Rotation r90 = new Rotation(src, out90);
            r90.rotation90();
            System.out.println("[TestRotation] Rotation 90° OK");

            Rotation r180 = new Rotation(src, out180);
            r180.rotation180();
            System.out.println("[TestRotation] Rotation 180° OK");

            Rotation r270 = new Rotation(src, out270);
            r270.rotation270();
            System.out.println("[TestRotation] Rotation 270° OK");

            Rotation r45 = new Rotation(src, out45);
            r45.rotation(45.0);
            System.out.println("[TestRotation] Rotation 45° OK");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("[TestRotation] Terminé.");
    }
}
