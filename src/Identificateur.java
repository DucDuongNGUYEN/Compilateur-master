import java.util.HashMap;

public class Identificateur {
    private static final int NB_IDENT_MAX = 100;
    private static T_ENREG_IDENT[] TABLE_IDENT = new T_ENREG_IDENT[NB_IDENT_MAX];
    private static HashMap<String, T_ENREG_IDENT> hachage = new HashMap<>();
    public String nom;

    public static void ajouterIdentificateur(String nom, T_ENREG_IDENT enreg) {
        TABLE_IDENT[nom.length()] = enreg;
        hachage.put(nom, enreg);
    }

    public static T_ENREG_IDENT obtenirIdentificateur(String nom) {
        return hachage.get(nom);
    }

    public static boolean estPresent(String nom) {
        return hachage.containsKey(nom);
    }

    private static int hachage(String nom) {
        // Implémentation de la fonction de hachage
        return 0;
    }

}