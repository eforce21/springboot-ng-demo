export class TableElement {
    private _position: number;
    private _data: any;

    get position(): number {
        return this._position;
    }

    set position(value: number) {
        this._position = value;
    }

    get data(): any {
        return this._data;
    }

    set data(value: any) {
        this._data = value;
    }
}