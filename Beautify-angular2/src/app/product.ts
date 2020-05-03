export class Product {
    productId: number;
    skuCode: string;
    name: string;
    price: number;
    description: string;
    quantityOnHand: number

    constructor(productId?: number, skuCode?: string, name?: string, price?: number, description?: string, quantityOnHand?: number) {
        this.productId = productId;
        this.skuCode = skuCode;
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantityOnHand = quantityOnHand;
    }
}
