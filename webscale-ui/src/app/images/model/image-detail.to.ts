export class ImageDetailTo {

    private _size: number;
    private _width: number;
    private _height: number;

    private _thumbnailWidth: number;
    private _thumbnailHeight: number;

    private _recordingDate: Date;

    private _categories: string[];

    private _previewPath: string;
    private _thumbnailPath: string;


    get size(): number {
        return this._size;
    }

    set size(value: number) {
        this._size = value;
    }

    get width(): number {
        return this._width;
    }

    set width(value: number) {
        this._width = value;
    }

    get height(): number {
        return this._height;
    }

    set height(value: number) {
        this._height = value;
    }

    get thumbnailWidth(): number {
        return this._thumbnailWidth;
    }

    set thumbnailWidth(value: number) {
        this._thumbnailWidth = value;
    }

    get thumbnailHeight(): number {
        return this._thumbnailHeight;
    }

    set thumbnailHeight(value: number) {
        this._thumbnailHeight = value;
    }

    get recordingDate(): Date {
        return this._recordingDate;
    }

    set recordingDate(value: Date) {
        this._recordingDate = value;
    }

    get categories(): string[] {
        return this._categories;
    }

    set categories(value: string[]) {
        this._categories = value;
    }

    get previewPath(): string {
        return this._previewPath;
    }

    set previewPath(value: string) {
        this._previewPath = value;
    }

    get thumbnailPath(): string {
        return this._thumbnailPath;
    }

    set thumbnailPath(value: string) {
        this._thumbnailPath = value;
    }
}