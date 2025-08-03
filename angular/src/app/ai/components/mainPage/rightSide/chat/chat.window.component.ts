import {Component} from '@angular/core';
import {InputText} from 'primeng/inputtext';
import {IconField} from 'primeng/iconfield';
import {InputIcon} from 'primeng/inputicon';

@Component({
    selector: 'chat-window-component',
    templateUrl: 'chat.window.component.html',
    imports: [
        InputText,
        IconField,
        InputIcon
    ],
    styleUrl: 'chat.window.component.scss'
})
export class ChatWindowComponent {

}
