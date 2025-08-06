import {Component, inject, signal} from '@angular/core';
import {InputText} from 'primeng/inputtext';
import {IconField} from 'primeng/iconfield';
import {InputIcon} from 'primeng/inputicon';
import {FormsModule} from '@angular/forms';
import {RagService} from '../../../../services/ragService';
import {Message} from '../../../../carriers/message';
import {ChatType} from '../../../../carriers/chat.type';
import {NewlineToBrPipe} from '../../../../../newline-to-br-pipe';
import {ProgressSpinner} from 'primeng/progressspinner';

@Component({
    selector: 'chat-window-component',
    templateUrl: 'chat.window.component.html',
    imports: [
        InputText,
        IconField,
        InputIcon,
        FormsModule,
        NewlineToBrPipe,
        ProgressSpinner
    ],
    styleUrl: 'chat.window.component.scss'
})
export class ChatWindowComponent {

    protected question: string = '';
    protected isThinking = false;
    protected messages = signal<Message[]>([]);
    protected ragService = inject(RagService);

    sendQuestion() {
        console.log(this.question)
        this.isThinking = true;
        this.ragService.getAiResponse(this.question)
            .subscribe(response => {
                this.addToMessageArray(response.message, ChatType.AI);
                this.isThinking = false;
            })

        this.addToMessageArray(this.question, ChatType.USER);
        this.question = '';
    }

    private addToMessageArray(message: string, chatType: ChatType) {
        const aiResponse: Message = {
            chatType: chatType,
            message: message,
        }
        this.messages.update(messageArray => [...messageArray, aiResponse]);
    }

    protected readonly ChatType = ChatType;
}
