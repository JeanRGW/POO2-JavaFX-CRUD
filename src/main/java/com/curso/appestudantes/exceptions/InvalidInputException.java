package com.curso.appestudantes.exceptions;

public class InvalidInputException extends Exception {
    public InvalidInputException(){
        super("Entrada inválida");
    }

    public InvalidInputException(String msg){
        super(msg);
    }
}
