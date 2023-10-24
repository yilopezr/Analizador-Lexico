package Equivalencia;

public class prueba {

    public static void main(String[] args) {
        int[][] trans1 = {{0, 0, 2},
        {0, 1, 1},
        {1, 0, 1},
        {1, 1, 1},
        {2, 0, 0},
        {2, 1, 1}};

        String[] est1 = {"0", "1", "2"};
        char[] alf1 = {'a', 'b'};
        int[] estFinal1 = {0, 2};

        int[][] trans2 = {{0, 0, 0},
        {0, 1, 1},
        {1, 0, 1},
        {1, 1, 1}};

        String[] est2 = {"0", "1"};
        char[] alf2 = {'a', 'b'};
        int[] estFinal2 = {0};

        automata A1 = new automata(est1, alf1, trans1, "0", estFinal1);
        automata A2 = new automata(est2, alf2, trans2, "0", estFinal2);

        equivalencia.calcular(A1, A2);
    }

}
