public class Couple {
    String s;
    int hash;
    public Couple(String s, int hash){
        this.s=s;
        this.hash=hash;
    }
    public boolean equals(Object o){
        if(o instanceof Couple){
            Couple c = (Couple)o;
            return c.s.equals(s);
        }
        return false;
    }
    public int hashCode(){
        return hash;
    }
    
    
}
