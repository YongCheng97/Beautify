export class Service
{
    serviceId: number; 
    serviceName: string; 
    serviceDescription: string;

    constructor(serviceId?: number, serviceName?: string, serviceDescription?: string) 
    {
        this.serviceId = serviceId; 
        this.serviceName = serviceName; 
        this.serviceDescription = serviceDescription;  
    }
}
