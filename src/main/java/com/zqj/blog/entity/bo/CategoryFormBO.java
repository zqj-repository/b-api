package com.zqj.blog.entity.bo;

import com.zqj.blog.entity.Category;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CategoryFormBO {

    private Integer id;

    @NotEmpty
    private String name;

    private String description;

    public Category toPO() {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setDescription(description);
        return category;
    }

}
