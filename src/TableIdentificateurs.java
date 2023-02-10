import java.util.ArrayList;

class TableIdentificateurs {
    ArrayList<Identificateur> table;

    TableIdentificateurs() {
        table = new ArrayList<Identificateur>();
    }

    int chercher(String nom) {
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).nom.equals(nom)) {
                return i;
            }
        }
        return -1;
    }

    int inserer(String nom, int genre) {
        int i = chercher(nom);
        if (i == -1) {
            table.add(new Identificateur(nom, genre));
            i = table.size() - 1;
        }
        return i;
    }

    void afficherTable() {
        System.out.println("Table des identificateurs :");
        System.out.println("Indice | Nom | Genre");
        for (int i = 0; i < table.size(); i++) {
            System.out.println(i + " | " + table.get(i).nom + " | " + table.get(i).genre);
        }
    }
}
