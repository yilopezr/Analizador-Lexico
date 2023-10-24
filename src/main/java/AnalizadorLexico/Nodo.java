
package AnalizadorLexico;

public class Nodo {
    String lexema;
    int atributo;
    String tipo;
    Nodo siguiente;

    public Nodo(String lexema, int atributo, String tipo) {
        this.lexema = lexema;
        this.atributo = atributo;
        this.tipo = tipo;
        this.siguiente = null;
    }
}

