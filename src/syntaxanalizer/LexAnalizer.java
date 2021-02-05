package syntaxanalizer;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class LexAnalizer {

    private ArrayList<Token> tokenList = new ArrayList();
    private int tokenCounter = 0;

    //EXPRESIONES REGULARES
    //GENERALES
    private final String spaceRegEx = "[\\s]+";//espacios: salto de línea, espacio y tabular
    private final String numRegEx = "[0-9]";//numérico
    private final String numRealRegEx = "[0-9.]";//numérico Real
    private final String alphaRegEx = "[a-zA-Z]";//alfabético
    private final String alphanumRegEx = "[a-zA-Z0-9_]";//alfanumérico

    //COMENTARIOS
    private final String commentLineRegEx = "[#]";//COMENTARIOS DE LÍNEA
    private final String commentParagraphRegEx = "[/][*].+[*][/]";//COMENTARIOS DE PARRAFO

    //OPERADORES
    private final String endSentenceRegEx = "[;]";//operador de cierre de sentencia
    private final String mathRegEx = "[+-/%()*]";//operadores artiméticos
    private final String relRegEx = "([<][=][>])|([<>!][=])|([<][>])|[=<>]";//operadores relacionales 
    private final String chainRegEx = "[/][*]|[*][/]|[\"']";//apertura y cierre de cadenas
    private final String delimiterRegEx = "[.,]";//delimitadores

    //TIPOS
    private final String realRegEx = "([0-9]+([.][0-9]+)?)";//racional 
    private final String stringRegEx = "([\"][^\"]*[\"])|([\'][^\']*[\'])";//cadena de texto 

    //PALABRAS RESERVADAS
    private final String[] logicWords = {
        "AND",
        "OR",
        "NOT",};//operadores lógicos 
    private final String[] keyWords = {

        "ANY",
        "AS",
        "ASC",
        "BETWEEN",
        "BY",
        "CONTAINS",
        "DELETE",
        "DESC",
        "FROM",
        "GROUP",
        "HAVING",
        "IN",
        "INSERT",
        "INTO",
        "IS",
        "LIKE",
        "LIMIT",
        "NULL",
        "ORDER",
        "SELECT",
        "SET",
        "UPDATE",
        "VALUES",
        "WHERE"
    };

    public LexAnalizer(ArrayList<Token> tokenList) {
        this.tokenList = tokenList;
    }

    public void analize(String string) {
        int state = 0;
        /*
        ESTADOS
        0:   Vacio(inicial)
        -100:  Vacio(final)
        otro:  procesando lexema 
        
        TOKENS
        -3:  Comentario de párrafo                          /*xxx...
        -2:  Comentario de línea                            //xxx xxx xxx##
        -1:  Espacio                            
        0:   Lexema desconocido (error)
        1:   Operador lógico                                AND OR NOT
        2:   Palabra reservada 
        3:   Identificador                                  empieza con alfabético y puede contener alfanumérico y _
        4:   Número                                         123.123 ó 123
        5:   String                                         "xxx xxx xxx"
        6:   Operador aritmético                            + - * / % ^ ( )
        7:   Operador lógico                                
        8:   Operador relacional                            < > <= >= = != <> <=>
        10:  Fin de sentencia                   ;
        11:  Delimitadores                                  . , 
         */
        int type = -1000;
        String lexema = "";

        ArrayList<String> lines = separateLines(string);//separa el texto por líneas en un arrayList

        for (int i = 0; i < lines.size(); i++) {//por cada línea en el texto
            for (int j = 0; j < lines.get(i).length(); j++) {//por cada caracter en la línea
                char charBefore;
                char charActive;
                char charNext;

                charActive = lines.get(i).charAt(j);

                if (j == 0) {//si es el primer caracter de la línea
                    charBefore = ' ';//asignar el anterior caracter como espacio para las comparaciones
                } else {//si NO es el último caracter de la línea
                    charBefore = lines.get(i).charAt(j - 1);//asignar el anterior caracter de la línea para las comparaciones
                }

                if (j == lines.get(i).length() - 1) {//si es el último caracter de la línea
                    charNext = ' ';//asignar el siguiente caracter como espacio para las comparaciones
                } else {//si NO es el último caracter de la línea
                    charNext = lines.get(i).charAt(j + 1);//asignar el siguiente caracter de la línea para las comparaciones
                }

                if (state == 0) {//si el estado es vacio
                    state = getInitialState(charActive);//revisar caracter activo y asignar estado
                }

                switch (state) {
                    case -2://comentario linea
                        lexema += lines.get(i).charAt(j);
                        if (j == lines.get(i).length() - 1) {//si es  el último caracter de la linea
                            type = -2;
                            state = 0;
                        }
                        break;
                    case -1://espacio
                        break;
                    case 1://identificador (inicia con alfabético)
                        lexema += lines.get(i).charAt(j);
                        if (!Pattern.matches(alphanumRegEx, String.valueOf(charNext))) {//si el siguiente caracter no es alfanumérico

                            if (isStrInArray(lexema, logicWords)) {//si es operador logico
                                type = 6;
                                state = 0;
                            } else if (isStrInArray(lexema, keyWords)) {//si es palabra reservada
                                type = 1;
                                state = 0;
                            } else {//o es identificador
                                type = 2;
                                state = 0;
                            }
                        }
                        break;
                    case 3://número
                        lexema += lines.get(i).charAt(j);

                        if (!Pattern.matches(numRealRegEx, String.valueOf(charNext))) {//si el siguiente caracter no es número o separador decimal
                            if (Pattern.matches(realRegEx, String.valueOf(lexema))) {//si el lexema es un entero
                                type = 3;
                                state = 0;
                            } else {//si el lexema es otra cosa
                                type = 0;
                                state = 0;
                            }
                        }
                        break;
                    case 4://chains
                        lexema += lines.get(i).charAt(j);
                        if ((lexema.length() > 3 && Pattern.matches(chainRegEx, String.valueOf(charBefore) + String.valueOf(charActive))) || (lexema.length() > 1 && Pattern.matches(chainRegEx, String.valueOf(charActive)))) {//si no es el primer caracter del lexema y coincide con el caracter de inicio/fin de cadena
                            if (Pattern.matches(stringRegEx, String.valueOf(lexema))) {//si la cadena es cualquier cosa encerrada entre caracteres de inicio/fin de String
                                type = 4;
                                state = 0;
                            } else if (Pattern.matches(commentParagraphRegEx, String.valueOf(lexema))) {//si la cadena es cualquier cosa encerrada entre caracteres de inicio/fin de comantario de parrafo
                                type = -3;
                                state = 0;
                            } else {//si es otra cosa
                                type = 0;
                                state = 0;
                            }
                        }
                        break;
                    case 5://simbolos
                        lexema += lines.get(i).charAt(j);

                        if (Pattern.matches(relRegEx, String.valueOf(charBefore) + String.valueOf(charActive) + String.valueOf(charNext))) {//si junto al siguiente caracter forman un operador aritmético compuesto
                            //state = 5;
                        } else if (Pattern.matches(mathRegEx, String.valueOf(charActive) + String.valueOf(charNext))) {//si junto al siguiente caracter forman un operador aritmético compuesto
                            //state = 5;
                        } else if (Pattern.matches(relRegEx, String.valueOf(charActive) + String.valueOf(charNext))) {//si junto al siguiente caracter forman un operador relacional compuesto
                            //state = 5
                        } else if (Pattern.matches(commentLineRegEx, String.valueOf(charActive))) {//si junto al siguiente caracter forman un inicio de comentario de línea
                            state = -2;
                        } else if (Pattern.matches(chainRegEx, String.valueOf(charActive) + String.valueOf(charNext))) {//si junto al siguiente caracter forman un inicio de cadena
                            state = 4;
                        } else if (Pattern.matches(mathRegEx, String.valueOf(lexema))) {//si el lexema actual forma un operador aritmético 
                            type = 5;
                            state = 0;
                        } else if (Pattern.matches(relRegEx, String.valueOf(lexema))) {//si el lexema actual forma un operador relacional 
                            type = 7;
                            state = 0;
                        } else if (Pattern.matches(endSentenceRegEx, String.valueOf(lexema))) {//si el lexema actual es el operador de fin de sentencia
                            type = 8;
                            state = 0;
                        } else if (Pattern.matches(delimiterRegEx, String.valueOf(lexema))) {//si el lexema actual es un delimitador
                            type = 9;
                            state = 0;
                        } else {
                            type = 10;
                            state = 0;
                        }
                        break;
                }

                if (state == 0) {//si el estado final es 0 el lexema esta completo y se puede añadir a la lista
                    tokenCounter++;
                    tokenList.add(new Token(tokenCounter, lexema, type, i + 1, j + 1));
                    lexema = "";
                    type = -1000;
                } else if (state == -1) {//si el estado final es 1 el lexema fue un espacio y será ignorado
                    state = 0;
                }
                //si el estado final es otro, el lexema aún está incompleto

                if (i == lines.size() - 1 && j == lines.get(i).length() - 1) {//si es el último caracter del texto
                    if (state != 0) {//si el lexma aun no esta completo
                        type = 0;//el lexema no coincide en su totalidad, puede estar incompleto
                        tokenCounter++;
                        tokenList.add(new Token(tokenCounter, lexema, type, i + 1, j + 1));
                        lexema = "";
                        type = -1000;
                    }
                    state = -100;
                }
            }
        }
    }

    public ArrayList<String> separateLines(String text) {
        String line = "";
        ArrayList<String> chain = new ArrayList<>();
        for (int i = 0; i < text.length(); i++) {//por cada caracter en el texto
            if (text.charAt(i) != '\n') {//si NO coincide con el separador
                line += String.valueOf(text.charAt(i));//concatenar en la línea
            } else {//de lo contrario
                chain.add(line);//iniciar una nueva línea
                line = "";
            }
        }
        chain.add(line);
        return chain;
    }

    public int getInitialState(char ch) {
        String character = String.valueOf(ch);
        if (Pattern.matches(spaceRegEx, character)) {
            return -1;//espacio
        } else if (Pattern.matches(alphaRegEx, character)) {
            return 1;//alfabetico
        } else if (Pattern.matches(numRegEx, character)) {
            return 3;//número
        } else if (Pattern.matches(chainRegEx, character)) {
            return 4;//cadenas (String, char, comentario de parrafo)
        } else {
            return 5;//símbolo
        }
    }

    public boolean isStrInArray(String str, String[] arr) {
        for (int k = 0; k < arr.length; k++) {//por cada elemento en el arreglo
            if (String.valueOf(arr[k]).equalsIgnoreCase(String.valueOf(str))) {//checkear si el string coincide
                return true;
            }
        }
        return false;
    }
}
