import {Component, HostListener, Input, OnInit} from '@angular/core';
import {ImagesService} from "../../shared/images.service";
import {CategoryTo} from "../../shared/model/category.to";
import {CategoryService} from "../../shared/category.service";
import {MessageService} from "../../shared/message.service";

@Component({
    selector: 'wu-image-thumbnail',
    templateUrl: './image-thumbnail.component.html',
    styleUrls: ['./image-thumbnail.component.css']
})
export class ImageThumbnailComponent implements OnInit {

    @Input()
    public imageId: number;
    public imageCategories: string[];

    public showCategoryInput: boolean;
    public image: any;
    public categories: CategoryTo[];
    public selectedCategory: CategoryTo;
    public showPreview: boolean;

    constructor(private imagesService: ImagesService,
                private categoryService: CategoryService,
                private messageService: MessageService) {
    }

    ngOnInit() {
        this.imagesService.loadThumbnail(this.imageId)
            .subscribe(blob => this.createImageFromBlob(blob), error => console.log(error));

        this.categoryService.allCategories()
            .subscribe(categories => this.categories = categories, error => console.log(error));

        this.imageDetails();
    }

    private imageDetails(): void {
        this.imagesService.imageDetails(this.imageId)
            .subscribe(imageDetails => this.imageCategories = imageDetails.categories);
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

    public toggleCategoryInput(): void {
        this.showCategoryInput = !this.showCategoryInput;
    }

    @HostListener('document:keyup', ['$event'])
    public closeCategoryInputOnEscape(event: any): void {
        if (event.key === 'Escape') {
            this.showCategoryInput = false;
        }
    }

    public saveCategory(): void {
        let categoryForImage: CategoryTo = Object.assign(new CategoryTo(), this.selectedCategory);
        this.imagesService.categoryToImage(categoryForImage, this.imageId)
            .subscribe(
                _ => {
                    this.selectedCategory = null;
                    this.showCategoryInput = false;
                    this.messageService.displayMessage('categoryAddSuccess');
                    this.imageDetails();
                },
                error => console.log(error));
    }

    public imagePreview(): void {
        this.showPreview = true;
    }

    public closeImagePreview(): void {
        this.showPreview = false;
    }
}
