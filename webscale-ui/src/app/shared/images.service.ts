import {Injectable} from '@angular/core';
import {ImageGetParams} from "../images/model/image-get-params";
import {Observable} from "rxjs/Observable";
import {ImageMetadataTo} from "../images/model/image-metadata.to";
import {CategoryTo} from "./model/category.to";
import {ImageDetailTo} from "../images/model/image-detail.to";
import {DownloadResponseWrapper} from "./model/download-response-wrapper";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {FileService} from "./file.service";
import {environment} from "../../environments/environment";
import {HttpData} from "./http-data.interface";

@Injectable()
export class ImagesService {

    private readonly IMAGE_URL: string = environment.backendBaseUrl + '/image';
    private jsonContentTypeHeader: HttpHeaders;
    private CONTENT_TYPE_FILE_TYPE: string[];

    constructor(private fileService: FileService,
                private httpClient: HttpClient) {
        this.jsonContentTypeHeader = new HttpHeaders().set('Content-Type', 'application/json');
        this.CONTENT_TYPE_FILE_TYPE = [];
        this.CONTENT_TYPE_FILE_TYPE['image/png'] = '.png';
    }

    public getImages(imageGetParams: ImageGetParams): Observable<ImageMetadataTo[]> {
        let urlParams: HttpParams = new HttpParams();
        urlParams = urlParams.append('pageNumber', imageGetParams.pageNumber.toString());
        urlParams = urlParams.append('amount', imageGetParams.amount.toString());

        if (null != imageGetParams.filterString) {
            urlParams = urlParams.append('filterString', imageGetParams.filterString);
        }
        return this.httpClient.get<ImageMetadataTo[]>(this.IMAGE_URL, {params: urlParams});
    }

    public loadThumbnail(imageId: number): Observable<Blob> {
        return this.imageData(imageId, 'thumbnail');
    }

    public loadPreview(imageId: number): Observable<Blob> {
        return this.imageData(imageId, 'preview');
    }

    public downloadImage(imageId: number): Observable<DownloadResponseWrapper> {
        return this.httpClient.get(this.IMAGE_URL + '/' + imageId + '/data', {responseType: 'blob'})
            .map(response => {
                let blob: Blob = new Blob([response], {type: 'application/octet-stream'});
                // let contentDisposition: string = response.headers.get('Content-Disposition');
                // let fileName: string = contentDisposition.split('=')[1];
                let responseWrapper: DownloadResponseWrapper = new DownloadResponseWrapper();
                responseWrapper.data = blob;
                responseWrapper.fileName = 'download' + this.CONTENT_TYPE_FILE_TYPE[response.type];
                return responseWrapper;
            });
    }

    private imageData(imageId: number, dataUrl: string): Observable<Blob> {
        return this.httpClient.get(this.IMAGE_URL + '/' + imageId + '/' + dataUrl, {responseType: 'blob'});
    }

    public categoryToImage(category: HttpData, imageId: number): Observable<Object> {
        return this.httpClient.post(this.IMAGE_URL + '/' + imageId + '/category', category.toPayload(), {headers: this.jsonContentTypeHeader});
    }

    public imageDetails(imageId: number): Observable<ImageDetailTo> {
        return this.httpClient.get<ImageDetailTo>(this.IMAGE_URL + '/' + imageId);
    }

    public imageUpload(image: File): Promise<Object> {
        return this.fileService.doUpload(image, this.IMAGE_URL);
    }
}
