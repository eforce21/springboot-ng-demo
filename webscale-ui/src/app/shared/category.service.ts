import {Injectable} from '@angular/core';
import {Observable} from "rxjs/Observable";
import {CategoryTo} from "./model/category.to";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable()
export class CategoryService {

    private readonly CATEGORY_BASE_URL: string = environment.backendBaseUrl + '/category';
    private jsonContentTypeHeader: HttpHeaders;

    constructor(private httpClient: HttpClient) {
        this.jsonContentTypeHeader = new HttpHeaders().set('Content-Type', 'application/json');
    }

    allCategories(): Observable<CategoryTo[]> {
        return this.httpClient.get<CategoryTo[]>(this.CATEGORY_BASE_URL);
    }

    newCategory(category: CategoryTo): Observable<Object> {
        return this.httpClient.post(this.CATEGORY_BASE_URL, category.toPayload(), {headers: this.jsonContentTypeHeader});
    }
}
