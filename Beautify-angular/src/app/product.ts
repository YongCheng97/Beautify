export class Product {
    productId: number;
    name: string;
    price: number;
    description: string;
    quantityOnHand: number;
    
    constructor(productId?: number, name?: string, price?: number, description?: string, quantityOnHand?: number) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantityOnHand = quantityOnHand;
    }
}
