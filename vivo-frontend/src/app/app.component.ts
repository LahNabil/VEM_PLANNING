import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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

  constructor(private keycloakService: KeycloakService, private router: Router) {}

  async ngOnInit() {
    this.isAuthenticated = await this.keycloakService.isLoggedIn();
    if (this.isAuthenticated) {
      this.router.navigate(['/dashboard']); // Navigate to the welcome page if authenticated
    }
  }

  onToggleSideNav(data: SideNavToggle): void {
    this.screenWidth = data.screenWidth;
    this.isSideNavCollapsed = data.collapsed;
  }
}
