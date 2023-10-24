
package AnalizadorLexico;

public class ListaEnlazada {
    Nodo inicio;
    Nodo fin;

    public ListaEnlazada() {
        inicio = null;
        fin = null;
    }

    public void agregarToken(String lexema, int atributo, String tipo) {
        Nodo nodoTokens = new Nodo(lexema, atributo, tipo);
        if (inicio == null) {
            inicio = nodoTokens;
            fin = nodoTokens;
        } else {
            fin.siguiente = nodoTokens;
            fin = nodoTokens;
        }
    }
    
    public void agregarError(String error, int numError, String tipo) {
        Nodo nodoErrores = new Nodo(error, numError, tipo);
        if (inicio == null) {
            inicio = nodoErrores;
            fin = nodoErrores;
        } else {
            fin.siguiente = nodoErrores;
            fin = nodoErrores;
        }
    }
    
    public void imprimirTokens() {
        Nodo actual = inicio;
        System.out.println("\t---------------------------------------------------------");
        System.out.println("\tLexema \t\tAtributo \tClasificación");
        System.out.println("\t---------------------------------------------------------");
        while (actual != null) {
            System.out.print("\t " + actual.lexema);
            System.out.print("\t\t " + actual.atributo);
            System.out.print("\t\t " + actual.tipo);
            System.out.println();
            actual = actual.siguiente;
        }
        System.out.println("\t---------------------------------------------------------");
    }  
    
    public void imprimirErrores() {
        Nodo actual = inicio;
        System.out.println("\t---------------------------------------------------------");
        System.out.println("\tError \t\tNúmero \t\tDescripción");
        System.out.println("\t---------------------------------------------------------");
        while (actual != null) {
            System.out.print("\t" + actual.lexema);            
            System.out.print("\t\t " + actual.atributo);
            System.out.print("\t\t " + actual.tipo);
            System.out.println();
            actual = actual.siguiente;
        }
        System.out.println("\t---------------------------------------------------------");
    }
}

