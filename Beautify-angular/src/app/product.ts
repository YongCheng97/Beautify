export class Product 
{
    productId: number; 
    skuCode: string; 
    name: string;
    price: number; 
    description: string; 
    quantityOnHand: number
    discountPrice: number

    constructor(productId?: number, skuCode?: string, name?: string, price?: number, description?: string, quantityOnHand?: number, discountPrice?: number) 
    {
        this.productId = productId; 
        this.skuCode = skuCode; 
        this.name = name; 
        this.price = price; 
        this.description = description; 
        this.quantityOnHand = quantityOnHand; 
        this.discountPrice = discountPrice; 
    }
}
