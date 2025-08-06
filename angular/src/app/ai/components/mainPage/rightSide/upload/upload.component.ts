import {Component} from '@angular/core';
import {FileUpload, FileUploadEvent, FileUploadHandlerEvent} from 'primeng/fileupload';
import {MessageService} from 'primeng/api';
import {Button} from 'primeng/button';
import {ProgressBar} from 'primeng/progressbar';
import {Toast} from 'primeng/toast';
import {Badge} from 'primeng/badge';
import {PrimeNG} from 'primeng/config';

@Component({
    selector: 'upload-component',
    templateUrl: 'upload.html',
    styleUrl: 'upload.scss',
    imports: [
        FileUpload,
        Button,
        ProgressBar,
        Toast,
        Badge
    ],
    providers: [MessageService]
})
export class UploadComponent {
    files = [];
    totalSize: number = 0;
    totalSizePercent: number = 0;

    constructor(private config: PrimeNG, private messageService: MessageService) {
    }

    choose(event, callback) {
        callback();
    }

    onRemoveTemplatingFile(event, file, removeFileCallback, index) {
        removeFileCallback(event, index);
        this.totalSize -= parseInt(this.formatSize(file.size));
        this.totalSizePercent = this.totalSize / 10;
    }

    onClearTemplatingUpload(clear) {
        clear();
        this.totalSize = 0;
        this.totalSizePercent = 0;
    }

    onTemplatedUpload() {
        this.messageService.add({severity: 'info', summary: 'Success', detail: 'File Uploaded', life: 3000});
    }

    onSelectedFiles(event) {
        this.files = event.currentFiles;
        this.files.forEach((file) => {
            this.totalSize += parseInt(this.formatSize(file.size));
        });
        this.totalSizePercent = this.totalSize / 10;
    }

    uploadEvent(callback) {
        callback();
    }

    formatSize(bytes) {
        const k = 1024;
        const dm = 3;
        const sizes = this.config.translation.fileSizeTypes;
        if (bytes === 0) {
            return `0 ${sizes[0]}`;
        }

        const i = Math.floor(Math.log(bytes) / Math.log(k));
        const formattedSize = parseFloat((bytes / Math.pow(k, i)).toFixed(dm));

        return `${formattedSize} ${sizes[i]}`;
    }


    onUpload($event: FileUploadEvent) {
        console.log($event);
        console.log($event.files);
    }

    myUploader($event: FileUploadHandlerEvent) {
        console.log($event.files.pop());
        console.log($event);

    }

}
