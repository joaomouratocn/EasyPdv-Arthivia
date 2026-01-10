package br.com.arthivia.api.util;

public final class Enums {

    private Enums(){}

    public enum UserRole {
        ADMIN,
        USER
    }

    public enum SaleStatus {
        OPEN,
        FINISHED,
        CANCELED
    }

    public enum PaymentType {
        MONEY,
        PIX,
        DEBIT_CARD,
        CREDIT_CARD,
        VOUCHER,
        TICKET
    }

    public enum SpunStatus {
        OPEN,
        PAY,
        LATE
    }

    public enum MovementType {
        ENTRY,
        EXIT_SALE,
        ADJUSTMENT,
        LOSS
    }
}
