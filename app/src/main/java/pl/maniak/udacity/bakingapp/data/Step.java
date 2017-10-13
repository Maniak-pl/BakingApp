package pl.maniak.udacity.bakingapp.data;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
class Step {

    private Integer id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;
}