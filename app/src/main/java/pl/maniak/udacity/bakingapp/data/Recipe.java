package pl.maniak.udacity.bakingapp.data;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Recipe {

    private Integer id;
    private String name;
    private List<Ingredient> ingredients = null;
    private List<Step> steps = null;
    private Integer servings;
    private String image;
}