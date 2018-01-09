import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ImagesService} from "../../shared/images.service";
import {MessageService} from "../../shared/message.service";

@Component({
    selector: 'wu-image-upload',
    templateUrl: './image-upload.component.html',
    styleUrls: ['./image-upload.component.css'],
    encapsulation: ViewEncapsulation.None
})
export class ImageUploadComponent implements OnInit {

    public images: File[];

    constructor(private imageService: ImagesService,
                private messageService: MessageService) {
    }

    ngOnInit() {
    }

    public chooseFile(event: any): void {
        this.images = Array.from(event.target.files);
    }

    public uploadImage(): void {
        this.images.forEach(image => {

            if (!/(\.jpg|\.jpeg|\.png|\.gif)$/i.exec(image.name)) {
                this.messageService.displayMessage('imageFileExtensionError');
                return;
            }

            this.imageService.imageUpload(image)
                .then(_ => {
                    this.messageService.displayMessage('imageUploadSuccess');
                    this.images = [];
                })
                .catch(_ => this.messageService.displayMessage('imageUploadFailure'))
        });
    }
}
