
package Equivalencia;

public class automata {    
    protected int s;
    protected int delta [][];
    protected char sigma[];
    protected int f [];
    protected int k [];                
                                                        
    public automata(String[] est, char[] alf, int[][] trans ,String estInicial, int[] estFinales){
        k = new int[est.length];
        for (int i = 0; i < k.length; i++) {
            k[i] = i;
        }
        delta = trans;
        sigma = alf;        
        for (int i = 0; i < est.length; i++) {
            if(estInicial.equals(est[i])){
                s = k[i];
            }
        }
        
        f = estFinales;
    }
        
    //SComprobar estados finales
    public boolean estadosfinales(int estNum){
        boolean estFinal = false;
        for (int i = 0; i < f.length; i++) {
            if(estNum == f[i]){
                estFinal = true;
                break;
            }
        }
        return estFinal;
    }
}
