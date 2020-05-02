<<<<<<< HEAD
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
=======
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
>>>>>>> 3741fcb8c952a53501ebe874a973aba8245f2f5d
    }
}
