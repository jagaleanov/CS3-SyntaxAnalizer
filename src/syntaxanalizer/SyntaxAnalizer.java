/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntaxanalizer;

import java.util.ArrayList;

/**
 *
 * @author jgale
 */
public class SyntaxAnalizer {

    private ArrayList<String> errorsList = new ArrayList();

    public SyntaxAnalizer(ArrayList<Token> tokenList) {
        this.analizer(tokenList);
    }

    public void analizer(ArrayList<Token> tokenList) {

        for (int i = 0; i < tokenList.size(); i++) {
            Token actualToken = tokenList.get(i);
            Token nextToken = null;
            Token backToken = null;
            String state = "";
            int contador1 = 0;
            int aux = 0;

            if (i != tokenList.size() - 1) {
                nextToken = tokenList.get(i + 1);
            }

            if (i != 0) {
                backToken = tokenList.get(i - 1);
            }

            /**
             *
             */
            if (actualToken.getLexema().equalsIgnoreCase("SELECT")) {//SELECT
                state = "SELECT";
                if (nextToken.getType() == 2) {
                    //OK
                } else if (nextToken.getLexema().equalsIgnoreCase("*")) {
                    //OK
                } else {
                    errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                }

            } else if (actualToken.getLexema().equalsIgnoreCase("FROM")) {//FROM
                state = "FROM";
                if (nextToken.getType() == 2) {
                    //OK
                } else {
                    errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                }
            } else if (actualToken.getLexema().equalsIgnoreCase("WHERE")) {//WHERE

            } else if (actualToken.getLexema().equalsIgnoreCase("ORDER")) {//ORDER
                state = "ORDER";
                if (nextToken.getLexema().equalsIgnoreCase("BY")) {
                    //OK
                } else {
                    errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                }

            } else if (actualToken.getLexema().equalsIgnoreCase("BY")) {//BY
                if (nextToken.getType() == 2) {
                    //OK
                } else {
                    errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                }
            } else if (actualToken.getLexema().equalsIgnoreCase("ASC") || actualToken.getLexema().equalsIgnoreCase("DESC")) {
                if (nextToken == null) {//ASC DESC
                    //OK
                } else if (nextToken.getLexema().equalsIgnoreCase(",")) {
                    //OK
                } else if (nextToken.getLexema().equalsIgnoreCase(";")) {
                    //OK
                } else {
                    errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                }
            } else if (actualToken.getLexema().equalsIgnoreCase("INSERT")) {//INSERT
                state = "INSERT";
                if (nextToken.getLexema().equalsIgnoreCase("INTO")) {
                    //OK
                } else {
                    errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                }
            } else if (actualToken.getLexema().equalsIgnoreCase("INTO")) {//INTO
                if (nextToken.getType() == 2) {
                    //OK
                } else {
                    errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                }
            } else if (actualToken.getLexema().equalsIgnoreCase("UPDATE")) {//UPDATE
                state = "UPDATE";
                if (nextToken.getType() == 2) {
                    //OK
                } else {
                    errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                }

            } else if (actualToken.getLexema().equalsIgnoreCase("DELETE")) {//DELETE
                state = "DELETE";
                if (nextToken.getLexema().equalsIgnoreCase("FROM")) {
                    //OK
                } else {
                    errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                }
            } else if (actualToken.getType() == 2) {//IDENTIFICADORES
                if ("SELECT".equals(state)) {
                    if (nextToken.getLexema().equalsIgnoreCase("FROM")) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase(",")) {
                        //OK
                    } else {
                        errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                    }
                } else if ("FROM".equals(state)) {
                    if (nextToken.getLexema().equalsIgnoreCase(";")) {
                        //OK
                    } else if (nextToken == null) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase("WHERE")) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase("ORDER")) {
                        //OK
                    } else {
                        errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                    }
                } else if ("ORDER".equals(state)) {
                    if (nextToken.getLexema().equalsIgnoreCase(";")) {
                        //OK
                    } else if (nextToken == null) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase("ASC")) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase("DESC")) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase(",")) {
                        //OK
                    } else {
                        errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                    }
                } else if ("DELETE".equals(state)) {
                    if (nextToken.getLexema().equalsIgnoreCase(";")) {
                        //OK
                    } else if (nextToken == null) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase("WHERE")) {
                        //OK
                    } else {
                        errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                    }
                } else if ("INSERT".equals(state)) {
                    state = "INSERT2";
                    if (nextToken.getLexema().equalsIgnoreCase("(")) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase("VALUES")) {
                        //OK
                    } else {
                        errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                    }
                } else if ("INSERT2".equals(state) || "INSERT3".equals(state)) {
                    contador1++;
                    if (nextToken.getLexema().equalsIgnoreCase(",")) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase(")")) {
                        //OK
                    } else {
                        errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                    }
                } else if ("UPDATE".equals(state)) {

                    if (nextToken.getLexema().equalsIgnoreCase("SET")) {
                        //OK
                    } else {
                        errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                    }
                } else if ("SET".equals(state)) {

                    if (nextToken.getLexema().equalsIgnoreCase("=")) {
                        //OK
                    } else {
                        errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                    }
                }
            } else if (actualToken.getLexema().equalsIgnoreCase(",")) {//COMA
                if (nextToken.getType() == 2) {
                    //OK
                } else {
                    errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                }
            } else if (actualToken.getLexema().equalsIgnoreCase("(")) {//(
                contador1 = 0;
                if (nextToken.getType() == 2) {
                    //OK

                } else {
                    errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                }
            } else if (actualToken.getLexema().equalsIgnoreCase(")")) {//)
                if (state == "INSERT2") {
                    if (nextToken.getLexema().equalsIgnoreCase("VALUES")) {
                        //OK
                    } else {
                        errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                    }
                } else if (state == "INSERT3") {

                    if (aux == contador1) {
                        //OK
                    } else {
                        errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                    }

                    if (nextToken.getLexema().equalsIgnoreCase(";")) {
                        //OK
                    } else if (nextToken == null) {
                        //OK
                    } else {
                        errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                    }
                }
            } else if (actualToken.getLexema().equalsIgnoreCase("VALUES")) {//VALUES
                state = "INSERT3";
                aux = contador1;
                contador1 = 0;
                if (nextToken.getLexema().equalsIgnoreCase("(")) {
                    //OK
                } else {
                    errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                }
            } else if (actualToken.getLexema().equalsIgnoreCase("SET")) {//SET
                state = "SET";
                if (nextToken.getType() == 2) {
                    //OK
                } else {
                    errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                }
            } else if (actualToken.getLexema().equalsIgnoreCase("=")) {//=
                state = "SET";
                if (nextToken.getType() == 5 || nextToken.getType() == 3) {
                    //OK
                } else {
                    errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                }
            } else if (actualToken.getType() == 5 || actualToken.getType() == 3) {//NÚMERO O STRING
                if (nextToken.getLexema().equalsIgnoreCase("WHERE")) {
                    //OK
                } else if (nextToken.getLexema().equalsIgnoreCase(";")) {
                    //OK
                } else if (nextToken.getLexema().equalsIgnoreCase(",")) {
                    //OK
                } else if (nextToken == null) {
                    //OK
                } else {
                    errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                }
            } else if (actualToken.getLexema().equalsIgnoreCase(";")) {//;
                if (nextToken.getLexema().equalsIgnoreCase("INSERT")) {
                    //OK
                } else if (nextToken.getLexema().equalsIgnoreCase("SELECT")) {
                    //OK
                } else if (nextToken.getLexema().equalsIgnoreCase("UPDATE")) {
                    //OK
                } else if (nextToken.getLexema().equalsIgnoreCase("DELETE")) {
                    //OK
                } else if (nextToken == null) {
                    //OK
                } else {
                    errorsList.add("Error en la línea " + nextToken.getRow() + " en la columna " + nextToken.getCol() + ".");
                }
            }
        }

    }

    public ArrayList<String> getErrorList() {
        return errorsList;
    }
}
