import { Product } from './product'; 
import { Service } from './service'

export class Promotion {

    promotionId: number; 
    promoCode: string; 
    name: string; 
    discountRate: number;
    startDate: Date;
    endDate: Date; 
    product: Product;
    service: Service; 

    constructor(promotionId?: number, promoCode?: string, name?: string, discountRate?: number, startDate?: Date, endDate?: Date) 
    {
        this.promotionId = promotionId; 
        this.promoCode = promoCode; 
        this.name = name; 
        this.discountRate = discountRate;
        this.startDate = startDate;
        this.endDate = endDate; 
    }

}
