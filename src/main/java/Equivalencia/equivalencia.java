package Equivalencia;

public class equivalencia {

    public static boolean calcular(automata aut1, automata aut2) {
        int[][] automata1 = aut1.delta;
        int[][] automata2 = aut2.delta;
        int estComp1 = aut1.s;
        int estComp2 = aut2.s;
        int[][] posArray = new int[2][2];
        int[][] comp = new int[30][2];
        int renglones = 0;
        int cont = 0;
        boolean result = false;

        //buscar en el arreglo de funcion de transiciones el número del estado inicial
        //Estados a comparar de los dos automatas
        comp[0][0] = estComp1;
        comp[0][1] = estComp2;
        renglones++;
        while ((aut1.estadosfinales(estComp1) && aut2.estadosfinales(estComp2) || !(aut1.estadosfinales(estComp1)) && !(aut2.estadosfinales(estComp2)))
                && cont < renglones) {
            cont++;
            //agarrar el estado del primer automata y buscar su destino en cada elemento
            for (int i = 0; i < automata1.length; i++) { //Obtener el siguiente estado
                if (automata1[i][0] == estComp1) {
                    for (int j = 0; j < aut1.sigma.length; j++) {
                        posArray[j][0] = automata1[i][2];
                    }
                }
            }

            //transiciones del segundo automata 
            for (int i = 0; i < automata2.length; i++) {
                if (automata2[i][0] == estComp2) {
                    for (int j = 0; j < aut2.sigma.length; j++) {
                        posArray[j][1] = automata2[i][2];
                    }
                }
            }

            //Verificar si las combinaciones no se repiten en el array
            boolean comb1 = false, comb2 = false;
            for (int i = 0; i < comp.length; i++) {
                if (posArray[0][0] == comp[i][0] && posArray[0][1] == comp[i][1] && !comb1) {
                    comb1 = true;
                }
                if (posArray[1][0] == comp[i][0] && posArray[1][1] == comp[i][1] && !comb2) {
                    comb2 = true;
                }
            }

            if (!comb1) {
                comp[renglones][0] = posArray[0][0];
                comp[renglones][1] = posArray[0][1];
                renglones++;
            }
            estComp1 = comp[cont][0];

            if (!comb2) {
                comp[renglones][0] = posArray[1][0];
                comp[renglones][1] = posArray[1][1];
                renglones++;
            }
            estComp2 = comp[cont][1];

        }
        result = (aut1.estadosfinales(estComp1) && aut2.estadosfinales(estComp2) || !(aut1.estadosfinales(estComp1)) && !(aut2.estadosfinales(estComp2)));

        if (result) {
            System.out.println("SI son equivalentes los autómatas.");
        } else {
            System.out.println("NO son equivalentes los autómatas.");
        }

        return result;
    }

}
