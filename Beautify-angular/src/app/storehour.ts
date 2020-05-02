export class Storehour 
{
    day: string; 
    openHour: Date; 
    closeHour: Date;
    
    constructor(day?: string, openHour?: Date, closeHour?: Date) 
    {
        this.day = day; 
        this.openHour = openHour; 
        this.closeHour = closeHour; 
    }
}
