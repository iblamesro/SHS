import java.util.LinkedList;

public class SHS{
    
    
    LinkedList<Couple> [] tableau; // table de hachage
    int count=0; // taille de la table de hachage
    private static final int T = 0; //T est un seuil qui permet de déterminer si la table de hachage doit être agrandie ou non
    public boolean add(String s){
        int hash = s.hashCode(); // hashcode est une méthode qui permet de générer un nombre unique pour chaque chaine de caractèrese basant sur le nombre de caractères de la chaine et leur valeur ASCII. ce nombre correspond à l'index de la chaine dans la table de hachage. hash correspond donc à l'index de la chaine s dans la table de hachage. il est utilisé pour déterminer l'emplacement où stocker ou récupérer la valeur associée à cette clé.
        int index = hash%tableau.length; // on calcule l'index de la chaine s dans la table de hachage.
        if(tableau[index]==null){ // si la case de la table de hachage à l'index index est vide
            tableau[index]=new LinkedList<Couple>(); // on créer une liste chainée à cette case
        }
        if(tableau[index].contains(s)){ // si la liste chainée à l'index index contient déjà la chaine s
            return false; // on retourne false
        }
        tableau[index].add(new Couple(s,hash)); // on ajoute la chaine s à la liste chainée à l'index index
        count++; // on incrémente la taille de la table de hachage
        return true; // on retourne true 
    }
    public boolean contains(String s){
        int hash = s.hashCode(); // on calcule le hash de la chaine s
        int index = hash%tableau.length; // on calcule l'index de la chaine s dans la table de hachage
        if(tableau[index]==null){ // si la case de la table de hachage à l'index index est vide
            return false; // on retourne false
        }
        return tableau[index].contains(s); // on retourne true si la liste chainée à l'index index contient la chaine s, false sinon
    }
    public void grow(){
        if (count/tableau.length>T){ // si le rapport entre la taille de la table de hachage et la taille de la table de hachage est supérieur à T
            LinkedList<Couple> [] nouveauTableau = new LinkedList[tableau.length*2]; // on crée une nouvelle table de hachage de taille double
            for(int i=0;i<tableau.length;i++){ // pour chaque case de la table de hachage
                if(tableau[i]!=null){ // si la case n'est pas vide
                    for(Couple c:tableau[i]){ // pour chaque couple c de la liste chainée à la case i
                        int index = c.hash%nouveauTableau.length; // on calcule l'index de la chaine c dans la nouvelle table de hachage
                        if(nouveauTableau[index]==null){ // si la case de la nouvelle table de hachage à l'index index est vide
                        nouveauTableau[index]=new LinkedList<Couple>(); // on créer une liste chainée à cette case
                        }
                        nouveauTableau[index].add(c); // on ajoute le couple c à la liste chainée à l'index index
                    }
                }
            }
            tableau=nouveauTableau; // on remplace la table de hachage par la nouvelle table de hachage
        }
    }
    public static void main(String[] args) {
        SHS s = new SHS();
        s.add("hello");
        s.add("world");
        s.add("worlds");
        System.out.println(s.contains("hello"));
        System.out.println(s.contains("world"));
        System.out.println(s.contains("worlds"));
    }
    
}

    

