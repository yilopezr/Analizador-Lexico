package AnalizadorLexico;

public class PruebaAnalizadorL {
    
    public static void main(String[] args) {
        System.out.println("Analizador Léxico que devuelve los tokens con su atributo y los errores mediante un determinado archivo de texto.\n");

        String nombreArchivo = "C:\\Users\\Yahir Lopez\\Desktop\\ejemploL.txt";
        Automata Aut = new Automata(nombreArchivo);

    }
    
}
