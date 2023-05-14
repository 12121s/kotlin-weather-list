package com.illis.weatherlist.data.source.remote

class EmptyBodyException(message: String? = "") : Exception(message)
class NetworkFailureException(message: String? = "") : Exception(message)