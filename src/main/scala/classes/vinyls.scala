package vinyls

class Vinyl (
    var vinylName: String,
    var vinylType: String,
    var musicType: String,
    var releaseDate: String,
    var condition: String,
    var price: Float
){
    private var vinylId: Int = 0
}

class Vinyls(){
    def loadVinyls(): Null = {}
    def saveVinyls(): Null = {}
}