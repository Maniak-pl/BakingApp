package pl.maniak.udacity.bakingapp.utils.helpers;


import java.util.List;

import pl.maniak.udacity.bakingapp.data.Ingredient;

public class StringHelpers {

    public static String preparationIngredientsList(String title, List<Ingredient> list) {
        StringBuilder sb = new StringBuilder();
        sb.append(title).append("\n");
        for (Ingredient ingredient : list) {
            sb.append("\n\t* ")
                    .append(ingredient.getIngredient())
                    .append(" (")
                    .append(ingredient.getQuantity())
                    .append(" ")
                    .append(ingredient.getMeasure())
                    .append(")");
        }
        return sb.toString();
    }
}