export class ImageGetParams {

    private _pageNumber: number;
    private _amount: number;
    private _filterString: string;

    get pageNumber(): number {
        return this._pageNumber;
    }

    set pageNumber(value: number) {
        this._pageNumber = value;
    }

    get amount(): number {
        return this._amount;
    }

    set amount(value: number) {
        this._amount = value;
    }

    get filterString(): string {
        return this._filterString;
    }

    set filterString(value: string) {
        this._filterString = value;
    }
}