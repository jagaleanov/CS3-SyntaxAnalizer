package syntaxanalizer;

import java.util.ArrayList;

public class SyntaxAnalizer {

    private ArrayList<String> errorsList = new ArrayList();

    public SyntaxAnalizer(ArrayList<Token> tokenList) {
        this.analizer(tokenList);
    }

    public void analizer(ArrayList<Token> tokenList) {
        String state = "";
        String aux = "";
        int contador1 = 0;
        int contador2 = 0;

        for (int i = 0; i < tokenList.size(); i++) {
            Token actualToken = tokenList.get(i);
            Token nextToken = null;

            if (i != tokenList.size() - 1) {
                nextToken = tokenList.get(i + 1);
            }

            if (actualToken.getType() == -3
                    || actualToken.getType() == -2
                    || actualToken.getType() == -3
                    || actualToken.getType() == -2) {
                //OK
            } else if (actualToken.getLexema().equalsIgnoreCase("SELECT")) {//SELECT
                state = "SELECT";
                if (nextToken.getType() == 2) {
                    //OK
                } else if (nextToken.getLexema().equalsIgnoreCase("*")) {
                    //OK
                } else {
                    System.out.println("error01");
                    this.setError(nextToken);
                }

            } else if (actualToken.getLexema().equalsIgnoreCase("*")) {//*
                if ("SELECT".equals(state)) {
                    if (nextToken.getLexema().equalsIgnoreCase("FROM")) {
                        //OK
                    } else {
                        System.out.println("error02");
                        this.setError(nextToken);
                    }
                } else {
                    System.out.println("error03");
                    this.setError(nextToken);
                }
            } else if (actualToken.getLexema().equalsIgnoreCase("FROM")) {//FROM
                state = "FROM";
                if (nextToken == null) {
                    System.out.println("error03a");
                    this.setError(actualToken);
                } else if (nextToken.getType() == 2) {
                    //OK
                } else {
                    System.out.println("error04");
                    this.setError(nextToken);
                }
            } else if (actualToken.getLexema().equalsIgnoreCase("ORDER")) {//ORDER
                state = "ORDER";
                if (nextToken.getLexema().equalsIgnoreCase("BY")) {
                    //OK
                } else {
                    System.out.println("error05");
                    this.setError(nextToken);
                }

            } else if (actualToken.getLexema().equalsIgnoreCase("BY")) {//BY
                if (nextToken.getType() == 2) {
                    //OK
                } else {
                    System.out.println("error06");
                    this.setError(nextToken);
                }
            } else if (actualToken.getLexema().equalsIgnoreCase("ASC") || actualToken.getLexema().equalsIgnoreCase("DESC")) {
                if (nextToken == null) {//ASC DESC
                    //OK
                } else if (nextToken.getLexema().equalsIgnoreCase(",")) {
                    //OK
                } else if (nextToken.getType() == 8) {
                    //OK
                } else {
                    System.out.println("error07");
                    this.setError(nextToken);
                }
            } else if (actualToken.getLexema().equalsIgnoreCase("INSERT")) {//INSERT
                state = "INSERT";
                if (nextToken.getLexema().equalsIgnoreCase("INTO")) {
                    //OK
                } else {
                    System.out.println("error08");
                    this.setError(nextToken);
                }
            } else if (actualToken.getLexema().equalsIgnoreCase("INTO")) {//INTO
                if (nextToken.getType() == 2) {
                    //OK
                } else {
                    System.out.println("error09");
                    this.setError(nextToken);
                }
            } else if (actualToken.getLexema().equalsIgnoreCase("UPDATE")) {//UPDATE
                state = "UPDATE";
                if (nextToken.getType() == 2) {
                    //OK
                } else {
                    System.out.println("error10");
                    this.setError(nextToken);
                }

            } else if (actualToken.getLexema().equalsIgnoreCase("DELETE")) {//DELETE
                state = "DELETE";
                if (nextToken.getLexema().equalsIgnoreCase("FROM")) {
                    //OK
                } else {
                    System.out.println("error11");
                    this.setError(nextToken);
                }
            } else if (actualToken.getType() == 2) {//IDENTIFICADORES
                System.out.println(state);
                if ("SELECT".equals(state)) {
                    if (nextToken == null) {
                        System.out.println("error12");
                        this.setError(actualToken);
                    } else if (nextToken.getLexema().equalsIgnoreCase("FROM")) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase(",")) {
                        //OK
                    } else {
                        System.out.println("error13");
                        this.setError(nextToken);
                    }
                } else if ("FROM".equals(state)) {
                    if (nextToken == null) {
                        //OK
                    } else if (nextToken.getType() == 2) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase("WHERE")) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase("ORDER")) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase(";")) {
                        //OK
                    } else {
                        System.out.println("error14");
                        this.setError(nextToken);
                    }
                } else if ("ORDER".equals(state)) {
                    if (nextToken == null) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase("FROM")) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase("ASC")) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase("DESC")) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase(",")) {
                        //OK
                    } else {
                        System.out.println("error15");
                        this.setError(nextToken);
                    }
                } else if ("DELETE".equals(state)) {
                    if (nextToken == null) {
                        //OK
                    } else if (nextToken.getType() == 8) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase("WHERE")) {
                        //OK
                    } else {
                        System.out.println("error16");
                        this.setError(nextToken);
                    }
                } else if ("INSERT".equals(state)) {
                    state = "INSERT2";
                    if (nextToken == null) {
                        System.out.println("error17");
                        this.setError(actualToken);
                    } else if (nextToken.getLexema().equalsIgnoreCase("(")) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase("VALUES")) {
                        //OK
                    } else {
                        System.out.println("error18");
                        this.setError(nextToken);
                    }
                } else if ("INSERT2".equals(state) || "INSERT3".equals(state)) {
                    contador1++;
                    if (nextToken == null) {
                        System.out.println("error19");
                        this.setError(actualToken);
                    } else if (nextToken.getLexema().equalsIgnoreCase(",")) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase(")")) {
                        //OK
                    } else {
                        System.out.println("error20");
                        this.setError(nextToken);
                    }
                } else if ("UPDATE".equals(state)) {

                    if (nextToken == null) {
                        System.out.println("error21");
                        this.setError(actualToken);
                    } else if (nextToken.getLexema().equalsIgnoreCase("SET")) {
                        //OK
                    } else {
                        System.out.println("error22");
                        this.setError(nextToken);
                    }
                } else if ("SET".equals(state)) {
                    if (nextToken == null) {
                        System.out.println("error23");
                        this.setError(actualToken);
                    } else if (nextToken.getLexema().equalsIgnoreCase("=")) {
                        //OK
                    } else {
                        System.out.println("error24");
                        this.setError(nextToken);
                    }
                } else if ("WHERE".equals(state)) {

                    if (nextToken == null) {
                        System.out.println("error25");
                        this.setError(actualToken);
                    } else if (nextToken.getType() == 8) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase("IS")) {
                        //OK
                    } else if (nextToken.getType() == 7) {
                        //OK
                    } else {
                        System.out.println("error26");
                        this.setError(nextToken);
                    }

                }
            } else if (actualToken.getLexema().equalsIgnoreCase(",")) {//,
                if (nextToken == null) {
                    System.out.println("error27");
                    this.setError(actualToken);
                } else if ("INSERT2".equals(state)) {
                    if (nextToken.getType() == 2) {
                        //OK
                    } else {
                        System.out.println("error28");
                        this.setError(nextToken);
                    }
                } else if ("INSERT3".equals(state)) {
                    if (nextToken.getType() == 4 || nextToken.getType() == 3) {
                        //OK
                    } else {
                        System.out.println("error29");
                        this.setError(nextToken);
                    }
                } else if ("SET".equals(state)) {
                    if (nextToken.getType() == 2) {
                        //OK
                    } else {
                        System.out.println("error29a");
                        this.setError(nextToken);
                    }
                } else {
                    System.out.println("error30");
                    this.setError(nextToken);
                }
            } else if (actualToken.getLexema().equalsIgnoreCase("(")) {//(
                contador1 = 0;
                if ("INSERT2".equals(state)) {
                    aux = "INSERT2";
                    if (nextToken.getType() == 2) {
                        //OK
                    } else {
                        System.out.println("error31");
                        this.setError(nextToken);
                    }
                } else if ("INSERT3".equals(state)) {
                    if (nextToken.getType() == 4 || nextToken.getType() == 3) {
                        //OK
                    } else {
                        System.out.println("error32");
                        this.setError(nextToken);
                    }
                } else {
                    System.out.println("error33");
                    this.setError(nextToken);
                }
            } else if (actualToken.getLexema().equalsIgnoreCase(")")) {//)
                if (state != "INSERT3" && nextToken == null) {
                    System.out.println("error34");
                    this.setError(actualToken);
                } else if (state == "INSERT2") {
                    if (nextToken.getLexema().equalsIgnoreCase("VALUES")) {
                        //OK
                    } else {
                        System.out.println("error35");
                        this.setError(nextToken);
                    }
                } else if (state == "INSERT3") {
                    System.out.println(contador2);
                    System.out.println(contador1);

                    if (nextToken == null) {
                        //OK
                    } else if (aux == "INSERT2") {

                        if (contador2 == contador1) {
                            //OK
                        } else {
                            System.out.println("error36");
                            this.setError(nextToken);
                        }
                        aux = "";
                    }

                    if (nextToken == null) {
                        //OK
                    } else if (nextToken.getType() == 8) {
                        //OK
                    } else {
                        System.out.println("error36a");
                        this.setError(nextToken);
                    }
                }
            } else if (actualToken.getLexema().equalsIgnoreCase("VALUES")) {//VALUES
                state = "INSERT3";
                contador2 = contador1;
                contador1 = 0;
                if (nextToken == null) {
                    System.out.println("error37");
                    this.setError(actualToken);
                } else if (nextToken.getLexema().equalsIgnoreCase("(")) {
                    //OK
                } else {
                    System.out.println("error38");
                    this.setError(nextToken);
                }
            } else if (actualToken.getLexema().equalsIgnoreCase("SET")) {//SET
                state = "SET";
                if (nextToken == null) {
                    System.out.println("error39");
                    this.setError(actualToken);
                } else if (nextToken.getType() == 2) {
                    //OK
                } else {
                    System.out.println("error40");
                    this.setError(nextToken);
                }
            } else if (actualToken.getLexema().equalsIgnoreCase("=")) {//=
                if (nextToken == null) {
                    System.out.println("error41");
                    this.setError(actualToken);
                } else if (nextToken.getType() == 4 || nextToken.getType() == 3) {
                    //OK
                } else {
                    System.out.println("error42");
                    this.setError(nextToken);
                }
            } else if (actualToken.getType() == 4 || actualToken.getType() == 3) {//NÚMERO O STRING
                if (state != "SET" && state != "WHERE" && nextToken == null) {
                    System.out.println("error43");
                    this.setError(actualToken);
                } else if (state == "WHERE") {
                    if (nextToken == null) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase("AND")) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase("OR")) {
                        //OK
                    } else if (nextToken.getType() == 8) {
                        //OK
                    } else {
                        System.out.println("error43a");
                        this.setError(nextToken);
                    }
                } else if (state == "INSERT3") {
                    contador1++;
                    if (nextToken == null) {
                        System.out.println("error44");
                        this.setError(nextToken);
                    } else if (nextToken.getLexema().equalsIgnoreCase(")")) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase(",")) {
                        //OK
                    } else {
                        System.out.println("error44a");
                        this.setError(nextToken);
                    }
                } else if (state == "SET") {
                    if (nextToken == null) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase(",")) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase(";")) {
                        //OK
                    } else if (nextToken.getLexema().equalsIgnoreCase("WHERE")) {
                        //OK
                    } else {
                        System.out.println("error44b");
                        this.setError(nextToken);
                    }
                } else if (nextToken.getLexema().equalsIgnoreCase("WHERE")) {
                    //OK
                } else if (nextToken.getType() == 8) {
                    //OK
                } else if (nextToken.getLexema().equalsIgnoreCase(",")) {
                    //OK
                } else {
                    System.out.println("error45");
                    this.setError(nextToken);
                }
            } else if (actualToken.getType() == 8) {//;
                if (nextToken == null) {
                    //OK
                } else if (nextToken.getLexema().equalsIgnoreCase("SELECT")) {
                    //OK
                } else if (nextToken.getLexema().equalsIgnoreCase("UPDATE")) {
                    //OK
                } else if (nextToken.getLexema().equalsIgnoreCase("DELETE")) {
                    //OK
                } else if (nextToken.getLexema().equalsIgnoreCase("INSERT")) {
                    //OK
                } else {
                    System.out.println("error46");
                    this.setError(nextToken);
                }
            } else if (actualToken.getLexema().equalsIgnoreCase("WHERE")) {//WHERE
                state = "WHERE";
                if (nextToken == null) {
                    System.out.println("error47");
                    this.setError(actualToken);
                } else if (nextToken.getType() == 2) {
                    //OK
                } else {
                    System.out.println("error48");
                    this.setError(nextToken);
                }
            } else if (actualToken.getType() == 7) {//OPERADOR RELACIONAL
                if (nextToken == null) {
                    System.out.println("error49");
                    this.setError(actualToken);
                } else if (nextToken.getType() == 4 || nextToken.getType() == 3) {
                    //OK
                } else {
                    System.out.println("error50");
                    this.setError(nextToken);
                }
            } else if (actualToken.getLexema().equalsIgnoreCase("IS")) {
                if (nextToken == null) {
                    System.out.println("error51");
                    this.setError(actualToken);
                } else if (nextToken.getLexema().equalsIgnoreCase("NOT")) {
                    //OK
                } else if (nextToken.getLexema().equalsIgnoreCase("NULL")) {
                    //OK
                } else {
                    System.out.println("error52");
                    this.setError(nextToken);
                }
            } else if (actualToken.getLexema().equalsIgnoreCase("NOT")) {

                if (nextToken == null) {
                    System.out.println("error53");
                    this.setError(actualToken);
                } else if (nextToken.getLexema().equalsIgnoreCase("NULL")) {
                    //OK
                } else {
                    System.out.println("error54");
                    this.setError(nextToken);
                }
            } else if (actualToken.getLexema().equalsIgnoreCase("NULL")) {
                if (nextToken == null) {
                    //OK
                } else if (nextToken.getLexema().equalsIgnoreCase("AND") || nextToken.getLexema().equalsIgnoreCase("OR")) {
                    //OK
                } else {
                    System.out.println("error55");
                    this.setError(nextToken);
                }
            } else if (actualToken.getLexema().equalsIgnoreCase("AND") || actualToken.getLexema().equalsIgnoreCase("OR")) {
                if (nextToken == null) {
                    System.out.println("error56");
                    this.setError(actualToken);
                } else if (nextToken.getType() == 2) {
                    //OK
                } else {
                    System.out.println("error57");
                    this.setError(nextToken);
                }
            } else {
                System.out.println("error58");
                this.setError(actualToken);
            }
        }
    }

    public void setError(Token token) {
        errorsList.add("Error de sintaxis en la línea " + token.getRow() + " en la columna " + token.getCol() + ".\n");
    }

    public ArrayList<String> getErrorList() {
        return errorsList;
    }
}
