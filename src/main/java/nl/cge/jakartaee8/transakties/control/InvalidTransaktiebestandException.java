package nl.cge.jakartaee8.transakties.control;

import javax.ejb.ApplicationException;

@ApplicationException
public class InvalidTransaktiebestandException extends RuntimeException {

    InvalidTransaktiebestandException(String msg) {
        super(msg);
    }
}
