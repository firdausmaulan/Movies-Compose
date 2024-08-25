package com.fd.movie.data.mapper

object MapHelper {

    fun get(value: Int?) : Int {
        return get(value, 0)
    }

    fun get(value : Int?, defaultValue : Int) : Int {
        if (value == null) return defaultValue
        return value
    }

    fun get(value: Double?) : Double {
        return get(value, 0.0)
    }

    fun get(value : Double?, defaultValue : Double) : Double {
        if (value == null) return defaultValue
        return value
    }

    fun get(value: String?) : String {
        return get(value, "")
    }

    fun get(value : String?, defaultValue : String) : String {
        if (value == null) return defaultValue
        return value
    }

}