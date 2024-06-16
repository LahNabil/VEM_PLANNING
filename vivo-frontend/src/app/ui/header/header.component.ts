import {Component, OnInit} from '@angular/core';
import {KeycloakService} from "keycloak-angular";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent implements OnInit{
  isMenuOpen = false;
  public profile : any;

  constructor(public keycloackService : KeycloakService) {
  }
  ngOnInit() {
    if(this.keycloackService.isLoggedIn()){
      this.keycloackService.loadUserProfile().then(profile=>{
        this.profile= profile;
      })
    }
  }



  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }
  async onLogin() {
    await this.keycloackService.login({
      redirectUri:window.location.origin
    });
  }

  logout() {
    this.keycloackService.logout(window.location.origin);
  }
}
