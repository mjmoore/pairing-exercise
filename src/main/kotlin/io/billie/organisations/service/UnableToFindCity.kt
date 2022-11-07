package io.billie.organisations.service

class UnableToFindCity(country: String, city: String)
    : RuntimeException("Could not find $city in $country")
