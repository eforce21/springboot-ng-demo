export class ImageMetadataTo {
    private _id: number;
    private _name: string;
    private _path: string;
    private _mimeType: string;

    get id(): number {
        return this._id;
    }

    set id(value: number) {
        this._id = value;
    }

    get name(): string {
        return this._name;
    }

    set name(value: string) {
        this._name = value;
    }

    get path(): string {
        return this._path;
    }

    set path(value: string) {
        this._path = value;
    }

    get mimeType(): string {
        return this._mimeType;
    }

    set mimeType(value: string) {
        this._mimeType = value;
    }
}