package hashcode;

import java.util.LinkedList;

public class SHS1 {
    LinkedList<Couple>[] data; // c'est un tableau de listes chaînées
    int size = 0; // nb d'éléments stockés
    static final double T = 0.7; // le seuil où il faut doubler la capacité du tableau

    // on va utiliser la méthode hashcode() de la classe String
    // pour pouvoir déterminer la case où on stocke l'élément, on va faire
    // hashcode%data.length

    public SHS1(int taille) {
        this.data = new LinkedList[taille];
    }

    class Couple {
        String s;
        int hash;

        public Couple(String s, int hash) {
            this.s = s;
            this.hash = hash;
        }

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }

        public int getHash() {
            return hash;
        }

        public void setHash(int hash) {
            this.hash = hash;
        }

        // il faut redéfinir la méthode equals pour que ça ne compare pas la référence,
        // mais le String

        @Override
        public boolean equals(Object obj) {
            // références nulles
            if (this == obj)
                return true;
            if (obj == null)
                return false;

            if (!(obj instanceof Couple))
                return false;
            Couple other = (Couple) obj;

            return this.getS().equals(other.getS());
        }
    }

    boolean add(String s) {
        if (this.contains(s)) return false;

        Couple couple = new Couple(s, s.hashCode());
        int caseTableau = Math.abs(couple.getHash() % data.length);

        // ensure capacity
        this.grow();

        if (data[caseTableau] == null) {
            data[caseTableau] = new LinkedList<>();
        }

        data[caseTableau].add(couple);
        this.size++;

        return true;
    }

    boolean contains(String s) {
        int caseTableau = Math.abs(s.hashCode() % data.length);
        if (data[caseTableau] == null) return false;
        return data[caseTableau].contains(new Couple(s, s.hashCode()));
    }

    boolean grow() {
        if (size / data.length > T) {
            // méthode pour doubler la longueur
            // il faut aussi redéfinir la position des éléments

            LinkedList<Couple>[] nouveauTableau = new LinkedList[this.data.length * 2];
            for (int i = 0; i < this.data.length; i++) {
                if (this.data[i] != null) {
                    int sizeListe = this.data[i].size();
                    for (int j = 0; j < sizeListe; j++) {
                        int caseTableau = Math.abs(this.data[i].get(j).getHash() % nouveauTableau.length);
                        if (nouveauTableau[caseTableau] == null) {
                            nouveauTableau[caseTableau] = new LinkedList<>();
                        }
                        nouveauTableau[caseTableau].add(this.data[i].get(j));
                    }
                }
            }
        }
        return true;
    }

    void afficheSHS() {
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null) {
                System.out.println("null");
            } else {
                for (Object o : data[i].toArray()) {
                    if (o instanceof Couple) {
                        Couple c = (Couple) o;
                        System.out.print(c.getS() + " ");
                    }
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SHS1 test1 = new SHS1(2);

        test1.add("bonjour");
        test1.add("algorithmique");
        test1.add("hello");

        test1.afficheSHS();

        System.out.println(test1.contains("bonjour"));
        System.out.println(test1.contains("hfwjehb"));

        test1.add("bonjour");

        test1.afficheSHS();

        // modifier le nombre (on peut mettre 1000, 10000, 100000, 1000000, etc)
        for (int i=0; i<1000; i++) {
            test1.add("t" + i);
        }

        test1.afficheSHS();

        // tester le temps pour toutes ces valeurs et ajouter des graphes (trouver une librairie similaire à Pandas en Python)
        // il faut faire ça pour StringHashSet, StringArrayList et StringTreeSet
        // on pourrait aussi comparer nos résultats aux ceux de l'implémentation de Collections
    }
}


// Pourquoi "new LinkedList<Couple> [10]" ne marche pas ?
// un problème mathématique
// quand on écrit ça, il va ensuite vérifier qu'on a pas affecté un truc à qq chose qui a un autre type
// il est obligé de vérifier que l'objet qu'on a créé est du type Couple, par exemple
// il n'est pas capable de faire ça, c'est trop compliqué
// ça va nous poser des problèmes à l'exécution, c'est pour ça qu'il préfère de créer un LinkedList simple, pas un LinkedList<Couple>