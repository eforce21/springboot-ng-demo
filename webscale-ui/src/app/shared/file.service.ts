import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";

@Injectable()
export class FileService {

    constructor() {
    }

    doUpload(file: File, url: string): Promise<Object> {
        return new Promise((resolve, reject) => {
            let formData: any = new FormData();
            let xhr = new XMLHttpRequest();

            formData.append('file', file, file.name);
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        resolve(xhr.response);
                    } else {
                        reject(xhr.status);
                    }
                }
            };
            xhr.open('POST', url, true);
            xhr.setRequestHeader('X-Auth-Token', localStorage.getItem(environment.authTokenKey));
            xhr.send(formData);
        });
    }
}
