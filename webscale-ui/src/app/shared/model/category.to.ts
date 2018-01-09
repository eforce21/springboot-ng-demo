import {HttpData} from "../http-data.interface";

export class CategoryTo implements HttpData {

    private _id: number;
    private _name: string;
    private _images: number;


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

    get images(): number {
        return this._images;
    }

    set images(value: number) {
        this._images = value;
    }

    toPayload(): string {
        let data = {
            id: this._id,
            name: this._name,
            images: this._images
        };
        return JSON.stringify(data);
    }
}