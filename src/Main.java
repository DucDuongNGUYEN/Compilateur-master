import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        AnalyseurLexical.INITIALISER();

        // Lecture du premier caractère
        AnalyseurLexical.LIRE_CAR();

        while (true) {
            AnalyseurLexical.T_UNILEX token = AnalyseurLexical.ANALEX();
            if (token == AnalyseurLexical.T_UNILEX.fin_fichier) {
                break;
            }
            if (token != null){
                System.out.println("Token: " + token);
            }
        }

        AnalyseurLexical.TERMINER();
    }
}
