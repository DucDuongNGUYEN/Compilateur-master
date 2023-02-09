import java.io.*;
import java.util.HashMap;

public class AnalyseurLexical {

    public AnalyseurLexical() throws FileNotFoundException {
    }

    enum T_UNILEX {
        motcle, ident, ent, ch, virg, ptvirg, point, deuxspts, parouv, parfer, inf, sup, eg, plus, moins, mult, divi, infe, supe, diff, aff
    }

    final int LONG_MAX_IDENT = 20;
    final int LONG_MAX_CHAINE = 50;
    final int NB_MOTS_RESERVES = 7;
    final int LONG_MAXINT = 32767;

    static String SOURCE;
    static char CARLU;
    static double NOMBRE;
    static String CHAINE;
    static int NUM_LIGNE = 0;
    static String[] TABLE_MOTS_RESERVES = new String[NB_MOTS_RESERVES];

    File file = new File(SOURCE);
    FileReader fr = new FileReader(file);
    BufferedReader bf = new BufferedReader(fr);

    public static void ERREUR(int n) {
        switch (n) {
            case 1:
                System.out.println("fin de fichier atteinte _ ligne n°" + NUM_LIGNE);
                System.exit(0);
            case 2:
                System.out.println("dépassé la valeur maximale");
                System.exit(0);
        }
    }

    public static void LIRE_CAR() throws IOException {
        char c = (char) bf.read();
        CARLU = c;
        if (c == '\n') {
            NUM_LIGNE++;
        } else {
            ERREUR(1);
        }
    }

    private static void SAUTER_SEPARATEURS() throws Exception {
        while (CARLU == ' ' || CARLU == '\t' || CARLU == '\r' || CARLU == '\n' || CARLU == '{' || CARLU == '}' || CARLU == '/' || CARLU == '*') {
            if (CARLU == '{') {
                while (CARLU != '}') {
                    LIRE_CAR();
                    if (CARLU == (char) -1) {
                        ERREUR(1);
                    }
                }
            }
            if (CARLU == '/') {
                LIRE_CAR();
                if (CARLU == '*') {
                    LIRE_CAR();
                    while (CARLU != '/') {
                        if (CARLU == (char) -1) {
                            ERREUR(1);
                        }
                        if (CARLU == '*') {
                            LIRE_CAR();
                            if (CARLU == '/') {
                                break;
                            }
                        } else {
                            LIRE_CAR();
                        }
                    }
                }
            }
            LIRE_CAR();
        }
    }


    public T_UNILEX RECO_ENTIER() throws IOException {
        int n = 0;
        while (Character.isDigit(CARLU)) {
            n = n * 10 + (CARLU - '0');
            if (n > LONG_MAXINT) {
                ERREUR(2);
            }
            LIRE_CAR();
        }
        NOMBRE = n;
        return T_UNILEX.ent;
    }

    public T_UNILEX RECO_CHAINE() throws IOException {
        CHAINE = "";
        CARLU = (char) bf.read();
        while (CARLU != '\'') {
            if (CHAINE.length() >= LONG_MAX_CHAINE) {
                System.err.println("Erreur : chaîne de caractères trop longue (max " + LONG_MAX_CHAINE + ") à la ligne " + NUM_LIGNE);
                System.exit(1);
            }
            CHAINE = CHAINE + CARLU;
            CARLU = (char) bf.read();
        }
        return T_UNILEX.ch;
    }


    public T_UNILEX RECO_IDENT_OU_MOT_RESERVE() throws IOException {
        CHAINE = "";
        while (Character.isLetterOrDigit(CARLU) || CARLU == '_') {
            if (CHAINE.length() < LONG_MAX_IDENT) {
                CHAINE += CARLU;
            }
            CARLU = (char) bf.read();
        }
        CHAINE = CHAINE.toUpperCase();

        boolean isMotCle = EST_UN_MOT_RESERVE(CHAINE);
        return isMotCle ? T_UNILEX.motcle : T_UNILEX.ident;
    }

    /*
        boolean EST_UN_MOT_RESERVE(String chaine) {
            for (String motCle : TABLE_MOTS_RESERVES) {
                if (motCle.equals(chaine)) {
                    return true;
                }
            }
            return false;
     */
    boolean EST_UN_MOT_RESERVE(String chaine) {
        int i = 0;
        int j = NB_MOTS_RESERVES - 1;
        while (i <= j) {
            int k = (i + j) / 2;
            int compareResult = chaine.compareTo(TABLE_MOTS_RESERVES[k]);
            if (compareResult == 0) {
                return true;
            } else if (compareResult < 0) {
                j = k - 1;
            } else {
                i = k + 1;
            }
        }
        return false;
    }


    public T_UNILEX RECO_SYMB() throws IOException {
        switch (CARLU) {
            case ';':
               // CARLU = (char) bf.read();
                LIRE_CAR();
                return T_UNILEX.ptvirg;
            case '.':
                LIRE_CAR();
                return T_UNILEX.point;
            case '(':
                LIRE_CAR();
                return T_UNILEX.parouv;
            case ')':
                LIRE_CAR();
                return T_UNILEX.parfer;
            case '<':
                LIRE_CAR();
                if (CARLU == '=') {
                    LIRE_CAR();
                    return T_UNILEX.infe;
                }
                return T_UNILEX.inf;
            case '>':
                LIRE_CAR();
                if (CARLU == '=') {
                    LIRE_CAR();
                    return T_UNILEX.supe;
                }
                return T_UNILEX.sup;
            case '=':
                LIRE_CAR();
                return T_UNILEX.eg;
            case '+':
                LIRE_CAR();
                return T_UNILEX.plus;
            case '-':
                LIRE_CAR();
                return T_UNILEX.moins;
            case '*':
                LIRE_CAR();
                return T_UNILEX.mult;
            case '/':
                LIRE_CAR();
                return T_UNILEX.divi;
            case ':':
                LIRE_CAR();
                if (CARLU == '=') {
                    LIRE_CAR();
                    return T_UNILEX.aff;
                }
                return T_UNILEX.deuxspts;
            case '!':
                LIRE_CAR();
                if (CARLU == '=') {
                    LIRE_CAR();
                    return T_UNILEX.diff;
                }
                break;
            default:
                break;
        }
        return null;
    }


    private T_UNILEX ANALEX() throws IOException {
        // Sauter les séparateurs et les commentaires éventuels
        while (isSeparateur(CARLU) || isCommentStart(CARLU)) {
            if (isCommentStart(CARLU)) {
                skipComment();
            } else {
                CARLU = nextChar();
            }
        }

        // Essayer de reconnaître une unité lexicale
        if (Character.isDigit(CARLU)) {
            return RECO_ENTIER();
        } else if (CARLU == '\"') {
            return RECO_CHAINE();
        } else if (Character.isLetter(CARLU)) {
            return RECO_IDENT_OU_MOT_RESERVE();
        } else {
            return RECO_SYMB();
        }
    }


    public static void INITIALISER() {
        NUM_LIGNE = 1;
        try {
            SOURCE = new BufferedReader(new FileReader("source.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Le fichier source n'a pas été trouvé");
            System.exit(0);
        }
        TABLE_MOTS_RESERVES = new HashMap<>();
        INSERE_TABLE_MOTS_RESERVES("PROGRAMME");
        INSERE_TABLE_MOTS_RESERVES("DEBUT");
        INSERE_TABLE_MOTS_RESERVES("FIN");
        INSERE_TABLE_MOTS_RESERVES("CONST");
        INSERE_TABLE_MOTS_RESERVES("VAR");
        INSERE_TABLE_MOTS_RESERVES("ECRIRE");
        INSERE_TABLE_MOTS_RESERVES("LIRE");
    }

    private static void INSERE_TABLE_MOTS_RESERVES(String mot) {
        TABLE_MOTS_RESERVES.put(mot, mot);
    }


    public void TERMINER() {
    }

}

