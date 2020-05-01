export class Service
{
    serviceId: number; 
    serviceName: string; 
    serviceDescription: string;
    discountPrice: number

    constructor(serviceId?: number, serviceName?: string, serviceDescription?: string, discountPrice?: number) 
    {
        this.serviceId = serviceId; 
        this.serviceName = serviceName; 
        this.serviceDescription = serviceDescription;  
        this.discountPrice = discountPrice; 
    }
}
