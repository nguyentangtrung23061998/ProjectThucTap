import { Deserializable } from '../interfaces/deserializable.interface';
import { Serializable } from '../interfaces/serializable.interface';

export class UserAccount implements Deserializable,Serializable{

    private username:string;

    private password:string;

    private email:string;

    private image: string;

    private fullname : string;

    private token : string;

    private role: string = "";
    
    private provider: string;

    constructor(){}

    set setUsername(username:string){
        this.username = username;
    }

    get getUserName():string{
        return this.username;
    }

    set setPassword(password:string){
        this.password = password;
    }

    get getPassword():string{
        return this.password;
    }

    set setEmail(email:string){
        this.email = email;
    }

    get getEmail():string{
        return this.email;
    }

    set setImage(image:string){
        this.image = image;
    }

    get getImage():string{
        return this.image;
    }

    set setToken(token:string){
        this.token = token;
    }

    get getToken():string{
        return this.token;
    }

    set setFullname(fullname:string){
        this.fullname = fullname;
    }

    get getFullname():string{
        return this.fullname;
    }

    set setRole(role:string){
        this.role = role;
    }

    get getRole():string{
        return this.role;
    }

    set setProvider(provider:string){
        this.provider = provider;
    }

    get getProvider():string{
        return this.provider;
    }

    serialize():this{
        let output: any = Object.assign({}, this);
		return output;
    }

    deserialize(input: any): this {
        Object.assign(this, input);
        return this;
    }

}