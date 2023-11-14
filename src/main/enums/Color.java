package main.enums;

public enum Color {
    WHITE("WHITE"),
    BLACK("BLACK");

    private String color;

    private Color(String color){
        this.color = color;
    }

    public String getColor(){
        return this.color;
    }
}
