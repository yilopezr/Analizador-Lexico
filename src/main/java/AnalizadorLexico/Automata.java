package AnalizadorLexico;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Automata {

    StringBuilder contenido;
    int estado = 0;
    int apInicial = 0;
    int apFinal = 0;
    int tokens = 0;
    int errores = 0;
    String cadena = "";
    String palabra = "";
    String palabraE = "";
    ListaEnlazada listaTokens;
    ListaEnlazada listaErrores;

    public Automata(String ruta) {
        this.contenido = new StringBuilder();
        this.listaTokens = new ListaEnlazada();
        this.listaErrores = new ListaEnlazada();
        LeerArchivo(ruta);
        Automata(cadena);
        System.out.println("");
        System.out.println("\n\tSe encontraron " + tokens + " tokens en el codigo fuente y se describen a continuación: ");
        listaTokens.imprimirTokens();
        System.out.println("\n\n\tSe encontraron " + errores + " errores en el codigo fuente y se describen a continuación: ");
        listaErrores.imprimirErrores();
    }

    void LeerArchivo(String ruta) {
        try {
            // Abre el archivo
            FileReader archivo = new FileReader(ruta);
            BufferedReader lector = new BufferedReader(archivo);

            String linea;

            // Lee y muestra cada línea del archivo
            while ((linea = lector.readLine()) != null) {
                contenido.append(linea);
            }
            System.out.println("\tLa cadena (De longitud: " + contenido.length() + ") a analizar es la siguiente:\n\t" + contenido);

            // Cierra el archivo
            lector.close();
        } catch (IOException e) {
            System.err.println("Error al abrir o leer el archivo: " + e.getMessage());
        }
        cadena = contenido.toString();
    }

    //Autómata que validara la cadena dle archivo de texto
    void Automata(String cadena) {
        //Si los apuntadores son menores a la longitud de la cadena
        if (apInicial < cadena.length() || apFinal < cadena.length()) {
            switch (estado) {
                //Estado 0
                case 0:
                    System.out.println("Entre al estado 0");
                    if (IsBlank(cadena.charAt(apInicial)) == true) {
                        //Si la cadena comienza con cero, avanzar los apuntadores
                        System.out.println("Est 0 - caracter" + apFinal + " es cero");
                        apInicial++;
                        apFinal++;
                        Automata(cadena);

                    } else if (IsDigit(cadena.charAt(apFinal)) == true) {
                        //Si es un digito, ir al estado 1 y avanzar apuntador final
                        System.out.println("Est 0 - caracter " + apFinal + " es digito");
                        apFinal++;
                        estado = 1;
                        Automata(cadena);

                    } else {
                        //Si no es blanco ni digito, generar error                    
                        System.out.println("Est 0 - caracter " + apFinal + " no se reconoce");
                        System.out.println("antes Inicial: " + apInicial + " Final: " + apFinal);
                        //Mientras no sea blanco, continuar con el error
                        while (IsBlank(cadena.charAt(apFinal+1)) == false && IsDigit(cadena.charAt(apFinal+1)) == false) {
                            apFinal++;
                        }
                        System.out.println("despues Inicial: " + apInicial + " Final: " + apFinal);
                        //Formar el error con una variable temporal
                        for (int i = apInicial; i <= apFinal; i++) {
                            palabraE += "" + cadena.charAt(i);
                        }
                        //Generar error
                        Error(palabraE, errores, "Debe comenzar con un digito");
                        System.out.println("Inicial: " + apInicial + " Final: " + apFinal);
                        Automata(cadena);
                    }
                    break;
                //Estado 1
                case 1:
                    System.out.println("Entre al estado 1");
                    //Si es blanco generar token
                    if (IsBlank(cadena.charAt(apFinal)) == true) {
                        System.out.println("Est 1 - caracter " + apFinal + " es blanco y genera token");
                        //Formar palabra con una variable temporal  
                        for (int i = apInicial; i <= apFinal; i++) {
                            palabra += "" + cadena.charAt(i);
                        }
                        System.out.println("Inicial: " + apInicial + " Final: " + apFinal);
                        //Generar token
                        Token(palabra, 257, "Números Enteros");
                        System.out.println("Inicial: " + apInicial + " Final: " + apFinal);
                        Automata(cadena);
                        //Si es digito, seguir el camino y volver al estado 1
                    } else if (IsDigit(cadena.charAt(apFinal)) == true) {
                        System.out.println("Est 1 - caracter " + apFinal + " es digito");
                        apFinal++;
                        estado = 1;
                        Automata(cadena);
                        //Si es un punto seguir el camino e ir al estado 2
                    } else if (IsDot(cadena.charAt(apFinal)) == true) {
                        System.out.println("Est 1 - caracter " + apFinal + " es un punto");
                        apFinal++;
                        estado = 2;
                        Automata(cadena);
                        //Si es exponente seguir el camino e ir al estado 4
                    } else if (IsExponent(cadena.charAt(apFinal)) == true) {
                        System.out.println("Est 1 - caracter " + apFinal + " es una E");
                        apFinal++;
                        estado = 4;
                        Automata(cadena);
                        //Si no se reconoce el caracter, generar error
                    } else {
                        System.out.println("Est 1 - caracter " + apFinal + " no se reconoce");
                        //Mientras no sea blanco, continuar con el error
                        while (IsBlank(cadena.charAt(apFinal+1)) == false && IsDigit(cadena.charAt(apFinal+1)) == false) {
                            apFinal++;
                        }
                        //Formar palabra con una variable temporal
                        for (int i = apInicial; i <= apFinal; i++) {
                            palabraE += "" + cadena.charAt(i);
                        }
                        //Generar error
                        Error(palabraE, errores, "Debe ser un digito, punto o exponente.");
                        System.out.println("Inicial: " + apInicial + " Final: " + apFinal);
                        Automata(cadena);
                    }
                    break;
                //Estado 2
                case 2:
                    System.out.println("entre al estado 2");
                    //Si es blanco generar error
                    if (IsBlank(cadena.charAt(apFinal)) == true) {
                        System.out.println("Est 2 - caracter " + apFinal + " es blanco y genera error");
                        //Formar error con una variable temporal
                        for (int i = apInicial; i <= apFinal; i++) {
                            palabraE += "" + cadena.charAt(i);
                        }
                        //Generar error
                        Error(palabraE, errores, "Debe ser un digito");
                        System.out.println("Inicial: " + apInicial + " Final: " + apFinal);
                        Automata(cadena);
                        //Si es un digito, seguir el camino y volver al estado 3
                    } else if (IsDigit(cadena.charAt(apFinal)) == true) {
                        System.out.println("Est 2 - caracter " + apFinal + " es digito");
                        apFinal++;
                        estado = 3;
                        Automata(cadena);
                        //Si no es blanco ni digito, no se reconoce y genera error
                    } else {
                        System.out.println("Est 2 - caracter " + apFinal + " no se reconoce");
                        System.out.println("Inicial: " + apInicial + " Final: " + apFinal);
                        //Mientras no haya blancos, seguir con el error
                        while (IsBlank(cadena.charAt(apFinal+1)) == false && IsDigit(cadena.charAt(apFinal+1))) {
                            apFinal++;
                        }
                        //Formar error con una variable temporal
                        for (int i = apInicial; i < apFinal; i++) {
                            palabraE += "" + cadena.charAt(i);
                        }
                        apFinal--;
                        System.out.println(palabraE);
                        System.out.println("Inicial: " + apInicial + " Final: " + apFinal);
                        //Generar error
                        Error(palabraE, errores, "EL siguiente caracter debe ser un digito");
                        Automata(cadena);
                    }
                    break;
                    //Estado 3
                case 3:
                    //SI es blanco, genera token
                    if (IsBlank(cadena.charAt(apFinal)) == true) {
                        System.out.println("Est 3 - caracter " + apFinal + " es blanco y genera token");                        
                        System.out.println("------ " + apInicial);
                        System.out.println("------ " + apFinal);
                        //Formar palabra con variable temporal
                        for (int i = apInicial; i <= apFinal; i++) {
                            palabra += "" + cadena.charAt(i);
                        }
                        //Genera token
                        Token(palabra, 258, "Números Flotantes");
                        System.out.println("Inicial: " + apInicial + " Final: " + apFinal);
                        Automata(cadena);
                        //SI es digito, seguir el camino y volver al estado 3
                    } else if (IsDigit(cadena.charAt(apFinal)) == true) {
                        System.out.println("Est 3 - caracter " + apFinal + " es digito");
                        apFinal++;
                        estado = 3;
                        Automata(cadena);
                        //Si es exponente, ir al estado 4
                    } else if (IsExponent(cadena.charAt(apFinal)) == true) {
                        System.out.println("Est 3 - caracter " + apFinal + " es exponente");
                        apFinal++;
                        estado = 4;
                        Automata(cadena);
                        //SI no es blanco, digito, ni exponente, generar error
                    } else {
                        System.out.println("Est 3 - caracter " + apFinal + " no se reconcoe");
                        for (int i = apInicial; i < apFinal; i++) {
                            palabra += "" + cadena.charAt(i);
                        }
                        //Generar Token
                        Token(palabra, 258, "Números Flotantes");
                        apFinal--;
                        apInicial = apFinal;
                        System.out.println("Inicial: " + apInicial + " Final: " + apFinal);
                        Automata(cadena);
                    }
                    break;
                    //Estado 4
                case 4:
                    //SI es blanco, generar error
                    if (IsBlank(cadena.charAt(apFinal)) == true) {
                        System.out.println("Est 4 - caracter " + apFinal + " es blanco y error");
                        //Formar palabra con variable temporal
                        for (int i = apInicial; i <= apFinal; i++) {
                            palabraE += "" + cadena.charAt(i);
                        }
                        //Generar error
                        Error(palabraE, errores, "Error");
                        System.out.println("Inicial: " + apInicial + " Final: " + apFinal);
                        Automata(cadena);
                        //Si es signo de menor o amyor, ir al estado 5
                    } else if (IsPlusMinus(cadena.charAt(apFinal)) == true) {
                        System.out.println("Est 4 - caracter " + apFinal + " es mas o menos");
                        apFinal++;
                        estado = 5;
                        Automata(cadena);
                        //SI es digito, ir al estado 6
                    } else if (IsDigit(cadena.charAt(apFinal)) == true) {
                        System.out.println("Est 4 - caracter " + apFinal + " es digito");
                        apFinal++;
                        estado = 6;
                        Automata(cadena);
                        //SI no es signo o digito, generar error
                    } else {
                        System.out.println("Est 4 - caracter " + apFinal + " no se reconoce");
                        //Mientras no sea blanco, generar error
                        while (IsBlank(cadena.charAt(apFinal+1)) == false && IsDigit(cadena.charAt(apFinal+1)) == false) {
                            apFinal++;
                        }
                        //Formar palabra con variable temporal
                        for (int i = apInicial; i <= apFinal; i++) {
                            palabraE += "" + cadena.charAt(i);
                        }
                        //Generar error
                        Error(palabraE, errores, "Error");
                        System.out.println("Inicial: " + apInicial + " Final: " + apFinal);
                        Automata(cadena);
                    }
                    break;
                    //Estado 5
                case 5:
                    //SI es blanco, generar error
                    if (IsBlank(cadena.charAt(apFinal)) == true) {
                        System.out.println("Est 5 - caracter " + apFinal + " es blanco y genera error");
                        //Formar error con una variable temporal
                        for (int i = apInicial; i <= apFinal; i++) {
                            palabraE += "" + cadena.charAt(i);
                        }
                        //Generar error
                        Error(palabraE, 1, "Error");
                        System.out.println("Inicial: " + apInicial + " Final: " + apFinal);
                        Automata(cadena);
                        //Si es digito, seguir el camino y e ir al estado 6
                    } else if (IsDigit(cadena.charAt(apFinal)) == true) {
                        System.out.println("Est 5 - caracter " + apFinal + " es digito");
                        apFinal++;
                        estado = 6;
                        Automata(cadena);
                        //Si no es digito, generar error
                    } else {
                        System.out.println("Est 5 - caracter " + apFinal + " no se reconoce");
                        //Mientras no sea blanco, continuar con el error
                        while (IsBlank(cadena.charAt(apFinal+1)) == false && IsDigit(cadena.charAt(apFinal+1)) == false) {
                            apFinal++;
                        }
                        //Formar palabra con variable temporal
                        for (int i = apInicial; i < apFinal; i++) {
                            palabraE += "" + cadena.charAt(i);
                        }
                        apFinal--;
                        //Generar error
                        System.out.println("antes Inicial: " + apInicial + " Final: " + apFinal);
                        Error(palabraE, errores, "El último caracter debe ser un digito");
                        System.out.println("depsues Inicial: " + apInicial + " Final: " + apFinal);
                        Automata(cadena);
                    }
                    break;
                    //Estado 6
                case 6:
                    //Si es blanco, genera token
                    if (IsBlank(cadena.charAt(apFinal)) == true) {
                        System.out.println("Est 6 - caracter " + apFinal + " es blanco y genera token");                        
                        System.out.println("Inicial: " + apInicial + " Final: " + apFinal);
                        //Formar palabra con variable temporal
                        for (int i = apInicial; i <= apFinal; i++) {
                            palabra += "" + cadena.charAt(i);
                        }
                        //Generar token
                        Token(palabra, 259, "Números Exponenciales");
                        System.out.println("Inicial: " + apInicial + " Final: " + apFinal);
                        Automata(cadena);
                        //Si es digito, seguir el camino y volver al estado 6
                    } else if (IsDigit(cadena.charAt(apFinal)) == true) {
                        System.out.println("Est 6 - caracter " + apFinal + " es digito");
                        apFinal++;
                        estado = 6;
                        Automata(cadena);
                        //Si no es digito, genera error
                    } else {
                        System.out.println("Est 6 - caracter " + apFinal + " no se reconoce");
                        //Mientras no sea blanco, continuar con el error
                        while (IsBlank(cadena.charAt(apFinal+1)) == false && IsDigit(cadena.charAt(apFinal+1))) {
                            apFinal++;
                        }
                        //Formar palabra con variable temporal
                        for (int i = apInicial; i < apFinal; i++) {
                            palabra += "" + cadena.charAt(i);
                        }
                        apFinal--;
                        //Generar token
                        System.out.println("antes token Inicial: " + apInicial + " Final: " + apFinal);
                        Token(palabra, 259, "Números Exponenciales");
                        System.out.println("despues token : " + apInicial + " Final: " + apFinal);
                        apFinal--;
                        apFinal=apInicial;                        
                        System.out.println("despues decremento Inicial: " + apInicial + " Final: " + apFinal);
                        Automata(cadena);
                    }
            }

        }
    }

    //Generar el token con el lexema, atributo y clasificación
    void Token(String lexema, int atributo, String tipo) {
        try {
            listaTokens.agregarToken(lexema, atributo, tipo);

            apFinal++;
            apInicial = apFinal;
            palabra = null;
            palabra = "";
            estado = 0;
            System.out.println("Se genero el token " + tokens + " con exito");
            tokens++;
        } catch (Exception e) {
            System.err.println("");
        }
    }

    void Error(String error, int numError, String tipo) {        
        try {
            numError++;
            listaErrores.agregarError(error, numError, tipo);

            apFinal++;
            apInicial = apFinal;
            palabraE = null;
            palabraE = "";
            estado = 0;
            System.out.println("Se genero error " + errores + " con exito");
            errores++;
        } catch (Exception e) {
            System.err.println("");
        }
    }

    private boolean IsBlank(char caracter) {
        if ((int) caracter == 32) {
            return true;
        }
        return false;
    }

    private boolean IsDigit(char caracter) {
        if ((int) caracter >= 48 && (int) caracter <= 57) {
            return true;
        }
        return false;
    }

    private boolean IsDot(char caracter) {
        if ((int) caracter == 46) {
            return true;
        }
        return false;
    }

    private boolean IsExponent(char caracter) {
        if ((int) caracter == 69) {
            return true;
        }
        return false;
    }

    private boolean IsPlusMinus(char caracter) {
        if ((int) caracter == 43 || (int) caracter == 45) {
            return true;
        }
        return false;
    }

}
