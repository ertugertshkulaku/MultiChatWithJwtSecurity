import { Injectable } from '@angular/core';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() { }

  signOut():void {
    window.localStorage.clear();
  }

saveToken(token: string): void{
    window.localStorage.removeItem(TOKEN_KEY);
    window.localStorage.setItem(TOKEN_KEY, token);
  }

 getToken():string{
      return localStorage.getItem(TOKEN_KEY);
  }

   saveUser(user):void{
    window.localStorage.removeItem(USER_KEY);
    window.localStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  getUser():any {
      return JSON.parse(localStorage.getItem(USER_KEY));
  }
}
