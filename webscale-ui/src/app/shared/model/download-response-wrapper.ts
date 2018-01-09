export class DownloadResponseWrapper {

    private _data: Blob;
    private _fileName: string;


    get data(): Blob {
        return this._data;
    }

    set data(value: Blob) {
        this._data = value;
    }

    get fileName(): string {
        return this._fileName;
    }

    set fileName(value: string) {
        this._fileName = value;
    }
}