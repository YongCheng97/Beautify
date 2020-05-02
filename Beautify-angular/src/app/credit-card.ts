export class CreditCard {

    creditCardId: number; 
    type: string;
    cardName: string; 
    cardNumber: string;
    expiryDate: string

    constructor(creditCardId?: number, type?: string, cardName?: string, cardNumber?: string, expiryDate?: string) {
        this.creditCardId = creditCardId; 
        this.type = type; 
        this.cardName = cardName; 
        this.cardNumber = cardNumber; 
        this.expiryDate = expiryDate; 
    }

}