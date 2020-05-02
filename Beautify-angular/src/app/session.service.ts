import { Injectable } from '@angular/core';

import { ServiceProvider } from './service-provider';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  constructor() { }

  getIsLogin(): boolean {
    if (sessionStorage.isLogin == "true") {
      return true;
    } else {
      return false;
    }
  }

  setIsLogin(isLogin: boolean): void {
    sessionStorage.isLogin = isLogin;
  }

  getCurrentServiceProvider(): ServiceProvider {
    return JSON.parse(sessionStorage.currentServiceProvider);
  }

  setCurrentServiceProvider(currentServiceProvider: ServiceProvider): void {
    sessionStorage.currentServiceProvider = JSON.stringify(currentServiceProvider);
    sessionStorage.currentServiceProvider.password = sessionStorage.password; 
  }

  getUsername(): string {
    return sessionStorage.username;
  }

  setUsername(username: string): void {
    sessionStorage.username = username;
  }

  getPassword(): string {
    return sessionStorage.password;
  }

  setPassword(password: string): void {
    sessionStorage.password = password;
  }
}
