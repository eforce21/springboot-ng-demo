import {Component, EventEmitter, HostListener, Input, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {ImagesService} from "../../shared/images.service";

@Component({
    selector: 'wu-image-preview',
    templateUrl: './image-preview.component.html',
    styleUrls: ['./image-preview.component.css'],
    encapsulation: ViewEncapsulation.None
})
export class ImagePreviewComponent implements OnInit {

    @Input()
    public showPreview: boolean;
    @Input()
    public imageId: number;
    @Output()
    public closePreview: EventEmitter<null> = new EventEmitter();
    public image: any;

    constructor(private imageService: ImagesService) {
    }

    ngOnInit() {
        this.imageService.loadPreview(this.imageId)
            .subscribe(blob => this.createImageFromBlob(blob), error => console.log(error));
    }

    private createImageFromBlob(image: Blob): void {
        let reader = new FileReader();
        reader.addEventListener("load", () => {
            this.image = reader.result;
        }, false);

        if (image) {
            reader.readAsDataURL(image);
        }
    }

    public onClosePreview(): void {
        this.closePreview.emit();
    }

    @HostListener('document:keyup', ['$event'])
    public closePreviewOnEscape(event: any): void {
        if (event.key === 'Escape') {
            this.closePreview.emit();
        }
    }

    public downloadImage(): void {
        this.imageService.downloadImage(this.imageId)
            .subscribe(
                responseWrapper => {
                    let a = document.createElement('a');
                    a.download = responseWrapper.fileName;
                    a.href = window.URL.createObjectURL(responseWrapper.data);
                    a.style.display = 'none';
                    a.click();
                },
                error => console.log(error)
            );

    }
}
