import { CategoryTypeEnum } from './category-type-enum.enum';

export class Category {
    categoryId: number;
    name: string;
    description: string;
    parentCategory: Category;
    type: CategoryTypeEnum;

    constructor(categoryId?: number, name?: string, description?: string, type?: CategoryTypeEnum)
	{
		this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.type = type;
	}
}
