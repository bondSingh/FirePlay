package saty.firebase


data class Person(
    var name: String,
    var age: Int,
    var place: String,
    var degree: String,
    var language: String
){
    constructor() : this("" ,-1,"","","")
}
