package pl.maniak.udacity.bakingapp.data;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
class Ingredient {

    private float quantity;
    private String measure;
    private String ingredient;
}