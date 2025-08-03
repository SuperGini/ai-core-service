import {Component} from '@angular/core';
import {ButtonBarComponent} from './leftSide/button.bar.component';
import {RouterOutlet} from '@angular/router';

@Component({
    selector: 'main-page-component',
    templateUrl: 'main.page.html',
    styleUrl: 'main.page.scss',
    imports: [
        ButtonBarComponent,
        RouterOutlet
    ]
})
export class MainPage {

}
