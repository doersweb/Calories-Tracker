package com.doersweb.core.domain.usecases

class FilterOutDecimals {
    operator fun invoke(text: String): String {
        return text.filter { it.isDigit() }
    }
}