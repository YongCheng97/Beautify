export class Staff {
    staffId: number;
    name: string;
    email: string;
    password: string;
    username: string; 

    constructor(staffId?: number, name?: string, email?: string, password?: string, username?: string) {
        this.staffId = staffId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username; 
    }
}
