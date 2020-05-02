export class Service
{
    serviceId: number; 
    serviceName: string; 
    description: string;
    price: string;

    constructor(serviceId?: number, serviceName?: string, description?: string, price?: string) 
    {
        this.serviceId = serviceId; 
        this.serviceName = serviceName; 
        this.description = description;  
        this.price = price;
    }
}
