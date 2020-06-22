package data

interface Mapper<From, To> {

    fun mapFrom(input: From): To

    fun mapTo(input: To): From

}
