import {Component, OnInit} from '@angular/core';
import {CategoryService} from "../../shared/category.service";
import {MatTableDataSource} from "@angular/material";
import {CategoryTo} from "../../shared/model/category.to";
import {TableElement} from "../model/table-element";

@Component({
    selector: 'wu-categories',
    templateUrl: './categories.component.html',
    styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit {

    public categoryTableDataSource: MatTableDataSource<TableElement>;
    public displayedColumns: string[];
    public category: CategoryTo;

    constructor(private categoryService: CategoryService) {
    }

    ngOnInit() {
        this.initViewModel();
        this.loadCategoryTable();
    }

    private loadCategoryTable() {
        this.categoryService.allCategories()
            .subscribe(
                categories => this.categoryTableDataSource = new MatTableDataSource(this.createTableElementsFromData(categories)),
                error => console.log(error)
            );
    }

    private initViewModel() {
        this.displayedColumns = [];
        this.displayedColumns.push('position', 'name');
        this.category = new CategoryTo();
    }

    private createTableElementsFromData(categories: CategoryTo[]): TableElement[] {
        let tableElements: TableElement[] = [];
        categories.forEach((category, index) => {
            let tableElement: TableElement = new TableElement();
            tableElement.position = index + 1;
            tableElement.data = category;
            tableElements.push(tableElement);
        });
        return tableElements;
    }

    saveCategory() {
        this.categoryService.newCategory(this.category)
            .subscribe(
                response => {
                    this.loadCategoryTable();
                    this.category = new CategoryTo();
                }, error => console.log(error)
            );
    }
}
