import { Routes } from '@angular/router';
import {MainPage} from './ai/components/mainPage/main.page.component';
import {ChatWindowComponent} from './ai/components/mainPage/rightSide/chat/chat.window.component';
import {RightComponent} from './ai/components/mainPage/rightSide/right.component';
import {UploadComponent} from './ai/components/mainPage/rightSide/upload/upload.component';

export const routes: Routes = [

    {
        path: '',
        component: MainPage,
        // pathMatch: 'full',
        children: [
            {
                path: 'right',
                component: RightComponent,
                // pathMatch: 'full',
                children: [
                    {
                        path: 'chat',
                        component: ChatWindowComponent,
                        pathMatch: 'full'
                    },
                    {
                        path: 'upload',
                        component: UploadComponent,
                        pathMatch: 'full'
                    }
                ]
            }
        ]
    },

];
