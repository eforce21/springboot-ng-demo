import {Component, OnInit} from "@angular/core";
import {ImagesService} from "../../shared/images.service";
import {ImageGetParams} from "../model/image-get-params";
import {ImageMetadataTo} from "../model/image-metadata.to";

@Component({
    selector: 'wu-images-display',
    templateUrl: './images-display.component.html',
    styleUrls: ['./images-display.component.css']
})
export class ImagesDisplayComponent implements OnInit {

    public imageMetaData: ImageMetadataTo[];
    public filter: string;
    private currentPageNumber: number;
    private readonly IMAGE_AMOUNT: number = 21;

    constructor(private imagesService: ImagesService) {
    }

    public ngOnInit(): void {
        this.currentPageNumber = 0;
        this.imageMetaData = [];
        this.loadInitialThumbnails();
    }

    private loadInitialThumbnails(): void {
        let imageParams: ImageGetParams = new ImageGetParams();
        imageParams.pageNumber = this.currentPageNumber;
        imageParams.amount = this.IMAGE_AMOUNT;
        this.loadThumbNailPage(imageParams);
    }

    public loadImagePage(): void {
        let imageParams: ImageGetParams = new ImageGetParams();
        imageParams.pageNumber = ++this.currentPageNumber;
        imageParams.amount = this.IMAGE_AMOUNT;
        imageParams.filterString = null;
        if (null != this.filter && this.filter.length > 0) {
            imageParams.filterString = this.filter;
        }
        this.loadThumbNailPage(imageParams);
    }

    private loadThumbNailPage(imageParams: ImageGetParams): void {
        this.imagesService.getImages(imageParams)
            .subscribe(imageMetaData => imageMetaData.forEach(data => this.imageMetaData.push(data)), error => console.log(error));
    }

    public filterImages(): void {
        let filterImageGetParams: ImageGetParams = new ImageGetParams();
        filterImageGetParams.pageNumber = 0;
        filterImageGetParams.amount = this.IMAGE_AMOUNT;
        filterImageGetParams.filterString = this.filter.length > 0 ? this.filter : null;
        this.imagesService.getImages(filterImageGetParams)
            .subscribe(imageMetaData => this.imageMetaData = imageMetaData, error => console.log(error));
    }
}