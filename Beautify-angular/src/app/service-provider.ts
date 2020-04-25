export class ServiceProvider {
    serviceProviderId: number;
    name: string;
    email: string;
    password: string;
    address: string;
    openingHours: Date;
    closingHours: Date;
    certification: File;
    isApproved: boolean;

    constructor(serviceProviderId?: number, name?: string, email?: string, password?: string, address?: string, openingHours?: Date, closingHours?: Date, certification?: File, isApproved?: boolean) {
        this.serviceProviderId = serviceProviderId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.openingHours = openingHours;
        this.closingHours = closingHours;
        this.certification = certification;
        this.isApproved = isApproved;
    }
}
