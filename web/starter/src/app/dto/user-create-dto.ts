export class UserCreateDto {
    name: string;
    email: string;
    password: string;
    city: String;


    constructor(
      name: string,
      email: string,
      password: string,
      phone: string,
      notes: string,
      role: string
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
  }
  