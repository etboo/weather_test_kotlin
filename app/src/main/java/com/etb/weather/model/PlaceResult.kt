package com.etb.weather.model


class PlacesResult {

    var predictions: List<Prediction>? = null

    var status: String? = null
}

class Prediction {
    var description: String? = null
}