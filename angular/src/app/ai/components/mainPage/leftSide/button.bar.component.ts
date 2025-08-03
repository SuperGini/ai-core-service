import {Component} from '@angular/core';
import {ButtonDirective, ButtonIcon, ButtonLabel} from 'primeng/button';
import {RouterLink} from '@angular/router';

@Component({
    selector: 'button-bar-component',
    templateUrl: 'button.bar.html',
    imports: [
        ButtonDirective,
        ButtonIcon,
        ButtonLabel,
        RouterLink
    ],
    styleUrl: 'button.bar.scss'
})
export class ButtonBarComponent {


}
