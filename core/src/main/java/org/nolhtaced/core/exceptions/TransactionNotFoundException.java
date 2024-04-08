package org.nolhtaced.core.exceptions;

public class TransactionNotFoundException extends Exception {
    public TransactionNotFoundException() {
        super("Transaction not found");
    }
}
