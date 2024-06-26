import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';

interface SideNavToggle {
  screenWidth: number;
  collapsed: boolean;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'vivo-frontend';
  isSideNavCollapsed = false;
  screenWidth = 0;
  isAuthenticated = false;

  constructor(private router: Router, private keycloakService: KeycloakService) {}

  async ngOnInit() {
    this.isAuthenticated = await this.keycloakService.isLoggedIn();
  }

  onToggleSideNav(data: SideNavToggle): void {
    this.screenWidth = data.screenWidth;
    this.isSideNavCollapsed = data.collapsed;
  }
}
