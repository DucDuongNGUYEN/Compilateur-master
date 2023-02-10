public class Main {

    public static void main(String[] args) throws Exception {
        AnalyseurLexical analyseurLexical = new AnalyseurLexical();
        analyseurLexical.INITIALISER();
        //System.out.println("hh'hh'h");
        analyseurLexical.LIRE_CAR();
        while (true) {
            AnalyseurLexical.T_UNILEX token = analyseurLexical.ANALEX();
            System.out.println(token + " ");
        }
        //analyseurLexical.TERMINER();
    }
}
